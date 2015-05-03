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
public class Gui extends JFrame {
	// window size
	private static int windowX = 1000;
	private static int windowY = 1000;

	private static int ddPanelHeight = 100;
	public static String selectedDate;
	static Calendar date = Utility.convertDateToCalendar(2015, 4, 20);

	// static JFrame MLBFrame;
	static JPanel scoresPanel;
	
	GameDay selectedGameDay = new GameDay(date);
	//System.out.println("GameDay made");
	
	public Gui() {
		// MLBFrame = new JFrame();
		System.out.println("Gui()");
		this.init();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void init() {
		System.out.println("init()");

		this.setTitle("MLB Scores");

		// create 3 panels that live on MLBFrame
		JPanel dropdownPanel = dropdownPanel();
		scoresPanel = scoresPanel(date);
		JPanel gamePanel = new JPanel();

		this.setBounds(0, 0, windowX, windowY);

		this.getContentPane().add(dropdownPanel);
		
		this.getContentPane().add(gamePanel);
		this.getContentPane().add(scoresPanel);

		// MLBFrame.setVisible(true);
		// this.add(MLBFrame);
		this.setVisible(true);

	}

	public JPanel dropdownPanel() {
		JPanel ddPanel = new JPanel();

		ddPanel.setSize(windowX, ddPanelHeight);

		String[] dates = { "April 1, 2015", "April 2, 2015", "April 3, 2015",
				"April 4, 2015" };
		JComboBox<String> dropdown = new JComboBox<String>(dates);

		dropdown.addActionListener(new dropdownListener(this));
		ddPanel.add(dropdown);
		ddPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return ddPanel;
	}

	/**
	 * Gets Games from GameDay and displays them in three sections:
	 * AL, NL and Interleague
	 * @param inputDate 
	 */
	public JPanel scoresPanel(Calendar inputDate) {
		System.out.println("sP()");
		System.out.println("sP:" + Utility.convertCalendarToDate(inputDate)[1]
				+ "/" + Utility.convertCalendarToDate(inputDate)[2] + "/"
				+ Utility.convertCalendarToDate(inputDate)[0]);
		JPanel scorePanel = new JPanel();
		// TODO connect to dropdown
		System.out.println("scorePanel made");
		
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
			ALGamesP.add(g.drawBasicScoreWithDate());
		}
		for (JPanel panel : ALGamesP) {
			ALPanel.add(panel);
		}

		ALPanel.setLayout(grid);
		scorePanel.add(ALPanel);

		// NL
		for (Game g : selectedNLGames) {
			NLGamesP.add(g.drawBasicScore());
		}
		for (JPanel panel : NLGamesP) {
			NLPanel.add(panel);
		}

		NLPanel.setLayout(grid);
		scorePanel.add(NLPanel);

		// Interleague
		for (Game g : selectedInterGames) {
			interGamesP.add(g.drawBasicScore());
		}
		for (JPanel panel : interGamesP) {
			interPanel.add(panel);
		}

		interPanel.setLayout(grid);
		scorePanel.add(interPanel);

		GridLayout scoresPanelgrid = new GridLayout(1, 3);
		scorePanel.setLayout(scoresPanelgrid);
		scorePanel.setBounds(0, ddPanelHeight, 1000, 400);
System.out.println("return scorePanel");
		return scorePanel;
	}

	public void dropdownChange(Calendar cal) {
		date = cal;
		System.out.println("dC:" + Utility.convertCalendarToDate(date)[1] + "/"
				+ Utility.convertCalendarToDate(date)[2] + "/"
				+ Utility.convertCalendarToDate(date)[0]);
		// scoresPanel.revalidate(); // not working
		// System.out.println("scoresPanel repainted");
		// MLBFrame.revalidate();
		// this..repaint();0
		// Gui.MLBFrame.repaint();
		// this.revalidate();
		System.out.println(this);
		// Gui.scoresPanel.revalidate();
		this.getContentPane().remove((scoresPanel));
		scoresPanel = null;
		scoresPanel = scoresPanel(date);
		
		// scoresPanel = new JPanel();
		// scoresPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// scoresPanel.setBounds(0, ddPanelHeight, 1000, 400);
		this.add(scoresPanel);
		this.validate();
		//this.validateTree();
		this.repaint();

	}
}
