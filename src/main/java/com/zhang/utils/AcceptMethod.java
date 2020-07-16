package com.zhang.utils;

public class AcceptMethod {

	public static void printValur(String str) {
		System.out.println("print value : " + str);
	}

	public static void main(String[] args) {
		//List<String> al = Arrays.asList("a", "b", "c", "d");
		//al.forEach(AcceptMethod::printValur);
		Integer f1 = 100, f2 = 100, f3 = 150, f4 = 150;
	    
		String a = "b";
		String b = new String("b");
		 
        System.out.println(f1 == f2);
        System.out.println(f3 == f4);
        System.out.println(a == b);

		// 下面的方法和上面等价的
		//Consumer<String> methodParam = AcceptMethod::printValur; // 方法参数
		//al.forEach(x -> methodParam.accept(x));// 方法执行accept
	}
}
