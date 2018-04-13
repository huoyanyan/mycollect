package com.ping.test.dateutilformat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 它不是线程安全的。在正常的测试情况之下，都没有问题，但一旦在生产环境中一定负载情况下时，这个问题就出来了。他会出现各种不同的情况，比如转化的时间不正确，
 * 比如报错，比如线程被挂死等等
 * 
 * @author zhangfei
 *
 */
public class DateUtil {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String formatDate(Date date) throws ParseException {
		return sdf.format(date);
	}

	public static Date parse(String strDate) throws ParseException {

		return sdf.parse(strDate);
	}
}