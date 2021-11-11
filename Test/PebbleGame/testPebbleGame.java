package PebbleGame;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class testPebbleGame {

	PebbleGame.Player newPlayer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PebbleGame g = new PebbleGame();
		PebbleGame.Player newPlayer = new PebbleGame.Player("1");
		g.bagMaker(1);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File f = new File("1_output.txt");
		f.delete();
	}

	@Before
	public void setUp() throws Exception {
		newPlayer = new PebbleGame.Player("1");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Category ({InputTest.class})
	public void testPlayerConstructorEmpty() {
		try {
			PebbleGame.Player p = new PebbleGame.Player("");
			fail("should throw an empty name exception");
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	@Test
	@Category ({InputTest.class})
	public void testPlayerConstructorCorrect() {
		try {
			PebbleGame.Player p = new PebbleGame.Player("player");
		} catch (Exception e) {
			fail("Player should be created without throwing an exception");
		}
	}

	@Test
	@Category ({InputTest.class})
	public void testDrawPeb() {
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
	public void testDiscardPeb() {
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
	public void testSumHand() {
		assertEquals(0, newPlayer.sumHand());
		newPlayer.drawPeb();
		assertNotEquals(0, newPlayer.sumHand());
	}

}
