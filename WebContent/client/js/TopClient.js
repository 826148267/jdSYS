
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
	document.getElementById('main').innerHTML="";	//清空主体
}

/** 
 * 切换成首页
 * @param page
 */
function toHome(page){
	toPage(page);
}

/**
 * 切换成公告
 */
function toNotice(page){
	document.getElementsByClassName("hint")[0].style.display="none";	//清除红点提示
	toPage(page);	//点击后“公告”改变样式，其他的初始化样式，核心代码在头部模块
	addNoticeInform();	//添加公告模块
}

function toMyIndent(page){
	document.getElementsByClassName("hint")[1].style.display="none";	//清除红点提示
	toPage(page);	//点击后“订单大厅”改变样式，其他的初始化样式，核心代码在头部模块
	addIndentInform();	//添加订单模块
}

