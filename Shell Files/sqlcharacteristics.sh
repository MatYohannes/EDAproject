#!/bin/bash

#
# Step 1:
# With the help of the APIPuller.sh and its helper, the text file 'Categories.txt',
# first add the new subcategories to the text file, save it, then run APIPuller.sh .
#
# Step 2:
# Have the files placed under their top parent name.
# eg Capacitor, Circuit Protection, Cable Assemblies
# To find out what the top parent category names are, go to:
#
# https://www.digikey.com/en/products
#
# Place the correct files in the top parent folder. These folders are to
# be place in a directory called, "Postman Exports/".
#
# Step 3:
# Run the AttributeCollector.sh file and have the ouput saved to a text file.
# The top portion of the output file will contain a file count, and output
# stating the file has been read, the the attributes names from the category.
#
# Step 4:
# Open the CategoryHeader.java file, then insert the attributes to the
# existing switch statement. The case value to use in the switch would be
# the top parent name the file belongs to.
#
#Step 5:
# Create a jar file and name it JSONSQL.jar .
# When User uploads the categories list below, you must also update the
# sqlmembership.sh file.
#
#
#
#
# For the code below, the java jar file JSONSQL.jar is passed with 2 arguments.
# The first is the category name which is provided with a list and a for-loop.
# The second is to indicate to the jar file which SQL table to update,
# 'm' for membership table, 'c' for characteristics table.
#
# 


# List of categories

categories="Capacitors 'Connectors, Interconnects' 'Crystals, Oscillators, Resonators' 'Inductors, Coils, Chokes' 'Integrated Circuits (ICs)' 'Potentiometers, Variable Resistors' Relays Resistors Switches"

# Iterate over each category in categoies

for category in $categories
do 
	java -jar JSONSQL.jar $category "c"
done

