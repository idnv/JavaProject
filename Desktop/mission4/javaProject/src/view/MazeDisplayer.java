package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


// this is (1) the common type, and (2) a type of widget
// (1) we can switch among different MazeDisplayers
// (2) other programmers can use it naturally
public abstract class MazeDisplayer extends Canvas{
	
	int[][] mazeData = { {0,0} , {0,0} };
	
	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
	}

	public void setMazeData(int[][] mazeData){
		this.mazeData=mazeData;
		getDisplay().syncExec(new Runnable() {
			
			@Override
			public void run() {
				redraw();
			}
		});
	}
	
	
	public abstract void setGoalPosition(int row,int col);
	
	public abstract void setCharacterPosition(int row,int col);

	public abstract void moveUp();

	public abstract  void moveDown();

	public abstract  void moveLeft();

	public  abstract void moveRight();
	
	public abstract int getColumn();
	
	public abstract int getRow();
	
	
	
}