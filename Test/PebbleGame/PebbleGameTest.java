package PebbleGame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
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

	PebbleGame.Player newPlayer;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		PebbleGame g = new PebbleGame();
		g.bagMaker(1);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		File f = new File("1_output.txt");
		f.delete();
	}

	@BeforeEach
	void setUp() throws Exception {
		newPlayer = new PebbleGame.Player("1");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@Category ({InputTest.class})
	void testPlayerConstructorEmpty() {
		try {
			PebbleGame.Player p = new PebbleGame.Player("");
			fail("should throw an empty name exception");
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	@Test
	@Category ({InputTest.class})
	void testPlayerConstructorCorrect() {
		try {
			PebbleGame.Player p = new PebbleGame.Player("player");
		} catch (Exception e) {
			fail("Player should be created without throwing an exception");
		}
	}

	@Test
	@Category ({InputTest.class})
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
	@Category ({InputTest.class})
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
	@Category ({InputTest.class})
	@DisplayName("test unable to discard pebble if is empty")
	void testSumHand() {
		assertEquals(0, newPlayer.sumHand());
		newPlayer.drawPeb();
		assertNotEquals(0, newPlayer.sumHand());
	}

}
