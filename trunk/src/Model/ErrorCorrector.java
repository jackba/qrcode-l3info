package Model;

import PolynomialPack.DivisionPolynomial;
import PolynomialPack.IntegerPolynom;
import PolynomialPack.TermeEntier;

/*
 *  Classe de correction d'erreur pour un message binaire QRcode
 *  Permet de générer une chaine binaire de correction d'erreur
 *  en fonction du message binaire et d'un nombre d'octets de
 *  correction désirés.
 *  
 *  Exemple d'utilisation:
 *  
 *  // message binaire "HELLO WORLD" codé selon la Version 1 - Correction d'erreur Q de la norme QRcode
 *  String msg = "00100000010110110000101101111000110100010111001011011100010011010100001101000000111011000001000111101100";
 *  
 *  ErrorCorrector ec = new ErrorCorrector();
 *  String correction = ec.getErrorCorrectionString(msg, 13);
 *  System.out.println(correction);
 *  // Affiche la chaine de correction suivante :
 *  // 10101000010010000001011001010010110110010011011010011100000000000010111000001111101101000111101000010000
 */
public class ErrorCorrector {
	
	private DivisionPolynomial m_divPolynomial;
	
	public ErrorCorrector()
	{
	}
	
	/*
	 *  Retourne une chaine binaire représentant les données et la correction d'erreurs
	 */
	public String getErrorCorrectionString(String donneesBinaires, Specification spec, String level)
	{
		CorrectionLevel corrLvl = null;
		
		// Recherche du niveau correspondant dans la spécification
		for (int i=0; i<spec.getCorrectionLevels().size(); i++)
			if (spec.getCorrectionLevels().get(i).getLevel().equalsIgnoreCase(level)) // Le niveau a été trouvé
				corrLvl = spec.getCorrectionLevels().get(i);	// Récupération du niveau
		
		if (corrLvl == null) return null;	// Le niveau n'existe pas dans la spécification
		
		// Création des matrices 2D de données et de corrections
		
		// Récupération du plus grand nombre de mots (= octets) de données dans un bloc du niveau de correction
		int line = 0;
		int column = 0;
		for (int i=0; i<corrLvl.getBlocks().size(); i++)
		{
			line += corrLvl.getBlocks().get(i).getNumber();
			if (corrLvl.getBlocks().get(i).getDataWords() > column)
				column = corrLvl.getBlocks().get(i).getDataWords();
		}
		
		// Création de la matrice 2D de données (largeur / hauteur)
		String[][] matriceData = new String[line][column];
		
		// Initialisation de la matrice de données
		/*
		for (int i=0; i<line; i++)
			for (int j=0; j<column; j++)
				matriceData[line][column] = null;
		*/
		
		// Récupération du plus grand nombre de mots (= octets) de corrections dans un bloc du niveau de correction
		column = 0;
		for (int i=0; i<corrLvl.getBlocks().size(); i++)
			if (corrLvl.getBlocks().get(i).getCorrectionWords() > column)
				column = corrLvl.getBlocks().get(i).getCorrectionWords();
		
		// Création de la matrice 2D de correction (largeur / hauteur)
		String[][] matriceCorrection = new String[line][column];
		
		// Initialisation de la matrice de correction
		/*
		for (int i=0; i<line; i++)
			for (int j=0; j<column; j++)
				matriceCorrection[line][column] = null;
				*/
		
		// Remplissage des matrices
		
		// Remplissage de la matrice de données
		int curWord = 0;	// Indice courant dans la chaine de données binaires
		int curLine = 0;	// Colonne courante dans la matrice de données
		for (int i=0; i<corrLvl.getBlocks().size(); i++)	// Pour chaque spécification de bloc
		{
			for (int j=0; j<corrLvl.getBlocks().get(i).getNumber(); j++)	// Créer un nouveau bloc de cette spécification "number" fois
			{
				for (int k=0; k<corrLvl.getBlocks().get(i).getDataWords(); k++)	// Créer un nouvel octet à partir de la chaine de données et l'assigner dans la matrice
				{
					matriceData[curLine][k] = donneesBinaires.substring(curWord, curWord+8);
					curWord += 8;
				}
				curLine ++;
			}
		}
		
		// Remplissage de la matrice de correction
		curWord = 0;	// Indice courant dans la chaine de données binaires
		curLine = 0;	// Colonne courante dans la matrice de correction
		String curData;	// Chaine de données pour le bloc courant
		String curCorrection;	// Chaine de correction pour le bloc courant
		for (int i=0; i<corrLvl.getBlocks().size(); i++)	// Pour chaque spécification de bloc
		{
			for (int j=0; j<corrLvl.getBlocks().get(i).getNumber(); j++)	// Créer un nouveau bloc de cette spécification "number" fois
			{
				// Créer la chaine de données pour le bloc courant
				curData = donneesBinaires.substring(curWord, curWord + (corrLvl.getBlocks().get(i).getDataWords()*8));
				curWord += corrLvl.getBlocks().get(i).getDataWords()*8;
				
				// Créer la chaine de correction pour le bloc courant
				curCorrection = getCorrectionString(curData,corrLvl.getBlocks().get(i).getCorrectionWords());
				
				// Pour tous les mots de la chaine de correction
				for (int k=0; k<curCorrection.length(); k++)
				{
					// Créer un nouvel octet (=mot) à partir de la chaine de correction et l'assigner dans la matrice
					matriceCorrection[curLine][k] = curCorrection.substring(k*8, k*8+8);
				}
				curLine ++;
			}
		}
		
		System.out.println("Hello");
		
		return null;
	}
	
	/*
	 *  Retourne une chaine binaire de correction d'erreur QRcode et
	 *  comportant nbOctetsCorrection pour le messageBinaire spécifié
	 */
	private String getCorrectionString(String messageBinaire, int nbOctetsCorrection)
	{
		m_divPolynomial = new DivisionPolynomial(messageBinaire, nbOctetsCorrection);
		IntegerPolynom myErrorCorrector = m_divPolynomial.createErrorCorrectorPolynom();
		
		return errorCorrectorPolynomToBinaryString(myErrorCorrector);
	}
	
	// Convertit un entier (base 10) en chaine de caractère binaire (base 2)
	private String BinaryStringFromInteger(int entier)
	{
		String value;
		try{
			value = Integer.toBinaryString(entier);
			
			// Ajout de zéro à gauche pour combler les octets incomplets
			if (value.length() < 8)
			{
				int diff = 8 - value.length();
				String zeros = "";
				for (int i=0; i<diff; i++) zeros += "0";
				value = zeros + value;
			}
			return value;
		}
		catch(Exception e)
		{
			System.err.println("ErrorCorrector -> BinaryStringFromInteger(int entier) : " + e.toString());
			return null;
		}
	}	
	
	private String errorCorrectorPolynomToBinaryString(IntegerPolynom p)
	{
		String chaine = "";
		
		// Conversion du coefficient de chaque terme en binaire et ajout dans la chaine
		for (int i=0; i<p.getNbTermes(); i++)
			chaine += BinaryStringFromInteger(((TermeEntier)p.getTermeAt(i)).getCoefficient());
		
		return chaine;
	}
	
}
