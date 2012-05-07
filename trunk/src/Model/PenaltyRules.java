package Model;

import java.util.ArrayList;

public class PenaltyRules {

	private QRcode m_qrcode;
	private int m_penaltyNb;
	private int m_masque;
	private ArrayList<Boolean[][]> m_listMask;

	public PenaltyRules(QRcode qrcode)
	{
		this.m_qrcode=qrcode;
		this.m_penaltyNb=0;
		this.m_masque=0;
		this.m_listMask = new ArrayList<Boolean[][]>();
		fillListMask();
		calculatePenalty();
	}
	
	//On rempli l'ArrayList m_listMask par les 8 masques possibles
	private void fillListMask(){
		for(int indexOfMask=0;indexOfMask<8;indexOfMask++){
			m_listMask.add(m_qrcode.getMaskedMatrix(indexOfMask));
		}
	}

	// La règles 1 applique une pénalité pour les pixels de même couleurs qui se suivent à partir de 5.
	private int applyRules1(int indexOfMask){
		int compteur=0;
		Boolean light = null;
		int nbPenalty=0;
		for(int i=0; i<m_qrcode.getMatriceSize();i++){
			for(int j=0;j<m_qrcode.getMatriceSize();j++){
				if(m_listMask.get(indexOfMask)[i][j]==light && m_listMask.get(indexOfMask)[i][j]!=null){
					compteur++;
					if(compteur==5){
						nbPenalty=nbPenalty+3;
					}else if(compteur>5){
						nbPenalty++;
					}
				}else{
					compteur=0;
					light = m_listMask.get(indexOfMask)[i][j];
				}
			}
		}

		compteur=0;
		light = null;
		for(int i=0; i<m_qrcode.getMatriceSize();i++){
			for(int j=0;j<m_qrcode.getMatriceSize();j++){
				if(m_listMask.get(indexOfMask)[j][i]==light && m_listMask.get(indexOfMask)[j][i]!=null){
					compteur++;
					if(compteur==5){
						nbPenalty=nbPenalty+3;
					}else if(compteur>5){
						nbPenalty++;
					}
				}else{
					compteur=0;
					light = m_listMask.get(indexOfMask)[j][i];
				}
			}
		}
		return nbPenalty;		
	}

	// La règles 2 applique une pénalité pour chaque carré de 2x2 pixels de mêmes couleurs.
	private int applyRules2(int indexOfMask){
		Boolean light = null;
		int nbPenalty=0;
		for(int i=0; i<m_qrcode.getMatriceSize()-1;i++){
			for(int j=0;j<m_qrcode.getMatriceSize()-1;j++){
				light=m_listMask.get(indexOfMask)[i][j];
				if(m_listMask.get(indexOfMask)[i+1][j]==light && m_listMask.get(indexOfMask)[i][j+1]==light && m_listMask.get(indexOfMask)[i+1][j+1]==light){
					nbPenalty=nbPenalty+3;
				}
			}
		}
		return nbPenalty;		
	}

	//Rules 3 : On cherche le motif noir - blanc - noir - noir - noir - blanc - noir suivi ou précédé ou les 2 de 4 pixels blanc.
	// Chaque fois que ce motif est trouvé, on applique une pénalité de 40.
	private int applyRules3(int indexOfMask){
		boolean find=false;
		int nbPenalty=0;
		for (int i=0; i< m_qrcode.getMatriceSize();i++){
			for(int j=0;j<m_qrcode.getMatriceSize()-6;j++){
				find = false;
				if(m_listMask.get(indexOfMask)[i][j] && !m_listMask.get(indexOfMask)[i][j+1] && m_listMask.get(indexOfMask)[i][j+2] && m_listMask.get(indexOfMask)[i][j+3] && m_listMask.get(indexOfMask)[i][j+4] && !m_listMask.get(indexOfMask)[i][j+5] && m_listMask.get(indexOfMask)[i][j+6]){
					if(j+10<m_qrcode.getMatriceSize()){
						if(!m_listMask.get(indexOfMask)[i][j+7] && !m_listMask.get(indexOfMask)[i][j+8] && !m_listMask.get(indexOfMask)[i][j+9] && !m_listMask.get(indexOfMask)[i][j+10]){
							find = true;
						}
					}
					if(j>3){
						if(!m_listMask.get(indexOfMask)[i][j-4] && !m_listMask.get(indexOfMask)[i][j-3] && !m_listMask.get(indexOfMask)[i][j-2] && !m_listMask.get(indexOfMask)[i][j-1]){
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

		for (int i=0; i< m_qrcode.getMatriceSize()-6;i++){
			for(int j=0;j<m_qrcode.getMatriceSize();j++){
				find = false;
				if(m_listMask.get(indexOfMask)[i][j] && !m_listMask.get(indexOfMask)[i+1][j] && m_listMask.get(indexOfMask)[i+2][j] && m_listMask.get(indexOfMask)[i+3][j] && m_listMask.get(indexOfMask)[i+4][j] && !m_listMask.get(indexOfMask)[i+5][j] && m_listMask.get(indexOfMask)[i+6][j]){
					if(i+10<m_qrcode.getMatriceSize()){
						if(!m_listMask.get(indexOfMask)[i+7][j] && !m_listMask.get(indexOfMask)[i+8][j] && !m_listMask.get(indexOfMask)[i+9][j] && !m_listMask.get(indexOfMask)[i+10][j]){
							find = true;
						}
					}
					if(i>3){
						if(!m_listMask.get(indexOfMask)[i-4][j] && !m_listMask.get(indexOfMask)[i-3][j] && !m_listMask.get(indexOfMask)[i-2][j] && !m_listMask.get(indexOfMask)[i-1][j]){
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
		return nbPenalty;
	}

	//Fonction qui calcule la pénalité pour chaque masque de 0 a 7 puis stock dans m_masque le numero du masque avec la pénalité la plus faible
	//La valeur de la pénalité est stocké dans m_penaltyNb
	private void calculatePenalty(){	
		int nbPenalty;
		for(int indexOfMask=0;indexOfMask<8;indexOfMask++){
			nbPenalty=applyRules1(indexOfMask)+applyRules2(indexOfMask)+applyRules3(indexOfMask);
			//System.out.println(indexOfMask+" : "+nbPenalty);
			if(indexOfMask==0){
				this.m_penaltyNb=nbPenalty;
				this.m_masque=indexOfMask;
			}else if(nbPenalty<this.m_penaltyNb){
				this.m_penaltyNb=nbPenalty;
				this.m_masque=indexOfMask;				
			}
		}
	}
	// GETTERS
	public int getPenaltyNb(){
		return this.m_penaltyNb;
	}

	public int getMask(){
		return this.m_masque;
	}

/*	public static void main(String[] args) {
		BinaryStringGenerator stringGenerator = new BinaryStringGenerator();
		String encodedData = stringGenerator.getBinaryString("HELLO WORLD", 1, 1, "Q");
		QRcode qrCode = new QRcode(1,"Q",encodedData);
		qrCode.fillQRmatrix();
		PenaltyRules penRul = new PenaltyRules(qrCode);

		System.out.println(penRul.getMask());
	}*/
	 
}


