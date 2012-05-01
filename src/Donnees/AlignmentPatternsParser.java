package Donnees;

import java.io.File;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class AlignmentPatternsParser {
	
	private static String XML_DEFAULT_PATH = "src/Donnees/AlignmentPatternsPositions.xml";	// Chemin par défaut du fichier xml
	private static AlignmentPatternsParser m_instance;	// L'instance unique de cette classe
	private Document m_document;	// Le document DOM chargé en mémoire
	private Element m_racine;	// L'élément racine du document
	private String m_xmlPath;	// Chemin vers le fichier xml à parser
	
	// Balises (B_******) - Attributs (A_*****)
	private static String B_VERSION = "version";
	private static String A_VER_NUM = "number";
	
	private static String B_POSITION = "position";
	
	// Retourne l'instance unique de cette classe (Singleton)
	public static AlignmentPatternsParser getInstance()
	{
		if (m_instance == null)
		{
			m_instance = new AlignmentPatternsParser();
		}
		return m_instance;
	}
	
	// Constructeur privé pour le singleton
	private AlignmentPatternsParser()
	{
		m_xmlPath = XML_DEFAULT_PATH;
		initParser();
	}
	
	// Retourne les positions des patterns d'alignement à partir de la version
	@SuppressWarnings("unchecked")
	public int[] getPositions(int version)
	{
		List<Element> versions = m_racine.getChildren(B_VERSION);
		int[] positions;
		
		if (version == 1) return new int[0];
		int position;
		int num;
		
		for(int i=0; i<versions.size(); i++)
		{
			num = Integer.parseInt(versions.get(i).getAttribute(A_VER_NUM).getValue());
			if (num == version)
			{
				positions = new int[versions.get(i).getChildren(B_POSITION).size()];
				
				for (int j=0; j<positions.length; j++)
				{
					position = Integer.parseInt(((Element)(versions.get(i).getChildren(B_POSITION).get(j))).getValue());
					positions[j] = position;
				}
				return positions;
			}
		}
		
		return new int[0];
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
	
	public static void main(String[] args)
	{
		AlignmentPatternsParser app = new AlignmentPatternsParser();
		int[] result = app.getPositions(80);
		for (int i=0; i<result.length; i++)
			System.out.println("[" + i + "] = " + result[i]);
	}
}
