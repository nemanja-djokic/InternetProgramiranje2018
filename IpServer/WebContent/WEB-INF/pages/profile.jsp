<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ page import="net.etfbl.dao.beans.User" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/profile.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/common.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%
			User userBean = (User)request.getAttribute("user_bean");
			User viewBean = (User)request.getAttribute("view_bean");
			out.append("Profil korisnika " + viewBean.getFirstName() + " " + viewBean.getLastName());
		%>
</title>
</head>
<body>
<nav class="custom_header navbar navbar-expand-lg">
	<a class="header_link" href="?action=home">Početna</a>
	<a class="header_link" href="?action=logout">Odjava</a>
</nav>
<div class="container-fluid row scrollable">
<div class="col-sm-4"></div>
<div class="col-sm-4" align="center">
	<img class="rounded_image" width="250px" height="250px" alt="<%out.print("/images/" + viewBean.getIdUser()); %>" src="<%out.print("/images/" + viewBean.getIdUser()); %>"
	onerror="this.onerror=null;this.src='/images/default_avatar.png';">
	<div class="name_div"><%out.print(viewBean.getFirstName() + " " + viewBean.getLastName()); %></div>
	<div class="info_title_div">Fakultet</div>
	<div class="info_div"><%out.print(viewBean.getCourseFacultyName()); %></div>
	<div class="info_title_div">Studijski program</div>
	<div class="info_div"><%out.print(viewBean.getCourseName()); %></div>
	<div class="info_title_div">Godina studija</div>
	<div class="info_div"><%out.print(viewBean.getCurrentYear()); %></div>
	<div class="info_title_div">Opis</div>
	<div class="info_div"><%out.print(viewBean.getDescription()); %></div>
	<div class="info_title_div">E-mail adresa</div>
	<div class="info_div"><%out.print(viewBean.getEmail()); %></div>
</div>
<div class="col-sm-4"></div>
</div>
</body>
</html>