package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tools.MD5;
import Dao.UserDao;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		//防止重复提交，检验并回收表单提交令牌
		if(this.hasToken(request)){
			//没有重复提交
			if(this.save(request)){	//注册成功
				response.sendRedirect("index/login.jsp");
			}else{
				out.print("<script language='javascript'>alert('ID未启用或已被注册')</script>");
				out.print("<script language='javascript'>location.href='index/register.jsp'</script>");
			}
		}
		out.flush();
		out.close();
	}
	
	/**
	 * 判断是否有表单token
	 * 有返回true
	 * 没有返回false
	 * @param request
	 * @return
	 */
	private boolean hasToken(HttpServletRequest request) {
		// TODO Auto-generated method stub
		boolean flag = false;	//初始标志位，没有令牌，重复提交
		HttpSession hs = request.getSession();
		if(hs.getAttribute("token")!=null||(!"".equals(hs.getAttribute("token")))){
			hs.removeAttribute("token");
			flag = true;
		}
		return flag;
	}

	/**
	 * 进行注册
	 * 成功返回true
	 * 失败返回false
	 * @param request
	 */
	private boolean save(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String identity = request.getParameter("identity");
		String username = request.getParameter("username");
		String password = MD5.md5(request.getParameter("password"));
		String name = request.getParameter("name");
		String referID = request.getParameter("referID");
		//判断注册人身份
		if("client".equals(identity)){
			//用户注册开始
			return UserDao.clientRegister(username,password,name,referID);
			
		}else if("manager".equals(identity)){
			//管理进行注册
			return UserDao.managerRegister(username,password,name);
			
		}else{
			return false;
		}
	}

}
