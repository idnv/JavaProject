package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


// this is (1) the common type, and (2) a type of widget
// (1) we can switch among different MazeDisplayers
// (2) other programmers can use it naturally
public abstract class MazeDisplayer extends Canvas{
	
	int[][] mazeData = { {0,0} , {0,0} };
	
	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
		
	addMouseWheelListener(new MouseWheelListener() {
				
				@Override
				public void mouseScrolled(MouseEvent arg0) {
					if((arg0.stateMask & SWT.CONTROL) == SWT.CONTROL)
					{
						if(arg0.count > 0){
	                        int width = getSize().x;
	                        int height = getSize().y;
	
	                        setSize((int)(width * 1.05), (int)(height * 1.05));
	
	
	                    }
	                    else {
	
	                        int width = getSize().x;
	                        int height = getSize().y;
	
	                        setSize((int)(width * 0.95), (int)(height * 0.95));
	
	                        }
					}
					
				}
				
			});
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
	
	
	public abstract  boolean isWon();
	
	public abstract  void setWon(boolean isWon);
	
	public abstract void setGoalPosition(int row,int col);
	
	public abstract void setCharacterPosition(int row,int col);

	public abstract void moveUp();

	public abstract  void moveDown();

	public abstract  void moveLeft();

	public  abstract void moveRight();
	
	public abstract int getColumn();
	
	public abstract int getRow();
	
	
	
}