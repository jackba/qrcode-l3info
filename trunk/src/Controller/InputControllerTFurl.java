package Controller;

import java.awt.event.FocusEvent;
import javax.swing.event.DocumentEvent;

import Vue.Fenetre;

// Classe chargée du controle du JTextField de l'url
public class InputControllerTFurl extends InputController {

	private static String HTTP = "http://";
	
	public InputControllerTFurl(Fenetre f) {
		super(f);
		m_fenetre.getTF_url().setText(HTTP);
	}
	
	public void onTextChanged(DocumentEvent event) {
		
	}
	
	public void onFocusLost(FocusEvent event)
	{
		if (!m_fenetre.getTF_url().getText().startsWith(HTTP))
			m_fenetre.getTF_url().setText(HTTP + m_fenetre.getTF_url().getText());
	}
	
	public boolean isValid()
	{
		if (m_fenetre.getTF_url().getText().length() - HTTP.length() > 0)
			return true;
		return false;
	}
}
