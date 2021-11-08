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
	private static volatile Boolean gameOver = false;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		int playerNum = 3;
		
		for (int i = 0; i < 3 ; i ++) {
			// TODO: USER INPUT
			//System.out.println("Please enter the location of bag number " + i + " to load: ");
			//String path = input.next();
			bags[i] = new Bag("C:\\Users\\bunny\\Desktop\\Coursework\\softDev_CA1\\ECM2414CA1\\src\\example_file_3.csv", i, playerNum);
		}
		
		ExecutorService ex = Executors.newFixedThreadPool(playerNum);

		for(int i = 0; i < playerNum; i++) {
			Player task = new Player();
			ex.execute(task);
		}
		ex.shutdown();
		
	}

	
	static class Player implements Runnable{
		
		
		private ArrayList<Pebble> pebHand = new ArrayList<>();
		
		public void drawPeb() {
			int n = rand.nextInt(3);
			while (this.pebHand.size() < 10) {
						Pebble p;
							p = bags[n].takePeb();
						this.pebHand.add(p);
						System.out.println(Thread.currentThread().getName() + " has drawn [" + p + "] from bag " + p.getNumber());
			}
		}
		
		
		// Discard Pebble (to white bag)
		public void discardPeb() {
			// pick a random pebble to discard
			int n = rand.nextInt(pebHand.size());
			// remove from hand
			Pebble peb = this.pebHand.remove(n);
			// "remove" (add to white bag) in the bag pair
			bags[peb.getNumber()].discardPeb(peb);
		}
		
		// Add up all of the pebble weight currently in hand
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
			while (!gameOver) {
				try {
					while (sumHand() != 400 && !gameOver) {
						//discard random pebble and choose again
						//discarding and drawing must be atomic
						discardPeb();
						drawPeb();
						//sleep for more readable
							Thread.sleep(10);
							if (!gameOver) {
								printHand();
							}
					}	
				} catch (InterruptedException e) {
					if(gameOver) {
						break;
					}
				}
				
				if (sumHand() == 400) {
					gameOver = true;
					System.out.println(Thread.currentThread().getName() + this.pebHand.toString() + " HAS WONN!!" );
					Thread.interrupted();
				}
			}
		}
		
	}
	
}
