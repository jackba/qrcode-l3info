package Vue;
import java.awt.BorderLayout;
//import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.*;

@SuppressWarnings("serial")
public class Fenetre extends JFrame {
	
	private JRadioButton m_RB_url;
	private JRadioButton m_RB_txt;
	private JRadioButton m_RB_tel;
	private JRadioButton m_RB_sms;
	private JRadioButton m_RB_image;
	private JRadioButton m_RB_coloriage;
	private JRadioButton m_RB_decode;
	private JRadioButton m_RB_correctionL;
	private JRadioButton m_RB_correctionM;
	private JRadioButton m_RB_correctionQ;
	private JRadioButton m_RB_correctionH;
	
	private JComboBox m_CmB_taille;
	
	private Box m_vB_url;
	private Box m_vB_txt;
	private Box m_vB_tel;
	private Box m_vB_sms;
	private Box m_vB_image;
	private Box m_vB_coloriage;
	private Box m_vB_decode;
	private Box m_hB_textResult;
	private Box m_hB_imageResult;
	private Box m_hB_saveResult;
	
	private JButton m_B_generer;
	private JButton m_B_enregistrer;
	private JButton m_B_imgLoad;
	private JButton m_B_decodeImgLoad;
	private JButton m_B_decodeProcess;
	private JButton m_B_resultSave;
	private JButton m_B_saveQrModif;
	
	private JTextField m_TF_url;
	private JTextField m_TF_tel;
	private JTextField m_TF_smsTel;
	private JTextField m_TF_imgPath;
	private JTextField m_TF_decodeImgPath;
	
	private QRcodeComponent m_qrModifiable;
	
	private JTextArea m_TA_txt;
	private JTextArea m_TA_smsMsg;
	private JTextArea m_TA_result;
	
	private JLabel m_L_txtCount;
	private JLabel m_L_smsMsgCount;
	private JLabel m_L_modeIndicator;
	private JLabel m_L_decodeNotif;
	private JLabel m_L_imgNotif;
	
	private QRcodeComponent m_qrPanel;
	private ImageComponent m_ImageComponent;
	
	public Fenetre()
	{
		super();
		build();
	}
	
	// Initialise et place les composants graphiques
	private void build()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Générateur de QRcode");
		// setSize(320,240);
		setLocationRelativeTo(null);
		
		// Application du look and feel du systeme.
		// Commenter le groupe de try/catch suivant pour
		// revenir au style Java par défaut (généralement moins beau).
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		// Boutons-radios de contenu
		m_RB_url = new JRadioButton("URL");
		m_RB_txt = new JRadioButton("Texte");
		m_RB_tel = new JRadioButton("Numéro de Tel");
		m_RB_sms = new JRadioButton("SMS");
		m_RB_image = new JRadioButton("Image");
		m_RB_coloriage = new JRadioButton("Coloriage");
		m_RB_decode = new JRadioButton("Décodeur");
 
        // Ajout des boutons dans un même groupe
        ButtonGroup BG_contenu = new ButtonGroup();
        BG_contenu.add(m_RB_url);
        BG_contenu.add(m_RB_txt);
        BG_contenu.add(m_RB_tel);
        BG_contenu.add(m_RB_sms);
        BG_contenu.add(m_RB_image);
        BG_contenu.add(m_RB_coloriage);
        BG_contenu.add(m_RB_decode);
        
        // Ajout des boutons dans une boîte horizontale
        Box hB_boutonsR = Box.createHorizontalBox();
        hB_boutonsR.add(m_RB_url);
        hB_boutonsR.add(m_RB_txt);
        hB_boutonsR.add(m_RB_tel);
        hB_boutonsR.add(m_RB_sms);
        hB_boutonsR.add(m_RB_image);
        hB_boutonsR.add(m_RB_coloriage);
        hB_boutonsR.add(m_RB_decode);
        hB_boutonsR.add(Box.createHorizontalGlue());
        
