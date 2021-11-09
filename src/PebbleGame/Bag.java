package PebbleGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Bag {
	/* this is a white/black bag pair since each white bag is linnked to a black bag */
	private ArrayList<Pebble> pebArrayWhite = new ArrayList<>();
	private ArrayList<Pebble> pebArrayBlack  = new ArrayList<>();
	// bag number so we can discard to it later
	private int bagNo;
	
	// random for the drawing process
	private static Random rand = new Random();
	
	// constructor
	public Bag(String filePath, int bagNo, int playerCount) throws Exception {
		// open file path given
        Scanner sc = null;
		// loop in case the file path doesn't work so it needs to be re-entered
		while (pebArrayBlack.size() == 0) {
				this.bagNo = bagNo;

				File file = new File(filePath); // java.io.File
				sc = new Scanner(file);     // java.util.Scanner
				String line;
				// read the full line
				line = sc.nextLine();
				Scanner lineScan = new Scanner(line);
				// split on commas
				lineScan.useDelimiter(",");
	   
				//extract peb vals from comma split
				ArrayList<Integer> pebVals  = new ArrayList<>();
				while (lineScan.hasNext()){
					// add to array list and trim in case spaces
					int number = Integer.parseInt(lineScan.next().trim());
					if (number <= 0) {
						Exception e = new Exception();
						throw e;
					}
					pebVals.add(number);
				}
	   
				// fill current bag
				while (this.pebArrayBlack.size() < (11 * playerCount)) {
				   int rn = rand.nextInt(pebVals.size());
				   // initialise all of the pebbles from pebVal
				   Pebble newPeb = new Pebble(bagNo, rn);
				   // add pebbles to black bag
				   this.pebArrayBlack.add(newPeb);
				   // TODO: can we remove this?
				   //System.out.println(this.pebArrayBlack);
				}
		}
	}
	
	// refill the black bag once its empty (doesn't need to be *synchronized* since it will only be triggered
	// from take pebble which is synchronized itself so it is thread safe)
	private void refilBag() {
		// just move contents of white bag to black bag
		for(int i = 0; i < this.pebArrayWhite.size(); i++)
		this.pebArrayBlack.add(this.pebArrayWhite.remove(i));
	}
	
	// take a pebble, synchronized because this needs to be atomic
	public Pebble takePeb() {
		Pebble newPeb;
		synchronized (this) {
			// pick a random pebble
			int n = rand.nextInt(pebArrayBlack.size());
			//take from black array
			newPeb = this.pebArrayBlack.remove(n);
			
			// fill up bag after in case the white bag is empty (so an empty check can be done)
			if (pebArrayBlack.size() < 1) {
				refilBag();
			}
		}
		
		return newPeb;
	}
	
	// discard a pebble, synchronized so its atomic
	public void discardPeb(Pebble peb) {
		// add the pebble to the white bag
		synchronized (this) {
			this.pebArrayWhite.add(peb);			
		}
	}
	
	// return true if the bag is empty
	public Boolean isEmpty() {
		synchronized (this) {
			if (pebArrayBlack.size() == 0) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Pebble> getWhiteList(){
		return this.pebArrayWhite;
	}
	
	public ArrayList<Pebble> getBlackList(){
		return this.pebArrayBlack;
	}
}
