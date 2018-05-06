package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tools.SQLTool;
import entity.Indent;

public class IndentDao {

	/**
	 * 通过订单状态和管理员id获取订单信息
	 * @param uid
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<Indent> getIndentByManager(String uid, String status) throws SQLException {
		ArrayList<Indent> indents = new ArrayList<Indent>();
		Connection conn = SQLTool.getConnection();
		String sql = "select * from indent_manager where uid=? and status=?";
		ResultSet rs = null;
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1,uid);
		stmt.setString(2,status);
		rs = stmt.executeQuery();
		while(rs.next()){
			Indent indent = new Indent();
			indent.setIndentId(Integer.parseInt(rs.getString("indent_id")));
			indent.setClientId(rs.getString("client_id"));
			indent.setGoodsId(Integer.parseInt(rs.getString("goodsid")));
			indent.setNum(Integer.parseInt(rs.getString("num")));
			indent.setOwnerQQ(Integer.parseInt(rs.getString("owner_qq")));
			indent.setIndentTime(rs.getString("indent_time"));
			indent.setGoodTime(rs.getString("good_time"));
			indent.setTxtPath(rs.getString("txtPath"));
			indent.setUid(rs.getString("uid"));
			indent.setRemark(rs.getString("remark"));
			indent.setDescription(rs.getString("description"));
			indent.setSurplus(Integer.parseInt(rs.getString("surplus")));
			indent.setCorp(rs.getString("corp"));
			indent.setGoodPrice(Float.parseFloat(rs.getString("good_price")));
			indent.setIndentPrice(Float.parseFloat(rs.getString("indent_price")));
			indent.setStatus(rs.getString("status"));
			indents.add(indent);
		}
		rs.close();
		stmt.close();
		conn.close();
		return indents;
	}


	/**
	 * 用户下单，创建订单信息
	 * @param indent
	 * @return
	 * @throws SQLException
	 */
	public static boolean addIndent(Indent indent) throws SQLException {
		boolean flag = false;
		Connection conn = SQLTool.getConnection();
		String sql = "insert into indent(goodsid,client_id,uid,price,num,owner_qq,status,txtPath) values(?,?,?,?,?,?,?,?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setLong(1,indent.getGoodsId());;
		stmt.setString(2,indent.getClientId());
		stmt.setString(3,indent.getUid());
		stmt.setFloat(4,indent.getIndentPrice());
		stmt.setLong(5,indent.getNum());
		stmt.setFloat(6,indent.getOwnerQQ());
		stmt.setString(7,indent.getStatus());
		stmt.setString(8,indent.getTxtPath());
		int i = stmt.executeUpdate();
		if(i==1){
			flag = true;
		}
		stmt.close();
		conn.close();
		return flag;
	}


	/**
	 * 更新订单状态
	 * @param status	更新变成的状态码
	 * @param indentId	更新的订单号
	 * @return	
	 * @throws SQLException
	 */	
	public static boolean updataIndentStatus(String status,int indentId) throws SQLException {
		boolean flag = false;
		Connection conn = SQLTool.getConnection();
		String sql = "update indent set status=? where indent_id=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1,status);
		stmt.setInt(2,indentId);
		int i = stmt.executeUpdate();
		if(i==1){
			flag = true;
		}
		stmt.close();
		conn.close();
		return flag;
	}


