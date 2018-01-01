package cn.et.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.et.model.MyNews;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class NewsController
 */
public class NewsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewsController() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static final String HTML_DIR = "E:\\html";
    MyNews myNews = new MyNews();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		Date date = new Date();
		String createtime = sdf.format(date);
		String uuid = UUID.randomUUID().toString();
		try {
			//生成html
			//通过freemarker的Configuration读取相应的ftl文件
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
			//设置模板加载的方式，读取路径
			cfg.setDirectoryForTemplateLoading(new File("src/main/resources"));
			//指定模板如何查看数据模型
			cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
			Map root = new HashMap();
			root.put("title", title);
			root.put("content", content);
			root.put("createtime", createtime);
			//实例化模板对象
			Template temp = cfg.getTemplate("newsdetail.ftl");
			//生成html文件，输出目标
			String htmlpath = HTML_DIR+"/"+uuid+".html";
			Writer out = new OutputStreamWriter(new FileOutputStream(htmlpath));
			temp.process(root, out);
			out.flush();
			out.close();
			myNews.insertNews(title, content, createtime, uuid+".html");
			response.getWriter().println("新闻发布成功......");
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
