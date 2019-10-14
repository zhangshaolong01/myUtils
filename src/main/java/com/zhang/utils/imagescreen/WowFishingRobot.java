package com.zhang.utils.imagescreen;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class WowFishingRobot extends Robot implements Runnable {

	private static WowFishingRobot instance;

	private WowFishingRobot() throws Exception {
		super();
	}

	private WowFishingRobot(String color, int sx, int sy, int wide, int high) throws Exception {
		super();
		this.sx = sx;
		this.sy = sy;
		this.wide = wide;
		this.high = high;
		this.sear_color = color;
	}

	public static WowFishingRobot getInstance() throws Exception {
		if (instance == null) {
			instance = new WowFishingRobot();
		}
		return instance;
	}

	// 是否退出系統
	public boolean flag = false;
	// 當前鼠標位置
	private int x = 0;
	private int y = 0;

	// 搜索區域
	private int sx = 0;
	private int sy = 0;
	private int wide = 1366;
	private int high = 768;

	/**
	 * 要搜索的颜色值
	 */
	public String sear_color = "FF9F9F";

	/**
	 * @Fields SIM : 搜索模糊度参数 非常精确，精确无误 0 ;精确 31; 模糊 61; 非常模糊 81
	 */
	public final static int SIM = 0;

	@Override
	public void run() {
		System.out.println("开始启动.........");
		Random random = new Random();
		int random_int = 0;
		// 设置Robot产生一个动作后的休眠时间,否则执行过快
		setAutoDelay(1000);
		while (true) {
			BufferedImage searchImage = createScreenCapture(new Rectangle(sx, sy, wide, high));
			random_int = random.nextInt(500);
			// 按键Q
			System.out.println("休眠2秒.........");
			delay(2000 + random_int);
			System.out.println("按下Q键.........");
			press(KeyEvent.VK_Q);
			System.out.println("休眠1秒.........");
			delay(1000 + random_int);

			CoordBean colorSearch1 = new CoordBean(0, 0);
			CoordBean colorSearch2;
			int x = 0;
			int y = 0;
			while (true) {
				colorSearch2 = colorSearch(sear_color, searchImage);
				if (colorSearch2 != null) {
					int x2 = colorSearch2.getX();
					int y2 = colorSearch2.getY();
					if (colorSearch1.getX() == 0 && colorSearch1.getY() == 0) {
						colorSearch1.setX(x2);
						colorSearch1.setY(y2);
						mouseMove(x2, y2);
						x = x2 - 50;
						y = y2 - 50;
					} else {
						colorSearch2.setX(x2 + x);
						colorSearch2.setY(y2 + y);
						double distance = getDistance(colorSearch1, colorSearch2);
						System.out.println(distance);
						if (distance > 20) {
							this.x = x2 + x;
							this.y = y2 + y;
							// 右键
							System.out.println("鼠标左键");
							mouseMove(this.x, this.y);
							mouseClick(InputEvent.BUTTON1_MASK);
							break;
						}
					}
					searchImage = createScreenCapture(new Rectangle(x, y, 100, 100));
				} else {
					System.out.println("没有找到");
					break;
				}
				delay(500);
			}
			delay(5000 + random_int);
			System.out.println("-----单次结束-----");
			if (flag) {
				System.out.println("退出循环");
				break;
			}
		}
		System.out.println("结束.........");
	}

	public static void main(String[] args) {
		try {
			WowFishingRobot robot = new WowFishingRobot();
			// robot.run();
			// robot.mouseMove(1161, 573);
			long time1 = new Date().getTime();
			CoordBean colorSearch1 = new CoordBean(0, 0);
			CoordBean colorSearch2;
			int x = 0;
			int y = 0;
			BufferedImage searchImage = robot.createScreenCapture(new Rectangle(0, 0, 1366, 768));
			while (true) {
				long time11 = new Date().getTime();
				colorSearch2 = robot.colorSearch("FF9F9F", searchImage);
				if (colorSearch2 != null) {
					int x2 = colorSearch2.getX();
					int y2 = colorSearch2.getY();
					if (colorSearch1.getX() == 0 && colorSearch1.getY() == 0) {
						colorSearch1.setX(x2);
						colorSearch1.setY(y2);
						x = x2 - 50;
						y = y2 - 50;
					} else {
						colorSearch2.setX(x2 + colorSearch1.getX() - 50);
						colorSearch2.setY(y2 + colorSearch1.getY() - 50);
						double distance = robot.getDistance(colorSearch1, colorSearch2);
						System.out.println(distance);
						if (distance > 9) {
							robot.x = x2 + colorSearch1.getX() - 50;
							robot.y = y2 + colorSearch1.getY() - 50;
							break;
						}
					}
					searchImage = robot.createScreenCapture(new Rectangle(x, y, 100, 100));
				} else {
					System.out.println("没有找到");
					break;
				}
				long time12 = new Date().getTime();
				System.out.println(time12 - time11);
				robot.delay(1000);
			}
			robot.mouseMove(robot.x, robot.y);
			long time2 = new Date().getTime();
			System.out.println(time2 - time1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double getDistance(CoordBean p1, CoordBean p2) {
		double _x = Math.abs(p1.getX() - p2.getX());
		double _y = Math.abs(p1.getY() - p2.getY());
		return Math.sqrt(_x * _x + _y * _y);
	}

	/**
	 * 按键
	 * 
	 * @param key 如：{@link KeyEvent.VK_A}
	 */
	public void press(int key) {
		keyPress(key);
		delay(5);
		keyRelease(key);
	}

	/**
	 * 组合键，如SHIFT+1
	 * 
	 * @param key1 如：{@link KeyEvent#VK_SHIFT}
	 * @param key2 如：{@link KeyEvent#VK_1}
	 */
	public void press(int key1, int key2) {
		keyPress(key1);
		delay(5);
		keyPress(key2);
		keyRelease(key1);
		keyRelease(key2);
	}

	/**
	 * 鼠标点击
	 * 
	 * @param x       点击的当前屏幕的x坐标
	 * @param y       点击的当前屏幕的y坐标
	 * @param buttons 何种方式点击，是左还是右，同 {@link java.awt.Robot#mousePress(int)}的参数
	 */
	public void mouseClick(int buttons) {
		delay(20);
		mousePress(buttons);
		delay(20);
		mouseRelease(buttons);
	}

	/**
	 * @Description: 搜索指定颜色的坐标 @return: List<CoordBean> @throws
	 */
	public List<CoordBean> colorSearch(String pxSource) {
		List<CoordBean> list = new ArrayList<CoordBean>();
		BufferedImage searchImage = createScreenCapture(new Rectangle(sx, sy, wide, high));
		RgbImageComparerBean pxSearch = getPX(searchImage);

		int[][] px = pxSearch.getColorArray(); // 要搜索的目标图的像素点
		for (int x = 0; x < pxSearch.getImgWidth(); x++) {
			for (int y = 0; y < pxSearch.getImgHeight(); y++) {
				if (colorCompare(pxSource, px[x][y], SIM)) {
					CoordBean coord = new CoordBean();
					coord.setX(x);
					coord.setY(y);
					list.add(coord);
				}
			}
		}
		return list;
	}

	/**
	 * @Description: 搜索指定颜色的坐标 @return: List<CoordBean> @throws
	 */
	public CoordBean colorSearch(String pxSource, BufferedImage searchImage) {
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
	 * 将图片改变成像素数据，同时获取搜索图片时的图片相关参数
	 * 
	 * @return {@link RgbImageComparerBean}
	 */
	public RgbImageComparerBean getPX(BufferedImage bufferedImage) {
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		int minx = bufferedImage.getMinX();
		int miny = bufferedImage.getMinY();

		RgbImageComparerBean rgb = new RgbImageComparerBean();
		int colorArray[][] = new int[width][height];
		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
				colorArray[i][j] = bufferedImage.getRGB(i, j);
			}
		}
		rgb.setColorArray(colorArray);
		return rgb;
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
	public boolean colorCompare(Color color1, Color color2, int sim) {
		if (Math.abs(color1.getRed() - color2.getRed()) <= sim && Math.abs(color1.getGreen() - color2.getGreen()) <= sim
				&& Math.abs(color1.getBlue() - color2.getBlue()) <= sim) {
			return true;
		} else {
			return false;
		}
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
	public boolean colorCompare(String pxSourceSt, int pxSearch, int sim) {
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
	 * 将颜色值int型转换为RGB类型，三原色数值单独分开
	 * 
	 * @param value 十进制的图像颜色，FFFFFF颜色转成10进制便是这个传入值
	 * @return {@link RGBBean}
	 */
	public Color intToColor(int value) {
		Color color = new Color(value);
		return color;
	}

}
