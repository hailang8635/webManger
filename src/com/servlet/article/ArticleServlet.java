package com.servlet.article;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tool.DbUtils;
import com.tool.PageUtil;

/**
 * Deprecated. 已不使用
 * @author mei
 *
 */
public class ArticleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8967182313137176011L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if("editUI".equals(method)){
			request.getRequestDispatcher("edit.jsp").forward(request, response);
			//"save".equals(method)
		}else if("edit".equals(method)){
			request.getRequestDispatcher("list.jsp").forward(request, response);
			//"save".equals(method)
		}else if("addUI".equals(method)){
			request.getRequestDispatcher("add.jsp").forward(request, response);
			//"save".equals(method)
		}else if("add".equals(method)){
			String content = request.getParameter("content");
			DbUtils db = new DbUtils();
			String sql = "insert into tbl_article(title,text,type1,author,create_time)values(?,?,?,?,?)";
			Object paramValues[] = new Object[5];
			paramValues[0]="中国河南下雨了";
			paramValues[1]=content;
			paramValues[2]="1";
			paramValues[3]="张小帅";
			paramValues[4]=new Date();
			db.save(sql, paramValues);
			request.setAttribute("content", content);
			request.getRequestDispatcher("list.jsp").forward(request, response);
		}else{
			DbUtils db = new DbUtils();
			String sql = "select count(1) num from tbl_article where (del != 1 or del is null)";
			List<Map<String,Object>> lst = db.query1(sql, null);
			int count = (Integer)lst.get(0).get("num");
			String currentPage = request.getParameter("currentPage");
			PageUtil pg = new PageUtil(Integer.parseInt(currentPage),count);
			String sql2 = "select Id,title,text,type1,author,create_Time,change_Time,view_count,todo from tbl_article";
			sql +=" where (del != 1 or del is null)";
			sql +=" order by create_Time desc";
			sql +=" limit ?,?";
			Object paramValues[] = new Object[2];
			paramValues[0]=pg.getPosition()-1;
			paramValues[1]=pg.getPage();
			request.setAttribute("lst",db.query1(sql2, paramValues));
			request.setAttribute("pageUtil", pg);
			request.getRequestDispatcher("list.jsp").forward(request, response);
		}
		//response.sendRedirect("view.jsp");
	}
}
