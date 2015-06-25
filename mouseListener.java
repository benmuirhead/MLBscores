import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * Implements a MouseListener for clicking on games to bring up the detailed score
 * @author Samuel Doud
 * @author Ben Muirhead
 */

public class mouseListener implements MouseListener {

	private Gui Gui;

	public mouseListener(Gui testGui) {
		this.Gui = testGui;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Game newGame;
		JPanel source = (JPanel) e.getSource();
		String gameURL = source.getName();
		System.out.println(gameURL);
		newGame = new Game(gameURL);
		Gui.newDetailGame(newGame);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
