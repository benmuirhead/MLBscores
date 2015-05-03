import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class buttonListener implements ActionListener {

	private Gui Gui;
	private JButton b1;

	public buttonListener(Gui testGui) {
		this.Gui = testGui;
		this.b1 = Gui.goButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//JButton button = (JButton) e.getSource();
		Gui.buttonPressed();
	}

}
