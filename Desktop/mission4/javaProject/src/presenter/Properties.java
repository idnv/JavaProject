package presenter;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Properties implements Serializable {

	private int numOfThreadsInThreadPool;
	private String defaultSolver;
	private String UI;
	private String IPOfServer;
	private int portServerIsListening;
	
	public Properties() {
		this.numOfThreadsInThreadPool = 4;
		this.defaultSolver = "mazeManhattanDistance";
		this.UI = "CLI";
		this.IPOfServer = "127.0.0.1";
		this.portServerIsListening = 3750;
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
	 * @return the uI
	 */
	public String getUI() {
		return UI;
	}

	/**
	 * @param uI the uI to set
	 */
	public void setUI(String uI) {
		UI = uI;
	}

	/**
	 * @return the iPOfServer
	 */
	public String getIPOfServer() {
		return IPOfServer;
	}

	/**
	 * @param iPOfServer the iPOfServer to set
	 */
	public void setIPOfServer(String iPOfServer) {
		IPOfServer = iPOfServer;
	}

	/**
	 * @return the portServerIsListening
	 */
	public int getPortServerIsListening() {
		return portServerIsListening;
	}

	/**
	 * @param portServerIsListening the portServerIsListening to set
	 */
	public void setPortServerIsListening(int portServerIsListening) {
		this.portServerIsListening = portServerIsListening;
	}
	
	
}
