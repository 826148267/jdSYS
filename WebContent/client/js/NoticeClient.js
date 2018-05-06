/*************************************************下面是公告方面处理函数************************************************/
function addNoticeInform(){
	var main = document.getElementById("main");
	var notice = document.createElement("div");
	notice.setAttribute("id","main_notice");
	main.appendChild(notice);
	getNoticeInform();	//拉取公告信息
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
	document.getElementById('main_notice').innerHTML="";
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
 * 获取数据库数据
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
			 	 "<div class='n_time'>"+notice[2]+"</div>" +
			 "</div>" +
		 "</div></br>";
	}
	alert(document.getElementById("main_notice"));
	document.getElementById("main_notice").innerHTML=str;
}



 /*************************************下面是websocket处理函数,即时通告**************************************/
 function haveNewNotice(){
	 alert("onmessage:haveNewNotice");
	 if(hold()!="notice"){
		document.getElementsByClassName("hint")[0].style.display="block";	//显示红点提示 
	 }else{
		getNoticeInform();
	 }
 }