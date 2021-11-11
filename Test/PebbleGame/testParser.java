package PebbleGame;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class testParser {

	Parser p;

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		try {
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
		} catch (Exception e) {
			
		}

	}

	@Before
	public void setUp() {
		try {
			p = new Parser();
			File f = new File("testfile_output.txt");
		} catch (Exception e) {

		}

	}

	@Test
	@Category ({NoInputTest.class})
	public void testAppendReal() {
		try {
			p.appendData("testfile", "s");
		} catch (IOException e) {
			fail("File exists, no exceptions should be thrown");
		}
	}

	@Test
	@Category ({NoInputTest.class})
	public void testAppendFake() {
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
