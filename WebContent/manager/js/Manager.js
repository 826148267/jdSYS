/**
 * 页面初始化
 * 将首页设置成默认点击项
 */
window.onload=function(){
	var home = document.getElementsByClassName("home")[0];
	var home_a = document.getElementById("home_a");
	changeColor(home,home_a);
	var connToWebSocket = new net.connToWebSocket(getUid(),"管理");	//打开websocket实时连接
}

/**
 * 改变成点击后的样式
 * @param node	li节点
 * @param node_a	a节点
 */
function changeColor(node,node_a){
	node.style.backgroundColor='#c7e0fc';
	node.style.borderRadius='15px';
	node.style.borderStyle='groove';
	node.style.color='#ffffff';
	node_a.style.color='#35a4f4';
}

/**
 * 将点击后的样式还原
 * @param node
 * @param node_a
 */
function comeBackStyle(node,node_a){
	node.style.backgroundColor='#35a4f4';
	node.style.borderRadius='0px';
	node.style.borderStyle='none';
	node.style.color='#ffffff';
	node_a.style.color='#ffffff';
}


/**
 * new NowNode()()返回当前存储的节点值
 * new NowNode()(node)设置新的节点替换旧节点
 * @param node
 * @returns {Function}
 */
function NowNode(){
	var NODE ="home";
	var hold = function(node){
		if(""==node||node==null){
			return NODE;
		}else{
			NODE = node;
			return NODE;
		}
	}
	return hold;
}

/**
 * 创建页面记录对象
 * 传入页面信息
 * 可以说是很失败了 
 */
var hold = new NowNode(); //获取记录对象
/**
 * 给我一个当前页面名称
 * 我能改变整个网站
 * @param page
 */
function toPage(page){
	var nowNode = hold();	//获取当前位置
	var node = document.getElementsByClassName(nowNode)[0];	//获取当前节点
	var node_a = document.getElementById(nowNode+'_a');	//	获取当前节点
	var new_node = document.getElementsByClassName(page)[0];	//获取新节点
	var new_node_a = document.getElementById(page+'_a');	//获取新节点
	comeBackStyle(node,node_a);		//还原旧点击点样式
	changeColor(new_node,new_node_a);	//改变点击页面的样式
	hold(page);	//记录当前页面
}



/****************************************************点击“首页”************************************************/

/**
 * 切换成首页
 * @param page
 */
function toHome(page){
	toPage(page);	//弹回首页
	cleanMain();
}

/*****************************************************点击“公告”***********************************************/




/**
 * 点击公告模块后进行的动作
 */
function toNotice(page){
	document.getElementsByClassName("hint")[0].style.display="none";	//隐藏消息提示点
	toPage(page);	//主要掌握跳转模块后各模块样式变化
	cleanMain();
	addNoticeInform();	//在页面主体中显示界面信息
}


/**
 * 弹出发布公告框
 */
function showSend(){
	document.getElementById('send_notice').style.display='block';
}

/**
 * 关闭发布公告框
 */
function hideSend(){
	document.getElementById('send_notice').style.display='none';
}


/**
 * 想服务器端发送公告表单
 * @param form
 */
function sendNotice(form){
	var title = form.title.value;
	var mainText = form.mainText.value;
	hideSend();
	var params = "title="+title+"&mainText="+mainText;
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/SendNoticeServlet?nocache="
			+new Date().getTime(),isSendOver,onerror,"POST",encodeURI(params));
}

/**
 * 对发布公告是否成功进行处理
 */
function isSendOver(){
	var result = this.req.responseText;
	alert(result);	
	getNoticeInform();	//拉取信息，在该方法中应清空原本的公告栏的内容
}

/**
 * 向前端拉取公告信息
 */
function getNoticeInform(){
	cleanNotice();	//清除公告栏
	getNotice();	//从数据库拉取信息
}


/**
 * 清除公告栏原有的内容
 */
function cleanNotice(){
	document.getElementById('notices').innerHTML="";
}

/**
 * x从数据库拉取数据
 */
function getNotice(){
	var params = "";
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/getNoticeServlet?nocache="
			+new Date().getTime(),isGetNotice,onerror,"POST",encodeURI(params));
}

/**
 * 获取数据库数据是否成功
 * 并对其结果进行处理
 */
function isGetNotice(){
	var result = this.req.responseText;
	var notices = result.split("#");
	var str = "";
	for(var i=0;i<notices.length-1;i++){
		var notice = notices[i].split("&");
		str=str+ "<div class='n_main'>" +
			 "<div class='n_title'>"+notice[0]+"</div>" +
			 "<div class='n_text'>"+notice[1]+"</div>" +
			 "<div class='n_d_l'>" +
			 	 "<div class='n_delete'><a href='javascript:void(0);' onClick=\"removeNotice('"+notice[2]+"')\">删除此公告</a></div>" +
			 	 "<div class='n_time'>"+notice[3]+"</div>" +
			 "</div>" +
		 "</div></br>";
	}
	document.getElementById("notices").innerHTML=str;
}

/**
 * 传入一个noticeId
 * 会异步提交到removenotice的servlet进行业务处理
 * @param noticeId
 */
function removeNotice(noticeId){
	var params ="noticeId="+noticeId;
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/RemoveNoticeServlet?nocache="
			+new Date().getTime(),isRemoveNotice,onerror,"POST",encodeURI(params));
}

/**
 * 删除是否成功结果返回
 */
function isRemoveNotice(){
	var result = this.req.responseText;
	alert(result);
	getNoticeInform();
}



/**
 * 点击公告模块后拉取公告页面内容
 */
function addNoticeInform(){
	var notice = document.getElementById("main_notice");
	notice.style.display='block';
	getNoticeInform();
}


