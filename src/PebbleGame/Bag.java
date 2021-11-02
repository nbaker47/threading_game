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
	
	private ArrayList<Pebble> pebArrayWhite = new ArrayList<>();
	private ArrayList<Pebble> pebArrayBlack  = new ArrayList<>();
	private int bagNo;
	
	private static Random rand = new Random();
	
	//constructor
	public Bag(String filePath, int bagNo, int playerCount) throws FileNotFoundException, IOException {
		//open file path given
		
        Scanner sc = null;
        try {
        	this.bagNo = bagNo;

        	File file = new File(filePath); // java.io.File
        	sc = new Scanner(file);     // java.util.Scanner
        	String line;
        	line = sc.nextLine();   // read full line
        	Scanner lineScan = new Scanner(line);
        	lineScan.useDelimiter(",");//split comma
   
        	//extract peb vals from comma split
        	ArrayList<Integer> pebVals  = new ArrayList<>();
        	while (lineScan.hasNext()){
        		pebVals.add(Integer.parseInt(lineScan.next().trim()));
        	}
   
        	//fill bag:
        	while (this.pebArrayBlack.size() < (11 * playerCount)) {
			   int rn = rand.nextInt(pebVals.size());
			   //make new pebble from pebvals
			   Pebble newPeb = new Pebble(bagNo, rn);
			   this.pebArrayBlack.add(newPeb); 
			   System.out.println(this.pebArrayBlack);
        	}
	   
		  }catch(FileNotFoundException e) {
			  System.out.println("File path is incorrect or the file does not exist");
			  Scanner input = new Scanner(System.in);
			  System.out.print("Please enter path again: ");
			  filePath = input.nextLine();
		  }
	}
	
	public void refilBag() {
		for(int i = 0; i < this.pebArrayWhite.size(); i++)
		this.pebArrayBlack.add(this.pebArrayWhite.remove(i));
		//System.out.println("white bag " + this.bagNo + " : "+ this.pebArrayWhite.toString());
	}
	
	public synchronized Pebble takePeb() {
		while (pebArrayBlack.size() < 1) {
			refilBag();
			//System.out.println(pebArrayBlack.size());
		}
		//System.out.println("Black bag " + this.bagNo + " : " + this.pebArrayBlack.toString());
		int n = rand.nextInt(pebArrayBlack.size());
		//take from black array
		Pebble newPeb = this.pebArrayBlack.remove(n);
		return newPeb;
	}
	
	public synchronized Boolean discardPeb(Pebble peb) {
		try {
			this.pebArrayWhite.add(peb);
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return true;
	}

	public ArrayList<Pebble> getBlackList() {
		return this.pebArrayBlack;
	}
	
	
}
