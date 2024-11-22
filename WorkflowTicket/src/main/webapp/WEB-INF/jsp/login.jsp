<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Login Form Design</title>
	
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<link rel="stylesheet" href="/css/login.css">
</head>
<body>
	<div class="box">
		<h2>Login</h2>
		<form action="">
			<div class="inputBox">
				<input type="text" id="username" name="username" required  autocomplete="on">
				<label for="username">Username</label>
			</div>
			<div class="inputBox">
				<input type="password" id ="password" name="password" required >
				<label for="password">Password</label>
			</div>
			<button class="button" type="button" onclick="CheckUser()"> Submit</button>
		</form>
	</div>
	<script src="/js/login.js"></script>
</body>
</html>