import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author Samuel Doud
 * @author Ben Muirhead
 *
 * 
 */
public class testRun extends JFrame {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new testRun();
	}

	public testRun() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.init();
	}

	public void init() {
		//GameDay testGames = new GameDay(2015, 04, 23);
		//System.out.println(testGames.toString());
		//List<Game> gameList = testGames.getGames();
		//JPanel lineTest = gameList.get(0).drawDetailed();
		
		
		Calendar startDate = Utility.convertDateToCalendar(2015, 4,15);
		Calendar endDate = Utility.convertDateToCalendar(2015, 4, 20);
		String team = Team.angels;
		
		
		
		GameRange testRange = new GameRange(team,startDate,endDate);
		List<Game> testRangeGames = testRange.getGames();
		
		JPanel lineTest = testRangeGames.get(0).drawBasicScoreWithDate();
		
		
		
		this.setSize(500, 500);
		this.setResizable(false);
		this.add(lineTest);
		this.setVisible(true);
	}

}
