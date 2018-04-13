package algorithm.backoff;

import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.BackOffExecution;
import org.springframework.util.backoff.FixedBackOff;

/**
 * 固定时间间隔重试
 * 
 * @author zhangfei
 *
 */
public class FixedBackOffTest {

	public static void main(String[] args) {
		testFixedBackOff();
	}

	public static void testFixedBackOff() {
		long interval = 100;
		long maxAttempts = 10;
		BackOff backOff = new FixedBackOff(interval, maxAttempts);
		BackOffExecution execution = backOff.start();

		for (int i = 1; i <= 10; i++) {
			// 每次重试时间是100毫秒
			System.out.println(execution.nextBackOff());
		}

		System.out.println(BackOffExecution.STOP);
		System.out.println(execution.nextBackOff());
	}
}
