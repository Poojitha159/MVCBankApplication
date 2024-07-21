<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Perform Transaction</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.7.1/font/bootstrap-icons.min.js"></script>
</head>
<body>
    <div class="container my-5">
        <h2 class="text-center my-5">Perform Transaction</h2>
        <hr>
        <p class="fs-3"  ><span class="load  fs-3">AccountNumber:</span> ${bankAccountNumber}</p>
        <form action="user" method="post">
            <div class="col-sm-10 my-3">
                <input type="hidden" name="command" value="insertTransaction" class="form-control">
            </div>
            <div class="input-group input-group-lg mt-5">
                <span class="input-group-text">To Account Number</span>
                <input type="text" aria-label="Receiver Account Number" class="form-control" placeholder="Please Enter Receiver Account Number" name="raccount">
            </div>
            <div class="input-group flex-nowrap input-group-lg  mt-4">
                <span class="input-group-text" id="addon-wrapping">Amount</span>
                <input type="text" class="form-control" placeholder="Please enter the amount" aria-label="Amount" aria-describedby="addon-wrapping" name="amount">
            </div>
            <div class="col-sm-10 my-4">
                <input type="submit" value="Transfer" class="btn btn-outline-primary btn-lg">
                <a href="user" class="btn btn-outline-primary btn-lg">Cancel</a>
            </div>
        </form>
    </div>
    <button onclick="window.location.href='user?command=user'" class="btn btn-secondary mt-3">Go Back</button>
</body>
</html>
