package Model;

import PolynomialPack.DivisionPolynomial;
import PolynomialPack.IntegerPolynom;

// Crée une instance d'un générateur de chaine binaire pour encoder la version du QRcode
// la chaine binaire de version est encodée via le code Bose-Chaudhuri-Hocquenghem (18,6)
// Tel qu'indiqué dans la spécification du QRcode.
//
// Chaque chaine est composée de 18 bits qui sont constitués ainsi:
// Les 6 premiers bits de la chaine codent le numéro de version en binaire et les 12
// derniers bits codent la correction d'erreur pour les 6 bits du numéro de version
public class VersionCorrector extends AbstractCorrector {

	private DivisionPolynomial m_division;
	private IntegerPolynom m_diviseur;
	private IntegerPolynom m_dividende;

	public VersionCorrector()
	{
		m_division = new DivisionPolynomial();

		// Générateur polynomial qui sert de diviseur
		// toujours égal à x^12 + x^11 + x^10 + x^9 + x^8 + x^5 + x^2 + 1
		m_diviseur = new IntegerPolynom();
		m_diviseur.addNewTerme(1, 12);
		m_diviseur.addNewTerme(1, 11);
		m_diviseur.addNewTerme(1, 10);
		m_diviseur.addNewTerme(1, 9);
		m_diviseur.addNewTerme(1, 8);
		m_diviseur.addNewTerme(0, 7);
		m_diviseur.addNewTerme(0, 6);
		m_diviseur.addNewTerme(1, 5);
		m_diviseur.addNewTerme(0, 4);
		m_diviseur.addNewTerme(0, 3);
		m_diviseur.addNewTerme(1, 2);
		m_diviseur.addNewTerme(0, 1);
		m_diviseur.addNewTerme(1, 0);
	}

	public String getVersionBinaryString(int version)
	{
		if (version > 40) return null;	// Il n'existe que 40 versions de Qrcode

		String nbrConverti = binaryStringFromInteger(version,6);
		
		m_dividende = getPolynomFromBinaryString(nbrConverti);
		m_dividende = raiseTo(m_dividende,12);	// Multiplie le polynome par la puissance (18-6) = 12
		
		IntegerPolynom reste = m_division.createRemainderPolynom(m_diviseur, m_dividende);	// Effectue la division polynomiale et retourne le reste
		addNullTerms(reste);	// Ajout des éventuels termes nuls de degrés inférieurs au plus petit degré du polynome et supérieurs à 0
		String binaryReste = getBinaryStringFromPolynom(reste);	// Récupération de la chaine binaire à partir du polynome existant
		while (binaryReste.length() < 12)	// Ajout de coefficients nulls à la fin du reste pour obtenir 12 octets de longueur
			binaryReste = "0" + binaryReste;
			
		String result = nbrConverti + binaryReste;	// Création de la chaine de correction d'erreurs pour la version
		return result;
	}

	/*
	public static void main(String[] args) {
		VersionCorrector vs = new VersionCorrector();
		
		String result = vs.getVersionBinaryString(13);
		String comparaison = "001101100001000111";
				
		System.out.println(result);
		if (result.equals(comparaison)) System.out.print(" - IDENTIQUES\n");
		else System.out.print(" - DIFFERENTS\n");
	}
	*/
	
}
