#!/bin/bash
#
# This file is used to read the files in folder and then
# output the attributes of the subcategories uses.
#
# Below is the variable directory. It will contain a list of
# folder with names from the top parent categories from DigiKey.
# A top parent category is a category from the DigiKey website:
# https://www.digikey.com/en/products
#
# In each category, subcategory files are place there. This files
# can be obtained by running the APIPuller.sh or APIPuller.jar file.
#
# The output can be saved to a text file.
# 
# The text file will contain the following:
# A count of the number of files in folder
# A list of files it read
# Finally, a list of attribute names. 
#
# The attribute names are used to used to JSONSQL.jar, sqlcharacteristics.sh,
# and sqlmembership.sh files.
#
#
# Path directory
directory="/root/EDAProject/Postman Exports"

#Check if directory exists
if [ -d "$directory" ]; then
	# Loop through each entry in the direcotory
	for entry in "$directory"/*; do
		#Check if the entry is a directory
		if [ -d "$entry" ]; then
			substring=$(echo $entry | cut -d'/' -f5-)
			echo "$substring"
			java -jar Printer.jar "$substring" > Attributes/"$substring".txt
		fi
	done
else
	echo "Directory does not exist."
fi
