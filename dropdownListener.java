import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;


public class dropdownListener implements ActionListener {
	
	private Gui Gui;
	public dropdownListener(Gui testGui){
	this.Gui = testGui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	JComboBox<String> dd = (JComboBox) e.getSource();
	String fullNewTeamName = (String) dd.getSelectedItem();
	Gui.newTeamSelected(fullNewTeamName);
	
	}
}

/*private Gui Gui;
public dropdownListener(Gui testGui){
this.Gui = testGui;
}
@Override
public void actionPerformed(ActionEvent e) {
JComboBox<String> dd = (JComboBox) e.getSource();
String newDateString = (String) dd.getSelectedItem();
System.out.println();
System.out.println(newDateString);
DateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
java.util.Date newDate = null;
try {
	newDate = df.parse(newDateString);
} catch (ParseException e1) {
	e1.printStackTrace();
}
System.out.println(newDate.getMonth());
newDate.setMonth(7);
Calendar cal = Calendar.getInstance();
cal.setTime(newDate);
cal.set(Calendar.MONTH, cal.MONTH+1);
System.out.println("Parse Date: " + Utility.convertCalendarToDate(cal)[1] + "/"
		+ Utility.convertCalendarToDate(cal)[2] + "/"
		+ Utility.convertCalendarToDate(cal)[0]);
Gui.dropdownChange(cal);

}*/