package Dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import tools.SQLTool;

public class NoticeDao {
	/**
	 * ������������ݿ�ִ�к�����
	 * �������ID�����⣬����
	 * ���봫��������Լ���ǰʱ��
	 * @param uid
	 * @param title
	 * @param mainText
	 * @return
	 * @throws SQLException
	 */
	public static boolean addNotice(String uid,String title, String mainText) throws SQLException{
		boolean flag = false;
		Connection conn = SQLTool.getConnection();
		String sql = "insert into notice(uid,title,maintext,sendtime) values(?,?,?,?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1,uid);
		stmt.setString(2,title);
		stmt.setString(3,mainText);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		stmt.setString(4,format.format(new Date()));
		int i = stmt.executeUpdate();
		if(i==1){
			flag=true;
		}
		stmt.close();
		conn.close();
		return flag;
	}

	/**
	 * ͨ������ID����ȡ������Ϣ
	 * @param uid
	 * @return
	 * @throws SQLException
	 */
	public static String getNotice(String uid) throws SQLException {
		Connection conn = SQLTool.getConnection();
		String sql = "select * from notice where uid=?";
		ResultSet rs = null;
		String result = "";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1,uid);
		rs = stmt.executeQuery();
		while(rs.next()){
			result = result + rs.getString("title")+"&"
					+ rs.getString("maintext")+"&"
					+ rs.getString("noticeid")+"&"
					+ rs.getString("sendtime")+"#";
		}
		rs.close();
		stmt.close();
		conn.close();
		return result;
	}

	/**
	 * ����һ��noticeId
	 * �����ݿ�ɾ����Ӧ��Ŀ
	 * �ɹ�������
	 * ʧ�ܷ��ؼ�
	 * @param nid
	 * @return
	 * @throws SQLException
	 */
	public static boolean removeNotice(int nid) throws SQLException {
		boolean flag = false;
		Connection conn = SQLTool.getConnection();
		String sql = "delete from notice where noticeid=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1,nid);
		int i = stmt.executeUpdate();
		if(i==1){
			flag = true;
		}
		stmt.close();
		conn.close();
		return flag;
	}

}
