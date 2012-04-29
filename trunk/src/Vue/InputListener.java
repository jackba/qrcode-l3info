package Vue;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Controller.InputController;

public class InputListener implements DocumentListener{
	
	private InputController m_controller;
	
	public void setController(InputController c)
	{
		m_controller = c;
	}
	
	// Le texte a changé - jamais appelé dans la réalité
	public void changedUpdate(DocumentEvent event)
	{
		if (m_controller != null) m_controller.onTextChanged(event);
	}
	
	// Du texte a été inséré
	public void insertUpdate(DocumentEvent event)
	{
		if (m_controller != null) m_controller.onTextChanged(event);
	}
	
	// Du texte a été supprimé
	public void removeUpdate(DocumentEvent event)
	{
		if (m_controller != null) m_controller.onTextChanged(event);
	}
}