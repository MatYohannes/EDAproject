#!/bin/bash

# The following inline code is to upload the categories to SQL table 'categories'.
# The program run reading a folder in the same directory as this file.
# 
# The directory the categories.json file must be located as such:
# Postman Exports/categories.json
#
# This will create a connection to a SQL server and upload the categories from the
# DigiKey website:
# https://www.digikey.com/en/products
#
# All subcategories will be uploaded to the SQL server with the following
# column names:
#		'category_id', 'category_name', 'parent_id'

# The catgory_id is the number DigiKey assigned to the subcategory.
# The category_name is self explanatory.
# For parent_id, if the subcatgories falls under a different category,
# that categories id is assigned.
# If the category does not have a subcategory, the value assigned is 0.
#
java -jar CategoriesToSQL.jar
