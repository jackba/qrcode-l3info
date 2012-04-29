package Controller;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Vue.Fenetre;

public class TFtelController extends AbstractController implements DocumentListener {

	public TFtelController(Fenetre f)
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
