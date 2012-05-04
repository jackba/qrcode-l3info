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
					if (7 + getFenetre().getTF_smsTel().getText().length()-1 + getFenetre().getTA_smsMsg().getText().length()+1 <= getMaximumChars()
							&& isTAsmsMsgValid())
						return true;
				}
				else
				{
					if (7 + getFenetre().getTF_smsTel().getText().length() + getFenetre().getTA_smsMsg().getText().length()+1 <= getMaximumChars()
							&& isTAsmsMsgValid())
						return true;
				}
		}
		return false;
	}
	
	public boolean isTAsmsMsgValid()
	{
		if (getMaximumChars()-17-getFenetre().getTA_smsMsg().getText().length() >= 0
				&& getMaximumChars()-17-getFenetre().getTA_smsMsg().getText().length() < getMaximumChars() - 17)
			return true;
		return false;
	}

	public void onTextChanged(DocumentEvent event)
	{
		switchEnableDisableBgenerer();
	}
	
	public void onMaxCharsChanged()
	{
		super.onMaxCharsChanged();
		switchEnableDisableBgenerer();
	}
	
	public void switchEnableDisableBgenerer()
	{
		if (getFenetre().getRB_sms().isSelected())
			if (isValid()) getFenetre().getB_generer().setEnabled(true);
			else getFenetre().getB_generer().setEnabled(false);
	}

	public void changedUpdate(DocumentEvent event) {onTextChanged(event);}
	public void insertUpdate(DocumentEvent event) {onTextChanged(event);}
	public void removeUpdate(DocumentEvent event) {onTextChanged(event);}

	public String getMessage()
	{
		return "SMSTO:" + getFenetre().getTF_smsTel().getText();
	}
}
