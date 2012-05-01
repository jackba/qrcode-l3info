package Model;

import PolynomialPack.DivisionPolynomial;
import PolynomialPack.IntegerPolynom;
import PolynomialPack.TermeEntier;

// Crée une instance d'un générateur de chaine binaire pour encoder la version du QRcode
// la chaine binaire de version est encodée via le code Bose-Chaudhuri-Hocquenghem (18,6)
// Tel qu'indiqué dans la spécification du QRcode
public class VersionCorrector {

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

		String nbrConverti = binaryStringFromInteger(version);
		
		m_dividende = getPolynomFromBinaryString(nbrConverti);
		m_dividende = raiseToBCHeighteenSix(m_dividende);
		
		IntegerPolynom reste = m_division.createRemainderPolynom(m_diviseur, m_dividende);	// Effectue la division polynomiale et retourne le reste
		addNullTerms(reste);	// Ajout des éventuels termes nuls de degrés inférieurs au plus petit degré du polynome et supérieurs à 0
		String binaryReste = getBinaryStringFromPolynom(reste);	// Récupération de la chaine binaire à partir du polynome existant
		while (binaryReste.length() < 12)	// Ajout de coefficients nulls à la fin du reste pour obtenir 12 octets de longueur
			binaryReste = "0" + binaryReste;
			
		String result = nbrConverti + binaryReste;	// Création de la chaine de correction d'erreurs pour la version
		
		return result;
	}

	// Multiplie le polynome pour l'élever à la puissance (18-6)
	private IntegerPolynom raiseToBCHeighteenSix(IntegerPolynom p)
	{
		// Copie locale du polynome passé en paramètres
		IntegerPolynom myPoly = (IntegerPolynom)p.clone();

		for (int i=0; i<p.getNbTermes(); i++)
		{
			// Multiplication par 18-6 = 12.
			// Il suffit donc de faire une somme de l'exposant courant + 12 
			myPoly.getTermeAt(i).setExposant(p.getTermeAt(i).getExposant() + 12);
		}
		
		// Ajoute les termes nuls en partant du plus petit degré et en allant jusqu'à zéro
		addNullTerms(myPoly);

		// Le polynome est prêt, on le retourne
		return myPoly;
	}
	
	// Ajoute les tous termes nuls de plus petits degrés au polynome
	private void addNullTerms(IntegerPolynom p)
	{
		// Tri du polynome par degrés décroissant (même si en théorie il est déjà trié)
		p.sortByExposants();

		// On récupère l'exposant du plus petit degré (qui n'est pas forcément le degré 0) présent dans le polynome
		int smallerExp = p.getTermeAt(p.getNbTermes()-1).getExposant();

		// Le dernier terme du polynome n'est pas de degré 0
		if (smallerExp > 0)
		{
			// On rajoute tous les termes nuls depuis le terme de
			// plus petit degré déjà présent dans le polynome jusqu'au
			// degré 0
			for (int i=smallerExp-1; i>=0; i--)
			{
				p.addNewTerme(0, i);
			}
		}
	}

	// Retourne le polynome avec coefficients entiers égaux à 0 ou 1, et associé à cette chaine binaire
	private IntegerPolynom getPolynomFromBinaryString(String binaryString)
	{
		// Création du polynome
		IntegerPolynom p = new IntegerPolynom();
		for (int i=0; i<binaryString.length(); i++)
		{
			if (binaryString.charAt(i) == '1')
				p.addNewTerme(1, binaryString.length()-1-i);
			else
				p.addNewTerme(0, binaryString.length()-1-i);
		}
		return p;
	}
	
	// Retourne une chaine binaire correspondant au polynome entier passé en paramètre
	private String getBinaryStringFromPolynom(IntegerPolynom p)
	{
		String result = "";
		
		p.sortByExposants();
		
		for (int i=0; i<p.getNbTermes(); i++)
		{
			result += ((TermeEntier)p.getTermeAt(i)).getCoefficient();
		}
		return result;
	}

	// Retourne une chaine de longueur 6 représentant la conversion binaire
	// du nombre entier naturel passé en paramètre
	private String binaryStringFromInteger(int entier)
	{
		try{
			// Conversion de la version en binaire
			String value = Integer.toBinaryString(entier);

			// Ajout de zéro à gauche pour combler si la chaine n'a pas 6 caractères
			if (value.length() < 6)
			{
				int diff = 6 - value.length();
				String zeros = "";
				for (int i=0; i<diff; i++) zeros += "0";
				value = zeros + value;
			}

			return value;
		}
		catch(Exception e)
		{
			System.err.println("ErrorCorrector -> BinaryStringFromInteger(int entier) : ");
			e.printStackTrace();
			return null;
		}
	}

	/*
	public static void main(String[] args) {
		VersionCorrector vs = new VersionCorrector();
		
		String result = vs.getVersionBinaryString(32);
		String comparaison = "100000100111010101";
				
		System.out.println(result);
		if (result.equals(comparaison)) System.out.print(" - IDENTIQUES\n");
		else System.out.print(" - DIFFERENTS\n");
	}
	*/
}
