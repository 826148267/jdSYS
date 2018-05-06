package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.NoticeDao;

/**
 * Servlet implementation class RemoveNoticeServlet
 */
@WebServlet("/RemoveNoticeServlet")
public class RemoveNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RemoveNoticeServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		dealRemoveByNoticeId(request,response);
	}

	/**
	 * 将noticeId强制类型转换后传送到持久层
	 * 并接受到处理结果后将结果返回给前端页面
	 * 
	 * @param request
	 * @param response
	 */
	private void dealRemoveByNoticeId(HttpServletRequest request, HttpServletResponse response) {
		String noticeId = request.getParameter("noticeId");
		int nid = Integer.parseInt(noticeId);
		boolean flag = false;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			flag = NoticeDao.removeNotice(nid);
			if(flag){
				out.print("删除成功");
			}else{
				out.print("删除失败");
			}
			out.flush();
			out.close();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

}








