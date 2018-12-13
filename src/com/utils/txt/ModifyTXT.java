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
		/*String path = "C:\\Users\\Administrator\\workspace\\hecsp_ssm\\web-component\\gw-ec-set";		//Ҫ������·��
		//String path = "C:\\Users\\Administrator\\workspace\\hecsp_ssm\\web-component\\gw-ec-set\\src\\main\\java\\com\\bjgoodwill\\set\\controller";
		List<File> fileList = new ArrayList<>();
		getFileList(fileList,path);
		for (File file : fileList) {
			System.out.println(file.getAbsolutePath());
		}*/
		
		
		
		 // ����һ��������ʽģʽ������ƥ��һ�����ʣ�\b=���ʱ߽磩
		  String patt = "\\bfavor\\b";
		  // ���ڲ��Ե������ַ���
		  String input = "Do me a favor? Fetch my favorites.AAA favor BBB";
		  System.out.println("Input:" + input);
		  // ��������ʽʵ�������з������鿴���������
		  Pattern r = Pattern.compile(patt);
		  Matcher m = r.matcher(input);
		  System.out.println("ReplaceAll:" + m.replaceAll("favour"));
		  // appendReplacement����
		  m.reset();
		  StringBuffer sb = new StringBuffer();
		  while (m.find()) {
		   // ��ƥ��֮ǰ���ַ������Ƶ�sb,�ٽ�ƥ�����滻Ϊ��"favour"����׷�ӵ�sb
		   m.appendReplacement(sb, "favour");
		  }
		  System.out.println(sb.toString());
		  m.appendTail(sb);
		  System.out.println(sb.toString());
		/*
		�����
		 
		Input:Do me a favor? Fetch my favorites.AAA favor BBB
		ReplaceAll:Do me a favour? Fetch my favorites.AAA favour BBB
		Do me a favour? Fetch my favorites.AAA favour
		Do me a favour? Fetch my favorites.AAA favour BBB
		*/
		
		
	}

	public static void getFileList(List<File> filelist,String path) {
        File dir = new File(path);
        File[] files = dir.listFiles(); // ���ļ�Ŀ¼���ļ�ȫ����������
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // �ж����ļ������ļ���
                    getFileList(filelist,files[i].getAbsolutePath()); // ��ȡ�ļ�����·��
                } else if (fileName.endsWith("Action.java")) { // �ж��ļ����Ƿ���Action.java��β
                    filelist.add(files[i]);
                } else {
                    continue;
                }
            }

        }
    }
	
	public static void updadeTxt(File file){
		try {
			//���ļ�
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			//�ڴ���
	       // CharArrayWriter caw=new CharArrayWriter();
	        StringBuffer buffer = new StringBuffer();
			String line = null;
			String n = "";
			//���ж�ȡ�ļ�
			while ((line = reader.readLine()) != null) {
				//�жϸ��������Ƿ����123
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
