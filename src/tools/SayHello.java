package tools;

import java.io.IOException;
import java.util.TimerTask;

import javax.websocket.Session;

public class SayHello extends TimerTask {

	private Session session;
	SayHello(Session session){
		this.session=session;
	}
	@Override
	public void run() {
		try {
			session.getBasicRemote().sendText("hello");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
