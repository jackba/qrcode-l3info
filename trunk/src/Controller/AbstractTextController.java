package Controller;

import Vue.Fenetre;

public abstract class AbstractTextController extends AbstractController {

	private int[][] m_maximumsChars;
	private int m_defaultLength;
	private CharacterMode m_currentMode;
	private int m_maximumCurrentChars;	// Maximum de caractères tous champs confondus pour la version et le niveau d'erreur sélectionnés actuellement
	
	public AbstractTextController(Fenetre f, int defaultLength, CharacterMode mode) {
		super(f);
		m_defaultLength = defaultLength;
		m_currentMode = mode;
	}
	
	public int[][] getMaximumsChars()
	{
		return m_maximumsChars;
	}
	
	public void setMaximumsChars(int[][] maximums)
	{
		m_maximumsChars = maximums;
		onMaxCharsChanged();
	}
	
	public void onMaxCharsChanged()
	{
		int line;
		int column;
		
		switch(m_currentMode)
		{
		case NUMERIC: column = 0; break;
		case ALPHANUMERIC: column = 1; break;
		case BYTES: column = 2; break;
		case KANJI: column = 3; break;
		default:
			column = 2;
		}
		
		if (getFenetre().getRB_correctionL().isSelected()) line = 0;
		else if (getFenetre().getRB_correctionM().isSelected()) line = 1;
		else if (getFenetre().getRB_correctionQ().isSelected()) line = 2;
		else if (getFenetre().getRB_correctionH().isSelected()) line = 3;
		else line = 0;
		
		m_maximumCurrentChars = m_maximumsChars[line][column];
	}

	public int getDefaultLength() {
		return m_defaultLength;
	}

	public void setDefaultLength(int defaultLength) {
		m_defaultLength = defaultLength;
	}
	
	public CharacterMode getMode()
	{
		return m_currentMode;
	}
	
	public void setMode(CharacterMode mode)
	{
		m_currentMode = mode;
	}
	
	public int getMaximumChars()
	{
		return m_maximumCurrentChars;
	}
	
	// Retourne le message qui sera encodé dans le qrCode
	public abstract String getMessage();
}
