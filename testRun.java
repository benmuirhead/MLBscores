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
public class testRun extends JFrame{
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testRun test = new testRun();
	}
	public testRun()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.init();
	}
	public void init()
	{
		GameDay testGames = new GameDay(2015,03,23);
		System.out.println(testGames.toString());
		List<Game> gameList = testGames.getGames();
		JPanel lineTest = Utility.drawLineScore(gameList.get(0));
		this.setSize(500, 500);
		this.add(lineTest);
		this.setVisible(true);
	}

}
