package com.zhang.utils.offce;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhang.utils.file.IOSave;

public class ExcelUtils {
	private final static Logger log = LoggerFactory.getLogger(ExcelUtils.class);

	public static void main(String[] args) throws IOException {
		//File file = new File("C:\\Users\\zhangshaolong\\Desktop\\小宠物列表.xlsx");
		//readExcel(file);
		
		List list = new ArrayList<>();
		
		Map map = new HashMap();
		map.put("1", "haha");
		map.put("2", "gege");
		list.add(map);
		Workbook wb = writeExcel(list);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        wb.write(bos);
        bos.close();
		IOSave.ByteArrayToFile(bos.toByteArray(), "C:\\Users\\zhangshaolong\\Desktop\\新版\\接口列表.xlsx");
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
		log.debug("==> 开始读取Excel文件;");
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		int sheetNum = workbook.getNumberOfSheets();
		log.debug("==> 工作表数量:" + sheetNum);
		List<Pet> petList = new ArrayList<Pet>();
		for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			if (sheet == null || sheet.getLastRowNum() == 0) {
				continue;
			}
			log.debug("==> 读取工作表,索引:" + sheetIndex + ",名称:" + sheet.getSheetName());
			petList.addAll(excelToBean(sheet));
		}
		log.debug("==> 结束读取Excel文件;");
		
		System.out.println(petList.size());
		for (Pet pet : petList) {
			//JSONArray fromObject = JSONArray.fromObject(pet);
			System.out.println(pet.toString());
		}
	}

	
	public static Workbook writeExcel(List<Map<String,String>> dataList) throws FileNotFoundException, IOException {
		log.debug("==> 开始创建工作簿;");
        XSSFWorkbook wb = new XSSFWorkbook();

        log.debug("==> 创建工作表:");
        XSSFSheet sheet = wb.createSheet();

        for (int i = 0; i < dataList.size(); i++) {
        	 log.debug("==> 创建表表头,索引为0");
             XSSFRow row = sheet.createRow(i);
             row.setHeightInPoints(25);
             
             Map<String,String> map = dataList.get(i);
             int j = 0;
             for(String value : map.values()){
        	    XSSFCell dataCell = row.createCell(j);
        	    dataCell.setCellType(CellType.STRING);
        	    dataCell.setCellValue(value);
        	    j++;
        	}
		}
        log.debug("==> 结束创建工作簿;");
        return wb;
		
	}
	
	
	
	
	static List<Pet> excelToBean(Sheet sheet) {
		log.debug("==> 开始读取工作表;");
		List<String[]> rowDataList = new ArrayList<>();//行数据集合
		log.debug("==> 工作表,名称:" + sheet.getSheetName());
        //工作表行数为零，返回空数组
        if (sheet.getLastRowNum() == 0) {
            log.debug("==> 工作表行数为零，返回空数组;");
            return new ArrayList<Pet>();
        }
		for (Row row : sheet) {
            if (row == null || row.getLastCellNum() == 0) {
                continue;
            }
            log.debug("==> 行:" + row.getRowNum() + ",列数:" + row.getLastCellNum());
            String[] rowData = new String[row.getLastCellNum()];
            for (int i = 0; i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                String cellData = "";
                if (cell == null) {
                    cellData = "";
                } else {
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            cellData = cell.getRichStringCellValue().getString();
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                Date date = cell.getDateCellValue();
                                if (date != null) {
                                    cellData = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                } else {
                                    cellData = "";
                                }
                            } else {
                                cellData = new DecimalFormat("0").format(cell.getNumericCellValue());
                            }
                            break;
                        case BOOLEAN:
                            cellData = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case FORMULA:
                            cellData = String.valueOf(cell.getNumericCellValue());
                            break;
                        default:
                            cellData = "";
                    }
                }
                rowData[i] = cellData;
            }
            rowDataList.add(rowData);
        }
		log.debug("==> 结束读取工作表;");
		
		List<Pet> petList = new ArrayList<Pet>();
		for (int i = 1; i < rowDataList.size(); i++) {
			String[] row = rowDataList.get(i);
			try {
				if(StringUtils.isBlank(row[0]) && StringUtils.isBlank(row[1])) {
					continue;
				}
				Pet pet = new Pet();
				pet.setItemId(row[0]);// 物品id
				pet.setPetId(row[1]);// 宠物id
				pet.setPetType(row[2]);// 类型
				pet.setPetNameCn(row[3]);// 中文名
				pet.setPetNameTd(row[4]);// 繁体名
				pet.setPetNameEn(row[5]);// 英文名
				pet.setIsCage(row[6]);// 装笼
				pet.setSourceType(row[7]);// 来源类型
				pet.setProfessionalType(row[8]);// 专业类型
				pet.setAcquisitionMethod(row[9]);// 获取来源
				pet.setItemNameCn(row[10]);// 物品名
				pet.setItemNameTd(row[11]);// 物品繁体名
				pet.setItemNameEn(row[12]);// 物品英文名
				pet.setCamp(row[13]);// 阵营
				pet.setIsOnly(row[14]);// 是否唯一
				pet.setPrice(row[15]);// 售价
				pet.setPriceUnit("");// 价格单位
				pet.setAddedVersion(row[16]);// 加入版本
				pet.setMemo(row[17]);// 备注

				petList.add(pet);
			} catch (ArrayIndexOutOfBoundsException e) {
				log.error(e.getMessage());
			}
		}
		System.out.println(petList.size());
		return petList;
	}

}