package visualizer.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyControls implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (KeyEvent.VK_ESCAPE == e.getKeyCode()){
			Runtime.getRuntime().exit(0);
		}
		System.out.println(e);		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
