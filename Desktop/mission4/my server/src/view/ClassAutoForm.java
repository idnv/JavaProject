package view;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;



public class ClassAutoForm extends BasicWindow{

	private boolean changeSucceeded = false;
	Object newCreatedClass;
	@SuppressWarnings("rawtypes")
	Class classToShow;
	boolean isSuccessfullyCreated = false;
	
	@SuppressWarnings("rawtypes")
	public ClassAutoForm(String title, Class classToShow, Shell parent,int width, int height) {
		super(title, width, height, parent);
		this.classToShow = classToShow;
	}
	
	
	@SuppressWarnings("rawtypes")
	public ClassAutoForm(String title, Class classToShow) {
		super(title, 300, 200);
		this.classToShow = classToShow;
	}

	public static void main(String[] args) {
		/*///////////// test //////////////
		Properties p = new Properties();
		ClassAutoForm win= new ClassAutoForm("maze example", p.getClass());
		win.run();
		p = (Properties)win.getNewCreatedClass();
		System.out.println(p.getNumOfThreadsInThreadPool());
		System.out.println(p.getDefaultSolver());
		System.out.println(p.getView());
		//*/
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2,false));
		
		Field[] fields= classToShow.getDeclaredFields();
		HashMap<String,Text> dataMembersValues = new HashMap<String, Text>();
		
		for(Field f : fields){
			
			Label lable = new Label(shell, SWT.READ_ONLY);
			lable.setText(f.getName());
			lable.setLayoutData(new GridData(SWT.FILL ,SWT.TOP ,false ,false ,1 ,1));
			
			dataMembersValues.put(f.getName(), new Text(shell, SWT.BORDER));
			dataMembersValues.get(f.getName()).setText("");
			dataMembersValues.get(f.getName()).setLayoutData(new GridData(SWT.FILL ,SWT.TOP ,false ,false ,1 ,1));
		}
		    // Save button        
			Button saveButton = new Button(shell, SWT.PUSH);
			saveButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
			saveButton.setText("OK");
			saveButton.addSelectionListener( new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					
					try {
						newCreatedClass = classToShow.newInstance();
						// 
						for(Field field : fields){
							field.setAccessible(true);
							
							if(!(dataMembersValues.get(field.getName()).getText()).equals("")){
								if (!field.getType().equals(String.class)) {
									field.set(newCreatedClass, Integer.parseInt(dataMembersValues.get(field.getName()).getText()));
								} 
								else {
									field.set(newCreatedClass, dataMembersValues.get(field.getName()).getText());
								}
							
							}
							else{
								setEror("All fields sould filled!");
								return;
							}
						}
						
						changeSucceeded = true;
						
					} catch (InstantiationException | IllegalAccessException e) {

						e.printStackTrace();
					}
					
					shell.dispose();
					}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		
			// Save button        
			Button cancelButton = new Button(shell, SWT.PUSH);
			cancelButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
			cancelButton.setText("Cancel");
			cancelButton.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					shell.dispose();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
	}
	
	
	/**
	 * @return isSuccessfullyCreated - if the create succeeded
	 */
	public boolean isSuccessfullyCreated() {
		return isSuccessfullyCreated;
	}

	/**
	 * @return the new object that created
	 */
	public Object getNewCreatedClass() {
		return newCreatedClass;
	}


	public boolean isChangeSucceeded() {
		return changeSucceeded;
	}


	public void setChangeSucceeded(boolean changeSucceeded) {
		this.changeSucceeded = changeSucceeded;
	}
	
	private void setEror(String eror){
		Display.getCurrent().syncExec(new Runnable() {
			
			@Override
			public void run() {
				MessageBox errorBox =  new MessageBox(shell, SWT.ICON_ERROR); 
				errorBox.setMessage(eror);
				errorBox.setText("Error");
				errorBox.open();				
			}
		});
	}
	
	
}
