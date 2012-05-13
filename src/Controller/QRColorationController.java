package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Model.QRcode;
import Vue.Fenetre;
import Vue.QRcodeComponent;

public class QRColorationController extends AbstractController implements MouseListener, ActionListener{
	
	private QRcode m_qrCode;
	private QRcodeComponent m_qrCodeComponent;
	private int m_qrcodeVersion;
	
	public QRColorationController(Fenetre f,QRcodeComponent qrCodeComponent) {
		super(f);
		this.m_qrCodeComponent = qrCodeComponent;
		m_qrcodeVersion = 1;
		m_qrCode = new QRcode(40);
		m_qrCodeComponent.drawImageFromMatrix(m_qrCode.getQRmatrix(), 1);
		m_qrCode=new QRcode(1);
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
		if(e.getY()<m_qrCodeComponent.getSize().getWidth()-15 && e.getX()<m_qrCodeComponent.getSize().getHeight()-15 && e.getX()>17 && e.getY()>17)
		m_qrCode.modifierPixel((int)(e.getY()-17)/4,(int)(e.getX()-17)/4);
		m_qrCodeComponent.drawImageFromMatrix(m_qrCode.getQRmatrix(), 1);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(getFenetre().getCmB_taille().getSelectedItem().toString()=="Auto"){
			m_qrcodeVersion = 1;
		}else{
			m_qrcodeVersion = Integer.parseInt(getFenetre().getCmB_taille().getSelectedItem().toString());
		}
		m_qrCode = new QRcode(m_qrcodeVersion);
		m_qrCodeComponent.drawImageFromMatrix(m_qrCode.getQRmatrix(), 1);
	}	
}
