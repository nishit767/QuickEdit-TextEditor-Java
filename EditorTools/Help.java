package EditorTools;
// Main.java requires no other files. 
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.io.File;  // Import the File class
import java.io.IOException;
import java.io.FileWriter; 
import java.io.FileReader; 
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JScrollPane;

public class Help extends JFrame implements ActionListener {
    JDialog hpframe=new JDialog();
    protected JTextField textField;
    protected JTextArea textArea;
    protected JScrollPane scrollPane;
    private final static String newline = "\n";
    String[] feature={"New","Open","Save","Save As","Rename","Exit","Cut","Copy","Paste","Delete","Undo","Redo","Select All","Time Stamp","Find","Replace","Font Settings","Bold","Italic","Underline"};
    String[] opt={"File Option","Edit Option","Search Option","Font","Help","About"};

    public Help() {
        hpframe.setTitle("Help");
        hpframe.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        input();

        textField = new JTextField();
        textField.setText("Search Here!!!");
        textField.selectAll();
        textField.addActionListener(this);
        textField.setPreferredSize( new Dimension( 300, 50 ) );
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JButton searchb=new JButton("Help");
        searchb.addActionListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
 
        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);
        add(searchb,c);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hpframe.add(scrollPane);
        hpframe.setSize(500,500);  
        hpframe.setLocationRelativeTo(null);
    }
  
    public void input()
    {
      textArea.setText("");
      int i;
      for (i=0;i<opt.length;i++)
      {
        print(opt,i);
      }
      //features writing
      for (i=0;i<feature.length;i++)
      {
        print(feature,i);
      }
    }

    public void actionPerformed(ActionEvent evt) {
        String text = textField.getText();
        textField.selectAll();
        String[] query=text.split("[? ,.]");
        textArea.setText("");
        int i,j,count=0;
        if (text!= "")
        {
          for (i=0;i<query.length;i++)
          {
            for(j=0;j<opt.length;j++)
            {
              if((opt[j].toUpperCase()).contains(query[i].toUpperCase()))
              {
                count++;
                print(opt,j);
              }
            }
          }

          for (i=0;i<feature.length;i++)
          {
            if((text.toUpperCase()).contains(feature[i].toUpperCase()))
            {
              count++;
              print(feature,i);
            }
          }
          if(count==0)
          {
            textArea.append("No results");
          } 
        }  
        
    }
    

    public void print(String[] array,int i)
    {
      textArea.append(newline +newline + array[i] + newline);
      String desc = ""; 
      try { 
        desc = new String(Files.readAllBytes(Paths.get("EditorTools//Description//"+array[i]+".txt"))); 
      }  
      catch (Exception e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      textArea.append(newline+desc+newline);
    }

    public void showHelp(){
      hpframe.setVisible(true);
    } 
}

