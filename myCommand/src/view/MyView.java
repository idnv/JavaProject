package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import controller.Command;
import controller.Controller;

public class MyView implements View {

	private Controller c;
	private BufferedReader in;
	private PrintWriter out;
	private HashMap<String,Command> commands;
	
	public MyView(Controller c, BufferedReader in, PrintWriter out ) {
		this.c = c;
		this.in = in;
		this.out = out;
	}

	@Override
	public void start() {
		CLI cli = new CLI(in, out, commands);
		cli.run();
	}

	@Override
	public void setHashMap(HashMap<String, Command> commands) {
		this.commands = commands;
	}

	@Override
	public void printStringArrayList(ArrayList<String> arr) {
		for(String str : arr)
			System.out.println(str);
	}
	
}
