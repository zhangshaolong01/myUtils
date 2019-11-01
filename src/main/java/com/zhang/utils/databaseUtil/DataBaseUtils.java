/**
 * <p>项目名称: myUtils </p> 
 * <p>文件名称: DataBaseUtils.java </p> 
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2019年10月24日 </p>
 * <p>公司信息: ************公司 *********部</p> 
 * @author <a href="mail to: *******@******.com" rel="nofollow">作者</a>
 * @version v1.0
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
package com.zhang.utils.databaseUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author zhangshaolong
 *
 */
public class DataBaseUtils {


	
	
	/**
	 * 数据库操作
	 *@title: main  
	 *@author: zhangshaolong
	 *@date: 2019年10月24日 下午4:20:01
	 *@param args
	 *@throws ClassNotFoundException
	 *@throws SQLException
	 *@throws: 
	 */ 
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// 加载驱动
		Class.forName("com.mysql.jdbc.Driver");
		// 获取链接
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mail", "root", "123");
		String sql = "select id,pet_name_cn  from pet";
		Statement stmt = conn.createStatement();
		// 执行sql
		ResultSet rs = stmt.executeQuery(sql);

		// 取结果
		while (rs.next()) {
			System.out.println(rs.getString(1) + "," + rs.getString(2));
		}

	}

}
