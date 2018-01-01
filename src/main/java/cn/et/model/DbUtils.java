package cn.et.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DbUtils {

	static Properties p = new Properties();
	static {
		InputStream is = DbUtils.class.getResourceAsStream("/jdbc.properties");
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		String url = p.getProperty("url");
		String driverClass = p.getProperty("driverClass");
		String username = p.getProperty("username");
		String password = p.getProperty("password");
		Connection conn = null;
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static List<Map> query(String sql){
		Connection conn = getConnection();
		PreparedStatement ps;
		List list = null;
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			list = new ArrayList<Object>();
			//获取列的个数
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				Map map = new HashMap<Object, Object>();
				for (int i = 1; i <= columnCount; i++) {
					String colName = rsmd.getColumnName(i);
					String colValue = rs.getString(i);
					map.put(colName, colValue);
				}
				list.add(map);
			}
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static int excute(String sql) throws Exception{
		Connection conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		int i = ps.executeUpdate();
		ps.close();
		conn.close();
		return i;
	}
	
	//打印输出emp表中的数据
	/*public static void main(String[] args) {
		List<Map> result = query("select * from (select t.*,rownum rn from DESK t where t.dname like '%%') where rn>=1 and rn<=2");
		System.out.println(result);
	}*/
}
