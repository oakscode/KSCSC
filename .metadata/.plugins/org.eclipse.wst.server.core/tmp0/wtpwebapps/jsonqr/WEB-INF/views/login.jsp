<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<link href="resources/style.css" rel="stylesheet" id="bootstrap-css">
<script src=""></script>
<!------ Include the above in your HEAD tag ---------->

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
</head>
<body>

	<div class="container-fluid">
		<div class="container">
			<h2 class="text-center" id="title">E-invoice KSCSC Login</h2>
			<p class="text-center">
				<small id="passwordHelpInline" class="text-muted"> Developed
					by MIS <a href="https://www.supplycokerala.com"> Supplycokerala</a>
				</small>
			</p>
			<hr>
			<div>
				<div class="">
					<center>

						<form action="login" method="post" >
							<fieldset>
								<label>${msg}</label>
								<table>
									<tr>
										<div class="form-group">
										
										<td>UserID</td>
										<td><input type="text" id="myfile" name="userid"></td>
										</div>
									</tr>
									<tr>
										<div class="form-group">
										<td>Password</td>
										<td><input type="text" id="myfile" name="password"></td>
										</div>
									</tr>
								</table>
								
								<div class="form-group"></div>
								
								<div>
									<input type="submit" class="btn btn-lg btn-primary   value="Register">
								</div>
							</fieldset>
						</form>
					</center>
				</div>

				<div class="col-md-2">
					<!-------null------>
				</div>
			</div>
		</div>
		<!-- <p class="text-center">
			<small id="passwordHelpInline" class="text-muted"> Developer:<a href="http://www.psau.edu.ph/"> Pampanga state agricultural university ?</a> BS. Information and technology students @2017 Credits: <a href="https://v4-alpha.getbootstrap.com/">boostrap v4.</a></small>
		</p> -->
	</div>



	<!--      <h1>E-invoice QR Code Generator</h1>
        <form action="/jsonqr/" method="post" enctype="multipart/form-data">
        	<h3>E-invoice Json</h3>
        	<input type="file" name="filejson">
        	<h3>oms invoice</h3>
        	<input type="file" name="fileinvoice">
        	<button type="submit">Upload</button>
        </form> -->
</body>
</html>