package PebbleGame;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;

import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParserTest {

	Parser p;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		File file = new File("fakefile_output.txt");
		file.delete();
		file = new File("1_output.txt");
		file.delete();
		file = new File("testfile_output.txt");
		file.delete();
		file = new File("test_output.txt");
		file.delete();
		file = new File("testfile_output.txt");
		file.delete();
	}

	@BeforeEach
	void setUp() throws Exception {
		p = new Parser();
		File f = new File("testfile_output.txt");
	}

	@AfterEach
	void tearDown() throws Exception {

	}

	@Test
	@Category ({NoInputTest.class})
	@DisplayName("testing the appending of data with actual file")
	void testAppendReal() {
		try {
			p.appendData("testfile", "s");
		} catch (IOException e) {
			fail("File exists, no exceptions should be thrown");
		}
	}

	@Test
	@Category ({NoInputTest.class})
	@DisplayName("testing the append function when it has to create a file")
	void testAppendFake() {
		try {
			p.appendData("fakefile", "s");
			if(! new File("fakefile_output.txt").isFile()) {
				fail("Failed to create file");
			}
		} catch (IOException e) {
			fail("No IO error should be thrown, file should be created");
		}
	}
}
