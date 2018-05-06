package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tools.WebSocketTool;
import entity.Manager;
import Dao.NoticeDao;

/**
 * Servlet implementation class SendNoticeServlet
 */
@WebServlet("/SendNoticeServlet")
public class SendNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendNoticeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		dealSend(req,res);
	}                     
	
	/**
	 * ���崦����ʵ��
	 * @param req
	 * @param res
	 * @throws IOException 
	 */
	private void dealSend(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String title = req.getParameter("title");
		String mainText = req.getParameter("mainText");
		Manager manager = (Manager)req.getSession().getAttribute("user");
		String uid = manager.getUsername();
		PrintWriter out = res.getWriter();
		boolean flag = false;
		try {
			flag = NoticeDao.addNotice(uid,title,mainText);
			callClient(uid);	//֪ͨ�ͻ��ѷ�������
		} catch (SQLException e){
			e.printStackTrace();
		}
		if(flag){
			out.print("�����ɹ�");
		}else{
			out.print("����ʧ��");
		}
		out.flush();
		out.close();
	}
	
	//֪ͨ�����ͻ��Ѿ�����������
	private void callClient(String uid) {
		String message = "���¹���";
		WebSocketTool.callAllClient(uid,message);
	}
}













