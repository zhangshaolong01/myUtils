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
		//Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.7.245:3306/zhyl_hecsp", "zhyl", "zhyl@123");
		Connection conn = DriverManager.getConnection("jdbc:mysql://47.95.226.253:3306/zhyl_ec_business_v5", "zhyl", "zhyl@123");
		//any_value()
		String sql2= "SELECT\r\n" + 
				"  a.chg_record_no,\r\n" + 
				"  a.employee_code,\r\n" + 
				"  b.employee_name,\r\n" + 
				"  a.employ_chg_type,\r\n" + 
				"  a.employ_chg_reason,\r\n" + 
				"  a.employ_chg_dt,\r\n" + 
				"  a.original_depart,\r\n" + 
				"  a.original_post,\r\n" + 
				"  a.new_depart,\r\n" + 
				"  a.new_post,\r\n" + 
				"  a.meno,\r\n" + 
				"  a.valid_flag,\r\n" + 
				"  a.update_time     update_time,\r\n" + 
				"  a.operate_man,\r\n" + 
				"  a.operate_reason,\r\n" + 
				"  a.depart_code,\r\n" + 
				"  a.institution_id\r\n" + 
				"FROM tb_hr_employer_chg a,\r\n" + 
				"  tb_employee_info b\r\n" + 
				"WHERE IFNULL(a.valid_flag,'0') != 1\r\n" + 
				"    AND a.employee_code = b.employee_code\r\n" + 
				"ORDER BY a.employee_code, a.update_time DESC\r\n" + 
				"LIMIT 1";
		
		Statement stmt = conn.createStatement();
		// 执行sql
		ResultSet rs = stmt.executeQuery(sql2);

		// 取结果
		while (rs.next()) {
			System.out.println(rs.getString(1) + "," + rs.getString(2));
		}

	}

}
