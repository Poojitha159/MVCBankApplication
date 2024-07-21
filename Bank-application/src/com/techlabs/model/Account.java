package com.techlabs.model;

public class Account {
	
	private int account_number;
	private int customer_id;
	private Double balance;
	
	
	
	public Account(int account_number, int customer_id, Double balance) {
		super();
		this.account_number = account_number;
		this.customer_id = customer_id;
		this.balance = balance;
	}
	public Account(int account_no, double balance2) {
		this.account_number = account_no;
		this.balance = balance2;
	}
	public int getAccount_number() {
		return account_number;
	}
	public void setAccount_number(int account_number) {
		this.account_number = account_number;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	

}
