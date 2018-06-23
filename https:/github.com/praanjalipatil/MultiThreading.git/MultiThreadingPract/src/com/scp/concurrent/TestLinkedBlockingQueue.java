package com.scp.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class TestLinkedBlockingQueue {

	public static void main(String[] args) {
		
		BlockingQueue<Integer> lbQueue = new LinkedBlockingQueue<>(5);
		
		ProducerLinkedList p1 = new ProducerLinkedList(lbQueue, "Producer");
		ConsumerLinkedList c1 = new ConsumerLinkedList(lbQueue, "Consumer");
		
		p1.start();
		c1.start();

	}

}

class ProducerLinkedList extends Thread{
	
	BlockingQueue<Integer> lbQueue;
	String tname;
	
	public ProducerLinkedList(BlockingQueue<Integer> lbQueue, String tname) {
		super(tname);
		this.lbQueue = lbQueue;
	}	
	
	public void run(){
		
		while(true){
			
			System.out.println(lbQueue);
			
			int randomNo = ThreadLocalRandom.current().nextInt(1,50);
			
			try {
				TimeUnit.SECONDS.sleep(1);
				System.out.println(Thread.currentThread().getName() +"  "+ "Produced : " + randomNo);
				System.out.println(lbQueue.offer(randomNo));										
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	}
}

class ConsumerLinkedList extends Thread{
	
	BlockingQueue<Integer> lbQueue;
	String tname;
	
	public ConsumerLinkedList(BlockingQueue<Integer> lbQueue, String tname) {
		super(tname);
		this.lbQueue = lbQueue;
	}	
	
	public void run(){
		
		while(true){
			
			System.out.println(lbQueue);

			try {
				TimeUnit.SECONDS.sleep(10);
				int randomNo = lbQueue.poll();
				System.out.println(Thread.currentThread().getName() +"  "+ "Consumed : " + randomNo);		
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
		
	}	
}

