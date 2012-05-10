package Controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import Vue.Fenetre;

public class TestController extends AbstractController implements MouseMotionListener{

	public TestController(Fenetre f) {
		super(f);
	}
	
	public boolean isValid() {
		return false;
	}
	
	public void mouseDragged(MouseEvent e) {
	}
	
	public void mouseMoved(MouseEvent e) {
		System.out.println("Moved");
	}

}
