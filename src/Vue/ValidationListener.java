package Vue;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import Controller.InputController;

public class ValidationListener implements FocusListener {

	private InputController m_controller;
	
	public void setController(InputController c)
	{
		m_controller = c;
	}
	
	public void focusGained(FocusEvent arg0) {
		
	}
	
	public void focusLost(FocusEvent event) {
		if (m_controller != null) m_controller.onFocusLost(event);
	}

}
