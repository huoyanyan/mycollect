package com.boot.test.springretry.demo1;

import java.sql.SQLDataException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * https://blog.csdn.net/u011116672/article/details/77823867
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryState;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.policy.CircuitBreakerRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Configuration
@EnableRetry(proxyTargetClass = true)
@Component
public class RetryExamples {
	private static final Logger log = LoggerFactory.getLogger(RetryExamples.class);

	/**
	 * 基于最大重试次数策略的重试，如果重试了3次仍然抛出异常则停止重试，执行兜底回调
	 * 
	 * @throws Exception
	 */
	private void retryExample3() throws Exception {
		RetryTemplate retryTemplate = new RetryTemplate();

		SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
		simpleRetryPolicy.setMaxAttempts(3);

		retryTemplate.setRetryPolicy(simpleRetryPolicy);

		Integer result = retryTemplate.execute(new RetryCallback<Integer, Exception>() {
			int i = 0;

			// 重试操作
			@Override
			public Integer doWithRetry(RetryContext retryContext) throws Exception {
				log.info("retry count: {}", retryContext.getRetryCount());
				return len(i++);
			}
		}, new RecoveryCallback<Integer>() { // 兜底回调
			@Override
			public Integer recover(RetryContext retryContext) throws Exception {
				log.info("after retry: {}, recovery method called!", retryContext.getRetryCount());
				return Integer.MAX_VALUE;
			}
		});
		log.info("final result: {}", result);
	}

	private int len(int i) throws Exception {
		if (i < 10)
			throw new Exception(i + " le 10");
		return i;
	}

	/**
	 * 熔断重试策略模式（CircuitBreakerRetryPolicy）
	 * 
	 * @throws Exception
	 */
	private void retryExample4() throws Exception {
		RetryTemplate template = new RetryTemplate();
		CircuitBreakerRetryPolicy retryPolicy = new CircuitBreakerRetryPolicy(new SimpleRetryPolicy(3));
		retryPolicy.setOpenTimeout(5000);
		retryPolicy.setResetTimeout(20000);
		template.setRetryPolicy(retryPolicy);

		for (int i = 0; i < 10; i++) {
			// Thread.sleep(100);
			try {
				Object key = "circuit";
				boolean isForceRefresh = false;
				RetryState state = new DefaultRetryState(key, isForceRefresh);
				String result = template.execute(new RetryCallback<String, RuntimeException>() {
					@Override
					public String doWithRetry(RetryContext context) throws RuntimeException {
						log.info("retry count: {}", context.getRetryCount());
						throw new RuntimeException("timeout");
					}
				}, new RecoveryCallback<String>() {
					@Override
					public String recover(RetryContext context) throws Exception {
						return "default";
					}
				}, state);
				log.info("result: {}", result);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	@Retryable(value = SQLDataException.class, backoff = @Backoff(value = 0L))
	public String service3() throws SQLDataException {
		log.info("service3 open");
		throw new SQLDataException();
	}

	@Recover
	public String recover(SQLDataException ne) {
		return "SQLDataException recover";
	}
}
