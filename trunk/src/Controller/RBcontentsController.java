package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import Vue.Fenetre;

public class RBcontentsController extends AbstractController implements ActionListener{

	private TFurlController m_TFurlController;
	private TAtxtController m_TAtxtController;
	private TFtelController m_TFtelController;
	private TFsmsTelController m_TFsmsTelController;
	private TAsmsMsgController m_TAsmsMsgController;
	private QRColorationController m_QRColorationController;
	private int m_previousSelectedIndex;	// L'item de la combo-box sélectionné dans le mode précédent
	private boolean m_needRefreshSelectedIndex;
	private JRadioButton m_previousSelectedLevel;
	private boolean m_needRefreshSelectedLevel;
	
	public RBcontentsController(Fenetre f,
			TFurlController urlController,
			TAtxtController txtController,
			TFtelController telController,
			TFsmsTelController smsTelController,
			TAsmsMsgController smsMsgController,
			QRColorationController colorationController)
	{
		super(f);
		m_TFurlController = urlController;
		m_TAtxtController = txtController;
		m_TFtelController = telController;
		m_TFsmsTelController = smsTelController;
		m_TAsmsMsgController = smsMsgController;
		m_QRColorationController = colorationController;
		m_needRefreshSelectedIndex = false;
		m_needRefreshSelectedLevel = false;
	}
	
	public boolean isValid() {
		return true;
	}
	
	public void actionPerformed(ActionEvent event)
	{
		getFenetre().hideShownBoxes();
		
		JRadioButton rb = (JRadioButton)event.getSource();
		if (rb.equals(getFenetre().getRB_url()) && rb.isSelected())
		{
			refreshCmB_taille();
			refreshRB_level();
			getFenetre().showUrlBox();
			m_TFurlController.switchEnableDisableBgenerer();
			getFenetre().getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(m_TFurlController.getMode()));
		}
		else if (rb.equals(getFenetre().getRB_txt()) && rb.isSelected())
		{
			refreshCmB_taille();
			refreshRB_level();
			getFenetre().showTxtBox();
			m_TAtxtController.switchEnableDisableBgenerer();
			getFenetre().getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(m_TAtxtController.getMode()));
		}
		else if (rb.equals(getFenetre().getRB_tel()) && rb.isSelected())
		{
			refreshCmB_taille();
			refreshRB_level();
			getFenetre().showTelBox();
			m_TFtelController.switchEnableDisableBgenerer();
			getFenetre().getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(m_TFtelController.getMode()));
		}
		else if (rb.equals(getFenetre().getRB_sms()) && rb.isSelected())
		{
			refreshCmB_taille();
			refreshRB_level();
			getFenetre().showSmsBox();
			m_TFsmsTelController.switchEnableDisableBgenerer();
			m_TAsmsMsgController.switchEnableDisableBgenerer();
			getFenetre().getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(m_TAsmsMsgController.getMode()));
		}
		else if (rb.equals(getFenetre().getRB_image()) && rb.isSelected())
		{
			lockOnAutoCmB_taille();
			lockOnErrorL();
			getFenetre().showImgBox();
			getFenetre().getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(CharacterMode.BYTES));
		}
		else if (rb.equals(getFenetre().getRB_coloriage()) && rb.isSelected())
		{
			lockOnErrorL();
			refreshCmB_taille();
			getFenetre().showPaintBox();
			getFenetre().getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(CharacterMode.BYTES));
			stockPreviousVersion();
			getFenetre().getCmB_taille().setSelectedIndex(m_QRColorationController.getVersion());
		}
		else if (rb.equals(getFenetre().getRB_decode()) && rb.isSelected())
		{
			lockOnAutoCmB_taille();
			lockOnErrorL();
			getFenetre().showDecodeBox();
			getFenetre().getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(null));
			getFenetre().getB_generer().setEnabled(false);
		}
	}
	
	// Rétablit l'item précédement sélectionné et la possibilité de sélectionner dans la combo-box
	private void refreshCmB_taille()
	{
		if (m_needRefreshSelectedIndex)
		{
			m_needRefreshSelectedIndex = false;
			getFenetre().getCmB_taille().setSelectedIndex(m_previousSelectedIndex);
			getFenetre().getCmB_taille().setEnabled(true);
		}
	}
	
	private void stockPreviousVersion()
	{
		m_needRefreshSelectedIndex = true;
		m_previousSelectedIndex = getFenetre().getCmB_taille().getSelectedIndex();
		//if (m_selectedIndexColoriage == -1) m_selectedIndexColoriage = getFenetre().getCmB_taille().getSelectedIndex();
		//else getFenetre().getCmB_taille().setSelectedIndex(m_selectedIndexColoriage);
	}

	// Sélectionne l'item Auto et empêche l'utilisateur de sélectionner dans la combo-box
	private void lockOnAutoCmB_taille()
	{
		m_previousSelectedIndex = getFenetre().getCmB_taille().getSelectedIndex();
		m_needRefreshSelectedIndex = true;
		getFenetre().getCmB_taille().setSelectedIndex(0);
		getFenetre().getCmB_taille().setEnabled(false);
	}
	
	// Rétablit le niveau précédemment sélectionné
	private void refreshRB_level()
	{
		if (m_needRefreshSelectedLevel)
		{
			m_needRefreshSelectedLevel = false;
			m_previousSelectedLevel.setSelected(true);
			enableRB_levels();
		}
	}
	
	// Change le niveau de correction d'erreur sur L et empêche l'utilisateur de le paramétrer
	private void lockOnErrorL()
	{
		if (getFenetre().getRB_correctionL().isSelected())
			m_previousSelectedLevel = getFenetre().getRB_correctionL();
		else if (getFenetre().getRB_correctionM().isSelected())
			m_previousSelectedLevel = getFenetre().getRB_correctionM();
		else if (getFenetre().getRB_correctionQ().isSelected())
			m_previousSelectedLevel = getFenetre().getRB_correctionQ();
		else if (getFenetre().getRB_correctionH().isSelected())
			m_previousSelectedLevel = getFenetre().getRB_correctionH();
		else
			m_previousSelectedLevel = getFenetre().getRB_correctionL();
		m_needRefreshSelectedLevel = true;
		getFenetre().getRB_correctionL().setSelected(true);
		disableRB_levels();
	}
	
	// Désactive les boutons de correction d'erreur
	private void disableRB_levels()
	{
		getFenetre().getRB_correctionL().setEnabled(false);
		getFenetre().getRB_correctionM().setEnabled(false);
		getFenetre().getRB_correctionQ().setEnabled(false);
		getFenetre().getRB_correctionH().setEnabled(false);
	}
	
	// Réactive les boutons de correction d'erreur, si ils étaient désactivés
	private void enableRB_levels()
	{
		if (!getFenetre().getRB_correctionL().isEnabled())
		{
			getFenetre().getRB_correctionL().setEnabled(true);
			getFenetre().getRB_correctionM().setEnabled(true);
			getFenetre().getRB_correctionQ().setEnabled(true);
			getFenetre().getRB_correctionH().setEnabled(true);
		}
	}
}
