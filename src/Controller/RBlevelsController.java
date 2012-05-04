package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Vue.Fenetre;

public class RBlevelsController extends AbstractController implements ActionListener{
	
	private TFurlController m_TFurlController;
	private TAtxtController m_TAtxtController;
	private TFtelController m_TFtelController;
	private TFsmsTelController m_TFsmsTelController;
	private TAsmsMsgController m_TAsmsMsgController;
	
	public RBlevelsController(Fenetre f,
			TFurlController urlController,
			TAtxtController txtController,
			TFtelController telController,
			TFsmsTelController smsTelController,
			TAsmsMsgController smsMsgController)
	{
		super(f);
		m_TFurlController = urlController;
		m_TAtxtController = txtController;
		m_TFtelController = telController;
		m_TFsmsTelController = smsTelController;
		m_TAsmsMsgController = smsMsgController;
	}

	public void actionPerformed(ActionEvent event)
	{
		// Notifie les controleurs des champs de texte que le nombre maximum de caractères a changé
		m_TFurlController.onMaxCharsChanged();
		m_TAtxtController.onMaxCharsChanged();
		m_TFtelController.onMaxCharsChanged();
		m_TFsmsTelController.onMaxCharsChanged();
		m_TAsmsMsgController.onMaxCharsChanged();
	}
	
	public boolean isValid() {
		return true;
	}

}
