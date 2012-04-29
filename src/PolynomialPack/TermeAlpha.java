package PolynomialPack;

// Classe représentant un terme d'un polynome de la forme alpha(^exposantAlpha)*x(^exposant)
// où exposantAlpha et exposant sont des entiers naturels
public class TermeAlpha extends Terme {
	
	private int m_exposantAlpha;
	
	public TermeAlpha()
	{
		super();
		m_exposantAlpha = 1;
	}
	
	public TermeAlpha(int exposant)
	{
		super(exposant);
		m_exposantAlpha = 1;
	}
	
	public TermeAlpha(int exposantAlpha, int exposant)
	{
		super(exposant);
		m_exposantAlpha = exposantAlpha;
	}
	
	public void setTermeAsNull()
	{
		m_exposantAlpha = -255;
	}
	
	public int getExposantAlpha()
	{
		return m_exposantAlpha;
	}
	
	public Boolean setExposantAlpha(int exposantAlpha)
	{
		if (exposantAlpha >= 0)
		{
			m_exposantAlpha = exposantAlpha;
			return true;
		}
		return false;
	}
	
	/*
	// Version simpliant l'affichage
	public String toString()
	{
		if (m_exposantAlpha == 0)
			return super.toString();
		if (m_exposantAlpha == 1)
			return "a." + super.toString();
		return "a^" + m_exposantAlpha + "." + super.toString();
	}
	*/
	
	// Version affichant tout
	public String toString()
	{
		if (m_exposantAlpha != -255)
			return "a^" + m_exposantAlpha + "." + super.toString();
		else
			return "a." + super.toString();
	}
	
	// Retourne le terme alpha transformé en terme entier
	public TermeEntier toTermeEntier()
	{
		int coefficient;
		if(m_exposantAlpha != -255)
			coefficient = m_galoisField.getAlphaValue(m_exposantAlpha);
		else
			coefficient = 0;
		return new TermeEntier(coefficient, m_exposant);
	}
	
	public Terme clone() {
		return new TermeAlpha(this.getExposantAlpha(), this.getExposant());
	}
}
