package com.zhang.utils.worm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.zhang.utils.file.FileDownUtil;

public class pet {
	//http://db.178.com/wow/cn/battlepetability/1365.html
	public static String path = "http://db.178.com/wow/cn/battlepet/";

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		
		//getAllLinks("http://db.178.com/wow/cn/battlepet/39.html");
		
		for (int i = 0; i < 1850; i++) {
			//System.out.println("页面="+i);
			String _path = path + i + ".html";
			String image = pet.getAllLinks(_path); // 开始爬取目标内容
			if(image != null) {
				//System.out.println(image);
				FileDownUtil.saveUrlAs("http://img.db.178.com/wow/icons/l/"+image+".jpg", 
						"D:\\CreateCode\\pet\\", image+".jpg" , "GET");
			}
		}
		
		long endTime = System.currentTimeMillis(); // 获取结束时间
		System.out.println("\n程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
	}
	
	/**
	 * @param path 目标地址
	 */
	public static String getAllLinks(String path) {
		Document doc = null;
		try {
			doc = Jsoup.parse(HttpUtil.get(path));
			Element info = doc.select("table").first();
			if(info == null) {
				return null;
			}
			Element table_2 = doc.select("table table").first();
			table_2.remove();
			
			String petId = table_2.select("span.itemID").text(); //id
			String petName = table_2.select("h2.q").text(); //名称
			String petType = table_2.select("td").get(2).text(); //类型
			String sourceType = table_2.select("td").get(3).text(); //专业类型
			
			Elements select = info.select("span.q7");
			String describe = select.text();
			select.remove();
			String sourceTypeDetail = info.text(); //冷却回合
			
			
			
			String docStr = doc.toString();
			String image = docStr.substring(docStr.indexOf("pageIcon:\"")+10, docStr.indexOf("\",pageLang"));
			
			
			String petabilitylevel = docStr.substring(docStr.indexOf("var petabilitylevel_data=[")+27, docStr.indexOf("];var petabilitylevel_num")-1);
			String[] split = petabilitylevel.split("\\],\\[");
			String skill_1 = "";
			String skill_2 = "";
			String skill_4 = "";
			String skill_10 = "";
			String skill_15 = "";
			String skill_20 = "";
			for (String string : split) {
				String[] split2 = string.split(",");
				String level = split2[6];
				switch (level) {
					case "1":
						skill_1 = split2[0];
						break;
					case "2":
						skill_2 = split2[0];
						break;
					case "4":
						skill_4 = split2[0];
						break;
					case "10":
						skill_10 = split2[0];
						break;
					case "15":
						skill_15 = split2[0];
						break;
					case "20":
						skill_20 = split2[0];
						break;
					default:
						break;
				}
			}
			
			//魔力冲撞 ID:1536 魔法 命中率: 25% 冷却: 5回合 100 命中几率 造成40点魔法伤害，并有25%的几率使目标昏迷。
			//String skill = info.select("td").get(0).text(); //备注
			//[魔力冲撞, ID:1536, 魔法, 命中率:, 25%, 冷却:, 5回合, 100, 命中几率, 造成40点魔法伤害，并有25%的几率使目标昏迷。]
			//String[] skills = skill.split(" ");
			/*
			String skillName = skills[0];
			String itemID = skills[1];
			String skillType = skills[2];
			String hitAccuracy = skills[4];
			String coolTime = skills[6];
			String memo = skills[9];
		
			*/
			//String memo = skills[skills.length-1]; //冷却回合
			
			System.out.println("("+petId +",\""+ petName +"\",\""+ petType +"\",\""+ sourceType +"\",\""+
			sourceTypeDetail +"\",\""+ describe +"\",\""+ image +"\",\""+ skill_1 +"\",\""+ skill_2 +"\",\""+ 
					skill_4 +"\",\""+ skill_10 +"\",\""+ skill_15 +"\",\""+ skill_20 +"\"),");
			return image;
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取文件内容
	 * 
	 * @param file 文件类
	 * @return 文件内容
	 */
	public static String readTxtFile(File file) {
		String result = ""; // 读取結果
		String thisLine = ""; // 每次读取的行
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			try {
				while ((thisLine = reader.readLine()) != null) {
					result += thisLine + "\n";
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 写入内容
	 * 
	 * @param file   文件类
	 * @param urlStr 要写入的文本
	 */
	public static void writeTxtFile(File file, String urlStr) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(urlStr);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取文件指定行数的数据，用于爬虫获取当前要爬的链接
	 * 
	 * @param file 目标文件
	 * @param num  指定的行数
	 */
	public static String getFileLine(File file, int num) {
		String thisLine = "";
		int thisNum = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while ((thisLine = reader.readLine()) != null) {
				if (num == thisNum) {
					return thisLine;
				}
				thisNum++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取文件总行数（有多少链接）
	 * 
	 * @param file 文件类
	 * @return 总行数
	 */
	public static int getFileCount(File file) {
		int count = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while (reader.readLine() != null) { // 遍历文件行
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
