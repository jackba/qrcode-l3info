package Model;

import java.util.Vector;

// Représente la spécification d'une version de QRcode
// telle que décrite dans la norme ISO 18004
public class Specification {

	private int m_version;	// Version de la spécification QRcode
	private int m_totalWords;	// Nombre total d'octets pour la chaine binaire (données + corrections d'erreurs comprises)
	private Vector<CorrectionLevel> m_correctionLevels;	// Liste des niveaux de correction pour cette version
	
	public Specification()
	{
		
	}
	
	public int getVersion() {return m_version;}
	public int getTotalWords() {return m_totalWords;}
	public Vector<CorrectionLevel> getCorrectionLevels() {return m_correctionLevels;}
	
}
