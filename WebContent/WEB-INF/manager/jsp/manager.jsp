<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎进入订单管理系统</title>
<link style="text/css" rel="stylesheet" href="common/css/top.css">
<link style="text/css" rel="stylesheet" href="manager/css/manager.css">
<link style="text/css" rel="stylesheet" href="manager/css/notice_manager.css">
<link style="text/css" rel="stylesheet" href="manager/css/user_control.css">
<link style="text/css" rel="stylesheet" href="manager/css/indent_manager.css">
</head>
<body>
	<div id="container">
		<%@ include file="top_manager.jsp" %>
		<div id="main">
			<!--<img alt="欢迎使用订单管理系统" src="../image/main.jpg" id="main_img">-->
		</div>
		<%@ include file="../html/notice.html" %>
		<%@ include file="../html/user_control.html" %>
		<%@ include file="../html/indent.html" %>
		<%@ include file="../../common/jsp/bottom.jsp" %>
		<input type="hidden" id="get_uid" value="${sessionScope.user.username }">
	</div>
	<script type="text/javascript" src="common/js/AjaxRequest.js"></script>
	<script type="text/javascript" src="common/js/WebSocket.js"></script>
	<script type="text/javascript" src="manager/js/Manager.js"></script>
	<script type="text/javascript" src="manager/js/IndentManager.js"></script>
</body>
</html>