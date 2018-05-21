package com.ping.test.thread.lock.rwlock.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * 读写锁经典案例，防缓存系统
 * https://blog.csdn.net/cdw8131197/article/details/52601559
 * @author zhangfei
 *
 */
public class ReadWriteLockTest {

	ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	Map<String, Object> cache = new HashMap<String, Object>();

	public Object get(String key) {
		Object value = null;
		try {
			rwl.readLock().lock();
			value = cache.get(key);
			if (null == value) {
				rwl.readLock().unlock();// 释放都锁，获取写锁
				try {
					rwl.writeLock().lock();
					value = cache.get(key);
					// 获取写锁后再次判断对象是否为null，方式下一个等待的写线程进入后直接获取数据去
					if (null == value) {
						System.out.println(Thread.currentThread().getName());
						value = "aaaaa";// 实际操作代码从数据库中查询得到的对象
						cache.put(key, value);
					}
					// 自身锁降级为都锁
					rwl.readLock().lock();
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					rwl.writeLock().unlock();// 释放写锁
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			rwl.readLock().unlock();
		}
		return value;
	}

}