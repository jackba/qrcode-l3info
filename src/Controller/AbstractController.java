package Controller;

import Vue.Fenetre;

// Classe de base des controleurs subsidiaires de l'application.
// Elle possède une référence sur la vue pour permettre aux controleurs d'interragir avec.
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