/*************************************************点击“用户管理”*****************************************************/

/**
 * 传入当前页面名称，管理ID
 * 利用异步传输
 * 将主体切换成用户管理
 * @param page
 * @param username
 */
function toUserControl(page){
	toPage(page);	//切换页面样式
	cleanMain();
	getUserInform();	//从后台获取用户信息，以字符串形式返回
}

/**
 * 请求用户信息，返回字符串并交给处理函数处理
 * @param username
 */
function getUserInform(){
	var params = "";
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/UserInformServlet?nocache="
			+new Date().getTime(),dealInform,onerror,"POST",encodeURI(params));
}


/**
 * 接收返回的字符串形式的用户信息
 * 解析成一组数组
 * 其中一个数组就是一个成员的昵称和ID
 * 并生成div添加到主体中
 */
function dealInform(){
	var userInform = "";
	if(this.req.responseText!="null"&&this.req.responseText!=null){
		userInform = this.req.responseText;	//获取响应信息
	}
	var users = userInform.split("#");	//对返回的字符串进行切割，按用户存入数组
	addUserInform(users);	//获取信息后创建信息表进行插入
}





/**
 * 有两个触发时间要记住，在这里面
 * 1.删除客户removeUser()
 * 2.添加用户addUser()
 * 
 * 能够将需要显示的用户信息添加到用户信息管理表中
 * 需要两个参数
 * 用户ID和用户昵称
 * @param users 客户信息集合，单个客户信息以字符串存在此数组中
 */
function addUserInform(users){
	var mainUser = document.getElementById("main_user");	//获取main主体div
	var userInform = document.getElementById("user_inform");
	var addUser = document.createElement("div");	//创建div，用于放"添加客户"按钮
	addUser.id="add_user";	//设置id
	var str1 = "<table border='1' borderColor='#ffffff'><tr borderColor='#91c1a9'><td>备注</td><td>用户ID</td><td>用户昵称</td><td>注册时间</td><td>首次登陆时间</td><td>删除用户</td></tr>";
	var str2 = "</table>";
	var str = "";
	for(var i = 0;i<users.length-1;i++){	//遍历用户信息字符串集合
		var client = users[i].split("&");	//分割出具体的每个用户对应的属性
		str = str+ "<tr><td>"+client[0]+"</td>" +	//备注
				"<td>"+client[1]+"</td>" +	//用户ID
				"<td>"+client[2]+"</td>" +	//用户昵称
				"<td>"+client[3]+"</td>" +	//注册时间
				"<td>"+client[4]+"</td>" +	//首次登陆时间
				"<td><a href='#' onClick='removeUser("+client[1]+")'>删除</a></td></tr>";
	}
	str = str1+str+str2;	//拼接好HTML表格信息
	userInform.innerHTML=str;	//写入新建div块中
	addUser.innerHTML="<a href='#' onClick='addUser()' style='text-decoration:none'>添加用户</a>";	//设置按钮内容
	userInform.appendChild(addUser);
	mainUser.style.display="block";
}





/**
 * 添加用户浮窗部分
 */
//打开浮窗
function addUser(){
	document.getElementById("addClient").style.display='block';
}
//关闭浮窗
function closeAdd(){
	document.getElementById("addClient").style.display='none';
}






/**
 * 添加用户
 * @param form
 */
function present(form){
	closeAdd();	//	关闭浮窗	
	var clientId = form.clientId.value;
	var remark = form.remark.value;
	var params = "clientId="+clientId+"&remark="+remark;
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/addUserServlet?nocache="
			+new Date().getTime(),isUserInform,onerror,"POST",encodeURI(params));
}

/**
 * 进行添加或删除的操作之后
 * 重新拉取页面
 */
function isUserInform(){
	var result = this.req.responseText;
	alert(result);
	if(result=="添加成功"){
		alert("添加成功");
	}else if(result=="添加失败"){
		alert("添加失败,用户已存在");
	}else if(result=="删除成功"){
		alert("删除成功");
	}else if(result=="删除失败"){
		alert("删除失败,用户已不存在");
	}
	getUserInform();
}





/**
 * 删除用户，传入指定用户ID
 * @param clientId
 */
function removeUser(clientId){
	var flag = confirm("点击确定将永久删除此用户");
	if(flag==false){
		return;
	}
	var params = "clientId="+clientId;
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/removeUserServlet?nocache="
			+new Date().getTime(),isUserInform,onerror,"POST",encodeURI(params))
}

/****************************************************点击“我的订单”***********************************************/

function toMyIndent(page){
	document.getElementsByClassName("hint")[1].style.display="none";
	toPage(page);
	cleanMain();
	document.getElementById("main_indent").style.display="block";	//显示订单大厅主体
	getWaitReceive();	//获取等待接收的订单
}


/*****************************************************点击“安全退出”***************************************************/

function toExit(page){
	
}


/*******************************************************通用函数****************************************************/

/**
 * 查询错误
 */
function onerror(){
	alert("获取信息失败，欢迎反馈！");
}

/**
 * 模块跳转后清楚主体中的内容
 */
function cleanMain(){
	//首页要清楚的东西
	document.getElementById('main').innerHTML="";	//清空主体
	
	//公告要清除的东西
	document.getElementById('main_notice').style.display="none";	//清除公告主体
	
	//订单要清除的东西
	document.getElementById('main_indent').style.display="none";	//清除订单大厅主体
	
	//用户管理要清除的东西
	document.getElementById('user_inform').innerHTML="";	//清除用户管理主体
	document.getElementById("addClient").style.display="none";	//不显示用户添加浮窗
}

/******************************************道具类，通过隐藏表单来获取管理ID****************************************/
//获取对应管理人id
function getUid(){
	var uid = document.getElementById("get_uid").value;
	return uid;
}
