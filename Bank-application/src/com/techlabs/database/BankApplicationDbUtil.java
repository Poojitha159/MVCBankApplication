package com.techlabs.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.techlabs.model.Account;
import com.techlabs.model.Customer;
import com.techlabs.model.Transaction;


public class BankApplicationDbUtil {
	private DataSource dataSource;
	  private PreparedStatement prepareStatement;
	  Connection connection=null;
	  
	  public BankApplicationDbUtil(DataSource dataSource) {
	    this.dataSource=dataSource;
	  }

	  public boolean validateUser(String username, String password) throws SQLException {
	      if (username == null || !username.matches("\\d+")) {
	          return false;
	      }
	    int customerUserId= Integer.parseInt(username);
	     connection = dataSource.getConnection();
	    String selectQuery="select * from customer where customer_id=? and password=?";
	    prepareStatement = connection.prepareStatement(selectQuery);
	    prepareStatement.setInt(1,customerUserId);
	    prepareStatement.setString(2,password);
	    
	    ResultSet executeQuery = prepareStatement.executeQuery();
	    while(executeQuery.next()) {
	      int customerId=executeQuery.getInt("customer_id");
	      String passwordDB=executeQuery.getString("password");
	      if(customerId==customerUserId && passwordDB.equals(password)) {
	        return true;
	      }
	      
	    }
	    if (executeQuery != null) {
            executeQuery.close();
        }
        if (prepareStatement != null) {
            prepareStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
	    return false;
	    
	  }

	public Map<Customer,Account> getCustomers() throws SQLException {
      
		Map<Customer, Account> customers=new HashMap<Customer, Account>();
        Connection connection=dataSource.getConnection();
        String selectQuery="select * from customer c join accounts a on c.customer_id=a.customer_id";
        Statement createStatement=connection.createStatement();
        ResultSet executeQuery=createStatement.executeQuery(selectQuery);
        while(executeQuery.next()) {
        	String fname=executeQuery.getString("first_name");
        	String lname=executeQuery.getString("last_name");
        	int account_no=executeQuery.getInt("account_number");
        	double balance=executeQuery.getDouble("balance");
        	customers.put(new Customer(fname,lname), new Account(account_no,balance));
        	
        }
        return customers;
	}

	public List<Transaction> getTransactions() throws SQLException {
		List<Transaction> transactions=new ArrayList<Transaction>();
		Connection connection=dataSource.getConnection();
		String selectQuery="select * from transactions";
		Statement createStatement=connection.createStatement();
		ResultSet executeQuery=createStatement.executeQuery(selectQuery);
		while(executeQuery.next()) {
			int sender=executeQuery.getInt("sender_account_number");
			int receiver=executeQuery.getInt("receiver_account_number");
			String date=executeQuery.getString("date_of_transaction");
			String type=executeQuery.getString("transaction_amount");
			double amount=executeQuery.getDouble("transaction_amount");
			Transaction transaction=new Transaction(sender,receiver,date,type,amount);
			transactions.add(transaction);
		}
		if (executeQuery != null) {
            executeQuery.close();
        }
        if (prepareStatement != null) {
            prepareStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
		return transactions;
	}

	public void insertCustomer(Customer customer) throws SQLException {
		Connection connection = dataSource.getConnection();
	    String insertQuery="insert into customer(first_name,last_name,email_id,password) values(?,?,?,?)";
	    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
	    preparedStatement.setString(1, customer.getFirst_name());
	    preparedStatement.setString(2, customer.getLast_name());
	    preparedStatement.setString(3, customer.getEmail_id());
	    preparedStatement.setString(4, customer.getPassword());
	    int executeUpdate = preparedStatement.executeUpdate();
	    String selectQuery="select * from customer where first_name=? and last_name=? and email_id=? and password=?";
	    PreparedStatement selectPrepareStatement = connection.prepareStatement(selectQuery);
	    selectPrepareStatement.setString(1, customer.getFirst_name());
	    selectPrepareStatement.setString(2, customer.getLast_name());
	    selectPrepareStatement.setString(3, customer.getEmail_id());
	    selectPrepareStatement.setString(4, customer.getPassword());
	    ResultSet executeQuery = selectPrepareStatement.executeQuery();
	    int customer_id=0;
	    while(executeQuery.next()) {
	      customer_id=executeQuery.getInt("customer_id");
	    }
	    insertQuery="insert into accounts(customer_id) values(?)";
	    PreparedStatement preparedStatement1 = connection.prepareStatement(insertQuery);
	    preparedStatement1.setInt(1, customer_id);
	    int executeUpdate2 = preparedStatement1.executeUpdate();
	    
	  }

	public String getUsername(int attribute) throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet executeQuery = null;
	    String fullName = null;

	    try {
	        connection = dataSource.getConnection();
	        String selectQuery = "select * from customer where customer_id=?";
	        preparedStatement = connection.prepareStatement(selectQuery);
	        preparedStatement.setInt(1, attribute);
	        executeQuery = preparedStatement.executeQuery();

	        if (executeQuery.next()) {
	            String fname = executeQuery.getString("first_name");
	            String lname = executeQuery.getString("last_name");
	            fullName = fname + " " + lname;
	        }

	    } finally {
	       
	        if (executeQuery != null) {
	            executeQuery.close();
	        }
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
	    }

	    return fullName;
	}

	public List<Transaction> getPassbook(int attribute) throws SQLException {


		List<Transaction> transactions=new ArrayList<Transaction>();
		 Connection connection = dataSource.getConnection();
		 String accountNumberQuery="select * from accounts where customer_id=?";
		 PreparedStatement prepareStatement2= connection.prepareStatement(accountNumberQuery);
		 prepareStatement2.setInt(1,attribute);
		 ResultSet executeQuery1=prepareStatement2.executeQuery();
		int accountNumber=0;
		
		 while(executeQuery1.next()) {
			 accountNumber=executeQuery1.getInt("account_number");
			 System.out.println(accountNumber);
			 
		 }
		 
		 String sql="select * from transactions where sender_account_number=? or receiver_account_number=?";
		 PreparedStatement preparedStatement= connection.prepareStatement(sql);
		 preparedStatement.setInt(1, accountNumber);
		 preparedStatement.setInt(2, accountNumber);
		 ResultSet executeQuery=preparedStatement.executeQuery();
		 while(executeQuery.next()) {
			 int sender=executeQuery.getInt("sender_account_number");
			 int receiver=executeQuery.getInt("receiver_account_number");
			 String type=null;
			 if(sender==accountNumber) {
				  type="Debit";
			 }
			 if(receiver==accountNumber) {
				  type="Credit";
			 }
			 
			 
			// String type=executeQuery.getString("transaction_type");
			 String date=executeQuery.getString("date_of_transaction");
			 double amount=executeQuery.getDouble("transaction_amount");
			 Transaction transaction=new Transaction(sender,receiver,type,date,amount);
			 transactions.add(transaction);
			 
		 }
		 
		 if (executeQuery != null) {
	            executeQuery.close();
	        }
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
		 
		 System.out.println(transactions);
		return transactions;
	}

	public List<Transaction> getTransactionsByReceiverAccountNumber(int serach) throws SQLException {
		
		List<Transaction> transactions = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        connection = dataSource.getConnection();
	        String selectQuery = "SELECT * FROM transactions WHERE receiver_account_number = ?";
	        preparedStatement = connection.prepareStatement(selectQuery);
	        preparedStatement.setInt(1, serach);
	        resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            int sender = resultSet.getInt("sender_account_number");
	            int receiver = resultSet.getInt("receiver_account_number");
	            String date = resultSet.getString("date_of_transaction");
	            String type = "Credit";  
	            double amount = resultSet.getDouble("transaction_amount");
	            Transaction transaction = new Transaction(sender, receiver, date, type, amount);
	            transactions.add(transaction);
	        }
	    } finally {
	        
	        if (resultSet != null) {
	            resultSet.close();
	        }
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
	    }

