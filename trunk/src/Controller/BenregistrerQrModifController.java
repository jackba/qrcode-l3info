package Controller;

import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;

import Vue.Fenetre;
import Vue.ImageFormat;

public class BenregistrerQrModifController extends BenregistrerController{

	public BenregistrerQrModifController(Fenetre f) {
		super(f);
		// TODO Auto-generated constructor stub
	}

	public void actionPerformed(ActionEvent event)
	{
		// Affichage de la fenêtre modale de sauvegarde.
		int bouton = getM_fileChooser().showSaveDialog(getFenetre());

		// L'utilisateur a cliqué sur enregistrer
		if (bouton == JFileChooser.APPROVE_OPTION)
		{
			// Chemin du fichier sélectionné par l'utilisateur
			String pathFile = getM_fileChooser().getSelectedFile().getAbsolutePath();
			
			// Récupération du format
			ImageFormat format = getFormat(pathFile);
			
			getFenetre().getQrModifiable().saveAsImage(format, pathFile);
		}
	}
}
