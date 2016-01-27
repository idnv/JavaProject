package controller;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.Model;
import model.MyModel;
import view.MyAdminView;
import view.View;

public class MyServer implements Controller{

	View view;
	Model  model;

	int port;
	ClientHandler clientHandler;
	volatile boolean stop;
	ExecutorService threadPool;
	int numOfClients;
	//volatile HashMap<Socket,SocketInfo> socketList;
	volatile HashMap<String,SocketInfo> socketList;
	
	public MyServer(Properties properties) {
		this.port = properties.getPortWhichServerListene();
		this.stop = false;
		this.socketList = new HashMap<>();
		this.threadPool = Executors.newFixedThreadPool(properties.getMaxNumOfClients());
	}
	
	public void start(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				startServer();
			}
		}).start();
	}
	private void startServer(){
		try {
			
			ServerSocket server = new ServerSocket(port); //Open new Server
			server.setSoTimeout(2000);
			while(!stop){
				try{
					final Socket someClient = server.accept(); // waits and listene for client to connect
					this.socketList.put(String.valueOf(someClient.hashCode()), new SocketInfo(someClient));
					updateView(someClient);
					System.out.println("client connected");
						threadPool.execute(new Runnable() {		
						@Override
						public void run() {
							try {
							clientHandler.handleClient(someClient);	
							if(socketList.get(String.valueOf(someClient.hashCode())).isConnected()){
								socketList.get(String.valueOf(someClient.hashCode())).setConnected(false);
								updateView(someClient);
							}
							someClient.close(); // close client
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
					
				}
				catch (SocketTimeoutException e) {
					System.out.println("waiting for next client");
				}

			}
			
			server.close(); //close server
			
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}
	public void closeServer(){
		stop = true;
		threadPool.shutdown();
	}
	/**
	 * Update the view when something happend to one of sockets
	 * @param socketList Hashmap that containes all data about a socket
	 */
	public void updateView(Socket socket){
		// case the GUI is disposed
		if(view != null)
			view.updateClientsInfo("" +socket.hashCode(),this.socketList.get(""+socket.hashCode()));
	}
	
	@Override
	public void updateStatus(Socket socket, String status) {
		this.socketList.get(""+socket.hashCode()).setStatus(status);
		updateView(socket);
	}

	@Override
	public void kick(String socketID) {
		try {
			// Closing socket and e=remove from connected sockets list
			socketList.get(socketID).setConnected(false);
			socketList.get(socketID).setStatus("Kicked");
			socketList.get(socketID).getSocket().close();
			//socketList.remove(socketID);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void setClientHandler(ClientHandler clientHandler) {
		this.clientHandler = clientHandler;
	}

	@Override
	public void setView(View v) {
		this.view = v;
	}

	@Override
	public void updateProperties(Properties properties) {
		this.closeServer();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.port = properties.getPortWhichServerListene();
		this.numOfClients = properties.getMaxNumOfClients();
		this.start();
	}

	
}
