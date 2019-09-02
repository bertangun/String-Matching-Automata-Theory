import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
				
		Text textReader = new Text("text.txt");              // text class'ýnýn contructor'ý çaðrýldý.
		ArrayList<String> text = textReader.getArrayList(); //textReader array'a atandý.   
		
		String pattern = "automata";   
				
		Source source = new Source();
		  
		source.computeTransitionFunction(pattern.toCharArray());
		
        long systemStartTimeString = System.nanoTime();
		
		source.naiveStringMatch(text, pattern);          //text icinde pattern'i arar.(naiveStringMatch)
		
		long stringMatchingTime = System.nanoTime() - systemStartTimeString;	
		
		long systemStartTimeFA = System.nanoTime();
		
		source.finiteAutomataMatcher(text, pattern);     //text icinde pattern'i arar.(finiteAutomata)
		
		long faMatchingTime = System.nanoTime() - systemStartTimeFA;
		
		System.out.println("Time for Naive-String-Matching: " + (float)stringMatchingTime / 1000000  + " ms");
		System.out.println("Time for Finite-Automata-Matcher: " + (float)faMatchingTime / 1000000 + " ms");
		
	}
}

