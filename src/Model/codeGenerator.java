package Model;

public class codeGenerator {
	
	private int longueur;
	private String resultBinaire;

	public codeGenerator(String texte)
	{
		resultBinaire = new String("0010"); // On l'initialise a 0010 car on veux de l'alpha numérique (sera ammené à évolué ...) 
											// On traite uniquement le texte pour l'instant
		
		String intermediaire = new String(Integer.toBinaryString(texte.length()));
		while (intermediaire.length()<9){
			intermediaire = "0"+intermediaire;
		}
		
		System.out.println("Intermediaire = "+intermediaire);
		
		for(int i = 0; i < texte.length(); i++){
			System.out.println(Integer.toBinaryString(convertChar(texte.charAt(i))));
						
		}
	}
	
	public int convertChar(char select) {
		switch(select)
		{
		case '0': return 0;
		case '1': return 1;
		case '2': return 2;
		case '3': return 3;
		case '4': return 4;
		case '5': return 5;
		case '6': return 6;
		case '7': return 7;
		case '8': return 8;
		case '9': return 9;
		case 'A': return 10;
		case 'B': return 11;
		case 'C': return 12;
		case 'D': return 13;
		case 'E': return 14;
		case 'F': return 15;
		case 'G': return 16;
		case 'H': return 17;
		case 'I': return 18;
		case 'J': return 19;
		case 'K': return 20;
		case 'L': return 21;
		case 'M': return 22;
		case 'N': return 23;
		case 'O': return 24;
		case 'P': return 25;
		case 'Q': return 26;
		case 'R': return 27;
		case 'S': return 28;
		case 'T': return 29;
		case 'U': return 30;
		case 'V': return 31;
		case 'W': return 32;
		case 'X': return 33;
		case 'Y': return 34;
		case 'Z': return 35;
		case ' ': return 36;
		case '$': return 37;
		case '%': return 38;
		case '*': return 39;
		case '+': return 40;
		case '-': return 41;
		case '.': return 42;
		case '/': return 43;
		case ':': return 44;
		}
		return 0;
	}
}
