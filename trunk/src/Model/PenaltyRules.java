package Model;

import Controller.MainController;

public class PenaltyRules {
	
	private QRcode m_qrcode;
	private int m_penaltyNb;
	
	public PenaltyRules(QRcode qrcode)
	{
		this.m_qrcode=qrcode;
		this.m_penaltyNb=0;
	}
	
	public void calculatePenalty(){	
		// Rules 1 : On cherche les pixels de même couleurs consécutifs par lignes
		int compteur=0;
		Boolean light = null;
		for(int i=0; i<m_qrcode.getMatriceSize();i++){
			for(int j=0;j<m_qrcode.getMatriceSize();j++){
				if(m_qrcode.getQRmatrix()[i][j]==light && m_qrcode.getQRmatrix()[i][j]!=null){
					compteur++;
					if(compteur==5){
						this.m_penaltyNb=this.m_penaltyNb+3;
					}else if(compteur>5){
						this.m_penaltyNb++;
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
						this.m_penaltyNb=this.m_penaltyNb+3;
					}else if(compteur>5){
						this.m_penaltyNb++;
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
					this.m_penaltyNb=this.m_penaltyNb+3;
				}
			}
		}
		
		//Rules 3 : On cherche le motif noir - blanc - noir - noir - noir - blanc - noir suivi ou précédé ou les 2 de 4 pixels blanc.
		// Chaque fois que ce motif est trouvé, on applique une pénalité de 40.
		
		//On cherche tout d'abord en parcourant par colonne 
/*		for (int i=0; i< m_qrcode.getMatriceSize();i++){
			for(int j=0;j<m_qrcode.getMatriceSize()-6;j++){
				if(m_qrcode.getQRmatrix()[i][j]==false && m_qrcode.getQRmatrix()[i][j+1]==true && m_qrcode.getQRmatrix()[i][j+2]==false && m_qrcode.getQRmatrix()[i][j+3]==false && m_qrcode.getQRmatrix()[i][j+4]==false && m_qrcode.getQRmatrix()[i][j+5]==true && m_qrcode.getQRmatrix()[i][j+6]==false){
					System.out.println("trouverColonne");
				}
			}
		}
		*/
		//Puis en parcourant ligne par ligne
/*		for (int i=0; i< m_qrcode.getMatriceSize()-6;i++){
			for(int j=0;j<m_qrcode.getMatriceSize();j++){
				if(m_qrcode.getQRmatrix()[j][i]==false && m_qrcode.getQRmatrix()[j][i+1]==true && m_qrcode.getQRmatrix()[j][i+2]==false && m_qrcode.getQRmatrix()[j][i+3]==false && m_qrcode.getQRmatrix()[j][i+4]==false && m_qrcode.getQRmatrix()[j][i+5]==true && m_qrcode.getQRmatrix()[j][i+6]==false){
					System.out.println("trouverLigne");
				}
			}
		}
	}*/
	
	public int getPenaltyNb(){
		return this.m_penaltyNb;
	}
	
	public static void main(String[] args) {
		BinaryStringGenerator stringGenerator = new BinaryStringGenerator();
		String encodedData = stringGenerator.getBinaryString("HELLO WORLD", 1, 1, "Q");
		QRcode qrCode = new QRcode(1,"Q",encodedData);
		qrCode.fillQRmatrix();
		PenaltyRules penRul = new PenaltyRules(qrCode);
		penRul.calculatePenalty();
		System.out.println(penRul.getPenaltyNb());
	}

}


