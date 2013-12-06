import org.martus.client.core.MartusUserNameAndPassword;
import org.martus.common.Exceptions.BlankUserNameException;
import org.martus.common.Exceptions.MartusClientApplicationException;
import org.martus.common.Exceptions.PasswordMatchedUserNameException;
import org.martus.common.Exceptions.PasswordTooShortException;

public class testWeakPassword
{
	public static void main(String[] args)
	{
		try
		{
			new testWeakPassword(args);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	private testWeakPassword(String[] input) throws Exception
	{
	String testPass = "";
	for (int i = 0; i < input.length; i++)
	{
		if( input[i].startsWith("--password") )
		{
			testPass = input[i].substring(input[i].indexOf("=")+1);
		}
	}
	if (!MartusUserNameAndPassword.isWeakPassword(testPass.toCharArray()))
	{
		System.out.println("Strong password");
	}
	else
	{
		System.out.println("Weak password");
	}
	}
}
