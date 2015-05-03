/**
 * 
 */
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author Samuel Doud
 * A class that stores a major league baseball team
 * Data such as logo, hometown, abbreviation, league, and name
 */
public class Team {
	public static final String AMERICAN_LEAGUE = "American League";
	public static final String NATIONAL_LEAGUE = "National League";

	// static variables of abbreviations
	public static final String angels = "ana";
	public static final String diamondBacks = "ari";
	public static final String braves = "atl";
	public static final String orioles = "bal";
	public static final String redSox = "bos";
	public static final String whiteSox = "cha";
	public static final String cubs = "chn";
	public static final String reds = "cin";
	public static final String indians = "cle";
	public static final String rockies = "col";
	public static final String tigers = "det";
	public static final String astros = "hou";
	public static final String royals = "kca";
	public static final String dodgers = "lan";
	public static final String marlins = "mia";
	public static final String brewers = "mil";
	public static final String twins = "min";
	public static final String yankees = "nya";
	public static final String mets = "nyn";
	public static final String athletics = "oak";
	public static final String phillies = "phi";
	public static final String pirates = "pit";
	public static final String padres = "sdn";
	public static final String mariners = "sea";
	public static final String giants = "sfn";
	public static final String cardinals = "sln";
	public static final String rays = "tba";
	public static final String rangers = "tex";
	public static final String blueJays = "tor";
	public static final String nationals = "was";

	public static final String[] abbreviationKey = { angels, diamondBacks,
			braves, orioles, redSox, whiteSox, cubs, reds, indians, rockies,
			tigers, astros, royals, dodgers, marlins, brewers, twins, yankees,
			mets, athletics, phillies, pirates, padres, mariners, giants,
			cardinals, rays, rangers, blueJays, nationals };
	public static final String[] cityKey = { "Los Angeles", "Arizona",
			"Atlanta", "Baltimore", "Boston", "Chicago", "Chicago",
			"Cincinati", "Cleveland", "Colorado", "Detroit", "Houston",
			"Kansas City", "Los Angeles", "Miami", "Milwaukee", "Minnesota",
			"New York", "New York", "Oakland", "Philidelphia", "Pittsburgh",
			"San Diego", "Seattle", "San Francisco", "St. Louis", "Tampa Bay",
			"Texas", "Toronto", "Washington" };
	public static final String[] nameKey = { "Angels", "Diamondbacks",
			"Braves", "Orioles", "Red Sox", "White Sox", "Cubs", "Reds",
			"Indians", "Rockies", "Tigers", "Astros", "Royals", "Dodgers",
			"Marlins", "Brewers", "Twins", "Yankees", "Mets", "Athletics",
			"Phillies", "Pirates", "Padres", "Mariners", "Giants", "Cardinals",
			"Rays", "Rangers", "Blue Jays", "Nationals" };
	public static final String[] leagueDivisionKey = { "aw", "nw", "ne", "ae",
			"ae", "ac", "nc", "nc", "ac", "nw", "ac", "aw", "ac", "nw", "ne",
			"nc", "ac", "ae", "ne", "aw", "ne", "nc", "nw", "aw", "nw", "nc",
			"ae", "aw", "ae", "ne" };
	// all of this data corresponds by index
	private String abbreviation;
	private String city;
	private String name;
	private String league;
	private ImageIcon logo;// possible feature. Make this a class itself and
							// offer logos based on the year of the game
							// requested

	/**
	 * A blank team is formed
	 */
	public Team() {
		abbreviation = "";
		city = "";
		name = "";
		// TODO use a default image
	}

	public Team(String abv) {
		setAbbreviation(abv);
		System.out.print("abv='" + abv + "'");
		int index = Team.findIndexOfAbbreviation(abv);
		this.city = cityKey[index];
		this.name = nameKey[index];
		this.setImage();
		this.setLeague(index);
		// TODO use a default image
	}

	private void setLeague(int index) {
		league = "National League";
		if (leagueDivisionKey[index].charAt(0) == 'a')// team is American league
		{
			league = "American League";
		}
	}

	/**
	 * Binary search
	 * @param abv
	 * @return
	 */
	private static int findIndexOfAbbreviation(String abv) {

//		for (int k = 0; k < abbreviationKey.length; k++) {
//			if (abbreviationKey[k].equals(abv)) {
//				System.out.println("  "+k);
//				return k;
//			}
//		}
//		System.out.println();
//		System.out.print("Match failed, returning 0 (angels)");
//		return 0;
//		
		int index = abbreviationKey.length;
		int differ = abbreviationKey.length;// middle of the array
		int direction = -1;
		// implement binary search for abbreviation
		while (Math.abs(differ) >= 1) {
			differ = Math.abs(differ) + 1;
			differ /= (2);
			differ *= direction;
			index = index + differ;

			direction = abv.compareTo(abbreviationKey[index]);
			if (direction == 0) {
				return index;// mainly here for safety of dividing by zero
			}
			direction /= Math.abs(direction);
		}
		return index;
	}

	public void setAbbreviation(String abv) {
		this.abbreviation = abv;
		this.setImage();// sets the image now that the necessary abbreviation is
						// passed
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setTeamName(String name) {
		this.name = name;
	}

	public void setLeague(String league) {
		if (league.equals(AMERICAN_LEAGUE) || league.equals(NATIONAL_LEAGUE)) {// ensures
																				// the
																				// passed
																				// league
																				// is
																				// actually
																				// a
																				// league
			this.league = league;
		}
	}

	/**
	 * To be done after abbreviation has been set
	 * sets the logo to the png of the same abbreviation
	 */
	private void setImage() {
		logo = new ImageIcon("logos/" + abbreviation + ".png",
				"The logo of the Team");
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getCity() {
		return city;
	}

	public String getName() {
		return name;
	}

	public String getLeague() {
		return league;
	}

	public boolean isAmericanLeague() {
		if (this.league.equals(AMERICAN_LEAGUE))// the team's league equals the
												// AMERICAN_LEAGUE constant
		{
			return true;
		}
		return false;// the team is not in the AL
	}

	public boolean isNationalLeague() {
		if (this.league.equals(NATIONAL_LEAGUE))// the team's league equals the
												// NATIONAL_LEAGUE constant
		{
			return true;
		}
		return false;// the team is not in the NL
	}

	public Icon getLogo() {
		return logo;
	}
}
