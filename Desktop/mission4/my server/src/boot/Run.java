package boot;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import controller.ClientHandler;
import controller.CommandHandler;
import controller.Controller;
import controller.MyServer;
import controller.Properties;
import model.Model;
import model.MyModel;
import view.MyAdminView;
import view.View;

public class Run {

	public static void main(String[] args)  throws Exception{
		
		Properties serverProperties = new Properties();
		XMLDecoder xmlDecoder = null;
		
		try {
			xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("server Properties.xml")));
			serverProperties = (Properties)xmlDecoder.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			xmlDecoder.close();
		}
		
		
		Controller server = new MyServer(serverProperties);
		Model model = new MyModel(server);
		View view = new MyAdminView(server,"Admin Server", 888, 500);
		ClientHandler clientHandler = new CommandHandler(model, null);
		server.setClientHandler(clientHandler);
		server.setView(view);
		// run GUI
		view.run();
		
		//Thread.sleep(1000 * 60 * 2);
		//server.closeServer();
		}

}
