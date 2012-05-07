package Model;

public class PenaltyRules {

	private QRcode m_qrcode;
	private int m_penaltyNb;
	private int m_masque;

	public PenaltyRules(QRcode qrcode)
	{
		this.m_qrcode=qrcode;
		this.m_penaltyNb=0;
		this.m_masque=0;
	}

	//Fonction qui calcule la pénalité pour chaque masque de 0 a 7 puis stock dans m_masque le numero du masque avec la pénalité la plus faible
	//La valeur de la pénalité est stocké dans m_penaltyNb
	public void calculatePenalty(){	
		// Rules 1 : On cherche les pixels de même couleurs consécutifs par lignes
		int compteur;
		Boolean light;
		boolean find;
		int nbPenalty;
		for(int indexOfMask=0;indexOfMask<8;indexOfMask++){
			nbPenalty=0;
			compteur=0;
			light = null;
			for(int i=0; i<m_qrcode.getMatriceSize();i++){
				for(int j=0;j<m_qrcode.getMatriceSize();j++){
					if(m_qrcode.getQRmatrix()[i][j]==light && m_qrcode.getQRmatrix()[i][j]!=null){
						compteur++;
						if(compteur==5){
							nbPenalty=nbPenalty+3;
						}else if(compteur>5){
							nbPenalty++;
						}
					}else{
						compteur=0;
						light = m_qrcode.getQRmatrix()[i][j];
					}
				}
			}

			// Rules 1 : On cherche les pixels de même couleurs consécutifs par colonne
			compteur=0;
			light = null;
			for(int i=0; i<m_qrcode.getMatriceSize();i++){
				for(int j=0;j<m_qrcode.getMatriceSize();j++){
					if(m_qrcode.getQRmatrix()[j][i]==light && m_qrcode.getQRmatrix()[j][i]!=null){
						compteur++;
						if(compteur==5){
							nbPenalty=nbPenalty+3;
						}else if(compteur>5){
							nbPenalty++;
						}
					}else{
						compteur=0;
						light = m_qrcode.getQRmatrix()[j][i];
					}
				}
			}

			//Rules 2 : On cherche les carré de 2*2 de même couleurs
			light=null;
			for(int i=0; i<m_qrcode.getMatriceSize()-1;i++){
				for(int j=0;j<m_qrcode.getMatriceSize()-1;j++){
					light=m_qrcode.getQRmatrix()[i][j];
					if(m_qrcode.getQRmatrix()[i+1][j]==light && m_qrcode.getQRmatrix()[i][j+1]==light && m_qrcode.getQRmatrix()[i+1][j+1]==light){
						nbPenalty=nbPenalty+3;
					}
				}
			}

			//Rules 3 : On cherche le motif noir - blanc - noir - noir - noir - blanc - noir suivi ou précédé ou les 2 de 4 pixels blanc.
			// Chaque fois que ce motif est trouvé, on applique une pénalité de 40.

			//On cherche tout d'abord en parcourant par colonne 
			find = false;
			for (int i=0; i< m_qrcode.getMatriceSize();i++){
				for(int j=0;j<m_qrcode.getMatriceSize()-6;j++){
					find = false;
					if(m_qrcode.getMaskedMatrix(indexOfMask)[i][j] && !m_qrcode.getMaskedMatrix(indexOfMask)[i][j+1] && m_qrcode.getMaskedMatrix(indexOfMask)[i][j+2] && m_qrcode.getMaskedMatrix(indexOfMask)[i][j+3] && m_qrcode.getMaskedMatrix(indexOfMask)[i][j+4] && !m_qrcode.getMaskedMatrix(indexOfMask)[i][j+5] && m_qrcode.getMaskedMatrix(indexOfMask)[i][j+6]){
						if(j+10<m_qrcode.getMatriceSize()){
							if(!m_qrcode.getMaskedMatrix(indexOfMask)[i][j+7] && !m_qrcode.getMaskedMatrix(indexOfMask)[i][j+8] && !m_qrcode.getMaskedMatrix(indexOfMask)[i][j+9] && !m_qrcode.getMaskedMatrix(indexOfMask)[i][j+10]){
								find = true;
							}
						}
						if(j>3){
							if(!m_qrcode.getMaskedMatrix(indexOfMask)[i][j-4] && !m_qrcode.getMaskedMatrix(indexOfMask)[i][j-3] && !m_qrcode.getMaskedMatrix(indexOfMask)[i][j-2] && !m_qrcode.getMaskedMatrix(indexOfMask)[i][j-1]){
								find = true;
							}
						}
						if(find){
							nbPenalty=nbPenalty+40;
							find = false;
						}
					}
				}
			}

			//Puis en parcourant lignes par lignes
			for (int i=0; i< m_qrcode.getMatriceSize()-6;i++){
				for(int j=0;j<m_qrcode.getMatriceSize();j++){
					find = false;
					if(m_qrcode.getMaskedMatrix(indexOfMask)[i][j] && !m_qrcode.getMaskedMatrix(indexOfMask)[i+1][j] && m_qrcode.getMaskedMatrix(indexOfMask)[i+2][j] && m_qrcode.getMaskedMatrix(indexOfMask)[i+3][j] && m_qrcode.getMaskedMatrix(indexOfMask)[i+4][j] && !m_qrcode.getMaskedMatrix(indexOfMask)[i+5][j] && m_qrcode.getMaskedMatrix(indexOfMask)[i+6][j]){
						if(i+10<m_qrcode.getMatriceSize()){
							if(!m_qrcode.getMaskedMatrix(indexOfMask)[i+7][j] && !m_qrcode.getMaskedMatrix(indexOfMask)[i+8][j] && !m_qrcode.getMaskedMatrix(indexOfMask)[i+9][j] && !m_qrcode.getMaskedMatrix(indexOfMask)[i+10][j]){
								find = true;
							}
						}
						if(i>3){
							if(!m_qrcode.getMaskedMatrix(indexOfMask)[i-4][j] && !m_qrcode.getMaskedMatrix(indexOfMask)[i-3][j] && !m_qrcode.getMaskedMatrix(indexOfMask)[i-2][j] && !m_qrcode.getMaskedMatrix(indexOfMask)[i-1][j]){
								find = true;
							}
						}
						if(find){
							nbPenalty=nbPenalty+40;
							find = false;
						}
					}
				}
			}
			if(indexOfMask==0){
				this.m_penaltyNb=nbPenalty;
				this.m_masque=indexOfMask;
			}else if(nbPenalty<this.m_penaltyNb){
				this.m_penaltyNb=nbPenalty;
				this.m_masque=indexOfMask;				
			}
		}
	}
	public int getPenaltyNb(){
		return this.m_penaltyNb;
	}
	
	public int getMask(){
		return this.m_masque;
	}

	/*public static void main(String[] args) {
		BinaryStringGenerator stringGenerator = new BinaryStringGenerator();
		String encodedData = stringGenerator.getBinaryString("HELLO WORLD", 1, 1, "Q");
		QRcode qrCode = new QRcode(1,"Q",encodedData);
		qrCode.fillQRmatrix();
		PenaltyRules penRul = new PenaltyRules(qrCode);
		penRul.calculatePenalty();
		
		System.out.println(penRul.getMask());
	}*/

}


