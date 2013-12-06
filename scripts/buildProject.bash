#!/bin/bash

##user options
#add external jars to classpath here
userClasspathEntries="./project/martus-thirdparty/libext/JUnit/bin/junit.jar:./project/martus-amplifier/classes" #had to add martus-amplifier here because it breaks "bin" naming convention
#define desired build directory
outputFolderName="bin"
##



#clean project
find ./project -name "*.class" -print0 | xargs -0 rm -rf
#clean build log
rm -rf ./project/build.log

#get package names from user file
packages=$(cat "./scripts/build.txt")

##loop and compile each package
for package in ${packages[@]}
do
	#initialize classpath variables
	classpath=""
	unset cPEntries
	i=0

	echo -n "Building $package..."

	#eclipse project file with classpath entries (XML-like format)
	eclipseCP="./project/$package/.classpath"

	#get lines from file if classpath entry kind is "src", "lib", or "output"
	file=$(cat $eclipseCP | egrep '[[:print:]]*(kind="src"|kind="lib"|kind="output")[[:print:]]*')
	IFS="<" #set internal field seperator

	##loop through each line contained in file
	for line in $file
	do
		#get entry type
		kind=${line#*'kind="'}
		kind=${kind%%'"'*}
		#get entry path
		path=${line#*'path="'}
		path=${path%%'"'*}

		##normalize path entries
		if [[ $kind = "output" ]]
		then
			outputDir=$path
		elif [[ $kind = "src" ]]
		then
			if [ ${path:0:1} = "/" ]
			then
				cPEntries[$i]="./project$path/$outputFolderName"
				i=$((i+1))
			else
				cPEntries[$i]="./project/$package/$path"
				i=$((i+1))
			fi
		elif [[ $kind = "lib" ]]
		then
			if [ ${path:0:1} = "/" ]
			then
				cPEntries[$i]="./project$path/"
				i=$((i+1))
			else
				cPEntries[$i]="./project/$package/$path"
				i=$((i+1))
			fi
		fi
	done

	#Add external jars defined aboveto classpath
	cPEntries[$i]=$userClasspathEntries

	##loop through each classpath entry
	for path in ${cPEntries[@]}
	do
		#concatenate entries with colons
		classpath=$classpath$path:
	done

	#compile each java file found inside $package path
	#output classes to $outputDir (/bin or /classes) and log stderr (includes javac warnings) to ./project/build.log
	find ./project/$package -name \*.java | xargs javac -encoding ISO-8859-1 -cp $classpath -d ./project/$package/$outputDir/ 2>> "./project/build.log"
	
	#if return code from javac is non-zero, alert user to error and location of log
	if [[ ! $? -eq 0 ]]
	then 
		echo "ERROR"
		echo "See ./project/build.log for more information."
	else 
		echo "DONE"
	fi
done
