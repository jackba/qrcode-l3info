package PolynomialPack;

//Classe abstraite représentant un terme d'un polynome de la forme x(^exposant)
public abstract class Terme {
	
	protected GaloisField m_galoisField;
	protected int m_exposant;
	
	public Terme()
	{
		m_galoisField = GaloisField.getInstance();
		m_exposant = 1;	// Exposant par défaut = 1
	}
	
	public Terme(int exposant)
	{
		m_galoisField = GaloisField.getInstance();
		m_exposant = exposant;
	}
	
	public int getExposant()
	{
		return m_exposant;
	}
	
	public Boolean setExposant(int exposant)
	{
		if (exposant >= 0)
		{
			m_exposant = exposant;
			return true;
		}
		return false;
	}
	
	// Empeche l'exposant de sortir du champs de Gallois
	public void fixGreaterExponent()
	{
		m_exposant = m_exposant % (m_galoisField.getFieldSize() - 1);
	}
	
	/*
	// Version simplifiant l'affichage
	public String toString()
	{
		if (m_exposant == 0)
			return "";
		if (m_exposant == 1)
			return "x";
		return "x^" + m_exposant;
	}
	*/
	
	public abstract Terme clone();
	
	// Version affichant tout
	public String toString()
	{
		return "x^" + m_exposant;
	}
}
