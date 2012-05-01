package Vue;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

// Cette classe fournit un panel de dessin pour y dessiner le QRcode final
// Le panel est géré en double buffering mémoire (c.à.d que le buffer n'est pas traité par la Carte Graphique, mais stocké dans la RAM et géré par le processeur)
// Elle fournit aussi une méthode de sauvegarde sous forme d'image de son contenu
@SuppressWarnings("serial")
class QRcodeComponent extends JPanel implements ComponentListener {

	// Buffer mémoire
	private Graphics m_buffer;
	// Image mémoire correspondant au buffer
	private Image m_image;
	// Dimensions maximums précédentes du buffer
	private int m_previousWidth;
	private int m_previousHeight;

	// Constructeur
	public QRcodeComponent()
	{
		// Définition d'une taille par défaut
		this.setPreferredSize(new Dimension(200,200));
		
		// initialisation des dernières plus grandes dimensions qu'a pris le composant
		m_previousWidth = getPreferredSize().width;
		m_previousHeight = getPreferredSize().height;
		
		// Ajout d'un écouteur sur le redimensionnement
		// Qui pointe sur cette classe
		// On implémente donc le listener ComponentListener
		this.addComponentListener(this);
	}

	// OnPaint
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Création du buffer s'il n'existe pas
		if(m_buffer == null){
			m_image = createImage(getPreferredSize().width,getPreferredSize().height);
			m_buffer = m_image.getGraphics();
		}

		// Dessin sur le buffer
		m_buffer.setColor(Color.white);
		m_buffer.fillRect(0, 0, getWidth(), getHeight());	// Rectangle blanc
		/*
		m_buffer.setColor(Color.black);
		m_buffer.drawString("Ceci est mon panel personnalisé",10,20);	// Texte noir
		*/

		// Recopie du buffer mémoire dans le buffer d'affichage
		g.drawImage(m_image, 0, 0, this);
	}

	// OnResize
	public void componentResized(ComponentEvent event)
	{
		// Récupération du composant émetteur (this en l'occurence)
		Component c = (Component)event.getSource();

		// Modification du buffer uniquement lorsque la fenêtre est agrandie
		if ((c.getSize().width > m_previousWidth || c.getSize().height > m_previousHeight) && (m_buffer != null))
		{
			// Récupération des tailles temporaire en fonction de ce qui a été agrandit
			int tempWidth, tempHeight;

			// Seule la largeur change
			if(c.getSize().width > m_previousWidth && c.getSize().height <= m_previousHeight)
			{
				tempWidth = c.getSize().width;
				tempHeight = m_previousHeight;
			}
			// Seule la hauteur change
			else if(c.getSize().width <= m_previousWidth && c.getSize().height > m_previousHeight)
			{
				tempWidth = m_previousWidth;
				tempHeight = c.getSize().height;
			}
			// La largeur et la hauteur changent
			else
			{
				tempWidth = c.getSize().width;
				tempHeight = c.getSize().height;
			}

			// Création d'un nouveau couple buffer/image de la taille du composant émetteur
			m_image = createImage(tempWidth,tempHeight);
			m_buffer = m_image.getGraphics();

			// Assignation des nouvelles dimensions comme étant les plus grandes dimensions atteintes jusqu'ici par le composant
			m_previousWidth = tempWidth;
			m_previousHeight = tempHeight;

			// Demander au composant de se redessiner
			this.repaint();
		}
	}

	// Sauvegarde l'image du composant au format et à l'emplacement choisi
	// Retourne vrai lorsque la sauvegarde s'est correctement déroulée
	public boolean saveAsImage(ImageFormat format, String pathName)
	{
		// Nouveau buffer où sera stocké l'image avant enregistrement
		BufferedImage outImage = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_RGB);
		// Nouvelle image associée au buffer
		Graphics2D graphics = outImage.createGraphics();

		this.paint(graphics);	// Peint le graphics avec le buffer d'image (ce qui est actuellement affiché à l'écran)

		// Création du fichier de destination
		File outFile = new File(pathName);
		// Format de l'image finale
		String mFormat;
		
		// Récupération du format
		switch (format)
		{
			case JPEG: mFormat = "jpg"; break;    
			case PNG: mFormat = "png"; break;     
			case BMP: mFormat = "bmp"; break;
			// case TIFF: mFormat = "tif"; break;	// Pas pris en charge
			case GIF: mFormat = "gif"; break;
			default: mFormat = "png"; break;
		}
		
		// Ecriture de l'image dans le fichier de sortie
		try {
			if (ImageIO.write(outImage,mFormat,outFile)) return true;
			System.err.println("Format d'écriture non pris en charge" );
			return false;
		} catch (Exception e) {
			System.err.println("erreur dans l'enregistrement de l'image :" );
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 *  Méthodes non-implémentée de l'interface ComponentListener
	 */
	public void componentHidden(ComponentEvent event) {}
	public void componentMoved(ComponentEvent event) {}
	public void componentShown(ComponentEvent event) {}
}