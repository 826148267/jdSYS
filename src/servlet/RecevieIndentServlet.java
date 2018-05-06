package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import tools.WebSocketTool;
import Dao.GoodDao;
import Dao.IndentDao;
import entity.Client;
import entity.Good;
import entity.Indent;

@WebServlet("/RecevieIndentServlet")
@MultipartConfig(fileSizeThreshold=1024*4,maxFileSize=1024*100)
public class RecevieIndentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Client client = (Client) request.getSession().getAttribute("user");
		String clientId = client.getUsername();
		String uid = client.getManagerId();
		Part part = request.getPart("material");	//获取表单的文件
		String result="";
		PrintWriter out = response.getWriter();
		Long time = System.currentTimeMillis();
		if(dealUpload(clientId,uid,part,time)){
			if(dealAddIndent(request,response,clientId,uid,part,time)){
				result="下单成功";
				WebSocketTool.callManager(uid,"havenewrecevie");	//通知管理
			}else{
				result="下单失败";
				removeMaterial(clientId,uid,part,time);	//回滚操作
			}
		}else{
			result="上传失败";
		}
		out.print(result);
		out.flush();
		out.close();
	}
	
	/**
	 * 下单失败时，操作回滚，删除已上传的文件
	 * @param clientId
	 * @param uid
	 * @param part
	 */
	private void removeMaterial(String clientId, String uid, Part part,Long time) {
		String cd = part.getHeader("Content-Disposition");
		//截取不同类型的文件需要自行判断
		String fn = cd.substring(cd.lastIndexOf("=")+2,cd.length()-1);
		String filename = fn.substring(fn.lastIndexOf("\\")+1);
		String basePath = this.getServletContext().getRealPath("/material");  
        String path = basePath+"/"+uid+"/"+clientId+"/"+time+"/"+filename; 
        File file = new File(path);
        if(file.exists()){
        	file.delete();
        }
	}

	/**
	 * 如果文件上传成功，处理数据，将数据存入数据库
	 * @param request
	 * @param response
	 * @param uid 
	 * @param clientId 
	 * @param part 
	 */
	private boolean dealAddIndent(HttpServletRequest request,
			HttpServletResponse response, String clientId, String uid,Part part, Long time) {
		boolean flag = false;
		int goodsId = Integer.parseInt(request.getParameter("goodsId"));
		int num= Integer.parseInt(request.getParameter("num"));
		float price = Float.parseFloat(request.getParameter("price"));
		try {
			Good good = GoodDao.getGoodByGoodsId(goodsId);	//拉取货号，准备创建订单
			if(good.getSurplus()>=num&&good.getPrice()==price){	//如果现在的库存量大于客户想要的量并且价格没有改动就可以继续
				String cd = part.getHeader("Content-Disposition");
				//截取不同类型的文件需要自行判断
				String fn = cd.substring(cd.lastIndexOf("=")+2,cd.length()-1);
				String filename = fn.substring(fn.lastIndexOf("\\")+1);
	            String path = "/jdSYS/material/"+uid+"/"+clientId+"/"+time+"/"+filename; 
				Indent indent = new Indent();
				indent.setClientId(clientId);
				indent.setGoodsId(goodsId);
				indent.setNum(num);
				indent.setOwnerQQ(good.getOwnerQQ());
				indent.setStatus("WaitExamine");
				indent.setTxtPath(path);
				indent.setUid(uid);
				indent.setIndentPrice(price);
				indent.setGoodPrice(good.getPrice());
	            flag = IndentDao.addIndent(indent);	//添加订单
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}


	/**
	 * 处理文佳上传
	 * 接收文件并将其保存
	 * @param request
	 * @param response
	 * @param uid
	 * @param clientId 
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	private boolean dealUpload(String clientId, String uid,Part part,Long time)
					throws IOException, ServletException {
		boolean flag = false;
		String cd = part.getHeader("Content-Disposition").trim();
		//截取不同类型的文件需要自行判断
		String fn = cd.substring(cd.lastIndexOf("=")+2,cd.length()-1);
		String filename = fn.substring(fn.lastIndexOf("\\")+1);
		String suffix = filename.substring(filename.lastIndexOf("."));
		if(".txt".equals(suffix)){
			String basePath = this.getServletContext().getRealPath("/material");  
            String path = basePath+"/"+uid+"/"+clientId+"/"+time+"/"+filename;
            File file = new File(basePath+"/"+uid+"/"+clientId+"/"+time);
            file.mkdirs();
            part.write(path);
            file = new File(path);
            if(file.exists()){
            	flag=true;
            }
		}
		return flag;
	}
}
