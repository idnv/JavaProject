package view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import presenter.Properties;

public class ClassAutoForm extends BasicWindow{

	Object theNewClass;
	@SuppressWarnings("rawtypes")
	Class classToShow;
	
	public ClassAutoForm(String title, Class classToShow, Shell parent) {
		super(title, 300, 200, parent);
		this.classToShow = classToShow;
	}

	public static void main(String[] args) {
		/*
		Properties p = new Properties();
		ClassAutoForm win= new ClassAutoForm("maze example", p.getClass());
		win.run();
		p = (Properties)win.getTheNewClass();
		System.out.println(p.getNumOfThreadsInThreadPool());
		System.out.println(p.getDefaultSolver());
		System.out.println(p.getView());
		*/
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2,false));
		
		// present's class name
		Label titel = new Label(shell,SWT.CENTER);		
		titel.setText(classToShow.getSimpleName());
		titel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		
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
		            
			Button button = new Button(shell, SWT.PUSH);
			button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
			button.setText("Save");
			button.addSelectionListener( new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					/*
					try {
						theNewClass = classToShow.newInstance();
						for(Field field : fields){
							field.setAccessible(true);
							field.set(theNewClass, dataMembersValues.get(field.getName()).getText());
							
							if (!field.getType().getSimpleName().equals("String")) {
								field.setAccessible(true);
								field.set(theNewClass, Integer.parseInt(dataMembersValues.get(field.getName()).getText()));
							} else {
								field.setAccessible(true);
								field.set(theNewClass, dataMembersValues.get(field.getName()).getText());
							}
						}
						
						
						
					} catch (InstantiationException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					*/
					shell.dispose();
					}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		
			
	}
		
	/**
	 * @return the theNewClass
	 */
	public Object getTheNewClass() {
		return theNewClass;
	}
	
	
	
}
