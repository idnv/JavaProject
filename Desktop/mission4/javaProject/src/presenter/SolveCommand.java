package presenter;

import model.Model;
import view.View;
/**
 * The class responsible on a command that solve a maze in separate thread.
 * @author Idan
 *
 */
public class SolveCommand  extends AbsCommonCommand {

	public SolveCommand(View v, Model m) {
		super(m, v);
	}
	
	@Override
	public void doCommand(String[] args) throws Exception {
		 String mazeName = args[0];
		 String algorithm = args[1];
		model.solve(mazeName, algorithm);
	}
}