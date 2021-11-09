package PebbleGame;

public class Pebble {

	// bag number so we know which bag pair to discard the pebble to
	int bagNo;
	// the actual weight of the pebble
	int weight;
	
	// constructor
	public Pebble(int bagNo, int weight) {
		this.weight = weight;
		this.bagNo = bagNo;
	}

	public int getNumber() {
		return this.bagNo;
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public String toString() {
		return Integer.toString(weight);
	}
}
