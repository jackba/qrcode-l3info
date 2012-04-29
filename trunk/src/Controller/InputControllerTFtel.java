package Controller;

import java.awt.event.FocusEvent;
import javax.swing.event.DocumentEvent;

import Vue.Fenetre;

// Controleur du champs de saisie du numéro de téléphone
public class InputControllerTFtel extends InputController {

	public InputControllerTFtel(Fenetre f) {
		super(f);
	}
	
	public void onTextChanged(DocumentEvent event) {
		System.out.println("TextField Tel changed");
	}
	
	public void onFocusLost(FocusEvent event) {}

	public boolean isValid()
	{
		return false;
	}
}
