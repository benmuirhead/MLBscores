import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
		// GameDay testGames = new GameDay(2015, 04, 23);
		// System.out.println(testGames.toString());
		// List<Game> gameList = testGames.getGames();
		// JPanel lineTest = gameList.get(0).drawDetailed();

		Gui testGui = new Gui();

		/*
		 * Calendar startDate = Utility.convertDateToCalendar(2014, 4,10);
		 * Calendar endDate = Utility.convertDateToCalendar(2014, 4, 20); String
		 * team = Team.mets;
		 * 
		 * GameRange testRange = new GameRange(team,startDate,endDate);
		 * List<Game> testRangeGames = testRange.getGames(); List<JPanel>
		 * testRangePanels = new ArrayList<JPanel>();
		 * 
		 * for (Game g : testRangeGames) {
		 * testRangePanels.add(g.drawBasicScoreWithDate()); }
		 * 
		 * JPanel listHolder = new JPanel(); GridLayout grid = new
		 * GridLayout(testRangePanels.size(),1); listHolder.setLayout(grid);
		 * 
		 * for (JPanel panel : testRangePanels) { listHolder.add(panel); }
		 * JScrollPane jScrollPane = new JScrollPane(listHolder);
		 * 
		 * this.setSize(100, 300); this.setResizable(true);
		 * this.add(jScrollPane); //this.add(testRange.drawRecord()); This is
		 * functional //this.setVisible(true);
		 */
	}

}
