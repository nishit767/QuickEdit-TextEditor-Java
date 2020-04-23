package EditorTools;
import javax.swing.JTextArea;
import java.awt.Font;

public class Italic
{
  JTextArea textArea;
  public Italic(JTextArea textArea)
  {
    this.textArea=textArea;
    //setItalic();
  }

  public void setItalic()
  {
    Font fontObj=textArea.getFont();
			Font newObj;
			if(fontObj.getStyle()==(Font.ITALIC+Font.BOLD))
			{
				newObj=new Font(fontObj.getFamily(),Font.BOLD,fontObj.getSize());
			}
			else if(fontObj.getStyle()==Font.BOLD)
			{
				newObj=new Font(fontObj.getFamily(),Font.ITALIC+Font.BOLD,fontObj.getSize());
			}
			else if(fontObj.getStyle()==Font.ITALIC)
			{
				newObj=new Font(fontObj.getFamily(),Font.PLAIN,fontObj.getSize());
			}
      else
      {
        newObj=new Font(fontObj.getFamily(),Font.ITALIC,fontObj.getSize());
      }
			textArea.setFont(newObj);
  }
}