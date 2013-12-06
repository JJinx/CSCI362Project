//testing to see if a Bulletin is Sealed by default works

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;
//below is newly added shit
import org.martus.common.bulletin.BulletinConstants;
import org.martus.common.database.DatabaseKey;
import org.martus.common.packet.UniversalId;
import org.martus.common.packet.*;
import org.martus.util.*;

public class testDatabaseKeySealed
{	//if one of the methods you're using in MAIN throws an exception, you must declare main to throw an exception as well
	public static void main(String [] args){
		//i'm doing the try/catch style as Bob had in testCase1 b/c to avoid runtime errors
		boolean sealedVal = Boolean.valueOf(args[0]);
		//below we use the two arguments as dummy variables in order to create a random universalId
		UniversalId uid = UniversalId.createFromAccountAndLocalId("1222", "2222");
		DatabaseKey key = DatabaseKey.createSealedKey(uid);
		try 
		{	
			new testDatabaseKeySealed(key);		
		} 
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	private testDatabaseKeySealed(DatabaseKey thisKey) throws Exception{
		System.out.println(thisKey.isSealed());
	}	
	
}