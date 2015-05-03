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
 */

/**
 *This GUI will create a large JPanel that will hold all the panels, dropdowns, etc
 *Structure:
 *	JPanel windowFrame
 *		Date Dropdown
 *		All Scores Panel - refreshes each time a new date is selected
 *			Will Contain 3 sections: AL/NL/ Interleague
 *				Each Section will hold all the relevant games
 *		Indiv Game Panel - refreshes each time a new game is selected
 *			When a game is selected, it shows the full 9 inning plue RHE for each team
 *
 */
public class Gui {
	// window size
	private static int windowX = 1000;
	private static int windowY = 1000;

	private static int ddPanelHright = 100;

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

		ddPanel.setSize(windowX, ddPanelHright);

		String[] dates = { "April 1, 2015", "April 2, 2015", "April 3, 2015",
				"April 4, 2015" };
		JComboBox<String> dropdown = new JComboBox<String>(dates);

		ddPanel.add(dropdown);
		ddPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return ddPanel;
	}

	/**
	 * Gets Games from GameDay and displays them in three sections:
	 * AL, NL and Interleague
	 */
	public JPanel scoresPanel() {

		JPanel scoresPanel = new JPanel();
		// TODO connect to dropdown
		Calendar date = Utility.convertDateToCalendar(2015, 4, 20);

		GameDay selectedGameDay = new GameDay(date);

		// List that holds games in category
		List<Game> selectedALGames = selectedGameDay.getAmericanGames();
		List<Game> selectedNLGames = selectedGameDay.getNationalGames();
		List<Game> selectedInterGames = selectedGameDay.getInterLeagueGames();

		// List that holds panels in category
		List<JPanel> ALGamesP = new ArrayList<JPanel>();
		List<JPanel> NLGamesP = new ArrayList<JPanel>();
		List<JPanel> interGamesP = new ArrayList<JPanel>();

		// Panel that holds all game panels in category
		JPanel ALPanel = new JPanel();
		JPanel NLPanel = new JPanel();
		JPanel interPanel = new JPanel();

		GridLayout grid = new GridLayout(Math.max(selectedALGames.size(),
				Math.max(selectedNLGames.size(), selectedInterGames.size())), 1);
		
		// AL
		for (Game g : selectedALGames) {
			ALGamesP.add(g.drawBasicScore());
		}
		for (JPanel panel : ALGamesP) {
			ALPanel.add(panel);
		}
		
		ALPanel.setLayout(grid);
		// ALPanel.setSize(100);
		scoresPanel.add(ALPanel);

		// NL
		for (Game g : selectedNLGames) {
			NLGamesP.add(g.drawBasicScore());
		}
		for (JPanel panel : NLGamesP) {
			NLPanel.add(panel);
		}
		
		NLPanel.setLayout(grid);
		//NLPanel.setSize(200, 300);
		scoresPanel.add(NLPanel);

		// Interleague
		for (Game g : selectedInterGames) {
			interGamesP.add(g.drawBasicScore());
		}
		for (JPanel panel : interGamesP) {
			interPanel.add(panel);
		}
		
		interPanel.setLayout(grid);
		scoresPanel.add(interPanel);

		GridLayout scoresPanelgrid = new GridLayout(1, 3);
		scoresPanel.setLayout(scoresPanelgrid);
		scoresPanel.setBounds(0, ddPanelHright, 1000, 400);
		

		return scoresPanel;
	}
}
