package Controller;

import java.io.File;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Vue.Fenetre;

public class TFimgPathController extends AbstractTextController implements DocumentListener{
	
	private File m_file;
	
	public TFimgPathController(Fenetre f) {
		super(f, 1024, CharacterMode.IMAGE);
	}

	// Retourne vrai si le texte correspond à un fichier existant
	public boolean isValid() {
		m_file = new File(getFenetre().getTF_imgPath().getText());
		return m_file.exists() && m_file.isFile();
	}
	
	public void switchEnableDisableBgenerer()
	{
		if (getFenetre().getRB_image().isSelected())
			if (isValid()) getFenetre().getB_generer().setEnabled(true);
			else getFenetre().getB_generer().setEnabled(false);
	}
	
	public void onTextChanged(DocumentEvent event)
	{
		switchEnableDisableBgenerer();
	}

	public void changedUpdate(DocumentEvent event) {onTextChanged(event);}
	public void insertUpdate(DocumentEvent event) {onTextChanged(event);}
	public void removeUpdate(DocumentEvent event) {onTextChanged(event);}
	
	public String getMessage() {
		return getFenetre().getTF_imgPath().getText();
	}
	
}
