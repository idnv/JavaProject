

import java.util.ArrayList;

public class Run {

	volatile static Integer x = null;
	
	public static void main(String[] args) {
	
		if(x == null)
			x = new Integer(3);
		else 
			System.out.println("blabla");
		
		System.out.println(x);
		
		try {
			Thread.sleep(100000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
