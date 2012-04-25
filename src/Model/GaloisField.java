package Model;

/* 
 * Implémente des opérations de base pour un champs de Galois d'une taille et d'un polynôme premier donné
 * La classe est basée sur une vulgarisation mathématique pour application à l'informatique:
 * http://www.aimglobal.org/technologies/barcode/Galois_Math.pdf
 */
public class GaloisField {
	
	public static int GFSIZE_deuxExpHuit = 256; // Taille du champs de Galois (valeurs de 0 à 255)
	public static int PP_QRcode = 285;	// Polynome premier utilisé dans le codage QRcode (x^8 + x^4 + x^3 +1)
	public static int PP_All = 301;	// Polynome premier utilisé dans le codage des autres codes de taille 256 (x^8 + x^5 + x^3 + x^2 +1)
	
	private int[] m_log;	// Tableau des logs
	private int[] m_alog;	// Tableau des anti-logs
	private int m_gfsize;	// Taille du champs de Galois
	private int m_pPremier;	// Polynome premier du champs de Galois
	
	// Instancie un nouveau champs de galois appliqué au QRcode
	public GaloisField()
	{
		m_gfsize = GFSIZE_deuxExpHuit;
		m_pPremier = PP_QRcode;
		m_log = new int[GFSIZE_deuxExpHuit];
		m_alog = new int[GFSIZE_deuxExpHuit];
		initLogAntilog();
	}
	
	// Instancie un nouveau champs de galois quelconque
	public GaloisField(int taille, int polynomePremier)
	{
		m_gfsize = taille;
		m_pPremier = polynomePremier;
		m_log = new int[taille];
		m_alog = new int[taille];
		initLogAntilog();
	}
	
	// Remplit les tableaux de log et antilog du champs de galois
	private void initLogAntilog() {
		
		m_log[0] = 1 - m_gfsize;	// -255 (erreur) dans le cas d'un champs de galois GF(2^8) (<=> 256) : 1-256 = -255
		m_alog[0] = 1;
		
		for (int i=1; i<m_gfsize; i++)
		{
			m_alog[i] = m_alog[i-1] * 2;
			if (m_alog[i] >= m_gfsize) m_alog[i] ^= m_pPremier;
			m_log[m_alog[i]] = i;
		}
		m_log[1] = 0;
	}
	
	public int Somme(int a, int b)
	{
		return (a ^ b);	// XOR binaire des deux entiers
	}
	
	// identique à la somme car dans un champs de Galois, a = -a
	public int Difference(int a, int b)
	{
		return (a ^ b); // XOR binaire des deux entiers
	}
	
	/*
	 *  Le produit de deux valeurs 'a' et 'b' est définit comme étant l'antilog de la somme de leurs log (<=> log(a) + log(b) ),
	 *  modulo la plus grande valeur du champs de Galois (<=> gfSize-1 )
	 */
	public int Produit(int a, int b) {
		if (a == 0 || b == 0) // Multiplication par zéro
			return 0;
		else	// Cas normal
			return (m_alog[(m_log[a] + m_log[b]) % (m_gfsize - 1)]);
	}
	
	/*
	 *  Le quotient de 'a' par 'b' (a est divisé par b) est définit comme étant l'antilog de la différence
	 *  de leurs log (<=> log(a) - log(b) ) + 255 (pour que le résultat du modulo ne soit pas toujours null),
	 *  modulo la plus grande valeur du champs de Galois (<=> gfSize-1 )
	 */
	public int QuotientAparB(int a, int b) throws Exception
	{
		if (b == 0)	// Division par zéro
			throw new Exception("GaloisField -> QuotientAparB(int A, int B) : Division par zéro.");
		else if (a == 0)	// Dividende égal à zéro
			return 0;
		else	// Cas normal
			return (m_alog[(m_log[a] - m_log[b] + m_gfsize - 1) % (m_gfsize - 1)]);
	}
	
	public String toString()
	{
		String logAlog = "LOG\tALOG\n";
		
		for (int i=0; i<m_gfsize; i++)
		{
			logAlog += m_log[i] + "\t" + m_alog[i] + "\n";
		}
		
		return logAlog;
	}
	
	// Retourne la valeur entière du coefficient alpha en fonction de son exposant
	public int getAlphaValue(int alphaExponent)
	{
		try
		{
			return m_alog[alphaExponent];
		}
		catch (Exception e)
		{
			System.err.println("GaloisField -> getLog(int entier) : " + e.toString());
			return -1;
		}
	}
	
	// Retourne l'exposant du coefficient alpha en fonction de la valeur entière du coefficient alpha
	public int getExponantValue(int alphaValue) throws Exception
	{
		try
		{
			if (alphaValue == 0) throw new Exception("alphaValue must be greater than zero.");	// Erreur sur log null
			return m_log[alphaValue];
		}
		catch (Exception e)
		{
			System.err.println("GaloisField -> getLog(int entier) : " + e.toString());
			return -1;
		}
	}
}
