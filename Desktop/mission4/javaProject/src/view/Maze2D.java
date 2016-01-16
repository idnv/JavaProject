package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;


public class Maze2D extends MazeDisplayer{

	public int characterColumnIndex = 0;
	public int characterRowIndex = 2;
	public int exitColumnIndex = -1;
	public int exitRowIndex = -1;
	private boolean isGoalFloor = false;
	
	public AbsCharacter player;
	public Image walls;
	public Image floorDownPassage;
	public Image floorUpPassage;
	public Image upAndDownPassage;
	public Image goal;
	public Image winScreen;
	
	/*
	 public Maze2D(Composite parent,int style){
	        super(parent, style);
	    	// set a white background   (red, green, blue)
	    	setBackground(new Color(null, 255, 255, 255));
	    	addPaintListener(new PaintListener() {
				
				@Override
				public void paintControl(PaintEvent e) {
					   e.gc.setForeground(new Color(null,0,0,0));
					   e.gc.setBackground(new Color(null,0,0,0));

					   int width=getSize().x;
					   int height=getSize().y;

					   int w=width/mazeData[0].length;
					   int h=height/mazeData.length;

					   for(int i=0;i<mazeData.length;i++)
					      for(int j=0;j<mazeData[i].length;j++){
					          int x=j*w;
					          int y=i*h;
					          if(mazeData[i][j]!=0)
					              e.gc.fillRectangle(x,y,w,h);
					      }
					}
			});
	 }
*/
	
	 
	 public Maze2D(Composite parent,int style, AbsCharacter player){
	        super(parent, style);
	    	
	        try {
					walls = new Image(this.getDisplay(), new FileInputStream("resources/wall.jpg"));
					floorDownPassage = new Image(this.getDisplay(), new FileInputStream("resources/floorDownPassage.png"));
					floorUpPassage = new Image(this.getDisplay(), new FileInputStream("resources/floorUpPassage.png"));
					upAndDownPassage = new Image(this.getDisplay(), new FileInputStream("resources/upAndDownPassage.jpg"));
					goal = new Image(this.getDisplay(), new FileInputStream("resources/goal.png"));
					winScreen = new Image(this.getDisplay(), new FileInputStream("resources/winner.jpg"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	       
	        this.player = player;
	        
	        // set a white background   (red, green, blue)
	        setBackground(new Color(null, 255, 255, 255));
	    	addPaintListener(new PaintListener() {
				
				@Override
				public void paintControl(PaintEvent e) {
					   isGoalFloor = false;
					   e.gc.setForeground(new Color(null,0,255,0));
					   e.gc.setBackground(new Color(null,0,255,0));

					   int width=getSize().x;
					   int height=getSize().y;

					   int w=width/mazeData[0].length;
					   int h=height/mazeData.length;
					  
					   for(int i=0;i<mazeData.length;i++)
					      for(int j=0;j<mazeData[i].length;j++){
					          int x=j*w;
					          int y=i*h;
					          				                 
					          if(mazeData[i][j] == 1)
					        	  e.gc.drawImage(walls, 0, 0, walls.getBounds().width,  walls.getBounds().height, x, y, w, h);
					          if(mazeData[i][j] == 2) 
					        	  e.gc.drawImage(floorDownPassage, 0, 0, floorDownPassage.getBounds().width,  floorDownPassage.getBounds().height, x, y, w, h);
					          if(mazeData[i][j] == 3) 
					        	  e.gc.drawImage(floorUpPassage, 0, 0, floorUpPassage.getBounds().width,  floorUpPassage.getBounds().height, x, y, w, h);
					          if(mazeData[i][j] == 4) 
					        	  e.gc.drawImage(upAndDownPassage, 0, 0, upAndDownPassage.getBounds().width,  upAndDownPassage.getBounds().height, x, y, w, h);
					          if(mazeData[i][j] == 5) {
					        	  isGoalFloor = true;
					        	  e.gc.drawImage(goal, 0, 0, goal.getBounds().width,  goal.getBounds().height, x, y, w, h);
					          }
					          if(i == characterRowIndex && j == characterColumnIndex)
					        	  //player.draw(e, x, y, width, height);
					        	  e.gc.drawImage(player.charachterPicture, 0, 0, player.charachterPicture.getBounds().width,  player.charachterPicture.getBounds().height, x, y, w, h);
					          if(exitColumnIndex == characterColumnIndex && exitRowIndex == characterRowIndex && isGoalFloor)  
								  e.gc.drawImage(winScreen, 0, 0, winScreen.getBounds().width, winScreen.getBounds().height, 0, 0, width, height);
					      }
					   
					}
			});
	 }
	 private void moveCharacter(int x,int y){
			if(x>=0 && x<mazeData[0].length && y>=0 && y<mazeData.length && mazeData[y][x] != 1){
				characterColumnIndex=x;
				characterRowIndex=y;
				getDisplay().syncExec(new Runnable() {
					
					@Override
					public void run() {
						redraw();
					}
				});
			}
	 }
	 
		/* (non-Javadoc)
		 * @see view.MazeDisplayer#moveUp()
		 */
		@Override
		public void moveUp() {
			int x=characterColumnIndex;
			int y=characterRowIndex;
			y=y-1;
			moveCharacter(x, y);
		}
		/* (non-Javadoc)
		 * @see view.MazeDisplayer#moveDown()
		 */
		@Override
		public void moveDown() {
			int x=characterColumnIndex;
			int y=characterRowIndex;
			y=y+1;
			moveCharacter(x, y);
		}
		/* (non-Javadoc)
		 * @see view.MazeDisplayer#moveLeft()
		 */
		@Override
		public void moveLeft() {
			int x=characterColumnIndex;
			int y=characterRowIndex;
			x=x-1;
			moveCharacter(x, y);
		}
		/* (non-Javadoc)
		 * @see view.MazeDisplayer#moveRight()
		 */
		@Override
		public void moveRight() {
			int x=characterColumnIndex;
			int y=characterRowIndex;
			x=x+1;
			moveCharacter(x, y);
		}
		
		@Override
		public void setCharacterPosition(int row, int col) {
			characterColumnIndex=col;
			characterRowIndex=row;
			System.out.println("moveCharacter(" + col + "," + row + ")");
			moveCharacter(col,row);
		}
		

	@Override
	public int getColumn() {
		return this.characterColumnIndex;
	}


	@Override
	public int getRow() {
		return this.characterRowIndex;
	}
	
	@Override
	public void setGoalPosition(int row, int col) {
		this.exitColumnIndex = col;
		this.exitRowIndex = row;
		
	}

}
