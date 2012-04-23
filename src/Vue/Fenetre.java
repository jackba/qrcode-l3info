package Vue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Fenetre extends JFrame{
	
	private Container m_CP_main;
	
	private BoxLayout m_BL_main;
	private BoxLayout m_BL_titre;
	private BoxLayout m_BL_commande;
	private BoxLayout m_BL_contenu;
	private BoxLayout m_BL_taille;
	private BoxLayout m_BL_generer;
	
	private JPanel m_P_image;
	private JPanel m_P_titre;
	private JPanel m_P_commande;
	private JPanel m_P_contenu;
	private JPanel m_P_saisie;
	private JPanel m_P_taille;
	private JPanel m_P_generer;
	private JPanel m_P_RBs;
	
	private JLabel m_L_titre;
	private JLabel m_L_taille;
	
	private JButton m_B_generer;
	
	private JComboBox m_CmB_taille;
	
	private JRadioButton m_RB_url;
	private JRadioButton m_RB_txt;
	private JRadioButton m_RB_tel;
	private JRadioButton m_RB_sms;
	
	private ButtonGroup m_BG_typesDonnees;
	
	static String string_RB_url = "URL";
    static String string_RB_txt = "Texte";
    static String string_RB_tel = "Numéro de Tel";
    static String string_RB_sms = "SMS";

	public Fenetre(String title) {
		
		super(title);	// Super constructeur
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	// Ferme la fenêtre lors d'un clic sur la croix

		m_CP_main = this.getContentPane();	// Récupère le panel de contenu de la fenêtre
		m_BL_main = new BoxLayout(m_CP_main, BoxLayout.X_AXIS);	// Crée un nouveau boxLayout orienté horizontalement
		
		m_CP_main.setLayout(m_BL_main);	// Définit le boxlayout comme étant notre gestionnaire de contenu principal
		
		m_P_image = new JPanel();	// Crée un nouveau JPanel pour y stocker le résultat du QRcode
		m_P_image.setMinimumSize(new Dimension(300,300));
		m_P_image.setPreferredSize(new Dimension(300,300));
		m_P_image.setMaximumSize(new Dimension(300,Short.MAX_VALUE));	// Largeur maximum fixée à 300, hauteur maximum infinie
		//m_P_image.setBackground(new Color(100,75,255));	// DEBUG Couleur bleue
		
		m_P_commande = new JPanel();	// Crée un nouveau JPanel pour y stocker les boutons et commandes
		m_P_commande.setMinimumSize(new Dimension(450,300));
		m_P_commande.setPreferredSize(new Dimension(450,300));
		m_P_commande.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));	// Hauteur et largeur maximum infinie
		//m_P_commande.setBackground(new Color(255,75,100));	// DEBUG Couleur rouge
		
		m_BL_commande = new BoxLayout(m_P_commande, BoxLayout.Y_AXIS);	// Crée un nouveau boxLayout orienté verticalement
		m_P_commande.setLayout(m_BL_commande);	// Attribue le BoxLayout comme étant le gestionnaire de contenu du JPanel
		
		m_L_titre = new JLabel("Générateur de QRcode");
		
		m_P_titre = new JPanel();
		m_BL_titre = new BoxLayout(m_P_titre, BoxLayout.X_AXIS);
		m_P_titre.setLayout(m_BL_titre);
		m_P_titre.add(m_L_titre);
		m_P_titre.setMaximumSize(new Dimension(Short.MAX_VALUE,m_P_titre.getHeight()));	// Largeur infinie, hauteur du label
		
		// Création des boutons-radio
        m_RB_url = new JRadioButton(string_RB_url);
        m_RB_url.setActionCommand(string_RB_url);
        m_RB_url.setSelected(true);
 
        m_RB_txt = new JRadioButton(string_RB_txt);
        m_RB_txt.setActionCommand(string_RB_txt);
 
        m_RB_tel = new JRadioButton(string_RB_tel);
        m_RB_tel.setActionCommand(string_RB_tel);
 
        m_RB_sms = new JRadioButton(string_RB_sms);
        m_RB_sms.setActionCommand(string_RB_sms);
 
        // Ajout des boutons dans un même groupe
        m_BG_typesDonnees = new ButtonGroup();
        m_BG_typesDonnees.add(m_RB_url);
        m_BG_typesDonnees.add(m_RB_txt);
        m_BG_typesDonnees.add(m_RB_tel);
        m_BG_typesDonnees.add(m_RB_sms);
        
        // Création et intégration des boutons radio dans un panel sur une seule ligne
        m_P_RBs = new JPanel(new GridLayout(1, 0));
        m_P_RBs.add(m_RB_url);
        m_P_RBs.add(m_RB_txt);
        m_P_RBs.add(m_RB_tel);
        m_P_RBs.add(m_RB_sms);
		
        // Création du panel de contenu
		m_P_contenu = new JPanel();
		m_BL_contenu = new BoxLayout(m_P_contenu, BoxLayout.X_AXIS);
		m_P_contenu.setLayout(m_BL_contenu);
		m_P_contenu.add(m_P_RBs);	// Ajout des boutons-radio dans le panel de contenu
		m_P_contenu.setMaximumSize(new Dimension(Short.MAX_VALUE,m_P_contenu.getHeight()));
		m_P_contenu.setBorder(BorderFactory.createTitledBorder("Contenu"));	// Définit la bordure et le titre
		
		// Création du panel de saisie
		m_P_saisie = new JPanel();
		m_P_saisie.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));
		m_P_saisie.setBorder(BorderFactory.createTitledBorder("Saisie :"));
		
		m_P_taille = new JPanel();
		m_P_taille.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));
		
		m_BL_taille = new BoxLayout(m_P_taille, BoxLayout.X_AXIS);
		m_P_taille.setLayout(m_BL_taille);
		
		m_L_taille = new JLabel("Taille:");
		String comboBoxItems[] = { "S","M", "L", "XL" };
		m_CmB_taille = new JComboBox(comboBoxItems);
		m_CmB_taille.setMaximumSize(new Dimension(50,20));
		
		m_P_taille.add(m_L_taille);
		m_P_taille.add(m_CmB_taille);
		m_P_taille.setMaximumSize(new Dimension(Short.MAX_VALUE,m_P_taille.getHeight()));	// Largeur infinie, hauteur du label
		
		m_B_generer = new JButton("Générer");
		
		m_P_generer = new JPanel();
		m_BL_generer = new BoxLayout(m_P_generer, BoxLayout.X_AXIS);
		m_P_generer.setLayout(m_BL_generer);
		m_P_generer.add(m_B_generer);
		m_P_generer.setMaximumSize(new Dimension(Short.MAX_VALUE,m_P_generer.getHeight()));	// Largeur infinie, hauteur du label
		
		m_P_commande.add(m_P_titre);
		m_P_commande.add(m_P_contenu);
		m_P_commande.add(m_P_saisie);
		m_P_commande.add(m_P_taille);
		m_P_commande.add(m_P_generer);
		
		m_CP_main.add(m_P_image);
		m_CP_main.add(m_P_commande);

		this.pack();	// Demande d'attribuer une taille minimale à la fenêtre tout en gardant visible tous les composants
		this.setMinimumSize(new Dimension(this.getWidth(),this.getHeight()));	// Définit la taille actuelle comme étant la taille minimale
		this.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
		this.setLocationRelativeTo(null);	// Centre la fenêtre
		this.setVisible(true);	// Affiche la fenêtre
	}
	
	// Fonction appelée lorsqu'un évènement est déclenché sur la fenêtre
	public void actionPerformed(ActionEvent e) {
		
    }
	
}
