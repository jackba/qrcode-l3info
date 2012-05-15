package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import Model.QRcode;
import Vue.Fenetre;
import Vue.QRcodeComponent;

public class QRColorationController extends AbstractController implements MouseListener, ActionListener{
	
	private QRcode m_qrCode;
	private QRcodeComponent m_qrCodeComponent;
	private int m_qrcodeVersion;
	
	public QRColorationController(Fenetre f,QRcodeComponent qrCodeComponent)
	{
		super(f);
		this.m_qrCodeComponent = qrCodeComponent;
		m_qrcodeVersion = 1;
		m_qrCode = new QRcode(m_qrcodeVersion);
		m_qrCodeComponent.drawImageFromMatrix(m_qrCode.getQRmatrix(), 1);
	}

	public boolean isValid() {
		return true;
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getY()<m_qrCodeComponent.getSize().getWidth()-15 && e.getX()<m_qrCodeComponent.getSize().getHeight()-15 && e.getX()>17 && e.getY()>17)
		m_qrCode.modifierPixel((int)(e.getY()-17)/4,(int)(e.getX()-17)/4);
		m_qrCodeComponent.drawImageFromMatrix(m_qrCode.getQRmatrix(), 1);
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	public void actionPerformed(ActionEvent e)
	{
		if(getFenetre().getCmB_taille().getSelectedItem().toString()=="Auto")
		{
			m_qrcodeVersion = 1;
		}
		else
		{
			m_qrcodeVersion = Integer.parseInt(getFenetre().getCmB_taille().getSelectedItem().toString());
		}
		m_qrCode = new QRcode(m_qrcodeVersion);
		m_qrCodeComponent.drawImageFromMatrix(m_qrCode.getQRmatrix(), 1);
	}	
}
