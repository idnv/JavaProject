package presenter;

import model.Model;
import view.View;

public class MazeSizeCommand extends AbsCommonCommand {
	
	public MazeSizeCommand(View v, Model m) {
		super(m, v);
	}
	
	@Override
	public void doCommand(String[] args) throws Exception {
		String mazeName = args[0]; 
		model.displayMazeMemorySize(mazeName);
	}
}