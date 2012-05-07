package Controller;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.regex.Pattern;

import Vue.Fenetre;

public abstract class AbstractTextController extends AbstractController {

	// Encodeurs statiques pour les principales langues dans lesquelles seront codés les QRcodes
	private static CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();	// Encodeur ASCII (127 caractères)
	private static CharsetEncoder jis8Encoder = Charset.forName("Shift_JIS").newEncoder();	// Encodeur JIS8 (255 caractères) - utilisé de base dans le QRcode (fait référence à la norme ECI 000002)
	private static CharsetEncoder latin9Encoder = Charset.forName("ISO-8859-15").newEncoder();	// Encodeur Latin-9 (aussi connu sous le nom de norme ISO-8859-15)
	
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
		case ECI: column = 2; break;
		default:
			column = 2;
		}
		
		if (getFenetre().getRB_correctionL().isSelected()) line = 0;
		else if (getFenetre().getRB_correctionM().isSelected()) line = 1;
		else if (getFenetre().getRB_correctionQ().isSelected()) line = 2;
		else if (getFenetre().getRB_correctionH().isSelected()) line = 3;
		else line = 0;
		
		m_maximumCurrentChars = m_maximumsChars[line][column];
		if (m_currentMode == CharacterMode.ECI)
		{
			m_maximumCurrentChars = m_maximumCurrentChars - 2;
			if (m_maximumCurrentChars < 0) m_maximumCurrentChars = 0;
		}
	}
	
	public static String getTextForModeIndicator(CharacterMode mode)
	{
		switch(mode)
		{
		case NUMERIC: return "(mode Numérique)";
		case ALPHANUMERIC: return "(mode Alpha-numérique)";
		case BYTES: return "(mode Octet)";
		case KANJI: return "(mode Kanji)";
		case ECI: return "(mode Latin-9)";
		default:
			return "(mode Octet)";
		}
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
	
	// Vérifie que la chaine est numérique
	public static Boolean isNumericString(String chaine)
	{
		return Pattern.matches("^[0-9]+$", chaine);
	}

	// Vérifie que la chaine est alpha numérique
	public static Boolean isAlphaNumericString(String chaine)
	{
		return Pattern.matches("^([0-9]|[A-Z]| |\\$|%|\\*|\\+|\\-|\\.|/|:)+$", chaine);
	}
	
	// Vérifie que la chaine est encodable en caractères ASCII
	public static boolean isAscii(String chaine) {
		return asciiEncoder.canEncode(chaine);
	}
	
	// Vérifie que la chaine est encodable en caractères JIS8 (encodage par défaut dans le mode Byte)
	public static boolean isJIS8(String chaine)
	{
		return jis8Encoder.canEncode(chaine);
	}
	
	// Vérifie que la chaine est encodable en caractères Latin 9, qui sont les caractères de notre langue
	public static boolean isLatin9(String chaine)
	{
		return latin9Encoder.canEncode(chaine);
	}
	
	// Retourne le mode dans lequel peut être encodée la chaine
	public static CharacterMode getAdaptedMode(String chaine)
	{
		if (isNumericString(chaine))
			return CharacterMode.NUMERIC;
		else if (isAlphaNumericString(chaine))
			return CharacterMode.ALPHANUMERIC;
		else if (isJIS8(chaine))
			return CharacterMode.BYTES;
		else if (isLatin9(chaine))
			return CharacterMode.ECI;
		else
			return CharacterMode.KANJI;
	}
}
