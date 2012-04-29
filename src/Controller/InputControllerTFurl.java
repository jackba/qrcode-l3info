package Controller;

import javax.swing.event.DocumentEvent;

import Vue.Fenetre;

// Classe chargée du controle du JTextField de l'url
public class InputControllerTFurl extends InputController {

	public InputControllerTFurl(Fenetre f) {
		super(f);
	}
	
	public void onTextChanged(DocumentEvent event) {
		System.out.println("TextField url has changed");
	}

}
