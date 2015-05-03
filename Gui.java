import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
	private static int gamePanelHeight = 100;
	public static String selectedDate;
	static Calendar date = Utility.convertDateToCalendar(2015, 4, 20);
	static Calendar date2 = Utility.convertDateToCalendar(2015, 4, 22);

	GameDay[] AprilGames;
	// static JFrame MLBFrame;
	static JPanel scoresPanel;
	static JPanel gamePanel;
	static JPanel ddPanel;
	public static JComboBox<String> dayDropdown;
	public static JComboBox<String> monthDropdown;
	public static JComboBox<String> yearDropdown;
	String[] days = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
			"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
			"23", "24", "25", "26", "27", "28", "29", "30", "31" };
	String[] months = { "Jan", "Feb", "March", "Apr", "May", "Jun", "Jul",
			"Aug", "Sep", "Oct", "Nov", "Dec" };
	String[] years = { "2015", "2014", "2013", "2012", "2011", "2010" };

	JButton goButton;
	GameDay selectedGameDay = new GameDay(date);

	// System.out.println("GameDay 1 created");
	// GameDay selectedGameDay2 = new GameDay(date2);

	// System.out.println("GameDay 2 created");
	// System.out.println("GameDay made");

	public Gui() {
		System.out.println("Gui()");
		this.init();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void init() {
		System.out.println("init()");

		this.setTitle("MLB Scores");
		// for (int i = 1; i < 10; i++) {
		// Calendar currentDate = Utility.convertDateToCalendar(2015, 3, i);
		// System.out.println("CurrentDate:"
		// + Utility.convertCalendarToDate(currentDate)[1] + "/"
		// + Utility.convertCalendarToDate(currentDate)[2] + "/"
		// + Utility.convertCalendarToDate(currentDate)[0]);
		// // GameDay newGameDay = new GameDay(currentDate);
		// // AprilGames[i] = newGameDay;
		//
		// }
		// create 3 panels that live on MLBFrame
		ddPanel = dropdownPanel();
		scoresPanel = scoresPanel(date);
		gamePanel = gamePanel();

		this.setBounds(0, 0, windowX, windowY);
		this.getContentPane().setBounds(0, 0, windowX, windowY);

		this.getContentPane().add(ddPanel);
		this.getContentPane().add(scoresPanel);
		this.getContentPane().add(gamePanel);
		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		this.setVisible(true);

	}

	private JPanel gamePanel() {
		JPanel gPanel = new JPanel();
		// gPanel.setBounds(0, 500, windowX, gamePanelHeight);
		gPanel.setBounds(0, 200, 300, 100);
		// gamePanel.setSize(windowX, gamePanelHeight);
		String[] dates = { "April 1, 2015", "July 2, 2015", "April 3, 2015",
				"April 4, 2015" };
		JComboBox<String> defaultDropdown = new JComboBox<String>(dates);

		gPanel.add(defaultDropdown);

		gPanel.setBorder(BorderFactory.createLineBorder(Color.blue));

		return gPanel;
	}

	/**
	 * 
	 * Holds 3 ComboBoxes, for Day, Month, and Year
	 * @return ddPanel
	 */
	public JPanel dropdownPanel() {
		ddPanel = new JPanel();
		// ddPanel.setSize(windowX, ddPanelHeight);
		ddPanel.setBounds(0, 0, windowX, ddPanelHeight);

		dayDropdown = new JComboBox<String>(days);
		monthDropdown = new JComboBox<String>(months);
		yearDropdown = new JComboBox<String>(years);

		goButton = new JButton("Go");
		goButton.addActionListener(new buttonListener(this));

		// dropdown.addActionListener(new dropdownListener(this));
		ddPanel.add(dayDropdown);
		ddPanel.add(monthDropdown);
		ddPanel.add(yearDropdown);
		ddPanel.add(goButton);
		
		ddPanel.setBorder(BorderFactory.createLineBorder(Color.green));
		return ddPanel;
	}

	/**
	 * Gets Games from GameDay and displays them in three sections:
	 * AL, NL and Interleague
	 * @param date 
	 */
	public JPanel scoresPanel(Calendar inputDate) {
		// selectedGameDay = selectedGameDay4;
		System.out.println("sP()");
		System.out.println("sP:" + Utility.convertCalendarToDate(date)[1] + "/"
				+ Utility.convertCalendarToDate(date)[2] + "/"
				+ Utility.convertCalendarToDate(date)[0]);
		JPanel scorePanel = new JPanel();
		// TODO connect to dropdown
		System.out.println("scorePanel made");

		GameDay selectedGameDay = new GameDay(inputDate);
		// System.out.println("GameDay created");
		// if (date2 == inputDate){
		// System.out.println("date==inputDate");
		// selectedGameDay=selectedGameDay2;
		// }

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



	public void buttonPressed() {
		System.out.println("Go Button Pressed");
		int day = Gui.dayDropdown.getSelectedIndex()+1;
		int month = Gui.monthDropdown.getSelectedIndex()+1;
		int year = 2015 - Gui.yearDropdown.getSelectedIndex();

		Calendar newDate = Utility.convertDateToCalendar(year, month, day);
		date = newDate;

		System.out.println("ButtonPressed:" + Utility.convertCalendarToDate(date)[1] + "/"
				+ Utility.convertCalendarToDate(date)[2] + "/"
				+ Utility.convertCalendarToDate(date)[0]);
		this.getContentPane().remove(scoresPanel);
		this.getContentPane().remove(gamePanel);
		scoresPanel = null;

		// Program crashes here, takes to long to get???
		// GameDay selectedGameDay3 = new GameDay(date);
		scoresPanel = scoresPanel(date);

		this.getContentPane().add(scoresPanel);
		this.getContentPane().add(gamePanel);
		this.getContentPane().revalidate();
		// this.getContentPane().repaint();
		this.getContentPane().setVisible(true);
	}
}
