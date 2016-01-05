package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class JoystickDisplayer extends Canvas {

	String [] PossibleMoves;
	
	public JoystickDisplayer(Composite parent, int style) {
		super(parent, style);
	}

	
	
	
	
	/**
	 * @return the possibleMoves
	 */
	public String[] getPossibleMoves() {
		return PossibleMoves;
	}

	/**
	 * @param possibleMoves the possibleMoves to set
	 */
	public void setPossibleMoves(String[] possibleMoves) {
		PossibleMoves = possibleMoves;
	}
	
}
