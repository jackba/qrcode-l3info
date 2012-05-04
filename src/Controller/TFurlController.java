package Controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import Vue.Fenetre;

public class TFurlController extends AbstractTextController implements FocusListener {

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
		if (!getFenetre().getTF_url().getText().startsWith(HTTP))
			getFenetre().getTF_url().setText(HTTP + getFenetre().getTF_url().getText());
	}
	
	// Méthodes inutilisées
	public void focusGained(FocusEvent event) {}
	
	public String getMessage()
	{
		return getFenetre().getTF_url().getText();
	}
}
