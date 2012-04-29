package Controller;

import javax.swing.event.DocumentEvent;
import Vue.Fenetre;

public abstract class InputController {
	
	protected Fenetre m_fenetre;
	
	public InputController(Fenetre f)
	{
		m_fenetre = f;
	}
	
	public abstract void verifyInput(DocumentEvent event);
}
