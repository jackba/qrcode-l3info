package Controller;

import java.awt.event.ActionEvent;
import Vue.Fenetre;

// Classe abstraite chargée de controler les composants de type JButton, JRadioButton, JCheckBox
public abstract class ButtonController {
	
	protected Fenetre m_fenetre;
	
	public ButtonController(Fenetre f)
	{
		m_fenetre = f;
	}
	
	public abstract void onClick(ActionEvent event);

}
