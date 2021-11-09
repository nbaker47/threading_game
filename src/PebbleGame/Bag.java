package PebbleGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.sun.source.tree.WhileLoopTree;


public class Bag {
	/* this is a white/black bag pair since each white bag is linnked to a black bag */
	private ArrayList<Pebble> pebArrayWhite = new ArrayList<>();
	private ArrayList<Pebble> pebArrayBlack  = new ArrayList<>();
	// bag number so we can discard to it later
	private int bagNo;
	
	// random for the drawing process
	private static Random rand = new Random();
	
	// constructor
	public Bag(String filePath, int bagNo, int playerCount) throws FileNotFoundException, IOException {
		// open file path given
        Scanner sc = null;
		// loop incase the file path doesnt work so it needs to be re-entered
		while (pebArrayBlack.size() == 0) {
			try {
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
					// add to array list and trim incase spaces
					pebVals.add(Integer.parseInt(lineScan.next().trim()));
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
		   
			  } catch(FileNotFoundException e) {
				// if the filepath doesnt work let the user know and let them enter the path again
				System.out.println("File path is incorrect or the file does not exist");
				Scanner input = new Scanner(System.in);
				System.out.print("Please enter path again: ");
				filePath = input.nextLine();
			  }
		}
	}
	
	// refill the black bag once its empty (doesnt need to be *synchronized* since it will only be triggered
	// from take pebble which is synchronized itself so it is thread safe)
	private void refilBag() {
		// just move contents of white bag to black bag
		for(int i = 0; i < this.pebArrayWhite.size(); i++)
		this.pebArrayBlack.add(this.pebArrayWhite.remove(i));
	}
	
	// take a pebble, synchronized because this needs to be atomic
	public synchronized Pebble takePeb() {
		// pick a random pebble
		int n = rand.nextInt(pebArrayBlack.size());
		//take from black array
		Pebble newPeb = this.pebArrayBlack.remove(n);
		
		// fill up bag after in case the white bag is empty (so an empty check can be done)
		if (pebArrayBlack.size() < 1) {
			refilBag();
		}
		
		return newPeb;
	}
	
	// discard a pebble, synchronized so its atomic
	public synchronized void discardPeb(Pebble peb) {
		// add the pebble to the white bag
		this.pebArrayWhite.add(peb);
	}
	
	// return true if the bag is empty
	public synchronized Boolean isEmpty() {
		if (pebArrayBlack.size() == 0) {
			return true;
		}
		return false;
	}
}
