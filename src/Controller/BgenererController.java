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
		String encodedData = m_stringGenerator.getBinaryString("HELLO WORLD", 1, 1, "Q");
		m_qrCode = new QRcode(1,"Q",encodedData);
		m_qrCode.fillQRmatrix();
		getFenetre().getQrPanel().drawImageFromMatrix(m_qrCode.getQRmatrix());
	}

}
