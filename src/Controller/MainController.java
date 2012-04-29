package Controller;

import javax.swing.SwingUtilities;

import Vue.Fenetre;

// Controleur principal. Il initialise et lance l'application
public class MainController {
	
	private Fenetre m_fenetre;	// Instance de la fenêtre de l'application
	
	private InputController m_contr_TF_url;	// Controleur du champs de saisie de l'url
	private InputController m_contr_TF_tel;	// Controleur du champs de saisie du numéro de téléphone
	private InputController m_contr_TF_smsTel;	// Controleur du champs de saisie du numéro de téléphone pour le sms
	private InputController m_contr_TA_smsMsg;	// Controleur du champs de saisie du message pour le sms
	private InputController m_contr_TA_txt;	// Controleur du champs de saisie du texte libre
	private ButtonController m_contr_B_generer;	// Controleur du bouton générer
	
	// Lance l'application
	public void runApplication()
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				initView();
			}
		});
	}
	
	// Initialise la fenetre et les controleurs
	public void initView()
	{
		// Instanciation d'une nouvelle fenêtre
		m_fenetre = new Fenetre();
		
		// Création des controleurs subsidiaires
		m_contr_TF_url = new InputControllerTFurl(m_fenetre);
		m_contr_TF_tel = new InputControllerTFtel(m_fenetre);
		m_contr_TF_smsTel = new InputControllerTFsmsTel(m_fenetre);
		m_contr_TA_smsMsg = new InputControllerTAsmsMsg(m_fenetre);
		m_contr_TA_txt = new InputControllerTAtxt(m_fenetre);	
		m_contr_B_generer = new ButtonControllerGenerate(m_fenetre);
		
		// Assignation des controleurs subsidiaires aux listeners pour que ces derniers effectuent une redirection vers les controleurs
		m_fenetre.getListener_TF_url().setController(m_contr_TF_url);
		m_fenetre.getListener_TF_urlValidate().setController(m_contr_TF_url);
		m_fenetre.getListener_TF_tel().setController(m_contr_TF_tel);
		m_fenetre.getListener_TF_smsTel().setController(m_contr_TF_smsTel);
		m_fenetre.getListener_TA_smsMsg().setController(m_contr_TA_smsMsg);
		m_fenetre.getListener_TA_txt().setController(m_contr_TA_txt);
		m_fenetre.getListener_B_generer().setController(m_contr_B_generer);
		
		// Affichage de la fenêtre
		m_fenetre.setVisible(true);
	}

}
