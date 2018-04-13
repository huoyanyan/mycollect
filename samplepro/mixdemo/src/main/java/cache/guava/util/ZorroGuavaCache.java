package cache.guava.util;

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

/**
 * @description: 利用guava实现的内存缓存。缓存加载之后永不过期，后台线程定时刷新缓存值。刷新失败时将继续返回旧缓存。
 *               需要在子类中初始化refreshDuration、refreshTimeunitType、
 *               cacheMaximumSize三个参数 后台刷新线程池为该系统中所有子类共享，大小为20.
 * @author: luozhuo
 * @date: 2017年6月21日 上午10:03:45
 * @version: V1.0.0
 * @param <K>
 * @param <V>
 */
public abstract class ZorroGuavaCache<K, V> {

	/**
	 * 缓存自动刷新周期
	 */
	protected int refreshDuration;

	/**
	 * 缓存刷新周期时间格式
	 */
	protected TimeUnit refreshTimeunitType;

	/**
	 * 缓存最大容量
	 */
	protected int cacheMaximumSize;

	private LoadingCache<K, V> cache;
	private ListeningExecutorService backgroundRefreshPools = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(20));

	/**
	 * @description: 初始化所有protected字段：
	 *               refreshDuration、refreshTimeunitType、cacheMaximumSize
	 * @author: luozhuo
	 * @date: 2017年6月13日 下午2:49:19
	 */
	protected abstract void initCacheFields();

	/**
	 * @description: 定义缓存值的计算方法
	 * @description: 新值计算失败时抛出异常，get操作时将继续返回旧的缓存
	 * @param key
	 * @return
	 * @author: luozhuo
	 * @throws Exception
	 * @date: 2017年6月14日 下午7:11:10
	 */
	protected abstract V getValueWhenExpire(K key) throws Exception;

	/**
	 * @description: 提供给外部使用的获取缓存方法，由实现类进行异常处理
	 * @param key
	 * @return
	 * @author: luozhuo
	 * @date: 2017年6月15日 下午12:00:57
	 */
	public abstract V getValue(K key);

	/**
	 * @description: 获取cache实例
	 * @return
	 * @author: luozhuo
	 * @date: 2017年6月13日 下午2:50:11
	 */
	private LoadingCache<K, V> getCache() {
		if (cache == null) {
			synchronized (this) {
				if (cache == null) {
					initCacheFields();

					cache = CacheBuilder.newBuilder().maximumSize(cacheMaximumSize)
							.refreshAfterWrite(refreshDuration, refreshTimeunitType).build(new CacheLoader<K, V>() {
								@Override
								public V load(K key) throws Exception {
									return getValueWhenExpire(key);
								}

								@Override
								public ListenableFuture<V> reload(final K key, V oldValue) throws Exception {
									return backgroundRefreshPools.submit(new Callable<V>() {
										public V call() throws Exception {
											return getValueWhenExpire(key);
										}
									});
								}
							});
				}
			}
		}
		return cache;
	}

	/**
	 * @description: 从cache中拿出数据的操作
	 * @param key
	 * @return
	 * @throws ExecutionException
	 * @author: luozhuo
	 * @date: 2017年6月13日 下午5:07:11
	 */
	protected V fetchDataFromCache(K key) throws ExecutionException {
		return getCache().get(key);
	}

}