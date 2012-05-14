package Controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Model.ImageParser;
import Vue.Fenetre;

public class BresultSaveController extends BenregistrerController {

	private FileFilter m_txtFileFilter;

	public BresultSaveController(Fenetre f) {
		super(f);
		m_txtFileFilter = new FileNameExtensionFilter("Texte *.txt","txt");
	}

	public boolean isValid()
	{
		return true;
	}

	public void actionPerformed(ActionEvent event)
	{
		String pathFile;
		boolean isText;

		// Il s'agit d'un texte
		if (getFenetre().isResultTextBoxVisible())
		{
			isText = true;
			getFileChooser().setFileFilter(m_txtFileFilter);
		}
		// Il s'agit d'une image
		else
		{
			isText = false;
			getFileChooser().setFileFilter(getImageFileFilter());
		}

		int bouton = getFileChooser().showSaveDialog(getFenetre());
		// L'utilisateur a appuyé sur "enregistrer"
		if (bouton == JFileChooser.APPROVE_OPTION)
		{
			// Récupération du chemin choisi
			pathFile = getFileChooser().getSelectedFile().getAbsolutePath();

			// Il s'agit d'un texte
			if (isText)
			{
				try {
					fileFromString(getFenetre().getTA_result().getText(), pathFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// Il s'agit d'une image
			else
			{
				try {
					ImageParser.saveImage(getFenetre().getImageComponent().getImage(), pathFile, getFenetre().getImageComponent().getFormat());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void fileFromString(String donnees, String pathFile) throws IOException
	{
		File fichier = new File(pathFile);
		FileOutputStream fOutStream = new FileOutputStream(fichier);
		Writer out = new OutputStreamWriter(fOutStream, Charset.forName("UTF8"));
		try {
			out.write(donnees);
		} finally {
			out.close();
		}
	}

}
