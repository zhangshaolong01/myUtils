package com.zhang.utils.file;

public class MultiThreadDown {
	public static void main(String[] args){
		// 初始化DownUtil对象
		final DownUtil downUtil = new DownUtil(
				//"http://ftp.yz.yamagata-u.ac.jp/pub/eclipse/technology/epp/downloads/RELEASE/2019-06/R/eclipse-jee-2019-06-R-win32-x86_64.zip",
				"https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/2019-06/R/eclipse-jee-2019-06-R-win32-x86_64.zip",
				"eclipse-jee-2019-06-R-win32-x86_64.zip", 1);
		// 开始下载
		try {
			downUtil.download();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		new Thread(() -> {
			while (downUtil.getCompleteRate() < 1) {
				// 每隔0.1秒查询一次任务的完成进度
				// GUI程序中可根据该进度来绘制进度条
				System.out.println("已完成：" + downUtil.getCompleteRate());
				try {
					Thread.sleep(100);
				} catch (Exception ex) {
				}

			}
		}).start();
	}
}