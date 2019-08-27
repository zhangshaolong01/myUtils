package com.utils.robot;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.apache.commons.lang3.RandomUtils;

public class RobotUtils {
	
	private static Robot robot;
	
	private RobotUtils(){
		
	}
	
	static Robot getInstance() throws AWTException {
		if(robot == null) {
			//构造机器人对象
			robot = new Robot();
		}
		return robot;
	}

	public static void main(String[] args) throws AWTException {
		setMouseWheel();
	}
	
	/**
	 * 滚轮动作
	 * 	向前一步
	 * 	向后一步
	 *          对应宠物字符串的自动化操作
	 * @throws AWTException 
	 */
	static void setMouseWheel() throws AWTException{
		getInstance();
		//睡眠一段时间/毫秒
		robot.delay(3000);
		
		boolean flag = true;
		while(true) {
			if(isInArea(1, 1, 2000, 2000)) {
				mousePressLeft();
				flag = flag?false:true;
				if(flag) {
					robot.mouseWheel(1);
				}else {
					robot.mouseWheel(-1);
				}
			}
			robot.delay(RandomUtils.nextInt(1500, 3000));
		}
	}
	
	
	/**
	 * 按左键
	 */
	static void mousePressLeft(){
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(200);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	/**
	 * 按右键
	 */
	static void mousePressRight(){
		robot.mousePress(InputEvent.BUTTON3_MASK);
		robot.delay(200);
		robot.mouseRelease(InputEvent.BUTTON3_MASK);
	}
	
	/**
	 * 移动到指定位置
	 * @param x
	 * @param y
	 */
	static void mouseMove(int x, int y) {
		//移动鼠标到指定位置(X,Y)
		robot.mouseMove(x, y);
	}
	
	
	/**
	 * 返回鼠标上的按钮数。
	 * @return
	 */
	static int getMouseNumber(){
		return MouseInfo.getNumberOfButtons();
	}
	
	
	/**
	 * 鼠标是否在指定区域
	 * @param x 横坐标
	 * @param y	纵坐标
	 * @param width	宽
	 * @param height 高
	 * @return
	 */
	static boolean isInArea(int x, int y, int width, int height){
		PointerInfo pointerInfo = MouseInfo.getPointerInfo();
		Point point = pointerInfo.getLocation();
		if(x < point.x && point.x < (x + width) && y < point.y && point.y < (y + height)) {
				System.out.println(true);
				return true;
		}else {
			System.out.println(false);
			return false;
		}
	}
}
