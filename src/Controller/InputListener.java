package Controller;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class InputListener implements DocumentListener{
	
	private InputController m_controller;
	
	public InputListener(InputController c)
	{
		m_controller = c;
	}
	
	// Le texte a changé
	public void changedUpdate(DocumentEvent event)
	{
		m_controller.verifyInput(event);
	}
	
	// Du texte a été inséré
	public void insertUpdate(DocumentEvent event) {}
	
	// Du texte a été supprimé
	public void removeUpdate(DocumentEvent event) {}
}