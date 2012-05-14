package Vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// Panel affichant simplement une image
@SuppressWarnings("serial")
public class ImageComponent extends JPanel {
	
	private Image m_image;
	private String m_format;
	
	public ImageComponent(){}
	
	public ImageComponent(Image img, String format)
	{
		m_image = img;
		m_format = format;
		refreshImage();
	}
	
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if (m_image != null)
		g.drawImage(m_image, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	public void setImage(Image img, String format)
	{
		m_image = img;
		m_format = format;
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				refreshImage();
			}
		});
	}
	
	public void refreshImage()
	{
		if (m_image != null)
		{
			this.setPreferredSize(new Dimension(m_image.getWidth(null), m_image.getHeight(null)));
			this.setMinimumSize(this.getPreferredSize());
			this.setMaximumSize(this.getPreferredSize());
			this.setSize(this.getPreferredSize());
			this.repaint();
			this.updateUI();
		}
	}
	
	public Image getImage()
	{
		return m_image;
	}
	
	public String getFormat()
	{
		return m_format;
	}
}
