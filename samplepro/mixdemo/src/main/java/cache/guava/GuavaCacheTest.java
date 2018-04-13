package cache.guava;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class GuavaCacheTest {

	public static void main(String[] args) {

	}

	/**
	 * 定时过期
	 */
	public static void test1() {
		LoadingCache<String, Object> caches = CacheBuilder.newBuilder().maximumSize(100)
				.expireAfterWrite(10, TimeUnit.MINUTES).build(new CacheLoader<String, Object>() {
					@Override
					public Object load(String key) throws Exception {
						return generateValueByKey(key);
					}
				});
		try {
			System.out.println(caches.get("key-zorro"));
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 定时刷新
	 */
	public static void test2() {
		LoadingCache<String, Object> caches = CacheBuilder.newBuilder().maximumSize(100)
				.refreshAfterWrite(10, TimeUnit.MINUTES).build(new CacheLoader<String, Object>() {
					@Override
					public Object load(String key) throws Exception {
						return generateValueByKey(key);
					}
				});
		try {
			System.out.println(caches.get("key-zorro"));
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 异步刷新
	 */
	public static void test3() {
		ListeningExecutorService backgroundRefreshPools = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(20));
		LoadingCache<String, Object> caches = CacheBuilder.newBuilder().maximumSize(100)
				.refreshAfterWrite(10, TimeUnit.MINUTES).build(new CacheLoader<String, Object>() {
					@Override
					public Object load(String key) throws Exception {
						return generateValueByKey(key);
					}

					@Override
					public ListenableFuture<Object> reload(String key, Object oldValue) throws Exception {
						return backgroundRefreshPools.submit(new Callable<Object>() {

							@Override
							public Object call() throws Exception {
								return generateValueByKey(key);
							}
						});
					}
				});
		try {
			System.out.println(caches.get("key-zorro"));
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public static String generateValueByKey(String key) {
		return null;
	}
}
