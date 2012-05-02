package Model;

import Donnees.ErrorCorrectionParser;

// Classe générant la chaine binaire de données et les corrections d'erreurs associées
public class BinaryStringGenerator {

	private CodeGenerator m_dataGenerator;
	private ErrorCorrector m_correctionGenerator;
	private ErrorCorrectionParser m_correctionParser;
	
	public BinaryStringGenerator()
	{
		m_correctionGenerator = new ErrorCorrector();
		m_correctionParser = ErrorCorrectionParser.getInstance();
	}
	
	// Retourne une chaine binaire répondant aux spécifications du QRcode.
	// mode: 0->Numérique - 1->Alphanumérique - 2->Byte - 3->Kanji
	public String getBinaryString(String message, int mode, int version, String level)
	{
		// Nouvelle instance du générateur binaire de la partie données
		m_dataGenerator = new CodeGenerator(message, mode, level.charAt(0), version);
		m_dataGenerator.generer();
		String datas = m_dataGenerator.getResultBinaire();	// Récupération de la chaine des données
		
		// Récupération de la spécification correspondant à la version
		Specification spec = m_correctionParser.getSpecification(version);
		
		// Retour de la chaine finale (données + correction d'erreurs)
		return m_correctionGenerator.getErrorCorrectionString(datas, spec, level);
	}
	
	public static void main(String[] args)
	{
		BinaryStringGenerator bsg = new BinaryStringGenerator();
		String maChaine = bsg.getBinaryString("0123456789012345", 0, 4, "H");
		System.out.println(maChaine);
	}
}
