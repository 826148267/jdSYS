package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Good;
import tools.SQLTool;

public class GoodDao {
	/**
	 * 将发布的表单各项添加到数据库中
	 * 成功返回true
	 * 失败返回false
	 * @param company
	 * @param num
	 * @param price
	 * @param description
	 * @param ownerQQ
	 * @param uid
	 * @return
	 * @throws SQLException
	 */
	public static boolean addGood(Good good)
			throws SQLException {
		boolean flag = false;
		Connection conn = SQLTool.getConnection();
		String sql = "insert into goods(corp,price,surplus,owner_qq,uid,description) values(?,?,?,?,?,?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1,good.getCorp());
		stmt.setFloat(2,good.getPrice());
		stmt.setInt(3,good.getSurplus());
		stmt.setFloat(4,good.getOwnerQQ());
		stmt.setString(5,good.getUid());
		stmt.setString(6,good.getDescription());
		int i = stmt.executeUpdate();
		if(i==1){
			flag = true;
		}
		stmt.close();
		conn.close();
		return flag;
	}

	/**
	 * 通过uid获取货物
	 * @param uid
	 * @param string
	 * @return 
	 * @throws SQLException 
	 */
	public static ArrayList<Good> getGood(String uid) throws SQLException {
		ArrayList<Good> goods = new ArrayList<Good>();
		Connection conn = SQLTool.getConnection();
		String sql = "select * from goods where uid=? and surplus>0";
		ResultSet rs = null;
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1,uid);
		rs = stmt.executeQuery();
		while(rs.next()){
			Good good = new Good();
			good.setCorp(rs.getString("corp"));
			good.setDescription(rs.getString("description"));
			good.setGoodsId(Integer.parseInt(rs.getString("goodsid")));
			good.setOwnerQQ(Float.parseFloat(rs.getString("owner_qq")));
			good.setSurplus(Integer.parseInt(rs.getString("surplus")));
			good.setPrice(Float.parseFloat(rs.getString("price")));
			good.setTime(rs.getString("time"));
			good.setUid(rs.getString("uid"));
			goods.add(good);
		}
		rs.close();
		stmt.close();
		conn.close();
		return goods;
	}

	/**
	 * 通过货物ID来删除货物信息
	 * @param goodsId
	 * @return
	 * @throws SQLException
	 */
	public static boolean removeGood(int goodsId) throws SQLException {
		boolean flag = false;
		Connection conn = SQLTool.getConnection();
		String sql = "update goods set surplus=? where goodsid=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1,0);
		stmt.setInt(2,goodsId);
		int i = stmt.executeUpdate();
		if(i==1){
			flag = true;
		}
		stmt.close();
		conn.close();
		return flag;
	}

	/**
	 * 通过货物ID来获取货物信息
	 * @param goodsId
	 * @return
	 * @throws SQLException
	 */
	public static Good getGoodByGoodsId(int goodsId) throws SQLException {
		Good good = null;
		Connection conn = SQLTool.getConnection();
		String sql = "select corp,price,surplus,owner_qq from goods where goodsid=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1,goodsId);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()){
			good = new Good();
			good.setCorp(rs.getString("corp"));
			good.setGoodsId(goodsId);
			good.setSurplus(Integer.parseInt(rs.getString("surplus")));
			good.setPrice(Float.parseFloat(rs.getString("price")));
			good.setOwnerQQ(Float.parseFloat(rs.getString("owner_qq")));
		}
		rs.close();
		stmt.close();
		conn.close();
		return good;
	}

	/**
	 * 更新库存量
	 * @param goodsId
	 * @param num
	 * @return
	 * @throws SQLException
	 */
	public static boolean updateGoodSurplus(int goodsId, int num) throws SQLException {
		boolean flag = false;
		Connection conn = SQLTool.getConnection();
		String sql = "update goods set surplus=surplus-? where goodsid=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1,num);
		stmt.setInt(2,goodsId);
		int i = stmt.executeUpdate();
		if(i==1){
			flag = true;
		}
		stmt.close();
		conn.close();
		return flag;
	}

}
