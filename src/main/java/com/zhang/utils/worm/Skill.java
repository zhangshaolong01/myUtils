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

import com.zhang.utils.file.FileDownUtil;

public class Skill {
	//http://db.178.com/wow/cn/battlepetability/1365.html
	public static String path = "http://db.178.com/wow/cn/battlepetability/";

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		
		//Skill.getAllLinks("http://db.178.com/wow/cn/battlepetability/1532.html");
		List<String> images = new ArrayList<>();
		for (int i = 0; i < 1600; i++) {
			//System.out.println("页面="+i);
			String _path = path + i + ".html";
			String image = Skill.getAllLinks(_path); // 开始爬取目标内容
			if(image != null) {
				System.out.println(image);
				FileDownUtil.saveUrlAs("http://img.db.178.com/wow/icons/l/"+image+".jpg", 
						"D:\\CreateCode\\skill\\", image+".jpg" , "GET");
				//images.add("http://img.db.178.com/wow/icons/l/"+image+".jpg");
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
			Element table_2 = info.select("table table").first();
			info.select("table table").first().remove();
			
			String skillId = table_2.select("span.itemID").text(); //id
			String skillName = table_2.select("h2.q").text(); //技能名称
			String skillType = table_2.select("td").get(2).text(); //类型
			String hitAccuracy = table_2.select("td").get(3).text(); //命中率
			String coolTime = table_2.select("td").get(4).text(); //冷却回合
			String memo = info.text();
			
			String docStr = doc.toString();
			String image = docStr.substring(docStr.indexOf("pageIcon:\"")+10, docStr.indexOf("\",pageLang"));
			
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
			
			//System.out.println("("+skillId +",\""+ skillName +"\",\""+ skillType +"\","+ hitAccuracy +","+ coolTime +",\""+ image +"\",\""+ memo +"\"),");
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
