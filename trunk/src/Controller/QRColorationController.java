package Controller;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import Model.QRcode;
import Vue.Fenetre;
import Vue.QRcodeComponent;

public class QRColorationController extends AbstractController implements MouseListener {
	
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
		int line = (int)(e.getY()-17)/4;
		int column = (int)(e.getX()-17)/4;
		if(e.getY()<m_qrCodeComponent.getSize().getWidth()-15 && e.getX()<m_qrCodeComponent.getSize().getHeight()-15 && e.getX()>17 && e.getY()>17)
		m_qrCode.modifierPixel(line,column);
		//m_qrCodeComponent.drawPoint(line, column, 1);
		m_qrCodeComponent.drawImageFromMatrix(m_qrCode.getQRmatrix(), 1);
	}

	public int getVersion()
	{
		return m_qrcodeVersion;
	}
	
	public void setVersion(int version)
	{
		m_qrcodeVersion = version;
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	public void drawNewQrVersion()
	{
		m_qrCode = new QRcode(m_qrcodeVersion);
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				m_qrCodeComponent.drawImageFromMatrix(m_qrCode.getQRmatrix(), 1);
				m_qrCodeComponent.updateUI();
				
				if (m_qrCodeComponent.getImageSize() + 285 > getFenetre().getBaseHeight())	// La hauteur du composant + la hauteur des autres composants est supérieure à la hauteur de base
				{
					getFenetre().setMinimumSize(new Dimension(getFenetre().getWidth(), m_qrCodeComponent.getImageSize() + 285));
				}
				int largeurQRpanel = 200;
				if (getFenetre().getQrPanel().getImageSize() > 200) largeurQRpanel = getFenetre().getQrPanel().getImageSize();
					
				if (m_qrCodeComponent.getImageSize() + largeurQRpanel + 30 > getFenetre().getBaseWidth())	// La largeur du composant + la largeur des autres composants est supérieure à la largeur de base
				{
					getFenetre().setMinimumSize(new Dimension(m_qrCodeComponent.getImageSize() + largeurQRpanel + 30, getFenetre().getHeight()));
				}
			}
		});
	}
}
