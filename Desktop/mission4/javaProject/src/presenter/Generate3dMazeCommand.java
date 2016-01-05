package presenter;


import model.Model;
import view.View;
/**
 * 
 * The class responsible on a command that generates a maze
 *
 */
public class Generate3dMazeCommand extends AbsCommonCommand {

	public Generate3dMazeCommand(View v, Model m) {
			super(m, v);
	}
	
	@Override
	public void doCommand(String[] args) throws Exception {
		try {
			String name = args[0];
		     int floors = Integer.parseInt(args[1]);
		     int rows = Integer.parseInt(args[2]);
		     int columns = Integer.parseInt(args[3]);
		     model.generate3dmaze(name, floors, rows, columns);
		} catch(NumberFormatException e) {
			e.printStackTrace();
			view.displayString("Illegal Input - Some filds must be Natural Numbers Bigger than 0");
		}
	}
}