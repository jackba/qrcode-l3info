package Controller;

import javax.swing.event.DocumentEvent;
import Vue.Fenetre;

public class InputControllerTFsmsTel extends InputController {

	public InputControllerTFsmsTel(Fenetre f) {
		super(f);
	}
	
	public void onTextChanged(DocumentEvent event) {
		System.out.println("TextField smsTel changed");
	}

}
