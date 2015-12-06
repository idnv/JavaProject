package controller;

import java.io.File;

public class Dir extends MyController implements Command {

	private File file;
	
	@Override
	public void doCommand() {
		m.displayDirectoryContents(file);
	}

	public void setFile(File f) {
		this.file = f;
	}
	

}
