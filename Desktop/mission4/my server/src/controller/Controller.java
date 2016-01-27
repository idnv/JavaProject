package controller;


import java.net.Socket;

import model.Model;
import view.View;
/**
 * Interface that sets behavior of Controller
 */
public interface Controller {
	public void setView(View v);
	
	public void updateProperties(Properties properties);
	
	
	public void setClientHandler(ClientHandler clientHandler);
	/**
	 * This function active the view function "updateClientsInfo" {@link View}
	 * The function updates the table of sockets in View
	 * @param socket The Socket that have the update {@link Socket}
	 * @param status String that containes 
	 */
	public void updateStatus(Socket socket, String status);
	
	/**
	 * This function start's the server
	 */
	public void start();
	/**
	 * This function close the server
	 */
	public void closeServer();
	/**
	 * This function kik from server socket with his uniq key
	 * @param socketID String {@link String} that represents the socket Hashcode 
	 */
	public void kick(String socketID);
	
}