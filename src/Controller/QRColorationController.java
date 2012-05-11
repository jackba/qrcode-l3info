package Controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Model.QRcode;
import Vue.Fenetre;
import Vue.QRcodeComponent;

public class QRColorationController extends AbstractController implements MouseListener{
	
	private QRcode m_qrCode;
	private QRcodeComponent m_qrCodeComponent;
	
	public QRColorationController(Fenetre f,QRcodeComponent qrCodeComponent) {
		super(f);
		this.m_qrCodeComponent = qrCodeComponent;
		m_qrCode = new QRcode(1);
		m_qrCodeComponent.drawImageFromMatrix(m_qrCode.getQRmatrix(), 4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		m_qrCode.modifierPixel(e.getX()/38,e.getY()/38);
		m_qrCodeComponent.drawImageFromMatrix(m_qrCode.getQRmatrix(), 4);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
