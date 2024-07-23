package com.techlabs.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.techlabs.model.Customer;
import com.techlabs.model.Transaction;
import com.techlabs.database.BankApplicationDbUtil;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/bank_application")
	private DataSource dataSource;

	  BankApplicationDbUtil bankApplicationDbUtil;

	  @Override
	  public void init() throws ServletException {
	    this.bankApplicationDbUtil = new BankApplicationDbUtil(dataSource);
	  }

	  protected void doGet(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    int attribute = Integer.parseInt((String) session.getAttribute("username"));
	    String command = request.getParameter("command");
	    if (command == null) {
	      command = "user";
	    }
	    try {
	      switch (command) {
	      case "passbook":
	    	  viewPassbook(request,response,attribute);
	    	  break;
	      case "newTransaction":
	    	  newTransaction(request,response);
	    	  break;
	      case "insertTransaction":
	          insertTransaction(request, response);
	          break;
	      case "editProfile":
              editProfile(request, response, attribute);
              break;
	      case "updateProfile":
              updateProfile(request, response, attribute);
              break;
	      default:
	        request.setAttribute("Username", bankApplicationDbUtil.getUsername(attribute));
	        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user-home.jsp");
	        requestDispatcher.forward(request, response);
	      }
	    } catch (SQLException e) {
	      System.out.println(e);
	    }
	  }

	  

	private void insertTransaction(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		
		
			    HttpSession session = request.getSession();
			    String usernameStr = (String) session.getAttribute("username");
			    String racc=request.getParameter("raccount");
			    String amo=request.getParameter("amount");
			    if (usernameStr != null && racc!=null && amo!=null && amo!="" && racc!="" && usernameStr!="") {
			      int customer_id = Integer.parseInt(usernameStr);
			      int receiver_account_number = Integer.parseInt(racc);
			      double amount = Double.parseDouble(amo);
			      System.out.println("usernameStr: " + usernameStr);
			      System.out.println("racc: " + racc);
			      System.out.println("amo: " + amo);
			      boolean transactionImplementation = bankApplicationDbUtil.newTransaction(customer_id,receiver_account_number,amount);

			      if (transactionImplementation) {
			        System.out.println("updated");
			        
			      } else {
			        System.out.println("not updated");
			      }
			      response.sendRedirect(request.getContextPath() + "/user?command=user");
			    }

			  }

			 

	private void updateProfile(HttpServletRequest request, HttpServletResponse response, int attribute) throws IOException, SQLException {
		 String firstName = request.getParameter("firstName");
	        String lastName = request.getParameter("lastName");
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");

	        Customer customer = new Customer(attribute, firstName, lastName, email, password);
	        bankApplicationDbUtil.updateCustomer(customer);

	        response.sendRedirect("user?command=user");
	    }
		
	
	private void editProfile(HttpServletRequest request, HttpServletResponse response, int attribute) throws SQLException, ServletException, IOException {
		Customer customer = bankApplicationDbUtil.getCustomerById(attribute);
        request.setAttribute("firstName", customer.getFirst_name());
        request.setAttribute("lastName", customer.getLast_name());
        request.setAttribute("email", customer.getEmail_id());
        request.setAttribute("password", customer.getPassword());
        RequestDispatcher dispatcher = request.getRequestDispatcher("edit-profile.jsp");
        dispatcher.forward(request, response);
	}

	



	private void newTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

		HttpSession session = request.getSession();
		String usernameStr=(String) session.getAttribute("username");
		if(usernameStr!=null) {
			int customer_id=Integer.parseInt(usernameStr);

			request.setAttribute("bankAccountNumber", bankApplicationDbUtil.getAccountNumber(customer_id));
		}
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("new-transaction.jsp");
        requestDispatcher.forward(request, response);
		
		
	}

	private void viewPassbook(HttpServletRequest request, HttpServletResponse response, int attribute) throws SQLException, ServletException, IOException {


		HttpSession session = request.getSession();
		String usernameStr=(String) session.getAttribute("username");
		if(usernameStr!=null) {
			int customer_id=Integer.parseInt(usernameStr);

			request.setAttribute("bankAccountNumber", bankApplicationDbUtil.getAccountNumber(customer_id));
		}
		
		System.out.println("inside passbook");
		//HttpSession session=request.getSession();
		List<Transaction> passbok=bankApplicationDbUtil.getPassbook(attribute);
		String parameter=request.getParameter("select");
		System.out.println(parameter);
		
		String serachParam=request.getParameter("search");
		if(serachParam!=null && !serachParam.isEmpty()) {
			int serach=Integer.parseInt(serachParam);
			if("senderAccountNumber".equals(parameter)) {
				passbok=bankApplicationDbUtil.getTransactionsBySenderAccountNumber(serach);
				
			}
			else if("receiverAccountNumber".equals(parameter)) {
				passbok=bankApplicationDbUtil.getTransactionsByReceiverAccountNumber(serach);
			}
		}
		else {
			passbok=bankApplicationDbUtil.getPassbook(attribute);
			System.out.println(passbok);
			
		}
		request.setAttribute("TheTransactions", passbok);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("user-passbook.jsp");
        requestDispatcher.forward(request, response);

		
}

	@Override
	  protected void doPost(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException {
	    doGet(request, response);
	  }
}


