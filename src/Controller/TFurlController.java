package Controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Vue.Fenetre;

public class TFurlController extends AbstractTextController implements FocusListener, DocumentListener {

	private static String HTTP = "http://";
	
	public TFurlController(Fenetre f)
	{
		super(f,255,CharacterMode.BYTES);
		f.getTF_url().setText(HTTP);
	}
	
	public boolean isValid()
	{
		if (getFenetre().getTF_url().getText().length() - HTTP.length() > 0 &&  getFenetre().getTF_url().getText().length() <= getMaximumChars())
			return true;
		return false;
	}
	
	public void focusLost(FocusEvent event)
	{
		if (!(getFenetre().getTF_url().getText().startsWith(HTTP.toLowerCase())
				|| getFenetre().getTF_url().getText().startsWith(HTTP.toUpperCase())))
		{
			String initial = getFenetre().getTF_url().getText();
			if(initial.equals(initial.toUpperCase()))
			{
				// La chaine est entièrement en majuscules
				getFenetre().getTF_url().setText(HTTP.toUpperCase() + getFenetre().getTF_url().getText());
			}
			else
			{
				// La chaine n'est pas entièrement en majuscules
				getFenetre().getTF_url().setText(HTTP + getFenetre().getTF_url().getText());
			}
		}
	}
	
	// Méthodes inutilisées
	public void focusGained(FocusEvent event) {}
	
	public String getMessage()
	{
		return getFenetre().getTF_url().getText();
	}
	
	public void switchEnableDisableBgenerer()
	{
		if (getFenetre().getRB_url().isSelected()) 
			if (isValid()) getFenetre().getB_generer().setEnabled(true);
			else getFenetre().getB_generer().setEnabled(false);
	}
	
	public void onTextChanged(DocumentEvent event)
	{
		CharacterMode prevMode = getMode(); // Récupération du mode précédent
		setMode(getAdaptedMode(getFenetre().getTF_url().getText()));	// Assignation du mode le plus adapté au texte courant (qui peut être identique au mode précédent)
		
		// Le nouveau mode est différent du précédent, le nbr de caractères disponibles change
		if (prevMode != getMode())
		{
			// On change le label du mode
			getFenetre().getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(getMode()));
			onMaxCharsChanged();	// On signale que le nombre maximum de caractères a changé
		}
		switchEnableDisableBgenerer();
	}
	
	// Méthode appelée lorsque le nombre maximum de caractères disponibles pour tout le qrcode a changé
	public void onMaxCharsChanged()
	{
		super.onMaxCharsChanged();
		switchEnableDisableBgenerer();
	}
	
	public void changedUpdate(DocumentEvent event) {onTextChanged(event);}
	public void insertUpdate(DocumentEvent event) {onTextChanged(event);}
	public void removeUpdate(DocumentEvent event) {onTextChanged(event);}
}
