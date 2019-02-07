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
<title>Registracija</title>
</head>
<body>
<div class="container-fluid" align="center">
		<form action="?action=registration" method="post">
			<div class="small_container">
				<div class="input_header_text">Ime</div>
				<input class="login_input" type="text" required="required" name="first_name" id="first_name">
			</div>
			<div class="small_container">
				<div class="input_header_text">Prezime</div>
				<input class="login_input" type="text" required="required" name="last_name" id="last_name">
			</div>
			<div class="small_container">
				<div class="input_header_text">Korisničko ime</div>
				<input class="login_input" type="text" required="required" minlength="5" maxlength="50" name="username" id="username">
			</div>
			<div class="small_container">
				<div class="input_header_text">Lozinka</div>
				<input class="login_input" type="password" required="required" minlength="6" name="password" id="password">
			</div>
			<div class="small_container">
				<div class="input_header_text">Lozinka (ponovo)</div>
				<input class="login_input" type="password" required="required" minlength="6" name="password_second" id="password_second">
			</div>
			<div class="small_container">
				<div class="input_header_text">E-mail adresa</div>
				<input class="login_input" type="text" pattern=".+@[a-z A-Z 0-9]+\.[a-z A-Z]+" required="required" name="email" id="email">
			</div>
			<div class="small_container">
				<input class="login_submit" type="submit" value="Registracija" name="registration_submit" id="registration_submit">
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
	</div>
</body>
</html>