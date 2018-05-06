package Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import entity.Client;
import entity.Manager;
import tools.SQLTool;
public class UserDao {
	/**
	 * �û�ע�᷽��
	 * ע��ɹ�����true
	 * ʧ�ܷ���false
	 * @param username
	 * @param password
	 * @param name
	 * @param referID
	 * @return 
	 * @throws SQLException 
	 */
	public static boolean clientRegister(String username, String password,
			String name, String referID){
		// TODO Auto-generated method stub
		Connection conn = SQLTool.getConnection();
		Statement stmt = null;
		String subordSql = "select uid from subord where client_id='"+username+"'";	//�ڴ��������ҳ��û�id��ע���û�ID��ͬ�Ĺ���ID
		String clientSql = "update client set psw='"+password+"',name='"+name+"',reg_time=now() where client_id='"+username+"'";	//�������ݲ���client����
		String isRegisterSql = "select reg_time from client where client_id='"+username+"'";
		ResultSet rs = null;	//�������ʼ��Ϊnull
		boolean flag = false;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(subordSql);	//ִ�в�ѯ
			if(!rs.next()){	//���ӹ�ϵ���в鲻���Ļ��ͽ��뷵��false,��ʾ��ID�в�����ע��
				flag = false;	
			}else{
				rs=stmt.executeQuery(isRegisterSql);
				rs.next();
				if(rs.getString("reg_time")==null||"null".equals(rs.getString("reg_time"))){
					if(stmt.executeUpdate(clientSql)==1){	//ִ����䲢����ɹ��ͷ���true
						flag = true;
					}
				}
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * ����ע��
	 * �ɹ�����true
	 * ʧ�ܷ���false
	 * @param username
	 * @param password
	 * @param name
	 * @return
	 */
	public static boolean managerRegister(String username, String password,
			String name) {
		// TODO Auto-generated method stub
		Connection conn = SQLTool.getConnection();
		Statement stmt = null;
		String selectSql = "select password from erpan where uid='"+username+"'";
		String joinSql = "update erpan set password='"+password+"',name='"+name+"' where uid='"+username+"'";
		ResultSet rs = null;
		boolean flag = false;
		try {
			stmt = conn.createStatement();
			rs=stmt.executeQuery(selectSql);
			if((!rs.next())){	//���������uid������false
				flag = false;
			}else{	//������д���uid������ע�ᣬ����true
				if("".equals(rs.getString("password").trim())){
					if(stmt.executeUpdate(joinSql)==1){	//�������ɹ��ͷ���true
						flag = true;
					}else{
						flag = false;
					}
				}else{
					flag = false;
				}
				
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * ͨ������ID��ȡ�û�����
	 * ��ѯ���û�ID���û��ǳ�
	 * �����ַ���ƴ�ӵ��û���Ϣ
	 * ͬһ�û���ϢΪ��&����
	 * ��ͬ�û���#����
	 * @param username	����ԱID
	 * @return  �û���ʵ�弯���ַ���
	 */
	public static String getUserInform(String username) {
		// TODO Auto-generated method stub
		Connection conn = null;		
		PreparedStatement stmt = null;	//����Ԥ������Ҫ���ǵ����ܻ�Դ˱�������ӿ�����ٶȣ���Ȼ��������
		ResultSet rs = null;	//��Ų�ѯ���Ľ����
		String sql = null;	//sql���
		String result = "";	//���ڴ�Ž��
		try {
			sql = "select remark,subord.client_id,name,subord.reg_time,client.reg_time from subord,client where client.client_id=subord.client_id and client.client_id in( select client_id from subord where uid=?)";
			conn = SQLTool.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			while(rs.next()){	//ѭ�������������û���Ϣ��ƴ�ӳ��ַ���
				result = result+rs.getString(1)+"&"+rs.getString(2)+"&"
						+rs.getString(3)+"&"+rs.getString(4)
						+"&"+rs.getString(5)+"#";
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * ��ӿͻ���ͬʱ����������ݣ����оͻع�
	 * @param uid
	 * @param clientId
	 * @param remark
	 * @return
	 * @throws SQLException
	 */
	public static boolean addClient(String uid, String clientId, String remark) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = null;		
		PreparedStatement stmt1 = null;	//����Ԥ������Ҫ���ǵ����ܻ�Դ˱�������ӿ�����ٶȣ���Ȼ��������
		PreparedStatement stmt2 = null;	//����Ԥ������Ҫ���ǵ����ܻ�Դ˱�������ӿ�����ٶȣ���Ȼ��������
		String subordSql = null;	//sql���
		String clientSql = null;	//sql���
		boolean flag = false;	//���ڴ�Ž��
		try {
			subordSql = "insert into subord(uid,client_id,remark) values(?,?,?)";
			clientSql = "insert into client(client_id) values(?)";
			conn=SQLTool.getConnection();
			conn.setAutoCommit(false);
			stmt1 = conn.prepareStatement(subordSql);
			stmt1.setString(1,uid);
			stmt1.setString(2,clientId);
			stmt1.setString(3,remark);
			int a = stmt1.executeUpdate();
			stmt2 = conn.prepareStatement(clientSql);
			stmt2.setString(1,clientId);
			int b = stmt2.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
			if(a+b!=2){
				flag = false;
			}else{
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch blo
			conn.rollback();
			e.printStackTrace();
			flag = false;
		}
		stmt1.close();
		stmt2.close();
		conn.close();
		return flag;
	}
	
	/**
	 * ɾ���û�������һ��ID��ͬʱ��������ɾ����Ϣ
	 * @param clientId
	 * @return
	 * @throws SQLException
	 */
	public static boolean removeClient(String clientId) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = null;		
		PreparedStatement stmt1 = null;	//����Ԥ������Ҫ���ǵ����ܻ�Դ˱�������ӿ�����ٶȣ���Ȼ��������
		PreparedStatement stmt2 = null;	//����Ԥ������Ҫ���ǵ����ܻ�Դ˱�������ӿ�����ٶȣ���Ȼ��������
		String subordSql = null;	//sql���
		String clientSql = null;	//sql���
		boolean flag = false;	//���ڴ�Ž��
		try {
			subordSql = "delete from subord where client_id=?";
			clientSql = "delete from client where client_id=?";
			conn=SQLTool.getConnection();
			conn.setAutoCommit(false);
			stmt1 = conn.prepareStatement(subordSql);
			stmt1.setString(1,clientId);
			int a = stmt1.executeUpdate();
			stmt2 = conn.prepareStatement(clientSql);
			stmt2.setString(1,clientId);
			int b = stmt2.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
			if(a+b!=2){
			flag = false;
			}else{
				flag = true;
			}
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		}
		stmt1.close();
		stmt2.close();
		conn.close();
		return flag;
	}

	/**
	 * �˷����߼������Ļ������ڸ�ʽ����
	 * �޸ĸ�ʽ����ʱ��Ӧ���Ǵ˴�
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public static Manager managerLogin(String username, String password) throws SQLException {
		Connection conn = SQLTool.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Manager manager = null;
		String sql = "select * from erpan where uid=?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1,username);
		rs = stmt.executeQuery();
		if(rs.next()){
			if(password.equals(rs.getString("password"))){
				manager = new Manager();
				manager.setName(rs.getString("name"));
				manager.setIdentity("manager");
				manager.setPassword(rs.getString("password"));
				manager.setUsername(rs.getString("uid"));
				manager.setRegTime(rs.getString("reg_time"));
			}
		}
		rs.close();
		stmt.close();
		conn.close();
		return manager;
	}
	
	/**
	 * ͬ�ϣ��෵��һ������id����Ҫ����websocket��session��Ӧ�洢
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public static Client clientLogin(String username, String password) throws SQLException {
		Connection conn = SQLTool.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Client client = null;
		String sql = "select client.client_id,psw,name,uid,client.reg_time from client,subord where subord.client_id=client.client_id and client.client_id=?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1,username);
		rs = stmt.executeQuery();
		if(rs.next()){
			if(password.equals(rs.getString("psw"))){
				client = new Client();
				client.setName(rs.getString("name"));
				client.setIdentity("client");
				client.setPassword(rs.getString("psw"));
				client.setUsername(rs.getString("client_id"));
				client.setUid(rs.getString("uid"));
				client.setRegTime(rs.getString("reg_time"));
			}
		}
		rs.close();
		stmt.close();
		conn.close();
		return client;
	}
	
}












