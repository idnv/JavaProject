package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import controller.Controller;
import controller.MyController;
import model.Model;
import model.MyModel;
import view.MyView;
import view.View;

public class Run {

	public static void main(String[] args) {
		System.out.println("3");
		Controller c = new MyController();
		Model m = new MyModel(c);
		View v = new MyView(c, new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out));
		c.setModel(m);
		c.setView(v);
		c.setViewCommand();
		v.start();
	}
}
