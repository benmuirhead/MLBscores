import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;


/**
 * @author Samuel Doud
 *
 */
public class GameRange {
	private String baseAddress = "http://gd2.mlb.com/components/game/mlb/";
	private String name;
	private List<String> dateAddresses = new ArrayList<String>();
	private List<String> dateHTML = new ArrayList<String>();
	private List<Game> gamesPlayedByTeam = new ArrayList<Game>();
	Calendar start, end;
	private int wins, losses;
	/**
	 * 
	 * @param team a string in the form of a abbreviation. Use the static variables of the team class
	 * @param start a calendar date representing the start of the range
	 * @param end a calendar date representing the end of the range
	 */
	public GameRange(String team, Calendar start, Calendar end) 
	{
		this.start = start;
		this.end = end;
		this.name = team;
		this.populateDateAddresses();
		this.findGames();
		this.fixRecord();
		//TODO find the record
	}
	/**
	 * Populate the date address list with the addresses of the Date
	 */
	private void populateDateAddresses()
	{
		String day, month, year,total = "";
		Calendar workingDay = start;
		while (workingDay.compareTo(end) < 1)
		{
			day = workingDay.get(Calendar.DAY_OF_MONTH) + "";
			month = workingDay.get(Calendar.MONTH) + "";
			year = workingDay.get(Calendar.YEAR) + "";
			
			//ensure day and month have two digits
			if (Integer.parseInt(day) < 10)
			{
				day = "0" + day;//append a zero to the end
			}
			if (Integer.parseInt(month) < 10)
			{
				month = "0" + month;
			}
			total = total + "year_" + year + "/";
			total = total + "month_" + month + "/";
			total = total + "day_" + day + "/";
			
			dateAddresses.add(total);
			
			total = "";
			
			workingDay = Utility.nextDay(workingDay);
		}
	}
	/**
	 * Uses the list of HTMLs and hunts for games that the team passed
	 * in the constructor. If the team is in the html, create a game.
	 * Add this game to gamesPlayedByTeam
	 * 
	 */
	private void findGames()
	{
		int index, start, end;
		String tempAddress;
		
		for (int i = 0; i < dateAddresses.size(); i++)
		{
			dateHTML.add(this.getHTML(dateAddresses.get(i)));
		}
		//all the HTML files are now in a list
		//find the games in each address
		
		for (int i = 0; i < dateHTML.size(); i++)
		{//Iterate through the list of addresses
			index = dateHTML.get(i).indexOf(name);//finds the team's name in the HTML file
			if (index != -1)
			{//the team exists in the HTML file
				//the index will return somewhere in the middle of a string like
				// "gid_2015_04_24_atlmlb_phimlb_1/"
				//trace forwards and backwards until the quotation marks are reached
				start = index;
				end = index;
				for (start = index; dateHTML.get(i).charAt(start) != '"'; start--){//keep going until the start index reaches the quotation mark
					}
				for (end = index; dateHTML.get(i).charAt(end) != '"'; end++){//keep going until the start index reaches the quotation mark
					}
				tempAddress = dateAddresses.get(i) + dateHTML.get(i).substring(start+1, end);
				//tempAddress is the dates address and the found game link
				gamesPlayedByTeam.add(new Game(baseAddress+tempAddress));
				//creates a game and adds it to the list of games
				
				}
				
				
			}
		}
	/**
	 * Code to get the HTML files of a address
	 * @param address
	 * @return
	 */
	private String getHTML(String address)
	{
		InputStream input;
		BufferedReader reader;
		String temp;
		String myHTML = "";
		URL addressURL = null;
		try
		{
			addressURL = new URL(baseAddress + address);
		}
		catch(MalformedURLException e)
		{
			System.out.println("error in GameRange.getHTML 1");
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
			System.out.println("error in GameRange.getHTML 2");
		}
		return myHTML;
	}

	public List<Game> getGames()
	{
		return gamesPlayedByTeam;
	}
	private void fixRecord()
	{
		for (Game g : gamesPlayedByTeam)
		{
			if (g.didTeamWin(name))
			{
				wins++;
			}
			else
			{
				if(g.didTeamLose(name))
				{
					losses++;
				}
			}
		}
	}
	public JLabel drawRecord()
	{
		return new JLabel(wins + "-" + losses);
	}
}
