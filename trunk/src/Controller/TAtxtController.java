package Controller;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Vue.Fenetre;

public class TAtxtController extends AbstractTextController implements DocumentListener{

	private int m_maxLength;	// Nombre de caractères maximum pour le champs
	private int m_difference;	// Nombre de caractères restants
	
	public TAtxtController(Fenetre f)
	{
		super(f,250,CharacterMode.BYTES);	// 250 caractères par défaut + mode bytes
		m_maxLength = getDefaultLength();
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
	
	public void switchEnableDisableBgenerer()
	{
		if (getFenetre().getRB_txt().isSelected())
			if (isValid())getFenetre().getB_generer().setEnabled(true);
			else getFenetre().getB_generer().setEnabled(false);
	}
	
	public void onTextChanged(DocumentEvent event)
	{
		m_difference = m_maxLength - event.getDocument().getLength();
		setCharsCountLabel();
		switchEnableDisableBgenerer();
	}
	
	// Méthode appelée lorsque le nombre maximum de caractères disponibles pour tout le qrcode a changé
	public void onMaxCharsChanged()
	{
		super.onMaxCharsChanged();
		m_maxLength = getMaximumChars();
		m_difference = m_maxLength - getFenetre().getTA_txt().getDocument().getLength();
		setCharsCountLabel();
		switchEnableDisableBgenerer();
	}
	
	public void changedUpdate(DocumentEvent event) {onTextChanged(event);}
	public void insertUpdate(DocumentEvent event) {onTextChanged(event);}
	public void removeUpdate(DocumentEvent event) {onTextChanged(event);}
	
	public String getMessage()
	{
		return getFenetre().getTA_txt().getText();
	}

}
