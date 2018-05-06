package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.GoodDao;

/**
 * Servlet implementation class RemoveGoodServlet
 */
@WebServlet("/RemoveGoodServlet")
public class RemoveGoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RemoveGoodServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		removeByGoodsId(request,response);	//ͨ������idɾ������
	}

	private void removeByGoodsId(HttpServletRequest request,
			HttpServletResponse response) {
		int goodsId = Integer.parseInt(request.getParameter("goodsId"));
		PrintWriter out = null;
		boolean flag = false;
		try {
			out = response.getWriter();
			flag = GoodDao.removeGood(goodsId);	//˵��ɾ������ʵ�ǽ��嵥��ʣ��������
			if(flag){
				out.print("ɾ���ɹ�");
			}else{
				out.print("ɾ��ʧ��");
			}
			out.flush();
			out.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	
}








