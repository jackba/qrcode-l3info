package Controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import Model.BinaryStringGenerator;
import Model.QRcode;
import Vue.Fenetre;

public class BgenererController extends AbstractController implements ActionListener {

	private BinaryStringGenerator m_stringGenerator;
	private QRcode m_qrCode;
	
	public BgenererController(Fenetre f) {
		super(f);
		m_stringGenerator = new BinaryStringGenerator();
	}
	
	public boolean isValid() {
		return false;
	}
	
	// OnClick
	public void actionPerformed(ActionEvent event)
	{
		String level = "";
		if (getFenetre().getRB_correctionL().isSelected()) level = "L";
		else if (getFenetre().getRB_correctionM().isSelected()) level = "M";
		else if (getFenetre().getRB_correctionQ().isSelected()) level = "Q";
		else if (getFenetre().getRB_correctionH().isSelected()) level = "H";
		
		int version = Integer.parseInt(getFenetre().getCmB_taille().getSelectedItem().toString());
		
		String encodedData = m_stringGenerator.getBinaryString("A test.", 2, version, level);
		m_qrCode = new QRcode(version,level,encodedData);
		m_qrCode.fillQRmatrix();
		//getFenetre().getQrPanel().drawImageFromMatrix(m_qrCode.getQRmatrix());
		getFenetre().getQrPanel().drawImageFromMatrix(m_qrCode.getMaskedMatrix(0),1);
		
		// Redimensionne la fenêtre sans tout faire bugguer
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				getFenetre().getQrPanel().updateUI();
				getFenetre().pack();
				getFenetre().setPreferredSize(new Dimension(415 + getFenetre().getQrPanel().getImageSize()+5, getFenetre().getQrPanel().getImageSize() + 70));
				getFenetre().setSize(getFenetre().getPreferredSize());
			}
		});
	}

}
