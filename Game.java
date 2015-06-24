import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import java.awt.Color;

/**
 * A class which holds the data for a given game in the form of a URL on the MLB API
 * @author Samuel Doud
 * @author Ben Muirhead
 *
 */
public class Game {

	private Team homeTeam, awayTeam;// home teams are last in the URL
	private int[] homeScoreInning;
	private int[] awayScoreInning;
	private URL base;
	private URL lineScoreURL;
	private String lineScoreXML;
	private String venue;
	private String gameURL;

	private final static String homeInningKey = "home_inning_runs";
	private final static String awayInningKey = "away_inning_runs";
	private final static String homeRunsKey = "home_team_runs";
	private final static String awayRunsKey = "away_team_runs";
	private final static String homeHitsKey = "home_team_hits";
	private final static String awayHitsKey = "away_team_hits";
	private final static String homeErrorsKey = "home_team_errors";
	private final static String awayErrorsKey = "away_team_errors";
	private final static String currentInningKey = " inning";
	private final static String statusKey = "status";
	private final static String inningSideKey = "top_key";
	private final static String locationKey = "venue";

	private Calendar myDate;

	public final int runsIndex = 10;
	public final int hitsIndex = runsIndex + 1;
	public final int errorsIndex = runsIndex + 2;

	/**
	 * Creates a game object by parsing a URL where the game's data is located
	 * Then constructor delegates to other methods
	 */
	public Game(String gameURL) {
		this.gameURL = gameURL;
		System.out.println();
		System.out.print("    Creating Game");
		try {
			base = new URL(gameURL);
			lineScoreURL = new URL(gameURL + "linescore.xml");
		} catch (MalformedURLException e) {
			System.out.println("error in Game constructor");
		}
		createTeams();
		getLineScore();
		fixVenue();
		setDate();

	}

	/**
	 * Creates the teams based on the URL
	 */
	private void createTeams() {
		int indexAway = 80;// all urls are the same so a static location can be
							// used
		int indexHome = indexAway + 7;
		int length = 3;
		System.out.println();

		System.out.print("      base:  " + base);
		System.out.print(base.toString().substring(indexAway,
				indexAway + length));
		System.out.println();
		System.out.print("      begin awayTeam:  ");
		awayTeam = new Team(base.toString().substring(indexAway,
				indexAway + length));
		System.out.print("  awayTeam made");

		System.out.println();
		System.out.print("      begin homeTeam:  ");
		homeTeam = new Team(base.toString().substring(indexHome,
				indexHome + length));
		System.out.println("  homeTeam made");
	}

	private void getLineScore() {
		InputStream input;
		BufferedReader reader;
		String temp;
		try {
			input = lineScoreURL.openStream();
			reader = new BufferedReader(new InputStreamReader(input));

			while ((temp = reader.readLine()) != null) {
				lineScoreXML += temp;
			}
			input.close();
			reader.close();
		} catch (IOException e) {
			System.out.println("error in Game.getLineScore()");
		}
		determineLineScore();

	}

