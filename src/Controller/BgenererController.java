package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		String encodedData = m_stringGenerator.getBinaryString("Alors Guild Wars 2 c'est bien ?", 2, 3, "Q");
		m_qrCode = new QRcode(3,"Q",encodedData);
		m_qrCode.fillQRmatrix();
		//getFenetre().getQrPanel().drawImageFromMatrix(m_qrCode.getQRmatrix());
		getFenetre().getQrPanel().drawImageFromMatrix(m_qrCode.getMaskedMatrix(0));
	}

}
