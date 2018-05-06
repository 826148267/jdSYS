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
	//���ڴ�Ź���ID�����ID��Ӧ��map(clientid,session)
	private static ConcurrentHashMap<String,ConcurrentHashMap<String,Session>> umMap = new ConcurrentHashMap<String,ConcurrentHashMap<String,Session>>();
	//���շ�ʽ���������Բ�֧��ͬ��,���ڴ�ȡsession����
	private static CopyOnWriteArrayList<Session> allSession = new CopyOnWriteArrayList<Session>();
	//���ô洢�����ID�Ͷ�Ӧ�ĻỰ
	private static ConcurrentHashMap<String,Session> cs_manager = null;
	//����������
	private static Timer timer = new Timer();
	//��������ʼ��ʱ��ʮ���ӷ�һ��
	static{
		//umMap.put("����",cs_manager);
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				callEveryOne();
			}},10*60*1000,10*60*1000);
	}
	public WebSocketTool(){}
	/**
	 * ���ӽ��������û�����Ự������
	 * �洢�ṹ����
	 * ConcurrentHashmap��UID��Concurrenthashmap��clientID��session����
	 * @param session
	 * @param uid
	 * @param clientId
	 */
	@OnOpen
	public void onOpen(Session session,@PathParam("uid")String uid,@PathParam("clientId")String clientId){
		System.out.print("������");
		//�Ȱ�session�ŵ�list�������˵
		allSession.add(session);
		//�����Ƕ��Ƿŵ�map�Ĳ���
		ConcurrentHashMap<String,Session> cs = umMap.get(uid);
		//����ǹ����Ͱ������⴦��
		if ("����".equals(clientId)) {
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
		System.out.print("������2");
	}
	
	/**
	 * ͨ��UID��clientID�ҳ�session���Ƴ�
	 * @param uid
	 * @param clientId
	 */
	@OnClose
	public void onClose(@PathParam("uid")String uid,@PathParam("clientId")String clientId){
		if ("����".equals(clientId)) {
			allSession.remove(cs_manager.get(uid));
			cs_manager.remove(uid);
			System.out.print("�����ѹر�1");
		}else{
			ConcurrentHashMap<String,Session> cs = umMap.get(uid);
			allSession.remove(cs.get(clientId));
			cs.remove(clientId);
			System.out.print("�����ѹر�2");
		}
	}
	
	/**
	 * �����ͻ��˷�������Ϣ
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
	 * ���������ͣ����ִ��ڰ�����״̬���׳����󲢹رջỰ����
	 * @param uid
	 * @param clientId
	 * @param error
	 */
	@OnError
	public void onError(@PathParam("uid")String uid,@PathParam("clientId")String clientId,Throwable error){
		error.printStackTrace();
		this.onClose(uid, clientId);	//�رո�����
	}
	
	
	
	/**
	 * ͨ��uid��clientId����ȡsession
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
	 * ֪ͨ�����û�
	 * �����ַ�����Ϣmessage������id
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
	 * ����������
	 * �����лỰ����hello��
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
	
	//֪ͨ����Ա��ͨ������ID
	public static void callManager(String uid,String message) {
		Session session = cs_manager.get(uid);
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//�������Ϳͻ�ID����Ϣ֪ͨ�ÿͻ�
	public static void callClient(String uid, String clientId, String message) {
		Session session = getSession(uid, clientId);
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
