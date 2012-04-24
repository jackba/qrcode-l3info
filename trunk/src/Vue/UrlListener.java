package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UrlListener implements ActionListener
{	
	private Fenetre m_f;
	
	public UrlListener(Fenetre f)
	{
		m_f = f;
	}
	
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("RadioButton URL clicked");
        m_f.showUrlBox();
    }
}