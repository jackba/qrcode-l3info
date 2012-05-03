package Controller;

import javax.swing.SwingUtilities;

import Vue.Fenetre;

// Controleur principal. Il initialise et lance l'application
public class MainController {
	
	private Fenetre m_fenetre;	// Instance de la fenêtre de l'application
	
	private TFurlController m_TFurlController;
	private TAtxtController m_TAtxtController;
	private TFtelController m_TFtelController;
	private TFsmsTelController m_TFsmsTelController;
	private TAsmsMsgController m_TAsmsMsgController;
	private BgenererController m_BgenererController;
	private BenregistrerController m_BenregistrerController;
	private CmBtailleController m_CmBtailleController;
	
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
		
		// Instanciation des listeners/controleurs
		m_TFurlController = new TFurlController(m_fenetre);
		m_TAtxtController = new TAtxtController(m_fenetre);
		m_TFtelController = new TFtelController(m_fenetre);
		m_TFsmsTelController = new TFsmsTelController(m_fenetre);
		m_TAsmsMsgController = new TAsmsMsgController(m_fenetre);
		m_BgenererController = new BgenererController(m_fenetre);
		m_BenregistrerController = new BenregistrerController(m_fenetre);
		m_CmBtailleController = new CmBtailleController(m_fenetre, m_TFurlController, m_TAtxtController, m_TFtelController, m_TFsmsTelController, m_TAsmsMsgController);
		
		// Assignation des listeners aux composants de la fenetre
		m_fenetre.getTF_url().addFocusListener(m_TFurlController);	// Listener sur le focus
		m_fenetre.getTA_txt().getDocument().addDocumentListener(m_TAtxtController);	// listener sur le changement du texte
		m_fenetre.getTF_tel().getDocument().addDocumentListener(m_TFtelController);
		m_fenetre.getTF_smsTel().getDocument().addDocumentListener(m_TFsmsTelController);
		m_fenetre.getTA_smsMsg().getDocument().addDocumentListener(m_TAsmsMsgController);
		m_fenetre.getB_generer().addActionListener(m_BgenererController);	// Listener sur le click
		m_fenetre.getB_enregistrer().addActionListener(m_BenregistrerController);
		m_fenetre.getCmB_taille().addActionListener(m_CmBtailleController);
		
		// Affichage de la fenêtre
		m_fenetre.setVisible(true);
	}
}
