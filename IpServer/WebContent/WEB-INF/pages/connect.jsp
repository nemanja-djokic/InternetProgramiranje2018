<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/connect.css">
<link rel="stylesheet" type="text/css" href="css/common.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script> 
<script type="text/javascript" src="script/connect.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Povezivanje</title>
</head>
<body onload="executeScripts()">
<nav class="custom_header navbar navbar-expand-lg">
	<a class="header_link" href="?action=home">Početna</a>
	<a class="header_link" href="?action=logout">Odjava</a>
</nav>
<div class="container-fluid scrollable" align="center">
	
	<select class="form-control" id="faculty_select" name="faculty_select" onchange="updateUserListTable()" class="select_with_margins">
	</select>
	<div class="row container-fluid">
		<div class="col-sm-3 w-100">
			<div>Povezivanje</div>
			<div class="scrollable">
				<table id="user_list_table" class="w-100 h-80"></table>
			</div>
		</div>
		<div class="col-sm-3 w-100 align-top">
			<div>Pristigli zahtjevi</div>
			<div class="scrollable">
				<table id="request_list_table" class="w-100 h-80"></table>
			</div>
		</div>
		<div class="col-sm-3 w-100 align-top">
			<div>Poslani zahtjevi</div>
			<div class="scrollable">
				<table id="sent_list_table" class="w-100 h-80"></table>
			</div>
		</div>
		<div class="col-sm-3 w-100 align-top">
			<div>Povezani korisnici</div>
			<div class="scrollable">
				<table id="connected_list_table" class="w-100 h-80"></table>
			</div>
		</div>
	</div>
</div>
</body>
</html>