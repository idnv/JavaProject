package controller;

import java.util.ArrayList;
import java.util.HashMap;

import model.Model;
import view.View;

public class MyController implements Controller {

	protected Model m;
	protected View v;
	private HashMap<String,Command> commands;
	
	public MyController() {
		//TODO: add commands in hashmap
		this.commands = new HashMap<String,Command>();
	}
	
	@Override
	public void setModel(Model m) {
		this.m = m;
	}

	@Override
	public void setView(View v) {
		this.v = v;
	}

	@Override
	public void showdir(ArrayList<String> arr) {
		v.printStringArrayList(arr);
	}

}
