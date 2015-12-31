package presenter;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Properties implements Serializable {

	private int numOfThreadsInThreadPool;
	private String defaultSolver;
	private String view;
	
	public Properties() {
		this.numOfThreadsInThreadPool = 4;
		this.defaultSolver = "mazeManhattanDistance";
		this.view = "CLI";
	}

	/**
	 * @return the numOfThreadsInThreadPool
	 */
	public int getNumOfThreadsInThreadPool() {
		return numOfThreadsInThreadPool;
	}

	/**
	 * @param numOfThreadsInThreadPool the numOfThreadsInThreadPool to set
	 */
	public void setNumOfThreadsInThreadPool(int numOfThreadsInThreadPool) {
		this.numOfThreadsInThreadPool = numOfThreadsInThreadPool;
	}

	/**
	 * @return the defaultSolver
	 */
	public String getDefaultSolver() {
		return defaultSolver;
	}

	/**
	 * @param defaultSolver the defaultSolver to set
	 */
	public void setDefaultSolver(String defaultSolver) {
		this.defaultSolver = defaultSolver;
	}

	/**
	 * @return the view
	 */
	public String getView() {
		return view;
	}

	/**
	 * @param view the view to set
	 */
	public void setView(String view) {
		this.view = view;
	}
	
	
}
