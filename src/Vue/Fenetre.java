package Vue;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.*;

@SuppressWarnings("serial")
public class Fenetre extends JFrame{
	
	private JRadioButton m_RB_url;
	private JRadioButton m_RB_txt;
	private JRadioButton m_RB_tel;
	private JRadioButton m_RB_sms;
	
	private Box m_vB_url;
	private Box m_vB_txt;
	private Box m_vB_tel;
	private Box m_vB_sms;
	
	private JButton m_B_generer;
	
	public Fenetre()
	{
		super();
		build();
	}
	
	private void build()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Générateur de QRcode");
		setSize(320,240);
		setLocationRelativeTo(null);
		
		// Boutons-radios de contenu
		m_RB_url = new JRadioButton("URL");
		m_RB_txt = new JRadioButton("Texte");
		m_RB_tel = new JRadioButton("Numéro de Tel");
		m_RB_sms = new JRadioButton("SMS");
 
        // Ajout des boutons dans un même groupe
        ButtonGroup BG_contenu = new ButtonGroup();
        BG_contenu.add(m_RB_url);
        BG_contenu.add(m_RB_txt);
        BG_contenu.add(m_RB_tel);
        BG_contenu.add(m_RB_sms);
        
        // Ajout des boutons dans une boîte horizontale
        Box hB_boutonsR = Box.createHorizontalBox();
        hB_boutonsR.add(m_RB_url);
        hB_boutonsR.add(m_RB_txt);
        hB_boutonsR.add(m_RB_tel);
        hB_boutonsR.add(m_RB_sms);
        hB_boutonsR.add(Box.createHorizontalGlue());
        
        // Ajout des listeners sur les boutons
        m_RB_url.addActionListener(new UrlListener(this));
        m_RB_txt.addActionListener(new TxtListener(this));
        m_RB_tel.addActionListener(new TelListener(this));
        m_RB_sms.addActionListener(new SmsListener(this));
		
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
		JTextField TF_url = new JTextField(Short.MAX_VALUE);
		TF_url.setMaximumSize(new Dimension(Short.MAX_VALUE,TF_url.getPreferredSize().height));	// Largeur du champs infinie
		Box hB_textfieldUrl = Box.createHorizontalBox();
		hB_textfieldUrl.add(TF_url);
		
		// Saisie URL (Label - TextField)
		m_vB_url = Box.createVerticalBox();
		m_vB_url.add(hB_labelUrl);
		m_vB_url.add(hB_textfieldUrl);
		
		/*
		 *  PANEL TEXTE LIBRE
		 */
		// Label Texte libre - caracteres restants
		JLabel L_txt = new JLabel("Texte libre:");
		JLabel L_txtCount = new JLabel("250 caractères restants");
		Box hB_labelTxt = Box.createHorizontalBox();
		hB_labelTxt.add(L_txt);
		hB_labelTxt.add(Box.createHorizontalGlue());
		hB_labelTxt.add(L_txtCount);
		
