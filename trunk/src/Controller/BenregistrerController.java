package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Vue.Fenetre;
import Vue.ImageFormat;

public class BenregistrerController extends AbstractController implements ActionListener {

	private JFileChooser m_fileChooser;
	private FileFilter m_fileFilter;

	public BenregistrerController(Fenetre f) {
		super(f);
		m_fileFilter = new FileNameExtensionFilter("Images *bmp, *gif, *jpg, *png", "bmp", "gif", "jpg", "jpeg", "png");
		m_fileChooser = new JFileChooser();
		m_fileChooser.setFileFilter(m_fileFilter);
	}

	public boolean isValid() {
		return true;
	}

	// OnClick
	public void actionPerformed(ActionEvent event)
	{
		// Affichage de la fenêtre modale de sauvegarde.
		int bouton = m_fileChooser.showSaveDialog(getFenetre());

		// L'utilisateur a cliqué sur enregistrer
		if (bouton == JFileChooser.APPROVE_OPTION)
		{
			// Chemin du fichier sélectionné par l'utilisateur
			String pathFile = m_fileChooser.getSelectedFile().getAbsolutePath();
			
			// Récupération du format
			ImageFormat format = getFormat(pathFile);
			
			getFenetre().getQrPanel().saveAsImage(format, pathFile);
		}
	}

	public ImageFormat getFormat(String pathFile)
	{
		ImageFormat format;
		int index = pathFile.lastIndexOf(".");
		
		String extension = "";
		if (index > -1 && index <pathFile.length())
			extension = pathFile.substring(index+1);
		
		if (extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("jpg"))
		{
			format = ImageFormat.JPEG;
		}
		else if (extension.equalsIgnoreCase("bmp") || extension.equalsIgnoreCase("bitmap"))
		{
			format = ImageFormat.BMP;
		}
		else if (extension.equalsIgnoreCase("gif"))
		{
			format = ImageFormat.GIF;
		}
		else if (extension.equalsIgnoreCase("png"))
		{
			format = ImageFormat.PNG;
		}
		else
		{
			format = ImageFormat.PNG;
		}
		
		return format;
	}
	
	public JFileChooser getFileChooser()
	{
		return m_fileChooser;
	}
}