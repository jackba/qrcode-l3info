package PolynomialPack;

// Classe représentant un terme d'un polynome de la forme coefficient*x(^exposant)
// où coefficient et exposant sont des entiers naturels
public class TermeEntier extends Terme {
	
	private int m_coefficient;
	
	public TermeEntier()
	{
		super();
		m_coefficient = 1;
	}
	
	public TermeEntier(int exposant)
	{
		super(exposant);
		m_coefficient = 1;
	}
	
	public TermeEntier(int coefficient, int exposant)
	{
		super(exposant);
		m_coefficient = coefficient;
	}
	
	public int getCoefficient()
	{
		return m_coefficient;
	}
	
	public Boolean setCoefficient(int coefficient)
	{
		if (coefficient >= 0)
		{
			m_coefficient = coefficient;
			return true;
		}
		return false;
	}
	
	/*
	// Version simplifiant l'affichage
	public String toString()
	{
		if (m_coefficient == 0)
			return "";
		if (m_coefficient == 1)
			return super.toString();
		return m_coefficient + "." + super.toString();
	}
	*/
	
	// Version affichant tout
	public String toString()
	{
		return m_coefficient + "." + super.toString();
	}
	
	// Retourne le terme entier transformé en terme alpha
	public TermeAlpha toTermeAlpha()
	{
		try {
			int alphaExposant = m_galoisField.getExponantValue(m_coefficient);
			return new TermeAlpha(alphaExposant, m_exposant);
		} catch (Exception e) {
			System.err.println("TermeEntier -> getTermeAlpha() : " + e.toString());
			return null;
		}
	}
}
