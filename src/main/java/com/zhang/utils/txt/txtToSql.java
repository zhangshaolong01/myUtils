package com.zhang.utils.txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class txtToSql {

	public static void main(String[] args) {
		String path = "E:\\私人文档\\bdwkxzq327\\Fish-v327\\kpdf\\GBT2260-2013全国行政区划代码数据库表(含层次关系)1.txt";
		writeTxt(path);
	}

	public static void writeTxt(String path) {
		try {
			File file = new File(path);
			List newList = new ArrayList<String>();
			InputStream instream = new FileInputStream(file);
			if (instream != null) {
				InputStreamReader inputreader = new InputStreamReader(instream);
				BufferedReader buffreader = new BufferedReader(inputreader);
				String line;
				// 分行读取
				while ((line = buffreader.readLine()) != null) {
					String[] splited = line.split("\\s+");
					if(splited.length == 7 && splited[0].length() == 6) {
						System.out.println("('"+splited[0]+"','"+splited[1]+"', '"+splited[2]+"', '"+splited[3]+"', '"+splited[4]+"', '"+splited[5]+"', '"+splited[6]+"'),");  
					}else {
						newList.add(line + "\n");
					}
				}
				System.out.println(newList.toString());
				instream.close();
			}
		} catch (Exception e) {
			
		}
	}

}
