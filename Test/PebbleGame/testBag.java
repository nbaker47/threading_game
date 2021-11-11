package PebbleGame;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.experimental.categories.Category;

public class testBag {

	Bag b;
	Pebble mockPebble;

	@After
	public void tearDownAfterClass() throws Exception {
		File file = new File("1_output.txt");
		file.delete();
	}

	@Before
	public void setUp() throws Exception {
		b = new Bag("example_file_1.csv", 1, 3);
		mockPebble = b.getBlackList().get(1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Category ({InputTest.class})
	public void testConstructorNoPlayer() {
		try {
			new Bag("example_file_1.csv", 1, 0);
			fail("should not allow 0 players");
		} catch (Exception e) {
			
		}
	}
	
	@Test
	@Category ({InputTest.class})
	public void testConstructorMinusPlayer() {
		try {
			new Bag("example_file_1.csv", 1, -1);
			fail("exception should of been thrown");
		} catch (Exception e) {
			//System.out.println(e.getStackTrace());
		}
	}

	@Test
	@Category ({InputTest.class})
	public void testConstructorMaxPlayer() {
		try {
			new Bag("example_file_1.csv", 1, Integer.MAX_VALUE);
			fail("exception should of been thrown");
		} catch (Exception e) {
			//System.out.println(e.getStackTrace());
		}
	}
	
	@Test
	@Category ({InputTest.class})
	public void testConstructorMinusNo() {
		try {
			new Bag("example_file_1.csv", -1, 1);
			fail("exception should of been thrown");
		} catch (Exception e) {
			//System.out.println(e.getStackTrace());
		}
	}

	@Test
	@Category ({InputTest.class})
	public void testConstructorMissing() {
		try {
			new Bag("i_dont_exist.csv", 1, 1);
			fail("exception should of been thrown");
		} catch (Exception e) {
			//System.out.println(e.getStackTrace());
		}
	}

	@Test
	@Category ({InputTest.class})
	public void testConstructorFormat() {
		try {
			new Bag("wrongFormat.csv", 1, 1);
			fail("exception should of been thrown");
		} catch (Exception e) {
			//System.out.println(e.getStackTrace());
		}
	}




	@Test
	@Category ({InputTest.class})
	public void testDiscardPeb() {
		ArrayList<Pebble> beforeWhite = b.getWhiteList();
		//create mock pebble and discard it
		beforeWhite.add(mockPebble);
		b.discardPeb(mockPebble);
		//assert
		assertEquals(beforeWhite, b.getWhiteList());
	}

	@Test
	@Category ({InputTest.class})
	public void testPebbleTake() {
		//test black array has grown
		ArrayList<Pebble> beforeBlack = b.getBlackList();
		//create mock pebble and discard it
		beforeBlack.remove(mockPebble);
		b.discardPeb(mockPebble);
		//assert
		assertEquals(beforeBlack, b.getBlackList());

	}

	@Test
	@Category ({InputTest.class})
	public void testRefillBag() {
		//fill white bag with a pebble (only thing in white bag now)
		b.discardPeb(mockPebble);
		String white  = b.getWhiteList().toString();
		int x = b.getBlackList().size();
		//empty bag (to test if refill will be called)
		for (int i = 0; i < x; i++) {
			b.takePeb();
		}
		assertEquals(b.getBlackList().toString(), white);
	}

}
