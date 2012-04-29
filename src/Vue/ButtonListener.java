package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controller.ButtonController;

public class ButtonListener implements ActionListener {

	private ButtonController m_controller;
	
	public void setController(ButtonController c)
	{
		m_controller = c;
	}
	
	public void actionPerformed(ActionEvent event)
	{
		if (m_controller != null) m_controller.onClick(event);
	}

}
