package Model;

import PolynomialPack.DivisionPolynomial;
import PolynomialPack.IntegerPolynom;

// Crée une instance d'un générateur de chaine binaire pour encoder le format du QRcode
// la chaine binaire du format est encodée via le code Bose-Chaudhuri-Hocquenghem (15,5)
// tel qu'indiqué dans la spécification du QRcode.
//
// Chaque chaine est composée de 15 bits qui sont constitués ainsi:
//
// Les 5 premiers bits de la chaine codent le format, qui consiste en la concaténation de
// l'indicateur binaire du niveau de correction d'erreur
// 							+
// l'indicateur binaire du masque utilisé dans le QRcode.
//
// Les 10 derniers bits de la chaine codent la correction d'erreur pour les 5 bits du format
public class FormatCorrector extends AbstractCorrector {
	
	private static String[][] m_errorCorrectionLevels = {{"L","M","Q","H"},{"01","00","11","10"}};
	private static String[] m_masks = {"000","001","010","011","100","101","110","111"};
	private static String m_mask = "101010000010010";
	private DivisionPolynomial m_division;
	private IntegerPolynom m_diviseur;
	private IntegerPolynom m_dividende;

	public FormatCorrector()
	{
		m_division = new DivisionPolynomial();

		// Générateur polynomial qui sert de diviseur
		// toujours égal à x^10 + x^8 + x^5 + x^4 + x^2 + x + 1
		m_diviseur = new IntegerPolynom();
		m_diviseur.addNewTerme(1, 10);
		m_diviseur.addNewTerme(0, 9);
		m_diviseur.addNewTerme(1, 8);
		m_diviseur.addNewTerme(0, 7);
		m_diviseur.addNewTerme(0, 6);
		m_diviseur.addNewTerme(1, 5);
		m_diviseur.addNewTerme(1, 4);
		m_diviseur.addNewTerme(0, 3);
		m_diviseur.addNewTerme(1, 2);
		m_diviseur.addNewTerme(1, 1);
		m_diviseur.addNewTerme(1, 0);
	}

	// Retourne la chaine du format avec la correction d'erreur en fonction du niveau de correction d'erreur (L,M,Q,H)
	// et de l'indice du masque choisi pour généré le QRcode.
	// Il y a huit masques, et l'index s'étend de 0 à 7 (et non de 1 à 8).
	public String getFormatBinaryString(String errorCorrectionLevel, int maskIndex)
	{
		if ( 0 > maskIndex || maskIndex > 7 || errorCorrectionLevel.length() > 1 ||
				!(
					errorCorrectionLevel.equalsIgnoreCase("L") ||
					errorCorrectionLevel.equalsIgnoreCase("M") ||
					errorCorrectionLevel.equalsIgnoreCase("Q") ||
					errorCorrectionLevel.equalsIgnoreCase("H")
				)
			)
			return null;	// les paramètres sont incorrects

		String format = "";	// Les 5 premiers bits de la chaine
		
		// Récupération de l'indicateur binaire du niveau de correction d'erreur
		for (int i=0; i<4; i++)
		{
			if (m_errorCorrectionLevels[0][i].equals(errorCorrectionLevel))
			{
				format += m_errorCorrectionLevels[1][i];	// On a récupéré l'indicateur binaire
				break;	// On sort de la boucle
			}
		}
		
		// Ajout de l'indicateur binaire du masque
		format += m_masks[maskIndex];
		
		m_dividende = getPolynomFromBinaryString(format);
		m_dividende = raiseTo(m_dividende,10);	// Multiplie le polynome par la puissance (15-5) = 10
		
		IntegerPolynom reste = m_division.createRemainderPolynom(m_diviseur, m_dividende);	// Effectue la division polynomiale et retourne le reste
		addNullTerms(reste);	// Ajout des éventuels termes nuls de degrés inférieurs au plus petit degré du polynome et supérieurs à 0
		String binaryReste = getBinaryStringFromPolynom(reste);	// Récupération de la chaine binaire à partir du polynome existant
		while (binaryReste.length() < 10)	// Ajout de coefficients nulls à la fin du reste pour obtenir 10 octets de longueur
			binaryReste = "0" + binaryReste;
			
		String result = format + binaryReste;	// Création de la chaine de correction d'erreurs pour le format
		
		return XORwithMask(result,m_mask);	// Retourne le résulat de l'application d'un OU-EXCLUSIF entre la chaine précédente et le masque prédéfini
	}
	
	private String XORwithMask(String binaryString, String binaryMask)
	{
		if (binaryString.length() != binaryMask.length()) return null;
		
		String result = "";
		
		int a,b,xor;
		for (int i=0; i<binaryString.length(); i++)
		{
			if (binaryString.charAt(i) == '0') a = 0;
			else a = 1;
			
			if (binaryMask.charAt(i) == '0') b = 0;
			else b = 1;
			
			xor = a ^ b;
			result += xor;
		}
		return result;
	}
	
	public static void main(String[] args) {
		FormatCorrector fc = new FormatCorrector();
		
		String result = fc.getFormatBinaryString("L",0);
		String comparaison = "111011111000100";
				
		System.out.println(result);
		if (result.equals(comparaison)) System.out.print(" - IDENTIQUES\n");
		else System.out.print(" - DIFFERENTS\n");
	}
}
