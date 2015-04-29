/**
 * 
 */
import java.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * @author Samuel Doud
 * Class that holds all of the games of a day
 */
public class GameDay 
{
	private String address = "http://gd2.mlb.com/components/game/mlb/"; //this is the base URL
	private URL addressURL; //the address that will hold the game day address
	private List<Game> gamesToday; // a list of Games during the day
	private String myHTML; //the HTML file of the address created will be written here
	private List<String> gameAddresses; //addresses of all of the games
	/**
	 * Create the address of the games base file based on the passed parameters
	 */
	public GameDay(Calendar c) 
	{
		//convert the calendar c to day, month, year
		int day, month,year;
		day = c.get(Calendar.DAY_OF_MONTH);
		month = c.get(Calendar.MONTH);
		year = c.get(Calendar.YEAR);
		
		
		myHTML="";
		//add the year to the address
		address = address + "year_" + year + "/";
		//add the month
		if (month < 10)
		{//if the month is not October, November, or December
			address = address + "month_0" + month + "/";
		}
		else
		{//if it is those three months
			address = address + "month_" + month + "/";//remove the static zero
		}
		//add the day. This will have to include the possibility of a leading 0
		if (day < 10)//leading zero is not present
		{
			address = address + "day_0" + day + "/";
		}
		else
		{
			address = address + "day_" + day + "/";			
		}
		getHTML();//get the HTML of the game day info
		findGames(); //find the games from HTML
		createGameObjects(); //create objects of games
	}
	/**
	 * Get the HTML base file for the given date
	 */
	private void getHTML()
	{
		InputStream input;
		BufferedReader reader;
		String temp;
		
		try
		{
			addressURL = new URL(address);
		}
		catch(MalformedURLException e)
		{
			System.out.println("error in GameDay.getHTML 1");
		}
		try
		{
			input = addressURL.openStream();
			reader = new BufferedReader(new InputStreamReader(input));

	        while ((temp = reader.readLine()) != null)
	        {
	            myHTML+=temp;
	        }
	        input.close();
	        reader.close();
		}
		catch (IOException e)
		{
			System.out.println("error in GameDay.getHTML 2");
		}
	}
	/**
	 * Find the games from a given base URL
	 */
	public void findGames()
	{
		//games URL parts are always in the format of 
		//"gid_2015_04_06_sdnmlb_lanmlb_1/" - 31  characters long.
		
		String temp;
		String key = "gid";//indicates address is a game
		int lengthOfKey = key.length();
		int lengthOfAddress = 31;
		boolean gamesYet = false;
		gameAddresses = new ArrayList<String>();//is a list of game addresses
		
		for (int index = 0; index < myHTML.length(); index++)
		{
			temp = myHTML.substring(index,  index + lengthOfKey);
			if (temp.equals(key))
			{
				temp = myHTML.substring(index, index + lengthOfAddress);//we now we have a address postfix
				gameAddresses.add(address + temp);//add the full address to the list of game addresses
				index += (lengthOfAddress * 2 + 24);//take index to the end of the address
				gamesYet = true;
			}
			else
			{
				if (gamesYet)
				{
					return;
				}
			}
		}
		
		//all addresses have been created
	}
	/**
	 * Returns the list of Games
	 * Irrespective of League
	 * @return
	 */
	public List<Game> getGames()
	{
		return gamesToday;
	}
	/**
	 * Returns the list of Games in the American League
	 * @return
	 */
	public List<Game> getAmericanGames()
	{
		List<Game> ALGames = new ArrayList<Game>();
		for(Game g : gamesToday)
		{
			if (g.isAmericanLeague())
			{//is the game played by AL teams?
				ALGames.add(g);//if so, add to list
			}
		}
		return ALGames;
	}
	/**
	 * Returns the list of Games in the National League
	 * @return
	 */
	public List<Game> getNationalGames()
	{
		List<Game> NLGames = new ArrayList<Game>();
		for(Game g : gamesToday)
		{
			if (g.isNationalLeague())
			{
				NLGames.add(g);
			}
		}
		return NLGames;
	}
	/**
	 * Returns the list of Games that are interleague
	 * @return
	 */
	public List<Game> getInterLeagueGames()
	{
		List<Game> ILGames = new ArrayList<Game>();
		for(Game g : gamesToday)
		{
			if (g.isInterLeague())
			{
				ILGames.add(g);
			}
		}
		return ILGames;
	}
	/**
	*	Takes the gameAddress and makes a Game out of them. Adds that game to
	*	the List of gamesToday
	 */
	public void createGameObjects()
	{
		gamesToday = new ArrayList<Game>();
		for (String s: gameAddresses)
		{
			gamesToday.add(new Game(s));//creates a new game of String s
		}
	}
	public String toString()
	{
		String text = "";
		for (Game g : gamesToday)
		{
			text = text + g.toString() + "\n\n";
		}
		return text;
	}
	//TODO this class will create the standings
}
