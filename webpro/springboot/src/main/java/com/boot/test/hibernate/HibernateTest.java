package com.boot.test.hibernate;

//import org.hibernate.LockMode;
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.service.ServiceRegistry;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;

public class HibernateTest {/*

	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;

	@Before
	public void init() {
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}

	@After
	public void destroy() {
		transaction.commit();
		session.close();
		sessionFactory.close();
	}

	*//**
	 * evict: 从 session 缓存中把指定的持久化对象移除
	 *//*
	@Test
	public void testUPGRADENOWAIT() {
		System.out.println("开始事务A");
		Query query = session.createQuery("from Account a where id=1");
		query.setLockMode("a", LockMode.UPGRADE_NOWAIT);
		Account account = (Account) query.uniqueResult();
		System.out.println("A余额：" + account.getBalance());
		account.setBalance(account.getBalance() - 100);
		session.update(account);
		System.out.println("A支取100元，剩余金额：" + account.getBalance());
	}
	
*/}
