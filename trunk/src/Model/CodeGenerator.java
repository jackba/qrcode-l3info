package Model;

import java.io.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.filter.*;
import java.util.List;
import java.util.Iterator;

public class CodeGenerator {
	
	private int longueur;
	private String resultBinaire;
	private String texte;
	private int version;
	private String mode; // 0->Numérique - 1->Alphanumérique - 2->Byte - 3->Kanji
	private char level; // L - M - Q - H

	public CodeGenerator(String texte, int mode, char level, int version)
	{
		//Initialisation des variable de classe.
		if(mode == 0){
			this.mode="numeric";
		}else if(mode == 1){
			this.mode="alphanumeric";			
		}else if(mode == 2){
			this.mode="byte";				
		}else if(mode == 3){
			this.mode="kanji";				
		}
		
		this.level = level;
		this.texte = texte;
		this.longueur = texte.length();
		this.resultBinaire = new String("");

		
		if(version==0){
			this.version = searchVersion();
		}else if(version==-1){
			System.out.println("nombre de caractères trop importants");
		}else{
			this.version=version;
		}
	}

	//Methode qui permet de trouver quelle est la meilleure version QRCode avec les paramètres données.
	//Cette méthode utilise le fichier XML numberOfSymbolCharacter.xml
	private int searchVersion(){
		org.jdom.Document document = null;
		Element racine;

		SAXBuilder sxb = new SAXBuilder();

		try {
			document = sxb.build(new File("/home/kbeutier/workspace/QrCode_Generator/src/Donnees/numberOfSymbolCharacter.xml"));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//On initialise un nouvel élément racine avec l'élément racine du document.
		racine = document.getRootElement();

		//On crée une List contenant tous les noeuds "etudiant" de l'Element racine
		List listVersion = racine.getChildren("version");

		Element version;
		List listLevel;
		Element level;
		Element capacite;
		for (int i = 0; i < listVersion.size(); i++){
			version = (Element) listVersion.get(i);
			listLevel = version.getChildren("correction_level");
			for(int j = 0; j < listLevel.size(); j++){
				level = (Element) listLevel.get(j);
				if(level.getAttributeValue("level").charAt(0)==this.level){
					capacite = level.getChild("data_capacity");
					if(Integer.parseInt(capacite.getChild("byte").getValue())>this.longueur){
						return Integer.parseInt(version.getAttributeValue("numero"));
					}
				}
			}
		}
		return -1; // Retourne -1 si aucune version existe pour le nombre de caractère données.
	}
	
	public void generer(){
		// Selon le mode sélectionné on assigne les 4 premiers bits.
		if(this.mode == "byte"){
			genererByte();
		}else{
			System.out.println("La génération de ce mode est en cours de dévelopement.");
		}
	}
	
	private void genererByte(){
		// Mode Indicator
		this.resultBinaire = "0100"; // Mode indicator
		
		// Count Indicator
		String countIndic = new String(Integer.toBinaryString(this.longueur));
		if(this.version>=1 && this.version <=9){
			while (countIndic.length()<8){
				countIndic = "0"+countIndic;
			}
		}else if(this.version>=10 && this.version <=40){
			while (countIndic.length()<16){
				countIndic = "0"+countIndic;
			}
		}else{
			System.out.println("Version non gérer");
		}
		this.resultBinaire = this.resultBinaire+countIndic;
		
		// Texte
		String lettre;
		for (int i = 0; i < this.longueur; i++){
			lettre = Integer.toBinaryString(texte.charAt(i));
			while(lettre.length()<8){
				lettre = "0"+lettre;
			}
			this.resultBinaire = this.resultBinaire+lettre;
		}
	}


	private int convertCharAlphanum(char select) {
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
	
	public String getResultBinaire(){
		return this.resultBinaire;
	}
	
	public static void main(String[] args)
	{
		CodeGenerator cg1 = new CodeGenerator("totto", 2, 'H', 0);
		cg1.generer();
		System.out.println(cg1.getResultBinaire());
	}

}

