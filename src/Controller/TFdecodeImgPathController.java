package Controller;

import java.io.File;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Vue.Fenetre;

public class TFdecodeImgPathController extends AbstractTextController implements DocumentListener{

	private File m_file;
	
	public TFdecodeImgPathController(Fenetre f) {
		super(f, 0, null);
	}
	
	public boolean isValid() {
		m_file = new File(getFenetre().getTF_decodeImgPath().getText());
		return m_file.exists() && m_file.isFile();
	}

	public void switchEnableDisableBdecoder()
	{
		if (getFenetre().getRB_decode().isSelected())
			if (isValid()) getFenetre().getB_decoder().setEnabled(true);
			else getFenetre().getB_decoder().setEnabled(false);
	}
	
	public void onTextChanged(DocumentEvent event)
	{
		switchEnableDisableBdecoder();
	}

	public void changedUpdate(DocumentEvent event) {onTextChanged(event);}
	public void insertUpdate(DocumentEvent event) {onTextChanged(event);}
	public void removeUpdate(DocumentEvent event) {onTextChanged(event);}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
