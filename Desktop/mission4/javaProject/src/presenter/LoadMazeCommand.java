package presenter;

import java.io.IOException;

import model.Model;
import view.View;
/**
 * The class responsible on a command that load a maze from a file
 * @author Idan
 *
 */
public class LoadMazeCommand extends AbsCommonCommand {


	public LoadMazeCommand(View v, Model m) {
		super(m, v);
	}
	
	@Override
	public void doCommand(String[] args) throws Exception, IOException, NullPointerException {
		 String fileName = args[0];
		 String mazeName = args[1];
		model.loadMazeFromFile(mazeName, fileName);
	}
}