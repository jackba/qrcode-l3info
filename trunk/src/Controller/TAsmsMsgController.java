package Controller;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Vue.Fenetre;

public class TAsmsMsgController extends AbstractTextController implements DocumentListener {

	private int m_maxLength;	// Nombre de caractères maximum pour le champs
	private int m_difference;	// Nombre de caractères restants
	private TFsmsTelController m_TFsmsTelController;
	
	public TAsmsMsgController(Fenetre f, TFsmsTelController smsTelController)
	{
		super(f,160,CharacterMode.BYTES);	// Champs avec une taille par défaut à 160 caractères et mode de départ en Bytes
		m_maxLength = getDefaultLength();
		m_difference = m_maxLength;
		m_TFsmsTelController = smsTelController;
		setCharsCountLabel();
	}
	
	private void setCharsCountLabel()
	{
		if (m_difference < 0)
			getFenetre().getL_smsMsgCount().setText("0/" + m_maxLength + " caractère restant (" + -m_difference + " en trop)");
		else if (m_difference == 1 || m_difference == 0)
			getFenetre().getL_smsMsgCount().setText(m_difference + "/" + m_maxLength + " caractère restant");
		else
			getFenetre().getL_smsMsgCount().setText(m_difference + "/" + m_maxLength + " caractères restants");
	}
	
	public boolean isValid()
	{
		/*
		if (m_difference >= 0 && m_difference < m_maxLength)
			return true;
		return false;
		*/
		return m_TFsmsTelController.isValid();
	}
	
	public void switchEnableDisableBgenerer()
	{
		if (getFenetre().getRB_sms().isSelected()) 
			if (isValid()) getFenetre().getB_generer().setEnabled(true);
			else getFenetre().getB_generer().setEnabled(false);
	}
	
	public void onTextChanged(DocumentEvent event)
	{
		CharacterMode prevMode = getMode(); // Récupération du mode précédent
		CharacterMode newMode = getAdaptedMode(getFenetre().getTA_smsMsg().getText());
		
		// Assignation du mode le plus adapté au texte courant (qui peut être identique au mode précédent)
		// uniquement si ce mode est supérieur ou égal au mode alphanumérique, qui est le mode minimum requis
		// pour générer le numéro de téléphone du sms
		switch (newMode)
		{
		case ALPHANUMERIC:
		case BYTES:
		case KANJI:
		case ECI: setMode(newMode); break;
		case NUMERIC: setMode(CharacterMode.ALPHANUMERIC); break;
		default: break;
		}
		
		// Le nouveau mode est différent du précédent, le nbr de caractères disponibles change
		if (prevMode != getMode())
		{
			// On change le label du mode
			getFenetre().getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(getMode()));
			onMaxCharsChanged();	// On signale que le nombre maximum de caractères a changé
			m_TFsmsTelController.setMode(getMode()); // On modifie le mode du numéro de téléphone du sms
			m_TFsmsTelController.onMaxCharsChanged(); // et on signale au controller du numéro de téléphone du sms que la taille a changée
		}
		m_difference = m_maxLength - event.getDocument().getLength();
		setCharsCountLabel();
		switchEnableDisableBgenerer();
	}
	
	// Méthode appelée lorsque le nombre maximum de caractères disponibles pour tout le qrcode a changé
	public void onMaxCharsChanged()
	{
		super.onMaxCharsChanged();
		m_maxLength = getMaximumChars() - 17;
		if (m_maxLength < 0) m_maxLength = 0;
		m_difference = m_maxLength - getFenetre().getTA_smsMsg().getText().length();
		setCharsCountLabel();
		switchEnableDisableBgenerer();
	}
	
	public void changedUpdate(DocumentEvent event) {onTextChanged(event);}
	public void insertUpdate(DocumentEvent event) {onTextChanged(event);}
	public void removeUpdate(DocumentEvent event) {onTextChanged(event);}
	
	public String getMessage()
	{
		return ":" + getFenetre().getTA_smsMsg().getText();
	}
}
