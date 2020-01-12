package Picture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import intrepreteur.Interpreteur;

public class MainIntrep {


	private static Scanner scanner;

	public static void main(String[] args) throws FileNotFoundException {
      
       try {
		System.out.println(3/0);
	} catch (Exception e) {
		System.out.println("/ by zero");
	}
        
        File f = new File("C:\\Users\\A\\Desktop\\comp\\source.txt");
        
        scanner = new Scanner(new BufferedReader(new FileReader(f)));
        String text="";
        while(scanner.hasNextLine()){
            text += scanner.nextLine()+"\n";
        }
        new Interpreteur(text);
    }
    
    
    
}