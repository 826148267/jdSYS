/**
 * 创建出订单总模块
 * 初始化两个分模块
 */
function addIndentInform(){
	var main = document.getElementById("main");
	var mainIndent = document.createElement("div");
	mainIndent.id = "main_indent";	//订单总模块
	var mainIndentMenu = document.createElement("div");
	mainIndentMenu.id = "main_indent_menu";
	
	var mainIndentMenuUl = document.createElement("ul");
	
	var mainIndentMenuLi1 = document.createElement("li");
	mainIndentMenuLi1.innerHTML="<a href='javascript:void(0)' onclick='getGoods()'>待接订单</a>";
	mainIndentMenuUl.appendChild(mainIndentMenuLi1);
	
	var mainIndentMenuLi2 = document.createElement("li");
	mainIndentMenuLi2.innerHTML="<a href='javascript:void(0)' onclick='getSuccessIndent()'>已成功订单</a>";
	mainIndentMenuUl.appendChild(mainIndentMenuLi2);
	
	var mainIndentMenuLi3 = document.createElement("li");
	mainIndentMenuLi3.innerHTML="<a href='javascript:void(0)' onclick='getHistoryIndent()'>历史订单</a>";
	mainIndentMenuUl.appendChild(mainIndentMenuLi3);
	
	mainIndentMenu.appendChild(mainIndentMenuUl)
	mainIndent.appendChild(mainIndentMenu);	//添加目录模块
	
	var mainIndentContainer = document.createElement("div");
	mainIndentContainer.id = "main_indent_container"
	mainIndent.appendChild(mainIndentContainer);	//添加订单容器
	getGoods();	//拉取货物订单
	main.appendChild(mainIndent);	//初始化整个模块
}

/**
 * 向服务器端拉取信息
 */
function getGoods(){
	var params = "";
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/GetGoodServlet?action=getWaitReceive&nocache="
			+new Date().getTime(),overGetWaitReceive,onerror,"GET",encodeURI(params));
}

/**
 * 处理返回的数据（json），渲染前端页面
 */
function overGetWaitReceive(){
	var result = this.req.responseText;
	var goods = JSON.parse(result);
	var mainIndentContainer = document.getElementById("main_indent_container");
	var str = "";
	for(var i=0;i<goods.length;i++){
		str = str + "<div class='good_main'>" +
				"<div class='good_id'>"+goods[i].goodsId+"</div>" +		//货号
				"<div class='good_uid'>"+goods[i].uid+"</div>" +		//货号
				"<div class='good_corp'>"+goods[i].corp+"</div>" +		//抬头
				"<div class='good_surplus'>"+goods[i].surplus+"</div>" +		//剩余量
				"<div class='good_price'>"+goods[i].price+"</div>" +		//单价
				"<div class='good_time'>"+goods[i].time+"</div>" +		//发布时间
				"<div class='good_status'>待接单</div>" +		//订单状态
				"<div class='good_description'>"+goods[i].description+"</div>" +		//订单备注
				"<div class='good_recevie'><a href='javascript:void(0)' onclick=\"showRecevieIndent("+goods[i].goodsId
					+",\'"+goods[i].corp+"\',"+goods[i].price+","+goods[i].surplus+")\">下单（点击提交资料）</a></div>" +		//撤销订单
			  "</div>"
	}
	mainIndentContainer.innerHTML = str;
}

/**
 * 传入货物单号，抬头名称
 * @param goodsId
 * @param corp
 */
function showRecevieIndent(goodsId,corp,price,surplus){
	var mainIndentContainer=document.getElementById("main_indent_container");
	var mainIndentMaterial=document.createElement("div");
	mainIndentMaterial.id="main_indent_material";
	mainIndentMaterial.innerHTML="<form action='return false;'>"+
									"抬头："+corp+"<br><br>"+	//抬头
									"单价："+price+"<br><br>"+	//单价
									"数量：<input type='text' id='num' name='num' value='' onkeyup='getSum("+price+")'><br><br>"+	//数量
									"总价：<input type='text' id='sum' value='' name='sum' readonly unselectable='on' style='color:gray'><br><br>"+
									"资料：<input type='file' name='material' value='0'><br><br>"+	//文件资料
									"<input type='button' value='提交' onClick='recevieIndent("+goodsId+","+price+","+surplus+",this.form)'>&nbsp;&nbsp;"+
									"<input type='button' value='取消' onClick='closeMaterial()'>&nbsp;&nbsp;"+
								  "</form>"
	mainIndentContainer.appendChild(mainIndentMaterial);
}

/**
 * 提交表单信息
 */
function recevieIndent(goodsId,price,surplus,form){
	var num = document.getElementById("num").value;
	var data = new FormData(form);
	data.enctype="multipart/form-data";
	data.append("goodsId",goodsId);
	data.append("num",num);
	data.append("price",price);
	var params = data;
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/RecevieIndentServlet",
			overRecevieIndent,onerror,"POST",params,"multipart/form-data");
}


