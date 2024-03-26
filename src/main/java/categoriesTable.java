import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class categoriesTable {

    public static void printTable(Map<Long, Object[]> map) {
        // Sort the map entries based on the "CategoryID"
        Map<Long, Object[]> sortedMap = new TreeMap<>(map);

        // Create a 2D array to store the table data
        Object[][] table = new Object[sortedMap.size()][3];
        int index = 0;
        // Iterate through the sorted map entries
        for (Map.Entry<Long, Object[]> entry : sortedMap.entrySet()) {
            // Extract data from the map entry
            Long categoryID = entry.getKey();
            Object[] value = entry.getValue();
            String categoryName = (String) value[0];
            Long parentID = (Long) value[1];
            // Populate the table with the extracted data
            table[index][0] = categoryID;
            table[index][1] = categoryName;
            table[index][2] = parentID;
            index++;
        }

        // Define the header for the table
        String[] header = {"Category ID", "Category Name", "Parent ID"};

        // Print header
        for (int i = 0; i < header.length; i++) {
            System.out.printf("%-50s", header[i]);
        }
        System.out.println();

        // Print the table
        for (Object[] row : table) {
            for (Object cell : row) {
                System.out.printf("%-50s", cell);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Map<Long, Object[]> table = new HashMap<>();


        // Create a JSON parser to parse the input file
        JSONParser parser = new JSONParser();
        try {

            // Parsing the JSON file
            Object obj = parser.parse(new FileReader("Postman Exports/categories.json"));
            JSONObject jsonObject = (JSONObject) obj;

            /*
             As the entire JSON file is stored in an array called "Categories",
              created an JSONArray to access the contents
             */
            JSONArray categories = (JSONArray) jsonObject.get("Categories");

            // Iterator will have access to the nested JSON Objects, including the outer (parent), middle (child),
            // and inner (grandchild)
            Iterator iteratorParent = categories.iterator();
            while (iteratorParent.hasNext()) {
                /*
                Each Object will have the following "features":
                    CategoryId: id number
                    ParentId: if product is child of another, parentId provide. Else id is 0 to indicate it's a parent
                    Name: category name
                    ProductCount: number of items in category
                    Children: if object has children, children will be their own Object within
                 */
                // Iterate through the entire JSONFile
                // Temp Object container for parent
                JSONObject temp = (JSONObject) iteratorParent.next();
                Long categoryId = (Long) temp.get("CategoryId");
                Long parentId = (Long) temp.get("ParentId");
                String name = (String) temp.get("Name");

                table.put(categoryId, new Object[]{name, parentId});

                // Add Object to 1 of 3 JSONArrays

                // Entering into the "Children" of the parent
                JSONArray tempChild = (JSONArray) temp.get("Children");
                Iterator iteratorChild = tempChild.iterator();

                // Iterate through the Children of the parent
                while (iteratorChild.hasNext()) {
                    // Temp Object container for child
                    JSONObject temp2 = (JSONObject) iteratorChild.next();
                    Long categoryId2 = (Long) temp2.get("CategoryId");
                    Long parentId2 = (Long) temp2.get("ParentId");
                    String name2 = (String) temp2.get("Name");

                    table.put(categoryId2, new Object[]{name2, parentId2});

                    // Entering into the Children of the child
                    JSONArray tempGrandChild = (JSONArray) temp2.get("Children");
                    Iterator iteratorGrandChild = tempGrandChild.iterator();

                    // Iterate through the Children of the child
                    while (iteratorGrandChild.hasNext()) {
                        // Temp Object container for grandchildren
                        JSONObject temp3 = (JSONObject) iteratorGrandChild.next();
                        Long categoryId3 = (Long) temp3.get("CategoryId");
                        Long parentId3 = (Long) temp3.get("ParentId");
                        String name3 = (String) temp3.get("Name");

                        table.put(categoryId3, new Object[]{name3, parentId3});
                    }
                }
            }

            // Accessing and printing the table
//            printTable(table);
            JDBC dbConnector = new JDBC();
            dbConnector.insertCategories(table);



            } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
