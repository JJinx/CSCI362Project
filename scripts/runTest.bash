#!/bin/bash

##function grabs data from testCase.txt files
getData()
{
label=$1
token=$label"=[[:blank:]*[:print:]+]" #token contains label text followed by any number of spaces and at least one printable character
line=$(echo "$fileContents" | egrep $token) #search for line containing token
echo ${line#"$label="} #return line with label text stripped
}

##function creates table row in resultFile for each test case
outputResult()
{
status=$1
echo '<tr class="'$status'"><td>#'$testNumber'</td><td><div id="wrapper"><img class="hover" src="../reports/img/'$status'.png" /><p class="reason">'$reason'</p></div></td><td class="variable">'$requirement'</td><td>'$method'</td><td>'$input'</td><td>'$expectedOutput'</td></tr>' >> "$reportFile"
}

#user defined script for building project that framework tests
buildScript="./scripts/buildProject.bash"
projectName=$(cat ./scripts/projectName.txt)
##if buildScript exists, prompt user to build or skip
if [ -e $buildScript ]
then
	while true; do
    read -p "Clean build $projectName? [y/n]" answer
    case $answer in
        [Yy]* ) bash $buildScript; break;;
        [Nn]* ) break;;
        * ) echo "Please answer yes or no.";;
    esac
	done
fi
rm -rf ./testCasesExecutables/*.class #clean build all test case executables
rm -rf ./temp/* #empty temp directory
rm -rf ./oracles/* #empty oracle directory
##create and initialize reportFile
reportFile="./reports/testReport_$(date +%m-%d-%Y)_$(date +%r).html"
echo "<link rel='stylesheet' type='text/css' href='report.css' media='screen' />" >> "$reportFile"
echo "<div class='header'><h1>Test Report: $(date +%m/%d/%Y) $(date +%r)</h1><br><a href='./'>See All</a></div>" >> "$reportFile"
echo "<table class='results' cellspacing=0><tr><th class='case'>Test Case</th><th class='status'>Result</th><th class='req'>Requirement</th><th class='method'>Method(s) Tested</th><th class='input'>Input Used</th><th class='output'>Output Expected</th></tr>" >> "$reportFile"

##loops through files in ./testCases that match extended regex and are sorted by their numbering
for testCase in $(ls ./testCases | egrep "^testCase[[:digit:]]+.txt$" | sort -n -k3 -te); do

	fileContents=$(cat "./testCases/"$testCase)

	##get data from testCase.txt
	testNumber=$(getData "testNumber") #get number of test case
	comments=$(getData "comments") #get comments	
	requirement=$(getData "requirement") #get requirement test covers
	method=$(getData "methodTested") #get method under test
	driverPath=$(getData "testDriverPath") #get test case path of .java file
	driver=$(getData "testDriver") #get test case .java file name
	classPath="-cp "$(getData "classPath") #get test case classpath entries
	input=$(getData "input") #get test case input
	expectedOutput=$(getData "expectedOutput") #get test case expected output

	#create oracle file with expected output
	echo $expectedOutput > "./oracles/testCase"$testNumber"Oracle.txt"

	#print comments on newline
	echo -e "\n"$comments

	##compile if .class file doesn't exist
	if [ ! -e "./testCasesExecutables/"$driver".class" ]
	then
		#concatenate full path of file to be compiled
		compilePath=$driverPath$driver".java"
		echo -n "Compiling test case #"$testNumber"..."

		#compile driver and place any stdout or stderr in $compilationResult
		compilationResult=$("javac" $classPath $compilePath 2>&1)
		
		##if compilation returned errors, fail with reason
		if [ ! $? -eq 0 ]
		then
			echo "FAIL"
			reason="Compilation error: "$compilationResult			
			failures[$testNumber]=$reason
			outputResult "fail"
			continue
		fi

		echo "DONE" #successfully compiled

	else
		echo "Driver has already been compiled..."
	fi

	echo -n "Running test case #"$testNumber"..."

	##if input parameter is a text file, get input from file	
	if echo $input | grep -q "[[:print:]+].txt$";
	then
		##check that file actually exists or fail with reason
		if [ -e "$input" ]
		then
			input=$(cat $input)
		else
			echo "FAIL"
			reason="Input file not found: "$input
			outputResult "fail"
			continue
		fi
	
	fi

	#Run driver and route both stdout and stderr to result file
	"java" $classPath $driver $input &> "./temp/testCase"$testNumber"Result.txt"

	##if last command returned code other than zero, fail with reason
	if [ ! $? -eq 0 ]
	then
		echo "FAIL"
		reason="Run-time error: "$(cat "./temp/testCase"$testNumber"Result.txt")
		outputResult "fail"
		continue
	fi

	echo "DONE" #successfully run

	resultFile="./temp/testCase"$testNumber"Result.txt"
	oracleFile="./oracles/testCase"$testNumber"Oracle.txt"

	#get actual result from results file
	actual=$(cat $resultFile)
	#get difference between result and oracle files
	result=$(diff $resultFile $oracleFile)

	echo -n "Checking result of test case #"$testNumber"..."

	##if diff returned anything other than empty string, test failed	
	if [ "$result" = "" ]
	then
		outputResult "pass"
		echo "PASS"	
	else
		reason="Expected result: $expectedOutput<br>Actual: $actual"
		outputResult "fail"
		echo "FAIL"
	fi
done

#open report file with default application for .html files
xdg-open "$reportFile"
