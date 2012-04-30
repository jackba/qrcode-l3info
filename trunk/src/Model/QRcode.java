package Model;

public class QRcode {
	
	private int m_version;
	private String m_binaryString;
	private String m_correcLevel;
	private Boolean[][] m_matrice;
	private int m_matriceSize;
	
	public static void main(String[] args) {
		QRcode code = new QRcode(1,"H","01010210");
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
	public void fillQRmatrix()
	{
		fillFinderPatterns();
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
