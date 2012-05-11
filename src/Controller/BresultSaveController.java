package Controller;

import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Vue.Fenetre;
import Vue.ImageFormat;

public class BresultSaveController extends BenregistrerController {
	
	private FileFilter m_txtFileFilter;
	
	public BresultSaveController(Fenetre f) {
		super(f);
		m_txtFileFilter = new FileNameExtensionFilter("Texte *.txt");
	}
	
	public boolean isValid()
	{
		return true;
	}
	
	public void actionPerformed(ActionEvent event)
	{
		String pathFile;
		// Il s'agit d'un texte
		if (getFenetre().isResultTextBoxVisible())
		{
			getFileChooser().setFileFilter(m_txtFileFilter);
		}
		// Il s'agit d'une image
		else
		{
			getFileChooser().setFileFilter(getImageFileFilter());
		}
		int bouton = getFileChooser().showSaveDialog(getFenetre());
		if (bouton == JFileChooser.APPROVE_OPTION)
			pathFile = getFileChooser().getSelectedFile().getAbsolutePath();
	}

}