function overRecevieIndent(){
	var result = this.req.responseText;
	if(result=="下单成功"){
		alert("下单成功");
		closeMaterial();
	}else if(result=="上传失败"){
		alert("上传失败，请注意文件格式应为txt文本，文件大小小于100k")
	}else if(result=="下单失败"){
		alert("下单失败，请重试");
	}else{
		alert("未知错误");
	}
}


//显示获取总价
function getSum(price){
	var num = document.getElementById('num').value;
	if(price!=null&&price!=''&&num!=null&&num!=''){
		var sum = parseFloat(num)*parseFloat(price);
		document.getElementById("sum").value=sum;
	}else{
		document.getElementById("sum").value='';
	}
}

/**
 * 删除下单表格节点
 */
function closeMaterial(){
	var thisNode=document.getElementById("main_indent_material");
	var parentNode=document.getElementById("main_indent_container");
	parentNode.removeChild(thisNode);
}
/******************************************下面是获取已成功订单**************************/
function getSuccessIndent(){
	var params = "";
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/GetIndentServlet?identity=client&action=getSuccess&nocache="
			+new Date().getTime(),overGetSuccessIndent,onerror,"GET",encodeURI(params));
}

function overGetSuccessIndent(){
	var result = this.req.responseText;
	var indents = JSON.parse(result);
	var menuIndent = document.getElementById("main_indent_container");
	var str = "";
	for(var i=0;i<indents.length;i++){
		str = str + "<div class='indent_main'>" +
				"<div class=''>"+"订单号："+indents[i].indentId+"</div>" + 	//订单号
				"<div class=''>"+"货号："+indents[i].goodsId+"</div>" +  	//货物号
				"<div class=''>"+"抬头："+indents[i].corp+"</div>" +  	//抬头
				"<div class=''>"+"此客户订单单价："+indents[i].indentPrice+"</div>" +  	//下单时单价
				"<div class=''>"+"货物目前单价："+indents[i].goodPrice+"</div>" +  	//目前单价
				"<div class=''>"+"下单数量："+indents[i].num+"</div>" +		//数量
				"<div class=''>"+"剩余数量："+indents[i].surplus+"</div>" +		//剩余量
				"<div class=''>"+"此订单总价："+indents[i].indentPrice*indents[i].num+"</div>" +		//订单总价
				"<div class=''>"+"货物备注："+indents[i].description+"</div>" +  	//货物备注
				"<div class=''>"+"货物发布时间："+indents[i].goodTime+"</div>" +		//货物发布时间
				"<div class=''>"+"客户下单时间："+indents[i].indentTime+"</div>" +		//订单时间
				"<div class=''><a href='http://localhost:8080/jdSYS/LookMaterialServlet?txtPath="+indents[i].txtPath+"' target='_blank'>查看资料</a></div>" +		//文本资料路径
				"<div class=''>订单状态：交易成功</div>" +		//订单状态
			  "</div>"
	}
	menuIndent.innerHTML = str;
}



/******************************************下面是获取历史订单**************************/

function getHistoryIndent(){
	var params = "";
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/GetIndentServlet?identity=client&action=getHistory&nocache="
			+new Date().getTime(),overGetHistoryIndent,onerror,"GET",encodeURI(params));
}

function overGetHistoryIndent(){
	var result = this.req.responseText;
	var indents = JSON.parse(result);
	var menuIndent = document.getElementById("main_indent_container");
	var str = "";
	for(var i=0;i<indents.length;i++){
		str = str + "<div class='indent_main'>" +
				"<div class=''>"+"订单号："+indents[i].indentId+"</div>" + 	//订单号
				"<div class=''>"+"货号："+indents[i].goodsId+"</div>" +  	//货物号
				"<div class=''>"+"抬头："+indents[i].corp+"</div>" +  	//抬头
				"<div class=''>"+"此客户订单单价："+indents[i].indentPrice+"</div>" +  	//下单时单价
				"<div class=''>"+"货物目前单价："+indents[i].goodPrice+"</div>" +  	//目前单价
				"<div class=''>"+"下单数量："+indents[i].num+"</div>" +		//数量
				"<div class=''>"+"剩余数量："+indents[i].surplus+"</div>" +		//剩余量
				"<div class=''>"+"此订单总价："+indents[i].indentPrice*indents[i].num+"</div>" +		//订单总价
				"<div class=''>"+"货物备注："+indents[i].description+"</div>" +  	//货物备注
				"<div class=''>"+"货物发布时间："+indents[i].goodTime+"</div>" +		//货物发布时间
				"<div class=''>"+"客户下单时间："+indents[i].indentTime+"</div>" +		//订单时间
				"<div class=''><a href='http://localhost:8080/jdSYS/LookMaterialServlet?txtPath="+indents[i].txtPath+"' target='_blank'>查看资料</a></div>" +		//文本资料路径
				"<div class=''>订单状态："+indents[i].status+"</div>" +		//订单状态
			  "</div>"
	}
	menuIndent.innerHTML = str;
}

/******************************************下面是即时推送：有新货物**************************/
function haveNewGood(){
	alert("有新货物发布");
	document.getElementsByClassName("hint")[1].style.display="block";	//显示红点提示 
}