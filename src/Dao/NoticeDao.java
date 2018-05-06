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
	 * 发布公告的数据库执行函数、
	 * 传入管理ID，标题，正文
	 * 存入传入的三项以及当前时间
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
	 * 通过管理ID来获取公告信息
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
	 * 传入一个noticeId
	 * 到数据库删除对应条目
	 * 成功返回真
	 * 失败返回假
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
