package com.scp.producerconsumer;

import java.util.ArrayList;
import java.util.Random;

public class ProducerConsumerList {

	public static void main(String[] args) {
		
		int maxSize = 10;
		ArrayList al = new ArrayList(maxSize);
		
		Producer p1 = new Producer(al, maxSize, "Producer1");
		Producer p2 = new Producer(al, maxSize, "Producer2");
		
		Consumer c1 = new Consumer(al, maxSize, "Consumer1");
		Consumer c2 = new Consumer(al, maxSize, "Consumer2");
		
		p1.start();
		p2.start();
		c1.start();
		c2.start();
	}
}

class Producer extends Thread{
	
	ArrayList al;
	int maxSize;
	String name;
	
	public Producer(ArrayList al, int maxSize, String name) {
		super(name);
		this.al = al;
		this.maxSize = maxSize;
	}
	
	public void run(){
		System.out.println("Inside Producer");
		int x=0;
		while(x<10){
			synchronized (al) {
				System.out.println(Thread.currentThread());
				if(al.size()==maxSize){
					try {
						System.out.println("List is Full....\n Wait for Consumer to Consume....");
						al.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				//Random rno = new Random();
				int rno =new Random().nextInt(50);
				al.add(rno);
				System.out.println("Produced : "+rno);
				al.notify();
			}
			x++;
		}		
	}	
}

class Consumer extends Thread{
	
	ArrayList al;
	int maxSize;
	String name;
	
	public Consumer(ArrayList al, int maxSize, String name) {
		super(name);
		this.al = al;
		this.maxSize = maxSize;
	}
	
	public void run(){
		System.out.println("Inside Consumer");
		int x=0;
		while(x<10){
			synchronized (al) {
				System.out.println(Thread.currentThread());
				if(al.isEmpty()){
					try {
						System.out.println("List is Empty.... \n Wait for Producer to Produce....");
						al.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for(int i=1; i<al.size(); i++){
					System.out.println("Consumed : "+al.remove(i));
				}
				al.notify();
			}
			x++;
		}	
	}	
}