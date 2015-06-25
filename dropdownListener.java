import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

/**
 * Implements an Action Listener on the Team Selection dropdown
 * @author Samuel Doud
 * @author Ben Muirhead
 */
public class dropdownListener implements ActionListener {
	
	private Gui Gui;
	public dropdownListener(Gui testGui){
	this.Gui = testGui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	JComboBox<String> dd = (JComboBox) e.getSource();
	String fullNewTeamName = (String) dd.getSelectedItem();
	Gui.newTeamSelected(fullNewTeamName);
	
	}
}
