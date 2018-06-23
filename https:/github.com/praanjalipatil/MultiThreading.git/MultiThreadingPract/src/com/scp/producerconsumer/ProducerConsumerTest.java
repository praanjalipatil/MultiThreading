package com.scp.producerconsumer;

import java.util.ArrayList;
import java.util.Random;

public class ProducerConsumerTest {

	public static void main(String[] args) {

		int max = 5;

		ArrayList al = new ArrayList(max);

		Producer2 p = new Producer2(al, max, "Producer");
		Consumer2 c = new Consumer2(al, max, "Consumer");
		p.start();
		c.start();
	}

}

class Producer2 extends Thread {

	ArrayList al;
	int max;
	String name;
	boolean flag = false;

	public Producer2(ArrayList al, int max, String name) {
		super(name);
		this.al = al;
		this.max = max;
	}

	public void run() {

		while (true) {
			synchronized (al) {

				if (al.size() == max) {
					while (flag) {
						try {
							al.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				int rno = new Random().nextInt(50);
				al.add(rno);
				System.out.println("Produced : " + rno);
				flag = true;
				al.notify();
			}
		}

	}
}

class Consumer2 extends Thread {

	ArrayList al;
	int max;
	String name;
	boolean flag = false;

	public Consumer2(ArrayList al, int max, String name) {
		super(name);
		this.al = al;
		this.max = max;
	}

	public void run() {
		while (true) {
			synchronized (al) {

				if (al.isEmpty()) {
					while (!flag) {
						try {
							al.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				for (int i = 1; i < al.size(); i++) {
					System.out.println("Consumed : " + al.remove(i));
					flag = false;
					al.notify();
				}
			}
		}
	}
}