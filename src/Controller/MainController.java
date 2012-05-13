package Controller;

import javax.swing.SwingUtilities;

import Vue.Fenetre;
import Vue.QRcodeComponent;

// Controleur principal. Il initialise et lance l'application
public class MainController {
	
	private Fenetre m_fenetre;	// Instance de la fenêtre de l'application
	private TFurlController m_TFurlController;
	private TAtxtController m_TAtxtController;
	private TFtelController m_TFtelController;
	private TFsmsTelController m_TFsmsTelController;
	private TAsmsMsgController m_TAsmsMsgController;
	private TFimgPathController m_TFimgPathController;
	private TFdecodeImgPathController m_TFdecodeImgPathController;
	private BgenererController m_BgenererController;
	private BenregistrerController m_BenregistrerController;
	private BchargerController m_BchargerImgController;
	private BchargerController m_BchargerDecodeImgController;
	private BdecoderController m_BdecoderController;
	private CmBtailleController m_CmBtailleController;
	private RBlevelsController m_RBlevelsController;
	private RBcontentsController m_RBcontentsController;
	private QRColorationController m_QRcolorationController;
	
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
		m_TAsmsMsgController = new TAsmsMsgController(m_fenetre, m_TFsmsTelController);
		m_TFimgPathController = new TFimgPathController(m_fenetre);
		m_TFdecodeImgPathController = new TFdecodeImgPathController(m_fenetre);
		m_BenregistrerController = new BenregistrerController(m_fenetre);
		m_BgenererController = new BgenererController(m_fenetre, m_TFurlController, m_TAtxtController, m_TFtelController, m_TFsmsTelController, m_TAsmsMsgController, m_TFimgPathController);
		m_BchargerImgController = new BchargerController(m_fenetre, m_fenetre.getTF_imgPath());
		m_BchargerDecodeImgController = new BchargerController(m_fenetre, m_fenetre.getTF_decodeImgPath());
		m_BdecoderController = new BdecoderController(m_fenetre);
		m_CmBtailleController = new CmBtailleController(m_fenetre, m_TFurlController, m_TAtxtController, m_TFtelController, m_TFsmsTelController, m_TAsmsMsgController);
		m_RBlevelsController = new RBlevelsController(m_fenetre, m_TFurlController, m_TAtxtController, m_TFtelController, m_TFsmsTelController, m_TAsmsMsgController);
		m_RBcontentsController = new RBcontentsController(m_fenetre, m_TFurlController, m_TAtxtController, m_TFtelController, m_TFsmsTelController, m_TAsmsMsgController);
		m_QRcolorationController = new QRColorationController(m_fenetre,m_fenetre.getQrModifiable());
		
		// Assignation des listeners aux composants de la fenetre
		m_fenetre.getTF_url().addFocusListener(m_TFurlController);	// Listener sur le focus
		m_fenetre.getTF_url().getDocument().addDocumentListener(m_TFurlController); // listener sur le changement du texte
		m_fenetre.getTA_txt().getDocument().addDocumentListener(m_TAtxtController);
		m_fenetre.getTF_tel().getDocument().addDocumentListener(m_TFtelController);
		m_fenetre.getTF_smsTel().getDocument().addDocumentListener(m_TFsmsTelController);
		m_fenetre.getTA_smsMsg().getDocument().addDocumentListener(m_TAsmsMsgController);
		m_fenetre.getTF_imgPath().getDocument().addDocumentListener(m_TFimgPathController);
		m_fenetre.getTF_decodeImgPath().getDocument().addDocumentListener(m_TFdecodeImgPathController);
		m_fenetre.getB_generer().addActionListener(m_BgenererController);	// Listener sur le click
		m_fenetre.getB_enregistrer().addActionListener(m_BenregistrerController);
		m_fenetre.getB_charger().addActionListener(m_BchargerImgController);
		m_fenetre.getB_chargerDecode().addActionListener(m_BchargerDecodeImgController);
		m_fenetre.getB_decoder().addActionListener(m_BdecoderController);
		m_fenetre.getCmB_taille().addActionListener(m_QRcolorationController);
		m_fenetre.getCmB_taille().addActionListener(m_CmBtailleController);
		m_fenetre.getRB_correctionL().addActionListener(m_RBlevelsController);
		m_fenetre.getRB_correctionM().addActionListener(m_RBlevelsController);
		m_fenetre.getRB_correctionQ().addActionListener(m_RBlevelsController);
		m_fenetre.getRB_correctionH().addActionListener(m_RBlevelsController);
		m_fenetre.getRB_url().addActionListener(m_RBcontentsController);
		m_fenetre.getRB_txt().addActionListener(m_RBcontentsController);
		m_fenetre.getRB_tel().addActionListener(m_RBcontentsController);
		m_fenetre.getRB_sms().addActionListener(m_RBcontentsController);
		m_fenetre.getRB_image().addActionListener(m_RBcontentsController);
		m_fenetre.getRB_coloriage().addActionListener(m_RBcontentsController);
		m_fenetre.getRB_decode().addActionListener(m_RBcontentsController);
		m_fenetre.getQrModifiable().addMouseListener(m_QRcolorationController);
		
		// DEBUG
		//m_fenetre.addMouseMotionListener(new TestController(m_fenetre));
		
		// Initialisation
		m_CmBtailleController.actionPerformed(null);	// Récupération de la taille auto
		m_fenetre.getRB_correctionL().setSelected(true);	// Niveau de correction d'erreur L coché
		m_fenetre.getRB_txt().setSelected(true);	// panel de texte coché
		m_fenetre.showTxtBox();	// affichage du panel de texte
		m_fenetre.getB_enregistrer().setVisible(false);	// On masque le m=bouton enregistrer qui n'a pas d'intérêt à être visible puisqu'il n'y a pas encore de qrCode de généré
		m_fenetre.getB_generer().setEnabled(false);
		m_fenetre.getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(CharacterMode.BYTES));
		m_fenetre.getB_decoder().setEnabled(false);
		
		// Affichage de la fenêtre
		m_fenetre.setVisible(true);
	}
}
