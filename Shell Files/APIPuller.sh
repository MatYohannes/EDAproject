#!/bin/bash

# This command executes the Digikey API Puller.
# It reads a file called "CategoriesList.txt" which contains
# a list of subcategories to pull from.
# If you, the User, wish to expand the number of sub categories
# from DigiKey, you can add them by inserting the correct and
# complete DigiKey subcategory name. 
#
# Go to the following website to find subcategories:
# https://www.digikey.com/en/products
#
# From there, you can scroll and select a subcategories.
#
# eg. 
# From Categories "Filters", insert "Cable Ferrites". 
# Exclude the double quotes.
#
# Save the file, then run this shell file
java -Xmx4g -jar APIPuller.jar
