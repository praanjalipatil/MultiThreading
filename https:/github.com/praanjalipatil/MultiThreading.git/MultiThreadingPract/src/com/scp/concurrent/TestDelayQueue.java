package com.scp.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TestDelayQueue {

	public static void main(String[] args) {

		BlockingQueue<Book> dQueue = new DelayQueue<>();
		ProducerDelay p1 = new ProducerDelay(dQueue, "Producer");
		ConsumerDelay c1 = new ConsumerDelay(dQueue, "Consumer");
		p1.start();
		c1.start();
		
	}
}

class ProducerDelay extends Thread{
	
	BlockingQueue<Book> dQueue;
	String tname;
	
	public ProducerDelay(BlockingQueue<Book> dQueue, String tname) {
		super(tname);
		this.dQueue = dQueue;
	}	
	
	public void run(){
		int id = 1;
		while(id<=5){
			try {
				Book b1 = new Book(id, "Book", 1000);
				dQueue.put(b1);
				System.out.println("\nProduced : "+b1);
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("\nProducer Queue : "+dQueue);
			id++;			
		}		
	}	
}

class ConsumerDelay extends Thread{
	
	BlockingQueue<Book> dQueue;
	String tname;
	
	public ConsumerDelay(BlockingQueue<Book> dQueue, String tname) {
		super(tname);
		this.dQueue = dQueue;
	}	
	
	public void run(){	
		int id = 1;		
		while(id<=5){			
			System.out.println("\nConsumer Queue : "+dQueue);
			
			try {
				//Book b2 = dQueue.take();
				System.out.println("Consumed : "+dQueue.take());
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			id++;
			
		}
		
	}
}

class Book implements Delayed{
	
	int bookId;
	String bookName;
	long startTime;
	
	public Book(int bookId, String bookName, long delay) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.startTime = System.currentTimeMillis()+delay;
	}

	@Override
	public String toString() {
		return "\n[bookId=" + bookId + ", bookName=" + bookName + ", startTime=" + startTime + "]";
	}

	@Override
	public int compareTo(Delayed o) {
		if( this.startTime < ((Book) o).startTime ){
			return -1;
		}
		if( this.startTime > ((Book) o).startTime ){
			return 1;
		}
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		long diff = startTime-System.currentTimeMillis();	
		return unit.convert(diff, TimeUnit.MILLISECONDS);
	}
	
}
