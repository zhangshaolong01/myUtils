package com.zhang.utils;

public class Result<T> {
	
	/* 提示信息 */
	private String result;
	
	/* 具体的内容 */
	private T data;
	
	private int totalrows;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(int totalrows) {
		this.totalrows = totalrows;
	}
	
}