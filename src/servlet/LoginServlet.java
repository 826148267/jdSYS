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
		dealLogin(request,response);	//�����¼��֤
	}

	private void dealLogin(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String identity = request.getParameter("identity");	//��¼���
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = null;
		PrintWriter out = null;
		String result = "";
		try{
			out = response.getWriter();
			if(!CheckFormatTool.checkIAP(username,password)){	//�����ʽ���Բ�ѯ������ṩ���������Ķ�
				out.print("<script>alert('��ʽ����')</script>");		//����״̬��Ϣ
				out.print("<script>location.href='index/login.jsp'</script>");		//ˢ��ҳ��
			}else{
				password = MD5.md5(password);	//������֮�����ļ���
				if("manager".equals(identity)){	//����ǹ���	
					user = UserDao.managerLogin(username,password);
				}else{	//�������û�·��
					user = UserDao.clientLogin(username,password);
				}
				if(user==null){	//	����з��ض���
					out.print("<script>alert('�������')</script>");		//����״̬��Ϣ
					out.print("<script>location.href='index/login.jsp'</script>");		//ˢ��ҳ��
				}else{
					request.getSession().setAttribute("user",user);	//��������session����
					if("manager".equals(identity)){	//����ǹ���	
						result = "WEB-INF/manager/jsp/manager.jsp";
					}else {
						result = "WEB-INF/client/jsp/client.jsp";
					}
					out.print("<script>alert('ע��ɹ�')</script>");		//ˢ��ҳ��
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













