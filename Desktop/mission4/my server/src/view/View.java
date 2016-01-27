package view;


import controller.SocketInfo;



/**
 * Interface that sets behavior of view
 */
public interface View {

	/**
	 * Starts the game 
	 */
	public void updateClientsInfo(String hashCode, SocketInfo socketList);
	
	/**
	 * exiting the connections.
	 */
	public void exit();
	
	/**
	 * receives a list of client's ID(thier hash code) and removes them from the server.
	 * @param list - list of clients ID that should kicked from the server
	 */
	public void kickClients(String [] socketList) ;
	
	
	public void run() ;

}

