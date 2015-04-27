/**
 * 
 */
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
/**
 * @author Samuel Doud
 * A class that stores a major league baseball team
 * Data such as logo, hometown, abbreviation, league, and name
 */
public class Team 
{
	public static final String AMERICAN_LEAGUE= "American League";
	public static final String NATIONAL_LEAGUE = "National League";
	
	public static final String[] abbreviationKey = {"ana","ari","atl","bal","bos","cha","chn","cin","cle","col","det","hou","kca","lan","mia","mil","min","nya","nyn","oak","phi","pit","sdn","sea","sfn","sln","tba","tex","tor","was"};
	public static final String[] cityKey = {"Los Angeles","Arizona","Atlanta","Baltimore","Boston","Chicago","Chicago","Cincinati","Cleveland","Colorado","Detroit","Houston","Kansas City","Los Angeles","Miami","Milwaukee","Minnesota","New York","New York","Oakland","Philidelphia","Pittsburgh","San Diego","Seattle","San Francisco","St. Louis","Tampa Bay","Texas","Toronto","Washington"};
	public static final String[] nameKey = {"Angels","Diamondbacks","Braves","Orioles","Red Sox","White Sox","Cubs","Reds","Indians","Rockies","Tigers","Astros","Royals","Dodgers","Marlins","Brewers","Twins","Yankees","Mets","Athletics","Phillies","Pirates","Padres","Mariners","Giants","Cardinals","Rays","Rangers","Blue Jays","Nationals"};
	public static final String[] leagueDivisionKey = {"aw","nw","ne","ae","ae","ac","nc","nc","ac","nw","ac","aw","ac","nw","ne","nc","ac","ae","ne","aw","ne","nc","nw","aw","nw","nc","ae","aw","ae","ne"};
	//all of this data corresponds by index
	private String abbreviation;
	private String city;
	private String name;
	private String league;
	private ImageIcon logo;//possible feature. Make this a class itself and offer logos based on the year of the game requested
	/**
	 * A blank team is formed
	 */
	public Team() 
	{
		abbreviation = "";
		city = "";
		name = "";
		// TODO use a default image
	}
	public Team(String abv) 
	{
		setAbbreviation(abv);
		int index = Team.findIndexOfAbbreviation(abv);
		this.city = cityKey[index];
		this.name = nameKey[index];
		this.setImage();
		this.setLeague(index);
		// TODO use a default image
	}
	private void setLeague(int index)
	{
		league = "National League";
		if (leagueDivisionKey[index].charAt(0) == 'a')//team is American league
		{
			league = "American League";
		}
	}
	private static int findIndexOfAbbreviation(String abv)
	{
		int index = abbreviationKey.length;
		int differ = abbreviationKey.length;//middle of the array
		int direction = -1;
		//implement binary search for abbreviation
		while (Math.abs(differ) >= 1)
		{
			differ = Math.abs(differ) + 1;
			differ /= (2);
			differ *= direction;
			index = index + differ;
			
			direction = abv.compareTo(abbreviationKey[index]);
			if(direction == 0)
			{
				return index;//mainly here for safety of dividing by zero
			}
			direction /= Math.abs(direction);
		}
		return index;
	}
	public void setAbbreviation(String abv)
	{
		this.abbreviation = abv;
		this.setImage();//sets the image now that the necessary abbreviation is passed
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public void setTeamName(String name)
	{
		this.name = name;
	}
	public void setLeague(String league)
	{
		if (league.equals(AMERICAN_LEAGUE) || league.equals(NATIONAL_LEAGUE))
		{//ensures the passed league is actually a league
			this.league = league;
		}
	}
	/**
	 * To be done after abbreviation has been set
	 * sets the logo to the png of the same abbreviation
	 */
	private void setImage()
	{
		logo = new ImageIcon("logos/" + abbreviation + ".png", "The logo of the Team");
	}
	public String getAbbreviation()
	{
		return abbreviation;
	}
	public String getCity()
	{
		return city;
	}
	public String getName()
	{
		return name;
	}
	public String getLeague()
	{
		return league;
	}
	public boolean isAmericanLeague()
	{
		if (this.league.equals(AMERICAN_LEAGUE))//the team's league equals the AMERICAN_LEAGUE constant
		{
			return true;
		}
		return false;//the team is not in the AL
	}
	public boolean isNationalLeague()
	{
		if (this.league.equals(NATIONAL_LEAGUE))//the team's league equals the NATIONAL_LEAGUE constant
		{
			return true;
		}
		return false;//the team is not in the NL
	}
	public Icon getLogo()
	{
		return logo;
	}
}
