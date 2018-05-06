/**
 * 检验账号密码是否符合格式，防止SQL注入
 * @param form
 * @returns {Boolean}
 */
function checkInform(form){
	var username = form.username.value;
	var password = form.password.value;
	var identity = "";
	if(form.identity[0].checked){
		identity = form.identity[0].value;
	}else{
		identity = form.identity[1].value;
	}
	//检验用户名是否为空
	if(username==null||username==""){
		alert("用户名不能为空!");
		document.getElementById("username").focus();
		return false;
	}
	//检验用户名是否符合格式
	if(!/^[0-9a-zA-Z_]{6,25}$/.test(username.trim())){
		alert("用户名格式错误!");
		alert("必须由数字字母或下划线组成，6到25字符之间！");
		document.getElementById("username").focus();
		return false;
	}
	
	
	//检验密码
	if(password==null||password==""){
		alert("密码不能为空!");
		document.getElementById("password").focus();
		return false;
	}
	//检验密码是否符合格式
	if(!/^[0-9a-zA-Z_]{6,25}$/.test(password.trim())){
		alert("密码格式错误!");
		alert("必须由数字字母或下划线组成，6到25字符之间！");
		document.getElementById("password").focus();
		return false;
	}
	
}

function onerror(){
	alert("请求或响应失败，请检查网络");
}




