package com.zhang.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class AcceptMethod {
	public static void printValur(String str) {
		System.out.println("print value : " + str);
	}

	public static void main(String[] args) {
		List<String> al = Arrays.asList("a", "b", "c", "d");
		//al.forEach(AcceptMethod::printValur);
		
		// 下面的方法和上面等价的
		Consumer<String> methodParam = AcceptMethod::printValur; // 方法参数
		al.forEach(x -> methodParam.accept(x));// 方法执行accept
	}
}
