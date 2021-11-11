package PebbleGame;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ testParser.class, testBag.class, testPebble.class, testPebbleGame.class })

public class AllTests {
}
