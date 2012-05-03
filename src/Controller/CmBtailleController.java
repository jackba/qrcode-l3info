package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Donnees.NumberOfSymbolCharacterParser;
import Vue.Fenetre;

public class CmBtailleController extends AbstractController implements ActionListener {

	private NumberOfSymbolCharacterParser m_symbolCharacterParser;
	private int[][] m_capacities;
	private TFurlController m_TFurlController;
	private TAtxtController m_TAtxtController;
	private TFtelController m_TFtelController;
	private TFsmsTelController m_TFsmsTelController;
	private TAsmsMsgController m_TAsmsMsgController;
	
	public CmBtailleController (Fenetre f,
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
		m_symbolCharacterParser = NumberOfSymbolCharacterParser.getInstance();
	}

	public boolean isValid() {
		return false;
	}
	
	public void actionPerformed(ActionEvent event)
	{
		int version = -1;
		boolean isAuto = true;
		try
		{
			version = Integer.parseInt(getFenetre().getCmB_taille().getSelectedItem().toString());
			isAuto = false;
		}
		catch (Exception e)
		{
			isAuto = true;
		}
		finally
		{
			// L'utilisateur a choisit une version
			if (!isAuto)
			{
				m_capacities = m_symbolCharacterParser.getCapacities(version);
			}
			// L'utilisateur a choisit la sélection automatique
			else
			{
				m_capacities = m_symbolCharacterParser.getCapacities(40);
			}
			m_TFurlController.setMaximumsChars(m_capacities);
			m_TAtxtController.setMaximumsChars(m_capacities);
			m_TFtelController.setMaximumsChars(m_capacities);
			m_TFsmsTelController.setMaximumsChars(m_capacities);
			m_TAsmsMsgController.setMaximumsChars(m_capacities);
		}
	}

}
