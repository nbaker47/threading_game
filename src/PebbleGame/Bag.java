package PebbleGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * class Representing a Black/White Pebble bag pair
 *
 */
public class Bag {
	/* this is a white/black bag pair since each white bag is linnked to a black bag */
	private ArrayList<Pebble> pebArrayWhite = new ArrayList<>();
	private ArrayList<Pebble> pebArrayBlack  = new ArrayList<>();
	// bag number so we can discard to it later
	private int bagNo;
	// random for the drawing process
	private static Random rand = new Random();

	/**
	 * Constructor for Bag
	 * @param filePath
	 * @param bagNo (0,1,2)
	 * @param playerCount
	 * @throws Exception
	 * @throws FileNotFoundException
	 */
	public Bag(String filePath, int bagNo, int playerCount) throws Exception, FileNotFoundException {
		if (playerCount < 1 || playerCount > 20) {
			throw new Exception("playerCount must be a positive value (< 20)");
		}
		else if (bagNo < 0 || bagNo > 2) {
			throw new Exception("invalid bag number");
		}
		// open file path given
        Scanner sc = null;
		// loop in case the file path doesn't work so it needs to be re-entered
		while (pebArrayBlack.size() == 0) {
				this.bagNo = bagNo;

				File file = new File(filePath); // java.io.File
				if (!file.exists())
					throw new FileNotFoundException();
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
						lineScan.close();
						throw e;
					}
					pebVals.add(number);
				}
				lineScan.close();
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

	/**	Refil Bag Method
	 * 	refill the black bag once its empty (doesn't need to be *synchronised* since it will only be triggered
	 * 	from take pebble which is synchronised itself so it is thread safe)
	 */
	private void refilBag() {
		// just move contents of white bag to black bag
		for(int i = 0; i < this.pebArrayWhite.size(); i++)
		this.pebArrayBlack.add(this.pebArrayWhite.remove(i));
	}


	/**
	 * Take new Pebble Method
	 * @return
	 */
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


	/**
	 * Discard Pebble Procedure
	 * @param peb
	 */
	public void discardPeb(Pebble peb) {
		// add the pebble to the white bag
		synchronized (this) {
			this.pebArrayWhite.add(peb);
		}
	}

	/**
	 * Check Bag is empty (thread-safe) method
	 * @return
	 */
	public Boolean isEmpty() {
		synchronized (this) {
			if (pebArrayBlack.size() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * White bag(list) getter
	 * @return
	 */
	public ArrayList<Pebble> getWhiteList(){
		return this.pebArrayWhite;
	}
	
	/**
	 * Black bag(list) getter
	 * @return
	 */
	public ArrayList<Pebble> getBlackList(){
		return this.pebArrayBlack;
	}
}
