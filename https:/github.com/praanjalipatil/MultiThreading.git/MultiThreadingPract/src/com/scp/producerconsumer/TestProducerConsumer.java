package com.scp.producerconsumer;

public class TestProducerConsumer {
	
	public static void main(String[] args) {
		Test test = new Test();
		new Producer22(test);
		new Consumer22(test);
		
	}
}

class Producer22 implements Runnable{

	Test test;
	
	public Producer22(Test test) {
		this.test = test;
		Thread t = new Thread(this, "Producer");
		t.start();
	}

	public void run() {
		int i=0;
		while(true){
			test.setNum(i++);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}	
}

class Consumer22 implements Runnable{

	Test test;
	
	public Consumer22(Test test) {
		this.test = test;
		Thread t = new Thread(this, "Consumer");
		t.start();
	}

	public void run() {
		while(true){
			test.getNum();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
}

class Test{
	int num;
	boolean flag = false;
	
	public synchronized void setNum(int num) {
		while(flag){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Produced : "+num);
		this.num = num;
		flag = true;
		notify();
	}	

	public synchronized void getNum() {
		while(!flag){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Consumed : "+num);
		flag = false;
		notify();
	}	
}
