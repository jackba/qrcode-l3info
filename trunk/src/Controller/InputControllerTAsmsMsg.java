package Controller;

import java.awt.event.FocusEvent;
import javax.swing.event.DocumentEvent;
import Vue.Fenetre;

// Controleur du champs de saisie du message du sms
public class InputControllerTAsmsMsg extends InputController {

	private int m_maxLenght;	// Nombre de caractères maximum pour le champs
	private int m_difference;	// Nombre de caractères restants
	
	public InputControllerTAsmsMsg(Fenetre f) {
		super(f);
		m_maxLenght = 160;
		m_difference = m_maxLenght;
		setCharsCountLabel();
	}
	
	public void setCharsCountLabel()
	{
		if (m_difference < 0)
			m_fenetre.getL_smsMsgCount().setText(0 + "/" + m_maxLenght + " caractères restants (" + -m_difference + " en trop)");
		else if (m_difference == 1)
			m_fenetre.getL_smsMsgCount().setText("1/" + m_maxLenght + " caractère restant");
		else
			m_fenetre.getL_smsMsgCount().setText(m_difference + "/" + m_maxLenght + " caractères restants");
	}
	
	public void onTextChanged(DocumentEvent event) {
		m_difference = m_maxLenght - event.getDocument().getLength();
		setCharsCountLabel();
	}
	
	public void onFocusLost(FocusEvent event) {}

	public boolean isValid()
	{
		if (m_difference >= 0 && m_difference < m_maxLenght)
			return true;
		return false;
	}

}
