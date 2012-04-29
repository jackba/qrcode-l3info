package Controller;

import java.awt.event.FocusEvent;

import javax.swing.event.DocumentEvent;
import Vue.Fenetre;

// Classe abstraite chargée du controle d'un champs de saisie type JTextField ou JTextArea
public abstract class InputController {
	
	protected Fenetre m_fenetre;
	
	public InputController(Fenetre f)
	{
		m_fenetre = f;
	}
	
	public abstract void onTextChanged(DocumentEvent event);	// méthode appelée lorsque le texte du champs est modifié
	
	public abstract void onFocusLost(FocusEvent event);	// méthode appelée lorsque le focus du champs change
	
	public abstract boolean isValid();	// Permet de vérifier que les données gérées par le controleur sont valides
}
