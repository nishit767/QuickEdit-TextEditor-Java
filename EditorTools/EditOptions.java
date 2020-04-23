package EditorTools;
// Importing Packages
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class EditOptions extends JFrame
{
	JTextArea fta;
	UndoManager umo = new UndoManager();
	public EditOptions(JTextArea ta)
	{
		fta = ta;
		fta.getDocument().addUndoableEditListener(umo);
	}

	public void cutText()
	{
		fta.cut();
	}
	public void copyText()
	{
		fta.copy();
	}
	public void pasteText()
	{
		fta.paste();
	}
	public void deleteText()
	{
		fta.replaceSelection("");
	}
	public void undoText()
	{
		try 
		{
          	umo.undo();
        }
        catch(Exception e) 
        {}
	}
	public void redoText()
	{
		try 
		{
          	umo.redo();
        }
        catch(Exception e) 
        {}
	}
	public void selectAllText()
	{
		fta.selectAll();
	}
	public void insertTimeStamp()
	{
		String months[] = {"January","February","March","April","May","June","July","August","September","October","November","December"};
		GregorianCalendar gcalendar = new GregorianCalendar(); 
		String h = String.valueOf(gcalendar.get(Calendar.HOUR)); 
		String m = String.valueOf(gcalendar.get(Calendar.MINUTE)); 
		String s = String.valueOf(gcalendar.get(Calendar.SECOND)); 
		String date = String.valueOf(gcalendar.get(Calendar.DATE)); 
		String month = months[gcalendar.get(Calendar.MONTH)]; 
		String year = String.valueOf(gcalendar.get(Calendar.YEAR)); 
		String timestamp = "\nTime:-"+h+":"+m+":"+s+"\n"+"Date:-"+date+"/"+month+"/"+year; 
		int loc = fta.getCaretPosition(); 
		fta.insert(timestamp,loc);
	}
}