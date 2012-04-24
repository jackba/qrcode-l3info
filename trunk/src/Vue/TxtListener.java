package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TxtListener implements ActionListener
{	
	private Fenetre m_f;
	
	public TxtListener(Fenetre f)
	{
		m_f = f;
	}
	
    public void actionPerformed(ActionEvent e)
    {
        m_f.showTxtBox();
    }
}