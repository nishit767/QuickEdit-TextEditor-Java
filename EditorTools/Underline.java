package EditorTools;
import javax.swing.JTextArea;
import java.awt.font.TextAttribute;
import java.awt.Font;
import java.util.*;

public class Underline
{
  JTextArea textArea;
  boolean tounderline;
  Font font;

  public Underline(JTextArea textArea,boolean tounderline)
  {
    this.textArea = textArea;
    this.tounderline = tounderline;
    font = textArea.getFont();
    makeChange();
  }

  public void makeChange()
  {
    if(tounderline)
    {
      Map attributes = font.getAttributes();
      attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
      textArea.setFont(font.deriveFont(attributes));
    }
    else
    {
      Font newFont = new Font(font.getFamily(),font.getStyle(),font.getSize());
      textArea.setFont(newFont);

    }
  }
}