package Model;

import java.util.Vector;

//Représente un niveau de correction d'erreur d'une version spécifique
public class CorrectionLevel {
	
	private String m_level;	// Niveau de correction d'erreur (L, M, Q, H)
	private int m_totalWords;	// Nombre total d'octets de correction d'erreur pour le niveau
	private Vector<Block> m_blocks;	// Blocs de spécification de découpage
	
	public CorrectionLevel(String level, int totalWords)
	{
		m_level = level;
		m_totalWords = totalWords;
		m_blocks = new Vector<Block>();
	}
	
	public void addBlock(Block b)
	{
		m_blocks.add(b);
	}
	public String getLevel() {return m_level;}
	public int getTotalWords() {return m_totalWords;}
	public Vector<Block> getBlocks() {return m_blocks;}
	
	public String toString()
	{
		String result = "level = \"" + m_level + "; total words = " + m_totalWords +"\n";
		for (int i=0; i<m_blocks.size(); i++)
			result += "\t - " + m_blocks.get(i).toString() + "\n";
		return result;
	}
	
	public CorrectionLevel clone()
	{
		CorrectionLevel cl = new CorrectionLevel(getLevel(), getTotalWords());
		for (int i=0; i<m_blocks.size(); i++)
			cl.addBlock(m_blocks.get(i));
		return cl;
	}
}