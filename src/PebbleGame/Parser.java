package PebbleGame;

import java.io.FileOutputStream;
import java.io.IOException;

public class Parser {


	// write to file
	public void appendData(String name, String data) throws IOException {
		synchronized (this) {
			// open the file in append mode
			FileOutputStream fos = new FileOutputStream(name + "_output.txt", true);
			// write to the file
			fos.write(data.getBytes());
			// close the stream
			fos.close();
		}
	}
}

