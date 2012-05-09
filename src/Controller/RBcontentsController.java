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
	
	public RBcontentsController(Fenetre f,
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
	}
	
	public boolean isValid() {
		return true;
	}
	
	public void actionPerformed(ActionEvent event)
	{
		getFenetre().hideShownBoxes();
		
		JRadioButton rb = (JRadioButton)event.getSource();
		if (rb.equals(getFenetre().getRB_url()))
		{
			getFenetre().showUrlBox();
			m_TFurlController.switchEnableDisableBgenerer();
			getFenetre().getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(m_TFurlController.getMode()));
		}
		else if (rb.equals(getFenetre().getRB_txt()))
		{
			getFenetre().showTxtBox();
			m_TAtxtController.switchEnableDisableBgenerer();
			getFenetre().getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(m_TAtxtController.getMode()));
		}
		else if (rb.equals(getFenetre().getRB_tel()))
		{
			getFenetre().showTelBox();
			m_TFtelController.switchEnableDisableBgenerer();
			getFenetre().getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(m_TFtelController.getMode()));
		}
		else if (rb.equals(getFenetre().getRB_sms()))
		{
			getFenetre().showSmsBox();
			m_TFsmsTelController.switchEnableDisableBgenerer();
			m_TAsmsMsgController.switchEnableDisableBgenerer();
			getFenetre().getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(m_TAsmsMsgController.getMode()));
		}
		else if (rb.equals(getFenetre().getRB_image()))
		{
			getFenetre().showImgBox();
			getFenetre().getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(CharacterMode.BYTES));
		}
		else if (rb.equals(getFenetre().getRB_coloriage()))
		{
			getFenetre().showPaintBox();
			getFenetre().getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(CharacterMode.BYTES));
		}
		else if (rb.equals(getFenetre().getRB_decode()))
		{
			getFenetre().showDecodeBox();
			getFenetre().getL_modeIndicator().setText(AbstractTextController.getTextForModeIndicator(null));
		}
	}

}
