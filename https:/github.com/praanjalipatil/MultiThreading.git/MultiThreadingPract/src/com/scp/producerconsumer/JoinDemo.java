package com.scp.producerconsumer;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class JoinDemo {

	public static void main(String[] args) throws InterruptedException {

		JoinClass j1 = new JoinClass("my thread ");
		j1.start();
		j1.join();
		
		for(int i=0; i<=5; i++){
			TimeUnit.SECONDS.sleep(1);
			System.out.println(Thread.currentThread().getName() + "Producing : " + ThreadLocalRandom.current().nextInt(30, 50));
		}
	}
}

class JoinClass extends Thread{
	
	int count =1;
	
	public JoinClass(String string) {
		super(string);
	}

	public void run(){
		while(count<=5){
			try{
				TimeUnit.SECONDS.sleep(1);
				System.out.println(Thread.currentThread().getName() + "Producing : " + ThreadLocalRandom.current().nextInt(1,20) );
			}catch(Exception e){
				
			}
			count++;
		}
		
	}
}