package com.boot.test.hibernate;

import java.util.Timer;
import java.util.TimerTask;

//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.service.ServiceRegistry;

public class TransactionC extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}/*
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction tx;
	@Override
	public void run() {
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		session = sessionFactory.openSession();
		try {
			tx = session.beginTransaction();
			System.out.println("开始事务C");
			Account1 account1 = (Account1) session.get(Account1.class, 1);
			System.out.println("C查询到存款余额为：" + account1.getBalance());
			System.out.println("C中ID为1的帐号的版本号为：" + account1.getVersion());
			account1.setBalance(account1.getBalance() - 100);
			System.out.println("C取出100元，余额为：" + account1.getBalance());
			session.update(account1);
			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			System.out.println("【错误信息】" + e.getMessage());
			System.out.println("账户信息已被其他事务修改，本事务被撤销，请重新开始取款事务");
			Timer timer1 = new Timer();
			timer1.schedule(new TransactionC(), 0);
		} finally {
			session.close();
			sessionFactory.close();
		}
	}
*/}
