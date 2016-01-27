package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import model.Model;
import view.View;

public class CommandHandler implements ClientHandler{

	Model model;
	
	  
	public CommandHandler(Model model, View view) {
		this.model = model;
	}
	
	@Override
	public void handleClient(Socket socket)  {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			MyCompressorOutputStream cout = new MyCompressorOutputStream(socket.getOutputStream()) ;
			ObjectOutputStream outToClient=new ObjectOutputStream(socket.getOutputStream()) ;
			ObjectInputStream inFromClient =new ObjectInputStream(socket.getInputStream());
			
			Object line1;
			String line;
				try {
					if(!(line1 = inFromClient.readObject()).equals("Exit")){
						line = (String)line1;
						System.out.println(line + "!");
						String [] commandWords = line.split(" ");
						// generate 3d maze command
						 if(line.startsWith("generate 3d maze")){
							 System.out.println("in generate!");
							String [] args = new String[5];
							if(commandWords.length == 7){
								args[0] = ("generate 3d maze");
								args [1] = (commandWords[3]);
								args [2] =  (commandWords[4]);
								args [3] =  (commandWords[5]);
								args [4] =  (commandWords[6]);
								
								try {
									 String mazeName = args[1];
								     int floors = Integer.parseInt(args[2]);
								     int rows = Integer.parseInt(args[3]);
								     int columns = Integer.parseInt(args[4]);
								     Maze3d maze = model.generate3dmaze(socket,mazeName,floors,rows,columns);
								     //send to client
								     if(!socket.isClosed()){
								     outToClient.writeObject(maze);
								     outToClient.flush();
								     }
								     
								} catch(NumberFormatException e) {
									e.printStackTrace();
									System.out.println("Illegal Input - Some filds must be Natural Numbers Bigger than 0");
								}
								
								}else{
									System.out.println("Syntax is not match to command 'generate 3d maze' ");
									printWriter.flush();
								 }	
						}
						 if(line.startsWith("solve")){
							 if(commandWords.length == 2){
								 String mazeName = (commandWords[1]);
								 Solution<Position>  solution = model.solve(socket,mazeName);
								 outToClient.writeObject(solution) ;
								 outToClient.flush();
							 }
							 else{
								System.out.println("Number of parameters is not match to command 'solve' ");
							 }
						}
						
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				bufferedReader.close(); // close stream
				printWriter.close();
				cout.close();
				outToClient.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
}
