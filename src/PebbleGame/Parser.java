package PebbleGame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Parser {
	
	// create the initial file to write to
	public void createFile(String name) throws IOException {
		synchronized (this) {
			// create the file
			File f = new File (name + "_output.txt");
			// warn the user if the file already exists
			if (!f.createNewFile()) {
				FileOutputStream fos = new FileOutputStream(name + "_output.txt", false);
				fos.write("".getBytes());
				fos.close();
			}
		}
	}
	
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

