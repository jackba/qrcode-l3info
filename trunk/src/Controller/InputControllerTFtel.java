package Controller;

import javax.swing.event.DocumentEvent;

import Vue.Fenetre;

public class InputControllerTFtel extends InputController {

	public InputControllerTFtel(Fenetre f) {
		super(f);
	}
	
	public void onTextChanged(DocumentEvent event) {
		System.out.println("TextField Tel changed");
	}

}
