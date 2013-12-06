/*

The Martus(tm) free, social justice documentation and
monitoring software. Copyright (C) 2001-2007, Beneficent
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

To the extent this copyrighted software code is used in the 
Miradi project, it is subject to a royalty-free license to 
members of the Conservation Measures Partnership when 
used with the Miradi software as specified in the agreement 
between Benetech and WCS dated 5/1/05.
*/
package org.martus.swing;

import java.awt.Component;
import java.awt.ComponentOrientation;

import javax.swing.SwingConstants;

import org.martus.util.language.LanguageOptions;


public class UiLanguageDirection
{
	static public ComponentOrientation getComponentOrientation()
	{
		if(LanguageOptions.isRightToLeftLanguage())
			return ComponentOrientation.RIGHT_TO_LEFT;
		return ComponentOrientation.LEFT_TO_RIGHT;
	}
	
	static public int getHorizontalAlignment()
	{
		if(LanguageOptions.isRightToLeftLanguage())
			return SwingConstants.RIGHT;
		return SwingConstants.LEFT;
	}
	
	static public float getAlignmentX()
	{
		if(LanguageOptions.isRightToLeftLanguage())
			return Component.RIGHT_ALIGNMENT;
		return Component.LEFT_ALIGNMENT;
	}
}
