<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎进入接单系统</title>
<link style="text/css" rel="stylesheet" href="common/css/top.css">
<link style="text/css" rel="stylesheet" href="client/css/client.css">
<link style="text/css" rel="stylesheet" href="client/css/indent_client.css">
</head>
<body>
	<div id="container">
		<%@ include file="top_client.jsp" %>
		<div id="main"></div>
		<%@ include file="../../common/jsp/bottom.jsp" %>
	</div>
	<input type="hidden" id="get_uid" value="${sessionScope.user.uid }">
	<input type="hidden" id="get_clientid" value="${sessionScope.user.username }">
	<script type="text/javascript" src="common/js/AjaxRequest.js"></script>
	<script type="text/javascript" src="common/js/WebSocket.js"></script>
	<script type="text/javascript" src="client/js/NoticeClient.js"></script>
	<script type="text/javascript" src="client/js/IndentClient.js"></script>
	<script type="text/javascript" src="client/js/TopClient.js"></script>
	<script type="text/javascript" src="client/js/Client.js"></script>
</body>
</html>