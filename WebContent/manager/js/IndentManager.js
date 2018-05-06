/******************************************下面是获取等待接单的订单**********************************************/


/**
 * 获取等待接收的订单
 */
function getWaitReceive(){
	var params = "";
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/GetGoodServlet?action=getWaitReceive&nocache="
			+new Date().getTime(),overGetWaitReceive,onerror,"GET",encodeURI(params));
}

/**
 * 回调函数
 * 从后端接受json数据并渲染前端页面
 */
function overGetWaitReceive(){
	var result = this.req.responseText;
	var goods = JSON.parse(result);
	var menuWait = document.getElementById("indent_menu_wait_r");
	var str = "";
	for(var i=0;i<goods.length;i++){
		str = str + "<div class='good_main'>" +
				"<div class='good_id'>"+goods[i].goodsId+"</div>" +		//货号
				"<div class='good_corp'>"+goods[i].corp+"</div>" +		//抬头
				"<div class='good_surplus'>"+goods[i].surplus+"</div>" +		//剩余量
				"<div class='good_price'>"+goods[i].price+"</div>" +		//单价
				"<div class='good_time'>"+goods[i].time+"</div>" +		//发布时间
				"<div class='good_ownerQQ'>"+goods[i].ownerQQ+"</div>" +		//仓库联系QQ
				"<div class='good_status'>待接单</div>" +		//订单状态
				"<div class='good_description'>"+goods[i].description+"</div>" +		//订单备注
				"<div class='good_delete'><a href='javascript:void(0)' onclick='removeGood("+goods[i].goodsId+")'>撤销订单</a></div>" +		//撤销订单
			  "</div>"
	}
	menuWait.innerHTML = str;
}

function removeGood(goodsId){
	var params = "";
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/RemoveGoodServlet?goodsId="+goodsId+"&nocache="
			+new Date().getTime(),overRemoveGood,onerror,"GET",encodeURI(params));
}

function overRemoveGood(){
	var result = this.req.responseText;
	alert(result);
	getWaitReceive();	//刷新页面
}

/*******************************************下面是发布订单模块*********************************************/


/**
 * 显示发布表单订单
 */
function showSendGood(){
	document.getElementById("indent_menu_send").style.display='block';
}

/**
 * 封装表单数据，异步发送给后端
 * @param form
 */
function sendGood(form){
	closeWin();	//关闭浮窗
	var company = form.company.value;
	var num = form.num.value;
	var price = form.price.value;
	var description = form.description.value;
	var ownerQQ = form.ownerQQ.value;
	var params = "company="+company+"&num="+num+"&price="+price+"&description="+description+"&ownerQQ="+ownerQQ;
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/SendGoodServlet?nocache="
			+new Date().getTime(),overSendGood,onerror,"POST",encodeURI(params));
}

/**
 * 解析发布是否成功，并作出相应的响应
 */
function overSendGood(){
	var result = this.req.responseText;
	alert(result);
	getWaitReceive();	//刷新页面
}




//关闭浮窗
function closeWin(){
	document.getElementById("indent_menu_send").style.display='none';
}

/*****************************************下面是获取待审批订单模块***********************************************/
// 获取待审批的订单
function getWaitExamine(){
	var params = "";
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/GetIndentServlet?action=getWaitExamine&identity=manager&nocache="
			+new Date().getTime(),overGetWaitExamine,onerror,"GET",encodeURI(params));
}

