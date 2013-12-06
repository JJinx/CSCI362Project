import java.io.File;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.FileWriter;

import org.martus.common.MartusUtilities.FileVerificationException;
import org.martus.common.crypto.MartusCrypto;
import org.martus.common.crypto.MockMartusSecurity;
import org.martus.common.database.Database;
import org.martus.common.database.DatabaseKey;
import org.martus.common.database.FileDatabase;
import org.martus.common.packet.UniversalId;
import org.martus.util.TestCaseEnhanced;
import org.martus.common.test.UniversalIdForTesting;

public class testDatabaseRecordCreateDelete
{
	public static void main(String[] args)
	{
		new testDatabaseRecordCreateDelete(args);
	}
	private testDatabaseRecordCreateDelete(String[] input)
	{
		try
		{
		for (int i = 0; i < input.length; i++)
		{
			if( input[i].startsWith("--entries-to-create") )
			{
				numRecords = Integer.parseInt(input[i].substring(input[i].indexOf("=")+1));
			}
			
			if( input[i].startsWith("--to-store") )
			{
				sampleString1 = input[i].substring(input[i].indexOf("=")+1);
			}
		}
		keys = new DatabaseKey[numRecords];
		tce = new TestCaseEnhanced("");
		security = MockMartusSecurity.createClient();
		File dir = new File("./temp/$$$MartusTestFileDatabaseSetup");
		dir.mkdir();
		db = new TestFileDatabase(dir, security);
		db.initialize();
		if (!(getRecordCount()==0))
		{
			System.out.println("Initialized database non-empty.");
			System.exit(0);
		}
		else
		{
			for (int i = 0; i < numRecords; i++)
			{
				keys[i] = DatabaseKey.createSealedKey(UniversalIdForTesting.createFromAccountAndPrefix(accountString1 , "x"));
				db.writeRecord(keys[i], sampleString1);
			}
			if (!(getRecordCount()==numRecords))
			{
				System.out.println("Incorrect record count after writing");
				System.exit(0);
			}
			for (int i = 0; i < numRecords; i++)
			{
				db.discardRecord(keys[i]);
			}
			System.out.println("Database record count = " + getRecordCount());
		}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	class TestFileDatabase extends FileDatabase
	{
		public TestFileDatabase(File directory, MartusCrypto securityToUse) 
		{
			super(directory, securityToUse);
		}
		public void verifyAccountMap() throws FileVerificationException, MissingAccountMapSignatureException 
		{
		}
		
		protected DatabaseKey getDatabaseKey(File accountDir, String bucketName, UniversalId uid)
		{
			return DatabaseKey.createSealedKey(uid);
		}
		protected String getBucketPrefix(DatabaseKey key)
		{
			return defaultBucketPrefix;
		}
	}
	int getRecordCount()
	{
		class PacketCounter implements Database.PacketVisitor
		{
			public void visit(DatabaseKey key)
			{
				++count;
			}

			int count;
		}

		PacketCounter counter = new PacketCounter();
		db.visitAllRecords(counter);
		return counter.count;
	}
TestCaseEnhanced tce;
MockMartusSecurity security;
TestFileDatabase db;
File dir;
String accountString1 = "acct1";
DatabaseKey keys[];
int numRecords = 0;
String sampleString1 = "This is just a little bit of data as a sample";
}
