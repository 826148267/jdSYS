package tools;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value="/websocket/{uid}/{clientId}")
public class WebSocketTool {
	//用于存放管理ID与管理ID对应的map(clientid,session)
	private static ConcurrentHashMap<String,ConcurrentHashMap<String,Session>> umMap = new ConcurrentHashMap<String,ConcurrentHashMap<String,Session>>();
	//快照方式遍历，所以不支持同步,用于存取session对象
	private static CopyOnWriteArrayList<Session> allSession = new CopyOnWriteArrayList<Session>();
	//设置存储管理的ID和对应的会话
	private static ConcurrentHashMap<String,Session> cs_manager = null;
	//设置心跳包
	private static Timer timer = new Timer();
	//心跳包开始计时，十分钟发一次
	static{
		//umMap.put("管理",cs_manager);
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				callEveryOne();
			}},10*60*1000,10*60*1000);
	}
	public WebSocketTool(){}
	/**
	 * 连接建立，将用户存进会话容器中
	 * 存储结构如下
	 * ConcurrentHashmap（UID，Concurrenthashmap（clientID，session））
	 * @param session
	 * @param uid
	 * @param clientId
	 */
	@OnOpen
	public void onOpen(Session session,@PathParam("uid")String uid,@PathParam("clientId")String clientId){
		System.out.print("已连接");
		//先把session放到list框架中再说
		allSession.add(session);
		//后面是都是放到map的操作
		ConcurrentHashMap<String,Session> cs = umMap.get(uid);
		//如果是管理，就把他另外处理
		if ("管理".equals(clientId)) {
			if (cs_manager==null) {
				cs_manager = new ConcurrentHashMap<String,Session>();
			}
			cs_manager.put(uid,session);
		}else {
			if(cs==null){
				cs = new ConcurrentHashMap<String,Session>();
				umMap.put(uid,cs);
			}else{
				cs = umMap.get(uid);
			}
			cs.put(clientId,session);
		}
		System.out.print("已连接2");
	}
	
	/**
	 * 通过UID和clientID找出session并移除
	 * @param uid
	 * @param clientId
	 */
	@OnClose
	public void onClose(@PathParam("uid")String uid,@PathParam("clientId")String clientId){
		if ("管理".equals(clientId)) {
			allSession.remove(cs_manager.get(uid));
			cs_manager.remove(uid);
			System.out.print("连接已关闭1");
		}else{
			ConcurrentHashMap<String,Session> cs = umMap.get(uid);
			allSession.remove(cs.get(clientId));
			cs.remove(clientId);
			System.out.print("连接已关闭2");
		}
	}
	
	/**
	 * 监听客户端发来的信息
	 * @param session
	 * @param message
	 */
	@OnMessage
	public void onMessage(Session session,String message){
		if ("ping".equals(message)) {
			try {
				session.getBasicRemote().sendText("pong");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 心跳包发送，发现处于半连接状态，抛出错误并关闭会话连接
	 * @param uid
	 * @param clientId
	 * @param error
	 */
	@OnError
	public void onError(@PathParam("uid")String uid,@PathParam("clientId")String clientId,Throwable error){
		error.printStackTrace();
		this.onClose(uid, clientId);	//关闭该连接
	}
	
	
	
	/**
	 * 通过uid和clientId来获取session
	 * @param uid
	 * @param clientId
	 * @return
	 */
	public static Session getSession(String uid,String clientId){
		ConcurrentHashMap<String, Session> cs = umMap.get(uid);
		if(cs==null){
			return null;
		}
		Session session = cs.get(clientId);
		return session;
	}
	
	
	
	
	
	
	
	/**
	 * 通知所有用户
	 * 传入字符串消息message，管理id
	 * @param uid
	 * @param message
	 */
	public static void callAllClient(String uid,String message){
		ConcurrentHashMap<String, Session> cs = umMap.get(uid);
		if(cs!=null){
			Iterator<Entry<String, Session>> it = cs.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, Session> map = it.next();
				try {
					map.getValue().getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 用于心跳包
	 * 想所有会话发送hello包
	 */
	public static void callEveryOne(){
		Iterator<Session> it = allSession.iterator();
		while (it.hasNext()) {
			Session session = (Session) it.next();
			try {
				session.getBasicRemote().sendText("ping");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//通知管理员，通过管理ID
	public static void callManager(String uid,String message) {
		Session session = cs_manager.get(uid);
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//传入管理和客户ID和信息通知该客户
	public static void callClient(String uid, String clientId, String message) {
		Session session = getSession(uid, clientId);
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
