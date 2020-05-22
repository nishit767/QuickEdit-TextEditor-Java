// Importing Various Inbuilt Packages and Corresponding Classes Used
import java.awt.Container;
import java.awt.Font;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;

// Importing Our Package and Classes
import EditorTools.FileOptions;
import EditorTools.EditOptions;
import EditorTools.Find;
import EditorTools.About;
import EditorTools.Help;
import EditorTools.Replace;
import EditorTools.FontSettings;
import EditorTools.Bold;
import EditorTools.Italic;
import EditorTools.Underline;

//Driver Class i.e. having Main
class QuickEdit
{
	public static void main(String[] args) 
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			MyTextEditor myedit = new MyTextEditor();
			myedit.texteditor();
		}
		catch(Exception e)
		{
			
		}
	}
}// End of Main

// Layout for Editor
class MyTextEditor extends JFrame implements ActionListener, KeyListener, WindowListener, MouseListener
{
	boolean tounderline=true;
	Container mycontentpane = getContentPane(); //This Container object will contain other components
	SpringLayout mylayout = new SpringLayout(); //For Layout
	JTextArea mytextarea = new JTextArea(); // Sets Text Area
	JScrollPane myscrollpane = new JScrollPane(mytextarea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	JMenuBar mymenubar = new JMenuBar(); // This menu bar will contain all Features
	File myfile = new File(System.getProperty("user.dir"));
	Font myfont = new Font("Arial",Font.PLAIN,15);
	FontSettings ft = new FontSettings(this,mytextarea);
	FileOptions fo = new FileOptions(myfile,mytextarea,this,ft);
	EditOptions eo = new EditOptions(mytextarea);
	Find fn = new Find(mytextarea);
	Replace rp = new Replace(mytextarea);
	Highlighter highlighter = mytextarea.getHighlighter();
	Help hp = new Help();
	About ab = new About();
	Italic il = new Italic(mytextarea);
	Bold bl = new Bold(mytextarea);

	//Main Editor Function
	void texteditor()
	{
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setTitle("New-QuickEdit");
		this.setSize(800,400); 
		this.setLocationRelativeTo(null);
		this.add(myscrollpane);
		this.addWindowListener(this);
		mytextarea.setLineWrap(true);
		mytextarea.addKeyListener(this);
		mytextarea.setFont(myfont);
		UIManager.put("Menu.font",myfont);
		this.setJMenuBar(mymenubar);
		mytextarea.addMouseListener(this);

		try
		{
			this.setIconImage(ImageIO.read(new File(System.getProperty("user.dir")+"\\EditorTools\\QE.png")));
		}
		catch(Exception e)
		{}

		JMenu m1 = new JMenu("File"); 
		JMenu m2 = new JMenu("Edit"); 
		JMenu m3 = new JMenu("Search"); 
		JMenu m4 = new JMenu("Font");
		JMenu m5 = new JMenu("Help");

		mymenubar.add(m1); 
		mymenubar.add(m2); 
		mymenubar.add(m3); 
		mymenubar.add(m4);
		mymenubar.add(m5);

		JMenuItem mi1[] = { 
			new JMenuItem("New"),
			new JMenuItem("Open"),
			new JMenuItem("Save"),
			new JMenuItem("Save As"),
			new JMenuItem("Rename"),
			new JMenuItem("Exit") 
		};
		for(int i=0;i<mi1.length;i++)  
			m1.add(mi1[i]);

		JMenuItem mi2[] = { 
			new JMenuItem("Cut"),
			new JMenuItem("Copy"),
			new JMenuItem("Paste"),
			new JMenuItem("Delete"),
			new JMenuItem("Undo"),
			new JMenuItem("Redo"),
			new JMenuItem("Select All"),
			new JMenuItem("Time Stamp"),
		};
		for(int i=0;i<mi2.length;i++)  
			m2.add(mi2[i]);

		JMenuItem mi3[] = {
			new JMenuItem("Find"),
			new JMenuItem("Replace")
		};
		for(int i=0;i<mi3.length;i++)  
			m3.add(mi3[i]);

		JMenuItem mi4[] = { 
			new JMenuItem("Font Settings"),
			new JMenuItem("Bold"),
			new JMenuItem("Italic"),
			new JMenuItem("Underline")
		};
		for(int i=0;i<mi4.length;i++)  
			m4.add(mi4[i]);

		JMenuItem mi5[] = { 
			new JMenuItem("Help"),
			new JMenuItem("About")
		};
		for(int i=0;i<mi5.length;i++)  
			m5.add(mi5[i]);

		for(int i=0;i<mi1.length;i++)
			mi1[i].addActionListener(this);
		for(int i=0;i<mi2.length;i++)
			mi2[i].addActionListener(this);
		for(int i=0;i<mi3.length;i++)
			mi3[i].addActionListener(this);
		for(int i=0;i<mi4.length;i++)
			mi4[i].addActionListener(this);
		for(int i=0;i<mi5.length;i++)
			mi5[i].addActionListener(this);

		setVisible(true);
	}//End of texteditor()

	public void windowClosing(WindowEvent we) 
	{
		if(!fo.saveflag && !fo.editflag)
			dispose();
		else if(fo.saveflag && !fo.editflag)
			dispose();
		else
		{
        	Object options[] = {"Save File","Don't Save","Cancel"};
			int optsel = JOptionPane.showOptionDialog(mytextarea,options[0],
						 "You have Unsaved Changes!",JOptionPane.YES_NO_CANCEL_OPTION,
						 JOptionPane.QUESTION_MESSAGE,null,options,options[2]);
						
			switch(optsel)
			{
				case 0:
					fo.savefile();
					break;
				case 1:
					dispose();
					break;
				case 2:
					break;
			}
		}
    }

    public void windowDeactivated(WindowEvent we)
    {}

    public void windowActivated(WindowEvent we)
    {}

    public void windowDeiconified(WindowEvent we)
    {}

    public void windowIconified(WindowEvent we)
    {}

    public void windowClosed(WindowEvent we)
    {}

    public void windowOpened(WindowEvent we)
    {}

	public void actionPerformed(ActionEvent ae)
	{
		String arg = (String)ae.getActionCommand();
		if(arg.equals("New"))
		{
			Boolean mynewflag = fo.newfile();
			if(!mynewflag)
			{
				MyTextEditor myedit1 = new MyTextEditor();
				myedit1.texteditor();		
			}
		}
		if(arg.equals("Open"))
			fo.openfile();
		if(arg.equals("Save"))
			fo.savefile();
		if(arg.equals("Save As"))
			fo.saveAsfile();
		if(arg.equals("Rename"))
			fo.renamefile();
		if(arg.equals("Exit"))
			fo.exitfile();

		if(arg.equals("Cut"))
			eo.cutText();
		if(arg.equals("Copy"))
			eo.copyText();
		if(arg.equals("Paste"))
			eo.pasteText();
		if(arg.equals("Delete"))
			eo.deleteText();
		if(arg.equals("Undo"))
			eo.undoText();
		if(arg.equals("Redo"))
			eo.redoText();
		if(arg.equals("Select All"))
			eo.selectAllText();
		if(arg.equals("Time Stamp"))
			eo.insertTimeStamp();

		if(arg.equals("Find"))
		{
			highlighter.removeAllHighlights();
			//Find fn=new Find();
			fn.findText();
		}
		if(arg.equals("Replace"))
		{
			highlighter.removeAllHighlights();
			rp.dialog1Method();
		}

		if(arg.equals("Font Settings"))
		{
			ft.fontset();
			tounderline=true;
		}
		if(arg.equals("Bold"))
		{
			bl.setBold();
			tounderline=true;
		}
		if(arg.equals("Italic"))
		{
			il.setItalic();
			tounderline=true;
		}
		if(arg.equals("Underline"))
		{
			new Underline(mytextarea,tounderline);
			tounderline = !tounderline;
		}

		if(arg.equals("Help"))
		{
			highlighter.removeAllHighlights();
			hp.showHelp();
		}
		if(arg.equals("About"))
		{
			highlighter.removeAllHighlights();
			ab.showAbout();
		}
	}

	public void keyPressed(KeyEvent ke)
	{
		if(ke.isControlDown() && ke.getKeyCode()==KeyEvent.VK_N)
		{
			Boolean mynewflag = fo.newfile();
			if(!mynewflag)
			{
				MyTextEditor myedit1 = new MyTextEditor();
				myedit1.texteditor();		
			}
		}
		if(ke.isControlDown() && ke.getKeyCode()==KeyEvent.VK_O)
			fo.openfile();
		if(ke.isControlDown() && ke.getKeyCode()==KeyEvent.VK_S)
			fo.savefile();
		if(ke.isControlDown() && ke.isShiftDown() && ke.getKeyCode()==KeyEvent.VK_S)
			fo.saveAsfile();
		if(ke.isControlDown() && ke.getKeyCode()==KeyEvent.VK_R)
			fo.renamefile();
		if(ke.isControlDown() && ke.isShiftDown() && ke.getKeyCode()==KeyEvent.VK_E)
			fo.exitfile();

		if(ke.isControlDown() && ke.getKeyCode()==KeyEvent.VK_X)
			eo.cutText();
		if(ke.isControlDown() && ke.getKeyCode()==KeyEvent.VK_C)
			eo.copyText();
		if(ke.isControlDown() && ke.getKeyCode()==KeyEvent.VK_DELETE)
			eo.deleteText();
		if(ke.isControlDown() && ke.getKeyCode()==KeyEvent.VK_Z)
			eo.undoText();
		if(ke.isControlDown() && ke.getKeyCode()==KeyEvent.VK_Y)
			eo.redoText();
		if(ke.isControlDown() && ke.getKeyCode()==KeyEvent.VK_A)
			eo.selectAllText();

		if(ke.isControlDown() && ke.getKeyCode()==KeyEvent.VK_F)
			fn.findText();
		if(ke.isControlDown() && ke.getKeyCode()==KeyEvent.VK_H)
			{rp.dialog1Method();}
		if(ke.isControlDown() && ke.getKeyCode()==KeyEvent.VK_B)
			bl.setBold();
		if(ke.isControlDown() && ke.getKeyCode()==KeyEvent.VK_I)
			il.setItalic();
		if(ke.isControlDown() && ke.getKeyCode()==KeyEvent.VK_U)
			{
				new Underline(mytextarea,tounderline);
				tounderline = !tounderline;}	
			}
	public void keyTyped(KeyEvent ke)
	{}
	public void keyReleased(KeyEvent ke)
	{}

	public void mouseClicked(MouseEvent e)
	{}

	public void mouseExited(MouseEvent e)
	{}

	public void mouseReleased(MouseEvent e){}

	public void mouseEntered(MouseEvent e){}

	public void mousePressed(MouseEvent e){
		highlighter.removeAllHighlights();
	}
}// End of MyTextEditor