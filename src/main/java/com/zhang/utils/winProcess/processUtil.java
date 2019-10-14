package com.zhang.utils.winProcess;

import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * 进程工具类，windows版
 *
 * @author LuLihong
 * @date 2014-8-13
 */
public class processUtil {
	private static final Logger log = Logger.getLogger(processUtil.class);

	/**
	 * 检测某进程是否在运行
	 * 
	 * @param proName
	 * @return
	 */
	public static boolean isRunning(String proName) {
		try {
			Process process = Runtime.getRuntime().exec("tasklist");
			Scanner in = new Scanner(process.getInputStream());
			while (in.hasNextLine()) {
				String p = in.nextLine();
				if (p.contains(proName)) {
					return true;
				}
			}
			in.close();
		} catch (IOException e) {
			log.error(String.format("Check process[%s] running error: " + e.getMessage(), proName));
		}

		return false;
	}
	
	/**
	 * 检测某进程网络是否已连接
	 * 
	 * @param proName
	 * @return
	 */
	public static boolean isNetworkConnection(int pid) {
		String pids = String.valueOf(pid);
		try {
			Process process = Runtime.getRuntime().exec("cmd.exe /k netstat -ano | findstr ESTABLISHED");
			Scanner in = new Scanner(process.getInputStream());
			while (in.hasNextLine()) {
				String p = in.nextLine();
				if (p.contains(pids)) {
					return true;
				}
			}
			in.close();
		} catch (IOException e) {
			log.error(String.format("Check process[%s] running error: " + e.getMessage(), pid));
		}

		return false;
	}

	/**
	 * 根据进程名寻找进程ID
	 * 
	 * @param proName
	 * @return 不存在，返回-1
	 */
	public static int findProcessId(String proName) {
		try {
			Process process = Runtime.getRuntime().exec("tasklist");
			Scanner in = new Scanner(process.getInputStream());
			while (in.hasNextLine()) {
				String p = in.nextLine();
				if (p.contains(proName)) {
					p = p.replaceAll("\\s+", ",");
					System.out.println(p);
					String[] arr = p.split(",");
					return Integer.valueOf(arr[1]);
				}
			}
			in.close();
		} catch (IOException e) {
			log.error(String.format("Find process[%s] id error: " + e.getMessage(), proName));
		}

		return -1;
	}

	/**
	 * 关闭某进程(根据PID)
	 * 
	 * @param pid
	 * @return
	 */
	public static boolean killProcess(int pid) {
		try {
			Runtime.getRuntime().exec("cmd.exe /c taskkill /f /pid " + pid);
		} catch (IOException e) {
			log.error(String.format("Kill process[id=%d] error: " + e.getMessage(), pid));
			return false;
		}

		return true;
	}

	/**
	 * 关闭进程(根据名称)
	 * 
	 * @param proName
	 * @return
	 */
	public static boolean killProcess(String proName) {
		int pid = findProcessId(proName);
		if (pid == -1)
			return true;
		return killProcess(pid);
	}

	/**
	 * 关闭某进程(根据名称)直到真正关闭
	 * 
	 * @param proName
	 * @return
	 */
	public static boolean killProcessBlock(String proName) {
		int pid = findProcessId(proName);
		if (pid == -1)
			return true;
		do {
			killProcess(pid);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		} while (isRunning(proName));

		return true;
	}

	/**
	 * 显示所有进程
	 */
	public static void listProcess() {
		try {
			Process process = Runtime.getRuntime().exec("tasklist");

			Scanner in = new Scanner(process.getInputStream());
			while (in.hasNextLine()) {
				System.out.println(in.nextLine());
			}
			in.close();
		} catch (IOException e) {
		}
	}

	public static void main(String[] args) {
		//netstat -ano | findstr ESTABLISHED
		//tasklist | findstr 1428
		
		long t1 = System.currentTimeMillis();
		//listProcess();// 显示所有进程
		
		String proName = "Wow.exe";
		int pid = findProcessId(proName);//根据进程名寻找进程ID
		
		boolean running = isNetworkConnection(pid);
		
		//boolean running = isRunning(proName); //检测某进程是否在运行
		//killProcess(pid); //关闭某进程(根据PID)
		
		//boolean b = killProcessBlock(proName); //关闭进程
		
		long t2 = System.currentTimeMillis();
		System.out.println("pid: " + running);
		System.out.println("Used time: " + (t2 - t1));
	}

}
