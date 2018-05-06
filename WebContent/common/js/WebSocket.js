//设为全局，为了今后拓展功能
var webSocket = null;
net.connToWebSocket = function(uid,clientId){
	if(uid!=""&&clientId!=""){
		alert(clientId);
		var uri = "ws://localhost:8080/jdSYS/websocket/"+uid+"/"+clientId;
		if('WebSocket' in window){
			webSocket = new WebSocket(uri);
		}else if('MozWebSocket' in window){
			webSocket = new MozWebSocket(uri);
		}else{
			alert("请尽量使用高版本浏览器，以便重要信息得到及时推送!");
			//userAjax();
		}
	}


	webSocket.onerror = function(){
		webSocket.onclose();
		alert('连接失败，请尝试确认网络是否通畅');
	}
	
	/**
	 * 监听连接的发生
	 */
	webSocket.onopen = function(){
		alert("测试：websocket连接成功");
		setTimeout((function(){webSocket.send("ping");}),5*1000*60)
		alert("心跳包启动");
	}
	
	/**
	 * 接收服务器端实时信息
	 */
	webSocket.onmessage = function(event){
		if(event.data=="有新公告"){
			alert("onmessage:有新公告");
			haveNewNotice();
		}else if(event.data=="ping"){
			webSocket.send("pong");
		}else if(event.data=="pong"){
		}else if(event.data=="有新货物"){
			haveNewGood();
		}else if(event.data=="havenewagain"){
			alert("您的订单已被接受，交易成功");
		}else if(event.data=="havenewrefuse"){
			alert("您的订单未通过");
		}else if(event.data=="havenewrecevie"){
			alert("您的货物有新的订单等待审批");
		}
	}
	
	/**
	 * 关闭websocket连接
	 */
	webSocket.onclose = function(){
		webSocket.close();
		alert("连接关闭");
	}
	
	/**
	 * 监听窗口关闭事件
	 */
	window.onbeforeunload = function(){
		webSocket.onclose();
	}
	
}





