package PebbleGame;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PebbleTest {

	Pebble p;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		p = new Pebble (1,1);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@Category ({NoInputTest.class})
	void test() {
		assertEquals (1, p.getNumber());
	}

}
