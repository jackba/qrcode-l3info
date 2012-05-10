package Controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.SwingUtilities;

import Donnees.NumberOfSymbolCharacterParser;
import Model.BinaryStringGenerator;
import Model.PenaltyRules;
import Model.QRcode;
import Vue.Fenetre;

public class BgenererController extends AbstractController implements ActionListener {

	private PenaltyRules m_penaltyRules;
	private BinaryStringGenerator m_stringGenerator;
	private QRcode m_qrCode;
	private TFurlController m_TFurlController;
	private TAtxtController m_TAtxtController;
	private TFtelController m_TFtelController;
	private TFsmsTelController m_TFsmsTelController;
	private TAsmsMsgController m_TAsmsMsgController;
	private TFimgPathController m_TFimgPathController;

	public BgenererController(Fenetre f,
			TFurlController urlController,
			TAtxtController txtController,
			TFtelController telController,
			TFsmsTelController smsTelController,
			TAsmsMsgController smsMsgController,
			TFimgPathController imgPathController)
	{
		super(f);
		m_TFurlController = urlController;
		m_TAtxtController = txtController;
		m_TFtelController = telController;
		m_TFsmsTelController = smsTelController;
		m_TAsmsMsgController = smsMsgController;
		m_TFimgPathController = imgPathController;
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
		else if (getFenetre().getRB_image().isSelected())
		{
			if (m_TFimgPathController.isValid())
			{
				message = m_TFimgPathController.getMessage();
				mode = m_TFimgPathController.getMode();
			}
		}
		else
		{
			
		}

		if (message != null && !message.equals("") && level != null && !level.equals(""))
		{
			int version = -1;
			try
			{
				version = Integer.parseInt(getFenetre().getCmB_taille().getSelectedItem().toString());
			}
			catch (Exception e)
			{
				int length;
				if (mode == CharacterMode.IMAGE)
					length = (int) (new File(message)).length();
				else
					length = message.length();
				
				version = NumberOfSymbolCharacterParser.getInstance().getFirstAdaptedVersion(mode, length, level);
				//version = NumberOfSymbolCharacterParser.getInstance().getFirstAdaptedVersion(mode, message.length(), level);
			}

			// Le message, le mode, la version et le niveau sont corrects, on peut passer à la génération
			if (version > -1)
			{
				int qrMode;

				switch(mode)
				{
				case NUMERIC: qrMode=0; break;
				case ALPHANUMERIC: qrMode=1; break;
				case BYTES: qrMode=2; break;
				case KANJI: qrMode=3; break;
				case ECI: qrMode=4; break;
				case IMAGE: qrMode=5; break;
				default:
					qrMode = 2;
				}

				String encodedData = m_stringGenerator.getBinaryString(message, qrMode, version, level);
				m_qrCode = new QRcode(version,level,encodedData);
				m_qrCode.fillQRmatrix();

				m_penaltyRules = new PenaltyRules(m_qrCode);
				getFenetre().getQrPanel().drawImageFromMatrix(m_qrCode.getMaskedMatrix(m_penaltyRules.getMask()),1);

				// Redimensionne la fenêtre sans tout faire bugguer
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						getFenetre().getB_enregistrer().setVisible(true);
						getFenetre().getQrPanel().updateUI();
						if (getFenetre().getQrPanel().getImageSize() + 70 < 325)
							getFenetre().setPreferredSize(new Dimension(485 + getFenetre().getQrPanel().getImageSize()+5, 325));
						else
							getFenetre().setPreferredSize(new Dimension(485 + getFenetre().getQrPanel().getImageSize()+5, getFenetre().getQrPanel().getImageSize() + 70));
						getFenetre().setMinimumSize(getFenetre().getPreferredSize());
						getFenetre().setSize(getFenetre().getPreferredSize());
					}
				});
			}
		}
	}

}
