package PolynomialPack;

import java.util.Collections;
import java.util.Vector;

public abstract class Polynom {
	
	protected Vector<Terme> m_vectTermes;	// Vecteur des termes
	
	public Polynom()
	{
		m_vectTermes =  new Vector<Terme>();
	}
	
	public int getNbTermes()
	{
		return m_vectTermes.size();
	}
	
	// Trie les termes du polynome par exposants décroissants
	public void sortByExposants()
	{
		Collections.sort(m_vectTermes, new TermeExponantComparator());
	}
	
	public abstract void addNewTerme(int coefficientOrExponent, int xExponent);
	
	public Boolean setTermeAt(int index, Terme t)
	{
		try
		{
			m_vectTermes.remove(index);
			m_vectTermes.add(index, t);
			return true;
		}
		catch (Exception e)
		{
			System.err.println("Polynom -> setTermeAt(int index, Terme t) : " +e.toString());
			return false;
		}
	}
	
	public Terme getTermeAt(int index)
	{
		return m_vectTermes.get(index);
	}
	
	protected void addNewTerme(Terme t)	// Ajoute un nouveau terme à la fin du polynome
	{
		m_vectTermes.add(t);
	}
	
	public Boolean removeTerme(Terme t)	// Supprime un terme du polynome
	{
		return m_vectTermes.remove(t);
	}
	
	public Boolean removeTermeAt(int index)	// Supprime un terme à l'index spécifié
	{
		if (m_vectTermes.remove(index) != null)
			return true;
		return false;
	}
	
	public String toString()
	{
		String p = "";
		if (m_vectTermes.size() > 0)
		{
			p = m_vectTermes.get(0).toString();
			int tailleMax = m_vectTermes.size();
			for (int i=1; i<tailleMax; i++)
				p += " + " + m_vectTermes.get(i).toString();
		}
		return p;
	}
	
	public abstract Polynom clone();
	
	protected void setTermes(Vector<Terme> termes)
	{
		m_vectTermes = termes;
	}

	protected Vector<Terme> getTermes()
	{
		Vector<Terme> t = new Vector<Terme>();
		for (int i=0; i<m_vectTermes.size(); i++)
			t.add(m_vectTermes.get(i).clone());
		
		//return (Vector<Terme>) m_vectTermes.clone();
		return t;
	}
}
