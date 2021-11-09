package PebbleGame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import PebbleGame.PebbleGame.Player;

class PebbleGameTest {

	Player newPlayer;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		newPlayer = new Player("1");
		PebbleGame g = new PebbleGame();
		g.bagMaker(1);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testPlayerConstructorEmpty() {
		try {
			Player p = new Player("");
			fail("should throw an empty name exception");
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	@Test
	void testPlayerConstructorCorrect() {
		try {
			Player p = new Player("player");
		} catch (Exception e) {
			fail("Player should be created without throwing an exception");
		}
	}
	
	@Test
	@Category ({InputPlayerTests.class})
	void testDrawPeb() {
		String hand = newPlayer.getHand().toString();
		newPlayer.drawPeb();
		// make sure that the hand has changed
		assertNotEquals(hand, newPlayer.getHand().toString());
		hand = newPlayer.getHand().toString();
		newPlayer.drawPeb();
		// shouldnt change because the hand is full so it cant draw
		assertEquals(hand, newPlayer.getHand().toString());
	}
	
	@Test
	@Category ({noInputPlayerTest.class})
	@DisplayName("test unable to discard pebble if is empty")
	void testDiscardPeb() {
		String hand = newPlayer.getHand().toString();
		newPlayer.discardPeb();
		assertEquals(hand, new ArrayList<Pebble>().toString());
		//test when hand is not empty
		newPlayer.drawPeb();
		hand = newPlayer.getHand().toString();
		newPlayer.discardPeb();
		assertNotEquals(hand, newPlayer.getHand().toString());
	}
	

	@Test
	@Category ({noInputPlayerTest.class})
	@DisplayName("test unable to discard pebble if is empty")
	void testSumHand() {
		assertEquals(0, newPlayer.sumHand());
		newPlayer.drawPeb();
		assertNotEquals(0, newPlayer.sumHand());
	}	
	
}
