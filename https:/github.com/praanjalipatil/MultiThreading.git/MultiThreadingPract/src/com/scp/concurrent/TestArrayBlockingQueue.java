package com.scp.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class TestArrayBlockingQueue {

	public static void main(String[] args) {

		BlockingQueue<Integer> abQueue = new ArrayBlockingQueue<>(2);
		ProducerArray p1 = new ProducerArray(abQueue, "Producer");
		ConsumerArray c1 = new ConsumerArray(abQueue, "Consumer");
		
		p1.start();
		c1.start();
		
		System.out.println(Thread.currentThread().getName()+"Completed....");		
	}
}

class ProducerArray extends Thread{
	
	BlockingQueue<Integer> abQueue;
	String tname;
	
	public ProducerArray(BlockingQueue<Integer> abQueue, String tname) {
		super(tname);
		this.abQueue = abQueue;
	}	
	
	public void run(){
		
		while(true){
			
			System.out.println(abQueue);
			
			int randomNo = ThreadLocalRandom.current().nextInt(1,50);
			
			//abQueue.add(randomNo);
			//System.out.println("Produced : " + randomNo + Thread.currentThread().getName());
			
			try {
				TimeUnit.SECONDS.sleep(1);
				
				System.out.println(Thread.currentThread().getName() +"  "+ "Produced : " + randomNo);
				
				//abQueue.put(randomNo);
				
				//System.out.println(abQueue.offer(randomNo));
				
				System.out.println(abQueue.offer(randomNo, 1000, TimeUnit.SECONDS));
							
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	}
}

class ConsumerArray extends Thread{
	
	BlockingQueue<Integer> abQueue;
	String tname;
	
	public ConsumerArray(BlockingQueue<Integer> abQueue, String tname) {
		super(tname);
		this.abQueue = abQueue;
	}	
	
	public void run(){
		
		while(true){
			
			System.out.println(abQueue);
			
			//int randomNo = abQueue.remove();
			//System.out.println("Consumed : " + randomNo + Thread.currentThread().getName());

			try {
				TimeUnit.SECONDS.sleep(10);
				
				//int randomNo = abQueue.take();
				//System.out.println(Thread.currentThread().getName() +"  "+ "Consumed : " + randomNo);
				
				//int randomNo = abQueue.poll();
				//System.out.println(Thread.currentThread().getName() +"  "+ "Consumed : " + randomNo);
				
				int randomNo = abQueue.poll(5000, TimeUnit.SECONDS);
				System.out.println(Thread.currentThread().getName() +"  "+ "Consumed : " + randomNo);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
		
	}	
}
