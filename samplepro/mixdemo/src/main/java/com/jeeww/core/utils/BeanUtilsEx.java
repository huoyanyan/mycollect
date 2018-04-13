package com.jeeww.core.utils;

import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.time.DateUtils;

/**
 * 重写BeanUtils方法，支持日期的拷贝.
 * @author 蒋文武
 */
public class BeanUtilsEx extends BeanUtils {
    static {
        ConvertUtils.register(new DateConvertExt(), java.util.Date.class);
        ConvertUtils.register(new DateConvertExt(), java.sql.Date.class);
        ConvertUtils.register(new DateConvertExt(), java.sql.Timestamp.class);
    }

    /**
     * 将实体orig中的值拷贝到dest中.
     * @param dest 拷贝到
     * @param orig 源数据
     * @param ignoreNull 是否忽略空值，即值为空时不拷贝.
     * @throws Exception e
     */
    public static void copyProperties(final Object dest, final Object orig, final boolean ignoreNull) throws Exception {
        PropertyUtilsBean pUtilsBean = BeanUtilsBean.getInstance().getPropertyUtils();
        if (orig instanceof DynaBean) {
            DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass().getDynaProperties();
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if (pUtilsBean.isReadable(orig, name) && pUtilsBean.isWriteable(dest, name)) {
                    Object value = ((DynaBean) orig).get(name);
                    copyProperty(dest, name, value, ignoreNull);
                }
            }
        } else if (orig instanceof Map) {
            Iterator<?> entries = ((Map<?, ?>) orig).entrySet().iterator();
            while (entries.hasNext()) {
                Entry<?, ?> entry = (Entry<?, ?>) entries.next();
                String name = (String) entry.getKey();
                if (pUtilsBean.isWriteable(dest, name)) {
                    copyProperty(dest, name, entry.getValue(), ignoreNull);
                }
            }
        } else { /* if (orig is a standard JavaBean) */
            PropertyDescriptor[] origDescriptors = pUtilsBean.getPropertyDescriptors(orig);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }
                if (pUtilsBean.isReadable(orig, name) && pUtilsBean.isWriteable(dest, name)) {
                    Object value = pUtilsBean.getSimpleProperty(orig, name);
                    copyProperty(dest, name, value, ignoreNull);
                }
            }
        }
    }

    /**
     * 自定义拷贝.
     * @param bean 目标对象.
     * @param name 字段名.
     * @param value 值.
     * @param ignoreNull 是否忽略空值，即值为空时不拷贝.
     * @throws Exception e
     */
    public static void copyProperty(final Object bean, final String name, final Object value, final boolean ignoreNull)
            throws Exception {
        if (ignoreNull) {
            if (value != null && !value.equals("")) {
                BeanUtilsBean.getInstance().copyProperty(bean, name, value);
            }
        } else {
            BeanUtilsBean.getInstance().copyProperty(bean, name, value);
        }
    }

    /**
     * 将实体orig中的值拷贝到dest中.
     * @param dest 拷贝到
     * @param orig 源数据
     */
    public static void copyProperties(final Object dest, final Object orig) {
        try {
            copyProperties(dest, orig, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * 内部类.
 * @author 蒋文武
 */
class DateConvertExt implements Converter {
    @Override
    public Object convert(@SuppressWarnings("rawtypes") final Class arg0, final Object arg1) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (arg1 != null) {
                if (arg1 instanceof java.util.Date) {
                    return df.parse(df.format(new java.util.Date(((java.util.Date) arg1).getTime())));
                } else if (arg1 instanceof java.sql.Date) {
                    return df.parse(df.format(new java.util.Date(((java.sql.Date) arg1).getTime())));
                } else {
                    String[] pattern = new String[] {"yyyy-MM", "yyyyMM", "yyyy/MM", "yyyyMMdd", "yyyy-MM-dd",
                            "yyyy/MM/dd", "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss" };
                    if (arg1 instanceof Object[]) {
                        Object[] at = (Object[]) arg1;
                        if (at[0] != null && !at[0].equals("")) {
                            return DateUtils.parseDate(at[0].toString(), pattern);
                        } else {
                            return null;
                        }
                    } else {
                        return DateUtils.parseDate(arg1.toString(), pattern);
                    }
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arg1;
    }
}
