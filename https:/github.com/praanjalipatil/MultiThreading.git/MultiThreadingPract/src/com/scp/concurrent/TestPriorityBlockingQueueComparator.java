package com.scp.concurrent;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class TestPriorityBlockingQueueComparator {

	public static void main(String[] args) {
		
		BlockingQueue<Student> pbQueue = new PriorityBlockingQueue<>(5, new StudIdSort());
		
		ProducerPriority1 p1 = new ProducerPriority1(pbQueue, "Producer");
		ConsumerPriority1 c1 = new ConsumerPriority1(pbQueue, "Consumer");
		
		p1.start();
		c1.start();
	}
}

class ProducerPriority1 extends Thread{
	
	BlockingQueue<Student> pbQueue;
	String tname;
	
	public ProducerPriority1(BlockingQueue<Student> pbQueue, String tname) {
		super(tname);
		this.pbQueue = pbQueue;
	}	
	
	public void run(){
		int id=1;
		while(true){
			
			try {
				Student s1 = new Student(id, "abc");
				System.out.println("\nProduced : " + s1);
				pbQueue.put(s1);									
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			id++;
			
			System.out.println("\nProducer Queue"+pbQueue);

		}
		
	}	
}

class ConsumerPriority1 extends Thread{
	
	BlockingQueue<Student> pbQueue;
	String tname;
	
	public ConsumerPriority1(BlockingQueue<Student> pbQueue, String tname) {
		super(tname);
		this.pbQueue = pbQueue;
	}	
	
	public void run(){
		
		while(true){
			
			System.out.println("\nConsumer Queue"+pbQueue);

			try {
				Student s2 = pbQueue.take();
				System.out.println("Consumed : " + s2);		
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
			
		}		
	}	
}

class Student{
	
	int studId;
	String studName;

	public Student(int studId, String studName) {
		this.studId = studId;
		this.studName = studName;
	}

	@Override
	public String toString() {
		return "[studId=" + studId + ", studName=" + studName + "]";
	}
}

class StudIdSort implements Comparator<Student>{
	
	@Override
	public int compare(Student o1, Student o2) {
		return o1.studId-o2.studId;
	}
}
