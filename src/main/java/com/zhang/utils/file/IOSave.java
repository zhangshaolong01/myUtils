package com.zhang.utils.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 字节数组流与文件字节流 1 选择源 2 选择流 3 搬运操作 4 关流
 */
public class IOSave {

	public static void main(String[] args) {
		// 根目录下的文件名
		//byte[] data = FileToByteArray("t.exe");
		// 自定义的拷贝的文件名字
		//ByteArrayToFile(data, "copy.exe");
		System.out.println("okk");
	}

	// 文件通过输入流写到字节数组，文件到字节数组的过程
	public static byte[] FileToByteArray(ByteArrayOutputStream bos, String fileName) {
		// 选择源
		File src = new File(fileName);
		// 选择流
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(src);
			int temp;
			// 用来暂时存放数据的，FileInputStream 的read方法会重复向里面读数据，
			// 接着通过ByteArrayOutputStream写，这是一个重复的过程。直到temp= -1 代表读完。然后return。
			byte[] bt = new byte[1024 * 10];
			while ((temp = fis.read(bt)) != -1) {
				bos.write(bt, 0, temp);
			}
			bos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关流
			try {
				if (null != fis)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bos.toByteArray();
	}

	// 字节数组到文件的过程
	public static void ByteArrayToFile(byte[] data, String newFileNmae) {
		// 选择源
		File file = new File(newFileNmae);
		// 选择流
		FileOutputStream fos = null;
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(data);
			fos = new FileOutputStream(file);
			int temp;
			byte[] bt = new byte[1024 * 10];
			while ((temp = bais.read(bt)) != -1) {
				fos.write(bt, 0, temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关流
			try {
				if (null != fos)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}