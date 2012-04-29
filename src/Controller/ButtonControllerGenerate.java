package Controller;

import java.awt.event.ActionEvent;

import Vue.Fenetre;

public class ButtonControllerGenerate extends ButtonController {

	public ButtonControllerGenerate(Fenetre f) {
		super(f);
	}

	public void onClick(ActionEvent event)
	{
		System.out.println("Button Generate clicked");
	}

}
