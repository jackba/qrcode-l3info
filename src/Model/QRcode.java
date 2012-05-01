package Model;

import Donnees.AlignmentPatternsParser;

// génère une matrice booléenne représentant le dessin du QRcode
public class QRcode {

	private int m_version;
	private String m_binaryString;
	private String m_correcLevel;
	private Boolean[][] m_matrice;
	private int m_matriceSize;
	
	// DEBUG
	public static void main(String[] args) {
		QRcode code = new QRcode(7,"H","01010210");
		code.fillQRmatrix();
		System.out.println(code);
	}

	public QRcode(int version, String correcLevel , String binaryDataEncoded)
	{
		m_binaryString = binaryDataEncoded;
		m_correcLevel = correcLevel;
		setVersion(version);
	}

	// Remplit la matrice du QRcode en se basant sur la version, le niveau de correction et la chaine binaire
	// False = carré blanc
	// True = carré noir
	public void fillQRmatrix()
	{
		fillFinderPatterns();
		fillTimingPatterns();
		fillBlackModulePattern();
		fillAlignmentPatterns();
	}
	
	// Ajoute les patrons d'alignements. Les patrons d'alignements sont les carrés
	// de taille plus réduite par rapport aux patrons de positionnements.
	// Les patrons d'alignements ne sont ajoutés qu'à partir de la deuxième version de QRcode
	private void fillAlignmentPatterns()
	{
		// Récupération de la position centrale des patrons
		int[] positions = AlignmentPatternsParser.getInstance().getPositions(m_version);
		
		// Placement d'un patron à chaque position sauf:
		// - le premier de la première ligne
		// - le dernier de la première ligne
		// - le premier de la dernière ligne
		for (int i=0; i<positions.length; i++)
		{
			for (int j=0; j<positions.length; j++)
			{
				if (!(i == 0 && j == 0) &&	// S'il ne s'agit pas du premier de la première ligne
						!(i == 0 && j == positions.length-1) &&	// Ni du dernier de la première ligne
						!(i == positions.length-1 && j == 0))	// ni du premier de la dernière ligne
				drawAnAlignmentPattern(positions[i],positions[j]);	// On ajoute le patron
			}
		}
	}
	
	// Dessine un patron d'alignement aux coordonnées spécifiées
	private void drawAnAlignmentPattern(int line, int column)
	{
		// Carré noir le plus à l'extérieur
		for (int i=line-2; i<=line+2; i++)
			if (i>=0 && i <m_matriceSize)
				for (int j=column-2; j<=column+2; j++)
					if (j>=0 && j<m_matriceSize)
						m_matrice[i][j] = true;
		
		// Carré blanc intermédiaire
		for (int i=line-1; i<=line+1; i++)
			if (i>=0 && i <m_matriceSize)
				for (int j=column-1; j<=column+1; j++)
					if (j>=0 && j<m_matriceSize)
						m_matrice[i][j] = false;
		
		// Point noir central
		if (line>=0 && line<m_matriceSize && column>=0 && column<m_matriceSize)
			m_matrice[line][column] = true;
	}
	
	// Ajoute un module noir toujours situé au même emplacement
	// c.a.d juqte à coté du patron de positionnement carré situé en bas à gauche
	private void fillBlackModulePattern()
	{
		m_matrice[m_matriceSize-8][8] = true;	// Patron horizontal
	}
	
	// Ajoute la ligne et la colonne composés d'une alternance de modules noirs et blanc
	// et situés entre les Finders pattern (les 3 gros carrés de détection de position).
	private void fillTimingPatterns()
	{
		// on commence toujours par un module noir et on termine toujours par un module noir
		for (int col=8; col<m_matriceSize-8; col++)
		{
			if (col%2 == 0)	// Le nombre est pair: la case sera noire
			{
				m_matrice[6][col] = true;	// Patron horizontal
				m_matrice[col][6] = true;	// Patron vertical
			}
			else	// Le nombre est impair: la case sera blanche
			{
				m_matrice[6][col] = false;	// Patron horizontal
				m_matrice[col][6] = false;	// Patron vertical
			}
		}
	}

