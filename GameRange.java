import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class GameRange {
	private String baseAddress = "http://gd2.mlb.com/components/game/mlb/";
	private List<String> dateAddresses = new ArrayList<String>();
	private List<String> dateHTML = new ArrayList<String>();
	private List<Game> gamesPlayedByTeam = new ArrayList<Game>();
	Calendar start, end;
	
	public GameRange(String team, Calendar start, Calendar end) 
	{
		this.start = start;
		this.end = end;
		populateDateAddresses();
		//TODO find the games...
		//TODO
	}
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
			total = total + "day_" + year + "/";
			
			dateAddresses.add(total);
			
			workingDay = Utility.nextDay(workingDay);
		}
	}
	private void findGames()
	{
		String currentHTML;
		
		for (int i = 0; i < dateAddresses.size(); i++)
		{
			currentHTML = getHTML(dateAddresses.get(i));
			dateHTML.add(currentHTML);
		}
	}
	private static String getHTML(String address)
	{
		InputStream input;
		BufferedReader reader;
		String temp;
		String myHTML = "";
		URL addressURL;
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
		return myHTML;
	}
}
