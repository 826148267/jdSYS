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
	 * 用户注册方法
	 * 注册成功返回true
	 * 失败返回false
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
		String subordSql = "select uid from subord where client_id='"+username+"'";	//在从属表中找出用户id与注册用户ID相同的管理ID
		String clientSql = "update client set psw='"+password+"',name='"+name+"',reg_time=now() where client_id='"+username+"'";	//各项数据插入client表中
		String isRegisterSql = "select reg_time from client where client_id='"+username+"'";
		ResultSet rs = null;	//结果集初始化为null
		boolean flag = false;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(subordSql);	//执行查询
			if(!rs.next()){	//主从关系表中查不到的话就进入返回false,表示此ID尚不允许注册
				flag = false;	
			}else{
				rs=stmt.executeQuery(isRegisterSql);
				rs.next();
				if(rs.getString("reg_time")==null||"null".equals(rs.getString("reg_time"))){
					if(stmt.executeUpdate(clientSql)==1){	//执行语句并插入成功就返回true
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
	 * 管理注册
	 * 成功返回true
	 * 失败返回false
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
			if((!rs.next())){	//如果不存在uid，返回false
				flag = false;
			}else{	//如果表中存在uid就让他注册，返回true
				if("".equals(rs.getString("password").trim())){
					if(stmt.executeUpdate(joinSql)==1){	//如果插入成功就返回true
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
	 * 通过管理ID获取用户集合
	 * 查询出用户ID和用户昵称
	 * 返回字符串拼接的用户信息
	 * 同一用户信息为用&隔开
	 * 不同用户用#隔开
	 * @param username	管理员ID
	 * @return  用户的实体集合字符串
	 */
	public static String getUserInform(String username) {
		// TODO Auto-generated method stub
		Connection conn = null;		
		PreparedStatement stmt = null;	//定义预处理，主要考虑到可能会对此被点击，加快访问速度，虽然开销会大点
		ResultSet rs = null;	//存放查询到的结果集
		String sql = null;	//sql语句
		String result = "";	//用于存放结果
		try {
			sql = "select remark,subord.client_id,name,subord.reg_time,client.reg_time from subord,client where client.client_id=subord.client_id and client.client_id in( select client_id from subord where uid=?)";
			conn = SQLTool.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			while(rs.next()){	//循环遍历出所有用户信息并拼接成字符串
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
	 * 添加客户，同时两表插入数据，不行就回滚
	 * @param uid
	 * @param clientId
	 * @param remark
	 * @return
	 * @throws SQLException
	 */
	public static boolean addClient(String uid, String clientId, String remark) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = null;		
		PreparedStatement stmt1 = null;	//定义预处理，主要考虑到可能会对此被点击，加快访问速度，虽然开销会大点
		PreparedStatement stmt2 = null;	//定义预处理，主要考虑到可能会对此被点击，加快访问速度，虽然开销会大点
		String subordSql = null;	//sql语句
		String clientSql = null;	//sql语句
		boolean flag = false;	//用于存放结果
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
	 * 删除用户，传入一个ID，同时在两表中删除信息
	 * @param clientId
	 * @return
	 * @throws SQLException
	 */
	public static boolean removeClient(String clientId) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = null;		
		PreparedStatement stmt1 = null;	//定义预处理，主要考虑到可能会对此被点击，加快访问速度，虽然开销会大点
		PreparedStatement stmt2 = null;	//定义预处理，主要考虑到可能会对此被点击，加快访问速度，虽然开销会大点
		String subordSql = null;	//sql语句
		String clientSql = null;	//sql语句
		boolean flag = false;	//用于存放结果
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
	 * 此方法逻辑成立的基础在于格式检验
	 * 修改格式检验时，应考虑此处
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
	 * 同上，多返回一个管理id，主要用于websocket的session对应存储
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












