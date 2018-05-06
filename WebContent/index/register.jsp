<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册中</title>
<style type="text/css">
	body{
    	background-color:#888444;
	}
	.container{
	  margin:auto;
	  width:600px;
	}
	.register-box{
	  background: hsla(0,0%,100%,.3);
	  padding: 60px;
	  padding-bottom:30px;
	  padding-top:30px;
	  margin-top:100px;
	  border-radius: 20px;
	}
	.register-title{
	  font-size: 40px;
	  text-align: center;
	  margin-left: auto;
	  margin-right: auto;
	}
	.register-input input{
	  border-left-style: none;
	  border-right-style:none;
	  border-top-style: none;
	  background-color:transparent;
	}
	.register-btn{
	  text-align: center;
	  margin-left: auto;
	  margin-right: auto;
	}
	.register-btn input{
	  width: 200px;
	  height: 20px;
	  border-radius: 6px;
	  background-color: #999999;
	}
	.register input:hover{
	  background-color: #CCCCCC;
	}
	#refer{
	  display:none;
	}
</style>
<script type="text/javascript" src="register.js"></script>
</head>
<body>
	<%  if(session.getAttribute("token")==null||"".equals(session.getAttribute("token"))){
			response.sendRedirect("../GetTokenServlet");
		} 
	%>
	<div class="container">
		<form action="../RegisterServlet" method="post" onSubmit="return checkRegister(this);">
			<div class="register-box">
				  	<div class="register-title">
				   	 <a>注册</a>
				  	</div>
					<div class="register-input"><br>
						<input type="hidden" name="token" value="<%=session.getAttribute("token") %>">
						<input type="radio" name="identity" value="client" onClick="showRefer();">用户&nbsp;&nbsp;&nbsp;&nbsp;
				  		<input type="radio" name="identity" value="manager" onClick="hidenRefer();">管理
						<p>
						  <a >账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</a>
						  <input type="text" name="username" id="username" value="">
						</p>
						<p>
						  <a >昵&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:</a>
						  <input type="text" name="name" id="name" value="">
						</p>
						<p>
						  <a >密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:</a>
						  <input type="password" name="password" id="password" value="">
						</p>
						<p>
						  <a >确认密码:</a>
						  <input type="password" name="repassword" id="repassword" value="">
						</p>
						<div id="refer">
							<a >推荐人ID:</a>
							<input type="text" name="referID" id="referID" value="">
						</div>
				  	</div><br>
				  	
				  <div class="register-btn">
						<input type="submit" name="register" value="提交" id="register"><br><br>
						<input type="button" name="comeback" value="返回" id="comeback" onClick="location.href='login.jsp'">
				  </div>
			</div>
		</form>
	</div>
</body>
</html>