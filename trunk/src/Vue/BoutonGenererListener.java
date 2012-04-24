package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoutonGenererListener implements ActionListener
{	
	@SuppressWarnings("unused")
	private Fenetre m_f;
	
	public BoutonGenererListener(Fenetre f)
	{
		m_f = f;
	}
	
    public void actionPerformed(ActionEvent e)
    {
    	System.out.println("Bouton \"Générer\" cliqué");
    }
}