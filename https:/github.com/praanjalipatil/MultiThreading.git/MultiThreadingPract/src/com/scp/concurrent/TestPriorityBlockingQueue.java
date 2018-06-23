package com.scp.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class TestPriorityBlockingQueue {

	public static void main(String[] args) {
		
		BlockingQueue<Employee> pbQueue = new PriorityBlockingQueue<>(5);
		
		ProducerPriority p1 = new ProducerPriority(pbQueue, "Producer");
		ConsumerPriority c1 = new ConsumerPriority(pbQueue, "Consumer");
		
		p1.start();
		c1.start();

	}

}

class ProducerPriority extends Thread{
	
	BlockingQueue<Employee> pbQueue;
	String tname;
	
	public ProducerPriority(BlockingQueue<Employee> pbQueue, String tname) {
		super(tname);
		this.pbQueue = pbQueue;
	}	
	
	public void run(){
		int id=1;
		while(true){
			
			try {
				Employee e1 = new Employee(id, "abc");
				System.out.println("\nProduced : " + e1);
				pbQueue.put(e1);									
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			id++;
			
			System.out.println("\nProducer Queue"+pbQueue);

		}
		
	}	
}

class ConsumerPriority extends Thread{
	
	BlockingQueue<Employee> pbQueue;
	String tname;
	
	public ConsumerPriority(BlockingQueue<Employee> pbQueue, String tname) {
		super(tname);
		this.pbQueue = pbQueue;
	}	
	
	public void run(){
		
		while(true){
			
			System.out.println("\nConsumer Queue"+pbQueue);

			try {
				Employee e2 = pbQueue.take();
				System.out.println("Consumed : " + e2);		
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
			
		}		
	}	
}

class Employee implements Comparable<Employee>{
	
	int empId;
	String empName;

	public Employee(int empId, String empName) {
		this.empId = empId;
		this.empName = empName;
	}

	@Override
	public String toString() {
		return "[empId=" + empId + ", empName=" + empName + "]";
	}

	@Override
	public int compareTo(Employee o) {
		return  this.empId-o.empId;
	}
	
}