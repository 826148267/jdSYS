<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请先登录</title>
<style type="text/css">
	body{
    	background-color:#888444;
	}
	.container{
	  margin:100px auto;
	  width:600px;
	}
	.login-box{
	  background: hsla(0,0%,100%,.3);
	  padding: 60px;
	  padding-bottom: 30px;
	  border-radius: 20px;
	}
	.login-title{
	  font-size: 40px;
	  text-align: center;
	  margin-left: auto;
	  margin-right: auto;
	}
	.login-input input{
	  border-left-style: none;
	  border-right-style:none;
	  border-top-style: none;
	  background-color:transparent;
	  margin-left: auto;
	  margin-right: auto;
	}
	.login-btn{
	  text-align: center;
	  margin-left: auto;
	  margin-right: auto;
	}
	.login-btn input{
	  width: 200px;
	  height: 20px;
	  border-radius: 6px;
	  background-color: #999999;
	  margin-left: auto;
	  margin-right: auto;
	}
	.login-btn input:hover{
	  background-color: #CCCCCC;
	}
</style>
<script type="text/javascript" src="AjaxRequest.js"></script>
<script type="text/javascript" src="login.js"></script>
</head>
<body>
<center>
	<div class="container">
		<form action="../LoginServlet" method="POST" onsubmit="checkInform(this)" >
			<div class="login-box">
			  	<div class="login-title">
			   	 <a>Login</a>
			  	</div>
				<div class="login-input">
					<p>
					  <a>username:</a>
					  <input type="text" name="username"  id="username" value="">
					</p>
					<p>
					  <a >password:</a>
					  <input type="password" name="password" id="password" value="">
					</p>
					<input type="radio" name="identity" value="client" checked="checked">用户&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="identity" value="manager">管理<br><br>
			  	</div>
			  <div class="login-btn">
					<input type="submit" name="login" value="登录" id="login"><br><br>
					<input type="button" name="register" value="注册" id="register" onClick="location.href='register.jsp'">
			  </div>
			</div>
		</form>
	</div>
</center>
</body>
</html>