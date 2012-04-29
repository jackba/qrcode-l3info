package Controller;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Vue.Fenetre;

public class TFsmsTelController extends AbstractController implements DocumentListener{

	public TFsmsTelController(Fenetre f)
	{
		super(f);
	}
	
	public boolean isValid()
	{
		return false;
	}
	
	public void onTextChanged(DocumentEvent event)
	{
		
	}
	
	public void changedUpdate(DocumentEvent event) {onTextChanged(event);}
	public void insertUpdate(DocumentEvent event) {onTextChanged(event);}
	public void removeUpdate(DocumentEvent event) {onTextChanged(event);}

}
