/*
 * 展示推荐人选项
 */
function showRefer(){
	document.getElementById("refer").style.display="block";
}

/*
 * 收起推荐人选项
 */
function hidenRefer(){
	document.getElementById("refer").style.display="none";
}


/**
 * 整个表单的验证
 * @param form
 * @returns {Boolean}
 */
function checkRegister(form){
	var username = form.username.value;
	var password = form.password.value;
	var repassword = form.repassword.value;
	var name = form.name.value;	//昵称
	var identity = form.identity.value;	//身份
	var referID = form.referID.value;	//推荐人
	var token = form.token.value;	//令牌
	//判断是否有防重复令牌
	if(token==null||token==""){
		return false;
	}
	//检验用户名是否为空
	if(username==null||username==""){
		alert("用户名不能为空!");
		document.getElementById("username").focus();
		return false;
	}
	//检验用户名是否符合格式
	if(!(/^[0-9a-zA-Z_]{6,25}$/.test(username.trim()))){
		alert("用户名格式错误!");
		alert("必须由数字字母或下划线组成，6到25字符之间！");
		document.getElementById("username").focus();
		return false;
	}
	
	//昵称非空验证
	if(name==null||name==""){
		alert("昵称不能为空!");
		document.getElementById("name").focus();
		return false;
	}
	//昵称合法性验证
	if(!(/^[\u4E00-\uFA29\uE7C7-\uE7F3a-zA-Z0-9_]{1,25}$/.test(name))){
		alert("必须由数字、汉字、字母或下划线组成，不包含非法符号且1到25字符之间！");
		document.getElementById("name").focus();
		return false;
	}
	
	//检验密码
	if(password==null||password==""){
		alert("密码不能为空!");
		document.getElementById("password").focus();
		return false;
	}
	//检验密码是否符合格式
	if(!(/^[0-9a-zA-Z_]{6,25}$/.test(password.trim()))){
		alert("密码格式错误!");
		alert("必须由数字字母或下划线组成，6到25字符之间！");
		document.getElementById("password").focus();
		return false;
	}
	//检验两次输入密码是否一致
	if(password!=repassword){
		alert("两次密码不一致");
		document.getElementById("repassword").focus();
		return false;
	}
	if(identity=="client"){
		//检验推荐人ID
		if(referID==null||referID==""){
			alert("推荐人ID不能为空!");
			document.getElementById("referID").focus();
			return false;
		}
		//检验推荐人ID是否符合格式
		if(!(/^[0-9a-zA-Z_]{6,25}$/.test(referID.trim()))){
			alert("推荐人ID格式错误!");
			alert("必须由数字字母或下划线组成，6到25字符之间！");
			document.getElementById("referID").focus();
			return false;
		}
	}
}