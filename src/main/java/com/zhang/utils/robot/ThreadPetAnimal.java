package com.zhang.utils.robot;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.logging.Logger;

/**
 * 守护线程
 * @author zhangshaolong
 *
 */
public class ThreadPetAnimal extends Thread {
	static Logger log = Logger.getLogger(ThreadPetAnimal.class.getName());

	@Override
	public void run() {
		int x = 0, y = 0, time = 0;
		while (true) {
			PointerInfo pointerInfo = MouseInfo.getPointerInfo();
			Point point = pointerInfo.getLocation();
			if (x == point.x && y == point.y) {
				if(!RobotUtils.isInArea(RobotUtils.WIDTH - 500 , RobotUtils.HEIGHT - 350, RobotUtils.WIDTH, RobotUtils.HEIGHT)) {
					log.info("坐标:x=" + x + ",y=" + y + ",停留时间:" + time + "秒");
					if (time > 10) {
						RobotUtils.mouseMove(RobotUtils.WIDTH - 250, RobotUtils.HEIGHT - 150);
						//点击选择
						RobotUtils.mousePressLeft();
					}
				}
				time++;
			} else {
				time = 0;
				x = point.x;
				y = point.y;
			}
			RobotUtils.delay(1000);
		}
	}
}
