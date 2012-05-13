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
	private FormatCorrector m_FormatCorrector;
	private Boolean[][] m_matricePatron;
	
	public QRcode(int version)
	{
		setVersion(version);		
		m_matrice = new Boolean[m_matriceSize][m_matriceSize];
		m_versionCorrector = new VersionCorrector();
		drawPatternMatrix(m_matrice,m_version);
		fillWhite();
	}

	public QRcode(int version, String correcLevel , String binaryDataEncoded)
	{
		m_binaryString = binaryDataEncoded;
		m_correcLevel = correcLevel;
		m_versionCorrector = new VersionCorrector();
		m_FormatCorrector = new FormatCorrector();
		setVersion(version);
	}

	private Boolean[][] m_mat0;
	private Boolean[][] m_mat1;
	private Boolean[][] m_mat2;
	private Boolean[][] m_mat3;
	private Boolean[][] m_mat4;
	private Boolean[][] m_mat5;
	private Boolean[][] m_mat6;
	private Boolean[][] m_mat7;

	// Remplit la matrice du QRcode en se basant sur la version, le niveau de correction et la chaine binaire
	// False = carré blanc
	// True = carré noir
	public void fillQRmatrix()
	{
		
		m_matricePatron = new Boolean[m_matriceSize][m_matriceSize];
		drawPatternMatrix(m_matricePatron,m_version);

		m_mat0 = new Boolean[m_matriceSize][m_matriceSize];
		m_mat1 = new Boolean[m_matriceSize][m_matriceSize];
		m_mat2 = new Boolean[m_matriceSize][m_matriceSize];
		m_mat3 = new Boolean[m_matriceSize][m_matriceSize];
		m_mat4 = new Boolean[m_matriceSize][m_matriceSize];
		m_mat5 = new Boolean[m_matriceSize][m_matriceSize];
		m_mat6 = new Boolean[m_matriceSize][m_matriceSize];
		m_mat7 = new Boolean[m_matriceSize][m_matriceSize];

		drawData(m_mat0, m_mat1, m_mat2, m_mat3, m_mat4, m_mat5, m_mat6, m_mat7);
		drawMaskedMatrixes(m_mat0, m_mat1, m_mat2, m_mat3, m_mat4, m_mat5, m_mat6, m_mat7);
		//System.out.println(matriceToString(m_mat0));
		//System.out.println(matriceToString(m_mat1));
	}

	// Retourne la matrice masquée complète avec les informations de format
	public Boolean[][] getMaskedMatrix(int indexOfMask)
	{
		switch(indexOfMask)
		{
		case 0: return m_mat0;
		case 1: return m_mat1;
		case 2: return m_mat2;
		case 3: return m_mat3;
		case 4: return m_mat4;
		case 5: return m_mat5;
		case 6: return m_mat6;
		case 7: return m_mat7;
		default: return m_mat0;
		}
	}

	private void drawMaskedMatrixes(Boolean[][] mat0, Boolean[][] mat1, Boolean[][] mat2, Boolean[][] mat3, Boolean[][] mat4, Boolean[][] mat5, Boolean[][] mat6, Boolean[][] mat7)
	{
		// Applique la formule du masquage sur chaque module de donnée des 8 matrices masques
		for (int line=0; line<m_matriceSize; line++)
		{
			for (int column=0; column<m_matriceSize; column++)
			{
				if (m_matricePatron[line][column] == null)
				{
					if ( (line + column) % 2 == 0 )
						mat0[line][column] = !mat0[line][column];

					if ( line % 2 == 0 )
						mat1[line][column] = !mat1[line][column];

					if ( column % 3 == 0 )
						mat2[line][column] = !mat2[line][column];

					if ( (line + column) % 3 == 0 )
						mat3[line][column] = !mat3[line][column];

					if ( ((line/2) + (column/3)) % 2 == 0 )
						mat4[line][column] = !mat4[line][column];

					if ( (line * column) % 2 + (line * column) % 3 == 0 )
						mat5[line][column] = !mat5[line][column];

					if ( ((line * column) % 2 + (line * column) % 3) % 2 == 0 )
						mat6[line][column] = !mat6[line][column];

					if ( ((line * column) % 3 + (line + column) % 2) % 2 == 0 )
						mat7[line][column] = !mat7[line][column];
				}
			}
		}
		
		// Ajout des patterns
		drawAllPatterns(mat0, m_version);
		drawAllPatterns(mat1, m_version);
		drawAllPatterns(mat2, m_version);
		drawAllPatterns(mat3, m_version);
		drawAllPatterns(mat4, m_version);
		drawAllPatterns(mat5, m_version);
		drawAllPatterns(mat6, m_version);
		drawAllPatterns(mat7, m_version);
		
		// Ajout des informations de format
		drawFormat(mat0, m_correcLevel, 0);
		drawFormat(mat1, m_correcLevel, 1);
		drawFormat(mat2, m_correcLevel, 2);
		drawFormat(mat3, m_correcLevel, 3);
		drawFormat(mat4, m_correcLevel, 4);
		drawFormat(mat5, m_correcLevel, 5);
		drawFormat(mat6, m_correcLevel, 6);
		drawFormat(mat7, m_correcLevel, 7);
	}

	private void drawFormat(Boolean[][] matrix, String correctionLvl, int mask)
	{
		// Récupération des informations de format
		String format = m_FormatCorrector.getFormatBinaryString(correctionLvl, mask);

		int size = matrix.length-1;
		
		// Ajout des informations de format
		for (int i=0; i<=7; i++)
			matrix[8][size-i] = getBoolAt(format,14-i);

		for (int i=0; i<=5; i++)
			matrix[i][8] = getBoolAt(format,14-i);
		matrix[7][8] = getBoolAt(format,8);
		matrix[8][8] = getBoolAt(format,7);

		for (int i=0; i<=6; i++)
			matrix[size-i][8] = getBoolAt(format,i);

		for (int i=0; i<=5; i++)
			matrix[8][i] = getBoolAt(format,i);
		matrix[8][7] = getBoolAt(format,6);
	}

	// Remplit la matrice avec la chaine de données du QRcode
	private void drawData(Boolean[][] mat0, Boolean[][] mat1, Boolean[][] mat2, Boolean[][] mat3, Boolean[][] mat4, Boolean[][] mat5, Boolean[][] mat6, Boolean[][] mat7)
	{
		boolean toTop = true;
		boolean currentData;
		int currentChar=0;

		for(int largeurParc=this.m_matriceSize-1; largeurParc>=0; largeurParc=largeurParc-2)
		{
			if(largeurParc==6)
			{
				largeurParc=5;
			}
			if(toTop)
			{
				for(int hauteurParc=this.m_matriceSize-1;hauteurParc>=0;hauteurParc--)
				{
					if(this.m_matricePatron[hauteurParc][largeurParc]==null)
					{
						currentData = getBoolAt(this.m_binaryString, currentChar);
						mat0[hauteurParc][largeurParc] = currentData;
						mat1[hauteurParc][largeurParc] = currentData;
						mat2[hauteurParc][largeurParc] = currentData;
						mat3[hauteurParc][largeurParc] = currentData;
						mat4[hauteurParc][largeurParc] = currentData;
						mat5[hauteurParc][largeurParc] = currentData;
						mat6[hauteurParc][largeurParc] = currentData;
						mat7[hauteurParc][largeurParc] = currentData;
						currentChar++;
					}
					if(largeurParc>0)
					{
						if(this.m_matricePatron[hauteurParc][largeurParc-1]==null)
						{
							currentData = getBoolAt(this.m_binaryString, currentChar);
							mat0[hauteurParc][largeurParc-1] = currentData;
							mat1[hauteurParc][largeurParc-1] = currentData;
							mat2[hauteurParc][largeurParc-1] = currentData;
							mat3[hauteurParc][largeurParc-1] = currentData;
							mat4[hauteurParc][largeurParc-1] = currentData;
							mat5[hauteurParc][largeurParc-1] = currentData;
							mat6[hauteurParc][largeurParc-1] = currentData;
							mat7[hauteurParc][largeurParc-1] = currentData;
							currentChar++;
						}
					}
				}
			}
			else
			{
				for(int hauteurParc=0;hauteurParc<this.m_matriceSize;hauteurParc++)
				{
					if(this.m_matricePatron[hauteurParc][largeurParc]==null)
					{
						currentData = getBoolAt(this.m_binaryString, currentChar);
						mat0[hauteurParc][largeurParc] = currentData;
						mat1[hauteurParc][largeurParc] = currentData;
						mat2[hauteurParc][largeurParc] = currentData;
						mat3[hauteurParc][largeurParc] = currentData;
						mat4[hauteurParc][largeurParc] = currentData;
						mat5[hauteurParc][largeurParc] = currentData;
						mat6[hauteurParc][largeurParc] = currentData;
						mat7[hauteurParc][largeurParc] = currentData;
						currentChar++;
					}
					if(largeurParc>0)
					{
						if(this.m_matricePatron[hauteurParc][largeurParc-1]==null)
						{
							currentData = getBoolAt(this.m_binaryString, currentChar);
							mat0[hauteurParc][largeurParc-1] = currentData;
							mat1[hauteurParc][largeurParc-1] = currentData;
							mat2[hauteurParc][largeurParc-1] = currentData;
							mat3[hauteurParc][largeurParc-1] = currentData;
							mat4[hauteurParc][largeurParc-1] = currentData;
							mat5[hauteurParc][largeurParc-1] = currentData;
							mat6[hauteurParc][largeurParc-1] = currentData;
							mat7[hauteurParc][largeurParc-1] = currentData;
							currentChar++;
						}
					}
				}
			}
			toTop= !toTop;
		}
	}

	// Retourne la valeur booléenne en fonction du caractère présent dans l'index
	private boolean getBoolAt(String binaryString, int index)
	{
		if(index<binaryString.length())
			if (binaryString.charAt(index) == '1')
				return true;
		return false;
	}

	// Retourne le nombre de modules nécessaires pour un côté
	// de la version de QRcode passée en paramètre
	public int getModuleCount(int version)
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

	public String matriceToString(Boolean[][] matrice)
	{
		String result = "";
		if (matrice != null)
			for (int line=0; line<matrice.length; line ++)
			{
				for (int col=0; col<matrice.length; col++)
				{
					if (matrice[line][col] != null)
					{
						if (matrice[line][col] == true)
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

	public int getMatriceSize(){
		return this.m_matriceSize;
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

	// Remplit la matrice patron qui servira à insérer les données et appliquer les masques
	private void drawPatternMatrix(Boolean[][] matrix, int version)
	{	
		int size = matrix.length;

		// Création des patterns
		drawAllPatterns(matrix, version);

		// Ajout des bandes horizontales et verticales de format qui ne doivent pas être remplies par des données
		// et qui seront remplies plus tards dans la matrice finale
		for (int i=0; i<6; i++)
		{
			matrix[8][i] = false;
			matrix[i][8] = false;
		}
		matrix[8][7] = false;
		matrix[8][8] = false;
		matrix[7][8] = false;

		for (int i=size-8; i<size; i++)
			matrix[8][i] = false;

		for (int i=size-7; i<size; i++)
			matrix[i][8] = false;

	}

	// Dessine tous les patrons de base dans la matrice
	private void drawAllPatterns(Boolean[][] matrix, int version)
	{
		drawExpandedFinderPatterns(matrix);
		drawExpandedTimingPatterns(matrix);
		drawExpandedAlignmentPatterns(matrix, version);
		drawExpandedVersionInformation(matrix, version);
		matrix[matrix.length-8][8] = true; // Ajout du module noir
	}

	// Ajoute les informations de version dans la matrice
	private void drawExpandedVersionInformation(Boolean[][] matrix, int version)
	{
		// Les informations de version sont ajoutées uniquement à partir de la version 7 ou plus
		if (version >= 7)
		{
			String binary = m_versionCorrector.getVersionBinaryString(version);
			int index = binary.length()-1;
			int line = 0;
			int column = matrix.length-11;

			// Ajout de la version en haut à droite
			// Ligne 1
			matrix[line][column] = getBoolAt(binary,index);
			matrix[line][column+1] = getBoolAt(binary,index-1);
			matrix[line][column+2] = getBoolAt(binary,index-2);
			// Ligne 2
			matrix[line+1][column] = getBoolAt(binary,index-3);
			matrix[line+1][column+1] = getBoolAt(binary,index-4);
			matrix[line+1][column+2] = getBoolAt(binary,index-5);
			// Ligne 3
			matrix[line+2][column] = getBoolAt(binary,index-6);
			matrix[line+2][column+1] = getBoolAt(binary,index-7);
			matrix[line+2][column+2] = getBoolAt(binary,index-8);
			// Ligne 4
			matrix[line+3][column] = getBoolAt(binary,index-9);
			matrix[line+3][column+1] = getBoolAt(binary,index-10);
			matrix[line+3][column+2] = getBoolAt(binary,index-11);
			// Ligne 5
			matrix[line+4][column] = getBoolAt(binary,index-12);
			matrix[line+4][column+1] = getBoolAt(binary,index-13);
			matrix[line+4][column+2] = getBoolAt(binary,index-14);
			// Ligne 6
			matrix[line+5][column] = getBoolAt(binary,index-15);
			matrix[line+5][column+1] = getBoolAt(binary,index-16);
			matrix[line+5][column+2] = getBoolAt(binary,index-17);


			// Réinitialisation
			line = matrix.length-11;
			column = 0;

			// Ajout de la version en bas à gauche
			// Colonne 1
			matrix[line][column] = getBoolAt(binary,index);
			matrix[line+1][column] = getBoolAt(binary,index-1);
			matrix[line+2][column] = getBoolAt(binary,index-2);
			// Colonne 2
			matrix[line][column+1] = getBoolAt(binary,index-3);
			matrix[line+1][column+1] = getBoolAt(binary,index-4);
			matrix[line+2][column+1] = getBoolAt(binary,index-5);
			// Colonne 3
			matrix[line][column+2] = getBoolAt(binary,index-6);
			matrix[line+1][column+2] = getBoolAt(binary,index-7);
			matrix[line+2][column+2] = getBoolAt(binary,index-8);
			// Colonne 4
			matrix[line][column+3] = getBoolAt(binary,index-9);
			matrix[line+1][column+3] = getBoolAt(binary,index-10);
			matrix[line+2][column+3] = getBoolAt(binary,index-11);
			// Colonne 5
			matrix[line][column+4] = getBoolAt(binary,index-12);
			matrix[line+1][column+4] = getBoolAt(binary,index-13);
			matrix[line+2][column+4] = getBoolAt(binary,index-14);
			// Colonne 6
			matrix[line][column+5] = getBoolAt(binary,index-15);
			matrix[line+1][column+5] = getBoolAt(binary,index-16);
			matrix[line+2][column+5] = getBoolAt(binary,index-17);

		}
	}

	private void drawExpandedTimingPatterns(Boolean[][] matrix)
	{
		int timingRange = matrix.length -16;
		int nbrRemain = timingRange % 4;
		int nbr4Multi = timingRange - nbrRemain;
		int nbrRepets = nbr4Multi / 4;

		int line = 8;
		int column = 8;

		// Remplissage par groupes de 5 bits
		for (int i=0; i<nbrRepets; i++)
		{
			matrix[6][column] = true;
			matrix[6][column+1] = false;
			matrix[6][column+2] = true;
			matrix[6][column+3] = false;

			matrix[line][6] = true;
			matrix[line+1][6] = false;
			matrix[line+2][6] = true;
			matrix[line+3][6] = false;

			column += 4;
			line += 4;
		}

		// Remplissage des bits restants
		for (int i=0; i<nbrRemain; i++)
		{
			if ((8+nbr4Multi+i)%2 == 0)	// Le nombre est pair: la case sera noire
			{
				matrix[6][8+nbr4Multi+i] = true;	// Patron horizontal
				matrix[8+nbr4Multi+i][6] = true;	// Patron vertical
			}
			else	// Le nombre est impair: la case sera blanche
			{
				matrix[6][8+nbr4Multi+i] = false;	// Patron horizontal
				matrix[8+nbr4Multi+i][6] = false;	// Patron vertical
			}
		}
	}

	// Dessine dans la matrice tous les patrons de recherche en mode déplié pour gagner en performances
	private void drawExpandedFinderPatterns(Boolean[][] matrix)
	{
		// 1: Ajout du premier patron (en haut à gauche)
		drawExpandedFinderPattern(matrix,0,0);
		draw1x8line(matrix,7,0,false,true);	// Ligne horizontale blanche
		draw1x8line(matrix,0,7,false,false);// Ligne verticale blanche

		// 2: Ajout du second patron (en haut à droite)
		drawExpandedFinderPattern(matrix,0,matrix.length-7);
		draw1x8line(matrix,7,matrix.length-8,false,true);
		draw1x8line(matrix,0,matrix.length-8,false,false);

		// 3: Ajout du troisième patron (en bas à gauche)
		drawExpandedFinderPattern(matrix,matrix.length-7,0);
		draw1x8line(matrix,matrix.length-8,0,false,true);
		draw1x8line(matrix,matrix.length-8,7,false,false);
	}

	// Dessine dans la matrice un patron de recherche en mode déplié pour gagner en performances
	private void drawExpandedFinderPattern(Boolean[][] matrix, int line, int column)
	{
		// Carré noir le plus à l'extérieur
		draw7x7unfilledSquare(matrix, line, column, true);

		// Carré blanc intermédiaire
		draw5x5unfilledSquare(matrix, line+1, column+1, false);

		// Carré noir central
		draw3x3unfilledSquare(matrix, line+2, column+2, true);
		matrix[line+3][column+3] = true;
	}

	// Dessine dans la matrice l'ensemble des patrons d'alignement en mode déplié pour gagner en performances
	private void drawExpandedAlignmentPatterns(Boolean[][] matrix, int version)
	{
		// Récupération de la position centrale des patrons
		int[] positions = AlignmentPatternsParser.getInstance().getPositions(version);

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
					drawExpandedAlignmentPattern(matrix, positions[i]-2,positions[j]-2);	// On ajoute le patron
			}
		}
	}

	// Dessine dans la matrice un patron d'alignement en mode déplié pour gagner en performances
	private void drawExpandedAlignmentPattern(Boolean[][] matrix, int line, int column)
	{
		// Carré noir le plus à l'extérieur
		draw5x5unfilledSquare(matrix, line, column, true);

		// Carré blanc intermédiaire
		draw3x3unfilledSquare(matrix,line+1,column+1,false);

		// Point central
		matrix[line+2][column+2] = true;
	}

	private void draw3x3unfilledSquare(Boolean[][] matrix, int line, int column, boolean value)
	{
		// Ligne en haut
		matrix[line][column] = value;
		matrix[line][column+1] = value;
		matrix[line][column+2] = value;
		// Ligne en bas
		matrix[line+2][column] = value;
		matrix[line+2][column+1] = value;
		matrix[line+2][column+2] = value;
		// Colonne à gauche
		matrix[line+1][column] = value;
		// Colonne à droite
		matrix[line+1][column+2] = value;
	}

	private void draw5x5unfilledSquare(Boolean[][] matrix, int line, int column, boolean value)
	{
		// Ligne en haut
		matrix[line][column] = value;
		matrix[line][column+1] = value;
		matrix[line][column+2] = value;
		matrix[line][column+3] = value;
		matrix[line][column+4] = value;
		// Ligne en bas
		matrix[line+4][column] = value;
		matrix[line+4][column+1] = value;
		matrix[line+4][column+2] = value;
		matrix[line+4][column+3] = value;
		matrix[line+4][column+4] = value;
		// Colonne à gauche
		matrix[line+1][column] = value;
		matrix[line+2][column] = value;
		matrix[line+3][column] = value;
		// Colonne à droite
		matrix[line+1][column+4] = value;
		matrix[line+2][column+4] = value;
		matrix[line+3][column+4] = value;
	}

	private void draw7x7unfilledSquare(Boolean[][] matrix, int line, int column, boolean value)
	{
		// Ligne en haut
		matrix[line][column] = value;
		matrix[line][column+1] = value;
		matrix[line][column+2] = value;
		matrix[line][column+3] = value;
		matrix[line][column+4] = value;
		matrix[line][column+5] = value;
		matrix[line][column+6] = value;
		// Ligne en bas
		matrix[line+6][column] = value;
		matrix[line+6][column+1] = value;
		matrix[line+6][column+2] = value;
		matrix[line+6][column+3] = value;
		matrix[line+6][column+4] = value;
		matrix[line+6][column+5] = value;
		matrix[line+6][column+6] = value;
		// Colonne à gauche
		matrix[line+1][column] = value;
		matrix[line+2][column] = value;
		matrix[line+3][column] = value;
		matrix[line+4][column] = value;
		matrix[line+5][column] = value;
		// Colonne à droite
		matrix[line+1][column+6] = value;
		matrix[line+2][column+6] = value;
		matrix[line+3][column+6] = value;
		matrix[line+4][column+6] = value;
		matrix[line+5][column+6] = value;
	}

	private void draw1x8line(Boolean[][] matrix, int line, int column, boolean value, boolean isHorizontal)
	{
		if (isHorizontal)
		{
			matrix[line][column] = value;
			matrix[line][column+1] = value;
			matrix[line][column+2] = value;
			matrix[line][column+3] = value;
			matrix[line][column+4] = value;
			matrix[line][column+5] = value;
			matrix[line][column+6] = value;
			matrix[line][column+7] = value;
		}
		else
		{
			matrix[line][column] = value;
			matrix[line+1][column] = value;
			matrix[line+2][column] = value;
			matrix[line+3][column] = value;
			matrix[line+4][column] = value;
			matrix[line+5][column] = value;
			matrix[line+6][column] = value;
			matrix[line+7][column] = value;
		}
	}
	
	private void fillWhite(){
		for(int i=0; i<this.m_matriceSize;i++){
			for(int j=0; j<this.m_matriceSize;j++){
				if(m_matrice[i][j]==null){
					m_matrice[i][j]=false;
				}
			}
		}
	}

	public void fillOptimizedMatrix(Boolean[][] matrix, int version)
	{
		drawExpandedFinderPatterns(matrix);
		drawExpandedTimingPatterns(matrix);
		drawExpandedAlignmentPatterns(matrix, version);
		drawExpandedVersionInformation(matrix, version);
	}
	
	public void modifierPixel(int x, int y){
		this.m_matrice[x][y]= !this.m_matrice[x][y];
	}

	// DEBUG
	/*
	public static void main(String[] args) {

		BinaryStringGenerator bsg = new BinaryStringGenerator();
		String encodedData = bsg.getBinaryString("HELLO WORLD", 1, 7, "L");
		QRcode code = new QRcode(7,"Q",encodedData);
		code.fillQRmatrix();
		//System.out.println(code + "\n\n");
		//System.out.println(code.matriceToString(code.getMaskedMatrix(0)));

		
		QRcode qr = new QRcode(0, "Q", "tralilol");
		Boolean[][] maMatrice = new Boolean[qr.getModuleCount(13)][qr.getModuleCount(13)];
		qr.fillOptimizedMatrix(maMatrice,13);
		System.out.println(qr.matriceToString(maMatrice));
		 

	}
	*/
	/*
	public static void main(String[] args) {
		QRcode qr = new QRcode(1);
		System.out.println(qr.toString());
		 
	}*/
}
