import javax.swing.SwingUtilities;
import Vue.Fenetre;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				Fenetre f = new Fenetre();
				f.setVisible(true);
			}
		});
	}
	
}
