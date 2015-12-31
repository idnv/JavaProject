package presenter;


import model.Model;
import view.View;
/**
 * The class responsible on a command that Calculates and print to out source all files and directories of some path
 */
public class DirCommand extends AbsCommonCommand {
	
	public DirCommand(View view, Model model) {
		super(model, view);
	}
	
	@Override
	public void doCommand(String[] args) throws NullPointerException {
		String dir = args[0];
		model.displayDirectoryContents(dir);
	}
}
