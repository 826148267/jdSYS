<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link style="text/css" rel="stylesheet" href="../css/top.css">
</head>
<body>
	<div id="top">
		<div id="top_user" class="top_inline">
		欢迎登录！${sessionScope.user.name}
		</div>
		<div id="top_nav" class="top_inline">
           	<ul>
                <li class="home"><a href="javascript:void(0)" onClick="toHome('home')" id="home_a">首页</a></li>
               	<li class="notice"><a href="#" onClick="toNotice('notice')" id="notice_a">公告</a><div class="hint" onClick="toNotice('notice')"><a href="javascript:void(0);">3</a></div></li>
                <li class="my_indent"><a href="#" onClick="toMyIndent('my_indent')" id="my_indent_a">订单大厅</a><div class="hint" onClick="toMyIndent('my_indent')"><a href="javascript:void(0);">2</a></div></li>
                <li class="exit"><a href="#" onClick="toExit('exit')" id="exit_a">安全退出</a></li>
            </ul>
		</div>
	</div>
</body>
</html>