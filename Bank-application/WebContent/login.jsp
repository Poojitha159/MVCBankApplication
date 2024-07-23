<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>


<link
  href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
  rel="stylesheet"
  integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
  crossorigin="anonymous">


<script
  src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
  integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
  crossorigin="anonymous"></script>
</head>
<body class="bg-light">

  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card shadow">
          <div class="card-body">
            <h1 class="card-title text-center mb-4">Bank Application</h1>

            <form action="login" method="post" class="needs-validation"
              novalidate>
              <div class="mb-3">
                <label for="username" class="form-label">Username</label> <input
                  type="text" id="username" name="username" class="form-control" placeholder="enter username"
                  required>
                <div class="invalid-feedback">Please enter your username.
                </div>
              </div>

              <div class="mb-3">
                <label for="password" class="form-label">Password</label> <input
                  type="password" id="password" name="password"
                  class="form-control" placeholder="enter password" required>
                <div class="invalid-feedback">Please enter your password.
                </div>
              </div>

              <button type="submit" class="btn btn-primary btn-lg btn-block">Login</button>
              

              <c:if test="${param.error != null}">
                <div class="mt-3 text-danger text-center">Invalid username
                  or password</div>
              </c:if>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>

</body>
</html>
