package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;

public class CLI extends Thread {

	private BufferedReader in;
	private PrintWriter out;
	private HashMap<String,Command> commands;
	
	public CLI(BufferedReader in,PrintWriter out, HashMap<String,Command> commands) {
		this.in = in;
		this.out = out;
		this.commands = commands;
	}
	private void runTaskInThread(){
		new Thread(new Runnable() {	
			@Override
			public void run() {
				start();
			}
		}).start();
	}
	public void run(){
		this.runTaskInThread();
	}
	public void start(){
		//TODO: what about line == null;
		boolean flag = true;
		String line = "";
		while(flag){
			try {
				 line = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(line.equals("exit"))
				flag = false;
		}
	}
}
