package PebbleGame;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

class BagTest {

	Bag b;
	Pebble mockPebble;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		File file = new File("1_output.txt");
		file.delete();
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
	@Category ({InputTest.class})
	@DisplayName("Constructor Test")
	void testConstructorNoPlayer() {
		try {
			Bag noPlayers = new Bag("example_file_1.csv", 1, 0);
			fail("should not allow 0 players");
		} catch (Exception e) {
			
		}
	}
	
	@Test
	@Category ({InputTest.class})
	@DisplayName("Constructor Test")
	void testConstructorMinusPlayer() {
		try {
			Bag minusPlayers = new Bag("example_file_1.csv", 1, -1);
			fail("exception should of been thrown");
		} catch (Exception e) {
			//System.out.println(e.getStackTrace());
		}
	}

	@Test
	@Category ({InputTest.class})
	@DisplayName("Constructor Test")
	void testConstructorMaxPlayer() {
		try {
			Bag maxPlayers = new Bag("example_file_1.csv", 1, Integer.MAX_VALUE);
			fail("exception should of been thrown");
		} catch (Exception e) {
			//System.out.println(e.getStackTrace());
		}
	}
	
	@Test
	@Category ({InputTest.class})
	@DisplayName("Constructor Test")
	void testConstructorMinusNo() {
		try {
			Bag minusNo = new Bag("example_file_1.csv", -1, 1);
			fail("exception should of been thrown");
		} catch (Exception e) {
			//System.out.println(e.getStackTrace());
		}
	}

	@Test
	@Category ({InputTest.class})
	@DisplayName("Constructor Test")
	void testConstructorMissing() {
		try {
			Bag missingFile = new Bag("i_dont_exist.csv", 1, 1);
			fail("exception should of been thrown");
		} catch (Exception e) {
			//System.out.println(e.getStackTrace());
		}
	}

	@Test
	@Category ({InputTest.class})
	@DisplayName("Constructor Test")
	void testConstructorFormat() {
		try {
			Bag wrongFormatFile = new Bag("wrongFormat.csv", 1, 1);
			fail("exception should of been thrown");
		} catch (Exception e) {
			//System.out.println(e.getStackTrace());
		}
	}




	@Test
	@Category ({InputTest.class})
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
	@Category ({InputTest.class})
	@DisplayName("Test taking of new pebbles")
	void testPebbleTake() {
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
