package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SmsListener implements ActionListener
{	
	private Fenetre m_f;
	
	public SmsListener(Fenetre f)
	{
		m_f = f;
	}
	
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("RadioButton SMS clicked");
    }
}