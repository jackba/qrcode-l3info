package PolynomialPack;
import java.util.regex.Pattern;

public class MessagePolynomial {
	
	private String m_msgBinaire;	// Chaine de 1 et de 0 générée par une autre classe
	private IntegerPolynom m_polynom;	// Polynome avec coefficients entiers
	
	public MessagePolynomial()
	{
		m_polynom = new IntegerPolynom();
	}
	
	public MessagePolynomial(String msgBinaire)
	{
		m_msgBinaire = msgBinaire;
		m_polynom = new IntegerPolynom();
	}
	
	// Crée un polynome avec coefficients entiers à partir de la chaine binaire de l'instance
	public IntegerPolynom createMessagePolynomial(int nbMotsDeCorrection)
	{
		return createMessagePolynomial(m_msgBinaire,nbMotsDeCorrection);
	}
	
	// Crée un polynome avec coefficients entiers à partir de la chaine binaire spécifiée
	public IntegerPolynom createMessagePolynomial(String msgBinaire, int nbMotsDeCorrection)
	{
		if (!isBinaryString(msgBinaire)) return null;	// La chaine n'est pas une chaine binaire
		
		if (msgBinaire.length() % 8 != 0) return null;	// La chaine n'est pas formée de blocs de 8 bits complets
		
		String octet;	// L'octet courant extrait de la chaine
		int coefficient;	// Le coefficient obtenu par conversion de l'octet courant
		
		/* 
		 * L'exposant du premier terme = (nombre d'octets dans la chaine) + (nombre de mots de correction d'erreur) - 1
		 * L'exposant du premier terme est l'exposant de plus haut degré.
		 * Les exposants des termes suivants seront tous de la forme: exposant = (exposant_degré_supérieur) - 1
		 */
		int exposant = (int)(msgBinaire.length() / 8) + nbMotsDeCorrection - 1;
		
		// La chaine est correctement formée
		int i=0;
		while (i<msgBinaire.length())	// Tant que nous ne sommes pas rendu au dernier octet (groupe de 8 bits)
		{
			octet = msgBinaire.substring(i,i+8);	// Récupération de l'octet i
			coefficient = intFromBinaryString(octet);	// Conversion du binaire vers entier
			m_polynom.addNewTerme(coefficient, exposant);	// Ajout du nouveau membre dans le polynome
			exposant --; // Décrémentation du degré de l'exposant pour l'octet suivant
			i += 8;// Incrémentation de l'index pour l'octet suivant
		}
		
		return m_polynom;		
	}
	
	// Convertit une chaine binaire (base 2) en entier (base 10)
	public Integer intFromBinaryString(String msgBinaire)
	{
		int value;
		try{
			value = Integer.parseInt(msgBinaire, 2);
		}
		catch(Exception e)
		{
			System.err.println("ErrorCorrector -> msgBinaire(String msgBinaire) : 'msgBinaire' is not a binary string.");
			return null;
		}
		return value;
	}
	
	public String getMsgBinaire()
	{
		return m_msgBinaire;
	}
	
	public Boolean setMsgBinaire(String msgBinaire)
	{
		if (!isBinaryString(msgBinaire)) return false;	// La chaine n'est pas une chaine binaire
		m_msgBinaire = msgBinaire;
		return true;
	}
	
	public IntegerPolynom getMessagePolynomial()
	{
		return m_polynom;
	}
	
	// Vérifie que la chaine est binaire
	public Boolean isBinaryString(String chaine)
	{
		return Pattern.matches("^(0|1)+$", chaine);
	}
}
