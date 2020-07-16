/**
 * <p>项目名称: myUtils </p> 
 * <p>文件名称: AutomaticAnswer.java </p> 
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2020年7月14日 </p>
 * <p>公司信息: ************公司 *********部</p> 
 * @author <a href="mail to: *******@******.com" rel="nofollow">作者</a>
 * @version v1.0
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
package com.zhang.utils.robot;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomUtils;

import com.xnx3.robot.support.CoordBean;
import com.zhang.utils.scvzerng.Application;
import com.zhang.utils.scvzerng.ImageFinder;
import com.zhang.utils.scvzerng.ScreenImageFinder;
import com.zhang.utils.scvzerng.entity.Coordinate;

/**
 * @author zhangshaolong
 *
 */
public class AutomaticAnswer {

	/**
	 *TODO
	 *@title: main  
	 *@author: zhangshaolong
	 *@date: 2020年7月14日 下午1:39:23
	 *@param args
	 *@throws: 
	 */
	public static void main(String[] args) {
		try {
			setAutomatic();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 钓鱼
	 * 
	 * @throws Exception
	 */
	static void setAutomatic() throws Exception {
		java.awt.Robot robot = new java.awt.Robot();
		BufferedImage search = ImageIO.read(Application.class.getClassLoader().getResourceAsStream("jt.png"));
		// 睡眠一段时间/毫秒
		robot.delay(3000);
		while (true) {
			if (isInArea(0, 0, 1920, 1080)) {
				// 查找图片位置181,37,25
				//BufferedImage cutImage =  robot.createScreenCapture(new Rectangle(0, 0, 1920, 1080));
				//writeImageFile(cutImage);
				//CoordBean colorSearch = ImageSearchUtils.colorSearch("B52519", cutImage);
				ImageFinder finder = ScreenImageFinder.getFinder();
				List<Coordinate> coordinateList = finder.match(search, 0.99);
				if (coordinateList.size() > 0) {
					Coordinate coordinate = coordinateList.get(0);
					// 移到鱼鳔位置
					System.out.println("移图片位置:" + coordinate.getX()+10 + "--" + coordinate.getY());
					mouseMove(robot,coordinate.getX()+10, coordinate.getY());
					
					// 右键点击自动拾取
					System.out.println("左键点击");
					mousePressLeft(robot);
				}
			}
			robot.delay(RandomUtils.nextInt(2000, 3000));
		}
	}
	
	/**
	 * 保存图片
	 *@title: writeImageFile  
	 *@author: zhangshaolong
	 *@date: 2020年7月14日 下午2:25:56
	 *@param bi
	 *@throws IOException
	 *@throws:
	 */
	public static void writeImageFile(BufferedImage bi) throws IOException {
        File outputfile = new File("saved.png");
        ImageIO.write(bi, "png", outputfile);
    }

	
	/**
	 * 按左键
	 */
	static void mousePressLeft(Robot robot) {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(200);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	/**
	 * 移动到指定位置
	 * 
	 * @param x
	 * @param y
	 */
	static void mouseMove(Robot robot,int x, int y) {
		// 移动鼠标到指定位置(X,Y)
		robot.mouseMove(x, y);
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

}
