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
		testRun test = new testRun();
	}

	public testRun() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.init();
	}

	public void init() {
		GameDay testGames = new GameDay(2015, 04, 23);
		System.out.println(testGames.toString());
		List<Game> gameList = testGames.getGames();
		JPanel lineTest = gameList.get(0).drawDetailed();
		//this.setSize(500, 500);
		this.add(lineTest);
		this.setVisible(true);
	}

}
