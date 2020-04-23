package EditorTools;
import javax.swing.JTextArea;
import java.awt.Font;

public class Bold
{
  JTextArea textArea;
  public Bold(JTextArea textArea)
  {
    this.textArea=textArea;
    //setBold();
  }

  public void setBold()
  {
    Font fontObj=textArea.getFont();
			Font newObj;
      if(fontObj.getStyle()==(Font.ITALIC+Font.BOLD))
			{
				newObj=new Font(fontObj.getFamily(),Font.ITALIC,fontObj.getSize());
			}
			else if(fontObj.getStyle()==Font.BOLD)
			{
				newObj=new Font(fontObj.getFamily(),Font.PLAIN,fontObj.getSize());
			}
			else if(fontObj.getStyle()==Font.ITALIC)
		  {
				newObj=new Font(fontObj.getFamily(),fontObj.getStyle()+Font.BOLD,fontObj.getSize());
			}
      else
      {
        newObj=new Font(fontObj.getFamily(),Font.BOLD,fontObj.getSize());
      }
			textArea.setFont(newObj);
  }
}