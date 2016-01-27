package controller;

import java.net.Socket;
import java.time.LocalDateTime;


public class SocketInfo {

	private Socket socket;
	private LocalDateTime connectionTime;
	private LocalDateTime disconnectingTime;
	private String status;
	private String clientIP;
	private boolean isConnected;
	
	public SocketInfo(Socket socket) {
		this.socket = socket;
		this.connectionTime = LocalDateTime.now();
		this.clientIP = socket.getInetAddress().getHostAddress();
		this.isConnected = true;
	}

	/**
	 * @return the connectionTime
	 */
	public LocalDateTime getConnectionTime() {
		return connectionTime;
	}

	/**
	 * @param connectionTime the connectionTime to set
	 */
	public void setConnectionTime(LocalDateTime connectionTime) {
		this.connectionTime = connectionTime;
	}

	/**
	 * @return the disconnectingTime
	 */
	public LocalDateTime getDisconnectingTime() {
		return disconnectingTime;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the isConnected
	 */
	public boolean isConnected() {
		return isConnected;
	}

	/**
	 * @param isConnected the isConnected to set
	 */
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
		if(isConnected == false){
			this.disconnectingTime = LocalDateTime.now();
			this.status = "Disconnected";
		}
		if(isConnected == true)
			this.connectionTime = LocalDateTime.now();
	}

	/**
	 * @return the iP
	 */
	public String getIP() {
		return clientIP;
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}
}
