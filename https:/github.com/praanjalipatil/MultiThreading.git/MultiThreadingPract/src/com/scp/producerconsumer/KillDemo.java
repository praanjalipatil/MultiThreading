package com.scp.producerconsumer;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class KillDemo {

	public static void main(String[] args) {
		
		KillClass k1 = new KillClass("abc");
		KillClass k2 = new KillClass("xyz");
		k1.start();
		k2.start();
	}

}

class KillClass extends Thread{
	
	
	volatile boolean flag = true;
	String str;
	volatile int count = 1;
		
	public KillClass(String str) {
		super(str);
	}
	
	public void kill(){
		flag = false;
		System.out.println("Killed Thread : "+ Thread.currentThread().getName());
	}

	public void run(){
				
		while(flag){
			
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(Thread.currentThread().getName().equals("abc")){
				if(count>=5){
					System.out.println("Killing");
					kill();
				}
			}
			
			count++;
			
			System.out.println("Produced : " + ThreadLocalRandom.current().nextInt(1,10));
			
		}
		
	}
}
