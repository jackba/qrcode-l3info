package Model;

import java.io.UnsupportedEncodingException;

// ECI : Extended Channel Interpretation
// Fournit des méthodes pour la génération de chaines de données binaires
// afin d'encoder les données dans des modes différents du mode ECI 000002, lequel
// est utilisé par défaut dans QRcode, et qui empêche notamment l'utilisation
// des accents français, espanols, allemands, etc.
public class ECI {
	
	// Liste des valeurs binaires ECI (6 bits) et leur correspondance ISO
	private static String[][] ECI_ISO_TABLE = 
		new String[][] {
		{"000003","ISO-8859-1"},
		{"000004","ISO-8859-2"},
		{"000005","ISO-8859-3"},
		{"000006","ISO-8859-4"},
		{"000007","ISO-8859-5"},
		{"000008","ISO-8859-6"},
		{"000009","ISO-8859-7"},
		{"000010","ISO-8859-8"},
		{"000011","ISO-8859-9"},
		{"000012","ISO-8859-10"},
		{"000013","ISO-8859-11"},
		{"000014","ISO-8859-12"},
		{"000015","ISO-8859-13"},
		{"000016","ISO-8859-14"},
		{"000017","ISO-8859-15"},
		};
	
	private static String ECI_MODE_INDICATOR = "0111";
	private static String BYTE_MODE_INDICATOR = "0100";
	
	private ECI()
	{
		// CONSTRUCTEUR PRIVEE: cette classe ne contient que des méthodes statiques, inutile de l'instancier
	}
	
	// Retourne une chaine encodée avec le header ECI 000017 (ISO-8859-15) correspondant 
	// aux principaux caractères de la plupart des langues d'europe occidentale, à savoir:
	//
	// allemand, anglais, basque, catalan, danois, espagnol, finnois, français,
	// italien, néerlandais, norvégien, portugais et suédois (CF wikipédia)
	public static String getBinaryStringForCommonEuropeanLanguage(String chaineAencoder, int version)
	{
		return getBinaryStringFor(chaineAencoder,version,"ISO-8859-15");
	}
	
	// Retourne une chaine binaire avec header ECI, encodée avec la norme demandée
	// et utilisant le mode BYTES pour coder les caractères (donc pas d'optimisations).
	public static String getBinaryStringFor(String chaineAencoder, int version, String normeISO)
	{
		// 1: Ajout de l'indicateur de mode ECI
		String result = ECI_MODE_INDICATOR;
		
		// 2: Ajout du nombre d'assignement qui désigne la norme ECI utilisée
		result += getAssignmentNumber(normeISO);
		
		// 3: Ajout de l'indicateur de l'encodage des données.
		// Dans notre cas les données seront toujours encodées sous forme d'octets,
		// mais on peut réaliser des optimisations (gains de place) en encodant
		// dans des types différents.
		result += BYTE_MODE_INDICATOR;
		
		// 4: Ajout du nombre de caractères qui seront codés.
		result += getCountIndicator(chaineAencoder, version);
		
		// 5: Ajout des données encodées sur un octet par caractère
		result += getBinaryStringFromData(chaineAencoder,normeISO);
		
		return result;
	}
	
	// Retourne le nombre de caractères codés selon la version du QRcode dans laquelle seront codés les caractères
	private static String getCountIndicator(String chaineAencoder, int version)
	{
		String countIndic = new String(Integer.toBinaryString(chaineAencoder.length()));
		if(version>=1 && version <=9){
			while (countIndic.length()<8){
				countIndic = "0"+countIndic;
			}
		}else if(version>=10 && version <=40){
			while (countIndic.length()<16){
				countIndic = "0"+countIndic;
			}
		}
		return countIndic;
	}
	
	// Retourne la chaine binaire d'assignement qui correspond au mode ECI utilisé pour coder les données
	private static String getAssignmentNumber(String normeISO)
	{
		Integer eci = getECInumber(normeISO);
		if (eci != null)
		{
			if (eci >= 0 && eci <= 999999)
			{
				String result = Integer.toBinaryString(eci);
				if (eci <= 127)
				{
					while (result.length() < 8)
						result = "0" + result;
				}
				else if (eci <= 16383)
				{
					while (result.length() < 14)
						result = "0" + result;
					result = "10" + result;
				}
				else
				{
					while (result.length() < 13)
						result = "0" + result;
					result = "110" + result;
				}
				return result;
			}
		}
		return null;
	}
	
	// Retourne le nombre ECI correspondant à la norme ISO
	private static Integer getECInumber(String normeISO)
	{
		for (int i=0; i<ECI_ISO_TABLE.length; i++)
		{
			if (ECI_ISO_TABLE[i][1].equals(normeISO))
				return Integer.parseInt(ECI_ISO_TABLE[i][0]);
		}
		return null;
	}
	
	// Retourne une chaine binaire encodée à partir des donnees et selon la norme ISO d'encodage spécifiée
	private static String getBinaryStringFromData(String donnees, String modeISO)
	{
		byte[] chaine = null;
		
		try {
			chaine = donnees.getBytes(modeISO);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		
		String result = "";
		String caractere;
		for (int i=0; i<chaine.length; i++)
		{
			caractere = Integer.toBinaryString(chaine[i]);
			if (caractere.length() >= 8)
				caractere = caractere.substring(caractere.length()-8);
			else
				while(caractere.length()<8)
					caractere = "0" + caractere;
			
			result += caractere;
		}
		
		return result;
	}
	
	// DEBUG
	public static void main(String[] args) {
		// System.out.println(Integer.toBinaryString(999999));
		ECI.getBinaryStringForCommonEuropeanLanguage("à table", 2);
	}
}
