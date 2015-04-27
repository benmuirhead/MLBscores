import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 */

/**
 * @author Samuel Doud
 *
 */
public class Game 
{
	
	private Team homeTeam, awayTeam;//home teams are last in the URL
	private int[] homeScoreInning;
	private int[] awayScoreInning;
	private URL base;
	private URL lineScoreURL;
	private String lineScoreXML;
	private String venue;
	
	private final static String homeInningKey = "home_inning_runs";
	private final static String awayInningKey = "away_inning_runs";
	private final static String homeRunsKey = "home_team_runs";
	private final static String awayRunsKey ="away_team_runs";
	private final static String homeHitsKey = "home_team_hits";
	private final static String awayHitsKey = "away_team_hits";
	private final static String homeErrorsKey = "home_team_errors";
	private final static String awayErrorsKey = "away_team_errors";
	private final static String currentInningKey = " inning";
	private final static String statusKey = "status";
	private final static String inningSideKey = "top_key";
	private final static String locationKey = "venue";
	
	public final int runsIndex = 10;
	public final int hitsIndex = runsIndex + 1;
	public final int errorsIndex = runsIndex + 2;
	
	/**
	 * Creates a game object by parsing a URL where the game's data is located
	 * Then contructor delegates to other methods
	 */
	public Game(String gameURL)
	{
		try
		{
		base = new URL(gameURL);	
		lineScoreURL = new URL(gameURL + "linescore.xml");
		}
		catch(MalformedURLException e)
		{
			
		}
		createTeams();
		getLineScore();
		fixVenue();
	}
	/**
	 * Creates the teams based on the URL
	 */
	private void createTeams()
	{
		int indexAway = 80;//all urls are the same so a static location can be used
		int indexHome = indexAway + 7;
		int length = 3;
		
		awayTeam = new Team(base.toString().substring(indexAway, indexAway + length));
		
		homeTeam = new Team(base.toString().substring(indexHome, indexHome + length));
	}

	private void getLineScore()
	{
		InputStream input;
		BufferedReader reader;
		String temp;
		try
		{
			input = lineScoreURL.openStream();
			reader = new BufferedReader(new InputStreamReader(input));

	        while ((temp = reader.readLine()) != null)
	        {
	            lineScoreXML+=temp;
	        }
	        input.close();
	        reader.close();
		}
		catch (IOException e)
		{
			System.out.println("error in Game.getLineScore()");
		}
		determineLineScore();
		
	}
	/**
	 * Determines the linescore for a game
	 * {inning 1 score, ..., inning 10 score, runs, hits, errors}
	 */
	private void determineLineScore()
	{
		homeScoreInning = new int[13]; //ten innings plus RHE
		awayScoreInning = new int[13];
		//Get inning by inning score
		//sample inning score <linescore inning="1" home_inning_runs="0" away_inning_runs="1"/>
		//method find "<linescore inning="XXXX"....../>
		int currentInning = 1;
		int currentIndex;
		String copyOfLineScore = lineScoreXML;
		String key = "<linescore inning=\"";
		String currentInningText = "";
		while (currentInning <= 10)
		{
			currentIndex = copyOfLineScore.indexOf(key + currentInning + "\"");
			if (currentIndex > 0)//the inning happened. Current Index is not -1
			{
				while (copyOfLineScore.charAt(currentIndex) != '/')
				{
					currentInningText = currentInningText + copyOfLineScore.charAt(currentIndex);
					currentIndex++;
				}
				
				homeScoreInning[currentInning - 1] = Integer.parseInt(Utility.JSONValueReader(currentInningText, Game.homeInningKey));
				awayScoreInning[currentInning - 1] = Integer.parseInt(Utility.JSONValueReader(currentInningText, Game.awayInningKey));
			}
			currentInning++;
			currentInningText = "";
			
		}
		
		homeScoreInning[runsIndex] = Integer.parseInt(Utility.JSONValueReader(copyOfLineScore, Game.homeRunsKey));
		awayScoreInning[runsIndex] = Integer.parseInt(Utility.JSONValueReader(copyOfLineScore, Game.awayRunsKey));
		homeScoreInning[hitsIndex] = Integer.parseInt(Utility.JSONValueReader(copyOfLineScore, Game.homeHitsKey));
		awayScoreInning[hitsIndex] = Integer.parseInt(Utility.JSONValueReader(copyOfLineScore, Game.awayHitsKey));
		homeScoreInning[errorsIndex] = Integer.parseInt(Utility.JSONValueReader(copyOfLineScore, Game.homeErrorsKey));
		awayScoreInning[errorsIndex] = Integer.parseInt(Utility.JSONValueReader(copyOfLineScore, Game.awayErrorsKey));
		
		
	}
		
	/**
	 * 
	 * @return
	 */
	public int[] getLine()
	{
		int[] combinedLine = new int[homeScoreInning.length + awayScoreInning.length];
		//make an array of double the size
		//away team occupies first slots
		for (int i = 0; i < homeScoreInning.length; i++)
		{
			combinedLine[i] = awayScoreInning[i];
			combinedLine[i+homeScoreInning.length] = homeScoreInning[i];
		}
		return combinedLine;
	}

	public String toString()
	{
		String text = venue + "\n";
		
		text = text + awayTeam.getCity() + " " + awayTeam.getName() + "\t";
		for (int i : awayScoreInning)
		{
			text = text + i + " ";
		}
		text = text + getInning() +"\n";
		text = text + homeTeam.getCity() + " " + homeTeam.getName() + "\t";
		for (int i : homeScoreInning)
		{
			text = text + i + " ";
		}
		
		return text;
	}
	
	public String getInning()
	{
		String text = "";
		if (Utility.JSONValueReader(lineScoreXML, Game.statusKey).equals("Final"))
		{//game is complete
			text = "F";
		}
		else
		{
			if(Utility.JSONValueReader(lineScoreXML, Game.statusKey).equals("Pre-Game"))
			{
				text = "P";
			}
			else
			{//game is in progress
				text = Utility.JSONValueReader(lineScoreXML, Game.currentInningKey);
				if (Utility.JSONValueReader(lineScoreXML, Game.inningSideKey).equals("Y"))
				{
					text = text + "↑";
				}
				else
				{
					text = text + "↓";
				}
			}
		}
		return text;
	}
	/**
	 * Is the game between two American league teams?
	 * @return
	 */
	public boolean isAmericanLeague()
	{
		return (homeTeam.isAmericanLeague() && awayTeam.isAmericanLeague());
	}/**
	 * Is the game between two National league teams?
	 * @return
	 */
	public boolean isNationalLeague()
	{
		return (homeTeam.isNationalLeague() && awayTeam.isNationalLeague());
	}
	/**
	 * Is the game interLeague?
	 * @return
	 */
	public boolean isInterLeague()
	{
		return !(isNationalLeague() || isAmericanLeague());
	}
	/**
	 * Takes the location of the game
	 * IE Fenway Park
	 */
	private void fixVenue()
	{
		venue = Utility.JSONValueReader(lineScoreXML, locationKey);
	}
	public String getVenue()
	{
		return venue;
	}
	public Team getHomeTeam()
	{
		return homeTeam;
	}
	public Team getAwayTeam()
	{
		return awayTeam;
	}
}
