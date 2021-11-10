package PebbleGame;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.jupiter.api.Test;

public class runAllTests {

    public static void main(String[] args) throws Exception{
        Result result = JUnitCore.runClasses(ParserTest.class);

        for (Failure failure : result.getFailures()) {
           System.out.println(failure.toString());
        }
  		
        System.out.println(result.wasSuccessful());
    }

}
		