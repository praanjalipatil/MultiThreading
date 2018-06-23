package com.scp.producerconsumer;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class TestBlockingQueue {

	public static void main(String[] args) {

		BlockingQueue<Integer> bqueue = new ArrayBlockingQueue<>(5);

		Produce p = new Produce(bqueue, "Produce");
		Consume c = new Consume(bqueue, "Consume");

		p.start();
		c.start();
	}

}

class Produce extends Thread {

	BlockingQueue<Integer> bqueue;

	public Produce(BlockingQueue<Integer> bqueue, String tName) {
		super(tName);
		this.bqueue = bqueue;
	}

	public void run() {

		while (true) {

			try {
				Thread.sleep(1000);
				int no = ThreadLocalRandom.current().nextInt(1, 50);
				System.out.println("\nProduced : " + no);
				bqueue.put(no);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Consume extends Thread {

	BlockingQueue<Integer> bqueue;

	public Consume(BlockingQueue<Integer> bqueue, String tName) {
		super(tName);
		this.bqueue = bqueue;
	}

	public void run() {

		while (true) {

			try {
				Thread.sleep(1000);
				System.out.println("Consumed : " + bqueue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
