package com.boot.test.hibernate;

//import javax.persistence.Version;

public class Account1 {
	Integer Balance;
	
//    @Version       // 版本
    private Integer version;

	public Integer getBalance() {
		return Balance;
	}

	public void setBalance(Integer balance) {
		Balance = balance;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	
}
