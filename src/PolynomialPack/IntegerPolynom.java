package PolynomialPack;

public class IntegerPolynom extends Polynom {
	
	public IntegerPolynom()
	{
		super();
	}
	
	public void addNewTerme(int coefficient, int xExponent)
	{
		this.addNewTerme(new TermeEntier(coefficient, xExponent));
	}
	
	// Retourne la version alpha de ce polynome entier
	public AlphaPolynom toAlphaPolynom()
	{
		AlphaPolynom ap = new AlphaPolynom();
		TermeEntier te;
		
		for (int i=0; i<this.getNbTermes(); i++)
		{
			te = (TermeEntier)getTermeAt(i);
			ap.addNewTerme(te.toTermeAlpha());
		}
		
		return ap;
	}
	
}
