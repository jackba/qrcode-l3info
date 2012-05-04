package Controller;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Vue.Fenetre;

public class TFsmsTelController extends AbstractTextController implements DocumentListener{
	
	public TFsmsTelController(Fenetre f)
	{
		super(f,12,CharacterMode.BYTES);
	}
	
	public boolean isValid()
	{
		boolean isCorrect;
		try
		{
			Integer.parseInt(getFenetre().getTF_smsTel().getText());
			isCorrect = true;
		}
		catch (Exception e)
		{
			isCorrect = false;
		}
		
		if (isCorrect)
		{
			if (getFenetre().getTF_smsTel().getText().length() <= 10)
				if (getFenetre().getTF_smsTel().getText().length() == 10)
				{
					if (3 + getFenetre().getTF_smsTel().getText().length()-1 + getFenetre().getTA_smsMsg().getText().length() <= getMaximumChars())
						return true;
				}
				else
				{
					if (3 + getFenetre().getTF_smsTel().getText().length() + getFenetre().getTA_smsMsg().getText().length() <= getMaximumChars())
						return true;
				}
		}
		return false;
	}
	
	public void onTextChanged(DocumentEvent event)
	{
		
	}
	
	public void changedUpdate(DocumentEvent event) {onTextChanged(event);}
	public void insertUpdate(DocumentEvent event) {onTextChanged(event);}
	public void removeUpdate(DocumentEvent event) {onTextChanged(event);}
	
	public String getMessage()
	{
		String tel;
		if (getFenetre().getTF_smsTel().getText().length() == 10)
			tel = getFenetre().getTF_smsTel().getText().substring(1, 9);
		else
			tel = getFenetre().getTF_smsTel().getText();
		return "+33" + tel;
	}
}
