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
		Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.7.245:3306/zhyl_hecsp", "zhyl", "zhyl@123");
		String sql = "select id,pet_name_cn  from pet";
		
		String sql2= "SELECT DISTINCT\r\n" + 
				"  any_value(a.activity_id) activity_id,\r\n" + 
				"  any_value(a.activity_abstract) activity_abstract,\r\n" + 
				"  any_value(a.activity_address) activity_address,\r\n" + 
				"  DATE_FORMAT(a.activity_beg_tm,'%Y-%m-%d %H:%i:%s')    activity_beg_tm,\r\n" + 
				"  any_value(a.activity_code) activity_code,\r\n" + 
				"  any_value(a.activity_detail) activity_detail,\r\n" + 
				"  any_value(DATE_FORMAT(a.activity_end_tm,'%Y-%m-%d %H:%i:%s'))    activity_end_tm,\r\n" + 
				"  any_value(a.activity_master) activity_master,\r\n" + 
				"  any_value(a.activity_name) activity_name,\r\n" + 
				"  any_value(a.activity_org_dept_code) activity_org_dept_code,\r\n" + 
				"  any_value(a.activity_org_dept_name) activity_org_dept_name,\r\n" + 
				"  any_value(DATE_FORMAT(a.activity_sub_beg_tm,'%Y-%m-%d %H:%i:%s'))    activity_sub_beg_tm,\r\n" + 
				"  any_value(DATE_FORMAT(a.activity_sub_end_tm,'%Y-%m-%d %H:%i:%s'))    activity_sub_end_tm,\r\n" + 
				"  any_value(a.activity_summary) activity_summary,\r\n" + 
				"  any_value(a.activity_type) activity_type,\r\n" + 
				"  any_value(DATE_FORMAT(a.cancel_time,'%Y-%m-%d %H:%i:%s'))    cancel_time,\r\n" + 
				"  any_value(a.cancel_user_id) cancel_user_id,\r\n" + 
				"  any_value(a.depart_code) depart_code,\r\n" + 
				"  any_value(a.h5_address) h5_address,\r\n" + 
				"  a.`master_tel`,\r\n" + 
				"  any_value(a.release_user_id) release_user_id,\r\n" + 
				"  a.`status`,\r\n" + 
				"  ''                          action_type,\r\n" + 
				"  any_value(d.imagePath )                imagePath,\r\n" + 
				"  '1'                         isPartake\r\n" + 
				"FROM tb_activity_info a\r\n" + 
				"  LEFT JOIN (SELECT\r\n" + 
				"               e.fun_id                     activity_id,\r\n" + 
				"               GROUP_CONCAT(e.photo_url)    imagePath\r\n" + 
				"             FROM tb_activity_photo e\r\n" + 
				"             WHERE e.valid_flag = 0\r\n" + 
				"             GROUP BY e.fun_id) d\r\n" + 
				"    ON d.activity_id = a.activity_id\r\n" + 
				"WHERE IFNULL(a.valid_flag, '0') = '0'\r\n" + 
				"    AND a.`status` IN(20,30,40,50)\r\n" + 
				"    AND a.activity_id IN(SELECT\r\n" + 
				"                           b.activity_id\r\n" + 
				"                         FROM tb_activity_participator b\r\n" + 
				"                         WHERE IFNULL(b.valid_flag, '0') = '0'\r\n" + 
				"                             AND b.institution_id = 'ba2303e74c2c495a999421831944fa6b'\r\n" + 
				"                             AND b.activity_user_id = '203a84df7d2b48098f571ab378ef24d1')\r\n" + 
				"GROUP BY a.`status` ASC,a.activity_beg_tm DESC\r\n" + 
				"\r\n" + 
				"";
		
		Statement stmt = conn.createStatement();
		// 执行sql
		ResultSet rs = stmt.executeQuery(sql2);

		// 取结果
		while (rs.next()) {
			System.out.println(rs.getString(1) + "," + rs.getString(2));
		}

	}

}
