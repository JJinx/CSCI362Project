/*

The Martus(tm) free, social justice documentation and
monitoring software. Copyright (C) 2010, Beneficent
Technology, Inc. (Benetech).

Martus is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either
version 2 of the License, or (at your option) any later
version with the additions and exceptions described in the
accompanying Martus license file entitled "license.txt".

It is distributed WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, including warranties of fitness of purpose or
merchantability.  See the accompanying Martus License and
GPL license for more details on the required license terms
for this software.

You should have received a copy of the GNU General Public
License along with this program; if not, write to the Free
Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA 02111-1307, USA.

*/
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
 
import org.martus.common.crypto.MartusSecurity;
import org.martus.util.inputstreamwithseek.ByteArrayInputStreamWithSeek;

public class testEncryptDecrypt
{
private static MartusSecurity security;
	public static void main(String [] args)
	{
		try 
		{
			new testEncryptDecrypt(args);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}	
	private testEncryptDecrypt(String[] input) throws Exception
	{
		security = new MartusSecurity();
		security.createKeyPair(512);
		
		StringBuilder builder = new StringBuilder();
		for (String word : input) 
		{
			if (builder.length() > 0)
			{
				builder.append(" ");
			}
			builder.append(word);
		}
		String inputString = builder.toString();
		byte[] data = inputString.getBytes();
		
		ByteArrayInputStream plainInputStream = new ByteArrayInputStream(data);
		ByteArrayOutputStream cipherOutputStream = new ByteArrayOutputStream();
		
		security.encrypt(plainInputStream, cipherOutputStream);
		plainInputStream.close();
		
		byte[] encrypted = cipherOutputStream.toByteArray();

		cipherOutputStream.close();
		ByteArrayInputStreamWithSeek cipherInputStream = new ByteArrayInputStreamWithSeek(encrypted);
		ByteArrayOutputStream plainOutputStream = new ByteArrayOutputStream();
		
		security.decrypt(cipherInputStream, plainOutputStream);
		cipherInputStream.close();
		
		String decrypted = plainOutputStream.toString(); 
		plainOutputStream.close();
		
		System.out.println("Decrypted string = \"" + decrypted + "\"");
	}
}
