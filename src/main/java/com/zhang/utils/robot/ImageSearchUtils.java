package com.zhang.utils.robot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import com.xnx3.robot.Robot;
import com.xnx3.robot.support.CoordBean;
import com.xnx3.robot.support.RGBBean;

public class ImageSearchUtils {
	static java.awt.Robot robot;
	
	/**
	 * 要搜索的颜色值
	 */
	public static String sear_color = "FEDDBC";

	/**
	 * @Fields SIM : 搜索模糊度参数 非常精确，精确无误 0 ;精确 31; 模糊 61; 非常模糊 81
	 */
	public final static int SIM = 0;

	public static void main(String[] args) throws AWTException {
		imageSearch();
		/*
		robot = new java.awt.Robot();
		BufferedImage cutImage =  robot.createScreenCapture(new Rectangle(0, 0, 1366, 768));
		CoordBean colorSearch = colorSearch(sear_color, cutImage);
		if(colorSearch != null) {
			System.out.println(colorSearch.getX()+":"+colorSearch.getY());
			
			robot.mouseMove(colorSearch.getX(), colorSearch.getY());
		}else {
			System.out.println("没有找到!");
		}
		*/
	}

	/**
	 * 图片搜索 为提高搜索的精确度，推荐使用配套工具截图 http://www.xnx3.com/software/xnx3/ScreenCapture
	 */
	public static void imageSearch() {
		Robot robot = new Robot();
		robot.setSourcePath(ImageSearchUtils.class); // 设置此处是为了让程序能自动找到要搜索的图片文件。图片文件在当前类下的res文件夹内

		// 在当前屏幕上搜索search.png图片，看起是否存在
		List<CoordBean> list1 = robot.imageSearch("search.png", Robot.SIM_BLUR);
		System.out.println(list1.size() > 0 ? "搜索到了" + list1.size() + "个目标" : "没搜索到");
		if (list1.size() > 0) {
			for (int i = 0; i < list1.size(); i++) {
				CoordBean coord = list1.get(i);
				System.out.println("搜索到的第" + (i + 1) + "个坐标：x:" + coord.getX() + ",y:" + coord.getY());
			}
		}
	}

	/**
	 * @Description: 搜索指定颜色的坐标 @return: List<CoordBean> @throws
	 */
	public static CoordBean colorSearch(String pxSource, BufferedImage searchImage) {
		CoordBean coord = new CoordBean();
		for (int x = searchImage.getMinX(); x < searchImage.getWidth(); x++) {
			for (int y = searchImage.getMinY(); y < searchImage.getHeight(); y++) {
				if (colorCompare(pxSource, searchImage.getRGB(x, y), SIM)) {
					coord.setX(x);
					coord.setY(y);
					return coord;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 两像素点的颜色值比较，是否匹配
	 * 
	 * @param pxSource 像素1，原像素，十进制的颜色值
	 * @param pxSearch 像素2，要搜索对比的像素，十进制的颜色值
	 * @param sim      模糊值，如：
	 *                 <ul>
	 *                 <li>非常精确，无误差：{@link Robot#SIM_ACCURATE_VERY}
	 *                 <li>精确，极小的误差：{@link Robot#SIM_ACCURATE}
	 *                 <li>模糊，有误差，模糊搜索：{@link Robot#SIM_BLUR}
	 *                 <li>非常模糊，误差很大：{@link Robot#SIM_BLUR_VERY}
	 *                 </ul>
	 * @return true:成功
	 */
	public static boolean colorCompare(String pxSourceSt, int pxSearch, int sim) {
		int pxSource = Integer.parseInt(pxSourceSt, 16);
		if (sim == 1) {
			return pxSearch == pxSource;
		} else {
			Color sourceRgb = intToColor(pxSource);
			Color searchRgb = intToColor(pxSearch);
			return colorCompare(sourceRgb, searchRgb, sim);
		}
	}
	
	/**
	 * 两颜色值比较，是否匹配
	 * 
	 * @param color1 颜色1
	 * @param color2 颜色2
	 * @param sim    模糊值，如：
	 *               <ul>
	 *               <li>非常精确，无误差：{@link Robot#SIM_ACCURATE_VERY}
	 *               <li>精确，极小的误差：{@link Robot#SIM_ACCURATE}
	 *               <li>模糊，有误差，模糊搜索：{@link Robot#SIM_BLUR}
	 *               <li>非常模糊，误差很大：{@link Robot#SIM_BLUR_VERY}
	 *               </ul>
	 * @return true:成功
	 */
	public static boolean colorCompare(Color color1, Color color2, int sim) {
		if (Math.abs(color1.getRed() - color2.getRed()) <= sim && Math.abs(color1.getGreen() - color2.getGreen()) <= sim
				&& Math.abs(color1.getBlue() - color2.getBlue()) <= sim) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 将颜色值int型转换为RGB类型，三原色数值单独分开
	 * 
	 * @param value 十进制的图像颜色，FFFFFF颜色转成10进制便是这个传入值
	 * @return {@link RGBBean}
	 */
	public static Color intToColor(int value) {
		Color color = new Color(value);
		return color;
	}

}
