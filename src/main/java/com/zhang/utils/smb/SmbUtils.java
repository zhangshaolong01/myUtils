package com.zhang.utils.smb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

public class SmbUtils {
	  public static void main(String[] args) {
	    	try {
				smbRead();
				//smbPut("", "");
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }

		private static void smbRead() throws MalformedURLException {
			// 主要利用类 SmbFile 去实现读取共享文件夹 shareFile 下的所有文件(文件夹)的名称
			String URL = "smb://zsl:123456789@192.168.7.252/hecsp/";
			SmbFile smbfile = new SmbFile(URL);
			try {
				if (!smbfile.exists()) {
					System.out.println("no such folder");
				} else {
					SmbFile[] files = smbfile.listFiles();
					for (SmbFile f : files) {
						System.out.println(f.getName());
					}
				}
			} catch (SmbException e) {
				e.printStackTrace();
			}
		}
		
		
		// 向共享目录上传文件
		public static void smbPut(String remoteUrl, String localFilePath){
			//String url_local="E://eclipse/mars0109/wooh-omp/src/main/resources/IOS/template.plist";
			String url_local="C:\\Users\\zhangshaolong\\Desktop\\服务员.apk";
			//String URL_remote="smb://smb:12345678@47.95.226.253:445/gs/服务员.apk";
			String URL_remote="smb://zsl:123456789@192.168.7.252/hecsp/服务员1.apk";
			InputStream ins = null;
	        OutputStream outs = null;
			try {
				File localfile=new File(url_local);
				ins = new FileInputStream(localfile);
				SmbFile smbfile=new SmbFile(URL_remote);
				if(smbfile.exists()){
					System.out.println("file is exists");
				}else{
					smbfile.connect();
					outs = new SmbFileOutputStream(smbfile);
					byte[] buffer = new byte[4096];
					int len = 0; //读取长度? 
					while ((len = ins.read(buffer, 0, buffer.length)) != -1) {
							outs.write(buffer, 0, len);
					}
					outs.flush(); //刷新缓冲的输出流? ?
					System.out.println("写入成功");
				}
			} catch (IOException e) {
				 e.printStackTrace();
			}finally {
	            try {
	                if (outs != null) {
	                	outs.close();
	                }
	                if (ins != null) {
	                	ins.close();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
		}
}