	// Ajoute les 3 patrons carrés de positionnement situés en haut à gauche de la matrice,
	// en haut à droite de la matrice et en bas à gauche de la matrice
	// Leur taille est fixée dans la spécification du QRcode et identique pour toutes les versions
	private void fillFinderPatterns()
	{
		int firstBlackSquare = 7;
		int secondWhiteSquare = 5;
		int thirdBlackSquare = 3;

		// Premier patron
		for (int line=0; line<firstBlackSquare; line ++)
			for (int col=0; col<firstBlackSquare; col++)
				m_matrice[line][col] = true;

		for (int line=1; line<secondWhiteSquare+1; line ++)
			for (int col=1; col<secondWhiteSquare+1; col++)
				m_matrice[line][col] = false;

		for (int line=2; line<thirdBlackSquare+2; line ++)
			for (int col=2; col<thirdBlackSquare+2; col++)
				m_matrice[line][col] = true;

		for (int i=0; i<=firstBlackSquare; i++)
		{
			m_matrice[firstBlackSquare][i] = false;	// Remplissage ligne blanche
			m_matrice[i][firstBlackSquare] = false;	// Remplissage colonne blanche
		}

		// Second patron
		for (int line=0; line<firstBlackSquare; line ++)
			for (int col=m_matriceSize-firstBlackSquare; col<m_matriceSize; col++)
				m_matrice[line][col] = true;

		for (int line=1; line<secondWhiteSquare+1; line ++)
			for (int col=m_matriceSize-secondWhiteSquare-1; col<m_matriceSize-1; col++)
				m_matrice[line][col] = false;

		for (int line=2; line<thirdBlackSquare+2; line ++)
			for (int col=m_matriceSize-thirdBlackSquare-2; col<m_matriceSize-2; col++)
				m_matrice[line][col] = true;

		for (int i=0; i<=firstBlackSquare; i++)
		{
			m_matrice[firstBlackSquare][m_matriceSize-1-i] = false;	// Remplissage ligne blanche
			m_matrice[i][m_matriceSize-1-firstBlackSquare] = false;	// Remplissage colonne blanche
		}

		// Troisième patron
		for (int line=m_matriceSize-firstBlackSquare; line<m_matriceSize; line ++)
			for (int col=0; col<firstBlackSquare; col++)
				m_matrice[line][col] = true;

		for (int line=m_matriceSize-secondWhiteSquare-1; line<m_matriceSize-1; line ++)
			for (int col=1; col<secondWhiteSquare+1; col++)
				m_matrice[line][col] = false;

		for (int line=m_matriceSize-thirdBlackSquare-2; line<m_matriceSize-2; line ++)
			for (int col=2; col<thirdBlackSquare+2; col++)
				m_matrice[line][col] = true;

		for (int i=0; i<=firstBlackSquare; i++)
		{
			m_matrice[m_matriceSize-firstBlackSquare-1][i] = false;	// Remplissage ligne blanche
			m_matrice[m_matriceSize-1-i][firstBlackSquare] = false;	// Remplissage colonne blanche
		}
	}

	// Retourne le nombre de modules nécessaires pour un côté
	// de la version de QRcode passée en paramètre
	private int getModuleCount(int version)
	{
		// Le nombre de module vaut 21 pour le premier module
		// puis le nombre est incrémenté de 4 à chaque module
		// ce qui donne: module 2 = 25; module 3 = 29; ...
		return 21 + 4*(version-1);
	}

	// Retourne la matrice actuelle du QRcode
	public Boolean[][] getQRmatrix()
	{
		return m_matrice;
	}

	public String toString()
	{
		String result = "";
		if (m_matrice != null)
			for (int line=0; line<m_matriceSize; line ++)
			{
				for (int col=0; col<m_matriceSize; col++)
				{
					if (m_matrice[line][col] != null)
					{
						if (m_matrice[line][col] == true)
							result += "[X]";
						else
							result += "[ ]";
					}
					else
						result += "...";
				}
				result += "\n";
			}
		return result;
	}

	/*
	 *  GETTERS AND SETTERS
	 */
	public int getVersion() {
		return m_version;
	}

	public void setVersion(int version) {
		m_version = version;
		m_matriceSize = getModuleCount(version);
		m_matrice = new Boolean[m_matriceSize][m_matriceSize];
	}

	public String getBinaryString() {
		return m_binaryString;
	}

	public void setBinaryString(String binaryString) {
		m_binaryString = binaryString;
	}

	public String getCorrecLevel() {
		return m_correcLevel;
	}

	public void setCorrecLevel(String correcLevel) {
		m_correcLevel = correcLevel;
	}
}
