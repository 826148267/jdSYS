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
	 * 具体处理函数实现
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
			callClient(uid);	//通知客户已发布公告
		} catch (SQLException e){
			e.printStackTrace();
		}
		if(flag){
			out.print("发布成功");
		}else{
			out.print("发布失败");
		}
		out.flush();
		out.close();
	}
	
	//通知其他客户已经发布公告了
	private void callClient(String uid) {
		String message = "有新公告";
		WebSocketTool.callAllClient(uid,message);
	}
}













