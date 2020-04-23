package EditorTools;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.Highlighter; 
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
public class Find extends JFrame {

	
	JTextArea fta;
	JTextField txtFind = new JTextField(50);
	JLabel mylabel = new JLabel("Enter Text to Search:");
	JPanel mypanel = new JPanel();
	JOptionPane mypane = new JOptionPane();
	//If text is found or not in the search
	public int flag=0;

	public Find(JTextArea ta)
	{
		fta = ta;
		mypanel.add(mylabel,BorderLayout.WEST);
		mypanel.add(txtFind,BorderLayout.CENTER);
	} 

	public void findText()
	{
		Object options[] = {"Find","Cancel"};
		int optsel = JOptionPane.showOptionDialog(fta,mypanel,"Find",
					 JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
		switch(optsel)
		{
			case 0:
				String input,pattern;
				pattern = txtFind.getText();
				input = fta.getText();
				char txt[] = input.toCharArray(); 
				char pat[] = pattern.toCharArray(); 
				search(txt, pat, fta);
				return;
			case 1:
				return;
		}
	}


	int NO_OF_CHARS = 256; 
	int max (int a, int b) { return (a > b)? a: b; } 
	void badCharHeuristic( char []str, int size,int badchar[]) 
	{ 
		int i; 
		for (i = 0; i < NO_OF_CHARS; i++) 
			badchar[i] = -1;
		for (i = 0; i < size; i++) 
			badchar[(int) str[i]] = i; 
	}
	void search( char txt[], char pat[], JTextArea area) 
	{ 
		flag=0;
		int m = pat.length; 
		int n = txt.length; 

		int badchar[] = new int[NO_OF_CHARS]; 
		badCharHeuristic(pat, m, badchar); 

		int s = 0; // s is shift of the pattern with 
					// respect to text 
		while(s <= (n - m)) 
		{ 
			int j = m-1; 

			while(j >= 0 && pat[j] == txt[s+j]) 
				j--; 

			if (j < 0) 
			{ 
				flag=1;
				//System.out.println("Patterns occur at shift = " + s); 
				try 
				{
				    Highlighter highlighter = area.getHighlighter();
				    HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.BLUE);
				    highlighter.addHighlight(s , s+m, painter);
				}
				catch(Exception ex) 
				{
					ex.printStackTrace();
				}
				s += (s+m < n)? m-badchar[txt[s+m]] : 1; 

			} 

			else
				s += max(1, j - badchar[txt[s+j]]); 
		}

		//if word is not found it will display a dialog box showing word not found
		if(flag==0)
		{
			mypane.showMessageDialog(fta,"Text Not Found","ERROR!!",JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			return;
		}
	} 
} 
