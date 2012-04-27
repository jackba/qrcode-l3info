package Donnees;

import java.io.*;

import org.jdom.*;
import org.jdom.input.*;

import Model.Block;
import Model.CorrectionLevel;
import Model.Specification;

import java.util.ArrayList;
import java.util.List;

public class ErrorCorrectionParser {
	
	private static String XML_DEFAULT_PATH = "src/Donnees/ErrorCorrectionTable.xml";	// Chemin par défaut du fichier xml
	private static ErrorCorrectionParser m_instance;	// L'instance unique de cette classe
	private Document m_document;	// Le document DOM chargé en mémoire
	private Element m_racine;	// L'élément racine du document
	private String m_xmlPath;	// Chemin vers le fichier xml à parser
	private ArrayList<Specification> m_specifications;	// Les spécifications chargées précédemment en mémoire (pour un accès ultérieur plus rapide)
	
	// Balises (B_******) - Attributs (A_*****)
	private static String B_VERSION = "version";
	private static String A_VER_NUM = "number";
	private static String A_VER_TOTAL = "total_words";
	
	private static String B_CORRECTION = "correction";
	private static String A_COR_LVL = "level";
	private static String A_COR_WORDS = "correction_words";
	
	private static String B_BLOCK = "block";
	private static String A_BLO_NUM = "number";
	private static String A_BLO_TOTAL = "total_words";
	private static String A_BLO_DATA = "data_words";
	
	// Retourne l'instance unique de cette classe (Singleton)
	public static ErrorCorrectionParser getInstance()
	{
		if (m_instance == null)
		{
			m_instance = new ErrorCorrectionParser();
		}
		return m_instance;
	}
	
	// Constructeur privé pour le singleton
	private ErrorCorrectionParser()
	{
		m_xmlPath = XML_DEFAULT_PATH;
		initParser();
	}
	
	// Retourne une spécification demandée à partir de sa version
	public Specification getSpecification(int version)
	{
		// On vérifie en premier que la spécification n'a pas déjà été créée
		if (m_specifications != null)
			if (m_specifications.size() > 0)
				for (int i=0; i<m_specifications.size(); i++)
					if (m_specifications.get(i).getVersion() == version) return m_specifications.get(i);
		
		// La spécification n'est pas dans la liste, on va la créer à partir du fichier xml
		return getSpecificationFromXML(version);	
	}
	
	// Retourne un objet Specification pour la version demandée,
	// et créé à partir des informations contenues dans le xml
	@SuppressWarnings("unchecked")
	private Specification getSpecificationFromXML(int version)
	{
		List<Element> versions = m_racine.getChildren(B_VERSION);
		
		for(int i=0; i<versions.size(); i++)
		{
			int num = Integer.parseInt(versions.get(i).getAttribute(A_VER_NUM).getValue());
			if (num == version)
			{
				return getSpecificationFromElement(versions.get(i));
			}
		}
		
		return null;
	}
	
	
	// Retourne une Specification à partir d'un élément DOM version
	@SuppressWarnings("unchecked")
	private Specification getSpecificationFromElement(Element e)
	{
		int version = Integer.parseInt(e.getAttribute(A_VER_NUM).getValue());
		int totalWords = Integer.parseInt(e.getAttribute(A_VER_TOTAL).getValue());
		Specification sp = new Specification(version, totalWords);
		
		// Ajout des niveaux de correction
		List<Element> correctionLevels = e.getChildren(B_CORRECTION);
		for(int i=0; i<correctionLevels.size(); i++)
			sp.addCorrectionLevel(getCorrectionLevelFromElement(correctionLevels.get(i)));
		
		return sp;
	}
	
	// Retourne un CorrectionLevel à partir d'un élément DOM correction
	@SuppressWarnings("unchecked")
	private CorrectionLevel getCorrectionLevelFromElement(Element e)
	{
		String level = e.getAttribute(A_COR_LVL).getValue();
		int totalWords = Integer.parseInt(e.getAttribute(A_COR_WORDS).getValue());
		CorrectionLevel cl = new CorrectionLevel(level, totalWords);
		
		// Ajout des blocs
		List<Element> blocks = e.getChildren(B_BLOCK);
		for(int i=0; i<blocks.size(); i++)
			cl.addBlock(getBlockFromElement(blocks.get(i)));
		
		return cl;
	}
	
	// Retourne un Block à partir d'un élément DOM block
	private Block getBlockFromElement(Element e)
	{
		int numberCount = Integer.parseInt(e.getAttribute(A_BLO_NUM).getValue());
		int totalCount = Integer.parseInt(e.getAttribute(A_BLO_TOTAL).getValue());
		int dataCount = Integer.parseInt(e.getAttribute(A_BLO_DATA).getValue());
		return new Block(numberCount, totalCount, dataCount);
	}
	
	// Initialise le parseur
	public void initParser()
	{
		m_document = null;
		m_racine = null;
		m_specifications = new ArrayList<Specification>();
		
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
	
	public static void main(String[] args)
	{
		ErrorCorrectionParser ecp = ErrorCorrectionParser.getInstance();
		System.out.println(ecp.getSpecification(23));
	}
}
