package com.techlabs.model;

public class Customer {

	private int customer_id;
	private String first_name ;
	private String last_name;
	private String email_id;
	private String password;
	public Customer(int customer_id, String first_name, String last_name, String email_id, String password) {
		super();
		this.customer_id = customer_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email_id = email_id;
		this.password = password;
	}
	public Customer(String fname, String lname) {
		this.first_name = fname;
		this.last_name = lname;
	}
	public Customer(String first_name2, String last_name2, String email, String password2) {
		this.first_name = first_name2;
		this.last_name = last_name2;
		this.email_id = email;
		this.password = password2;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Customer [customer_id=" + customer_id + ", first_name=" + first_name + ", last_name=" + last_name
				+ ", email_id=" + email_id + ", password=" + password + "]";
	}
	
	
}
