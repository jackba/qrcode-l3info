package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

	private ButtonController m_controller;
	
	public ButtonListener(ButtonController c)
	{
		m_controller = c;
	}
	
	public void actionPerformed(ActionEvent event)
	{
		m_controller.performAction(event);
	}

}
