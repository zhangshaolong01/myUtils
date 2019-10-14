package com.zhang.utils.imagescreen;

/**
 * 线程控制
 * 
 * @author jianggujin
 *
 */
public class ThreadCtrlDemo extends Thread {
	private final int STOP = -1;
	private final int SUSPEND = 0;
	private final int RUNNING = 1;
	private int status = 1;
	private long count = 0;

	public ThreadCtrlDemo(String name) {
		super(name);
	}

	@Override
	public synchronized void run() {
		// 判断是否停止
		while (status != STOP) {
			// 判断是否挂起
			if (status == SUSPEND) {
				try {
					// 若线程挂起则阻塞自己
					wait();
				} catch (InterruptedException e) {
					System.out.println("线程异常终止...");
				}
			} else {
				count++;
				System.out.println(this.getName() + "第" + count + "次运行...");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println("线程异常终止...");
				}
			}
		}
	}

	/**
	 * 恢复
	 */
	public synchronized void myResume() {
		// 修改状态
		status = RUNNING;
		// 唤醒
		notifyAll();
	}

	/**
	 * 挂起
	 */
	public void mySuspend() {
		// 修改状态
		status = SUSPEND;
	}

	/**
	 * 停止
	 */
	public void myStop() {
		// 修改状态
		status = STOP;
	}

	public static void main(String[] args) {
		ThreadCtrlDemo demo = new ThreadCtrlDemo("测试线程");
		demo.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("主线程异常终止...");
		}
		System.out.println("测试线程即将被挂起...");
		demo.mySuspend();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("主线程异常终止...");
		}
		System.out.println("测试线程即将被唤醒...");
		demo.myResume();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("主线程异常终止...");
		}
		System.out.println("终止测试线程...");
		demo.myStop();
		System.out.println("主线程终止...");
	}
}