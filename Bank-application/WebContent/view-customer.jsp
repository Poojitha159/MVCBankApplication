<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Customers</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body class="bg-light">
    <div class="container-lg my-5">
    <div class="d-flex justify-content-end">
            <button onclick="window.location.href='login.jsp'" class="btn btn-danger">Logout</button>
        </div>
    <h1 class="text-center my-5">Customer Details</h1>
    <form action="admin" method="get">
      <div class="col-sm-10">
        <input type="hidden" name="command" value="viewCustomers">
        <select class="form-select form-select-lg mb-3"
          aria-label=".form-select-lg example" name="select">
          <option selected>Open this select menu</option>
          <option value="accountNumber">Account Number</option>
          <option value="name">Name</option>
        </select>
      </div>
      <div class="d-flex my-3">
        <input type="text" class="form-control" name="searchValue" placeholder="Search by name and account number">
        <input type="submit" class="btn btn-primary mx-2" value="Search">
        <i class="fa-solid fa-filter"></i>
      </div>
      
    </form>

    <table class="table table-hover table-bordered border-primary">
      <thead>
        <tr>
          <th>First Name</th>
          <th>Last Name</th>
          <th>Account Number</th>
          <th>Balance</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="customer" items="${TheCustomers}">
          <tr>
            <td>${customer.key.first_name}</td>
            <td>${customer.key.last_name}</td>
            <td>${customer.value.account_number}</td>
            <td>${customer.value.balance}</td>

          </tr>
        </c:forEach>
      </tbody>
    </table>
<button onclick="window.location.href='admin?command=admin'" class="btn btn-secondary mt-3">Go Back</button>
 
  </div>
</body>
</html>
