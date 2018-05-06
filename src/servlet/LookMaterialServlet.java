package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class LookMaterialServlet
 */
@WebServlet("/LookMaterialServlet")
public class LookMaterialServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dealLookMaterial(request,response);
	}


	/**
	 * 目前将资料存在项目中，并没有分开存储
	 * 从后台拉取资料数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void dealLookMaterial(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String txtPath = request.getParameter("txtPath");
		txtPath = txtPath.substring(6);
		String basePath = request.getServletContext().getRealPath("/");
        String path = basePath+txtPath;
        String filename=txtPath.substring(txtPath.lastIndexOf("/")+1);
        filename=URLEncoder.encode(filename,"utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition","attachment;filename="+filename);
		File file = new File(path);
		InputStream in = new FileInputStream(file);
		OutputStream out = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(out);
		BufferedInputStream bis = new BufferedInputStream(in);
		byte[] buffer = new byte[1024];
		int i = -1;
		while ((i=bis.read(buffer))!=-1) {
			bos.write(buffer,0,i);
		}
		bos.flush();
		bos.close();
		bis.close();
		out.close();
		in.close();
	}

}