		// Contenu
		Box hB_contenu = Box.createHorizontalBox();
		hB_contenu.add(hB_boutonsR);	// Ajout des boutons-radio dans le panel de contenu
		hB_contenu.setBorder(BorderFactory.createTitledBorder("Contenu"));	// Définit la bordure et le titre
		hB_contenu.add(Box.createHorizontalGlue());
		
		/*
		 *  PANEL URL
		 */
		// Label URL
		JLabel L_url = new JLabel("URL:");
		Box hB_labelUrl = Box.createHorizontalBox();
		hB_labelUrl.add(L_url);
		hB_labelUrl.add(Box.createHorizontalGlue());
		
		// Champs texte URL
		m_TF_url = new JTextField(Short.MAX_VALUE);
		m_TF_url.setMaximumSize(new Dimension(Short.MAX_VALUE,m_TF_url.getPreferredSize().height));	// Largeur du champs infinie
		Box hB_textfieldUrl = Box.createHorizontalBox();
		hB_textfieldUrl.add(m_TF_url);
		
		// Saisie URL (Label - TextField)
		m_vB_url = Box.createVerticalBox();
		m_vB_url.add(hB_labelUrl);
		m_vB_url.add(hB_textfieldUrl);
		
		/*
		 *  PANEL TEXTE LIBRE
		 */
		// Label Texte libre - caracteres restants
		JLabel L_txt = new JLabel("Texte libre:");
		m_L_txtCount = new JLabel("250 caractères restants");
		Box hB_labelTxt = Box.createHorizontalBox();
		hB_labelTxt.add(L_txt);
		hB_labelTxt.add(Box.createHorizontalGlue());
		hB_labelTxt.add(m_L_txtCount);
		
