package com.scp.concurrent;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

public class TestTransferQueue {

	public static void main(String[] args) {
		
		TransferQueue<Integer> tQueue = new LinkedTransferQueue<>();
		ProducerTransfer p1 = new ProducerTransfer(tQueue, "Producer");
		ConsumerTransfer c1 = new ConsumerTransfer(tQueue, "Consumer");
		p1.start();
		c1.start();
		
	}
}

class ProducerTransfer extends Thread{
	
	TransferQueue<Integer> tQueue;
	String tname;
	
	public ProducerTransfer(TransferQueue<Integer> tQueue, String tname) {
		super(tname);
		this.tQueue = tQueue;
	}
	
	public void run(){
		
		while(true){
			
			int randomNo = ThreadLocalRandom.current().nextInt(1,50);
			
			try {
				TimeUnit.SECONDS.sleep(1);
				System.out.println("\nProducing : "+randomNo);
				System.out.println("Acknowledgement : " + tQueue.tryTransfer(randomNo));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}

class ConsumerTransfer extends Thread{
	
	TransferQueue<Integer> tQueue;
	String tname;
	
	public ConsumerTransfer(TransferQueue<Integer> tQueue, String tname) {
		super(tname);
		this.tQueue = tQueue;
	}
	
	public void run(){
		
		while(true){
			try {
				TimeUnit.SECONDS.sleep(1);
				int randomNo = tQueue.take();
				System.out.println("Consumed : "+randomNo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
}
