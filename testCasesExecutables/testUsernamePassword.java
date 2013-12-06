import org.martus.client.core.MartusUserNameAndPassword;
import org.martus.common.Exceptions.BlankUserNameException;
import org.martus.common.Exceptions.MartusClientApplicationException;
import org.martus.common.Exceptions.PasswordMatchedUserNameException;
import org.martus.common.Exceptions.PasswordTooShortException;

public class testUsernamePassword
{
	public static void main(String [] args)
	{				
		new testUsernamePassword(args);
	}
	public testUsernamePassword(String[] input)
	{
		try
		{
			String username = "";
			String password = "";
			for (int i = 0; i < input.length; i++)
			{
				if( input[i].startsWith("--username") )
				{
					username = input[i].substring(input[i].indexOf("=")+1);
				}
			
				if( input[i].startsWith("--password") )
				{
					password = input[i].substring(input[i].indexOf("=")+1);
				}
			}
			
			MartusUserNameAndPassword.validateUserNameAndPassword(username, password.toCharArray());
			System.out.println("Username and password are valid.");
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}	
}
