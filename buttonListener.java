import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Runs Gui.goButtonPressed() when Go button is clicked on
 * @author Ben Muirhead
 * 
 */
public class buttonListener implements ActionListener {

	private Gui Gui;

	public buttonListener(Gui testGui) {
		this.Gui = testGui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Gui.goButtonPressed();
	}

}
