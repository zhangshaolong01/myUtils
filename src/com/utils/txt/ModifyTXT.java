package com.utils.txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.utils.xml.XmlUtils;

public class ModifyTXT {

	public static void main(String[] args) {
		System.out.println("---------执行开始-----------");
		String path = "D:\\Workspaces\\aa";	//要遍历的路径
		List<File> fileList = new ArrayList<>();
		Map<String,String> urlMap = new HashMap<>();
		getFileList(fileList,path,"struts.xml");
		for (File file : fileList) {
			XmlUtils.handleXML(file, urlMap);
		}
		System.out.println(urlMap.toString());
		
		/*getFileList(fileList,path,"Action.java");
		for (File file : fileList) {
			updadeTxt(file);
		}*/
		System.out.println("---------执行结束-----------");
	}
	
	
	/**
	 * 遍历文件目录
	 * @param filelist
	 * @param path
	 */
	public static void getFileList(List<File> filelist,String path,String reg) {
        File dir = new File(path);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(filelist,files[i].getAbsolutePath(),reg); // 获取文件绝对路径
                } else if (fileName.indexOf(reg)>0) { // 判断文件名是否以reg结尾
                    filelist.add(files[i]);
                } else {
                    continue;
                }
            }

        }
    }
	
	/**
	 * 修改行
	 * @param file
	 */
	public static void updadeTxt(File file){
		BufferedReader reader = null;
		OutputStreamWriter osw = null;
		try {
			//打开文件
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			//内存流
	        StringBuffer buffer = new StringBuffer();
			String line = null;
			//按行读取文件
			while ((line = reader.readLine()) != null) {
				//判断改行数据是否包含123
				String trim = line.trim();
				if(trim.length()>0) {
					updateDetail(buffer, trim);
				}
			}
            osw = new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath().replace("Action", "Controller")));
            osw.write(buffer.toString());
            osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(reader!=null) {
					reader.close();
				}
				if(osw!=null) {
					osw.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	
	public static void updateDetail(StringBuffer buffer,String line){
		if (line.contains("PageAction")) {
			addDetail(buffer, "@Controller");
			addDetail(buffer, "@RequestMapping(value=\"/omp\")");
			addDetail(buffer, "@Scope(value=\"prototype\")");
			line.replace("PageAction", "PageController");
		}else if(line.startsWith("private") && line.endsWith("Service;")) {
			addDetail(buffer, "@Autowired");
		}else if(line.startsWith("public String") && line.contains("(") && !line.contains("get")) {
			addDetail(buffer, "@ResponseBody");
			addDetail(buffer, "@PostMapping(value = \"/querylist_globalpar\")");
			if(line.contains("querylist")) {
				line.replace("()", "(String data,int pagesize,int pagerow,String sort,String dir)");
			}else {
				line.replace("()", "(String data)");
			}
		}else if(line.contains("setClass")) {
			line.replace("(", "(data,");
		}else if(line.contains("this.getPagesize()")) {
			line.replace("this.getPagesize()", "pagesize");
		}else if(line.contains("this.getPagerow()")) {
			line.replace("this.getPagerow()", "pagerow");
		}else if(line.contains("this.getSort()")) {
			line.replace("this.getSort()", "sort");
		}else if(line.contains("this.getDir()")) {
			line.replace("this.getDir()", "dir");
		}else if(line.contains("setTotalpage")) {
			//addDetail(buffer, line);
		}else if(line.contains("\"success\"")) {
			line.replace("\"success\"", "success()");
		}
		addDetail(buffer, line);
	}
	
	public static void addDetail(StringBuffer buffer,String line){
		buffer.append(line);
		buffer.append(System.getProperty("line.separator"));
	}
}
