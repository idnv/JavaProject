package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;


/**
 * The class Responsible of get in IO loop (in Thread) a input from user and 
 * activate a match Command
 * @author Idan
 *
 */
public class CLI extends Observable {

	private BufferedReader in;
	private PrintWriter out;
	
	/**
	 * c'tor
	 */
	public CLI(BufferedReader in,PrintWriter out) {
		this.in = in;
		this.out = out;
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
	 * The function gets in IO thread user commands from an input source {@link BufferedReader}
	 */
	private void inputFromUser(){
		
		String line;
		ArrayList<String> args = new ArrayList<String>();
		printCommands();
		try {
			while(!(line = in.readLine()).equals("exit")){
				String [] commandWords = line.split(" ");
				// dir command
				if(line.startsWith("dir")){
					 if(commandWords.length == 2){
						 args.add("dir");
						 args.add(commandWords[1]);
						 setChanged();
						notifyObservers(args);
						args.clear();
					 }
					 else{
						 out.write("Number of parameters is not match to 'command dir'");
						 out.flush();
					 }
				}
				// display solution command
				else if(line.startsWith("display solution")){
					 if(commandWords.length == 3){
						 args.add("display solution");
						 args.add(commandWords[2]);
						 setChanged();
						 notifyObservers(args);
						 args.clear();
					 }
					 else{
						 out.write("Number of parameters is not match to command 'display solution'");
						 out.flush();
					 }
				}
				// maze size command
				else if(line.startsWith("maze size")){
					 if(commandWords.length == 3){
						 args.add("maze size");
						 args.add(commandWords[2]);
						 setChanged();
						 notifyObservers(args);
						 args.clear();
					 }
					 else{
						 out.write("Number of parameters is not match to command 'maze size' ");
						 out.flush();
					 }
				}
				// save maze command
				else if(line.startsWith("save maze")){
					 if(commandWords.length == 4){
						 args.add("save maze");
						 args.add(commandWords[2]);
						 args.add(commandWords[3]);
						 setChanged();
						 notifyObservers(args);
						 args.clear();
					 }
					 else{
						 out.write("Number of parameters is not match to command 'save maze' ");
						 out.flush();
					 }
				}
				// load maze command
				else if(line.startsWith("load maze")){
					 if(commandWords.length == 4){
						 args.add("load maze");
						 args.add(commandWords[2]);
						 args.add(commandWords[3]);
						 setChanged();
						 notifyObservers(args);
						 args.clear();
					 }
					 else{
						 out.write("Number of parameters is not match to command 'save maze' ");
						 out.flush();
					 }
				}
				// solve command
				else if(line.startsWith("solve")){
					 if(commandWords.length == 3){
						 args.add("solve");
						 args.add(commandWords[1]);
						 args.add(commandWords[2]);
						 setChanged();
						 notifyObservers(args);
						 args.clear();
					 }
					 else{
						 out.write("Number of parameters is not match to command 'solve' ");
						 out.flush();
					 }
				}
				// file size command
				else if(line.startsWith("file size")){
					
					 if(commandWords.length == 3){
						 args.add("file size");
						 args.add(commandWords[2]);
						 setChanged();
						 notifyObservers(args);
						 args.clear();
					 }
					 else{
						 out.write("Number of parameters is not match to command 'file size' ");
						 out.flush();
					 }
				}
				// display cross section by X command
				else if(line.startsWith("display cross section by")){
					
					 if(commandWords.length == 8 && commandWords[6].equals("for")){
						args.add("display cross section by");
						args.add(commandWords[4]);
						args.add(commandWords[5]);
						args.add(commandWords[7]);
						setChanged();
						notifyObservers(args);
						args.clear();
					 }
					else{
							out.write("Syntax is not match to command 'display cross section by' ");
							out.flush();
					}
				}
				// display command
				else if(line.startsWith("display")){
					
					 if(commandWords.length == 2){
						 args.add("display");
						 args.add(commandWords[1]);
						 setChanged();
					     notifyObservers(args);
					     args.clear();
					 }
					 else{
						 out.write("Number of parameters is not match to command 'display'");
						 out.flush();
					 }
				}
				// generate 3d maze command
				else if(line.startsWith("generate 3d maze")){
					
					if(commandWords.length == 7){
						args.add("generate 3d maze");
						args.add(commandWords[3]);
						args.add(commandWords[4]);
						args.add(commandWords[5]);
						args.add(commandWords[6]);
						setChanged();
						notifyObservers(args);
						args.clear();
					}
					else{
							out.write("Syntax is not match to command 'generate 3d maze' ");
							out.flush();
						 }
				}
				else{	
					out.println("Invalid command!");
					out.flush();
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The function print to out source the list of commands 
	 */
	private void printCommands(){
		
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
	
}
