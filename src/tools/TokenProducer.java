package tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import sun.misc.BASE64Encoder;

public class TokenProducer {
	/*
	 * 双校检锁模式
	 * 以后单例再也不用这种模式
	 * 由于jvm内存的无序写入导致这种写法有时候会混乱
	 * 老老实实用枚举
	 */
	private volatile static TokenProducer tokenProducer; 	//告知机器小心阅读，防止自动优化，自认为是第一重锁
	private TokenProducer(){}	//封闭实例化方法
	public static TokenProducer getInstance(){
		if(tokenProducer==null){	//排除大多数情况，先过滤一遍
			synchronized(TokenProducer.class){	//类构造器上锁,第二重锁
				if(tokenProducer==null)	//最后防止上一个被挡在锁外的线程越过上层判空就进来的情况
					return new TokenProducer();
			}
		}
		return tokenProducer;
	}
	
	public static String getToken(){
		String token = System.currentTimeMillis()+new Random().nextInt(999999999)+"";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte md5[] = md.digest(token.getBytes());
		//为了传byte型不知廉耻地用了base64，虽然还有其他办法，比如直接new String(byte[] xxx)
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(md5);
	}
}








