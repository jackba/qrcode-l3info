package Controller;

import Vue.Fenetre;

public abstract class AbstractController {
	
	private Fenetre m_fenetre;
	
	public AbstractController(Fenetre f)
	{
		m_fenetre = f;
	}

	public Fenetre getFenetre()
	{
		return m_fenetre;
	}

	public void setFenetre(Fenetre mFenetre)
	{
		m_fenetre = mFenetre;
	}
	
	public abstract boolean isValid();
}
