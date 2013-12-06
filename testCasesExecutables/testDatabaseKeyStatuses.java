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

public class testDatabaseKeyStatuses
{	//if one of the methods you're using in MAIN throws an exception, you must declare main to throw an exception as well
	public static void main(String [] args){
		//i'm doing the try/catch style as Bob had in testCase1 b/c to avoid runtime errors
		boolean sealedVal = Boolean.valueOf(args[0]);
		//below we use the two arguments as dummy variables in order to create a random universalId
		UniversalId uid1 = UniversalId.createFromAccountAndLocalId("1222", "2222");
		DatabaseKey key1 = DatabaseKey.createSealedKey(uid1);

		UniversalId uid2 = UniversalId.createFromAccountAndLocalId("1333", "2333");
		DatabaseKey key2 = DatabaseKey.createDraftKey(uid2);
		try 
		{	
			new testDatabaseKeyStatuses(key1, key2);		
		} 
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	private testDatabaseKeyStatuses(DatabaseKey thisKey, DatabaseKey thatKey) throws Exception{
		System.out.println(thisKey.isSealed() == thatKey.isSealed());
	}	
	
}