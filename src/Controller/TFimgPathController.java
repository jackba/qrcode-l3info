package Controller;

import java.io.File;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Model.ImageParser;
import Vue.Fenetre;

public class TFimgPathController extends AbstractTextController implements DocumentListener{

	private File m_file;

	public TFimgPathController(Fenetre f) {
		super(f, 1024, CharacterMode.IMAGE);
	}

	// Retourne vrai si le texte correspond à un fichier existant
	public boolean isValid() {
		m_file = new File(getFenetre().getTF_imgPath().getText());
		return m_file.exists() && m_file.isFile() &&
		ImageParser.isUnderMaximum(m_file,2953) &&
		ImageParser.isSupportedImageFormat(m_file);
	}

	public void switchEnableDisableBgenerer()
	{
		if (getFenetre().getRB_image().isSelected())
			if (isValid()) getFenetre().getB_generer().setEnabled(true);
			else getFenetre().getB_generer().setEnabled(false);
	}

	public void onTextChanged(DocumentEvent event)
	{
		if (getFenetre().getRB_image().isSelected())
		{
			m_file = new File(getFenetre().getTF_imgPath().getText());
			if (!m_file.exists() || !m_file.isFile())
			{
				getFenetre().getB_generer().setEnabled(false);
				getFenetre().getL_imgNotif().setText("Aucun fichier sélectionné ou le fichier n'existe pas.");
			}
			else if (!ImageParser.isUnderMaximum(m_file,2953))
			{
				getFenetre().getB_generer().setEnabled(false);
				getFenetre().getL_imgNotif().setText("Le fichier est trop volumineux (taille maximum: 2953 octets ou 2,88 ko).");
			}
			else if (!ImageParser.isSupportedImageFormat(m_file))
			{
				getFenetre().getB_generer().setEnabled(false);
				getFenetre().getL_imgNotif().setText("Le format n'est pas supporté (formats supportés: jpg, png, gif, bmp).");
			}
			else
			{
				getFenetre().getB_generer().setEnabled(true);
				getFenetre().getL_imgNotif().setText("Fichier valide.");
			}
		}
	}

	public void changedUpdate(DocumentEvent event) {onTextChanged(event);}
	public void insertUpdate(DocumentEvent event) {onTextChanged(event);}
	public void removeUpdate(DocumentEvent event) {onTextChanged(event);}

	public String getMessage() {
		return getFenetre().getTF_imgPath().getText();
	}

}
