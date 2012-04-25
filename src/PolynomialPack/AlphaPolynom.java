package PolynomialPack;

public class AlphaPolynom extends Polynom {
	
	public AlphaPolynom()
	{
		super();
	}
	
	public void addNewTerme(int alphaExponent, int xExponent)
	{
		this.addNewTerme(new TermeAlpha(alphaExponent, xExponent));
	}
	
	// Retourne la version entière de ce polynome alpha
	public IntegerPolynom toIntegerPolynom()
	{
		IntegerPolynom ip = new IntegerPolynom();
		TermeAlpha ta;
		
		for (int i=0; i<this.getNbTermes(); i++)
		{
			ta = (TermeAlpha)getTermeAt(i);
			ip.addNewTerme(ta.toTermeEntier());
		}
		
		return ip;
	}
	
}
