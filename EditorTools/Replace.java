package EditorTools;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.io.File; 
import java.util.*;
import java.awt.*;
import javax.swing.JDialog;
import java.awt.event.*;  
import javax.swing.*;  
import javax.swing.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Replace extends JFrame implements ActionListener
{
  JTextArea textArea;
  JDialog dialog1;
  JDialog dialog2;
  boolean replace=false;
  boolean replaceAll=false;
  JTextField fromField;
  JTextField toField;
  
  public Replace(JTextArea textArea)
  {
    //super(new GridLayout());
    this.textArea = textArea;

    dialog1=new JDialog();
    dialog1.setTitle("Replace");
    dialog1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    dialog1.setLayout(new GridLayout(3,4));

    JButton btnReplace = new JButton("Replace Word");
    dialog1.add(btnReplace);
    btnReplace.addActionListener(this);

    JButton btnReplaceAll = new JButton("Replace All");
    dialog1.add(btnReplaceAll);
    btnReplaceAll.addActionListener(this);

    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (int) ((dimension.getWidth() - dialog1.getWidth()) / 2);
    int y = (int) ((dimension.getHeight() - dialog1.getHeight()) / 2);
    dialog1.setLocation(x, y);
    dialog1.setSize(300,200);
    dialog2=new JDialog();
    dialog2.setTitle("Replace");
    dialog2.setSize(350, 100);
    dialog2.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    JPanel panel = new JPanel();
    JButton replaceButton = new JButton("Replace");
    replaceButton.addActionListener(this);

    fromField = new JTextField(8);

    toField = new JTextField(8);
    panel.add(replaceButton);
    panel.add(fromField);
    panel.add(new JLabel("with"));
    panel.add(toField);
    dialog2.add(panel);
    
    dialog2.setLocation(x,y);


  }

  public void dialog1Method()
  { 
   
	  dialog1.setVisible(true);

  }

  public void dialog2Method()
  {
    dialog2.setVisible(true);
  }


  public void actionPerformed(ActionEvent ae)
  {
    String arg = (String)ae.getActionCommand();
    if(arg.equals("Replace Word"))
		{
			replace=true;
      replaceAll=false;
      dialog2Method();
      dialog1.dispose();
		}	
    if(arg.equals("Replace All"))
		{
      replace=false;
      replaceAll=true;
      dialog2Method();
      dialog1.dispose();
    }
    if(arg.equals("Replace"))
    {
      replacetext();
      dialog2.dispose();
      
    }


  }

  public void replacetext()
  {
    String mytext;
    String from = fromField.getText();
    int start=0; 
    int fromindex=0;
    int count=0;
    if(replace!=true)
    {
      while(start>=0 && from.length()>0)
      {
        start = textArea.getText().indexOf(from,fromindex); 
        if(from.length() < toField.getText().length())
        {
          fromindex = start + from.length();
        }
        else 
        {
          fromindex = start + toField.getText().length();
        }
        if (start >= 0 && from.length() > 0)
        {
          count ++;
          textArea.replaceRange(toField.getText(), start, start+ from.length());
        }
        
      }    
    }
    else
    {
      String check="\"''\n, _@!#+-/%&*(){}[]?=/|\\.;:";
      while(start>=0 && from.length()>0 )
      {
        mytext=textArea.getText();
        start = mytext.indexOf(from,fromindex); 
        if(start>0 && mytext.length()>=start+from.length()+1 && check.contains(mytext.substring(start-1,start)) && check.contains(mytext.substring(start+from.length(),start+from.length()+1)))
        {
          fromindex=start-1;
          count++;
          textArea.replaceRange(toField.getText(), start, start+ from.length());
        }
        else if(start==0 && mytext.length()>=start+from.length()+1 && check.contains(mytext.substring(start+from.length(),(start+from.length()+1))))
        {
          fromindex=0;
          count++;
          textArea.replaceRange(toField.getText(), start, start+ from.length());
        }
        else if(start>0 && mytext.length()==start+from.length() && check.contains(mytext.substring(start-1,start)))
        {
          fromindex=start-1;
          count++;
          textArea.replaceRange(toField.getText(), start, start+ from.length());
        }
        else if(start==0 && mytext.length()==start+from.length())
        {
          fromindex=0;
          count++;
          textArea.replaceRange(toField.getText(), start, start+ from.length());
        }
        else
        {
            fromindex= start + from.length();
        }
      }
    }
    if(count==0)
    {
      new OptionPaneExample("NO Match Found !!!");
    }
    else
    {
      new OptionPaneExample("Successfully Updated !!!"); 
    }
    
  }


}

 
class OptionPaneExample {  
  JFrame f;  
  OptionPaneExample(String message){  
    f=new JFrame();  
    JOptionPane.showMessageDialog(f,message,"Alert",JOptionPane.WARNING_MESSAGE);   
    f.dispose();  
  }  
} 