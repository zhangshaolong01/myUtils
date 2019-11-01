package com.zhang.utils;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author zhangshaolong
 *
 */
public class LruTest implements ActionListener{

	Frame f;
	TextField tf1,tf2,tf3;;
	Button bt1,bt2;

	public LruTest() {
		f = new Frame("求最大公约数");
		tf1 = new TextField(10);
		tf2 = new TextField(10);
		tf3 = new TextField(400);
		bt1 = new Button("运行");
		bt2 = new Button("关闭");
		
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		
		f.add(tf1);
		f.add(tf2);
		
		f.add(bt1);
		f.add(bt2);
		f.add(tf3);
		
		f.setLayout(new FlowLayout());
		f.setSize(400, 500);
		//窗口界面不可以放大缩小
        f.setResizable(false);
		f.setVisible(true);
	}
	


	@Override
	public void actionPerformed(ActionEvent a) {
		if(a.getSource() == bt1) {
			int a1 = Integer.parseInt(tf1.getText());
			int a2 = Integer.parseInt(tf2.getText());
			int a3 = 0;
			if(a1>=a2) {
				for (int i = a2; i > 0; i--) {
					if(a1%i == 0 && a2%i == 0) {
						a3 = i;
						break;
					}
				}
			}else {
				for (int i = a1; i > 0; i--) {
					if(a1%i == 0 && a2%i == 0) {
						a3 = i;
						break;
					}
				}
			}
		}else {
			System.exit(0);
		}
		
	}
	
	public static void main(String[] args) {
		new LruTest();
	}
	
	
}
