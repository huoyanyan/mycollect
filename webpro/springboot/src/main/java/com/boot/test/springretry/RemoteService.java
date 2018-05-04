package com.boot.test.springretry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class RemoteService {

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@Retryable(value = { BusinessException.class }, maxAttempts = 3, backoff = @Backoff(delay = 5000l, multiplier = 2))
	public void call() throws Exception {
		logger.info("do something...");
		throw new BusinessException("RPC调用异常");
	}

	@Recover
	public void recover(BusinessException e) {
		logger.info(" ---------------------------  ");
		logger.info(e.getMessage());
	}
}
