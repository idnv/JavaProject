package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyClient {
	
	int port;
	String host;
	
	public MyClient(int port,String host) {
		this.port = port;
		this.host = host;
	}
	
	public void start(){
		
		//BufferedReader bufferedReader = new BufferedReader(new InputStreamReader());
		//PrintWriter printWriter = new PrintWriter(System.out);

	}
	

	public static void main(String[] args) throws Exception {
		
		System.out.println("Client Side");
		Socket theServer = new Socket("127.0.0.1", 5600);
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
		PrintWriter outToServer = new PrintWriter(theServer.getOutputStream());
		String line;
		do{
			line = inFromUser.readLine();
			outToServer.println(line);
			outToServer.flush();
			if(!line.equals("Exit"))
				System.out.println(inFromServer.readLine());
		}while(!line.equals("Exit"));
	
	}

}
