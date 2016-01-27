package view;

import java.time.LocalDateTime;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import controller.Controller;
import controller.Properties;
import controller.SocketInfo;

public class MyAdminView extends BasicWindow implements View {

	

	Controller controller;
	Table table;
	 Button stopServerButton, startServerButton;
	 TableItem [] items;
	 
	public MyAdminView(Controller controller, String title, int width, int height) {
		super(title, width, height);
		this.controller = controller;
		Image icon = new Image(display,"lib/server.png");
		Image background = new Image(display,"lib/server_background.jpg");
		this.shell.setImage(icon);
		this.shell.setBackgroundImage(background);
	}

	
	@Override
	void initWidgets() {
		
		    shell.setLayout(new GridLayout(2,false));
		    shell.addDisposeListener(new DisposeListener() {
				
				@Override
				public void widgetDisposed(DisposeEvent arg0) {
					
					controller.closeServer();
				}
			});
			// ------------------- Menu Bar -------------------------- //	
			Menu menuBar, fileMenu;
			MenuItem fileHeader;
			MenuItem fileExitItem,fileOpenPropertiesItem;
			
			menuBar = new Menu(shell, SWT.BAR);
			
			fileHeader = new MenuItem(menuBar, SWT.CASCADE);
			fileHeader.setText("File");
			
			fileMenu = new Menu(shell,SWT.DROP_DOWN);
			fileHeader.setMenu(fileMenu);
			
			// ---------------------------------------- openProperties (at menu bar) ----------------------------- //
			fileOpenPropertiesItem = new MenuItem(fileMenu,SWT.PUSH);
			fileOpenPropertiesItem.setText("Open Properties");
			fileOpenPropertiesItem.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					Properties properties = new Properties();
					ClassAutoForm win= new ClassAutoForm("Set Properties", properties.getClass(), shell,240,200);
					win.run();
					if(win.isSuccessfullyCreated){
						properties = (Properties)win.getNewCreatedClass();
						System.out.println(properties);
						controller.updateProperties(properties);
					}	
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			// ---------------------------------------- Exit (at menu bar) ----------------------------- //
			fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
			fileExitItem.setText("Exit");
			fileExitItem.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					shell.dispose();
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			//set menu bar
			shell.setMenuBar(menuBar);
			 // ---------------------------- table -------------------------------------------------- //
			
		    table = new Table(shell,  SWT.MULTI |  SWT.BORDER | SWT.FULL_SELECTION);
	        table.setLinesVisible(true);
	        table.setHeaderVisible(true);
	        
	        GridData data = new GridData(SWT.NONE, SWT.FILL, false, true, 1, 2);
	        table.setLayoutData(data);

	        String[] titles = {"ID", "IP","port", "Status", "Is Conected" ,"Connected Time", "Disconnected Time"};
	        
	        for (int i = 0; i < titles.length; i++) {
	            TableColumn column = new TableColumn(table, SWT.NONE);
	            column.setText(titles[i]);
	            table.getColumn(i).pack();
	        }

	       // ----------------------------------- start server Button --------------------------------------------//
	        startServerButton = new Button(shell, SWT.PUSH);
	        startServerButton.setText("Strat Server");
	        startServerButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
	        startServerButton.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					controller.start();
					startServerButton.setEnabled(false);
					stopServerButton.setEnabled(true);
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	        // ----------------------------------- stop server Button --------------------------------------------//
	        stopServerButton = new Button(shell, SWT.FLAT);
	        stopServerButton.setText("Stop Server");
	        stopServerButton.setLayoutData(new GridData(SWT.FILL, SWT.None, true, true, 1, 1));
	        stopServerButton.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					controller.closeServer();
					startServerButton.setEnabled(true);
					stopServerButton.setEnabled(false);
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	        // ----------------------------------- Kick Buttom --------------------------------------------//
	        Button kickClientsButton = new Button(shell, SWT.PUSH);
	        kickClientsButton.setText("kick");
	        kickClientsButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
	        kickClientsButton.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					
					 TableItem[] selection = table.getSelection();
					 if(selection.length > 0){
						 String [] soketsListHashCode = new String[selection.length];
						 for (int i = 0; i < selection.length; i++) {
							 soketsListHashCode[i] = selection[i].getText();
						}
						kickClients(soketsListHashCode);
					 }
					 
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	        // -------------------------------- Clear disconnected from table Button --------------------------------------//
	        Button clearDisconnectedClientsButton = new Button(shell, SWT.PUSH);
	        clearDisconnectedClientsButton.setText("Clear Disconnected");
	        clearDisconnectedClientsButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
	        clearDisconnectedClientsButton.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					clearDisconnectedSockets();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	}
	
	
	private void clearDisconnectedSockets()
	{
display.syncExec(new Runnable() {
			@Override
			public void run() {
			   items = table.getItems();	
			   
			   for (int i = 0; i < items.length; i++) {
					if(items[i].getText(4).equals("false")){
						int tableIndex = indexInTable(items[i].getText());
						table.remove(tableIndex);
						
					}
						
				}
			}
		});
	
		
		
	}
	
