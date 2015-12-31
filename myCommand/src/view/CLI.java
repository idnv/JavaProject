package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;


import controller.Command;
import controller.DirCommand;
import controller.DisplayCommand;
import controller.DisplaySolutionCommand;
import controller.FileSizeCommand;
import controller.Generate3dMazeCommand;
import controller.GetCrossByXCommand;
import controller.GetCrossByYCommand;
import controller.GetCrossByZCommand;
import controller.LoadMazeCommand;
import controller.MazeSizeCommand;
import controller.SaveMazeCommand;
import controller.SolveCommand;
/**
 * The class Responsible of get in IO loop (in Thread) a input from user and 
 * activate a match Command
 * @author Idan
 *
 */
public class CLI extends Thread {

	private BufferedReader in;
	private PrintWriter out;
	private HashMap<String,Command> commands;
	
	/**
	 * c'tor
	 */
	public CLI(BufferedReader in,PrintWriter out) {
		this.in = in;
		this.out = out;
		// Controller update the commands
		this.commands = null;
	}
	
	/**
	 * The function run the main IO thread
	 */
	public void runTaskInThread(){
		new Thread(new Runnable() {	
			@Override
			public void run() {
				inputFromUser();
			}
		}, "Main CLI").start();
	}
	/**
	 * IO thread that get from user commands
	 */
	private void inputFromUser(){
		
		String line;
		out.println("welcome!");
		out.println("dir <path> - exmple: dir E:\\exams");
		out.println("1. generate 3d maze <name> <number of floors> <number of rows> <number of columns> - exmple: generate 3d maze myMaze 10 10 10");
		out.println("2. display <name> - exmple: display myMaze ");
		out.println("3. display cross section by {X,Y,Z} <index> for <name> - exmple: display cross section by X for myMaze  ");
		out.println("4. save maze <name> <file name> - exmple: save maze myMaze file");
		out.println("5. load maze <file name> <name> - exmple: load maze f newMyMaze");
		out.println("6. maze size <name> - exmple: maze size newMyMaze");
		out.println("7. file size <name> - exmple: file size newMyMaze");
		out.println("8. solve <name> <BFS/mazeManhattanDistance/mazeAirDistance> - exmple: solve newMyMaze mazeAirDistance");
		out.println("9. display solution <name + algorithm> - exmple:  display solution newMyMazemazeAirDistance");
		out.println("");
		out.flush();
		
		try {
			while(!(line = in.readLine()).equals("exit")){
				// dir command
				if(line.startsWith("dir")){
					
					 String [] arr = line.split(" ");
					 if(arr.length == 2){
					 DirCommand dirCommand = (DirCommand)this.commands.get("dir");
					 dirCommand.setFile(new File(arr[1]));
					 dirCommand.doCommand();
					 }
					 else{
						 out.write("Number of parameters is not match to 'command dir'");
						 out.flush();
					 }
				}
				// display solution command
				else if(line.startsWith("display solution")){
					
					 String [] arr = line.split(" ");
					 if(arr.length == 3){
					 DisplaySolutionCommand displaySolutionCommand = (DisplaySolutionCommand)this.commands.get("display solution");
					displaySolutionCommand.setSolutionName(arr[2]);
					displaySolutionCommand.doCommand();
					 }
					 else{
						 out.write("Number of parameters is not match to command 'display solution'");
						 out.flush();
					 }
				}
				// maze size command
				else if(line.startsWith("maze size")){
					
					 String [] arr = line.split(" ");
					 if(arr.length == 3){
					 MazeSizeCommand mazeSizeCommand = (MazeSizeCommand)this.commands.get("maze size");
					 mazeSizeCommand.setName(arr[2]);
					 mazeSizeCommand.doCommand();
					 }
					 else{
						 out.write("Number of parameters is not match to command 'maze size' ");
						 out.flush();
					 }
				}
				// save maze command
				else if(line.startsWith("save maze")){
					
					 String [] arr = line.split(" ");
					 if(arr.length == 4){
					 SaveMazeCommand saveMazeCommand = (SaveMazeCommand)this.commands.get("save maze");
					 saveMazeCommand.setFileName(arr[3]);
					 saveMazeCommand.setMazeName(arr[2]);
					 saveMazeCommand.doCommand();
					 }
					 else{
						 out.write("Number of parameters is not match to command 'save maze' ");
						 out.flush();
					 }
				}
				// load maze command
				else if(line.startsWith("load maze")){
					
					 String [] arr = line.split(" ");
					 if(arr.length == 4){
					 LoadMazeCommand loadMazeCommand = (LoadMazeCommand)this.commands.get("load maze");
					 loadMazeCommand.setFileName(arr[2]);
					 loadMazeCommand.setMazeName(arr[3]);
					 loadMazeCommand.doCommand();
					 }
					 else{
						 out.write("Number of parameters is not match to command 'save maze' ");
						 out.flush();
					 }
				}
				// solve command
				else if(line.startsWith("solve")){
					
					 String [] arr = line.split(" ");
					 if(arr.length == 3){
					 SolveCommand solveCommand = (SolveCommand)this.commands.get("solve");
					 solveCommand.setMazeName(arr[1]);
					 solveCommand.setAlgorithm(arr[2]);
					 solveCommand.doCommand();
					 }
					 else{
						 out.write("Number of parameters is not match to command 'solve' ");
						 out.flush();
					 }
				}
				// file size command
				else if(line.startsWith("file size")){
					
					 String [] arr = line.split(" ");
					 if(arr.length == 3){
					 FileSizeCommand fileSizeCommand = (FileSizeCommand)this.commands.get("file size");
					 fileSizeCommand.setFileName(arr[2]);
					 fileSizeCommand.doCommand();
					 }
					 else{
						 out.write("Number of parameters is not match to command 'file size' ");
						 out.flush();
					 }
				}
				// display cross section by X command
				else if(line.startsWith("display cross section by X")){
					
					try{
					 String [] arr = line.split(" ");
					 if(arr.length == 8 && arr[6].equals("for")){
					 GetCrossByXCommand getCrossByXCommand = (GetCrossByXCommand)this.commands.get("display cross section by X");
					 getCrossByXCommand.setMazeName(arr[7]);
					 getCrossByXCommand.setIndex(Integer.parseInt(arr[5]));
					 getCrossByXCommand.doCommand();
					 }
					 else{
						 out.write("Syntax is not match to command 'display cross section by X' ");
						 out.flush();
					 }
					 } catch(NumberFormatException e){
							out.println("Syntax eror in index - not a legal number");
							out.flush();
						}
				}
				// display cross section by Y command
				else if(line.startsWith("display cross section by Y")){
					
					try{
					 String [] arr = line.split(" ");
					 if(arr.length == 8 && arr[6].equals("for")){
					 GetCrossByYCommand getCrossByYCommand = (GetCrossByYCommand)this.commands.get("display cross section by Y");
					 getCrossByYCommand.setMazeName(arr[7]);
					 getCrossByYCommand.setIndex(Integer.parseInt(arr[5]));
					 getCrossByYCommand.doCommand();
					 }
					 else{
						 out.write("Syntax is not match to command 'display cross section by Y' ");
						 out.flush();
					 }
					 } catch(NumberFormatException e){
							out.println("Syntax eror in index - not a legal number");
							out.flush();
						}
				}
				// display cross section by Z command
				else if(line.startsWith("display cross section by Z")){
					
					try{
					 String [] arr = line.split(" ");
					 if(arr.length == 8 && arr[6].equals("for")){
					 GetCrossByZCommand getCrossByZCommand = (GetCrossByZCommand)this.commands.get("display cross section by Z");
					 getCrossByZCommand.setMazeName(arr[7]);
					 getCrossByZCommand.setIndex(Integer.parseInt(arr[5]));
					 getCrossByZCommand.doCommand();
					 }
					 else{
						 out.write("Syntax is not match to command 'display cross section by Z' ");
						 out.flush();
					 }
					 } catch(NumberFormatException e){
							out.println("Syntax eror in index - not a legal number");
							out.flush();
						}
				}
				// display command
				else if(line.startsWith("display")){
					
					 String [] arr = line.split(" ");
					 if(arr.length == 2){
					 DisplayCommand displayCommand = (DisplayCommand)this.commands.get("display");
					 displayCommand.setMazeName(arr[1]);
					 displayCommand.doCommand();
					 }
					 else{
						 out.write("Number of parameters is not match to command 'display'");
						 out.flush();
					 }
				}
				// generate 3d maze command
				else if(line.startsWith("generate 3d maze")){
					try{
						 String [] arr = line.split(" ");
						 if(arr.length == 7){
							 Generate3dMazeCommand generate3dMazeCommand = (Generate3dMazeCommand)this.commands.get("generate 3d maze");
							 generate3dMazeCommand.setColumns(Integer.parseInt(arr[6]));
							 generate3dMazeCommand.setFloors(Integer.parseInt(arr[4]));
							 generate3dMazeCommand.setRows(Integer.parseInt(arr[5]));
							 generate3dMazeCommand.setName(arr[3]);
							 generate3dMazeCommand.doCommand();
						 }
						 else{
							 out.write("Syntax is not match to command 'generate 3d maze' ");
							 out.flush();
						 }
					
						 } catch(NumberFormatException e){
								out.println("Syntax eror in index - not a legal numbers");
								out.flush();
						 }
				}
				else{	
					out.println("Invalid command!");
					out.flush();
				}
				
			}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	/**
	 * @return the in
	 */
	public BufferedReader getIn() {
		return in;
	}

	/**
	 * @param in the in to set
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}

	/**
	 * @return the out
	 */
	public PrintWriter getOut() {
		return out;
	}

	/**
	 * @param out the out to set
	 */
	public void setOut(PrintWriter out) {
		this.out = out;
	}

	/**
	 * @return the commands
	 */
	public HashMap<String, Command> getCommands() {
		return commands;
	}

	/**
	 * @param commands the commands to set
	 */
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}
	
	
}
