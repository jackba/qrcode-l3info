package Controller;

import javax.swing.event.DocumentEvent;
import Vue.Fenetre;

// Classe abstraite chargée du controle d'un champs de saisie type JTextField ou JTextArea
public abstract class InputController {
	
	protected Fenetre m_fenetre;
	
	public InputController(Fenetre f)
	{
		m_fenetre = f;
	}
	
	public abstract void onTextChanged(DocumentEvent event);
}