	/**
	 * Determines the linescore for a game
	 * {inning 1 score, ..., inning 10 score, runs, hits, errors}
	 */
	private void determineLineScore() {
		homeScoreInning = new int[13]; // ten innings plus RHE
		awayScoreInning = new int[13];
		// Get inning by inning score
		// sample inning score <linescore inning="1" home_inning_runs="0"
		// away_inning_runs="1"/>
		// method find "<linescore inning="XXXX"....../>
		int currentInning = 1;
		int currentIndex;
		String copyOfLineScore = lineScoreXML;
		String key = "<linescore inning=\"";
		String currentInningText = "";
		while (currentInning <= 10) {
			currentIndex = copyOfLineScore.indexOf(key + currentInning + "\"");
			if (currentIndex > 0)// the inning happened. Current Index is not -1
			{
				while (copyOfLineScore.charAt(currentIndex) != '/') {
					currentInningText = currentInningText
							+ copyOfLineScore.charAt(currentIndex);
					currentIndex++;
				}

				homeScoreInning[currentInning - 1] = Integer
						.parseInt(Utility.JSONValueReader(currentInningText,
								Game.homeInningKey));
				awayScoreInning[currentInning - 1] = Integer
						.parseInt(Utility.JSONValueReader(currentInningText,
								Game.awayInningKey));
			}
			currentInning++;
			currentInningText = "";

		}

		homeScoreInning[runsIndex] = Integer.parseInt(Utility.JSONValueReader(
				copyOfLineScore, Game.homeRunsKey));
		awayScoreInning[runsIndex] = Integer.parseInt(Utility.JSONValueReader(
				copyOfLineScore, Game.awayRunsKey));
		homeScoreInning[hitsIndex] = Integer.parseInt(Utility.JSONValueReader(
				copyOfLineScore, Game.homeHitsKey));
		awayScoreInning[hitsIndex] = Integer.parseInt(Utility.JSONValueReader(
				copyOfLineScore, Game.awayHitsKey));
		homeScoreInning[errorsIndex] = Integer.parseInt(Utility
				.JSONValueReader(copyOfLineScore, Game.homeErrorsKey));
		awayScoreInning[errorsIndex] = Integer.parseInt(Utility
				.JSONValueReader(copyOfLineScore, Game.awayErrorsKey));

	}

	/**
	 * 
	 * @return
	 */
	public int[] getLine() {
		int[] combinedLine = new int[homeScoreInning.length
				+ awayScoreInning.length];
		// make an array of double the size
		// away team occupies first slots
		for (int i = 0; i < homeScoreInning.length; i++) {
			combinedLine[i] = awayScoreInning[i];
			combinedLine[i + homeScoreInning.length] = homeScoreInning[i];
		}
		return combinedLine;
	}

	public String toString() {
		String text = venue + "\n";

		text = text + awayTeam.getCity() + " " + awayTeam.getName() + "\t";
		for (int i : awayScoreInning) {
			text = text + i + " ";
		}
		text = text + getInning() + "\n";
		text = text + homeTeam.getCity() + " " + homeTeam.getName() + "\t";
		for (int i : homeScoreInning) {
			text = text + i + " ";
		}

		return text;
	}

	/**
	 * get the inning currently being played
	 * useful if the game is final
	 * @return
	 */
	public String getInning() {
		String text = "";
		if (Utility.JSONValueReader(lineScoreXML, Game.statusKey).equals(
				"Final")) {// game is complete
			text = "F";
		} else {
			if (Utility.JSONValueReader(lineScoreXML, Game.statusKey).equals(
					"Pre-Game")) {
				text = "P";
			} else {// game is in progress
				text = Utility.JSONValueReader(lineScoreXML,
						Game.currentInningKey);
				if (Utility.JSONValueReader(lineScoreXML, Game.inningSideKey)
						.equals("Y")) {
					text = text + "top";
				} else {
					text = text + "bottom";
				}
			}
		}
		return text;
	}

	/**
	 * Draws a basic score
	 * Name and score
	 * winner is bolded
	 * @return
	 */
	public JPanel drawBasicScoreWithDate() {
		// creates a date of format MM/DD/YY
		String dateFormat = myDate.get(Calendar.MONTH) + "/"
				+ myDate.get(Calendar.DAY_OF_MONTH) + "/"
				+ myDate.get(Calendar.YEAR) % 100;
		JLabel dateLabel = new JLabel(dateFormat);

		// the fonts of the winners and losers
		Font winner = new Font("", Font.BOLD, 12);
		Font loser = new Font("", Font.PLAIN, 12);
		JPanel basicScorePanel = new JPanel();
		GridLayout grid = new GridLayout(3, 1);
		basicScorePanel.setLayout(grid);

		basicScorePanel.add(dateLabel);

		// Logic to set the team names to a length of twelve chars
		String homeName = homeTeam.getName();
		String awayName = awayTeam.getName();

		for (int i = 0; i <= 12; i++) {
			// is the iterator at the length of the String?
			if (i >= homeName.length()) {
				homeName = homeName + " ";// add a space
			}
			if (i >= awayName.length()) {
				awayName = awayName + " ";// add a space
			}
		}

		// Create a label of the team name and score
		JLabel homeLabel = new JLabel(homeName + homeScoreInning[runsIndex]);
		JLabel awayLabel = new JLabel(awayName + awayScoreInning[runsIndex]);
		// Determine winner (or leader, if tied, no bold) winner is bolded
		if (homeScoreInning[runsIndex] > awayScoreInning[runsIndex]) {
			homeLabel.setFont(winner);
			awayLabel.setFont(loser);
		}
		if (homeScoreInning[runsIndex] < awayScoreInning[runsIndex]) {
			awayLabel.setFont(winner);
			homeLabel.setFont(loser);
		}
		basicScorePanel.add(awayLabel);// add the labels
		basicScorePanel.add(homeLabel);
<<<<<<< HEAD
		
		basicScorePanel.setSize(85, 30);//give the panel n arbitrary size
		basicScorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));//put a border around the panel
