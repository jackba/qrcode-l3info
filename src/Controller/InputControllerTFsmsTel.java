package Controller;

import java.awt.event.FocusEvent;
import javax.swing.event.DocumentEvent;
import Vue.Fenetre;

// Controleur du champs de saisie du numéro de téléphone du sms
public class InputControllerTFsmsTel extends InputController {

	public InputControllerTFsmsTel(Fenetre f) {
		super(f);
	}
	
	public void onTextChanged(DocumentEvent event) {
		System.out.println("TextField smsTel changed");
	}
	
	public void onFocusLost(FocusEvent event) {}
	
	public boolean isValid()
	{
		return false;
	}
}
