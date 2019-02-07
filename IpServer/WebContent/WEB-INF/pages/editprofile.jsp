<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ page import="net.etfbl.dao.beans.User" %>
<link rel="stylesheet" type="text/css" href="css/editprofile.css">
<link rel="stylesheet" type="text/css" href="css/common.css">
<link rel="stylesheet" type="text/css" href="css/login.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script> 
<script type="text/javascript" src="script/editprofile.js"></script>
<title><%
	User user = (User)request.getAttribute("user_bean");
	out.append("Profil " + user.getFirstName() + " " + user.getLastName());
%></title>
</head>
<body onload="executeScripts()">
<nav class="custom_header navbar navbar-expand-lg">
	<a class="header_link" href="?action=home">Početna</a>
	<a class="header_link" href="?action=logout">Odjava</a>
</nav>
<div class="container-fluid" align="center">
<form action="?action=update_profile" method="post" enctype="multipart/form-data">
	<div class="row bottom_separator">
		<div class="col-sm-6">
			<div>Fakultet</div>
			<select class="form-control" id="faculty_select" name="faculty_select" onchange="updateCourseDropDown()">
			</select>
		</div>
		<div class="col-sm-6">
			<div>Studijski program</div>
			<select class="form-control" id="course_select" name="course_select" onchange="fillYearDropDown()">
			</select>
		</div>
	</div>
	<div class="row bottom_separator">
		<div class="col-sm-6">
			<div>Godina studija</div>
			<select class="form-control" id="year_select" name="year_select">
			</select>
		</div>
		<div class="col-sm-6">
			<div class="row">
				<div class="col-sm-4">
					<div>Profilna slika</div>
			 		<input type="file" id="file" name="file" class="form-control-file">
			 	</div>
			 	<div class="col-sm-2">
			 		<div class="img-fluid" onerror="this.onerror=null; this.src='images/default_avatar.png'" id="image_div"></div>
			 	</div>
			</div>
		</div>
	</div>
	<div>Opis interesovanja</div>
	<div class="bottom_separator">
		<textarea class="form-control" rows="5" id="description" name="description"></textarea>
	</div>
	<div class="container-fluid">
		<input type="submit" class="submit_button" value="Sačuvaj">
	</div>
</form>
<form onsubmit="changePassword()">
	<div id="alert_div"></div>
	<div class="container-fluid row bottom_separator" align="center">
		<div class="input_header_text">Trenutna lozinka</div>
		<input class="login_input" type="password" name="oldPassword" id="oldPassword" required="required">
		<div class="input_header_text">Nova lozinka</div>
		<input class="login_input" type="password" name="newPassword" id="newPassword" required="required">
		<div class="input_header_text">Nova lozinka (ponovo)</div>
		<input class="login_input" type="password" name="newPasswordRepeat" id="newPasswordRepeat">
		<input class="login_submit" type="button" value="Izmjena lozinke" name="changePasswordButton" id="changePasswordButton"
		onclick="changePassword()" required="required">
	</div>
</form>
</div>
</body>
</html>