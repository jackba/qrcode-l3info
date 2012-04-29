package Controller;

import javax.swing.event.DocumentEvent;
import Vue.Fenetre;

public class InputControllerTAsmsMsg extends InputController {

	public InputControllerTAsmsMsg(Fenetre f) {
		super(f);
	}
	
	public void onTextChanged(DocumentEvent event) {
		System.out.println("TextArea smsMsg changed");
	}

}
