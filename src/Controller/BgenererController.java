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
	private TFurlController m_TFurlController;
	private TAtxtController m_TAtxtController;
	private TFtelController m_TFtelController;
	private TFsmsTelController m_TFsmsTelController;
	private TAsmsMsgController m_TAsmsMsgController;

	public BgenererController(Fenetre f,
			TFurlController urlController,
			TAtxtController txtController,
			TFtelController telController,
			TFsmsTelController smsTelController,
			TAsmsMsgController smsMsgController)
	{
		super(f);
		m_TFurlController = urlController;
		m_TAtxtController = txtController;
		m_TFtelController = telController;
		m_TFsmsTelController = smsTelController;
		m_TAsmsMsgController = smsMsgController;
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

		int version = -1;
		try
		{
			version = Integer.parseInt(getFenetre().getCmB_taille().getSelectedItem().toString());
		}
		catch (Exception e)
		{
			version = 40;
		}
		finally
		{
			if (version > -1)
			{
				
				String message = null;
				CharacterMode mode = null;
				// Vérification de la validité des données
				// récupération du message
				// récupération du mode
				if (getFenetre().getRB_url().isSelected())
				{
					if (m_TFurlController.isValid())
					{
						message = m_TFurlController.getMessage();
						mode = m_TFurlController.getMode();
					}
				}
				else if (getFenetre().getRB_txt().isSelected())
				{
					if (m_TAtxtController.isValid())
					{
						message = m_TAtxtController.getMessage();
						mode = m_TAtxtController.getMode();
					}
				}
				else if (getFenetre().getRB_tel().isSelected())
				{
					if (m_TFtelController.isValid())
					{
						message = m_TFtelController.getMessage();
						mode = m_TFtelController.getMode();
					}
				}
				else if (getFenetre().getRB_sms().isSelected())
				{
					if (m_TFsmsTelController.isValid())
					{
						message = m_TFsmsTelController.getMessage();
						if (m_TAsmsMsgController.isValid() && message != null)
						{
							message += m_TAsmsMsgController.getMessage();
							mode = m_TFsmsTelController.getMode();
						}
					}
				}
				else
				{
					
				}
				
				// Le message, le mode, la version et le niveau sont corrects, on peut passer à la génération
				if (message != null && mode != null && version > -1 && level != null && !level.equals(""))
				{
					int qrMode;
					
					switch(mode)
					{
					case NUMERIC: qrMode=0; break;
					case ALPHANUMERIC: qrMode=1; break;
					case BYTES: qrMode=2; break;
					case KANJI: qrMode=3; break;
					default:
						qrMode = 2;
					}
					
					String encodedData = m_stringGenerator.getBinaryString(message, qrMode, version, level);
					m_qrCode = new QRcode(version,level,encodedData);
					m_qrCode.fillQRmatrix();
					
					// TODO afficher la bonne matrice avec le masque ayant le moins de pénalités
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
		}
	}

}
