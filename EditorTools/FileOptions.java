package EditorTools;
// Importing Packages that will be used for File Operations
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import java.io.File; 
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.awt.Font;
// Importing Required Package Files
import EditorTools.FileSecurityOptions;
import EditorTools.FontSettings;

public class FileOptions implements DocumentListener
{
	JFrame myframe;
	JTextArea fta;
	File fmyfile;
	JFileChooser myfilechooser = new JFileChooser();
	JOptionPane myoptionpane = new JOptionPane();
	FileSecurityOptions fso;
	FontSettings ft;
	public Boolean saveasflag = false, saveflag = false, editflag = false;
	String extension = "", mypassword = "";

	public FileOptions(File fob, JTextArea ta, JFrame jf, FontSettings ft1)
	{
		fmyfile = fob;
		ft = ft1;
		myframe = jf;
		fta = ta;	
		fso = new FileSecurityOptions(ta,myframe);
		fta.getDocument().addDocumentListener(this);

		myfilechooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter myfilefilter = new FileNameExtensionFilter("Password Protected File","ppf");
		myfilechooser.setFileFilter(myfilefilter);
	}

	public void changedUpdate(DocumentEvent de)
	{}
	public void removeUpdate(DocumentEvent de)
	{}
	public void insertUpdate(DocumentEvent de)
	{
		saveflag = false;
		editflag = true;
	}

	public Boolean newfile()
	{
		if(!saveflag)
		{
			if(!editflag)
				myoptionpane.showMessageDialog(fta,"New File is Already Open!"); 
			else
			{
				Object options[] = {"Save File","Don't Save","Cancel"};
				int optsel = myoptionpane.showOptionDialog(fta,options[0],
							 "You have Unsaved Changes!",myoptionpane.YES_NO_CANCEL_OPTION,
							 myoptionpane.QUESTION_MESSAGE,null,options,options[2]);
				
				switch(optsel)
				{
					case 0:
						savefile();
						return false;
					case 1:
						myframe.dispose();
						return false;
					case 2:
						return true;
				}
			}
			return true;
		}
		else
			return false;
	}

