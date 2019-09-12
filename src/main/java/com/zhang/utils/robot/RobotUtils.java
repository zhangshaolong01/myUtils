package com.zhang.utils.robot;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomUtils;

import com.zhang.utils.scvzerng.Application;
import com.zhang.utils.scvzerng.ImageFinder;
import com.zhang.utils.scvzerng.ScreenImageFinder;
import com.zhang.utils.scvzerng.entity.Coordinate;

/**
 * wow 助手
 * 
 * @author zhangshaolong
 *
 */
public class RobotUtils {
	static Logger log = Logger.getLogger(RobotUtils.class.getName());
	public static final String HYPHENS_DATE = "yyyy-MM-dd";
	public static final String FORMAT = "jpg";
	public static int WIDTH = 0;
	public static int HEIGHT = 0;
	private static Robot robot;

	private RobotUtils() {
		
	}

	static Robot getInstance() throws AWTException {
		if (robot == null) {
			/**
			 * 屏幕大小
			 */
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			WIDTH  = dimension.width;
			HEIGHT = dimension.height;
			
			// 构造机器人对象
			robot = new Robot();
		}
		return robot;
	}

	/**
	 * 滚宠物 向前一步 向后一步 对应宠物字符串的自动化操作
	 * 
	 * @throws AWTException
	 */
	static void setPetAnimal() throws AWTException {
		// 初始化root
		log.info("初始化Robot");
		RobotUtils.getInstance();
		// 守护线程
		log.info("启动守护线程");
		ThreadPetAnimal thread = new ThreadPetAnimal();
		thread.setDaemon(true);
		thread.start();
		// 睡眠一段时间/毫秒
		robot.delay(3000);

		boolean flag = true;
		int i = 1;
		while (true) {
			if (isInArea(WIDTH - 500 , HEIGHT - 350, WIDTH, HEIGHT)) {
				flag = flag ? false : true;
				if (flag) {
					// 前滚一步
					robot.mouseWheel(1);
				} else {
					// 后滚一步
					robot.mouseWheel(-1);
					System.out.println(i++);
				}
			}
			robot.delay(RandomUtils.nextInt(50, 80));
		}
	}

	/**
	 * 钓鱼
	 * 
	 * @throws Exception
	 */
	static void setFishing() throws Exception {
		// 睡眠一段时间/毫秒
		robot.delay(3000);
		while (true) {
			if (isInArea(0, 0, 1000, 1000)) {
				// 点击选择
				System.out.println("点击选择");
				mousePressLeft();

				// 前滚一步,甩鱼竿
				System.out.println("甩鱼竿");
				robot.mouseWheel(1);
				// 停一秒,等待动画
				robot.delay(RandomUtils.nextInt(1000, 1500));

				// 是否有鱼上钩
				// 查找鱼漂位置
				System.out.println("查找鱼漂位置");
				ImageFinder finder = ScreenImageFinder.getFinder();
				BufferedImage search = ImageIO.read(Application.class.getClassLoader().getResourceAsStream("qq.png"));
				List<Coordinate> coordinateList = finder.match(search, 0.99);
				// coordinateList.stream().findAny().ifPresent(coordinate ->
				// System.out.println(String.format("find help image x:%d,y:%d",
				// coordinate.getX(), coordinate.getY())));
				if (coordinateList.size() > 0) {
					Coordinate coordinate = coordinateList.get(0);
					// 移到鱼鳔位置
					System.out.println("移到鱼鳔位置:" + coordinate.getX() + "--" + coordinate.getY());
					mouseMove(coordinate.getX(), coordinate.getY());
					// 比较鱼漂变化
					System.out.println("比较鱼漂变化:");
					boolean isOk = compareCork(coordinate.getX(), coordinate.getY());
					if (isOk) {
						// 右键点击自动拾取
						System.out.println("点击自动拾取:");
						mousePressLeft();
					}
				}
			}
			robot.delay(RandomUtils.nextInt(2000, 3000));
		}
	}

	/**
	 * 鱼漂比较
	 * 
	 * @return
	 * @throws Exception
	 */
	static boolean compareCork(int x, int y) throws Exception {
		BufferedImage firstImage = null;
		;
		while (true) {
			BufferedImage captureScreen = captureScreen(x - 50, y - 50, x + 50, y + 50);
			if (firstImage != null) {
				long before = System.currentTimeMillis();
				double similarity = ImgSimilarity.getSimilarity(firstImage, captureScreen);
				long after = System.currentTimeMillis();
				System.out.println("图片比较时间: " + (after - before) / 1000);
				if (similarity > 98) {
					return true;
				}
			} else {
				firstImage = captureScreen;
			}
			// 等待间隔时间
			robot.delay(500);
		}
	}

	/**
	 * 保存图片到本地
	 * 
	 * @throws IOException
	 */
	static void saveImage(BufferedImage image, String path) throws IOException {
		File f = new File(path);
		// 将image对象写入图像文件
		ImageIO.write(image, FORMAT, f);
	}

	/**
	 * 休眠
	 * 
	 * @param tiem
	 */
	static void delay(int tiem) {
		robot.delay(tiem);
	}

	/**
	 * 按左键
	 */
	static void mousePressLeft() {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(200);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	/**
	 * 按右键
	 */
	static void mousePressRight() {
		robot.mousePress(InputEvent.BUTTON3_MASK);
		robot.delay(200);
		robot.mouseRelease(InputEvent.BUTTON3_MASK);
	}

	/**
	 * 移动到指定位置
	 * 
	 * @param x
	 * @param y
	 */
	static void mouseMove(int x, int y) {
		// 移动鼠标到指定位置(X,Y)
		robot.mouseMove(x, y);
		log.info("移动鼠标至 -->" + x + " : " + y);
	}

	/**
	 * 返回鼠标上的按钮数。
	 * 
	 * @return
	 */
	static int getMouseNumber() {
		return MouseInfo.getNumberOfButtons();
	}

	/**
	 * 鼠标是否在指定区域
	 * 
	 * @param x      横坐标
	 * @param y      纵坐标
	 * @param width  宽
	 * @param height 高
	 * @return
	 */
	static boolean isInArea(int x, int y, int width, int height) {
		PointerInfo pointerInfo = MouseInfo.getPointerInfo();
		Point point = pointerInfo.getLocation();
		if (x < point.x && point.x < (x + width) && y < point.y && point.y < (y + height)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 截图
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage captureScreen(int x, int y, int width, int height) throws Exception {
		Rectangle screenRectangle = new Rectangle(x, y, width, height);
		return robot.createScreenCapture(screenRectangle);
	}
}
