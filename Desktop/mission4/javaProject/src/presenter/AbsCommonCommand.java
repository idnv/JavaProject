package presenter;

import model.Model;
import view.View;
/**
 * The class represents the common of all commands (Class that implements Command interface)
 * 
 */
public abstract class AbsCommonCommand implements Command {

	protected Model model;
	protected View view;
	
	public AbsCommonCommand(Model m, View v) {
		this.model = m;
		this.view = v;
	}
	
	public abstract void doCommand(String[] args) throws Exception;

}
