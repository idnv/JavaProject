package presenter;

import model.Model;
import view.View;
/**
 * 
 *The class responsible on a command that prints to out source a solution for maze
 */
public class DisplaySolutionCommand extends AbsCommonCommand {

	public DisplaySolutionCommand(View v, Model m) {
		super(m, v);
	}
	
	@Override
	public void doCommand(String[] args) throws Exception {
		String solutionName = args[0];
		model.displaySolution(solutionName);
	}
}