package boot;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import model.MyModel;
import presenter.MyPresenter;
import presenter.Presenter;
import presenter.Properties;
import view.CLI;
import view.MyView;
import view.View;

public class Run {

	public static void main(String[] args) {
		
		Properties properties = new Properties();
		XMLDecoder xmlDecoder = null;
		
		try {
			xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("Properties.xml")));
			properties = (Properties)xmlDecoder.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			xmlDecoder.close();
		}
		
		
		if(properties.getView().equals("CLI")){
			Model model = new MyModel(properties);
			CLI cli = new CLI(new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out));
			View view = new MyView(cli,new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out));
			Presenter presenter = new MyPresenter(model, view);
			((Observable)view).addObserver(presenter);
			((Observable)model).addObserver(presenter);
			((Observable)cli).addObserver((Observer)view);;
			view.start();
		}
		if(properties.getView().equals("GUI")){
			
		}
	}

}
