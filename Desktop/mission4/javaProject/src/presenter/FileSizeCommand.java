package presenter;

import model.Model;
import view.View;
/**
 * 
 * The class responsible on a command that prints to out source a size a file
 *
 */
public class FileSizeCommand extends AbsCommonCommand {
	
	public FileSizeCommand(View v, Model m) {
		super(m, v);
	}
	
	@Override
	public void doCommand(String[] args) throws Exception {
		String fileName = args[0];
		model.displayFileSize(fileName);
	}
}