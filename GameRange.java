import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class GameRange {
	private String baseAddress = "http://gd2.mlb.com/components/game/mlb/";
	private List<String> dateAddresses = new ArrayList<String>();
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
	

}
