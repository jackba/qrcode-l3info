package Controller;

import javax.swing.event.DocumentEvent;

import Vue.Fenetre;

public class InputControllerTAtxt extends InputController {

	public InputControllerTAtxt(Fenetre f) {
		super(f);
	}
	
	public void onTextChanged(DocumentEvent event) {
		System.out.println("TextArea txt changed");
	}
}
