package Model;

import PolynomialPack.IntegerPolynom;
import PolynomialPack.TermeEntier;

// Implémente les opérations de base pour les générateurs de correction d'erreurs que sont par exemple FormatCorrector et VersionCorrector
public abstract class AbstractCorrector {
	
	// Multiplie le polynome à la puissance power
	protected IntegerPolynom raiseTo(IntegerPolynom p, int power)
	{
		// Copie locale du polynome passé en paramètres
		IntegerPolynom myPoly = (IntegerPolynom)p.clone();

		// Multiplication du polynome à la puissance power
		for (int i=0; i<p.getNbTermes(); i++)
		{
			// Il suffit de faire une somme de l'exposant courant + power
			myPoly.getTermeAt(i).setExposant(p.getTermeAt(i).getExposant() + power);
		}
		
		// Ajoute les termes nuls en partant du plus petit degré et en allant jusqu'à zéro
		addNullTerms(myPoly);

		// Le polynome est prêt, on le retourne
		return myPoly;
	}
	
	// Ajoute les tous termes nuls de plus petits degrés au polynome
	protected void addNullTerms(IntegerPolynom p)
	{
		// Tri du polynome par degrés décroissant (même si en théorie il est déjà trié)
		p.sortByExposants();

		if (p.getNbTermes()>0)
		{
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
	}

	// Retourne le polynome avec coefficients entiers égaux à 0 ou 1, et associé à cette chaine binaire
	protected IntegerPolynom getPolynomFromBinaryString(String binaryString)
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
	protected String getBinaryStringFromPolynom(IntegerPolynom p)
	{
		String result = "";
		
		p.sortByExposants();
		
		for (int i=0; i<p.getNbTermes(); i++)
		{
			result += ((TermeEntier)p.getTermeAt(i)).getCoefficient();
		}
		return result;
	}

	// Retourne une chaine de longueur length représentant la conversion binaire
	// du nombre entier naturel passé en paramètre
	protected String binaryStringFromInteger(int entier, int length)
	{
		try{
			// Conversion de la version en binaire
			String value = Integer.toBinaryString(entier);

			// Ajout de zéro à gauche pour remplir la chaine jusqu'à la la longueur désirée
			while (value.length() < length)
				value = "0" + value;

			return value;
		}
		catch(Exception e)
		{
			System.err.println("AbstractCorrector -> binaryStringFromInteger(int entier, int length) : ");
			e.printStackTrace();
			return null;
		}
	}
	
}
