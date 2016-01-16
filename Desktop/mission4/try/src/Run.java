

import java.util.ArrayList;

public class Run {

	public static void main(String[] args) {
	//	ArrayList<String> array= new ArrayList<>();
	//	int a = 45;
	//	array.add(String.valueOf(a));
	//	System.out.println(array);
		
		String text = "1246";
		if (text.matches("[0-9]+"))
				System.out.println("only Numbers");
		else
			System.out.println("Not only Numbers");
	}
	
}
