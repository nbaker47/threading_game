package PebbleGame;

import java.io.File;
/* IMPORTS */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
	
	// random for selecting a random marble and bag
	private static Random rand = new Random();
	// scanner for user input
	private static Scanner input = new Scanner(System.in);
	// convenient way to store all of the bags
	private static Bag[] bags = new Bag[3];
	// associate bags with their actual name (X-Z & A-C)
	private static String[] bagAssociation = {"XA", "YB", "ZC"};
	// a flag for the threads to check if game is over
	private static volatile Boolean gameOver = false;
	// for player names
	private static int[] numbers;
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		int playerNum = 3;
		
		// System.out.println("Please enter the number of players:");
		// playerNum = input.nextInt();
		// while (playerNum <= 0) {
		// 	System.out.println("The number of players must be a positive integer larger than 1, please try again:");
		// 	playerNum = input.nextInt();
		// }	
		// add numbers to array
		// numbers = new int[playerNum];
		// for (int i = 0; i < playerNum; i++) {
		// 	numbers[i] = i+1;
		// }
		
		// ask for a file path for each one of the bags and initialise them with the values
		for (int i = 0; i < 3 ; i ++) {
			// TODO: USER INPUT
			//System.out.println("Please enter the location of bag number " + i + " to load: ");
			//String path = input.next();
			bags[i] = new Bag("C:\\Users\\bunny\\Desktop\\Coursework\\softDev_CA1\\ECM2414CA1\\src\\example_file_3.csv", i, playerNum);
		}
		
		// create a new thread pool with the player number and start all of the threads
		ExecutorService ex = Executors.newFixedThreadPool(playerNum);
		for(int i = 0; i < playerNum; i++) {
			Player task = new Player();
			ex.execute(task);
		}
		ex.shutdown();
	}
	
	/* PLAYER CLASS*/
	static class Player implements Runnable{
		
		// player name
		String name;
		// stores all of the pebbles currently in hand 
		private ArrayList<Pebble> pebHand = new ArrayList<>();
	
		// set the name for the current player
		public synchronized void setName() {
			// loop through numbers and get the first one
			for (int i = 0; i < numbers.length; i++) {
				// check if number is available
				if (numbers[i] != -1) {
					// set the name
					this.name = "player" + Integer.toString(numbers[i]);
				}
			}
		}
		
		// create the initial file to write to
		public synchronized void createFile() throws IOException {
			// create the file
			File f = new File (this.name + "_output.txt");
			// warn the user if the file already exists
			if (!f.createNewFile()) {
				System.out.println("Warning: file " + this.name + "_output.txt already exists");
			}
		}
		
		// write to file
		public void appendData(String data) throws IOException {
			// open the file in append mode
			FileOutputStream fos = new FileOutputStream(this.name + "_output.txt", true);
			// write to the file
			fos.write(data.getBytes());
			// close the stream
			fos.close();
		}
				
		// draws one or more pebbles so the player can hold 10 after a draw
		public void drawPeb() {
			if (!gameOver) {
				// select a random bag
				int n = rand.nextInt(3);
				// draw from it until the player has 10 pebbles in hand
				while (this.pebHand.size() < 10) {
					// check if selected bag is empty and if it is pick a new one
					while (bags[n].isEmpty()) {
						n = rand.nextInt(3);
					}
					Pebble p;
					p = bags[n].takePeb();
					this.pebHand.add(p);
					// write data to file
					try {
						appendData(this.name + " has drawn a" + p + " from bag " + bagAssociation[p.getNumber()].charAt(0) + "\n" + printHand());	
					} catch (IOException e) {
						System.out.println("Error when trying to write to file " + this.name + "_output.exe");
						e.printStackTrace();
					}
				}
			}
		}
		
		// Discard Pebble (to white bag)
		public void discardPeb() {
			if (!gameOver) {
				// pick a random pebble to discard
				int n = rand.nextInt(this.pebHand.size());
				// remove from hand
				Pebble peb = this.pebHand.remove(n);
				// "remove" (add to white bag) in the bag pair
				bags[peb.getNumber()].discardPeb(peb);
				// write output to file
				try {
					appendData(this.name + " has discarded a " + peb + " to bag " + bagAssociation[peb.getNumber()].charAt(1) + "\n" + printHand());
				} catch (IOException e) {
					System.out.println("Error when trying to write to file " + this.name + "_output.exe");
					e.printStackTrace();
				}
			}
		}
		
		// Add up all of the pebble weight currently in hand
		private int sumHand() {
			int sum = 0;
			for(int i = 0; i < pebHand.size(); i++) {
				sum += pebHand.get(i).getWeight();
			}
			return sum;
		}
		
		// TODO: might not need this, ive done the propper formatted printing
		// print hand
		private String printHand() {
			return (this.name + " hand is " + this.pebHand.toString());
		}
		
		@Override
		public void run() {
			try {
				createFile();
			} catch(IOException e) {
				System.out.println("Error creating file for " + this.name);
				e.printStackTrace();
			}
			setName();
			drawPeb();
			// draw and discard pebbles until the game is finished
			while (!gameOver) {
				try {
					// loop exits when the current pebble wins or another pebble has already won
					while (sumHand() != 100 && !gameOver) {
						// discard pebble first
						discardPeb();
						// draw a pebble (or more till hand size = 10)
						drawPeb();
						//sleep for more readable
						Thread.sleep(10);
						// only print if game hasn't finished (another pebble hasnt won)
					}	
				} catch (InterruptedException e) {
					if(gameOver) {
						break;
					}
				}
				
				// check if the current pebble has won
				if (sumHand() == 100) {
					// set the flag to true so the other threads know the game is over
					gameOver = true;
					// print winning message
					System.out.println(Thread.currentThread().getName() + this.pebHand.toString() + " HAS WONN!!" );
					Thread.interrupted();
				}
			}
		}
		
	}
	/* CAN YOU SEE THIS */
}
