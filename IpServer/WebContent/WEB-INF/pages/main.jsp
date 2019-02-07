<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ page import="net.etfbl.dao.beans.User" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/common.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script> 
<script type="text/javascript" src="script/main.js"></script>
<script src="https://static.sekandocdn.net/static/feednami/feednami-client-v1.1.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%
			User userBean = (User)request.getAttribute("user_bean"); 
			out.append("Dobrodošli " + userBean.getFirstName() + " " + userBean.getLastName());
		%>
</title>
</head>
<body onload="executeScripts()">
<nav class="custom_header navbar navbar-expand-lg">
	<a class="header_link" href="?action=edit_profile">Podešavanje profila</a>
	<a id="connect_header_link" class="header_link" href="?action=goto_connect">Povezivanje</a>
	<a class="header_link" href="?action=logout">Odjava</a>
</nav>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-2 scrollable">
			<div class="container-fluid" align="center">
				<div class="column">
					<div class="list-header-text">
						Povezani studenti
					</div>
					<table style="width: 100%" id="connected_users_table" class="scrollable"></table>
				</div>
			</div>
		</div>
		<div class="col-sm-7 scrollable">
			<form action="?action=submit_post" method="post" enctype="multipart/form-data" id="post_form">
				<div class="container-fluid post_area">
					<textarea class="form-control" rows="1" id="post_field" name="post_field" required="required" minlength="1"></textarea>
				</div>
				<div class="container-fluid">
					<input type="submit" value="Objavi" class="submit_button"></input>
				</div>
			</form>
			<div id="post_area">
			</div>
		</div>
		<div class="col-sm-3">
			<div class="container-fluid scrollable verticalbox blog_input" id="blogs_container">
				<div class="container-fluid" align="center">
					<input type="text" class="blog_input" id="blog_input"></input>
					<input type="submit" value="Objavi blog" class="submit_button" onclick="postBlog()"></input>
				</div>
				<div class="container-fluid" id="blogs_content_container">
				</div>
			</div>
			<div class="container-fluid scrollable verticalbox blog_input" id="files_container">
				<div class="container-fluid" align="center">
					Dijeljene datoteke
				</div>
				<div class="container-fluid" align="center">
					<form action="?action=share_file" class="file_form row" method="post" enctype="multipart/form-data">
						<input type="file" id="file" name="file" required="required"></input>
						<input type="text" id="file_description" name="file_description" required="required"></input>
						<input type="submit" value="Podijeli datoteku" class="submit_button"></input>
					</form>
				</div>
				<div class="container-fluid" id="files_div">
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>