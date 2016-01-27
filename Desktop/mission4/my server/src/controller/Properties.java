package controller;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Properties implements Serializable {

	private int maxNumOfClients;
	private int portWhichServerListene;
	
	public Properties() {
		this.maxNumOfClients = 4;
		this.portWhichServerListene = 3750;
	}

	/**
	 * @return the maxNumOfClients
	 */
	public int getMaxNumOfClients() {
		return maxNumOfClients;
	}

	/**
	 * @param maxNumOfClients the maxNumOfClients to set
	 */
	public void setMaxNumOfClients(int maxNumOfClients) {
		this.maxNumOfClients = maxNumOfClients;
	}

	/**
	 * @return the portWhichServerListene
	 */
	public int getPortWhichServerListene() {
		return portWhichServerListene;
	}

	/**
	 * @param portWhichServerListene the portWhichServerListene to set
	 */
	public void setPortWhichServerListene(int portWhichServerListene) {
		this.portWhichServerListene = portWhichServerListene;
	}
}