=======
		basicScorePanel.setName(gameURL);
		basicScorePanel.setSize(85, 30);// give the panel n arbitrary size
		basicScorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));// put
																				// a
																				// border
																				// around
																				// the
																				// panel
>>>>>>> b0d836b864d124b5dc35787de3611b5809cbafdb
		return basicScorePanel;
	}

	/**
	 * Create a panel with the teams and their scores
	 * Bold the text of the winner
	 * @return
	 */
	public JPanel drawBasicScore() {
		// the fonts the winners and losers will use
		Font winner = new Font("", Font.BOLD, 12);
		Font loser = new Font("", Font.PLAIN, 12);

		JPanel basicScorePanel = new JPanel();
		GridLayout grid = new GridLayout(2, 1);
		basicScorePanel.setLayout(grid);
		// Logic to set the team names to a length of twelve chars
		String homeName = homeTeam.getName();
		String awayName = awayTeam.getName();

		for (int i = 0; i <= 12; i++) {
			// is the iterator at the length of the String?
			if (i >= homeName.length()) {
				homeName = homeName + " ";// add a space
			}
			if (i >= awayName.length()) {
				awayName = awayName + " ";// add a space
			}
		}

		// Create a label of the team name and score
		JLabel homeLabel = new JLabel(homeName + homeScoreInning[runsIndex]);
		JLabel awayLabel = new JLabel(awayName + awayScoreInning[runsIndex]);
		// Determine winner (or leader, if tied, no bold) and bold the winner's
		// text
		if (homeScoreInning[runsIndex] > awayScoreInning[runsIndex]) {
			homeLabel.setFont(winner);
			awayLabel.setFont(loser);
		}
		if (homeScoreInning[runsIndex] < awayScoreInning[runsIndex]) {
			awayLabel.setFont(winner);
			homeLabel.setFont(loser);
		}
		basicScorePanel.add(awayLabel);// add the created score labels to the
										// panel
		basicScorePanel.add(homeLabel);
		basicScorePanel.setName(gameURL);
		basicScorePanel.setSize(85, 30);// give the panel an arbitaray size
		basicScorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));// put
																				// a
																				// border
																				// around
																				// the
																				// panel
		return basicScorePanel;
	}

	public JPanel drawBasicScoreWithLogos() {
		// the fonts the winners and losers will use
		Font winner = new Font("", Font.BOLD, 16);
		Font loser = new Font("", Font.PLAIN, 16);

		JPanel basicScorePanel = new JPanel();
		GridLayout grid = new GridLayout(2, 2);
		basicScorePanel.setLayout(grid);
		// Logic to set the team names to a length of twelve chars
		String homeName = homeTeam.getName();
		String awayName = awayTeam.getName();

		for (int i = 0; i <= 12; i++) {
			// is the iterator at the length of the String?
			if (i >= homeName.length()) {
				homeName = homeName + " ";// add a space
			}
			if (i >= awayName.length()) {
				awayName = awayName + " ";// add a space
			}
		}

		// Create a label of the team name and score
		JLabel homeLabel = new JLabel(homeName + homeScoreInning[runsIndex]);
		JLabel awayLabel = new JLabel(awayName + awayScoreInning[runsIndex]);
		// Determine winner (or leader, if tied, no bold) and bold the winner's
		// text
		if (homeScoreInning[runsIndex] > awayScoreInning[runsIndex]) {
			homeLabel.setFont(winner);
			awayLabel.setFont(loser);
		}
		if (homeScoreInning[runsIndex] < awayScoreInning[runsIndex]) {
			awayLabel.setFont(winner);
			homeLabel.setFont(loser);
		}
		// creat logo to add in front of team name
		JLabel homeTeamLogo = new JLabel(homeTeam.getLogo());
		JLabel awayTeamLogo = new JLabel(awayTeam.getLogo());
		// homeTeamLogo.setPreferredSize(new Dimension(100,100));
		// awayTeamLogo.setPreferredSize(new Dimension(50,100));

		// add the created labels and logos back to the panel
		basicScorePanel.add(awayTeamLogo);
		basicScorePanel.add(awayLabel);
		basicScorePanel.add(homeTeamLogo);
		basicScorePanel.add(homeLabel);
		basicScorePanel.setName(gameURL);
		basicScorePanel.setSize(85, 30);// give the panel an arbitaray size
		basicScorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));// put
																				// a
																				// border
																				// around
																				// the
																				// panel
		return basicScorePanel;
	}

	public JPanel drawBasicScoreWithLogosAndDate() {
		// creates a date of format MM/DD/YY
		String dateFormat = myDate.get(Calendar.MONTH) + "/"
				+ myDate.get(Calendar.DAY_OF_MONTH) + "/"
				+ myDate.get(Calendar.YEAR) % 100;
		JLabel dateLabel = new JLabel(dateFormat);
		// the fonts the winners and losers will use
		Font winner = new Font("", Font.BOLD, 16);
		Font loser = new Font("", Font.PLAIN, 16);

		JPanel basicScorePanel = new JPanel();
		GridLayout grid = new GridLayout(3, 2);
		
		basicScorePanel.setLayout(grid);
		
		basicScorePanel.add(dateLabel);
		
		//fill extra slot
		JPanel fillPanel = new JPanel();
		basicScorePanel.add(fillPanel);
		
		// Logic to set the team names to a length of twelve chars
		String homeName = homeTeam.getName();
		String awayName = awayTeam.getName();

		for (int i = 0; i <= 12; i++) {
			// is the iterator at the length of the String?
			if (i >= homeName.length()) {
				homeName = homeName + " ";// add a space
			}
			if (i >= awayName.length()) {
				awayName = awayName + " ";// add a space
			}
		}

		// Create a label of the team name and score
		JLabel homeLabel = new JLabel(homeName + homeScoreInning[runsIndex]);
		JLabel awayLabel = new JLabel(awayName + awayScoreInning[runsIndex]);
		// Determine winner (or leader, if tied, no bold) and bold the winner's
		// text
		if (homeScoreInning[runsIndex] > awayScoreInning[runsIndex]) {
			homeLabel.setFont(winner);
			awayLabel.setFont(loser);
		}
		if (homeScoreInning[runsIndex] < awayScoreInning[runsIndex]) {
			awayLabel.setFont(winner);
			homeLabel.setFont(loser);
		}
		// creat logo to add in front of team name
		JLabel homeTeamLogo = new JLabel(homeTeam.getLogo());
		JLabel awayTeamLogo = new JLabel(awayTeam.getLogo());
		// homeTeamLogo.setPreferredSize(new Dimension(100,100));
		// awayTeamLogo.setPreferredSize(new Dimension(50,100));

		// add the created labels and logos back to the panel
		basicScorePanel.add(awayTeamLogo);
		basicScorePanel.add(awayLabel);
		basicScorePanel.add(homeTeamLogo);
		basicScorePanel.add(homeLabel);
		basicScorePanel.setName(gameURL);
		basicScorePanel.setSize(85, 60);// give the panel an arbitaray size
		basicScorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));// put
																				// a
																				// border
																				// around
																				// the
																				// panel
		return basicScorePanel;
	}

	/**
	 * Gives a detailed description of the game
	 * TODO a batting list
	 * @return
	 */
	public JPanel drawDetailed() {
		JPanel detailedGame = new JPanel();
		GridLayout layout = new GridLayout(2, 1);// currently two objects will
													// exist on the Panel
		detailedGame.setLayout(layout);

		// detailedGame.setLayout(new BoxLayout(detailedGame,
		// BoxLayout.Y_AXIS));
		detailedGame.add(this.drawLogoBar());// adds the logo bar
		detailedGame.add(this.drawLineScore());// adds the line score
		detailedGame.setToolTipText(this.venue);// the toolTip describes the
												// park the game is being played
												// at

		return detailedGame;
	}

	/**
	 * Creates a logo JPanel
	 * 1x3 grid displaying both logos and a description of the game
	 */
	private JPanel drawLogoBar() {
		JPanel logoBar = new JPanel();
		GridLayout logoSpliter = new GridLayout(1, 3);
		logoBar.setLayout(logoSpliter);

		JLabel description = new JLabel(awayTeam.getCity() + " "
				+ awayTeam.getName() + " at the " + homeTeam.getCity() + " "
				+ homeTeam.getName());
		Font descriptionFont = new Font("", Font.BOLD, 17);
		description.setFont(descriptionFont);

		JLabel homeTeamLogo = new JLabel(homeTeam.getLogo());
		JLabel awayTeamLogo = new JLabel(awayTeam.getLogo());

		logoBar.add(awayTeamLogo);
		logoBar.add(description);
		logoBar.add(homeTeamLogo);
		logoBar.setBorder(BorderFactory.createLineBorder(Color.green));
		logoBar.setMaximumSize(new Dimension(1000, 500));
		return logoBar;
	}

	/**
	 * Draws a line Score
	 * @param currentGame the game which is to be drawn
	 * @return a JScroll with the line score
	 */
	private JScrollPane drawLineScore() {
		final int AWAY = 0;
		final int HOME = 1;
		final int NAME = 0;
		JTable lineTable;

		// Create Table Label
		String[] tableLabels = new String[14];
		tableLabels[NAME] = "Teams";
		tableLabels[tableLabels.length - 3] = "R";
		tableLabels[tableLabels.length - 2] = "H";
		tableLabels[tableLabels.length - 1] = "E";

		// Sets the innings labels
		for (int i = 1; i <= 10; i++) {
			tableLabels[i] = ((i) + "");
		}

		Object[][] data = new Object[2][tableLabels.length];

		// Create away and home team data
		data[AWAY][NAME] = this.awayTeam.getCity() + " "
				+ this.awayTeam.getName();
		data[HOME][NAME] = this.homeTeam.getCity() + " "
				+ this.homeTeam.getName();

		for (int i = 0; i < homeScoreInning.length; i++) {
			data[AWAY][i + tableLabels.length - awayScoreInning.length] = awayScoreInning[i];// places
																								// the
																								// inning
																								// data
																								// in
																								// the
																								// correct
																								// data
																								// array
																								// cell
			data[HOME][i + tableLabels.length - homeScoreInning.length] = homeScoreInning[i];
		}

		// Create a JTable of data with headers tableLabels
		lineTable = new JTable(data, tableLabels);
		// set the preferred width of the columns to the variable width
		int width = 20;
		TableColumn tempCol = null;
		for (int i = 0; i < data[0].length; i++) {
			tempCol = lineTable.getColumnModel().getColumn(i);// gets the column
																// i
			tempCol.setPreferredWidth(width);
			tempCol.setResizable(false);// set the column so it cannot be
										// resized

		}
		// set the column of the team name to a size that can fit the name (four
		// times the normal width)
		lineTable.getColumnModel().getColumn(NAME).setPreferredWidth(width * 8);

		JScrollPane scroll = new JScrollPane(lineTable);
		// scroll.setSize(500, 100);
		scroll.setBorder(BorderFactory.createLineBorder(Color.red));
		Font tablefont = new Font("", Font.PLAIN, 16);
		scroll.setFont(tablefont);
		scroll.setPreferredSize(new Dimension(500, 100));
		return scroll;
		/*
		 * JPanel homePanel = new JPanel(); JPanel awayPanel = new JPanel();
		 * 
		 * //A 3x1 gridlayout of a flow panel //the flow houses the individual
		 * game GridLayout combinedLineLayout = new GridLayout(3,2); FlowLayout
		 * homeLineLayout = new FlowLayout(); FlowLayout awayLineLayout = new
		 * FlowLayout();
		 * 
		 * homePanel.setLayout(homeLineLayout);
		 * awayPanel.setLayout(awayLineLayout);
		 * basicScore.setLayout(combinedLineLayout);
		 * 
		 * Icon homeLogo = this.getHomeTeam().getLogo(); Icon awayLogo =
		 * this.getAwayTeam().getLogo();
		 * 
		 * 
		 * int[] combinedLine = this.getLine();//gets the line from the Game
		 * 
		 * String homeTeamScores = ""; String awayTeamScores = ""; String
		 * lineScoreTopLine =
		 * "                                   1  2  3  4  5  6  7  8  9 10 R  H  E"
		 * ;
		 * 
		 * //Set lineScore data
		 * 
		 * for (int i = 0; i < combinedLine.length / 2; i++) {//go through the
		 * array totally by using two seperate halves int tempScore =
		 * combinedLine[i]; awayTeamScores = awayTeamScores + combinedLine[i];
		 * if (tempScore < 10)//the score is a single digit { awayTeamScores =
		 * awayTeamScores + "  "; //needs a space to even it out with double
		 * digits } //awayTeam info is first, homeTeam follows i + half of array
		 * length tempScore = combinedLine[i + combinedLine.length / 2];
		 * homeTeamScores = homeTeamScores + combinedLine[i +
		 * combinedLine.length / 2]; if (tempScore < 10)//the score is a single
		 * digit { homeTeamScores = homeTeamScores + "  "; //needs a space to
		 * even it out with double digits } }
		 * 
		 * String homeTeamString = this.getHomeTeam().getCity() + " "+
		 * this.getHomeTeam().getName(); String awayTeamString =
		 * this.getAwayTeam().getCity() + " "+ this.getAwayTeam().getName();
		 * 
		 * //Make the strings of equal length. The Phillies have the longest
		 * name //at 21 characters
		 * 
		 * for (int i = homeTeamString.length(); i <
		 * "Philidelphia Phillies".length(); i++) { homeTeamString =
		 * homeTeamString + " ";//add a blank to the end } for (int i =
		 * awayTeamString.length(); i < "Philidelphia Phillies".length(); i++) {
		 * awayTeamString = awayTeamString + " "; } awayTeamString =
		 * awayTeamString + "\t"; homeTeamString = homeTeamString + "\t";
		 * //makes a label of the top line JLabel innings = new
		 * JLabel(lineScoreTopLine); //the two lines below make a label of the
		 * line of a team by combining their name and score. //attaches logo to
		 * the front JLabel homeTeamLabel = new JLabel( homeTeamString + " " +
		 * homeTeamScores); JLabel awayTeamLabel = new JLabel(awayTeamString +
		 * " " + awayTeamScores);
		 * 
		 * JLabel awayLogoLabel = new JLabel(awayLogo); JLabel homeLogoLabel =
		 * new JLabel(homeLogo); //Basic Score tooltip displays park
		 * basicScore.setToolTipText(this.getVenue());
		 * 
		 * homePanel.add(homeTeamLabel);//adds the home Team Label to the panel
		 * awayPanel.add(awayTeamLabel);//same as above for away Team
		 * 
		 * 
		 * //TODO turn this into a table basicScore.add(new JLabel());
		 * basicScore.add(innings); basicScore.add(awayLogoLabel);
		 * basicScore.add(awayTeamLabel); basicScore.add(homeLogoLabel);
		 * basicScore.add(homeTeamLabel);
		 */
	}

	/**
	 * Is the game between two American league teams?
	 * @return
	 */
	public boolean isAmericanLeague() {
		return (homeTeam.isAmericanLeague() && awayTeam.isAmericanLeague());
	}

	/** Is the game between two National league teams?
	* @return
	*/
	public boolean isNationalLeague() {
		return (homeTeam.isNationalLeague() && awayTeam.isNationalLeague());
	}

	/**
	 * Is the game interLeague?
	 * @return
	 */
	public boolean isInterLeague() {
		return !(isNationalLeague() || isAmericanLeague());
	}

	private void setDate() {
		String year = "year_";
		String month = "month_";
		String day = "day_";
		String temp;
		int index;
		int tempNum;
		myDate = new GregorianCalendar();
		temp = base.toString();// sets the URL to temp

		index = temp.indexOf(year) + year.length();
		tempNum = Integer.parseInt(temp.substring(index, index + 4));
		myDate.set(Calendar.YEAR, tempNum);

		index = temp.indexOf(month) + month.length();
		tempNum = Integer.parseInt(temp.substring(index, index + 2));
		myDate.set(Calendar.MONTH, tempNum);

		index = temp.indexOf(day) + day.length();
		tempNum = Integer.parseInt(temp.substring(index, index + 2));
		myDate.set(Calendar.DAY_OF_MONTH, tempNum);
	}

	/**
	 * Takes the location of the game
	 * IE Fenway Park
	 */
	private void fixVenue() {
		venue = Utility.JSONValueReader(lineScoreXML, locationKey);
	}

	public String getVenue() {
		return venue;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public static JScrollPane putInScrollPane(List<JPanel> panels) {
		JScrollPane scroller = new JScrollPane();
		for (JPanel panel : panels) {
			scroller.add(panel);
		}
		return scroller;
	}

	/**
	 * Determines if the team passed won the game
	 * @param team the team to be checked
	 * @return a boolean, true if the team won the game. False in anyother situation (loss, not complete)
	 */
	public boolean didTeamWin(String team) {
		if (Utility.JSONValueReader(lineScoreXML, Game.statusKey).equals(
				"Final")) {// game is complete and therefore there is a winner
							// or loser
			if (homeTeam.getName().equals(team))// the passed team is the home
												// team
			{
				if (homeScoreInning[runsIndex] > awayScoreInning[runsIndex]) {// the
																				// home
																				// team
																				// out
																				// scored
																				// the
																				// away
																				// team
					return true;
				}
				return false;

			} else // the passed team is the away team
			{
				if (homeScoreInning[runsIndex] > awayScoreInning[runsIndex]) {// the
																				// home
																				// team
																				// out
																				// scored
																				// the
																				// away
																				// team
					return false;
				}
				return true;
			}
		}
		return false;// the game is not complete
	}

	/**
	 * Determines if the team passed lost the game
	 * @param team the team to be checked
	 * @return a boolean, true if the team lost the game. False in anyother situation (victory, not complete)
	 */
	public boolean didTeamLose(String team) {
		if (Utility.JSONValueReader(lineScoreXML, Game.statusKey).equals(
				"Final")) {// game is complete and therefore there is a winner
							// or loser
			if (homeTeam.getName().equals(team))// the passed team is the home
												// team
			{
				if (homeScoreInning[runsIndex] > awayScoreInning[runsIndex]) {// the
																				// home
																				// team
																				// out
																				// scored
																				// the
																				// away
																				// team
					return false;
				}
				return true;

			} else // the passed team is the away team
			{
				if (homeScoreInning[runsIndex] > awayScoreInning[runsIndex]) {// the
																				// home
																				// team
																				// out
																				// scored
																				// the
																				// away
																				// team
					return true;
				}
				return false;
			}
		}
		return false;// the game is not complete
	}
}
