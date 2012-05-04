package Controller;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Vue.Fenetre;

public class TFtelController extends AbstractTextController implements DocumentListener {

	public TFtelController(Fenetre f)
	{
		super(f,14,CharacterMode.ALPHANUMERIC);
	}
	
	public boolean isValid()
	{
		boolean isCorrect;
		try
		{
			Integer.parseInt(getFenetre().getTF_tel().getText());
			isCorrect = true;
		}
		catch (Exception e)
		{
			isCorrect = false;
		}
		
		if (isCorrect)
		{
			if (getFenetre().getTF_tel().getText().length() <= 10)
				if (getFenetre().getTF_tel().getText().length() == 10)
				{
					if (4 + getFenetre().getTF_tel().getText().length()-1 <= getMaximumChars())
						return true;
				}
				else
				{
					if (4 + getFenetre().getTF_tel().getText().length() <= getMaximumChars())
						return true;
				}
		}
		return false;
	}

	public void switchEnableDisableBgenerer()
	{
		if (getFenetre().getRB_tel().isSelected()) 
			if (isValid()) getFenetre().getB_generer().setEnabled(true);
			else getFenetre().getB_generer().setEnabled(false);
	}
	
	public void onTextChanged(DocumentEvent event)
	{
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
	
	public String getMessage()
	{
		return "TEL:" + getFenetre().getTF_tel().getText();
	}

}
