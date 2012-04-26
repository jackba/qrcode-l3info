package PolynomialPack;

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
 *  String correction = ec.getErrorCorrectorStringFor(msg, 13);
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
	 *  Retourne une chaine binaire de correction d'erreur QRcode et
	 *  comportant nbOctetsCorrection pour le messageBinaire spécifié
	 */
	public String getErrorCorrectorStringFor(String messageBinaire, int nbOctetsCorrection)
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
