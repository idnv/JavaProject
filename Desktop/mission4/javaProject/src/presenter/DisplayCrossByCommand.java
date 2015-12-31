package presenter;

import model.Model;
import view.View;
/**
 * The class responsible on a command that prints to out source a column cross section of a maze in some index
 *
 */
public class DisplayCrossByCommand extends AbsCommonCommand {

	public DisplayCrossByCommand(View v, Model m) {
		super(m, v);
	}

	@Override
	public void doCommand(String[] args) throws IndexOutOfBoundsException, Exception{
		try {
			String dimension = args[0];
			int index = Integer.parseInt(args[1]);
			String mazeName = args[2];
			model.getCrossBy(mazeName, index, dimension);
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
	}
}
