package tools;

import javax.naming.*;
import javax.sql.*;

import java.sql.*;
/**
 * �Ȳ���Tomcat���õ�DBCP���ӳ�
 * 1.���������ȶ��Ը�
 * 2.ʹ������࣬��Դ������ѧϰ
 * 3.Ŀǰ���ã����������վЧ�ʵ��˿��Կ�����ʵ��Ч������ʱ�ٿ��ǻ�����
 */

/*
 * �����������Ӻ������������һ����
 * Ϊ�˼������ӷ�æʱ��Ҫ��ȥ��������
 */
public class SQLTool{
	/*
	 *  JNDI�е�������Naming����
	 *  ���ǽ�Java������ĳ�����Ƶ���ʽ�󶨣�binding����һ������������Context���У�
	 *  �Ժ��������������Context���Ĳ��ң�lookup�������ֿ��Բ��ҳ�ĳ���������󶨵�Java����
	 */
	private static Context ctx;	//��������
	private static DataSource dataSource;	//����Դ����
	private static Connection conn = null;
	static{
		try{
			ctx = new InitialContext();	//��ȡ��������
			/*
			 * ��JNDI����Ҫ����Ϊ�˵�ʱ�����ݿ��ʱ���ܹ����ڲ���(�Ժ�ҲҪ������)
			 * �����ϴ�ͳ�����ӷ�ʽ��������࣬ȴ��������ά�������ö����ڴ�������Է��뵽xml�ļ�����������
			 * (����˵ʲô���ó���Ա�����������ⶼ�Ǽٵģ��Ͼ������������˸���һ���������)
			 */
			ctx = (Context)ctx.lookup("java:comp/env");	//��ʵ����JNDI��name����������Դ����
			dataSource = (DataSource) ctx.lookup("jdbc/jdxt");	//��ȡ���ӳض���
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * ��ȡ���Ӷ���
	 */
	public static Connection getConnection(){
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
