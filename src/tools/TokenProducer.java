package tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import sun.misc.BASE64Encoder;

public class TokenProducer {
	/*
	 * ˫У����ģʽ
	 * �Ժ�����Ҳ��������ģʽ
	 * ����jvm�ڴ������д�뵼������д����ʱ������
	 * ����ʵʵ��ö��
	 */
	private volatile static TokenProducer tokenProducer; 	//��֪����С���Ķ�����ֹ�Զ��Ż�������Ϊ�ǵ�һ����
	private TokenProducer(){}	//���ʵ��������
	public static TokenProducer getInstance(){
		if(tokenProducer==null){	//�ų������������ȹ���һ��
			synchronized(TokenProducer.class){	//�๹��������,�ڶ�����
				if(tokenProducer==null)	//����ֹ��һ��������������߳�Խ���ϲ��пվͽ��������
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
		//Ϊ�˴�byte�Ͳ�֪���ܵ�����base64����Ȼ���������취������ֱ��new String(byte[] xxx)
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(md5);
	}
}








