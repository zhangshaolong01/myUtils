package com.zhang.utils.robot;

import java.awt.AWTException;

/**
 * 启动类
 * @author zhangshaolong
 *
 */
public class RobotApplication {

	public static void main(String[] args) throws AWTException {
		
		// 宠物
		RobotUtils.setPetAnimal();
		
		// 守护线程
		//log.info("启动守护线程");
		//ThreadPetAnimal thread = new ThreadPetAnimal();
		//thread.start();
		
		// setFishing();
	}

}
