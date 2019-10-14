package com.zhang.utils.imagescreen;

import java.awt.event.KeyEvent;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

public class HotKey implements HotkeyListener{

	static WowFishingRobot wowFishing;
	static{
		try {
			wowFishing = WowFishingRobot.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    static final int KEY_1 = 88;  
    static final int KEY_2 = 89;
    static final int KEY_3 = 90;
  
    /** 
     * 该方法负责监听注册的系统热键事件 
     * 
     * @param key:触发的热键标识 
     */  
    public void onHotKey(int key) {
    	try{
    		 switch (key) {
             case KEY_1:  
            	 wowFishing.run();break;  
             case KEY_2:  
                 wowFishing.flag = true;
                 System.out.println("外部结束");
                 break;
             case KEY_3:  
            	 destroy(); break;
         } 
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }  
  
  
    /** 
     * 解除注册并退出 
     */  
    void destroy() {  
        JIntellitype.getInstance().unregisterHotKey(KEY_1);  
        JIntellitype.getInstance().unregisterHotKey(KEY_2);
        JIntellitype.getInstance().unregisterHotKey(KEY_3);
        System.exit(0);  
    }  
  
    /** qqqqqqq
     * 初q始化热键并注册监听事件 
     */  
    void initHotkey() {  
        //参数KEY_1表示改组热键组合的标识，第二个参数表示组合键，如果没有则为0，该热键对应ctrl+alt+I  
        JIntellitype.getInstance().registerHotKey(KEY_1, 0, KeyEvent.VK_F2);  
        JIntellitype.getInstance().registerHotKey(KEY_2, 0, KeyEvent.VK_F9);
        JIntellitype.getInstance().registerHotKey(KEY_3, 0, KeyEvent.VK_ESCAPE);  
        JIntellitype.getInstance().addHotKeyListener(this);
    }  
  
    public static void main(String[] args) throws Exception {
        HotKey key = new HotKey();  
        key.initHotkey();
        
        int read = System.in.read();
	   System.out.println(read);
	   synchronized (key) {
		   System.out.println("主线程+++++++++++");
		   key.wait(10000);
		   System.out.println("主线程+++++++++++");
		   wowFishing.wait(10000);
	    }

    } 
}
