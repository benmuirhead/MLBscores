import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JComboBox;

public class dropdownListener implements ActionListener {

	private Gui Gui;
	public dropdownListener(Gui testGui){
		this.Gui = testGui;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> dd = (JComboBox) e.getSource();
		String newDateS = (String) dd.getSelectedItem();
		DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
		java.util.Date newDate = null;
		try {
			newDate = df.parse(newDateS);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(newDate);
		
		Gui.dropdownChange(cal);

	}

}