	/**
	 * 
	 * @param uid
	 * @param status1
	 * @param status2
	 * @param status3
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<Indent> getHistoryIndentByManager(String uid,
			String status1, String status2, String status3) throws SQLException {
		ArrayList<Indent> indents = new ArrayList<Indent>();
		Connection conn = SQLTool.getConnection();
		String sql = "select * from indent_manager where uid=? and (status=? or status=? or status=?)";
		ResultSet rs = null;
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1,uid);
		stmt.setString(2,status1);
		stmt.setString(3,status2);
		stmt.setString(4,status3);
		rs = stmt.executeQuery();
		while(rs.next()){
			Indent indent = new Indent();
			indent.setIndentId(Integer.parseInt(rs.getString("indent_id")));
			indent.setClientId(rs.getString("client_id"));
			indent.setGoodsId(Integer.parseInt(rs.getString("goodsid")));
			indent.setNum(Integer.parseInt(rs.getString("num")));
			indent.setOwnerQQ(Integer.parseInt(rs.getString("owner_qq")));
			indent.setIndentTime(rs.getString("indent_time"));
			indent.setGoodTime(rs.getString("good_time"));
			indent.setTxtPath(rs.getString("txtPath"));
			indent.setUid(rs.getString("uid"));
			indent.setRemark(rs.getString("remark"));
			indent.setDescription(rs.getString("description"));
			indent.setSurplus(Integer.parseInt(rs.getString("surplus")));
			indent.setCorp(rs.getString("corp"));
			indent.setGoodPrice(Float.parseFloat(rs.getString("good_price")));
			indent.setIndentPrice(Float.parseFloat(rs.getString("indent_price")));
			indent.setStatus(rs.getString("status"));
			indents.add(indent);
		}
		rs.close();
		stmt.close();
		conn.close();
		return indents;
	}


	/**
	 * 通过用户ID和管理ID确定属于自己的订单
	 * 然后用状态码筛选订单
	 * @param uid
	 * @param clientId
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<Indent> getIndentByClient(String uid, String clientId, String status) throws SQLException {
		ArrayList<Indent> indents = new ArrayList<Indent>();
		Connection conn = SQLTool.getConnection();
		String sql = "select * from indent_client where uid=? and status=? and client_id=?";
		ResultSet rs = null;
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1,uid);
		stmt.setString(2,status);
		stmt.setString(3,clientId);
		rs = stmt.executeQuery();
		while(rs.next()){
			Indent indent = new Indent();
			indent.setIndentId(Integer.parseInt(rs.getString("indent_id")));
			indent.setClientId(clientId);
			indent.setGoodsId(Integer.parseInt(rs.getString("goodsid")));
			indent.setNum(Integer.parseInt(rs.getString("num")));
			indent.setIndentTime(rs.getString("indent_time"));
			indent.setGoodTime(rs.getString("good_time"));
			indent.setTxtPath(rs.getString("txtPath"));
			indent.setUid(rs.getString("uid"));
			indent.setDescription(rs.getString("description"));
			indent.setSurplus(Integer.parseInt(rs.getString("surplus")));
			indent.setCorp(rs.getString("corp"));
			indent.setGoodPrice(Float.parseFloat(rs.getString("good_price")));
			indent.setIndentPrice(Float.parseFloat(rs.getString("indent_price")));
			indent.setStatus(rs.getString("status"));
			indents.add(indent);
		}
		rs.close();
		stmt.close();
		conn.close();
		return indents;
	}


	public static ArrayList<Indent> getHistoryIndentByClient(String uid,
			String clientId, String status1, String status2, String status3) throws SQLException {
		ArrayList<Indent> indents = new ArrayList<Indent>();
		Connection conn = SQLTool.getConnection();
		String sql = "select * from indent_client where uid=? and client_id=? and (status=? or status=? or status=?)";
		ResultSet rs = null;
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1,uid);
		stmt.setString(2,clientId);
		stmt.setString(3,status1);
		stmt.setString(4,status2);
		stmt.setString(5,status3);
		rs = stmt.executeQuery();
		while(rs.next()){
			Indent indent = new Indent();
			indent.setIndentId(Integer.parseInt(rs.getString("indent_id")));
			indent.setClientId(rs.getString("client_id"));
			indent.setGoodsId(Integer.parseInt(rs.getString("goodsid")));
			indent.setNum(Integer.parseInt(rs.getString("num")));
			indent.setIndentTime(rs.getString("indent_time"));
			indent.setGoodTime(rs.getString("good_time"));
			indent.setTxtPath(rs.getString("txtPath"));
			indent.setUid(rs.getString("uid"));
			indent.setDescription(rs.getString("description"));
			indent.setSurplus(Integer.parseInt(rs.getString("surplus")));
			indent.setCorp(rs.getString("corp"));
			indent.setGoodPrice(Float.parseFloat(rs.getString("good_price")));
			indent.setIndentPrice(Float.parseFloat(rs.getString("indent_price")));
			indent.setStatus(rs.getString("status"));
			indents.add(indent);
		}
		rs.close();
		stmt.close();
		conn.close();
		return indents;
	}
}