//解析json数据，渲染前端页面
function overGetWaitExamine(){
	var result = this.req.responseText;
	var indents = JSON.parse(result);
	var menuIndent = document.getElementById("indent_menu_wait_r");
	var str = "";
	for(var i=0;i<indents.length;i++){
		str = str + "<div class='indent_main'>" +
				"<div class=''>"+"订单号："+indents[i].indentId+"</div>" + 	//订单号
				"<div class=''>"+"货号："+indents[i].goodsId+"</div>" +  	//货物号
				"<div class=''>"+"抬头："+indents[i].corp+"</div>" +  	//抬头
				"<div class=''>"+"此客户订单单价："+indents[i].indentPrice+"</div>" +  	//客户下单时单价
				"<div class=''>"+"欲下单数量："+indents[i].num+"</div>" +		//数量
				"<div class=''>"+"剩余数量："+indents[i].surplus+"</div>" +		//剩余量
				"<div class=''>"+"此客户订单总价："+indents[i].indentPrice*indents[i].num+"</div>" +  	//客户下单时单价
				"<div class=''>"+"货物目前单价："+indents[i].goodPrice+"</div>" +  	//客户下单时单价
				"<div class=''>"+"货物备注："+indents[i].description+"</div>" +  	//货物备注
				"<div class=''>"+"客户ID(备注)："+indents[i].clientId+"("+indents[i].remark+")"+"</div>" + 	  //客户账号（备注）
				"<div class=''>"+"货物发布时间："+indents[i].goodTime+"</div>" +		//货物发布时间
				"<div class=''>"+"客户下单时间："+indents[i].indentTime+"</div>" +		//订单时间
				"<div class=''>"+"仓库QQ："+indents[i].ownerQQ+"</div>" +		//仓库联系QQ
				"<div class=''><a href='http://localhost:8080/jdSYS/LookMaterialServlet?txtPath="+indents[i].txtPath+"' target='_blank'>查看资料</a></div>" +		//文本资料路径
				"<div class=''>订单状态：待审批</div>" +		//订单状态
				"<div class=''><a href='javascript:void(0)' onclick='againIndent("+indents[i].indentId+","+indents[i].goodsId+","+indents[i].num+",\""+indents[i].clientId+"\")'>同意</a></div>" +		//接收此订单
				"<div class=''><a href='javascript:void(0)' onclick='refuseIndent("+indents[i].indentId+",\""+indents[i].clientId+"\")'>拒绝</a></div>" +		//拒绝此订单
			  "</div>"
	}
	menuIndent.innerHTML = str;
}

//同意此订单，传入订单号，更新订单状态码
function againIndent(indentId,goodsId,num,clientId){
	var params = "";
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/UpdateIndentStatusServlet?clientId="+clientId+"&action=againIndent&indentId="+indentId+
			"&num="+num+"&goodsId="+goodsId+"&nocache="+new Date().getTime(),overUpdateIndentStatus,onerror,"GET",encodeURI(params));
}


function refuseIndent(indentId,clientId){
	alert(clientId);
	var params = "";
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/UpdateIndentStatusServlet?clientId="+clientId+"&action=refuseIndent&indentId="+indentId+
			"&nocache="+new Date().getTime(),overUpdateIndentStatus,onerror,"GET",encodeURI(params));
}

//拒绝订单回调函数，传入订单ID，将更新订单状态
function overUpdateIndentStatus(){
	var result = this.req.responseText;
	if(result=="successtorefuse"){
		alert("已成功拒绝此订单");
	}else if(result=="failtorefuse"){
		alert("操作失败，请重试");
	}else if(result=="successtoagain"){
		alert("订单交易成功");
	}else if(result=="failtoagain"){
		alert("操作失败，请重试");
	}else{
		alert("未知错误");
	}
	getWaitExamine(); //刷新章节信息
}
/*****************************************下面是已成功订单模块***********************************************/
//获取已成功订单
function getSuccessIndent(){
	var params = "";
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/GetIndentServlet?identity=manager&action=getSuccess&nocache="
			+new Date().getTime(),overGetSuccessIndent,onerror,"GET",encodeURI(params));
}

