package com.zhang.utils.imagescreen;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.xnx3.media.ColorUtil;
import com.xnx3.robot.Robot;
import com.xnx3.robot.support.CoordBean;
import com.xnx3.robot.support.RgbImageComparerBean;

public class ImageSearchUtils {

	
	/**
	 * 点的颜色比较
	 */
	public static void colorCompare(){
	    Robot robot = new Robot();
	    //获取屏幕上某点颜色
	    Color color = robot.getPixelColor(100, 100);
	    System.out.println("当前点的颜色值："+ColorUtil.colorToHex(color));
	     
	    //判断屏幕上制定点的颜色是否跟指定颜色相匹配（近似相等）
	    boolean b = robot.getColorCompare(100, 100, "EBF1F9", Robot.SIM_ACCURATE);
	    System.out.println("匹配颜色相似度："+(b? "相似":"不相似"));
	}

	
	/**
	 * 点的颜色比较
	 * @throws AWTException 
	 */
	public static void colorSearch() throws AWTException{
	    Robot robot_ = new Robot();
	    java.awt.Robot robot = new java.awt.Robot();
	    Color color = new Color(199, 35, 36);
	    BufferedImage cutImage = robot.createScreenCapture(new Rectangle(500, 100,800,400)); 
	     
	    RgbImageComparerBean px = robot_.getPX(cutImage);
	    
	   // robot_.colorCompare(color1, color2, sim);
	    //判断屏幕上制定点的颜色是否跟指定颜色相匹配（近似相等）
	   // boolean b = robot.getColorCompare(100, 100, "EBF1F9", Robot.SIM_ACCURATE);
	    //System.out.println("匹配颜色相似度："+(b? "相似":"不相似"));
	    System.out.println(px.getColorArray().toString());
	}

	
	public static void main(String[] args) {
		/*
		 	java.awt.Robot robot = new java.awt.Robot();;
			BufferedImage cutImage = robot.createScreenCapture(new Rectangle(500, 100,800,400)); 
			List<CoordBean> colorSearch2 = colorSearch2(5617149,cutImage);
			robot.setAutoDelay(50);
			robot.delay(5000);
			if(colorSearch2.size()>0){
				for (int i = 0; i < colorSearch2.size(); i++) {
					robot.mouseMove(colorSearch2.get(i).getX()+500, colorSearch2.get(i).getY()+100);
					System.out.println((colorSearch2.get(i).getX()+500) +"++"+ (colorSearch2.get(i).getY()+100));
				}
			}else{
				System.out.println("没有");
			}
			*/
		imageSearch();
	}
	
	
	/**
	 * 图片搜索
	 * 为提高搜索的精确度，推荐使用配套工具截图 http://www.xnx3.com/software/xnx3/ScreenCapture
	 */
	public static void imageSearch(){
	    Robot robot = new Robot();
	    robot.setSourcePath(ImageSearchUtils.class);   //设置此处是为了让程序能自动找到要搜索的图片文件。图片文件在当前类下的res文件夹内
	     
	    //在当前屏幕上搜索search.png图片，看起是否存在
	    List<CoordBean> list1 = robot.imageSearch("search.png", Robot.SIM_BLUR);
	    System.out.println(list1.size()>0? "搜索到了"+list1.size()+"个目标":"没搜索到");
	    if(list1.size()>0){
	        for (int i = 0; i < list1.size(); i++) {
	            CoordBean coord = list1.get(i);
	            System.out.println("搜索到的第"+(i+1)+"个坐标：x:"+coord.getX()+",y:"+coord.getY());
	        }
	    }
	     
	     
	    //在屏幕上指定的区域：左上方x100，y100， 右下方x300，y300的范围内搜索多个图像
	    /*
	    List<CoordBean> list2 = robot.imageSearch(100, 100, 300, 300, "search.png|L.png", Robot.SIM_BLUR_VERY);
	    System.out.println(list2.size()>0? "搜索到了"+list2.size()+"个目标":"没搜索到");
	    if(list2.size()>0){
	        for (int i = 0; i < list2.size(); i++) {
	            CoordBean coord = list2.get(i);
	            System.out.println("搜索到的第"+(i+1)+"个坐标：x:"+coord.getX()+",y:"+coord.getY());
	        }
	    }
	    */
	}
	
	
	
	public static List<CoordBean> colorSearch2(int pxSource,BufferedImage searchImage){
		List<CoordBean> list = new ArrayList<CoordBean>();
		Robot robot_ = new Robot();
		RgbImageComparerBean pxSearch = robot_.getPX(searchImage);
		
		int sim = robot_.SIM_ACCURATE;
		
		int[][] pxS = pxSearch.getColorArray();	//要搜索的目标图的像素点
		//根据sim计算最小像素匹配率
		double pxPercent = 0.99f;
		if(sim>0){
			//RGB的模糊率／4为最小的像素匹配率，大于这个匹配率，为图像识别成功
			pxPercent = ((double)sim/255)/4;
		}
		
		int yes = 0;
		for (int xS = 0; xS < pxSearch.getImgWidth(); xS++) {
			for (int yS = 0; yS < pxSearch.getImgHeight(); yS++) {
				if(robot_.colorCompare(pxSource, pxS[xS][yS], sim)){
					CoordBean coord = new CoordBean();
					coord.setX(xS);
					coord.setY(yS);
					list.add(coord);
				}
			}
		}
		return list;
	}
	
}