	private void updateItemInIndex(int index, SocketInfo info, String Privatekey){
		//"ID", "IP","port", "Status", "Is Conected" ,"Connected Time", "Disconnected Time"
		if(info.isConnected()){
			table.getItem(index).setForeground(display.getSystemColor(SWT.COLOR_GREEN));
			table.getItem(index).setText(0, Privatekey);
			table.getItem(index).setText(1, info.getIP());
			table.getItem(index).setText(2, ""+info.getSocket().getPort());
			table.getItem(index).setText(3, info.getStatus());
			table.getItem(index).setText(4, String.valueOf(info.isConnected()));
			table.getItem(index).setText(5, info.getConnectionTime().toString());
		}
		else{
			table.getItem(index).setForeground(display.getSystemColor(SWT.COLOR_RED));
			table.getItem(index).setText(0, Privatekey);
			table.getItem(index).setText(1, info.getIP());
			table.getItem(index).setText(2, ""+info.getSocket().getPort());
			table.getItem(index).setText(3, info.getStatus());
			table.getItem(index).setText(4, String.valueOf(info.isConnected()));
			table.getItem(index).setText(5, info.getConnectionTime().toString());
			table.getItem(index).setText(6, info.getDisconnectingTime().toString());
		}
		
		 for (int i = 0; i <table.getColumnCount(); i++) {
			 table.getColumn(i).pack();
		 }
	}
	
	private void AddNewItemToTable(SocketInfo info, String hashString) {
		TableItem item = new TableItem(table, SWT.NONE);   
        item.setText(0, hashString);
        item.setText(1, info.getIP());
        item.setText(2, ""+info.getSocket().getPort());
        item.setText(4, String.valueOf(info.isConnected()));
        item.setText(5, info.getConnectionTime().toString());
         // update roe color to green
        table.getItem(indexInTable(hashString)).setForeground(display.getSystemColor(SWT.COLOR_GREEN));
        
		 for (int i = 0; i <table.getColumnCount(); i++) {
			 table.getColumn(i).pack();
		 }
	}
	
	private int indexInTable(String SockethashCode){
	
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
			   items = table.getItems();				
			}
		});
		
		
		for (int i = 0; i < items.length; i++) {
			if(items[i].getText(0).equals(SockethashCode))
				return i;
		}
		
		return -1;
	}

	@Override
	public void updateClientsInfo(String hashCode, SocketInfo socketInfo) {
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				String hashString = hashCode;
				int tableIndex = indexInTable(hashString);
				if(tableIndex != -1)
					updateItemInIndex(tableIndex,socketInfo,hashString);
				else
					AddNewItemToTable(socketInfo,hashString);
			}
		});
		
	}

	@Override
	public void kickClients(String [] socketList) {
		//"ID", "IP","port", "Status", "Is Conected" ,"Connected Time", "Disconnected Time"
		for (int i = 0; i < socketList.length; i++) {
			
			int indexInTable = indexInTable(socketList[i]);
			if(!table.getItem(indexInTable).getText(4).equals("false")){
				// update table data
				table.getItem(indexInTable).setText(3, "Kicked");
				table.getItem(indexInTable).setText(4, "false");
				table.getItem(indexInTable).setText(6, LocalDateTime.now().toString());
				// update color of row in table
				table.getItem(indexInTable).setForeground(display.getSystemColor(SWT.COLOR_DARK_RED));
				// call the controller to cick
				controller.kick(socketList[i]);
			}
		}
		// pack table
		for (int i = 0; i <table.getColumnCount(); i++) {
			 table.getColumn(i).pack();
		 }
	}

	@Override
	public void exit() {
		controller.closeServer();
	}
}
