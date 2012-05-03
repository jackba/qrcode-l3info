package Controller;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Vue.Fenetre;

public class TAtxtController extends AbstractTextController implements DocumentListener{

	private int m_maxLength;	// Nombre de caractères maximum pour le champs
	private int m_difference;	// Nombre de caractères restants
	
	public TAtxtController(Fenetre f)
	{
		super(f);
		m_maxLength = 250;
		m_difference = m_maxLength;
		setCharsCountLabel();
	}
	
	public void setMaxLength(int maxLength)
	{
		m_maxLength = maxLength;
		m_difference = m_maxLength;
		setCharsCountLabel();
	}

	private void setCharsCountLabel()
	{
		if (m_difference < 0)
			getFenetre().getL_txtCount().setText("0/" + m_maxLength + " caractère restant (" + -m_difference + " en trop)");
		else if (m_difference == 1 || m_difference == 0)
			getFenetre().getL_txtCount().setText(m_difference + "/" + m_maxLength + " caractère restant");
		else
			getFenetre().getL_txtCount().setText(m_difference + "/" + m_maxLength + " caractères restants");
	}
	
	public boolean isValid()
	{
		if (m_difference >= 0 && m_difference < m_maxLength)
			return true;
		return false;
	}
	
	public void onTextChanged(DocumentEvent event)
	{
		m_difference = m_maxLength - event.getDocument().getLength();
		setCharsCountLabel();
	}
	
	public void changedUpdate(DocumentEvent event) {onTextChanged(event);}
	public void insertUpdate(DocumentEvent event) {onTextChanged(event);}
	public void removeUpdate(DocumentEvent event) {onTextChanged(event);}

}
