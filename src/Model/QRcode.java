package Model;

import Donnees.AlignmentPatternsParser;

// génère une matrice booléenne représentant le dessin du QRcode
public class QRcode {

	private int m_version;
	private String m_binaryString;
	private String m_correcLevel;
	private Boolean[][] m_matrice;
	private int m_matriceSize;
	private VersionCorrector m_versionCorrector;
	private Boolean[][] m_matricePatron;

	// DEBUG
	public static void main(String[] args) {
		BinaryStringGenerator bsg = new BinaryStringGenerator();
		String encodedData = bsg.getBinaryString("HELLO WORLD", 1, 7, "Q");
		QRcode code = new QRcode(7,"Q",encodedData);
		System.out.println(encodedData);
		code.fillQRmatrix();
		//	System.out.println(code.patronToString() + "\n\n");
		System.out.println(code);
	}

	public QRcode(int version, String correcLevel , String binaryDataEncoded)
	{
		m_binaryString = binaryDataEncoded;
		m_correcLevel = correcLevel;
		m_versionCorrector = new VersionCorrector();
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
		fillVersionInformation();
		fillPatternMatrix();
		fillData();
	}

	// Remplit la matrice avec la chaine de données du QRcode
	private void fillData()
	{
		boolean toTop = true;
		int currentChar=0;
		for(int largeurParc=this.m_matriceSize-1; largeurParc>=0; largeurParc=largeurParc-2){
			if(largeurParc==6){
				largeurParc=5;
			}
			if(toTop){
				for(int hauteurParc=this.m_matriceSize-1;hauteurParc>=0;hauteurParc--){
					if(this.m_matricePatron[hauteurParc][largeurParc]==null){
						this.m_matrice[hauteurParc][largeurParc]= getBoolAt(this.m_binaryString, currentChar);
						currentChar++;
					}
					if(largeurParc>0){
						if(this.m_matricePatron[hauteurParc][largeurParc-1]==null){
							this.m_matrice[hauteurParc][largeurParc-1]= getBoolAt(this.m_binaryString, currentChar);
							currentChar++;
						}
					}
				}
			}else{
				for(int hauteurParc=0;hauteurParc<this.m_matriceSize;hauteurParc++){
					if(this.m_matricePatron[hauteurParc][largeurParc]==null){
						this.m_matrice[hauteurParc][largeurParc]= getBoolAt(this.m_binaryString, currentChar);
						currentChar++;
					}
					if(largeurParc>0){
						if(this.m_matricePatron[hauteurParc][largeurParc-1]==null){
							this.m_matrice[hauteurParc][largeurParc-1]= getBoolAt(this.m_binaryString, currentChar);
							currentChar++;
						}
					}
				}
			}
			toTop= !toTop;
		}


		/*
		boolean isUpwardDirection = true;	// Indicateur pour savoir dans quelle direction on met les données suivantes

		int prev_line = m_matriceSize-1;	// La ligne de la donnée précédente
		int prev_column = m_matriceSize-1;	// La colonne de la donnée précédente

		int next_line;	// La ligne de la donnée suivante
		int next_column;	// La colonne de la donnée suivante 

		int curr_firstColumn = m_matriceSize-1;	// La première des deux colonnes dans lesquelles doivent se placer les données lorsqu'il n'y a pas d'obstacles

		// Initialisation
		m_matrice[prev_line][prev_column] = getBoolAt(m_binaryString, 0);

		// Placement de tous les caractères
		for (int i=1; i<m_binaryString.length(); i++)
		{
			// On place les caractères en montant
			if (isUpwardDirection)
			{
				// Nous sommes sur la colonne de droite
				// par rapport aux deux colonnes qui nous sont attribuées
				// On va se placer sur la colonne de gauche
				if (prev_column == curr_firstColumn)
				{
					next_line = prev_line;	// La ligne reste la ligne courante
					next_column = prev_column-1; // La colonne devient la colonne de gauche
				}
				// Nous sommes sur la colonne de gauche
				// On va donc monter d'une ligne et revenir sur la colonne de droite
				else
				{
					next_line = prev_line-1;	// La ligne devient la ligne du dessus
					next_column = prev_column+1; // La colonne devient la colonne de droite
				}
			}
			// On place les caractères en descendant
			else
			{

			}*/
	}

	// Retourne la valeur booléenne en fonction du caractère présent dans l'index
	private Boolean getBoolAt(String binaryString, int index)
	{
		if(index<binaryString.length()){
			if (binaryString.charAt(index) == '1')
				return true;
			return false;
		}else{
			return false;
		}
	}

	// Remplit la matrice patron qui servira à insérer les données et appliquer les masques
	private void fillPatternMatrix()
	{
		// Copie de la matrice courante avec tous les patterns remplits
		m_matricePatron = new Boolean[m_matriceSize][m_matriceSize];
		for (int i=0; i<m_matriceSize; i++)
			for (int j=0; j<m_matriceSize; j++)
				m_matricePatron[i][j] = m_matrice[i][j];

		// Ajout des bandes horizontales et verticales de format qui ne doivent pas être remplies par des données
		// et qui seront remplies plus tards dans la matrice finale
		for (int i=0; i<6; i++)
		{
			m_matricePatron[8][i] = false;
			m_matricePatron[i][8] = false;
		}
		m_matricePatron[8][7] = false;
		m_matricePatron[8][8] = false;
		m_matricePatron[7][8] = false;

		for (int i=m_matriceSize-8; i<m_matriceSize; i++)
			m_matricePatron[8][i] = false;

		for (int i=m_matriceSize-7; i<m_matriceSize; i++)
			m_matricePatron[i][8] = false;
	}

	// Ajoute les informations de version dans la matrice
	private void fillVersionInformation()
	{
		// Les informations de version sont ajoutées uniquement à partir de la version 7 ou plus
		if (m_version >= 7)
		{
			String version = m_versionCorrector.getVersionBinaryString(m_version);
			int charCount = 0;
			int x,y;

			// Ajout de la version en haut à droite
			for (int i=0; i<6; i++)
			{
				for (int j=0; j<3; j++)
				{
					x = 5-i;
					y = m_matriceSize-9-j;
					if (version.charAt(charCount) == '1')
						m_matrice[x][y] = true;
					else
						m_matrice[x][y] = false;
					charCount++;
				}
			}

			// Ajout de la version en bas à gauche
			charCount = 0;
			for (int i=0; i<6; i++)
			{
				for (int j=0; j<3; j++)
				{
					x = m_matriceSize-9-j;
					y = 5-i;
					if (version.charAt(charCount) == '1')
						m_matrice[x][y] = true;
					else
						m_matrice[x][y] = false;
					charCount++;
				}
			}
		}
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

	public String patronToString()
	{
		String result = "";
		if (m_matricePatron != null)
			for (int line=0; line<m_matriceSize; line ++)
			{
				for (int col=0; col<m_matriceSize; col++)
				{
					if (m_matricePatron[line][col] != null)
					{
						if (m_matricePatron[line][col] == true)
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
