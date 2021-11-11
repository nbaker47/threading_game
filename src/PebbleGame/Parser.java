package PebbleGame;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class for Parsing Files
 *
 */
public class Parser {

	/**
	 * Append data to file Procedure
	 * @param name
	 * @param data
	 * @throws IOException
	 */
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

