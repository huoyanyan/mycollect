package com.jeeww.core.ext;

import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

/**
 * 类描述：性能监控类.
 * @author 蒋文武
 */
public class PerfInterceptor implements MethodInterceptor {
    /**
     * 日志.
     */
    private Logger logger = Logger.getLogger(PerfInterceptor.class);
    /**
     * 当前执行的方法.
     */
    private static ConcurrentHashMap<String, MethodStats> methodStats = new ConcurrentHashMap<String, MethodStats>();
    /**
     * .
     */
    private static final long STAT_LOG_FREQUENCY = 10;
    /**
     * 方法报警时间点.
     */
    private static final long METHOD_WARNING_THRESHOLD = 1000;

    /**
     * @throws Throwable 异常.
     * @return Object o.
     * @param method 执行的方法.
     */
    public Object invoke(final MethodInvocation method) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return method.proceed();
        } finally {
            updateStats(method.getMethod().getDeclaringClass().getName(), method.getMethod().getName(),
                    (System.currentTimeMillis() - start));
        }
    }

    /**
     * @param className 类名.
     * @param methodName 方法名.
     * @param elapsedTime 执行时间.
     */
    private void updateStats(final String className, final String methodName, final long elapsedTime) {
        MethodStats stats = methodStats.get(methodName);
        if (stats == null) {
            stats = new MethodStats(methodName);
            methodStats.put(methodName, stats);
        }
        stats.count++;
        stats.totalTime += elapsedTime;
        if (elapsedTime > stats.maxTime) {
            stats.maxTime = elapsedTime;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("性能监控：类名--- " + className + " 方法名---" + methodName + " 消耗时间--- " + elapsedTime);
        }
        if (elapsedTime > METHOD_WARNING_THRESHOLD) {
            logger.warn("警告: 类名---" + className + " 方法名---" + methodName + " 运行次数--- " + stats.count + ", 当前消耗时间 = "
                    + elapsedTime + ", 最长消耗时间--- " + stats.maxTime);
        }
        if (stats.count % STAT_LOG_FREQUENCY == 0) {
            long avgTime = stats.totalTime / stats.count;
            long runningAvg = (stats.totalTime - stats.lastTotalTime) / STAT_LOG_FREQUENCY;
            logger.debug("警告: 类名---" + className + " 方法名---" + methodName + " 运行次数--- " + stats.count + ", 当前消耗时间 = "
                    + elapsedTime + ", 最长消耗时间--- " + stats.maxTime + ", avgTime = " + avgTime + ", runningAvg = "
                    + runningAvg);
            // reset the last total time
            stats.lastTotalTime = stats.totalTime;
        }
    }

    /**
     * 内部类.
     * @author 蒋文武
     */
    static class MethodStats {
        /**
         * 方法名称.
         */
        private String methodName;
        /**
         * 次数.
         */
        private long count;
        /**
         * 总时间.
         */
        private long totalTime;
        /**
         * 最后执行时间.
         */
        private long lastTotalTime;
        /**
         * 最大时间.
         */
        private long maxTime;

        /**
         * @return the methodName
         */
        public final String getMethodName() {
            return methodName;
        }

        /**
         * @param methodName the methodName to set.
         */
        public void setMethodName(final String methodName) {
            this.methodName = methodName;
        }

        /**
         * @return the count
         */
        public final long getCount() {
            return count;
        }

        /**
         * @param count the count to set
         */
        public final void setCount(final long count) {
            this.count = count;
        }

        /**
         * @return the totalTime
         */
        public final long getTotalTime() {
            return totalTime;
        }

        /**
         * @param totalTime the totalTime to set
         */
        public final void setTotalTime(final long totalTime) {
            this.totalTime = totalTime;
        }

        /**
         * @return the lastTotalTime
         */
        public final long getLastTotalTime() {
            return lastTotalTime;
        }

        /**
         * @param lastTotalTime the lastTotalTime to set
         */
        public final void setLastTotalTime(final long lastTotalTime) {
            this.lastTotalTime = lastTotalTime;
        }

        /**
         * @return the maxTime
         */
        public final long getMaxTime() {
            return maxTime;
        }

        /**
         * @param maxTime the maxTime to set
         */
        public final void setMaxTime(final long maxTime) {
            this.maxTime = maxTime;
        }

        /**
         * 方法名称.
         * @param paraMethodName 方法名.
         */
        public MethodStats(final String paraMethodName) {
            this.methodName = paraMethodName;
        }
    }
}
