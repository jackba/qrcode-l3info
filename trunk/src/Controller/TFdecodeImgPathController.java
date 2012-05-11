package Controller;

import java.io.File;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Model.ImageParser;
import Vue.Fenetre;

public class TFdecodeImgPathController extends AbstractTextController implements DocumentListener{

	private File m_file;

	public TFdecodeImgPathController(Fenetre f) {
		super(f, 0, null);
	}

	public boolean isValid() {
		m_file = new File(getFenetre().getTF_decodeImgPath().getText());
		return m_file.exists() && m_file.isFile() && ImageParser.isSupportedImageFormat(m_file);
	}

	public void switchEnableDisableBdecoder()
	{
		if (getFenetre().getRB_decode().isSelected())
			if (isValid()) getFenetre().getB_decoder().setEnabled(true);
			else getFenetre().getB_decoder().setEnabled(false);
	}

	public void onTextChanged(DocumentEvent event)
	{
		if (getFenetre().getRB_decode().isSelected())
		{
			m_file = new File(getFenetre().getTF_decodeImgPath().getText());
			if (!m_file.exists() || !m_file.isFile())
			{
				getFenetre().getB_decoder().setEnabled(false);
				getFenetre().getL_decodeNotif().setText("Aucun fichier sélectionné ou le fichier n'existe pas.");
			}
			else if (!ImageParser.isSupportedImageFormat(m_file))
			{
				getFenetre().getB_decoder().setEnabled(false);
				getFenetre().getL_decodeNotif().setText("Le format n'est pas supporté (formats supportés: jpg, png, gif, bmp).");
			}
			else
			{
				getFenetre().getB_decoder().setEnabled(true);
				getFenetre().getL_decodeNotif().setText("Fichier valide.");
			}
		}
	}

	public void changedUpdate(DocumentEvent event) {onTextChanged(event);}
	public void insertUpdate(DocumentEvent event) {onTextChanged(event);}
	public void removeUpdate(DocumentEvent event) {onTextChanged(event);}

	public String getMessage() {
		return getFenetre().getTF_decodeImgPath().getText();
	}

}
