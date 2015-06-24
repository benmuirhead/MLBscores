import java.awt.Color;
<<<<<<< HEAD
=======
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
>>>>>>> b0d836b864d124b5dc35787de3611b5809cbafdb
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
<<<<<<< HEAD

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
=======
>>>>>>> b0d836b864d124b5dc35787de3611b5809cbafdb

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *This GUI will create a large JPanel that will hold all the panels, dropdowns, etc
 *Structure:
 *	JFrame Gui 
 *		Day/Month/Year Dropdowns + Go Button
 *		Header Panel
 *		All Scores Panel - refreshes each time a new date is selected
 *			Contains 3 sections: AL/NL/ Interleague
 *				Each Section holds all the relevant games
 *		Indiv Game Panel - refreshes each time a new game is selected
 *			When a game is selected, it shows the full 9 inning plue RHE for each team
 *++++++++++++++++++++++++++++++++++
 *+         dropdown1    | dropdown2 +
 *+      |       |       |           +
 *+ AL   | NL    | IL    |Game Range +
 *+      |       |       |           +
 *+                                  +
 *+           Detailed Game          +
 *+                                  +
 *++++++++++++++++++++++++++++++++++++
 *
 * @author Ben Muirhead
 *
 */
<<<<<<< HEAD
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
		List<Game> selectedGames = selectedGameDay.getNationalGames();
		List<JPanel> selectedGamesPanels = new ArrayList<JPanel>();

		for (Game g : selectedGames) {
			selectedGamesPanels.add(g.drawBasicScore());
		}

		
		GridLayout grid = new GridLayout(selectedGamesPanels.size(), 1);
		scoresPanel.setLayout(grid);

		for (JPanel panel : selectedGamesPanels) {
			scoresPanel.add(panel);
		}

		scoresPanel.setSize(windowX, 400);

		return scoresPanel;
=======
@SuppressWarnings("serial")
public class Gui extends JFrame {
	// window size
	private static int windowX = 1400;
	private static int windowY = 1000;

	private static int ddPanelHeight = 50;
	private static int gamePanelHeight = 300;
	public static String selectedDate;
	static Calendar date = Utility.convertDateToCalendar(2015, 4, 20);
	// static Calendar date2 = Utility.convertDateToCalendar(2015, 4, 22);

