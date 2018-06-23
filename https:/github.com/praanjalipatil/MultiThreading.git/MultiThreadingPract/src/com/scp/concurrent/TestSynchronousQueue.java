package com.scp.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class TestSynchronousQueue {

	public static void main(String[] args) {

		BlockingQueue<Integer> sbQueue = new SynchronousQueue<>();
		ProducerSync p1 = new ProducerSync(sbQueue, "Producer");
		ConsumerSync c1 = new ConsumerSync(sbQueue, "Consumer");
		
		p1.start();
		c1.start();
		
		System.out.println(Thread.currentThread().getName()+"Completed....");		
	}
}

class ProducerSync extends Thread{
	
	BlockingQueue<Integer> sbQueue;
	String tname;
	
	public ProducerSync(BlockingQueue<Integer> sbQueue, String tname) {
		super(tname);
		this.sbQueue = sbQueue;
	}	
	
	public void run(){
		
		while(true){
			
			System.out.println(sbQueue);
			
			int randomNo = ThreadLocalRandom.current().nextInt(1,50);			
	
			try {
				TimeUnit.SECONDS.sleep(1);				
				System.out.println(Thread.currentThread().getName() +"  "+ "Produced : " + randomNo);				
				sbQueue.put(randomNo);											
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
}

class ConsumerSync extends Thread{
	
	BlockingQueue<Integer> sbQueue;
	String tname;
	
	public ConsumerSync(BlockingQueue<Integer> sbQueue, String tname) {
		super(tname);
		this.sbQueue = sbQueue;
	}	
	
	public void run(){
		
		while(true){
			
			System.out.println(sbQueue);
			
			try {
				TimeUnit.SECONDS.sleep(1);				
				int randomNo = sbQueue.take();
				System.out.println(Thread.currentThread().getName() +"  "+ "Consumed : " + randomNo);	
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}		
	}	
}
