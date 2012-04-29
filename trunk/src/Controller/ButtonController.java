package Controller;

import java.awt.event.ActionEvent;
import Vue.Fenetre;

public abstract class ButtonController {
	
	protected Fenetre m_fenetre;
	
	public ButtonController(Fenetre f)
	{
		m_fenetre = f;
	}
	
	public abstract void performAction(ActionEvent event);

}
