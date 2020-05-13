package EditorTools;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.io.File; 
import java.util.*;
import java.awt.*;
import java.awt.event.*;  
import javax.swing.*;  
import javax.swing.event.*;
import java.awt.BorderLayout;  
import java.awt.Color;  
import java.awt.Container;   
import javax.swing.JScrollPane;  
import javax.swing.JTextPane;  
import javax.swing.text.BadLocationException;  
import javax.swing.text.Document;  
import javax.swing.text.SimpleAttributeSet;  
import javax.swing.text.StyleConstants;  


public class About
{
  JFrame frame = new JFrame("About"); 
  public About()
  {
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel pane = new JPanel();
    Container container = frame.getContentPane( );
    container.setLayout(new GridLayout( 4, 4, 0, 10));
		JLabel label = new JLabel("<html> <font size=+8> About </font><br> </html>",JLabel.CENTER);
    container.add(label); 
    JLabel created_by = new JLabel( "<html><font>QuickEdit created by</font><font><ul><li>Alfiza Shaikh - 1811118</li><li>Devansh shah - 1811111</li><li>Nishit Shah - 1811114</li></ul></font> <p>This is a mini project in sem 4 in kjsce </p></html>");
    container.add(created_by);
		frame.add(pane); 
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2.5 );
    int y = (int) ((dimension.getHeight() - frame.getHeight()) /2.5 );
    frame.setLocation(x,y);
		frame.setSize(400, 450); 
  }

  public void showAbout(){
    frame.setVisible(true);
  }
}