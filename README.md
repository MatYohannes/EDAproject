
# EDAProject
# Electronic Design Automation (EDA)

## Description

This program efficiently gathers data from an API using user-specified keywords and arranges it into well-organized files. It simplifies the task of fetching and structuring data from an API, automating processes such as pagination, error management, and file organization. Additionally, it enables the extraction and processing of data from JSON files obtained via the API, transforming it into structured data suitable for insertion into an SQL database. This provides a comprehensive solution for various data processing requirements.

## For who

This program is create for use by Professor Jeffrey A. Wiegley. 
Jeffrey A. Wiegley is the owner of the program and its use.

## Authors

- Mathewos Yohannes [@MatYohannes](https://github.com/MatYohannes)
- Yana Zaynullina [@yanalina](https://github.com/yanalina)
- Jason Muturi [@jcodesfr](https://github.com/jcodesfr)
## Installation


The following imports are used to run the programs. 
```
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.*;
import java.io.*;
import org.json.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.ParseException;
```

## Dependencies
There is a pom.xml that contains the following dependencies:
```
json-simple
GroupId: com.googlecode.json-simple
ArtifactId: json-simple
Version: 1.1.1
httpclient
GroupId: org.apache.httpcomponents
ArtifactId: httpclient
Version: 4.5.13
mysql-connector-java
GroupId: mysql
ArtifactId: mysql-connector-java
Version: 8.0.30
```

    
## Usage/Examples

The program contains easy to use shell files and jar files.
Below are more details.

### Shell Scripts
#### 1. APIPuller.sh

It reads a file called "CategoriesList.txt" which contains
a list of subcategories to pull from.
If you, the User, wish to expand the number of sub categories
from DigiKey, you can add them by inserting the correct and
complete DigiKey subcategory name.

Go to the following website to find subcategories:
[https://www.digikey.com/en/products](https://www.digikey.com/en/products "https://www.digikey.com/en/products")


From there, you can scroll and select a subcategories.

eg.
From Categories "Filters", insert "Cable Ferrites".
Exclude the double quotes.

Save the file, then run this shell file

APIPuller shell script:
```
java -Xmx4g -jar APIPuller.jar
```
#### 2. AttributeCollector.sh

This file is used to read the files in folder and then
output the attributes of the subcategories uses.

Below is the variable directory. It will contain a list of
folder with names from the top parent categories from DigiKey.
A top parent category is a category from the DigiKey website:
[https://www.digikey.com/en/products](https://www.digikey.com/en/products "https://www.digikey.com/en/products")

In each category, subcategory files are place there. This files
can be obtained by running the APIPuller.sh or APIPuller.jar file.

The output can be saved to a text file.

The text file will contain the following:
A count of the number of files in folder
A list of files it read
Finally, a list of attribute names.

The attribute names are used to used to JSONSQL.jar, sqlcharacteristics.sh,
and sqlmembership.sh files.

AttributeCollector shell script:
```
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
```
#### 3. sqlCategories.sh
The following inline code is to upload the categories to SQL table 'categories'.
The program run reading a folder in the same directory as this file.

The directory the categories.json file must be located as such:
Postman Exports/categories.json

This will create a connection to a SQL server and upload the categories from the DigiKey website:
[https://www.digikey.com/en/products](https://www.digikey.com/en/products "https://www.digikey.com/en/products")

All subcategories will be uploaded to the SQL server with the following
column names:
            'category_id', 'category_name', 'parent_id'

The catgory_id is the number DigiKey assigned to the subcategory.
The category_name is self explanatory.
For parent_id, if the subcatgories falls under a different category, that categories id is assigned.
If the category does not have a subcategory, the value assigned is 0.


sqlCategories shell script:
```
java -jar CategoriesToSQL.jar
```

#### 4. sqlcharacteristics.sh
 Have the files placed under their top parent name.
eg Capacitor, Circuit Protection, Cable Assemblies
To find out what the top parent category names are, go to:

[https://www.digikey.com/en/products](https://www.digikey.com/en/products "https://www.digikey.com/en/products")

Place the correct files in the top parent folder. These folders are to
be place in a directory called, "Postman Exports/".

Step 3:
Run the AttributeCollector.sh file and have the ouput saved to a text file.
The top portion of the output file will contain a file count, and output
stating the file has been read, the the attributes names from the category.

Step 4:
Open the CategoryHeader.java file, then insert the attributes to the
existing switch statement. The case value to use in the switch would be
the top parent name the file belongs to.

Step 5:
Create a jar file and name it JSONSQL.jar .
When User uploads the categories list below, you must also update the
sqlmembership.sh file.

For the code below, the java jar file JSONSQL.jar is passed with 2 arguments.
The first is the category name which is provided with a list and a for-loop.
The second is to indicate to the jar file which SQL table to update,
'm' for membership table, 'c' for characteristics table.

sqlcharacteristics shell script:
```
# List of categories

categories="Capacitors 'Connectors, Interconnects' 'Crystals, Oscillators, Resonators' 'Inductors, Coils, Chokes' 'Integrated Circuits (ICs)' 'Potentiometers, Variable Resistors' Relays Resistors Switches"

# Iterate over each category in categoies

for category in $categories
do
        java -jar JSONSQL.jar $category "c"
done
```
#### 5. sqlmemberhip.sh
Step 1:
With the help of the APIPuller.sh and its helper, the text file 'Categories.txt',
first add the new subcategories to the text file, save it, then run APIPuller.sh .

Step 2:
Have the files placed under their top parent name.
eg Capacitor, Circuit Protection, Cable Assemblies
To find out what the top parent category names are, go to:

[https://www.digikey.com/en/products](https://www.digikey.com/en/products "https://www.digikey.com/en/products")

Place the correct files in the top parent folder. These folders are to
be place in a directory called, "Postman Exports/".

Step 3:
Run the AttributeCollector.sh file and have the ouput saved to a text file.
The top portion of the output file will contain a file count, and output
stating the file has been read, the the attributes names from the category.

Step 4:
Open the CategoryHeader.java file, then insert the attributes to the
existing switch statement. The case value to use in the switch would be
the top parent name the file belongs to.

Step 5:
Create a jar file and name it JSONSQL.jar .
When User uploads the categories list below, you must also update the
sqlcharacteristics.sh file.

For the code below, the java jar file JSONSQL.jar is passed with 2 arguments.
The first is the category name which is provided with a list and a for-loop.
The second is to indicate to the jar file which SQL table to update,
'm' for membership table, 'c' for characteristics table.


sqlmemberhip shell script:
```
# Category List
categories = "Capacitors 'Connectors, Interconnects' 'Crystals, Oscillators, Resonators' 'Inductors, Coils, Chokes' 'Integrated Circuits (ICs)' 'Potentiometers, Variable Resistors' Relays Resistors Switches"

# Iterate over each category in categories

for category in $categories
do
        java -jar JSONSQL.jar $category "m"
done
```

### Java Files
#### 1. AttributeCollector
This java file essentially goes through a collection of JSON files, extracts certain attribute values from them, and prints
out a list of unique attributes found across all files.
Breakdown of the code: 
#####   1. Command Line Arguments
The program expects a category folder name to be passed as a command-line argument. This folder contains JSON files representing products.
##### 2. File Directory
It specifies the directory where the JSON files are located. By default, it's set to "Postman Exports/", but it could be modified to match the actual directory structure.
##### 3. File Processing
It iterates through each file in the specified directory.
For each file, it constructs the file path and reads the JSON content using a JSONParser.
It extracts the array of products from the JSON object.
##### 4. Attribute Extraction
For each product, it iterates through its parameters.
It extracts the parameter attributes and adds them to a HashSet named attributes.
The use of a HashSet ensures that only unique attributes are collected, avoiding duplicates.
#### 5. Output
After processing all files, it prints out the unique attributes collected to the console.

#### 2. CategoriesToSQL

This Java program processes category data stored in a JSON file, constructs a table from that data, and prints the resulting table. 
Breakdown of the code: 
##### 1.printTable Method
This method takes a Map<Long, Object[]> as input, where the key is the category ID, and the value is an array containing the category name and parent ID.
It sorts the map entries based on the category ID.
Then, it constructs a 2D array to store the table data.
It iterates through the sorted map entries, extracts the category data, and populates the table array.
Finally, it prints the table header and the table data row by row.
##### 2. main Method
It initializes a HashMap<Long, Object[]> named table to store the category data.
It creates a JSONParser object to parse the input JSON file.
It parses the JSON file containing category data.
It iterates through the categories in the JSON file, extracting category ID, parent ID, and name.
For each category, it adds an entry to the table map.
If a category has children, it iterates through its children and grandchildren, adding entries for each.
After constructing the table, it calls the printTable method to print the category data as a table.
It then prints the total number of categories processed.
Finally, it initializes a JDBC object to interact with a MySQL database, inserts the category data into the database, and closes the database connection.

##### 3. DigiKeyAPI3
This Java code orchestrates the process of retrieving data from an API based on provided keywords and storing the results in JSON files. 
Breakdown of the code:
##### 1. arrayCount Method
Counts the number of elements in a JSON array stored in a file specified by filePath.
It parses the JSON file using a JSONParser, retrieves the JSON array named "Products", and returns its size.
##### 2. insertArrayLines Method
Inserts an array of lines at the end of a file.
Reads existing content from the file, appends the new lines after the first '[', and writes the modified content back to the file.
##### 3. getPosition Method
Calculates the position in the content string to insert a new line at a given line number.
It iterates through the content to find the position of the line separator for the specified line number.
##### 4. insertTemplate Method
Inserts a template into a file specified by filePath.
Writes a predefined JSON template to the file.
##### 5. getAccessToken Method
Obtains an access token from an API using client credentials.
It creates an HTTP client, sends a POST request to obtain the access token, and parses the response to extract the token.
##### 6. sendPostRequest Method
Sends a POST request to an API.
It creates an HTTP client, sends a POST request with the provided parameters (access token, offset, limit, keyword, client ID), and returns the response body.

##### 8. File Name Selection
The selectingFileName method takes a keyword and selects an appropriate file name based on predefined categories and subcategories of products. If the provided keyword matches any predefined category or subcategory, it returns the corresponding file name; otherwise, it provides a default file name.
##### 9. File Folder Selection
 The selectingFileFolder method works similarly to selectingFileName, but instead of selecting a file name, it selects a folder name based on the provided keyword.
##### 10. File Existence Check and Creation
The checkingFile method checks whether a file exists in a specified folder. If the file exists, it updates the offset and product index. If the file does not exist, it creates a new file using a template.
##### 11. Client List Management
The clientListUsedUp method checks if all lines in the "ClientList.txt" file end with "O". If they do, it replaces "O" with "X" for all lines, effectively resetting the client list. Otherwise, it prints a message indicating open client IDs and client secrets.
The categoryListComplete method checks if each line in a specified file (presumably "CategoriesList.txt") ends with "O". If all lines end with "O", it returns true; otherwise, it returns false.
##### 12. main Method
The code defines constants for the OAuth 2.0 authorization endpoint and the API endpoint for accessing Digi-Key's product information.
Orchestrates the process of retrieving data from an API based on provided keywords.
It manages file operations, API requests, and error conditions.
It obtains access tokens, sends requests to the API, handles various error scenarios, writes response data to JSON files, and manages offsets for pagination.
The process repeats until a certain condition is met (e.g., a maximum number of API calls reached).

### Java Helper Files
#### 1. CategoriesCheckList
This class provides methods to manipulate a checklist file containing categories.
##### 1. findCategory Method
This method takes the path to the checklist file as input.
It reads the file line by line using a BufferedReader.
For each line, it checks if the line ends with "X", indicating that the category hasn't been processed yet.
If a line ending with "X" is found, it splits the line by the "|" character (assuming it's used as a delimiter) to extract the category name.
The method returns the first category name found that needs processing, or null if none is found.
##### 2. categoryComplete Method
This method marks the completion of a category in the checklist file.
It takes the path to the checklist file and the category to mark as completed as input.
It creates a temporary file to write the modified checklist.
It reads the original checklist file line by line and checks if each line contains the specified category (searchString) and ends with "X".
If a line matches these conditions, it replaces the last character "X" with "O", indicating completion.
Each modified line is written to the temporary file.
After processing all lines, the original file is deleted, and the temporary file is renamed to the original file name.
If any errors occur during file operations, they are caught, and a stack trace is printed.
#### 2. CategoriesCheckList
The provided Java code (splittingJson) reads information from a JSON file named "categories.json" and organizesthe data into a table structure representing a hierarchy of categories. The JSON file contains a list of
categories, each with a unique identifier (CategoryId), a reference to its parent category (ParentId), a name (Name), and the number of products within the category (ProductCount). The code processes this hierarchical data, creating three separate JSONArrays (parentsList, childrenList, and grandChildrenList) to
store information about parent, child, and grandchild categories, respectively.
It then iterates through these arrays, constructs a new list (tableList) containing rows with parent, child, and grandchild names, and finally displays the resulting table with three columns: parent category, child category, and grandchild category. The table represents the hierarchical structure of categories, including null values where applicable, and provides insights into the relationships between different levels of the category hierarchy.
The code efficiently handles the nested structure of the JSON data to organize it into a meaningful table.
##### 1. Reading and Parsing JSON Data
The code imports necessary libraries for JSON handling (org.json.simple.* and org.json.simple.parser.*).
It creates a JSONParser object to parse the JSON file.
Using a try-catch block, it parses the "categories.json" file and retrieves the JSON array named "Categories" containing category information.
##### 2. Processing Category Hierarchy
The code iterates through each JSON object in the "Categories" array, representing categories at different levels of the hierarchy (parent, child, grandchild).
For each category object, it extracts the CategoryId, ParentId, Name, and ProductCount.
It separates these categories into three JSONArrays: parentsList, childrenList, and grandChildrenList, based on their level in the hierarchy.
It then iterates through each category to organize them into a meaningful table structure.
##### 3.Constructing the Table
The code creates a mutable list (tableList) to store rows of the table.
It iterates through the childrenList, finding parent categories for each child and checking for grandchild categories.
For each child category, it checks if there are corresponding grandchild categories. If so, it adds rows with the parent, child, and grandchild names to the tableList. If not, it adds a row with a null value for the grandchild.
It constructs a two-dimensional array (table) from the tableList to represent the table structure.
##### 4. Displaying the Table
Finally, the code displays the constructed table by iterating through the two-dimensional array and printing each row with tab-separated columns.
#### 3. CategoryHeaders
This Java code, CategoryHeaders, is a utility class that provides a method for selecting and returning an array of header names based on a given category. 
##### 1. Method Description
The class contains a single static method named selectCategory.
This method takes a String parameter category, representing the category for which headers are to be selected.
It returns an array of String, which represents the header names specific to the selected category.
##### 2. Switch Statement
The method uses a switch statement to determine the appropriate headers based on the input category.
For each case, corresponding to different categories, it initializes the header array with the appropriate set of header names.
Each set of header names is assigned to the header array specific to the category.
##### 3. Header Arrays
Each case in the switch statement initializes the header array with specific header names corresponding to the category.
The header arrays are constructed with strings representing the names of various attributes or properties related to the category.
##### 4. Default Case
If the input category does not match any of the cases in the switch statement, it prints a message indicating that the header list for the selected category is not available.
The default case handles situations where the provided category does not have predefined header names.
##### 5. Return Statement
Finally, the method returns the header array, containing the selected header names based on the input category.

#### 3. Characteristics
This Java code defines a class named Characteristics that encapsulates information about characteristics. 
##### 1. Class Definition
The class Characteristics is declared with three private instance variables: customId, attributes, and value.
These variables represent the custom ID associated with the characteristics, the attributes describing the characteristics, and the value of the characteristics, respectively.
##### 2. Constructors
The class defines two constructors:
The default constructor Characteristics() initializes all fields to default values. This constructor is empty, meaning it doesn't contain any initialization logic.
The parameterized constructor Characteristics(int customId, String attributes, String value) initializes the customId, attributes, and value fields with the provided values.
##### 3. Getters and Setters
The class provides public getter and setter methods for each private instance variable.
The getter methods (getCustomId(), getAttributes(), getValue()) allow other classes to retrieve the values of the private variables.
The setter methods (setCustomId(int customId), setAttributes(String attributes), setValue(String value)) allow other classes to modify the values of the private variables.
##### 4. Encapsulation
The class follows the principle of encapsulation by keeping its instance variables private and providing public getter and setter methods to access and modify those variables.
This approach helps in maintaining the integrity of the class and controlling access to its data.

#### 4. characteristics2Table
This class contains a static method createCustomTable that takes an original table, membership headers, original headers, and a database connection as input parameters and returns a custom characteristics table based on the original tables. The method iterates through the original table, retrieves the custom ID based on the manufacturer part ID from the membership table, and creates custom rows for the characteristics table.
##### 1. Method Signature
The createCustomTable method signature specifies the parameters and return type:
originalTable: The original table data retrieved from the database.
membershipHeaders: Headers of the membership table (SQL column names).
originalHeaders: Headers of the original table.
connection: The JDBC database connection.
Returns a list of Characteristics representing the custom table that matches the SQL table.
Throws a SQLException if a SQL error occurs during execution.
##### 2. Initialization
The method initializes an empty list finalTable to store the custom characteristics.
It creates two index mapping maps (firstTableIndexMap and secondTableIndexMap) to map headers to their respective column indices in the original tables.
##### 3. Retrieving Custom IDs
It constructs a SQL query to retrieve custom IDs for all manufacturer part IDs from the membership table.
Executes the query and populates a customIdMap with the mapping between manufacturer part IDs and custom IDs.
##### 4. Generating Custom Table
It iterates over each row of the original table.
For each row, it iterates over the columns starting from index 9.
If the value in the column is not null, empty, "0", or "-", it retrieves the value, attribute name, and manufacturer part ID.
It looks up the custom ID corresponding to the manufacturer part ID from the customIdMap.
If a custom ID is found, it creates a new Characteristics object, sets its custom ID, attributes, and value, and adds it to the finalTable list.
##### 5. Return
Returns the finalTable containing the custom characteristics.

#### 5. ClientScanner
The ClientScanner class provides methods for managing client keys.
It includes functionality to find free client keys, mark used client keys, and check if the client list is complete.
##### 1. findFreeClient
This method reads client keys from the provided file path.
It identifies a free client key by checking if the line ends with "X".
If a free client key is found, it extracts and returns the client ID and client secret in an array.
##### 2. clientUsed
This method marks a client key as used in the client key list file.
It reads the original file, identifies the line containing the completed client key, and changes the last character from "X" to "O" to mark it as used.
The original file is replaced with a temporary file where the modification is made, and then the temporary file is renamed to the original file name.
##### 3. clientListComplete
This method checks if the client list is complete (all client keys are used).
It reads each line of the file and checks if any line ends with "X", indicating a free client key.
If any line ends with "X", it returns false, indicating that the client list is not complete.
If all lines end with "O", indicating that all client keys are used, it returns true, indicating that the client list is complete.

#### 6. DirectoryFiler
This Java class, DirectoryFiler, provides functionality for working with directories and files.
##### 1. fileCounter
This method counts the number of files in a directory that start with a given prefix.
It utilizes a directory stream (DirectoryStream<Path>) to iterate through the files in the specified directory.
For each file, it extracts the file name and checks if it starts with the provided prefix.
If a file matches the prefix, the count is incremented.
Finally, it returns the count of files that match the prefix.
##### 2. createJSONFileWithPrefix
This method creates a JSON file containing the names of files in a directory that start with a given prefix.
It again uses a directory stream to iterate through the files in the specified directory.
For each file matching the prefix, it creates a JSON object with the file name and adds it to a JSON array.
After iterating through all files, it constructs a JSON file name based on the prefix and file count.
It then writes the JSON array to a JSON file with the constructed name in the specified directory.
Finally, it returns the name of the created JSON file.
##### 3. getFileNamesInDirectory
This method retrieves the names of JSON files in a directory.
It first lists all files in the specified directory.
For each file, it checks if it's a regular file and if its name ends with ".json" (case-insensitive).
If these conditions are met, it adds the file name to a list of matching file names.
Finally, it returns the list of names of JSON files in the directory.

#### 7. JDBC
This code provides a JDBC (Java Database Connectivity) class for interacting with a MySQL database.
It includes methods for establishing and closing a database connection, inserting data into various tables,
removing SQL safety settings, and clearing data from tables.
##### 1. Connection
This method returns the database connection object.
##### 2. JDBC Constructor
This is the constructor of the class.
It initializes the database connection by loading the JDBC driver class and establishing a connection using the provided URL, username, and password.
If the connection is successfully established, it prints a confirmation message.
##### 3. closeConnection
This method closes the database connection.
##### 4. insertMembership
This method inserts data into the 'membership' table.
##### 5. insertCharacteristics
This method inserts data into the 'characteristics' table.
##### 6. insertCategories
This method inserts data into the 'categories' table.
##### 7. removeSQLSAFE
This method removes SQL safety settings, allowing unsafe updates.
##### 8. clearMembership
This method clears data from the 'membership' table.
##### 9. clearCharacteristics
This method clears data from the 'characteristics' table.
##### 10. clearCategories
This method clears data from the 'categories' table.

#### 8. membershipTable
This class contains a static method createCustomTable that takes an original table
and a database connection as input parameters and returns a custom table
based on the original one. The method retrieves the highest custom ID from the
membership table in the database, increments it, and then creates custom rows
by iterating through the original table.
##### 1. createCustomTable
This method takes two parameters: the original table as a list of Object arrays and the database connection.
It returns a list of Object arrays representing the custom table.
The method begins by initializing an empty list customTable to store the custom table rows and sets the initial customID to 0.
It constructs an SQL command to retrieve the highest custom ID from the existing membership table.
Then, it executes the SQL command to obtain the highest custom ID using the provided database connection.
After retrieving the highest custom ID, it increments the value to prepare for assigning new custom IDs to the custom table rows.
Next, it iterates through each row of the original table.
For each row, it creates a custom row with four elements:
The first element is the incremented customID, representing the custom ID.
The remaining elements are copied from the corresponding columns of the original table row: manID, manufacture, and manProductNumber.
The custom row is added to the customTable list.
Finally, the method returns the list representing the custom table.





## How to Create a DigiKey Client List
### Step 1
Go to https://developer.digikey.com/ and create an account.
### Step 2
Select 'Organizations' from the top bar.
### Step 3
You will be redirected to a page where the User can create an Organization. Select the '+ Create Organization' button.
Fill in as instructioned.
### Step 4
When Organization is create, it's name will be under the column name 'Organization name'. Select it.
### Step 5
There are 3 tabs, 'View', 'Members', 'Production Apps'. Select 'Production Apps'.
### Step 6
Select '+Create Production Apps'.
### Step 7
Fill in the following:
Production App name
OAuth Callback

Select one or more Production products that will be displayed.
After information is filled, select 'Add production app'.
Return to previous page.

### Step 8
Select the production app you create.
Copy the Client ID and Client Secret and added them to the ClientList.txt file.




## Expanding program
Coming soon to a theater near you.