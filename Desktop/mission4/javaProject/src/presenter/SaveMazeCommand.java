package presenter;

import java.io.IOException;

import model.Model;
import view.View;
/**
 * The class responsible on a command that saves a maze in a file
 * @author Idan
 *
 */
public class SaveMazeCommand extends AbsCommonCommand {

	public SaveMazeCommand(View v, Model m) {
		super(m, v);
	}
	
	@Override
	public void doCommand(String[] args) throws IOException, Exception {
		 String mazeName = args[0];
		 String fileName = args[1];
		model.saveMazeInFile(mazeName, fileName);
	}
}