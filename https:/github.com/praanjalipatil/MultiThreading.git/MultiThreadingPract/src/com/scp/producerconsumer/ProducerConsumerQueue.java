package com.scp.producerconsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ProducerConsumerQueue {

	public static void main(String[] args) {

		int maxSize = 10;

		Queue<Integer> queue = new LinkedList<>();

		Producer1 p1 = new Producer1(queue, maxSize, "Producer");
		Consumer1 c1 = new Consumer1(queue, maxSize, "Consumer");

		p1.start();
		c1.start();
	}
}

class Producer1 extends Thread {

	Queue<Integer> queue;
	int maxSize;
	String name;

	public Producer1(Queue<Integer> queue, int maxSize, String name) {
		super(name);
		this.queue = queue;
		this.maxSize = maxSize;
	}

	public void run() {

		System.out.println("Inside Producer");
		while (true) {
			synchronized (queue) {
				if (queue.size() == maxSize) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Random random = new Random();
				int no = random.nextInt(20);
				System.out.println("Produced " + no);
				queue.add(no);
				queue.notify();
			}
		}
	}
}

class Consumer1 extends Thread {

	Queue<Integer> queue;
	int maxSize;
	String name;

	public Consumer1(Queue<Integer> queue, int maxSize, String name) {
		super(name);
		this.queue = queue;
		this.maxSize = maxSize;
	}

	public void run() {

		System.out.println("Inside Consumer");
		while (true) {
			synchronized (queue) {
				if (queue.isEmpty()) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Consumed " + queue.remove());
				queue.notify();

			}
		}
	}
}
