import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Calendar;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Samuel Doud
 *
 */
public class Utility 
{

	public Utility() 
	{
		
	}
	/**
	 * takes text, searches for a key in the text, returns the value attached to the key
	 * For example, "key" = 6. The method returns "6"
	 * @param key
	 * @return
	 */
	public static String JSONValueReader(String JSONText, String key)
	{
		String value = "";
		int index = JSONText.indexOf(key) + key.length() + 2;
		//index takes the location of the Key within JSONText and adds the length of the key and one more
		//this takes index to the value of the key
		while (JSONText.charAt(index) != '"' && index != key.length() + 1)//the index != key.length + 1 insures against a -1 index
		{
			value = value + JSONText.charAt(index++);//adds the text at the value one by one
			//Stops when a quote is noted
		}
		if (value == "")
		{
			return "0";
			//is null when a inning is not played. IE the bottom of the ninth with the home team up
		}
		return value;
	}
	public static int[] convertCalendarToDate(Calendar c)
	{
		
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		return new int[]{year,month,day};
	}
	public static Calendar nextDay(Calendar c)
	{
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c;
	}
	

}
