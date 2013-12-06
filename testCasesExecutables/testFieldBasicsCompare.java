//testing to see if a Bulletin is Sealed by default works

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;
//below is newly added shit
import org.martus.common.GridData;
import org.martus.common.field.*;
import org.martus.common.MiniLocalization;
import org.martus.common.PoolOfReusableChoicesLists;
import org.martus.common.fieldspec.ChoiceItem;
import org.martus.common.fieldspec.DropDownFieldSpec;
import org.martus.common.fieldspec.FieldSpec;
import org.martus.common.fieldspec.FieldType;
import org.martus.common.fieldspec.FieldTypeAnyField;
import org.martus.common.fieldspec.FieldTypeBoolean;
import org.martus.common.fieldspec.FieldTypeDate;
import org.martus.common.fieldspec.FieldTypeDateRange;
import org.martus.common.fieldspec.FieldTypeLanguage;
import org.martus.common.fieldspec.FieldTypeMessage;
import org.martus.common.fieldspec.FieldTypeMultiline;
import org.martus.common.fieldspec.FieldTypeNormal;
import org.martus.common.fieldspec.GridFieldSpec;
import org.martus.common.fieldspec.GridFieldSpec.UnsupportedFieldTypeException;
import org.martus.common.utilities.MartusFlexidate;
import org.martus.util.MultiCalendar;
import org.martus.util.*;

public class testFieldBasicsCompare
{	//if one of the methods you're using in MAIN throws an exception, you must declare main to throw an exception as well
	public static void main(String [] args) {
		//i'm doing the try/catch style as Bob had in testCase1 b/c to avoid runtime errors
		MiniLocalization localization;
		PoolOfReusableChoicesLists noReusableChoices =PoolOfReusableChoicesLists.EMPTY_POOL;
		// BELOW is not hard coded. Martus enters this statement as it appears below so that it may return "tag" or "label" when 
		//.....a user attempts to get a type. The intent is for both FieldSpec and MartusField to have the the same attributes once a FieldSpec is set
		FieldSpec spec = FieldSpec.createCustomField(args[0], args[1], new FieldTypeNormal());
		MartusField mf = new MartusField(spec, noReusableChoices);
		/*
		assertEquals("wrong tag?", spec.getTag(), f.getTag());
		assertEquals("wrong label?", spec.getLabel(), f.getLabel());
		assertEquals("wrong type?", spec.getType(), f.getType());
		assertEquals("not initially blank?", "", f.getData());
		*/
		try{
			new testFieldBasicsCompare(spec,mf);
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}
	public testFieldBasicsCompare(FieldSpec thisSpec, MartusField thisField){
		boolean labelResult = (thisSpec.getLabel() == thisField. getLabel());		//TRUE if they're same
		boolean tagResult = (thisSpec.getTag() == thisField.getTag());			//TRUE if they're same
		boolean diffLabelTag = ((thisSpec.getTag() != thisSpec.getLabel()) && (thisField.getLabel() != thisField.getTag()));
		System.out.println(diffLabelTag);
	}
	
}