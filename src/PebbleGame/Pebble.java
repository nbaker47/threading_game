package PebbleGame;

public class Pebble {

	//attribute from where bag came from
	int bagNo;
	int weight;
	
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
		return Integer.toString(weight) + "g:b" + Integer.toString(bagNo);
	}
}