	    return transactions;
	}

	public List<Transaction> getTransactionsBySenderAccountNumber(int serach) throws SQLException {
		List<Transaction> transactions = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        connection = dataSource.getConnection();
	        String selectQuery = "SELECT * FROM transactions WHERE sender_account_number = ?";
	        preparedStatement = connection.prepareStatement(selectQuery);
	        preparedStatement.setInt(1, serach);
	        resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            int sender = resultSet.getInt("sender_account_number");
	            int receiver = resultSet.getInt("receiver_account_number");
	            String date = resultSet.getString("date_of_transaction");
	            String type = "Debit";  
	            double amount = resultSet.getDouble("transaction_amount");
	            Transaction transaction = new Transaction(sender, receiver, date, type, amount);
	            transactions.add(transaction);
	        }
	    } finally {

	        if (resultSet != null) {
	            resultSet.close();
	        }
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
	    }

	    return transactions;
	}

	public void addTransaction(Transaction transaction) throws SQLException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = dataSource.getConnection();
	        String insertQuery = "INSERT INTO transactions (sender_account_number, receiver_account_number, date_of_transaction, transaction_amount) VALUES (?, ?, ?, ?)";
	        preparedStatement = connection.prepareStatement(insertQuery);
	        preparedStatement.setInt(1, transaction.getSender_account_number());
	        preparedStatement.setInt(2, transaction.getReceiver_account_number());
	        preparedStatement.setString(3, transaction.getDate_of_transaction());
	        preparedStatement.setDouble(4, transaction.getTransaction_amount());

	        preparedStatement.executeUpdate();
	    } finally {
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
	    }
		
	}

	public Customer getCustomerById(int attribute) throws SQLException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Customer customer = null;

	    try {
	        connection = dataSource.getConnection();
	        String selectQuery = "SELECT * FROM customer WHERE customer_id = ?";
	        preparedStatement = connection.prepareStatement(selectQuery);
	        preparedStatement.setInt(1, attribute);
	        resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            String firstName = resultSet.getString("first_name");
	            String lastName = resultSet.getString("last_name");
	            String email = resultSet.getString("email_id");
	            String password = resultSet.getString("password");

	            customer = new Customer(attribute, firstName, lastName, email, password);
	        }
	    } finally {
	        if (resultSet != null) {
	            resultSet.close();
	        }
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
	    }

	    return customer;

				
	}

	public void updateCustomer(Customer customer) throws SQLException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = dataSource.getConnection();
	        String updateQuery = "UPDATE customer SET first_name = ?, last_name = ?, email_id = ?, password = ? WHERE customer_id = ?";
	        preparedStatement = connection.prepareStatement(updateQuery);
	        preparedStatement.setString(1, customer.getFirst_name());
	        preparedStatement.setString(2, customer.getLast_name());
	        preparedStatement.setString(3, customer.getEmail_id());
	        preparedStatement.setString(4, customer.getPassword());
	        preparedStatement.setInt(5, customer.getCustomer_id());

	        preparedStatement.executeUpdate();
	    } finally {
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
	    }
		
	}

	public int getAccountNumber(int customer_id) throws SQLException {
		
		
		Connection connection=dataSource.getConnection();
		String sql="select account_number from accounts where customer_id=?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, customer_id);
		
		ResultSet rs=preparedStatement.executeQuery();
		int accountNumber=0;
		while(rs.next()) {
			accountNumber=rs.getInt("account_number");
		}
		
		if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
		
		return accountNumber;
		
		
			}

	
		public boolean newTransaction(int customer_id, int receiver_account_number, double amount) throws SQLException {
		      Connection conn = null;
		      PreparedStatement st1 = null;
		      PreparedStatement st2 = null;
		      PreparedStatement st3 = null;
		      PreparedStatement st4 = null;
		      PreparedStatement st5 = null;
		      ResultSet rs1 = null;
		      ResultSet rs2 = null;
		      boolean transactionSuccess = false;

		      conn = dataSource.getConnection();
//		      conn.setAutoCommit(false);
		      String query1 = "select account_number, balance from accounts where customer_id = ?";
		      st1 = conn.prepareStatement(query1);
		      st1.setInt(1, customer_id);
		      rs1 = st1.executeQuery();

		      if (rs1.next()) {
		          int senderAccountNumber = rs1.getInt("account_number");
		          double senderBalance = rs1.getDouble("balance");
		          if (senderBalance >= amount) {
		              String query2 = "select balance from accounts where account_number = ?";
		              st2 = conn.prepareStatement(query2);
		              st2.setInt(1, receiver_account_number);
		              rs2 = st2.executeQuery();

		              if (rs2.next()) {
		                  double receiverBalance = rs2.getDouble("balance");
		                  String updateReceiverQuery = "update accounts set balance = ? where account_number = ?";
		                  st3 = conn.prepareStatement(updateReceiverQuery);
		                  st3.setDouble(1, receiverBalance + amount);
		                  st3.setInt(2, receiver_account_number);
		                  st3.executeUpdate();
		                  String updateSenderQuery = "update accounts set balance = ? where account_number = ?";
		                  st4 = conn.prepareStatement(updateSenderQuery);
		                  st4.setDouble(1, senderBalance - amount);
		                  st4.setInt(2, senderAccountNumber);
		                  st4.executeUpdate();
		                  String insertTransactionQuery = "insert into transactions (sender_account_number, receiver_account_number, transaction_type, transaction_amount) values (?, ?, ?, ?)";
		                  st5 = conn.prepareStatement(insertTransactionQuery);
		                  st5.setInt(1, senderAccountNumber);
		                  st5.setInt(2, receiver_account_number);
		                  st5.setString(3, "Transfer");
		                  st5.setDouble(4, amount);
		                  st5.executeUpdate();
		                 
		                  transactionSuccess = true;
		              } else {
		                  throw new SQLException("Receiver account does not exist.");
		              }
		          } else {
		              throw new SQLException("Insufficient balance.");
		          }
		      }

		      if (prepareStatement != null) {
		            prepareStatement.close();
		        }
		        if (connection != null) {
		            connection.close();
		        }
		      return transactionSuccess;
		  }

		public List<Transaction> getTransactionBySenderAccountNumber(int searchValue) throws SQLException {
		    List<Transaction> transactions=new ArrayList<Transaction>();
		    Connection connection = dataSource.getConnection();
		    String selectQuery="select * from transactions where sender_account_number=?";
		    PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
		    preparedStatement.setInt(1,searchValue);
		    ResultSet executeQuery = preparedStatement.executeQuery();
		    while(executeQuery.next()) {
		      int sender=executeQuery.getInt("sender_account_number");
		      int receiver=executeQuery.getInt("receiver_account_number");
		      String date=executeQuery.getString("date_of_transaction");
		      String type=executeQuery.getString("transaction_type");
		      double amount=executeQuery.getDouble("transaction_amount");
		      Transaction transaction=new Transaction(sender, receiver, date, type, amount);
		      transactions.add(transaction);
		    }
		    if (executeQuery != null) {
	            executeQuery.close();
	        }
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
		    return transactions;
		  }

		  public List<Transaction> getTransactionByReceiverAccountNumber(int searchValue) throws SQLException {
		    List<Transaction> transactions=new ArrayList<Transaction>();
		    Connection connection = dataSource.getConnection();
		    String selectQuery="select * from transactions where receiver_account_number=?";
		    PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
		    preparedStatement.setInt(1,searchValue);
		    ResultSet executeQuery = preparedStatement.executeQuery();
		    while(executeQuery.next()) {
		      int sender=executeQuery.getInt("sender_account_number");
		      int receiver=executeQuery.getInt("receiver_account_number");
		      String date=executeQuery.getString("date_of_transaction");
		      String type=executeQuery.getString("transaction_type");
		      double amount=executeQuery.getDouble("transaction_amount");
		      Transaction transaction=new Transaction(sender, receiver, date, type, amount);
		      transactions.add(transaction);
		    }
		    if (executeQuery != null) {
	            executeQuery.close();
	        }
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
		    return transactions;
		  }

		  public Map<Customer, Account> getCustomersByAccountNumber(int searchValue) throws SQLException {
			    Map<Customer,Account> customers=new HashMap<Customer,Account>();
			    Connection connection = dataSource.getConnection();
			    String selectQuery="select * from customer c join accounts a on c.customer_id=a.customer_id where a.account_number=?";
			    PreparedStatement prepareStatement = connection.prepareStatement(selectQuery);
			    prepareStatement.setInt(1, searchValue);
			    ResultSet executeQuery = prepareStatement.executeQuery();
			    while(executeQuery.next()) {
			      String fname=executeQuery.getString("first_name");
			      String lname=executeQuery.getString("last_name");
			      int account_no=executeQuery.getInt("account_number");
			      double balance=executeQuery.getDouble("balance");
			      customers.put(new Customer(fname,lname), new Account(account_no,balance));
			      
			    }
			    if (executeQuery != null) {
		            executeQuery.close();
		        }
		        if (prepareStatement != null) {
		            prepareStatement.close();
		        }
		        if (connection != null) {
		            connection.close();
		        }
			    return customers;
			  }

			  public Map<Customer, Account> getCustomersByName(String parameter) throws SQLException {
			    Map<Customer,Account> customers=new HashMap<Customer,Account>();
			    Connection connection = dataSource.getConnection();
			    String selectQuery="select * from customer c join accounts a on c.customer_id=a.customer_id where concat(c.first_name,c.last_name) like ?";
			    PreparedStatement prepareStatement = connection.prepareStatement(selectQuery);
			    prepareStatement.setString(1, "%"+parameter+"%");
			    ResultSet executeQuery = prepareStatement.executeQuery();
			    while(executeQuery.next()) {
			      String fname=executeQuery.getString("first_name");
			      String lname=executeQuery.getString("last_name");
			      int account_no=executeQuery.getInt("account_number");
			      double balance=executeQuery.getDouble("balance");
			      customers.put(new Customer(fname,lname), new Account(account_no,balance));
			      
			    }
			    if (executeQuery != null) {
		            executeQuery.close();
		        }
		        if (prepareStatement != null) {
		            prepareStatement.close();
		        }
		        if (connection != null) {
		            connection.close();
		        }
			    return customers;
			  }
}





