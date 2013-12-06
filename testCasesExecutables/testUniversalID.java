import org.martus.common.packet.UniversalId;
import org.martus.common.test.UniversalIdForTesting;

public class testUniversalID
{
	public static void main(String[] args)
	{
		try
		{
			new testUniversalID(args);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	public testUniversalID(String[] input) throws Exception
	{
		String accountID = "";
		String localID = "";
		String prefix = "";
		boolean fromString = false;
		UniversalId uid;

		for (int i = 0; i < input.length; i++)
		{
			if( input[i].startsWith("--account-id") )
			{
				accountID = input[i].substring(input[i].indexOf("=")+1);
			}
			
			if( input[i].startsWith("--local-id") )
			{
				localID = input[i].substring(input[i].indexOf("=")+1);
			}
			
			if( input[i].startsWith("--prefix") )
			{
				prefix = input[i].substring(input[i].indexOf("=")+1);
			}
			
			if( input[i].startsWith("--from-string") )
			{
				fromString = Boolean.valueOf(input[i].substring(input[i].indexOf("=")+1));
			}
		}
		if (!fromString && (prefix.length() == 0))
		{
			uid = UniversalId.createFromAccountAndLocalId(accountID, localID);
			System.out.println("Account ID = " + uid.getAccountId() + " & Local ID = " + uid.getLocalId());
		}
		else if (prefix.length() != 0)
		{
			uid = UniversalIdForTesting.createFromAccountAndPrefix(accountID, prefix);
			System.out.println("Account ID = " + uid.getAccountId() + " & Local ID length = " + uid.getLocalId().length());
		}
		else
		{
			uid = UniversalId.createFromString(UniversalId.createFromAccountAndLocalId(accountID, localID).toString());
			System.out.println("Account ID = " + uid.getAccountId() + " & Local ID = " + uid.getLocalId());
		}
	}
}
