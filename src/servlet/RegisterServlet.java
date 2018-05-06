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
		//��ֹ�ظ��ύ�����鲢���ձ��ύ����
		if(this.hasToken(request)){
			//û���ظ��ύ
			if(this.save(request)){	//ע��ɹ�
				response.sendRedirect("index/login.jsp");
			}else{
				out.print("<script language='javascript'>alert('IDδ���û��ѱ�ע��')</script>");
				out.print("<script language='javascript'>location.href='index/register.jsp'</script>");
			}
		}
		out.flush();
		out.close();
	}
	
	/**
	 * �ж��Ƿ��б�token
	 * �з���true
	 * û�з���false
	 * @param request
	 * @return
	 */
	private boolean hasToken(HttpServletRequest request) {
		// TODO Auto-generated method stub
		boolean flag = false;	//��ʼ��־λ��û�����ƣ��ظ��ύ
		HttpSession hs = request.getSession();
		if(hs.getAttribute("token")!=null||(!"".equals(hs.getAttribute("token")))){
			hs.removeAttribute("token");
			flag = true;
		}
		return flag;
	}

	/**
	 * ����ע��
	 * �ɹ�����true
	 * ʧ�ܷ���false
	 * @param request
	 */
	private boolean save(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String identity = request.getParameter("identity");
		String username = request.getParameter("username");
		String password = MD5.md5(request.getParameter("password"));
		String name = request.getParameter("name");
		String referID = request.getParameter("referID");
		//�ж�ע�������
		if("client".equals(identity)){
			//�û�ע�Ὺʼ
			return UserDao.clientRegister(username,password,name,referID);
			
		}else if("manager".equals(identity)){
			//�������ע��
			return UserDao.managerRegister(username,password,name);
			
		}else{
			return false;
		}
	}

}
