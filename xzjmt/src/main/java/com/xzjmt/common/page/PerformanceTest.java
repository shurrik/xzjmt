package com.xzjmt.common.page;

public class PerformanceTest {
	private long beginTime = System.currentTimeMillis();
	private long lastTime;
	public PerformanceTest(){
		lastTime = beginTime;
	}
	public void printTime(String part){
		long thisTime = System.currentTimeMillis();
//		System.out.println(part+"耗时:"+(thisTime-lastTime)+"ms.");
		lastTime = thisTime;
	}
	public void printTotalTime(String total){
		total = total==null?"":total;
		long thisTime = System.currentTimeMillis();
//		System.out.println(total+"总耗时:"+(thisTime-beginTime)+"ms.");
		lastTime = thisTime;
	}
	
	public long getTime() {
		long thisTime = System.currentTimeMillis();
		long curTime = thisTime - lastTime;
		lastTime = thisTime;
		
		return curTime;
	}
	
	public long getTotalTime() {
		long thisTime = System.currentTimeMillis();
		long totalTime = thisTime - beginTime;
		lastTime = thisTime;
		return totalTime;
	}
}
