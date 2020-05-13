package EditorTools;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class FileSecurityOptions
{
	JFrame passframe;
	JTextArea pta;
	JPasswordField mypf = new JPasswordField(40);
	private static final String ALGORITHM = "Blowfish";
	private static String keyString = "";
	private String mypass = "";
	public Boolean passwordflag = false;

	FileSecurityOptions(JTextArea ta, JFrame fr)
	{
		passframe = fr;
		pta = ta;
	}

	public String setPassword()
	{
		int pass = JOptionPane.showConfirmDialog(pta, mypf, "Set Password: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if(pass == JOptionPane.OK_OPTION)
		{ 
  			char[] gpass = mypf.getPassword();
  			mypass = String.valueOf(gpass);
  			passwordflag = true;
		}
		else
		{
			JOptionPane.showMessageDialog(passframe,"Entering Password is Mandatory","WARNING!!!",JOptionPane.WARNING_MESSAGE);
			mypass = this.setPassword();
		}
		return mypass;
	}

	public String getPassword()
	{
		int pass = JOptionPane.showConfirmDialog(pta, mypf, "Enter Password: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if(pass == JOptionPane.OK_OPTION)
		{ 
  			char[] gpass = mypf.getPassword();
  			mypass = String.valueOf(gpass);
		}
		else
		{
			JOptionPane.showMessageDialog(passframe,"Entering Password is Mandatory","WARNING!!!",JOptionPane.WARNING_MESSAGE);
			mypass = this.getPassword();
		}
		return mypass;
	}

	public boolean encryptFileData(String strkey, File filetobeenc)
	{
		keyString = strkey;
		return doCrypto(Cipher.ENCRYPT_MODE, filetobeenc);
	}
	
	public boolean decryptFileData(String strkey, File filetobedec)
	{
		keyString = strkey;
		return doCrypto(Cipher.DECRYPT_MODE, filetobedec);
	}
	
	public boolean doCrypto(int cipherMode, File filepassed)
	{
		try
		{
			FileInputStream fisob = new FileInputStream(filepassed);
			Key skeyspec = new SecretKeySpec(keyString.getBytes(),ALGORITHM);
			Cipher myciob = Cipher.getInstance(ALGORITHM);
			myciob.init(cipherMode, skeyspec);
			byte[] inpbytes = new byte[(int) filepassed.length()];
			fisob.read(inpbytes);
			fisob.close();
			byte[] opbytes = myciob.doFinal(inpbytes);
			FileOutputStream fosob = new FileOutputStream(filepassed);
			fosob.write(opbytes);
			fosob.close();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
