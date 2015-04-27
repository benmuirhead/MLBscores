import java.awt.FlowLayout;
import java.awt.GridLayout;

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
	/**
	 * Draws a line Score
	 * @param currentGame the game which is to be drawn
	 * @return a JPanel with the team logos and line score
	 */
	public static JPanel drawLineScore(Game currentGame)
	{
		JPanel basicScore = new JPanel();
		JPanel homePanel = new JPanel();
		JPanel awayPanel = new JPanel();
		
		//A 3x1 gridlayout of a flow panel
		//the flow houses the individual game
		GridLayout combinedLineLayout = new GridLayout(3,2);
		FlowLayout homeLineLayout = new FlowLayout();
		FlowLayout awayLineLayout = new FlowLayout();
		
		homePanel.setLayout(homeLineLayout);
		awayPanel.setLayout(awayLineLayout);
		basicScore.setLayout(combinedLineLayout);
		
		Icon homeLogo = currentGame.getHomeTeam().getLogo();
		Icon awayLogo = currentGame.getAwayTeam().getLogo();
		
		
		int[] combinedLine = currentGame.getLine();//gets the line from the Game
		
		String homeTeamScores = "";
		String awayTeamScores = "";
		String lineScoreTopLine = "                                   1  2  3  4  5  6  7  8  9 10 R  H  E";
		
		//Set lineScore data
		
		for (int i = 0; i < combinedLine.length / 2; i++)
		{//go through the array totally by using two seperate halves
			int tempScore = combinedLine[i];
			awayTeamScores = awayTeamScores + combinedLine[i];
			if (tempScore < 10)//the score is a single digit
			{
				awayTeamScores = awayTeamScores + "  ";
				//needs a space to even it out with double digits
			}
			//awayTeam info is first, homeTeam follows i + half of array length
			tempScore = combinedLine[i + combinedLine.length / 2];
			homeTeamScores = homeTeamScores + combinedLine[i + combinedLine.length / 2];
			if (tempScore < 10)//the score is a single digit
			{
				homeTeamScores = homeTeamScores + "  ";
				//needs a space to even it out with double digits
			}
		}
		
		String homeTeamString = currentGame.getHomeTeam().getCity() + " "+ currentGame.getHomeTeam().getName();
		String awayTeamString = currentGame.getAwayTeam().getCity() + " "+ currentGame.getAwayTeam().getName();
		
		//Make the strings of equal length. The Phillies have the longest name
		//at 21 characters
		
		for (int i = homeTeamString.length(); i < "Philidelphia Phillies".length(); i++)
		{
			homeTeamString = homeTeamString + " ";//add a blank to the end
		}
		for (int i = awayTeamString.length(); i < "Philidelphia Phillies".length(); i++)
		{
			awayTeamString = awayTeamString + " ";
		}
		awayTeamString = awayTeamString + "\t";
		homeTeamString = homeTeamString + "\t";
		//makes a label of the top line
		JLabel innings = new JLabel(lineScoreTopLine);
		//the two lines below make a label of the line of a team by combining their name and score.
		//attaches logo to the front
		JLabel homeTeamLabel = new JLabel( homeTeamString + " " + homeTeamScores);
		JLabel awayTeamLabel = new JLabel(awayTeamString + " " + awayTeamScores);
		
		JLabel awayLogoLabel = new JLabel(awayLogo);
		JLabel homeLogoLabel = new JLabel(homeLogo);
		//Basic Score tooltip displays park
		basicScore.setToolTipText(currentGame.getVenue());
		
		homePanel.add(homeTeamLabel);//adds the home Team Label to the panel
		awayPanel.add(awayTeamLabel);//same as above for away Team
		
		basicScore.add(new JLabel());
		basicScore.add(innings);
		basicScore.add(awayLogoLabel);
		basicScore.add(awayTeamLabel);
		basicScore.add(homeLogoLabel);
		basicScore.add(homeTeamLabel);
		
		
		
		
		
		return basicScore;
	}

}
