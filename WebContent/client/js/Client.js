window.onload=function(){
	var connToWebSocket = new net.connToWebSocket(getUid(),getClientId());	//打开websocket实时连接
}

/*************************************************样式类函数修改到此结束*********************************************/
//获取对应管理人id
function getUid(){
	var uid = document.getElementById("get_uid").value;
	return uid;
}

//获取自己的id
function getClientId(){
	var clientId = document.getElementById("get_clientid").value;
	return clientId;
}
/*************************************************上面是方便编码而重构的函数**********************************************/


 
 /************************************************************************************************************/
 
 
 
 
 
 

 
 
 
 
 
 
 
 