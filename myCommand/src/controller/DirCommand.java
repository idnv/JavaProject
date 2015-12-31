package controller;

import java.io.File;

import model.Model;
import view.View;
/**
 * The class responsible on a command that Calculates and print to out source all files and directories of some path
 */
public class DirCommand extends AbsCommonCommand {

	private File file;
	
	public DirCommand(View view, Model model) {
		super(model, view);
	}
	
	@Override
	public void doCommand() {
		view.displayDirectoryContents(file);
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the view
	 */
	public View getView() {
		return view;
	}

	/**
	 * @param view the view to set
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(Model model) {
		this.model = model;
	}
	
	
}
