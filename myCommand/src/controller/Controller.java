package controller;

import java.util.ArrayList;

import model.Model;
import view.View;

public interface Controller {
	public void setModel(Model m);
	public void setView(View v);
	public void showdir(ArrayList<String> arr);
}