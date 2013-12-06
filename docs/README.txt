*********************************************************
*		       Overview				*
*********************************************************

The testing framework is packaged with .java test files 
for the open source project Martus (www.Martus.org), but
can easily be adapted to test any Java project. The 
framework uses text files to define test cases to run,
and creates a detailed report for each test run.

*********************************************************
*		     Requirements			*
*********************************************************

The framework should run in any Unix environment and the
Java version compatibility is entirely dependant on the 
methods used in your classes. However, testing of the
framework with the pre-packaged class files has been
limited to Ubuntu 12.04/Java 1.6.

*********************************************************
*		      How to Use			*
*********************************************************

Using terminal, navigate to the top level folder (/team4 
by default) and type 'sh runAllTests.sh'. This will 
execute all available test cases, and, upon completion,
will open the report file created in '/team4/reports'.

*********************************************************
*	     Creating Your Own Test Cases		*
********************************************************* 

The framework is made extensible through the use of test
case plain text files. Once you have written a test in 
Java it is a simple matter of creating a test case with
the necessary parameters to add the new test to the suite.
Test case files must be named using the convention 
'testCaseX.txt' where X is any number. The following 
parameters are contained in a test case file:

    testNumber=		(required) Must be unique, conventionally the same number used in the file name.
    comments=		(optional) Comments displayed during test compilation/running.
    requirement=	(optional) The requirement being tested, used in the report file.
    methodTested=	(optional) The method being tested, used in the report file.
    testDriverPath=	(required) The path to the .java file that runs the test.
    testDriver=		(required) The name of the .java file that runs the test.
    compileClassPath=	(required) The classpath entries needed to compile. Exactly as they would be typed
			in the terminal. e.g. '-cp /cp/entry/one:/cp/entry/two' see Java documentation
			for further help in using the classpath flag.
    runClassPath=	(required) The classpath entries needed to run. Entered as above.
    input=		(optional) The input, if any, used by your Java test class. Input is passed to the class
			exactly as typed. The only exception being that input will accept the relative path to 
			a .txt file as input, and retrieve literal input from the file. Any spaces in the input
			parameter will be interpreted by your Java main class as delimiting String array 
			elements. String building must be handled in your Java class.
    expectedOutput=	(required) The expected output of your Java class. Determines test pass or fail status.