	GameDay[] AprilGames;
	// static JFrame MLBFrame;
	static JPanel scoresPanel;
	static JPanel gamePanel;
	static JPanel ddPanel;
	static JPanel selectTeamPanel;
	static JPanel gPanel;
	static JPanel gameRangePanel;
	public static JComboBox<String> dayDropdown;
	public static JComboBox<String> monthDropdown;
	public static JComboBox<String> yearDropdown;
	public static JComboBox<String> teamsDropdown;
	String[] days = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
			"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
			"23", "24", "25", "26", "27", "28", "29", "30", "31" };
	String[] months = { "Jan", "Feb", "March", "Apr", "May", "Jun", "Jul",
			"Aug", "Sep", "Oct", "Nov", "Dec" };
	String[] years = { "2015", "2014", "2013"};//, "2012", "2011", "2010" };
	String[] abbreviationKey = { "angels", "diamondBacks", "braves", "orioles",
			"redSox", "whiteSox", "cubs", "reds", "indians", "rockies",
			"tigers", "astros", "royals", "dodgers", "marlins", "brewers",
			"twins", "yankees", "mets", "athletics", "phillies", "pirates",
			"padres", "mariners", "giants", "cardinals", "rays", "rangers",
			"blueJays", "nationals" };
	String[] cityKey = { "Los Angeles", "Arizona", "Atlanta", "Baltimore",
			"Boston", "Chicago", "Chicago", "Cincinati", "Cleveland",
			"Colorado", "Detroit", "Houston", "Kansas City", "Los Angeles",
			"Miami", "Milwaukee", "Minnesota", "New York", "New York",
			"Oakland", "Philidelphia", "Pittsburgh", "San Diego", "Seattle",
			"San Francisco", "St. Louis", "Tampa Bay", "Texas", "Toronto",
			"Washington" };
	String[] nameKey = { "Angels", "Diamondbacks", "Braves", "Orioles",
			"Red Sox", "White Sox", "Cubs", "Reds", "Indians", "Rockies",
			"Tigers", "Astros", "Royals", "Dodgers", "Marlins", "Brewers",
			"Twins", "Yankees", "Mets", "Athletics", "Phillies", "Pirates",
			"Padres", "Mariners", "Giants", "Cardinals", "Rays", "Rangers",
			"Blue Jays", "Nationals" };
	String[] teams = { "Arizona Diamondbacks", "Atlanta Braves",
			"Baltimore Orioles", "Boston Red Sox", "Chicago White Sox",
			"Chicago Cubs", "Cincinati Reds", "Cleveland Indians",
			"Colorado Rockies", "Detroit Tigers", "Houston Astros",
			"Kansas City Royals", "Los Angeles Dodgers", "Miami Marlins",
			"Milwaukee Brewers", "Minnesota Twins", "New York Yankees",
			"New York Mets", "Oakland Athletics", "Philidelphia Phillies",
			"Pittsburgh Pirates", "San Diego Padres", "Seattle Mariners",
			"San Francisco Giants", "St. Louis Cardinals", "Tampa Bay Rays",
			"Texas Rangers", "Toronto Blue Jays", "Washington Nationals" };

	JButton goButton;
	GameDay selectedGameDay = new GameDay(date); // Creates Default GameDay
	String team = Team.giants;// "San Francisco Giants"; // Creates initial team

	public Gui() {
		System.out.println("Gui()");
		this.init();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void init() {
		System.out.println("init()");

		this.setTitle("MLB Scores");
		// use grid bag layout
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		// day/month/year dropdown
		ddPanel = dropdownPanel();
		c.gridx = 0;
		c.gridy = 0;
		this.getContentPane().add(ddPanel, c);
		System.out.println("ddPanel added");

		// Holds all games for a given date
		scoresPanel = scoresPanel(date);
		c.gridx = 0;
		c.gridy = 1;
		this.getContentPane().add(scoresPanel, c);
		System.out.println("scoresPanel added");

		// select team dropdown
		selectTeamPanel = selectTeamPanel();
		c.gridx = 1;
		c.gridy = 0;
		this.getContentPane().add(selectTeamPanel, c);
		System.out.println("selectTeamPanel added");

		// Holds all games for one team
		gameRangePanel = buildGameRangePanel(team);
		c.gridx = 1;
		c.gridy = 1;
		this.getContentPane().add(gameRangePanel, c);

		// Holds detailed view of one game
		gamePanel = gamePanel(selectedGameDay.getGames().get(0));
		c.gridx = 0;
		c.gridy = 2;
		this.getContentPane().add(gamePanel, c);
		System.out.println("gamePanel added");

		// Select team dropdown

		this.setBounds(0, 0, windowX, windowY);
		this.getContentPane().setBounds(0, 0, windowX, windowY);

		this.setVisible(true);

	}

	private JPanel buildGameRangePanel(String team) {
		gameRangePanel = new JPanel();

		Calendar startDate = Utility.convertDateToCalendar(2015, 4, 28);
		Calendar endDate = Utility.convertDateToCalendar(2015, 5, 3);
		GameRange gameRange = new GameRange(team, startDate, endDate);

		List<Game> gameRangeList = gameRange.getGames();
		List<JPanel> gameRangePanels = new ArrayList<JPanel>();

		JPanel newGamePanel;// temp var that holds newly created panels
		System.out.println(gameRangeList.get(0).getClass());
		// newGamePanel = gameRangeList.get(0).drawBasicScoreWithDate();
		// gameRangePanel.add(newGamePanel);
		for (Game g : gameRangeList) {
			System.out.println(g.getAwayTeam());
			newGamePanel = g.drawBasicScoreWithLogosAndDate();
			newGamePanel.addMouseListener(new mouseListener2(this));
			gameRangePanels.add(newGamePanel);
		}

		GridLayout grid = new GridLayout(gameRangePanels.size(), 1);
		//
		for (JPanel panel : gameRangePanels) {

			gameRangePanel.add(panel);
		}
		gameRangePanel.setLayout(grid);

		// TODO Auto-generated method stub
		//gameRangePanel.setMinimumSize(new Dimension(400,300));
		gameRangePanel.setPreferredSize(new Dimension(400,300));
		return gameRangePanel;
	}

	@SuppressWarnings("null")
	private JPanel selectTeamPanel() {
		System.out.println("selectTeamsPanel started");
		selectTeamPanel = new JPanel();

		teamsDropdown = new JComboBox<String>(teams);

		System.out.println("JCombo box created");
		teamsDropdown.addActionListener(new dropdownListener(this));

		selectTeamPanel.setMaximumSize(new Dimension(200, ddPanelHeight));

		selectTeamPanel.add(teamsDropdown);

		selectTeamPanel
				.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));

		return selectTeamPanel;

	}

	/**
	 * @return gPanel
	 */
	private JPanel gamePanel(Game selectedGame) {
		// Game selectGame = selectedGameDay.getGames().get(0);
		gPanel = new JPanel();
		// gPanel.setBounds(0, 200, 300, 100);
//		gPanel.setMinimumSize(new Dimension(windowX - 800, gamePanelHeight / 2));
//		gPanel.setPreferredSize(new Dimension(windowX - 500, gamePanelHeight));
		gPanel.setMaximumSize(new Dimension(windowX -800, gamePanelHeight));
		gPanel.add(selectedGame.drawDetailed());

		// String[] dates = { "April 1, 2015", "July 2, 2015", "April 3, 2015",
		// "April 4, 2015" };
		// JComboBox<String> defaultDropdown = new JComboBox<String>(dates);

		// gPanel.add(defaultDropdown);

		gPanel.setBorder(BorderFactory.createLineBorder(Color.blue));

		return gPanel;
	}

	/**
	 * Holds 3 ComboBoxes, for Day, Month, and Year, and Go Button
	 * @return ddPanel
	 */
	public JPanel dropdownPanel() {
		ddPanel = new JPanel();

		dayDropdown = new JComboBox<String>(days);
		monthDropdown = new JComboBox<String>(months);
		yearDropdown = new JComboBox<String>(years);
		dayDropdown.setSelectedIndex(19);// 21st
		monthDropdown.setSelectedIndex(3);// April
		goButton = new JButton("Go");
		goButton.addActionListener(new buttonListener(this));

		ddPanel.setPreferredSize(new Dimension(800, ddPanelHeight));

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
	 * @param inputDate 
	 * @return scorePanel
	 */
	public JPanel scoresPanel(Calendar inputDate) {

		System.out.println("sP:" + Utility.convertCalendarToDate(date)[1] + "/"
				+ Utility.convertCalendarToDate(date)[2] + "/"
				+ Utility.convertCalendarToDate(date)[0]);
		JPanel scorePanel = new JPanel();
		System.out.println("scorePanel made");

		GameDay selectedGameDay = new GameDay(inputDate);

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

		ALPanel.setBorder(BorderFactory
				.createTitledBorder("American League Games"));
		NLPanel.setBorder(BorderFactory
				.createTitledBorder("National League Games"));
		interPanel.setBorder(BorderFactory
				.createTitledBorder("Inter-League Games"));

		GridLayout grid = new GridLayout(Math.max(selectedALGames.size(),
				Math.max(selectedNLGames.size(), selectedInterGames.size())), 1);
		JPanel newGamePanel;
		// AL
		for (Game g : selectedALGames) {
			newGamePanel = g.drawBasicScoreWithLogos();
			newGamePanel.addMouseListener(new mouseListener(this));
			ALGamesP.add(newGamePanel);
		}
		for (JPanel panel : ALGamesP) {
			ALPanel.add(panel);
		}

		ALPanel.setLayout(grid);
		scorePanel.add(ALPanel);

		// NL
		for (Game g : selectedNLGames) {
			newGamePanel = g.drawBasicScoreWithLogos();
			newGamePanel.addMouseListener(new mouseListener(this));
			NLGamesP.add(newGamePanel);
		}
		for (JPanel panel : NLGamesP) {
			NLPanel.add(panel);
		}

		NLPanel.setLayout(grid);
		scorePanel.add(NLPanel);

		// Interleague
		for (Game g : selectedInterGames) {
			newGamePanel = g.drawBasicScoreWithLogos();
			newGamePanel.addMouseListener(new mouseListener(this));
			interGamesP.add(newGamePanel);
		}
		for (JPanel panel : interGamesP) {
			interPanel.add(panel);
		}

		interPanel.setLayout(grid);
		scorePanel.add(interPanel);

		GridLayout scoresPanelgrid = new GridLayout(1, 3);
		scorePanel.setLayout(scoresPanelgrid);
		// scorePanel.setBounds(0, ddPanelHeight, 700, 400);
		System.out.println("return scorePanel");
		scorePanel.setMinimumSize(new Dimension(windowX - 500, 300));
		scorePanel.setPreferredSize(new Dimension(windowX - 500, 600));
		return scorePanel;
		//TODO
	}

	/**
	 * 
	 */
	public void goButtonPressed() {
		System.out.println("Go Button Pressed");
		int day = Gui.dayDropdown.getSelectedIndex() + 1;
		int month = Gui.monthDropdown.getSelectedIndex() + 1;
		int year = 2015 - Gui.yearDropdown.getSelectedIndex();
		System.out.println(month + "/" + day + "/" + year);
		Calendar newDate = Utility.convertDateToCalendar(year, month, day);
		date = newDate;

		System.out.println("ButtonPressed:"
				+ Utility.convertCalendarToDate(date)[1] + "/"
				+ Utility.convertCalendarToDate(date)[2] + "/"
				+ Utility.convertCalendarToDate(date)[0]);

		this.getContentPane().remove(scoresPanel);
		this.getContentPane().remove(gamePanel);

		scoresPanel = scoresPanel(date);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		this.getContentPane().add(scoresPanel, c);
		c.gridx = 0;
		c.gridy = 2;
		this.getContentPane().add(gamePanel, c);
		this.getContentPane().revalidate();
		this.getContentPane().setVisible(true);
	}

	public void newDetailGame(Game newGame) {
		System.out.println("new Game Selected");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		this.getContentPane().remove(gamePanel);
		gamePanel = gamePanel(newGame);
		this.getContentPane().add(gamePanel, c);
		this.getContentPane().revalidate();
		this.getContentPane().setVisible(true);
	}

	public void newTeamSelected(String fullNewTeamName) {

		System.out.println("fullNameNewTeam: " + fullNewTeamName);
		int index = 0;
		for (int i = 0; i < abbreviationKey.length - 1; i++) {
			System.out.println(teams[i]);
			if (fullNewTeamName.equals(teams[i])) {
				index = i;
				System.out.println(index);
				break;
			}
		}
		String newTeam = Team.abbreviationKey[index + 1];
		System.out.println(newTeam);

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		this.getContentPane().remove(gameRangePanel);
		gameRangePanel = buildGameRangePanel(newTeam);
		this.getContentPane().add(gameRangePanel, c);
		this.getContentPane().revalidate();
		this.getContentPane().setVisible(true);

>>>>>>> b0d836b864d124b5dc35787de3611b5809cbafdb
	}
}