		// Champs Texte libre
		m_TA_txt = new JTextArea(Short.MAX_VALUE,Short.MAX_VALUE);
		m_TA_txt.setLineWrap(true);	// Découpe la ligne lorsqu'on arrive au bout
		m_TA_txt.setWrapStyleWord(true);	// Effectue un retour à la ligne si le mot est trop long pour être affiché en bout de ligne
		m_TA_txt.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));	// Largeur et hauteur de la zone de texte infinie
		m_TA_txt.setMinimumSize(new Dimension(0,0));	// Largeur et hauteur de la zone de texte infinie
		JScrollPane SP_TAtxt = new JScrollPane(m_TA_txt,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Box hB_textareaTxt = Box.createHorizontalBox();
		hB_textareaTxt.add(SP_TAtxt);
		
		// Saisie Texte libre (Label1, Label2 - TextArea)
		m_vB_txt = Box.createVerticalBox();
		m_vB_txt.add(hB_labelTxt);
		m_vB_txt.add(hB_textareaTxt);
		
		/*
		 * PANEL NUMERO DE TEL
		 */
		// Label Num de Tel
		JLabel L_tel = new JLabel("Numéro de Téléphone:");
		Box hB_labelTel = Box.createHorizontalBox();
		hB_labelTel.add(L_tel);
		hB_labelTel.add(Box.createHorizontalGlue());
		
		// Champs texte Num de Tel
		m_TF_tel = new JTextField(Short.MAX_VALUE);
		m_TF_tel.setMaximumSize(new Dimension(Short.MAX_VALUE, m_TF_tel.getPreferredSize().height));	// Largeur du champs infinie
		Box hB_textfieldTel = Box.createHorizontalBox();
		hB_textfieldTel.add(m_TF_tel);
		
		// Saisie Num de Tel (Label - TextField)
		m_vB_tel = Box.createVerticalBox();
		m_vB_tel.add(hB_labelTel);
		m_vB_tel.add(hB_textfieldTel);
		
		/*
		 * PANEL SMS
		 */
		// Label SMS - Num de tel
		JLabel L_smsTel = new JLabel("Numéro de Téléphone:");
		Box hB_labelSmsTel = Box.createHorizontalBox();
		hB_labelSmsTel.add(L_smsTel);
		hB_labelSmsTel.add(Box.createHorizontalGlue());
		
		// Champs texte SMS - Num de Tel
		m_TF_smsTel = new JTextField(Short.MAX_VALUE);
		m_TF_smsTel.setMaximumSize(new Dimension(Short.MAX_VALUE,m_TF_smsTel.getPreferredSize().height));	// Largeur du champs infinie
		Box hB_textfieldSmsTel = Box.createHorizontalBox();
		hB_textfieldSmsTel.add(m_TF_smsTel);
		
		// Label Message - caracteres restants
		JLabel L_smsMsg = new JLabel("Message:");
		m_L_smsMsgCount = new JLabel("160 caractères restants");
		Box hB_labelSmsMsg = Box.createHorizontalBox();
		hB_labelSmsMsg.add(L_smsMsg);
		hB_labelSmsMsg.add(Box.createHorizontalGlue());
		hB_labelSmsMsg.add(m_L_smsMsgCount);
		
		// Champs Texte libre
		m_TA_smsMsg = new JTextArea(Short.MAX_VALUE,Short.MAX_VALUE);
		m_TA_smsMsg.setLineWrap(true);	// Découpe la ligne lorsqu'on arrive au bout
		m_TA_smsMsg.setWrapStyleWord(true);	// Effectue un retour à la ligne si le mot est trop long pour être affiché en bout de ligne
		m_TA_smsMsg.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));	// Largeur et hauteur de la zone de texte infinie
		m_TA_smsMsg.setMinimumSize(new Dimension(0,0));	// Largeur et hauteur minimum de la zone de texte nulle
		JScrollPane SP_TAsms = new JScrollPane(m_TA_smsMsg,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Box hB_textAreaSmsMsg = Box.createHorizontalBox();
		hB_textAreaSmsMsg.add(SP_TAsms);
		
		// Saisie SMS - Num de Tel (Label - TextField) - Message (Label1, Label2 - TextArea)
		m_vB_sms = Box.createVerticalBox();
		m_vB_sms.add(hB_labelSmsTel);
		m_vB_sms.add(hB_textfieldSmsTel);
		m_vB_sms.add(hB_labelSmsMsg);
		m_vB_sms.add(hB_textAreaSmsMsg);
		
		/*
		 * PANEL IMAGE
		 */
		// Boite de chargement
		m_B_imgLoad = new JButton("Charger");
		m_TF_imgPath = new JTextField(Short.MAX_VALUE);
		m_TF_imgPath.setMaximumSize(new Dimension(Short.MAX_VALUE, m_B_imgLoad.getPreferredSize().height));
		
		Box hB_buttonPath = Box.createHorizontalBox();
		hB_buttonPath.add(m_B_imgLoad);
		hB_buttonPath.add(m_TF_imgPath);
		hB_buttonPath.add(Box.createHorizontalGlue());
		
		// Boite de notification
		m_L_imgNotif = new JLabel("");
		
		Box hB_imgNotification = Box.createHorizontalBox();
		hB_imgNotification.add(m_L_imgNotif);
		hB_imgNotification.add(Box.createHorizontalGlue());
		
		m_vB_image = Box.createVerticalBox();
		m_vB_image.add(hB_buttonPath);
		m_vB_image.add(hB_imgNotification);
		
		/*
		 * PANEL COLORIAGE
		 */
		m_B_saveQrModif = new JButton("Enregistrer");
		JLabel L_qrcoloriage = new JLabel("QRCode à modifier :");
		Box hB_labelColoriage = Box.createHorizontalBox();
		hB_labelColoriage.add(L_qrcoloriage);
		hB_labelColoriage.add(Box.createHorizontalGlue());
		
		m_qrModifiable = new QRcodeComponent();
		m_qrModifiable.setMinimumSize(getMaximumSize());
		m_qrModifiable.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		m_qrModifiable.setMinimumSize(getMaximumSize());
		Box hB_qrModifiable = Box.createHorizontalBox();
		hB_qrModifiable.add(Box.createHorizontalGlue());
		hB_qrModifiable.add(m_qrModifiable);
		hB_qrModifiable.add(Box.createHorizontalGlue());
		
		m_vB_coloriage = Box.createVerticalBox();
		m_vB_coloriage.add(hB_labelColoriage);
		m_vB_coloriage.add(hB_qrModifiable);
		m_vB_coloriage.add(m_B_saveQrModif);

		/*
		 * TODO: Ajouter des box, composants, etc pour le coloriage manuel du QRcode
		 * 
		 * Quelques méthodes utiles pour construire l'interface:
		 * - Créer une boite horizontale: Box maBoiteHorizontale = Box.createHorizontalBox();
		 * - Créer une boite verticale: Box maBoiteVerticale = Box.createVerticalBox();
		 * - Ajouter un composant dans une boite: maBoite.add(monComposant);
		 * - Ajouter un espace de taille fixe dans la boite (où XXX = horizontal ou vertical): maBoite.add(Box.createXXXStrut());
		 * - Ajouter un espace flexible (équivalent des stretchs dans d'autres librairies): maBoite.add(Box.createXXXGlue());
		 * 
		 * Pour faire en sorte qu'un composant se redimensionne en prenant toujours tout l'espace disponible en largeur et en hauteur:
		 * - monComposant.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		 * 
		 * Pour empecher qu'un composant se redimensionne (en hauteur par exemple):
		 * - monComposant.setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
		 * 
		 * Enfin, une fois que tous les composants sont créés et agencés dans des boites, les ajouter dans la boite m_vB_coloriage
		 * Qui est automatiquement masquée/affichée lorsque l'utilisateur clique sur les boutons radios dans l'interface.
		 */
		
		/*
		 * PANEL DECODEUR
		 */
		
		// Boite de décodage
		m_B_decodeImgLoad = new JButton("Charger");
		m_TF_decodeImgPath = new JTextField(Short.MAX_VALUE);
		m_TF_decodeImgPath.setMaximumSize(new Dimension(Short.MAX_VALUE, m_B_decodeImgLoad.getPreferredSize().height));
		Component strut = Box.createHorizontalStrut(30);
		strut.setMaximumSize(new Dimension(30,m_B_decodeImgLoad.getPreferredSize().height));
		m_B_decodeProcess = new JButton("Décoder");
		
		Box hB_decodeButtonPath = Box.createHorizontalBox();
		hB_decodeButtonPath.add(m_B_decodeImgLoad);
		hB_decodeButtonPath.add(m_TF_decodeImgPath);
		hB_decodeButtonPath.add(strut);
		hB_decodeButtonPath.add(m_B_decodeProcess);
		hB_decodeButtonPath.add(Box.createHorizontalGlue());
		
		// Boite de notification
		m_L_decodeNotif = new JLabel("");
		
		Box hB_decodeNotification = Box.createHorizontalBox();
		hB_decodeNotification.add(m_L_decodeNotif);
		hB_decodeNotification.add(Box.createHorizontalGlue());
		
		// Boite de résultat textuel
		// Champs de texte du résultat avec possible saisie
		m_TA_result = new JTextArea(Short.MAX_VALUE,Short.MAX_VALUE);
		m_TA_result.setLineWrap(true);	// Découpe la ligne lorsqu'on arrive au bout
		m_TA_result.setWrapStyleWord(true);	// Effectue un retour à la ligne si le mot est trop long pour être affiché en bout de ligne
		m_TA_result.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));	// Largeur et hauteur de la zone de texte infinie
		m_TA_result.setMinimumSize(new Dimension(0,0));	// Largeur et hauteur minimum de la zone de texte nulle
		JScrollPane SP_TAresult = new JScrollPane(m_TA_result,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		m_hB_textResult = Box.createHorizontalBox();
		m_hB_textResult.add(SP_TAresult);
		
		// Boite de résultat image
		m_ImageComponent = new ImageComponent();
		m_ImageComponent.setPreferredSize(new Dimension(20,20));
		
		m_hB_imageResult = Box.createHorizontalBox();
		m_hB_imageResult.add(m_ImageComponent);
		
		// Bouton de sauvegarde
		m_B_resultSave = new JButton("Sauvegarder");
		// Boite du bouton de sauvegarde
		m_hB_saveResult = Box.createHorizontalBox();
		m_hB_saveResult.add(Box.createHorizontalGlue());
		m_hB_saveResult.add(m_B_resultSave);
		m_hB_saveResult.add(Box.createHorizontalGlue());
		
		m_vB_decode = Box.createVerticalBox();
		m_vB_decode.add(hB_decodeButtonPath);
		m_vB_decode.add(hB_decodeNotification);
		m_vB_decode.add(m_hB_textResult);
		m_vB_decode.add(m_hB_imageResult);
		m_vB_decode.add(m_hB_saveResult);
		
		m_hB_textResult.setVisible(false);
		m_hB_imageResult.setVisible(false);
		
		// Saisie
		Box vB_saisie = Box.createHorizontalBox();
		
		// On ajoute les différents panneaux
		vB_saisie.add(m_vB_url);
		vB_saisie.add(m_vB_txt);
		vB_saisie.add(m_vB_tel);
		vB_saisie.add(m_vB_sms);
		vB_saisie.add(m_vB_image);
		vB_saisie.add(m_vB_coloriage);
		vB_saisie.add(m_vB_decode);
		
		// On masque les différents panneaux. Leur affichage sera géré dynamiquement lors des évènements utilisateurs
		m_vB_url.setVisible(false);
		m_vB_txt.setVisible(false);
		m_vB_tel.setVisible(false);
		m_vB_sms.setVisible(false);
		m_vB_image.setVisible(false);
		m_vB_coloriage.setVisible(false);
		m_vB_decode.setVisible(false);
		
		vB_saisie.add(Box.createHorizontalGlue());	// Etend la boite sur toute la largeur
		vB_saisie.setBorder(BorderFactory.createTitledBorder("Saisie"));	// Définit la bordure et le titre
		
		/*
		 *  PANEL PARAMETRES 
		 */
		
		// Composants de correction d'erreur
		// Correction (Label)
		JLabel L_correct = new JLabel("Correction : ");

		// Boutons-radios de version
		m_RB_correctionL = new JRadioButton("L (7%)");
		m_RB_correctionM = new JRadioButton("M (15%)");
		m_RB_correctionQ = new JRadioButton("Q (25%)");
		m_RB_correctionH = new JRadioButton("H (30%)");

		// Ajout des boutons dans un même groupe
		ButtonGroup BG_correction = new ButtonGroup();
		BG_correction.add(m_RB_correctionL);
		BG_correction.add(m_RB_correctionM);
		BG_correction.add(m_RB_correctionQ);
		BG_correction.add(m_RB_correctionH);

		// Ajout des composants dans une boîte horizontale
		Box hB_correction = Box.createHorizontalBox();
		hB_correction.add(L_correct);
		hB_correction.add(m_RB_correctionL);
		hB_correction.add(m_RB_correctionM);
		hB_correction.add(m_RB_correctionQ);
		hB_correction.add(m_RB_correctionH);
		hB_correction.add(Box.createHorizontalGlue());

		// Composants de taille
		// Version (Label)
		JLabel L_taille = new JLabel("Taille : ");

		// Combo-box pour les niveaux de correction d'erreur
		String itemsTaille[] = new String[41];	// items pour peupler la combo-box
		itemsTaille[0] = "Auto";
		for (int i=1; i<41; i++)	// Remplissage des items
			itemsTaille[i] = Integer.toString(i);
		m_CmB_taille = new JComboBox(itemsTaille);	// Création de la combo box
		
		// Attribution d'une taille max et d'un alignement horizontal centré
		m_CmB_taille.setMaximumSize(m_CmB_taille.getPreferredSize());
		m_CmB_taille.setAlignmentX(CENTER_ALIGNMENT);
		
		// Label du mode
		m_L_modeIndicator = new JLabel("(Mode byte)");
		
		// Structure séparant le label et la combo-box
		Component struc = Box.createHorizontalStrut(8);
		struc.setMaximumSize(new Dimension(8,m_CmB_taille.getPreferredSize().height));
		
		// Ajout des composants dans une boîte horizontale
		Box hB_taille = Box.createHorizontalBox();
		hB_taille.add(L_taille);
		hB_taille.add(m_CmB_taille);
		hB_taille.add(struc);
		hB_taille.add(m_L_modeIndicator);
		hB_taille.add(Box.createHorizontalGlue());
		
		Box vB_params = Box.createVerticalBox();
		vB_params.add(hB_correction);
		vB_params.add(hB_taille);

		// Boite parametres
		Box hB_parametre = Box.createHorizontalBox();
		hB_parametre.add(vB_params);
		hB_parametre.add(Box.createHorizontalGlue());	// Etend la boite sur toute la largeur
		hB_parametre.setBorder(BorderFactory.createTitledBorder("Paramètres"));	// Définit la bordure et le titre
		
        // Générer (Bouton)
        m_B_generer = new JButton("Générer");
        
        Box hB_generer = Box.createHorizontalBox();
        hB_generer.add(m_B_generer);
        hB_generer.add(Box.createHorizontalGlue());
        
        // Paramètres - Générer (VerticalBox)
        Box vB_tailleGenerer = Box.createVerticalBox();
        vB_tailleGenerer.add(hB_parametre);
        vB_tailleGenerer.add(Box.createVerticalStrut(5));
        vB_tailleGenerer.add(hB_generer);

        // Boite de commandes
        Box vB_commandes = Box.createVerticalBox();
        vB_commandes.add(hB_contenu);
        vB_commandes.add(vB_saisie);
        vB_commandes.add(Box.createVerticalGlue());
        vB_commandes.add(vB_tailleGenerer);
        
        // Panel de l'image QRcode (taille fixée à 200x200 pixels)
        m_qrPanel = new QRcodeComponent();
        m_qrPanel.setPreferredSize(new Dimension(200,200));
        m_qrPanel.setMaximumSize(new Dimension(200,200));
        m_qrPanel.setMinimumSize(new Dimension(200,200));
        m_qrPanel.setSize(200, 200);
        
        m_B_enregistrer = new JButton("Enregistrer");
        m_B_enregistrer.setAlignmentX(CENTER_ALIGNMENT);
        
        // Boite de l'image QRcode
        Box vB_image = Box.createVerticalBox();
        vB_image.add(Box.createVerticalGlue());
        vB_image.add(m_qrPanel);
        vB_image.add(m_B_enregistrer);
        vB_image.add(Box.createVerticalGlue());
        
        // Boite principale
        Box hB_principale = Box.createHorizontalBox();
        hB_principale.add(vB_image);
        hB_principale.add(vB_commandes);
        
        Container c = getContentPane();	// Récupère la zone cliente de la frame (zone dans laquelle on peut placer des composants/conteneurs)
        c.add(hB_principale,BorderLayout.CENTER);	// Place la boite principale dans la fenêtre
        pack();	// Ajuste la taille de la frame de manière à ce que tous les composants soient visibles
        setMinimumSize(new Dimension(getBaseWidth(),getBaseHeight()));	// Définit la taille minimale de la frame de manière à ce que tous les composants soient bien visibles
	}
	
	// Masque les panels de saisie affichés (en théorie un seul est affiché)
	public void hideShownBoxes()
	{
		if (m_vB_url.isVisible()) m_vB_url.setVisible(false);
		if (m_vB_txt.isVisible()) m_vB_txt.setVisible(false);
		if (m_vB_tel.isVisible()) m_vB_tel.setVisible(false);
		if (m_vB_sms.isVisible()) m_vB_sms.setVisible(false);
		if (m_vB_image.isVisible()) m_vB_image.setVisible(false);
		if (m_vB_coloriage.isVisible()) m_vB_coloriage.setVisible(false);
		if (m_vB_decode.isVisible()) m_vB_decode.setVisible(false);
	}
	
	// Permet d'afficher les conteneurs Box désirés depuis les listeners
	public void showUrlBox() {m_vB_url.setVisible(true);}
	public void showTxtBox() {m_vB_txt.setVisible(true);}
	public void showTelBox() {m_vB_tel.setVisible(true);}
	public void showSmsBox() {m_vB_sms.setVisible(true);}
	public void showImgBox() {m_vB_image.setVisible(true);}
	public void showPaintBox() {m_vB_coloriage.setVisible(true);}
	public void showDecodeBox() {m_vB_decode.setVisible(true);}
	
	// Meme chose qu'au dessus, mais à l'intérieur de la box de résultat
	public void showResultTextBox() {m_hB_textResult.setVisible(true);}
	public void showResultImgBox() {m_hB_imageResult.setVisible(true);}
	public void showResultSaveBox() {m_hB_saveResult.setVisible(true);}
	public void hideResultSaveBox() {m_hB_saveResult.setVisible(false);}
	public boolean isResultTextBoxVisible() {return m_hB_textResult.isVisible();}
	public boolean isResultImageBoxVisible() {return m_hB_imageResult.isVisible();}
	
	// Masque les boites de résultat affichées
	public void hideShownResultBoxes()
	{
		if (m_hB_textResult.isVisible()) m_hB_textResult.setVisible(false);
		if (m_hB_imageResult.isVisible()) m_hB_imageResult.setVisible(false);
	}

	/*
	 *  GETTERS
	 */
	public JRadioButton getRB_url() {
		return m_RB_url;
	}

	public JRadioButton getRB_txt() {
		return m_RB_txt;
	}

	public JRadioButton getRB_tel() {
		return m_RB_tel;
	}

	public JRadioButton getRB_sms() {
		return m_RB_sms;
	}
	
	public JRadioButton getRB_image() {
		return m_RB_image;
	}
	
	public JRadioButton getRB_coloriage() {
		return m_RB_coloriage;
	}
	
	public JRadioButton getRB_decode() {
		return m_RB_decode;
	}

	public JButton getB_generer() {
		return m_B_generer;
	}
	
	public JButton getB_enregistrer() {
		return m_B_enregistrer;
	}
	
	public JButton getB_saveQrModif(){
		return this.m_B_saveQrModif;
	}
	
	public JButton getB_charger() {
		return m_B_imgLoad;
	}
	
	public JButton getB_chargerDecode() {
		return m_B_decodeImgLoad;
	}
	
	public JButton getB_decoder() {
		return m_B_decodeProcess;
	}
	
	public JButton getB_resultSave() {
		return m_B_resultSave;
	}
	
	public JTextField getTF_url() {
		return m_TF_url;
	}

	public JTextField getTF_tel() {
		return m_TF_tel;
	}

	public JTextField getTF_smsTel() {
		return m_TF_smsTel;
	}
	
	public JTextField getTF_imgPath() {
		return m_TF_imgPath;
	}
	
	public JTextField getTF_decodeImgPath() {
		return m_TF_decodeImgPath;
	}

	public JTextArea getTA_txt() {
		return m_TA_txt;
	}

	public JTextArea getTA_smsMsg() {
		return m_TA_smsMsg;
	}
	
	public JTextArea getTA_result() {
		return m_TA_result;
	}

	public JLabel getL_txtCount() {
		return m_L_txtCount;
	}

	public JLabel getL_smsMsgCount() {
		return m_L_smsMsgCount;
	}
	
	public JLabel getL_decodeNotif() {
		return m_L_decodeNotif;
	}
	
	public JLabel getL_imgNotif() {
		return m_L_imgNotif;
	}
	
	public QRcodeComponent getQrPanel() {
		return m_qrPanel;
	}
	
	public ImageComponent getImageComponent() {
		return m_ImageComponent;
	}

	public JRadioButton getRB_correctionL() {
		return m_RB_correctionL;
	}

	public JRadioButton getRB_correctionM() {
		return m_RB_correctionM;
	}

	public JRadioButton getRB_correctionQ() {
		return m_RB_correctionQ;
	}

	public JRadioButton getRB_correctionH() {
		return m_RB_correctionH;
	}

	public JComboBox getCmB_taille() {
		return m_CmB_taille;
	}
	
	public JLabel getL_modeIndicator() {
		return m_L_modeIndicator;
	}
	
	public QRcodeComponent getQrModifiable(){
		return m_qrModifiable;
	}
	
	public int getBaseWidth()
	{
		return 780;
	}
	
	public int getBaseHeight()
	{
		return 400;
	}
}
