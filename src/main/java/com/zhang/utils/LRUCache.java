
package com.zhang.utils;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 缓存
 * 
 * @author zhangshaolong
 */
public class LRUCache<V>{

	private LinkedList<V> linkedList;
	
	/**
	 * 容量
	 */
	private int max;

	/**
	 * 互斥锁
	 */
	private final Lock lock = new ReentrantLock();
	
	public LRUCache(int max) {
		if(linkedList == null) {
			linkedList = new LinkedList<V>();
		}
		this.max = max;
	}

	/**
	 * 添加
	 *@title: addToFirst  
	 *@author: zhangshaolong
	 *@date: 2019年11月1日 上午10:48:54
	 *@param v
	 *@return
	 *@throws:
	 */
	public boolean add(V v) {
		try {
			lock.lock();
			Boolean fail = false;
			if(linkedList.contains(v)) {
				linkedList.remove(v);
				linkedList.addFirst(v);
				fail = true;
			}else {
				if (size() < max)
					linkedList.addFirst(v);
				else {
					linkedList.removeLast();
					linkedList.addFirst(v);
				}
			}
			return fail;
		} finally {
			lock.unlock();
		}
	}
	

	public int size() {
		try {
			lock.lock();
			return linkedList.size();
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 打印列表 
	 *@title: printList  
	 *@author: zhangshaolong
	 *@date: 2019年11月1日 上午10:50:46
	 *@throws:
	 */
	public void printList() {
		try {
			lock.lock();
			for (V v : linkedList) {
				System.out.print(v + " ");
			}
		} finally {
			lock.unlock();
		}
	}
}
