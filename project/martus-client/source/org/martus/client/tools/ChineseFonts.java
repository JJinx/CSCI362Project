/*

The Martus(tm) free, social justice documentation and
monitoring software. Copyright (C) 2005-2007, Beneficent
Technology, Inc. (The Benetech Initiative).

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

package org.martus.client.tools;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Vector;

public class ChineseFonts
{

	public static void main(String[] args)
	{
		// Determine which fonts support Chinese here ...
		Vector chinesefonts = new Vector();
		Font[] allfonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		int fontcount = 0;
		String chinesesample = "\u4e00";
		for (int j = 0; j < allfonts.length; j++) 
		{
			if (allfonts[j].canDisplayUpTo(chinesesample) == -1) 
			{ 
			    chinesefonts.add(allfonts[j].getFontName());
			}
			fontcount++;
		}
		
		int count = chinesefonts.size();
		System.out.println("Found " + count + " chinese-capable fonts:");
		for(int i=0; i < count; ++i)
		{
			System.out.println(chinesefonts.get(i));
		}
	}
}