function overGetSuccessIndent(){
	var result = this.req.responseText;
	var indents = JSON.parse(result);
	var menuIndent = document.getElementById("indent_menu_wait_r");
	var str = "";
	for(var i=0;i<indents.length;i++){
		str = str + "<div class='indent_main'>" +
				"<div class=''>"+"订单号："+indents[i].indentId+"</div>" + 	//订单号
				"<div class=''>"+"货号："+indents[i].goodsId+"</div>" +  	//货物号
				"<div class=''>"+"抬头："+indents[i].corp+"</div>" +  	//抬头
				"<div class=''>"+"此客户订单单价："+indents[i].indentPrice+"</div>" +  	//客户下单时单价
				"<div class=''>"+"货物目前单价："+indents[i].goodPrice+"</div>" +  	//客户下单时单价
				"<div class=''>"+"下单数量："+indents[i].num+"</div>" +		//数量
				"<div class=''>"+"剩余数量："+indents[i].surplus+"</div>" +		//剩余量
				"<div class=''>"+"此订单总价："+indents[i].indentPrice*indents[i].num+"</div>" +		//订单总价
				"<div class=''>"+"货物备注："+indents[i].description+"</div>" +  	//货物备注
				"<div class=''>"+"客户ID(备注)："+indents[i].clientId+"("+indents[i].remark+")"+"</div>" + 	  //客户账号（备注）
				"<div class=''>"+"货物发布时间："+indents[i].goodTime+"</div>" +		//货物发布时间
				"<div class=''>"+"客户下单时间："+indents[i].indentTime+"</div>" +		//订单时间
				"<div class=''>"+"仓库QQ："+indents[i].ownerQQ+"</div>" +		//仓库联系QQ
				"<div class=''><a href='http://localhost:8080/jdSYS/LookMaterialServlet?txtPath="+indents[i].txtPath+"' target='_blank'>查看资料</a></div>" +		//文本资料路径
				"<div class=''>订单状态：交易成功</div>" +		//订单状态
				"<div class=''><a href='javascript:void(0)' onclick='requestAnnulIndent("+indents[i].indentId+")'>请求撤销此订单</a></div>" +	//请求撤销此订单
			  "</div>"
	}
	menuIndent.innerHTML = str;
}


/*******************************************下面是获取历史订单模块*********************************************/
//获取历史订单
function getHistoryIndent(){
	var params = "";
	var loader = new net.AjaxRequest("http://localhost:8080/jdSYS/GetIndentServlet?identity=manager&action=getHistory&nocache="
			+new Date().getTime(),overGetHistoryIndent,onerror,"GET",encodeURI(params));
}

function overGetHistoryIndent(){
	var result = this.req.responseText;
	var indents = JSON.parse(result);
	var menuIndent = document.getElementById("indent_menu_wait_r");
	var str = "";
	for(var i=0;i<indents.length;i++){
		str = str + "<div class='indent_main'>" +
				"<div class=''>"+"订单号："+indents[i].indentId+"</div>" + 	//订单号
				"<div class=''>"+"货号："+indents[i].goodsId+"</div>" +  	//货物号
				"<div class=''>"+"抬头："+indents[i].corp+"</div>" +  	//抬头
				"<div class=''>"+"此客户订单单价："+indents[i].indentPrice+"</div>" +  	//客户下单时单价
				"<div class=''>"+"货物目前单价："+indents[i].goodPrice+"</div>" +  	//客户下单时单价
				"<div class=''>"+"下单数量："+indents[i].num+"</div>" +		//数量
				"<div class=''>"+"剩余数量："+indents[i].surplus+"</div>" +		//剩余量
				"<div class=''>"+"此订单总价："+indents[i].indentPrice*indents[i].num+"</div>" +		//订单总价
				"<div class=''>"+"货物备注："+indents[i].description+"</div>" +  	//货物备注
				"<div class=''>"+"客户ID(备注)："+indents[i].clientId+"("+indents[i].remark+")"+"</div>" + 	  //客户账号（备注）
				"<div class=''>"+"货物发布时间："+indents[i].goodTime+"</div>" +		//货物发布时间
				"<div class=''>"+"客户下单时间："+indents[i].indentTime+"</div>" +		//订单时间
				"<div class=''>"+"仓库QQ："+indents[i].ownerQQ+"</div>" +		//仓库联系QQ
				"<div class=''><a href='http://localhost:8080/jdSYS/LookMaterialServlet?txtPath="+indents[i].txtPath+"' target='_blank'>查看资料</a></div>" +		//文本资料路径
				"<div class=''>订单状态："+indents[i].status+"</div>" +		//订单状态
			  "</div>"
	}
	menuIndent.innerHTML = str;
}

