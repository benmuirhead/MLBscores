import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author Ben
 *
 *This GUI will create a large JPanel that will hold all the panels, dropdowns, etc
 *Structure:
 *	JPanel windowFrame
 *		Date Dropdown
 *		All Scores Panel - refreshes each time a new date is selected
 *			Will Contain 3 sections: AL NL Interleague
 *				Each Section will hold all the relevant games
 *		Indiv Game Panel - refreshes each time a new game is selected
 *			When a game is selected, it shows the full 9 inning plue RHE for each team
 *
 */
public class Gui {
	// window size
	private static int windowX = 1000;
	private static int windowY = 1000;

	public Gui() {
		init();
	}

	public void init() {

		JFrame MLBFrame;
		MLBFrame = new JFrame();
		MLBFrame.setTitle("MLB Scores");

		// create 3 panels that live on MLBFrame
		JPanel dropdownPanel = dropdownPanel();
		JPanel scoresPanel = scoresPanel();
		JPanel gamePanel = new JPanel();

		MLBFrame.setBounds(0, 0, windowX, windowY);

		MLBFrame.add(dropdownPanel);
		MLBFrame.add(scoresPanel);
		MLBFrame.add(gamePanel);

		MLBFrame.setVisible(true);
		MLBFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JPanel dropdownPanel() {
		JPanel ddPanel = new JPanel();

		ddPanel.setSize(windowX, 100);

		String[] dates = { "April 1, 2015", "April 2, 2015", "April 3, 2015",
				"April 4, 2015" };
		JComboBox<String> dropdown = new JComboBox<String>(dates);

		ddPanel.add(dropdown);
		ddPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return ddPanel;
	}

	public JPanel scoresPanel() {
		/**
		 * Gets Games from Game Range?
		 */
		JPanel scoresPanel = new JPanel();
		Calendar date = Utility.convertDateToCalendar(2015, 4, 20);

		GameDay selectedGameDay = new GameDay(date);
		List<Game> selectedGames = selectedGameDay.getGames();
		List<JPanel> selectedGamesPanels = new ArrayList<JPanel>();

		for (Game g : selectedGames) {
			selectedGamesPanels.add(g.drawBasicScore());
		}

		
		GridLayout grid = new GridLayout(selectedGamesPanels.size(), 1);
		scoresPanel.setLayout(grid);

		for (JPanel panel : selectedGamesPanels) {
			scoresPanel.add(panel);
		}

		scoresPanel.setSize(windowX, 800);

		return scoresPanel;
	}
}
