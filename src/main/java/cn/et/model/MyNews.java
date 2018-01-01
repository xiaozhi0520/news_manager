package cn.et.model;

import java.util.List;
import java.util.Map;

public class MyNews {

	/**
	 * 添加新闻
	 * @param title  标题
	 * @param content  内容
	 * @param createtime  创建时间
	 * @param htmlpath  html文件路径
	 * @throws Exception
	 */
	public void insertNews(String title, String content,String createtime,String htmlpath) throws Exception{
		String sql = "insert into mynews(title,content,createtime,htmlpath) values('"+title+"','"+content+"','"+createtime+"','"+htmlpath+"')";
		DbUtils.excute(sql);
	}
	/**
	 * 查询新闻
	 * @return
	 */
	public List<Map> queryNews(){
		String sql = "select * from mynews";
		return DbUtils.query(sql);
	}
}
