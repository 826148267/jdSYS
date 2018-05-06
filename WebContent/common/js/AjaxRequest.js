/**
 * js奇葩的封装对象的方法
 * 封装AjaxRequest走起
 */
var net = new Object();
net.AjaxRequest=function(url,resFunction,onerror,method,params,Header){
	this.req = null;	//定义XmlHttpRequest对象用的
	this.onerror = (onerror)?onerror:this.defaultError;	//设置默认报错方法
	this.resFunction = resFunction;	//保存回调处理函数
	this.loadDate(encodeURI(url),method,params,Header);	//发送请求,接收数据，回调给处理函数
}

/**
 * 加载数据
 * 开出XmlHttpRequest对象来准备异步
 * @param url
 * @param method
 * @param params
 */
net.AjaxRequest.prototype.loadDate=function(url,method,params,Header){
	//默认方法为get
	if(!method){
		method="GET";
	}
	//创建异步请求对象
	if(window.XMLHttpRequest){
		this.req = new XMLHttpRequest();	//非IE或者较新版本的IE
	}else if(window.ActiveXObject){
		alert(window.ActiveXObject);
		try{
			this.req = new ActiveXObject("Microsoft.XMLHTTP");
		}catch(e){
			this.req = new ActiveXObject("Msxml2.XMLHTTP");
		}
	}else{
		alert("建议换个浏览器试试");
		return false;
	}
	
	//如果创建成功
	if(this.req){
		try{
			var loader = this;	//this指实例
			//调取传入的函数
			this.req.onreadystatechange = function(){
				//调用重构的函数，重构的原因很简单，就是还有4.200这些状态码的判断如果不封装在这个对象里的话就会有大量的冗余代码
				net.AjaxRequest.onReadyState.call(loader);	//值得一说的是没有用prototype修饰的方法是不能用this来调用的
			}
			this.req.open(method,url,true);	//与服务器建立连接
			
			if(method=="POST"){
				if(Header==null||Header==""){
					this.req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	//设置请求内容类型
				}
				this.req.setRequestHeader("x-requested-with","ajax")	//设置请求发起者
			}
			this.req.send(params);	//发送请求
		}catch(e){
			this.onerror.call(this);	//报错
		}
	}
}

/**
 * 默认错误响应
 */
net.AjaxRequest.prototype.defaultError = function(){
	alert("错误数据\n\n回调状态："+this.req.readyState+"\n状态："+this.request.status);
}

/**
 * 回调重构函数
 * 主要是封装了一小段关于状态码的处理
 * 
 * 测试的时候发现有点小意思，简直刮目相看
 * 这货在整个请求响应整个过程中都会跑
 * 也就是说会跑很多次，从ready状态1跑到4
 * 可能是为了及时处理函数中各种状态情况所设计
 * 很有意思的一个对象
 * 
 * 0：xmlhttprequest对象初始化完毕
 * 1：open()被调用，还未send()发送请求
 * 2：请求已发送，尚未接收到响应
 * 3：响应头部已接收
 * 4：响应体已接收，完毕
 * 
 * 1xx: 信息提示，临时响应
 * 2xx: 请求成功
 * 3xx: 重定向
 * 4xx: 客户端错误
 * 5xx: 服务器端错误
 */
net.AjaxRequest.onReadyState=function(){
	var req = this.req;
	var ready = req.readyState;	//XMLhttpRequest对象中的请求状态码
	if(ready==4){		//请求完成
		if(req.status==200){	//请求成功，开始处理返回数据
			this.resFunction.call(this);	//回调传入的处理函数
		}else{
			this.onerror.call(this);
		}	
	}
}











