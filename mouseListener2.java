import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


public class mouseListener2 implements MouseListener {

	
	
	
	private Gui Gui;

	public mouseListener2(Gui testGui) {
		this.Gui = testGui;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Game newGame;
		JPanel source = (JPanel) e.getSource();
		String gameURL = source.getName();
		System.out.println(gameURL);
		newGame = new Game(gameURL);
		Gui.newDetailGame(newGame);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
