package Donnees;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import Controller.CharacterMode;

public class NumberOfSymbolCharacterParser {

	private static String XML_DEFAULT_PATH = "src/Donnees/numberOfSymbolCharacter.xml";	// Chemin par défaut du fichier xml
	private static NumberOfSymbolCharacterParser m_instance;	// L'instance unique de cette classe
	private Document m_document;	// Le document DOM chargé en mémoire
	private Element m_racine;	// L'élément racine du document
	private String m_xmlPath;	// Chemin vers le fichier xml à parser
	private ArrayList<Version> m_versions;

	// Balises (B_******) - Attributs (A_*****)
	private static String B_VERSION = "version";
	private static String A_VER_NUM = "numero";

	private static String B_LEVEL = "correction_level";
	private static String B_CAPACITY = "data_capacity";

	// Retourne l'instance unique de cette classe (Singleton)
	public static NumberOfSymbolCharacterParser getInstance()
	{
		if (m_instance == null)
		{
			m_instance = new NumberOfSymbolCharacterParser();
		}
		return m_instance;
	}

	// Constructeur privé pour le singleton
	private NumberOfSymbolCharacterParser()
	{
		m_xmlPath = XML_DEFAULT_PATH;
		m_versions = new ArrayList<Version>();
		initParser();
	}

	// Retourne les capacités en nombre de caractères de la version choisie
	// et dans les différents types d'encodage et les différents modes de correction.
	// Une colonne représente un mode d'encodage du plus petit mode de correction (en haut)
	// au plus grand mode de correction (en bas).
	@SuppressWarnings("unchecked")
	public int[][] getCapacities(int version)
	{
		// Recherche dans la liste des existants
		for (int i=0; i<m_versions.size(); i++)
		{
			if (m_versions.get(i).getNumber() == version)
				return m_versions.get(i).getCapacities();
		}

		// La version n'a pas encore été demandée, on va la créer puis la retourner depuis le xml
		List<Element> versions = m_racine.getChildren(B_VERSION);
		int[][] capacities;

		int capacite;
		int num;

		for(int i=0; i<versions.size(); i++)
		{
			num = Integer.parseInt(versions.get(i).getAttribute(A_VER_NUM).getValue());
			if (num == version)
			{
				List<Element> levels = versions.get(i).getChildren(B_LEVEL);
				capacities = new int[levels.size()][4];

				for (int j=0; j<levels.size(); j++)
				{
					List<Element> types = ((Element)(levels.get(j).getChildren(B_CAPACITY).get(0))).getChildren();
					for (int k=0; k<types.size(); k++)
					{
						capacite = Integer.parseInt(types.get(k).getValue());
						capacities[j][k] = capacite;
					}
				}
				m_versions.add(new Version(version,capacities)); // On ajoute la nouvelle version dans la liste
				return capacities;
			}
		}
		return null;
	}

	// Retourne la première version adaptée au mode, à la longueur des données et au niveau de correction d'erreur demandé
	@SuppressWarnings("unchecked")
	public int getFirstAdaptedVersion(CharacterMode mode, int capacity, String level)
	{
		List<Element> versions = m_racine.getChildren(B_VERSION);

		int capacite;
		int num;
		int lvl;
		int mod;
		
		// Dans notre cas les balises de niveaux dans le xml sont toujours placés dans le même ordre (L,M,Q,H).
		// Pour optimiser notre algorithme, on va toujours rechercher notre niveau à une position bien définie
		if (level.equalsIgnoreCase("L")) lvl = 0;
		else if (level.equalsIgnoreCase("M")) lvl = 1;
		else if (level.equalsIgnoreCase("Q")) lvl = 2;
		else if (level.equalsIgnoreCase("H")) lvl = 3;
		else lvl = 0;
		
		// La même chose que pour les niveaux s'applique aux modes (numeric, alphanumeric, byte, kanji)
		switch(mode)
		{
		case NUMERIC: mod = 0; break;
		case ALPHANUMERIC: mod = 1; break;
		case BYTES: mod = 2; break;
		case KANJI: mod = 3; break;
		default: mod = 2;
		}
		
		for(int i=0; i<versions.size(); i++)
		{
			num = Integer.parseInt(versions.get(i).getAttribute(A_VER_NUM).getValue());
			List<Element> levels = versions.get(i).getChildren(B_LEVEL);
			
			capacite = Integer.parseInt(((Element)levels.get(lvl).getChild(B_CAPACITY).getChildren().get(mod)).getValue());
			
			if (capacite >= capacity) return num;	// la capacité est suffisante pour cette version, ce sera donc celle choisie
		}

		return -1;
	}

	// Initialise le parseur
	public void initParser()
	{
		m_document = null;
		m_racine = null;

		SAXBuilder sxb = new SAXBuilder();	// Nouveau parseur sax

		try {
			// Création du document
			m_document = sxb.build(new File(m_xmlPath));
			// Récupération de la racine du document
			m_racine = m_document.getRootElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Recharge le document dans le parseur
	// (à utiliser si le document est mis à jour pdt l'exécution de l'application)
	public void reloadParser()
	{
		initParser();
	}

	// Recharge le document au chemin spécifié dans le parseur
	// (à utiliser si le document est mis à jour pdt l'exécution de l'application)
	public void reloadParser(String xmlPath)
	{
		m_xmlPath = xmlPath;
		initParser();
	}

	// Classe stockant toutes les capacités d'une version
	public class Version {
		private int m_number;
		private int[][] m_capacities;

		public Version(int number)
		{
			m_number = number;
		}
		public Version(int number, int[][] capacities)
		{
			m_number = number;
			m_capacities = capacities;
		}
		public int[][] getCapacities()
		{
			return m_capacities;
		}
		public int getNumber()
		{
			return m_number;
		}
		public void setCapacities(int[][] capacities)
		{
			m_capacities = capacities;
		}
	}
}