		// Champs Texte libre
		JTextArea TA_txt = new JTextArea(Short.MAX_VALUE,Short.MAX_VALUE);
		TA_txt.setLineWrap(true);	// Découpe la ligne lorsqu'on arrive au bout
		TA_txt.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Ajoute une bordure grise d'1 pixel autour de la zone de texte
		TA_txt.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));	// Largeur et hauteur de la zone de texte infinie
		Box hB_textareaTxt = Box.createHorizontalBox();
		hB_textareaTxt.add(TA_txt);
		
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
		JTextField TF_tel = new JTextField(Short.MAX_VALUE);
		TF_tel.setMaximumSize(new Dimension(Short.MAX_VALUE,TF_tel.getPreferredSize().height));	// Largeur du champs infinie
		Box hB_textfieldTel = Box.createHorizontalBox();
		hB_textfieldTel.add(TF_tel);
		
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
		JTextField TF_smsTel = new JTextField(Short.MAX_VALUE);
		TF_smsTel.setMaximumSize(new Dimension(Short.MAX_VALUE,TF_smsTel.getPreferredSize().height));	// Largeur du champs infinie
		Box hB_textfieldSmsTel = Box.createHorizontalBox();
		hB_textfieldSmsTel.add(TF_smsTel);
		
		// Label Message - caracteres restants
		JLabel L_smsMsg = new JLabel("Message:");
		JLabel L_smsMsgCount = new JLabel("160 caractères restants");
		Box hB_labelSmsMsg = Box.createHorizontalBox();
		hB_labelSmsMsg.add(L_smsMsg);
		hB_labelSmsMsg.add(Box.createHorizontalGlue());
		hB_labelSmsMsg.add(L_smsMsgCount);
		
		// Champs Texte libre
		JTextArea TA_smsMsg = new JTextArea(Short.MAX_VALUE,Short.MAX_VALUE);
		TA_smsMsg.setLineWrap(true);	// Découpe la ligne lorsqu'on arrive au bout
		TA_smsMsg.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Ajoute une bordure grise d'1 pixel autour de la zone de texte
		TA_smsMsg.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));	// Largeur et hauteur de la zone de texte infinie
		Box hB_textAreaSmsMsg = Box.createHorizontalBox();
		hB_textAreaSmsMsg.add(TA_smsMsg);
		
		// Saisie SMS - Num de Tel (Label - TextField) - Message (Label1, Label2 - TextArea)
		m_vB_sms = Box.createVerticalBox();
		m_vB_sms.add(hB_labelSmsTel);
		m_vB_sms.add(hB_textfieldSmsTel);
		m_vB_sms.add(hB_labelSmsMsg);
		m_vB_sms.add(hB_textAreaSmsMsg);
		
		// Saisie
		Box vB_saisie = Box.createHorizontalBox();
		
		// On ajoute les différents panneaux
		vB_saisie.add(m_vB_url);
		vB_saisie.add(m_vB_txt);
		vB_saisie.add(m_vB_tel);
		vB_saisie.add(m_vB_sms);
		
		// On masque les différents panneaux. Leur affichage sera géré dynamiquement lors des évènements utilisateurs
		m_vB_url.setVisible(false);
		m_vB_txt.setVisible(false);
		m_vB_tel.setVisible(false);
		m_vB_sms.setVisible(false);
		
		vB_saisie.add(Box.createHorizontalGlue());
		vB_saisie.setBorder(BorderFactory.createTitledBorder("Saisie"));	// Définit la bordure et le titre
		
		// Taille (Label - ComboBox)
		JLabel L_taille = new JLabel("Taille : ");
		String comboBoxItems[] = { "S","M", "L", "XL" };
		JComboBox CmB_taille = new JComboBox(comboBoxItems);
		CmB_taille.setMaximumSize(CmB_taille.getPreferredSize());
		CmB_taille.setAlignmentX(CENTER_ALIGNMENT);
       
        Box hB_taille = Box.createHorizontalBox();
        hB_taille.add(L_taille);
        hB_taille.add(CmB_taille);
        hB_taille.add(Box.createHorizontalGlue());
        
        // Générer (Bouton)
        m_B_generer = new JButton("Générer");
        m_B_generer.addActionListener(new BoutonGenererListener(this));	// Ajout d'un listener sur le bouton
        
        Box hB_generer = Box.createHorizontalBox();
        hB_generer.add(m_B_generer);
        hB_generer.add(Box.createHorizontalGlue());
        
        // Taille - Générer (VerticalBox)
        Box vB_tailleGenerer = Box.createVerticalBox();
        vB_tailleGenerer.add(hB_taille);
        vB_tailleGenerer.add(Box.createVerticalStrut(5));
        vB_tailleGenerer.add(hB_generer);

        // Boite de commandes
        Box vB_commandes = Box.createVerticalBox();
        vB_commandes.add(hB_contenu);
        vB_commandes.add(vB_saisie);
        vB_commandes.add(Box.createVerticalGlue());
        vB_commandes.add(vB_tailleGenerer);
        
        // Panel de l'image QRcode (taille fixée à 200x200 pixels)
        JPanel P_image = new JPanel();
        P_image.setMaximumSize(new Dimension(200,200));
        P_image.setMinimumSize(new Dimension(200,200));
        P_image.setPreferredSize(new Dimension(200,200));
        P_image.setSize(200, 200);
        P_image.setBackground(Color.WHITE);
        
        // Boite de l'image QRcode
        Box vB_image = Box.createVerticalBox();
        vB_image.add(Box.createVerticalGlue());
        vB_image.add(P_image);
        vB_image.add(Box.createVerticalGlue());
        
        // Boite principale
        Box hB_principale = Box.createHorizontalBox();
        hB_principale.add(vB_image);
        hB_principale.add(vB_commandes);
        
        Container c = getContentPane();	// Récupère la zone cliente de la frame (zone dans laquelle on peut placer des composants/conteneurs)
        c.add(hB_principale,BorderLayout.CENTER);	// Place la boite principale dans la fenêtre
        pack();	// Ajuste la taille de la frame de manière à ce que tous les composants soient visibles
        setMinimumSize(getPreferredSize());	// Définit la taille minimale de la frame comme étant la taille après l'opération pack (tous les composants visibles)
	}
	
	// Masque les panels de saisie affichés (en théorie un seul est affiché)
	public void hideShownBoxes()
	{
		if (m_vB_url.isVisible()) m_vB_url.setVisible(false);
		if (m_vB_txt.isVisible()) m_vB_txt.setVisible(false);
		if (m_vB_tel.isVisible()) m_vB_tel.setVisible(false);
		if (m_vB_sms.isVisible()) m_vB_sms.setVisible(false);
	}
	
	// Permet d'afficher les conteneurs Box désirés depuis les listeners
	public void showUrlBox() {m_vB_url.setVisible(true);}
	public void showTxtBox() {m_vB_txt.setVisible(true);}
	public void showTelBox() {m_vB_tel.setVisible(true);}
	public void showSmsBox() {m_vB_sms.setVisible(true);}
}
