package Controller;

import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;

import Vue.Fenetre;

public class BchargerController extends BenregistrerController{

	public BchargerController(Fenetre f)
	{
		super(f);
	}
	
	public void actionPerformed(ActionEvent event)
	{
		// Affichage de la fenêtre modale de chargement.
		int bouton = getFileChooser().showOpenDialog(getFenetre());

		// L'utilisateur a cliqué sur charger
		if (bouton == JFileChooser.APPROVE_OPTION)
		{
			// Affichage du chemin du fichier sélectionné par l'utilisateur	dans le champs de texte	
			getFenetre().getTF_imgPath().setText(getFileChooser().getSelectedFile().getAbsolutePath());
		}
	}
	
}
