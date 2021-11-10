package PebbleGame;

/* IMPORTS */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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

	public static void main(String[] args) {

		PebbleGame game = new PebbleGame();
		game.printWelcome();
		int playerNum = game.playerNumMaker();
		// ask for a file path for each one of the bags and initialise them with the value
		game.bagMaker(playerNum);
		// create a new thread pool with the player number and start all of the threads
		game.threadMaker(playerNum);

	}

	
	private void printWelcome() {
		System.out.println("Welcome to the PebbleGame!!");
		System.out.println("You will be asked to enter the number of players.");
		System.out.println("and then for the location of three files in turn containing comma seperated integer values for the pebble weights.");
		System.out.println("The integer values must be strictly positive.");
		System.out.println("The game will then be simulated, and output written to files in this directory.");

	}

	void bagMaker(int playerNum) {
		Scanner input = null;
		for (int i = 0; i < 3 ; i ++) {
			// TODO: USER INPUT
			System.out.println("Please enter the location of bag number " + i + " to load: ");
			input = new Scanner(System.in);
			String path = input.nextLine();
			do {
				try {
					bags[i] = new Bag(path , i, playerNum);
					break;
				}catch(FileNotFoundException e) {
					// if the file path doesn't work let the user know and let them enter the path again
					System.out.println("File path is incorrect or the file does not exist");
					System.out.print("Please enter path again: ");
					path = input.nextLine();
				}catch (NumberFormatException e) {
					System.out.println("The file given is not in the correct format.");
					System.out.println("Please enter path again: ");
					path = input.nextLine();
				}catch (Exception e) {
					System.out.println("All numbers in the file must be more than 0.");
					System.out.println("Please enter path again: ");
					path = input.nextLine();
				}
				System.out.println(path);
			} while(true);
		}
		input.close();
	}

	private void threadMaker(int playerNum) {
		ExecutorService ex = Executors.newFixedThreadPool(playerNum);
		for(int i = 0; i < playerNum; i++) {
			Player task = null;
			try {
				task = new Player("player" + (i+1) );
			} catch (Exception e) {
				System.out.println("Invalid Name");
			}
			ex.execute(task);
		}
		ex.shutdown();
	}

	private int playerNumMaker() {
		System.out.println("Please enter the number of players:");
		int playerNum = input.nextInt();
		while (playerNum <= 0 || playerNum > 20) {
			System.out.println("The number of players must be a positive integer larger than 1 (<20), please try again:");
		 	playerNum = input.nextInt();
		 }
		return playerNum;
	}

	/* PLAYER CLASS*/
	static class Player implements Runnable{

		Parser parse = new Parser();
		// player name
		String name;
		// stores all of the pebbles currently in hand
		private ArrayList<Pebble> pebHand = new ArrayList<>();

		public Player(String name) throws Exception {
			if (name == "") {
				throw new Exception("Invalid Name, cannot be empty");
			}
			this.name = name;
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
						String data = " has drawn a " + p + " from bag " + bagAssociation[p.getNumber()].charAt(0) + "\n" + printHand() + "\n";
						parse.appendData(this.name, data);
					} catch (IOException e) {
						System.out.println("Error when trying to write to file " + this.name + "_output.exe");
						e.printStackTrace();
					}
				}
			}
		}

		// Discard Pebble (to white bag)
		public void discardPeb() {
			if (!gameOver && pebHand.size() > 0) {
				// pick a random pebble to discard
				int n = rand.nextInt(this.pebHand.size());
				// remove from hand
				Pebble peb = this.pebHand.remove(n);
				// "remove" (add to white bag) in the bag pair
				bags[peb.getNumber()].discardPeb(peb);
				// write output to file
				try {
					String data = " has discarded a " + peb + " to bag " + bagAssociation[peb.getNumber()].charAt(1) + "\n" + printHand() + "\n";
					parse.appendData(this.name, data);
				} catch (IOException e) {
					System.out.println("Error when trying to write to file " + this.name + "_output.exe");
					e.printStackTrace();
				}
			}
		}

		// Add up all of the pebble weight currently in hand
		int sumHand() {
			int sum = 0;
			for (Pebble element : pebHand) {
				sum += element.getWeight();
			}
			return sum;
		}

		// TODO: might not need this, ive done the propper formatted printing
		// print hand
		private String printHand() {
			return (this.name + " hand is " + this.pebHand.toString().replace("[", "").replace("]", ""));
		}

		@Override
		public void run() {
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
						Thread.sleep(0);
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
					System.out.println(this.name + " HAS WONN!!" );
					Thread.interrupted();
				}
			}
		}

		public ArrayList<Pebble> getHand() {
			return this.pebHand;
		}

	}
}
