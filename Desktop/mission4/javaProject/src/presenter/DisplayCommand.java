package presenter;
/**
 * The class responsible on a command that prints to out source a maze 
 */
import model.Model;
import view.View;

public class DisplayCommand extends AbsCommonCommand {
	
	public DisplayCommand(View v, Model m) {
		super(m, v);
	}
	
	@Override
	public void doCommand(String[] args) throws Exception {
		String mazeName = args[0];
		model.displayMaze(mazeName);
	}
}
