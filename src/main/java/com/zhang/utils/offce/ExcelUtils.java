package com.zhang.utils.offce;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class ExcelUtils {
	
	public static void main(String[] args) throws IOException {
		File file = new File("C:\\Users\\zhangshaolong\\Desktop\\国寿嘉园二期\\users.xlsx");
		readExcel(file);
	}

	/**
	 * 
	 * @param file
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	static void readExcel(File file) throws FileNotFoundException, IOException {
		/**
		 * 这里根据不同的excel类型 可以选取不同的处理类： 1.XSSFWorkbook 2.HSSFWorkbook
		 */
		// 获得工作簿
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));

		// 获得工作表
		XSSFSheet sheet = workbook.getSheetAt(0);

		int rows = sheet.getPhysicalNumberOfRows();

		List<GsEmployee> empList = new ArrayList<GsEmployee>();
		
		for (int i = 0; i < rows; i++) {
			// 获取第i行数据
			XSSFRow sheetRow = sheet.getRow(i);
			// 获取第0格数据
			XSSFCell id = sheetRow.getCell(0);
			XSSFCell userName = sheetRow.getCell(1);
			XSSFCell empName = sheetRow.getCell(2);
			XSSFCell projectId = sheetRow.getCell(3);
			XSSFCell projectName = sheetRow.getCell(4);
			
			// 调用toString方法获取内容
			/*
			 * System.out.println(id); System.out.println(userName);
			 * System.out.println(empName); System.out.println(projectId);
			 * System.out.println(projectName);
			 */
			
			
			GsInstitution institution = new GsInstitution();
			institution.setInstitutionId(projectId.getStringCellValue());
			institution.setInstitutionName(projectName.getStringCellValue());
			
			List<GsInstitution> orgList = new ArrayList<>();
			orgList.add(institution);
			
			GsEmployee employee = new GsEmployee();
			employee.setEmployeeNo(id.getStringCellValue());
			employee.setEmployeeName(empName.getStringCellValue());
			employee.setUseraccount(userName.getStringCellValue());
			employee.setDepartId("0");
			employee.setSex("0");
			employee.setInstitutionIds(orgList);
			employee.setOperateStatus("1");
			
			if(empList.size()>0) {
				GsEmployee gsEmployee = empList.get(empList.size()-1);
				if(employee.getEmployeeNo().equals(gsEmployee.getEmployeeNo())) {
					gsEmployee.getInstitutionIds().add(institution);
					continue;
				}else {
					empList.add(employee);
				}
			}else {
				empList.add(employee);
			}
		}
		
		for (GsEmployee gsEmployee : empList) {
			JSONArray fromObject = JSONArray.fromObject(gsEmployee);
			System.out.println(fromObject.toString());
		}
	}

}