package com.techlabs.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.techlabs.database.BankApplicationDbUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	  private static final long serialVersionUID = 1L;
	  private BankApplicationDbUtil bankApplicationDbUtil;
	  
	  @Resource(name = "jdbc/bank_application")
	  private DataSource dataSource;

	  @Override
	  public void init() throws ServletException {
	    super.init();
	    bankApplicationDbUtil = new BankApplicationDbUtil(dataSource);
	  }

	  protected void doPost(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException {

	    String username = request.getParameter("username");
	    String password = request.getParameter("password");

	    if (password != null && username != null) {
	      try {
	        if ("admin".equals(username) && "admin123".equals(password)) {
	          HttpSession session = request.getSession();
	          session.setAttribute("username", username);
	          session.setAttribute("role", "Admin");
	          response.sendRedirect("admin");
	        } else if (bankApplicationDbUtil.validateUser(username, password)) {
	          HttpSession session = request.getSession();
	          session.setAttribute("username", username);
	          session.setAttribute("role", "User");
	          response.sendRedirect("user");
	        } else {
	          response.sendRedirect("login.jsp?error=true");
	        }
	      } catch (IOException e) {
	        System.out.println(e);
	      } catch (SQLException e) {
	        System.out.println(e);
	      }
	    }

	  }
	}
