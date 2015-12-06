package view;

import java.util.ArrayList;
import java.util.HashMap;

import controller.Command;

public interface View {
	public void start();
	public void setHashMap(HashMap<String,Command> commands);
	public void printStringArrayList(ArrayList<String> arr);
}