	public void openfile()
	{
		try
		{
			myfilechooser.setDialogTitle("Select File to Open");
			int fcval = myfilechooser.showOpenDialog(null);

			if(fcval == JFileChooser.APPROVE_OPTION)
			{
				fmyfile = myfilechooser.getSelectedFile();
				if(fmyfile.exists())
				{
					int i = fmyfile.toString().lastIndexOf(".");
					if(i>0)
						extension = fmyfile.toString().substring(i+1);
					if(extension.equals("ppf"))
					{
						Properties prop = new Properties();
						try(InputStream input = new FileInputStream("config.properties")) 
						{
      						prop.load(input);
      						String fontname = prop.getProperty(fmyfile.getAbsolutePath()+"n");
      						int fontstyle = Integer.parseInt(prop.getProperty(fmyfile.getAbsolutePath()+"s"));
      						int fontsize = Integer.parseInt(prop.getProperty(fmyfile.getAbsolutePath()+"sz"));
      						Font filefont = new Font(fontname,fontstyle,fontsize);
      						fta.setFont(filefont);
    					} 
    					catch(Exception ex) 
    					{

    					}
						mypassword = fso.getPassword();
						boolean x = fso.decryptFileData(mypassword,fmyfile);
						if(x)
						{
							BufferedReader mybr = new BufferedReader(new FileReader(fmyfile));
							String myfcont = "", line = "";
							while((line = (String)mybr.readLine()) != null)
								myfcont += line+"\n";
							mybr.close();
							fta.setText(myfcont);
							fso.encryptFileData(mypassword, fmyfile);
							myframe.setTitle(fmyfile.getName());
							saveasflag = true;
							saveflag = true;
							editflag = false;
						}
						else
						{
							JOptionPane.showMessageDialog(fta, "Wrong Password Cannot Decrypt File!!", "Error!", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					else
					{
						myoptionpane.showMessageDialog(myfilechooser,"Application Can only open .ppf Files!!");
						return;
					}
				}
				else
				{
					myoptionpane.showMessageDialog(myfilechooser,"File Doesnot Exist!","ERROR!!",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			else
				return;
		}
		catch(Exception e)
		{
			//
		}
	}

	public void savefile()
	{
		if(!saveasflag)
		{
			saveAsfile();
		}
		else
		{
			try
			{
				BufferedWriter mybwr = new BufferedWriter(new FileWriter(fmyfile));
				mybwr.write(fta.getText());
                mybwr.close();
                fso.encryptFileData(mypassword,fmyfile);
                myframe.setTitle(fmyfile.getName());
                saveflag = true;
                editflag = false;
                try(OutputStream ops = new FileOutputStream(System.getProperty("user.dir")+"\\config.properties"))
				{
					Properties prop = new Properties();
					prop.setProperty(fmyfile.getAbsolutePath()+"n",fta.getFont().getName());
					prop.setProperty(fmyfile.getAbsolutePath()+"s",""+fta.getFont().getStyle());
					prop.setProperty(fmyfile.getAbsolutePath()+"sz",""+fta.getFont().getSize());
					prop.store(ops,null);
				}
				catch(Exception e)
				{
					//
				}
			}
			catch(Exception e)
			{

			}
		}
	}

	public void saveAsfile()
	{
		if(saveflag)
		{
			try
			{
				myfilechooser.setDialogTitle("Save As");
				int fcval = myfilechooser.showSaveDialog(null);
				if(fcval == JFileChooser.APPROVE_OPTION)
				{ 
					fmyfile = myfilechooser.getSelectedFile();
					int i = fmyfile.toString().lastIndexOf(".");
					if(i>0)
						extension = fmyfile.toString().substring(i+1);
					if(!extension.equals("ppf"))
						fmyfile = new File(fmyfile.toString()+".ppf");
					BufferedWriter mybwr = new BufferedWriter(new FileWriter(fmyfile));
					mybwr.write(fta.getText());
                	mybwr.close();
					fso.encryptFileData(mypassword,fmyfile);
					myframe.setTitle(fmyfile.getName());
					saveasflag = true;
					saveflag = true;
					editflag = false;
					try(OutputStream ops = new FileOutputStream(System.getProperty("user.dir")+"\\config.properties"))
					{
						Properties prop = new Properties();
						prop.setProperty(fmyfile.getAbsolutePath()+"n",fta.getFont().getName());
						prop.setProperty(fmyfile.getAbsolutePath()+"s",""+fta.getFont().getStyle());
						prop.setProperty(fmyfile.getAbsolutePath()+"sz",""+fta.getFont().getSize());
						prop.store(ops,null);
					}
					catch(Exception e)
					{
						//
					}
				}
				else
					return;
			}  
			catch(Exception e)
			{

			}
		}
		else
		{
			try
			{ 
				myfilechooser.setDialogTitle("Save As");
				int fcval = myfilechooser.showSaveDialog(null);
				if(fcval == JFileChooser.APPROVE_OPTION)
				{
					fmyfile = myfilechooser.getSelectedFile();
			
					int i = fmyfile.toString().lastIndexOf(".");
					if(i>0)
						extension = fmyfile.toString().substring(i+1);
					if(!extension.equals("ppf"))
						fmyfile = new File(fmyfile.toString()+".ppf");

					mypassword = fso.setPassword();
					BufferedWriter mybwr = new BufferedWriter(new FileWriter(fmyfile));
					mybwr.write(fta.getText());
                	mybwr.close();
					fso.encryptFileData(mypassword,fmyfile);
					myframe.setTitle(fmyfile.getName());
					saveasflag = true;
					saveflag = true;
					editflag = false;
					try(OutputStream ops = new FileOutputStream(System.getProperty("user.dir")+"\\config.properties"))
					{
						Properties prop = new Properties();
						prop.setProperty(fmyfile.getAbsolutePath()+"n",fta.getFont().getName());
						prop.setProperty(fmyfile.getAbsolutePath()+"s",""+fta.getFont().getStyle());
						prop.setProperty(fmyfile.getAbsolutePath()+"sz",""+fta.getFont().getSize());
						prop.store(ops,null);
					}
					catch(Exception e)
					{
						//
					}
				}
				else
					return;
			}  
			catch(Exception e)
			{}
		}
	}

	public void renamefile()
	{
		if(saveasflag)
		{
			try
			{
				String newname;
				newname = myoptionpane.showInputDialog(fta,"Enter New Name for File");
				extension = "";
				int i = newname.lastIndexOf(".");
				if(i>0)
					extension = fmyfile.toString().substring(i+1);
				if(!extension.equals("ppf"))
					newname = newname+".ppf";
	            ProcessBuilder builder = new ProcessBuilder();
    	        builder.directory(new File(fmyfile.getParent()));
        	    builder.command("cmd.exe", "/c","ren \""+fmyfile.getName()+"\" \""+newname+"\"");
            	builder.start();
            	fmyfile = new File(fmyfile.getParent()+"\\"+newname);
            	myframe.setTitle(fmyfile.getName());
			}
			catch(Exception e)
			{}
		}
		else
			myoptionpane.showMessageDialog(fta,"File Has yet not been Saved! Cannot Rename","ERROR!!",JOptionPane.ERROR_MESSAGE);
	}	

	public void exitfile()
	{
		if(!saveflag && !editflag)
			myframe.dispose();
		else if(saveflag && !editflag)
			myframe.dispose();
		else
		{
			Object options[] = {"Save File","Don't Save","Cancel"};
			int optsel = myoptionpane.showOptionDialog(fta,options[0],
				"You have Unsaved Changes!",JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,null,options,options[2]);
				
			switch(optsel)
			{
				case 0:
					savefile();
					break;
				case 1:
					myframe.dispose();
					break;
				case 2:
					break;
			}
		}
	}
}