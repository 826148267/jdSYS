package tools;

import javax.naming.*;
import javax.sql.*;

import java.sql.*;
/**
 * 先采用Tomcat内置的DBCP连接池
 * 1.持续运行稳定性高
 * 2.使用者最多，开源，便于学习
 * 3.目前够用，后期如果网站效率低了可以看出真实的效果，到时再考虑换其他
 */

/*
 * 把最大空闲连接和最大活动连接设置一样了
 * 为了减少连接繁忙时还要再去创建销毁
 */
public class SQLTool{
	/*
	 *  JNDI中的命名（Naming），
	 *  就是将Java对象以某个名称的形式绑定（binding）到一个容器环境（Context）中，
	 *  以后调用容器环境（Context）的查找（lookup）方法又可以查找出某个名称所绑定的Java对象
	 */
	private static Context ctx;	//容器对象
	private static DataSource dataSource;	//数据源对象
	private static Connection conn = null;
	static{
		try{
			ctx = new InitialContext();	//获取容器对象
			/*
			 * 用JNDI，主要就是为了到时换数据库的时候能够易于部署(以后也要用这种)
			 * 和书上传统的连接方式编码量差不多，却更加易于维护，不用都缠在代码里，可以分离到xml文件让容器管理
			 * (网上说什么不用程序员关心连接问题都是假的，毕竟。。。还有人跟我一起做这个？)
			 */
			ctx = (Context)ctx.lookup("java:comp/env");	//其实就是JNDI—name，引用数据源环境
			dataSource = (DataSource) ctx.lookup("jdbc/jdxt");	//获取连接池对象
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * 获取连接对象
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
