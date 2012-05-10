package Controller;

import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import Vue.Fenetre;

public class BchargerController extends BenregistrerController{

	private JTextField m_champs;
	
	public BchargerController(Fenetre f, JTextField champsTexte)
	{
		super(f);
		m_champs = champsTexte;
	}
	
	public void actionPerformed(ActionEvent event)
	{
		// Affichage de la fenêtre modale de chargement.
		int bouton = getFileChooser().showOpenDialog(getFenetre());

		// L'utilisateur a cliqué sur charger
		if (bouton == JFileChooser.APPROVE_OPTION)
		{
			// Affichage du chemin du fichier sélectionné par l'utilisateur	dans le champs de texte	
			m_champs.setText(getFileChooser().getSelectedFile().getAbsolutePath());
		}
	}
	
}
