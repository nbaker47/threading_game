package PebbleGame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class PebbleGame {
	
	private static Random rand = new Random();
	private static Scanner input = new Scanner(System.in);
	private static Bag[] bags = new Bag[3];

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		//C:\\Users\\bunny\\Desktop\\softDevCA1\\ECM2414CA1\\src\\example_file_3.csv
		
		//System.out.println("how many players:");
		//int playerNum = input.nextInt();
		int playerNum = 3;
		
		for (int i = 0; i < 3 ; i ++) {
			//System.out.println("Please enter the location of bag number " + i + " to load: ");
			//String path = input.next();
			bags[i] = new Bag("C:\\Users\\bunny\\Desktop\\Coursework\\softDev_CA1\\ECM2414CA1\\src\\example_file_3.csv", i, playerNum);
		}
		
		
		//threadpool
		//TODO
		ExecutorService ex = Executors.newFixedThreadPool(playerNum);

		for(int i = 0; i < playerNum; i++) {
			Player task = new Player();
			ex.execute(task);
		}
		ex.shutdown();
		
		/*
    	Player task1 = new Player();
    	Player task2 = new Player();
    	Player task3 = new Player();
    	Thread t1 = new Thread(task1);
    	Thread t2 = new Thread(task2);
    	Thread t3 = new Thread(task3);
    	t1.start();
    	t2.start();
    	t3.start();
    	*/
		
	}

	
	static class Player implements Runnable{
		
		private volatile Boolean gameOver = false;
		private ArrayList<Pebble> pebHand = new ArrayList<>();
		
		//draw
		public void drawPeb() {
			int n = rand.nextInt(3);
			while (this.pebHand.size() < 10) {
				//check to see if bag is empty
	
					if (bags[n].getBlackList().size() > 0) {
						Pebble p;
						synchronized (bags[n]){
							p = bags[n].takePeb();
						}
						this.pebHand.add(p);
						System.out.println(Thread.currentThread().getName() + " has drawn [" + p + "] from bag " + p.getNumber());
					}
					else {
						bags[n].refilBag();//Refill bag if empty
					}
			}
		}
		
		
		//discard
		public void discardPeb() {
			int n = rand.nextInt(pebHand.size());
			Pebble peb = this.pebHand.remove(n);
			synchronized (bags[peb.getNumber()]) {
				bags[peb.getNumber()].discardPeb(peb);
			}
		}
		
		//sum of pebbles in hand
		private int sumHand() {
			int sum = 0;
			for(int i = 0; i < pebHand.size(); i++) {
				sum += pebHand.get(i).getWeight();
			}
			return sum;
		}
		
		//print hand
		private void printHand() {
			System.out.println(Thread.currentThread().getName() + this.pebHand.toString() + " = " + sumHand());
		}
		
		@Override
		public void run() {
			drawPeb();
			printHand();
			//TODO change to 100
			while (gameOver == false) {
				while (sumHand() != 400 ) {
					//discard random pebble and choose again
					//discarding and drawing must be atomic
					//synchronized (bags) {
						discardPeb();
						drawPeb();
					//}
					//sleep for more readable
					try {
						Thread.sleep(10);
						printHand();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println(Thread.currentThread().getName() + this.pebHand.toString() + " HAS WONN!!" );
				gameOver = true;
			}
		}
		
	}
	
}
