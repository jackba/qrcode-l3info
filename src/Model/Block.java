package Model;

//Représente le découpage de la chaine binaire en un bloc d'octets
public class Block {
	private int m_number;	// Nombre de fois que doit être utilisé ce bloc
	private int m_totalWords;	// Nombre d'octets total du bloc (données et corrections d'erreurs confondus)
	private int m_dataWords;	// Nombre d'octets de données uniquement du bloc
	private int m_correctionWords;	// Nombre d'octets de correction d'erreur uniquement
	
	public Block(int number, int totalWords, int dataWords)
	{
		m_number = number;
		m_totalWords = totalWords;
		m_dataWords = dataWords;
		m_correctionWords = totalWords - m_dataWords;
	}
	
	public int getNumber() {return m_number;}
	public int getTotalWords() {return m_totalWords;}
	public int getDataWords() {return m_dataWords;}
	public int getCorrectionWords() {return m_correctionWords;}
	
	public String toString()
	{
		return "number = " + m_number +
		"; total words = " + m_totalWords +
		"; data words = " + m_dataWords +
		"; correction words = " + m_correctionWords;
	}
	
	public Block clone()
	{
		return new Block(this.getNumber(),this.getTotalWords(),this.getDataWords());
	}
	
	public int getTotalCorrectionWords()
	{
		return m_number*m_correctionWords;
	}
	public int getTotalDataWords()
	{
		return m_number*m_dataWords;
	}
	public int getTotalTotalWords()
	{
		return m_number*m_totalWords;
	}
}