package com.utils.txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyTXT {

	public static void main(String[] args) {
		/*String path = "C:\\Users\\Administrator\\workspace\\hecsp_ssm\\web-component\\gw-ec-set";		//要遍历的路径
		//String path = "C:\\Users\\Administrator\\workspace\\hecsp_ssm\\web-component\\gw-ec-set\\src\\main\\java\\com\\bjgoodwill\\set\\controller";
		List<File> fileList = new ArrayList<>();
		getFileList(fileList,path);
		for (File file : fileList) {
			System.out.println(file.getAbsolutePath());
		}*/
		
		
		
		 // 创建一个正则表达式模式，用以匹配一个单词（\b=单词边界）
		  String patt = "\\bfavor\\b";
		  // 用于测试的输入字符串
		  String input = "Do me a favor? Fetch my favorites.AAA favor BBB";
		  System.out.println("Input:" + input);
		  // 从正则表达式实例中运行方法并查看其如何运行
		  Pattern r = Pattern.compile(patt);
		  Matcher m = r.matcher(input);
		  System.out.println("ReplaceAll:" + m.replaceAll("favour"));
		  // appendReplacement方法
		  m.reset();
		  StringBuffer sb = new StringBuffer();
		  while (m.find()) {
		   // 将匹配之前的字符串复制到sb,再将匹配结果替换为："favour"，并追加到sb
		   m.appendReplacement(sb, "favour");
		  }
		  System.out.println(sb.toString());
		  m.appendTail(sb);
		  System.out.println(sb.toString());
		/*
		输出：
		 
		Input:Do me a favor? Fetch my favorites.AAA favor BBB
		ReplaceAll:Do me a favour? Fetch my favorites.AAA favour BBB
		Do me a favour? Fetch my favorites.AAA favour
		Do me a favour? Fetch my favorites.AAA favour BBB
		*/
		
		
	}

	public static void getFileList(List<File> filelist,String path) {
        File dir = new File(path);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(filelist,files[i].getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.endsWith("Action.java")) { // 判断文件名是否以Action.java结尾
                    filelist.add(files[i]);
                } else {
                    continue;
                }
            }

        }
    }
	
	public static void updadeTxt(File file){
		try {
			//打开文件
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			//内存流
	       // CharArrayWriter caw=new CharArrayWriter();
	        StringBuffer buffer = new StringBuffer();
			String line = null;
			String n = "";
			//按行读取文件
			while ((line = reader.readLine()) != null) {
				//判断改行数据是否包含123
				if(line.length()>0) {
					if(line.indexOf("extends")>0) {
						buffer.append(line);
						buffer.append(System.getProperty("line.separator"));
						//caw.write(line);
						//caw.append(System.getProperty("line.separator"));
						n = "";
					}
				}
			}
			reader.close();
			
			//FileWriter fw = new FileWriter("C:\\Users\\Administrator\\Desktop\\11.txt");
			//new OutputStreamWriter(new OutputStream)
			//caw.writeTo(fw);
			//fw.write(buffer.toString());
			//fw.close();
			
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("C:\\Users\\Administrator\\Desktop\\11.txt"));
            osw.write(buffer.toString());
            osw.flush();
            osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
