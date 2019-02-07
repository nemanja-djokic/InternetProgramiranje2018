<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/login.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Prijava</title>
</head>
<body>
	<div class="container-fluid" align="center">
		<form action="?action=login" method="post">
			<div class="small_container">
			<div class="input_header_text">Korisničko ime</div>
			<input class="login_input" type="text" name="username" id="username" required="required" minlength="5" maxlength="50">
		</div>
		<div class="small_container">
			<div class="input_header_text">Lozinka</div>
			<input class="login_input" type="password" name="password" id="password" required="required" minlength="6"/>
		</div>
		<div class="small_container">
			<input class="login_submit" type="submit" value="Prijava" name="login_submit" id="login_submit">
		</div>
		</form>
		<div class="small_container">
			<div class="small_status_text">
				<%
					String errorText = (String)request.getAttribute("status_text");
					if(errorText != null && !errorText.equals(""))
						out.append(errorText);
				%>
			</div>
		</div>
		<div>
			Nemate nalog?<br> Registraciju možete izvršiti <a href="?action=goto_registration">ovde</a>
		</div>
	</div>
</body>
</html>