package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelListener implements ActionListener
{	
	private Fenetre m_f;
	
	public TelListener(Fenetre f)
	{
		m_f = f;
	}
	
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("RadioButton Numéro de Tel clicked");
    }
}