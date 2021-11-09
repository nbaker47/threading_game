package PebbleGame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BagTest {

	Bag b;
	Pebble mockPebble;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		b = new Bag("example_file_1.csv", 1, 3);
		mockPebble = b.getBlackList().get(1);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Constructor Test")
	void testConstructor() {
		try {
			Bag noPlayers = new Bag("example_file_1.csv", 1, 0);
			Bag minusPlayers = new Bag("example_file_1.csv", 1, -1);
			Bag maxPlayers = new Bag("example_file_1.csv", 1, Integer.MAX_VALUE);
			Bag minusNo = new Bag("example_file_1.csv", -1, 1);
			Bag missingFile = new Bag("i_dont_exist.csv", 1, 1);
			Bag wrongFormatFile = new Bag("wrongFormat.csv", 1, 1);
			fail("exception should of been thrown");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		} 
	}
	
	
	@Test
	@DisplayName("Test discarding of pebble")
	void testDiscardPeb() {
		ArrayList<Pebble> beforeWhite = b.getWhiteList();
		//create mock pebble and discard it
		beforeWhite.add(mockPebble);
		b.discardPeb(mockPebble);
		//assert
		assertEquals(beforeWhite, b.getWhiteList());
	}
	
	@Test
	@DisplayName("Test taking of new pebbles")
	void testPebbleTake() {
		//test black array has grown
		ArrayList<Pebble> beforeBlack = b.getBlackList();
		
	}
	
	@Test
	void testRefillBag() {
		//fill white bag with a pebble (only thing in white bag now)
		b.discardPeb(mockPebble);
		String white  = b.getWhiteList().toString();
		int x = b.getBlackList().size();
		//empty bag (to test if refil will be called)
		for (int i = 0; i < x; i++) {
			b.takePeb();
		}
		assertEquals(b.getBlackList().toString(), white);
	}

	
}
