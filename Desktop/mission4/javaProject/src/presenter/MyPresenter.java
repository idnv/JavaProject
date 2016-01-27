package presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

public class MyPresenter implements Presenter {

	View view;
	Model model;
	private HashMap<String,Command> commands;
	
	public MyPresenter(Model m, View v) {
		this.view = v;
		this.model = m;
		commands = new HashMap<String,Command>();
		commands.put("dir", new DirCommand(view, model));
		commands.put("generate 3d maze", new Generate3dMazeCommand(view, model));
		commands.put("display", new DisplayCommand(view, model));
		commands.put("display cross section by", new DisplayCrossByCommand(view, model));
		commands.put("save maze", new SaveMazeCommand(view, model));
		commands.put("load maze", new LoadMazeCommand(view, model));
		commands.put("maze size", new MazeSizeCommand(view, model));
		commands.put("file size", new FileSizeCommand(view, model));
		commands.put("solve", new SolveCommand(view, model));
		commands.put("display solution", new DisplaySolutionCommand(view, model));
		commands.put("move charachter", new CharacterMovementCommand(model, view));
		commands.put("update start position", new UpdateStartPositionCommand(model, view));
		commands.put("get start position", new GetStartPositionCommand(model, view));
		commands.put("get floor information", new GetCrossSectionByFloorWithInformation(model, view));
		commands.put("exit", new ExitCommand(model, view));
	}
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		if(o == view){
			if(arg instanceof ArrayList<?>){
				try {
					String[] arrArgs = new String[((ArrayList<String>) arg).size() - 1];
					Command command = this.commands.get(((ArrayList<String>) arg).get(0));
					((ArrayList<String>) arg).remove(0);
					((ArrayList<String>) arg).toArray(arrArgs);
					command.doCommand(arrArgs);
				} catch (Exception e) {
					//e.printStackTrace();
					view.displayEror(e.getMessage());
				}
			}
			if(arg instanceof Properties){
				try {
					model.saveAndUpdateProperties((Properties) arg);
				} catch (Exception e) {
					e.printStackTrace();
					view.displayEror(e.getMessage());
				}
			}
		}
		if(o == model){
			
			if(arg instanceof String)
				view.displayString((String) arg);
			if(arg instanceof Maze3d)
				view.displayMaze((Maze3d) arg);
			if(arg instanceof int[][])
				view.displayMatrix((int[][]) arg);
			if(arg instanceof ArrayList<?>){
				view.displayArrayList((ArrayList<?>) arg);
			}
		}
	}

}
