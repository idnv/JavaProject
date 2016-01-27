package boot;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import presenter.Properties;

public class CreateXmlFile {

	public static void main(String[] args) {
		
		XMLEncoder xmlEncoder = null;
		Properties properties = new Properties();
		properties.setNumOfThreadsInThreadPool(4);
		properties.setDefaultSolver("mazeManhattanDistance");
		properties.setUI("GUI");
		properties.setIPOfServer("127.0.0.1");
		properties.setPortServerIsListening(3750);
		
		try {
			xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("properties.xml")));
			xmlEncoder.writeObject(properties);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			xmlEncoder.close();
		}
	}

}
