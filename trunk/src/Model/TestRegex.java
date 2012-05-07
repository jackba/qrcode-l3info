package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

// Cette classe a uniquement pour but de fournir une méthode de test des expressions régulières
public class TestRegex {

	public static void main(String[] args) throws IOException{
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		String regex;
		String line;
		
		while (true) {

			System.out.println("\nEntrez votre regex: ");
			regex = br.readLine();

			System.out.println("\nEntrez la chaine à introspecter: ");
			line = br.readLine();

			boolean found = Pattern.matches(regex, line);
			
			if (found) System.out.println("Concordance");
			else System.out.println("Regex non-vérifiée");
		}
	}
	
}
