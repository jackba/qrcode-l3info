package Model;

import java.util.Vector;

// Représente la spécification d'une version de QRcode
// telle que décrite dans la norme ISO 18004
// Cette classe utilise le fichier xml ErrorCorrectionTable.xml
public class Specification {

	private int m_version;	// Version de la spécification QRcode
	private int m_totalWords;	// Nombre total d'octets pour la chaine binaire (données + corrections d'erreurs comprises)
	private Vector<CorrectionLevel> m_correctionLevels;	// Liste des niveaux de correction pour cette version
	
	public Specification(int version, int totalWords)
	{
		m_version = version;
		m_totalWords = totalWords;
		m_correctionLevels = new Vector<CorrectionLevel>();
	}
	
	public void addCorrectionLevel(CorrectionLevel cl)
	{
		m_correctionLevels.add(cl);
	}
	public int getVersion() {return m_version;}
	public int getTotalWords() {return m_totalWords;}
	public Vector<CorrectionLevel> getCorrectionLevels() {return m_correctionLevels;}
	
	public String toString()
	{
		String result = "Version -" + m_version + "-\t(" + m_totalWords +" words)\n";
		for (int i=0; i<m_correctionLevels.size(); i++)
			result += "\t - " + m_correctionLevels.get(i).toString() + "\n";
		return result;
	}
	
	public Specification clone()
	{
		Specification sp = new Specification(getVersion(), getTotalWords());
		for (int i=0; i<m_correctionLevels.size(); i++)
			sp.addCorrectionLevel(m_correctionLevels.get(i));
		return sp;
	}
}
