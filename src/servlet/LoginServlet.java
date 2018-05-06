package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import tools.CheckFormatTool;
import tools.MD5;
import Dao.UserDao;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
    	
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		dealLogin(request,response);	//处理登录验证
	}

	private void dealLogin(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String identity = request.getParameter("identity");	//登录身份
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = null;
		PrintWriter out = null;
		String result = "";
		try{
			out = response.getWriter();
			if(!CheckFormatTool.checkIAP(username,password)){	//检验格式，对查询语句有提供便利，慎改动
				out.print("<script>alert('格式错误')</script>");		//返回状态信息
				out.print("<script>location.href='index/login.jsp'</script>");		//刷新页面
			}else{
				password = MD5.md5(password);	//检验完之后将明文加密
				if("manager".equals(identity)){	//如果是管理	
					user = UserDao.managerLogin(username,password);
				}else{	//否则走用户路线
					user = UserDao.clientLogin(username,password);
				}
				if(user==null){	//	如果有返回对象
					out.print("<script>alert('密码错误')</script>");		//返回状态信息
					out.print("<script>location.href='index/login.jsp'</script>");		//刷新页面
				}else{
					request.getSession().setAttribute("user",user);	//将它存在session域中
					if("manager".equals(identity)){	//如果是管理	
						result = "WEB-INF/manager/jsp/manager.jsp";
					}else {
						result = "WEB-INF/client/jsp/client.jsp";
					}
					out.print("<script>alert('注册成功')</script>");		//刷新页面
					request.getRequestDispatcher(result).forward(request, response);
				}
			}
			out.flush();
			out.close();
		}catch(SQLException e){
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		super.service(arg0, arg1);
	}
}













