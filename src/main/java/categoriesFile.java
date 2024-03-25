import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

/*
        The provided Java code (splittingJson) reads information from a JSON file named "categories.json" and organizes
    the data into a table structure representing a hierarchy of categories. The JSON file contains a list of
    categories, each with a unique identifier (CategoryId), a reference to its parent category (ParentId),
    a name (Name), and the number of products within the category (ProductCount). The code processes this
    hierarchical data, creating three separate JSONArrays (parentsList, childrenList, and grandChildrenList) to
    store information about parent, child, and grandchild categories, respectively.
        It then iterates through these arrays, constructs a new list (tableList) containing rows with parent,
    child, and grandchild names, and finally displays the resulting table with three columns: parent category,
    child category, and grandchild category. The table represents the hierarchical structure of categories,
    including null values where applicable, and provides insights into the relationships between different
    levels of the category hierarchy.
    The code efficiently handles the nested structure of the JSON data to organize it into a meaningful table.
 */

public class categoriesFile {
    public static void main(String[] args) {

        // Create lists to store JSON objects for different levels of hierarchy
        JSONArray parentsList = new JSONArray();
        JSONArray childrenList = new JSONArray();
        JSONArray grandChildrenList = new JSONArray();

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
                Long productCount = (Long) temp.get("ProductCount");

                // Create container Object
                JSONObject object = new JSONObject();

                // Inserting features to create new parent Object
                object.put("CategoryId", categoryId);
                object.put("ParentId", parentId);
                object.put("Name", name);
                object.put("ProductCount", productCount);

                // Add Object to 1 of 3 JSONArrays
                parentsList.add(object);

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
                    Long productCount2 = (Long) temp2.get("ProductCount");
                    JSONObject object2 = new JSONObject();

                    // Inserting features to create new child Object
                    object2.put("CategoryId", categoryId2);
                    object2.put("ParentId", parentId2);
                    object2.put("Name", name2);
                    object2.put("ProductCount", productCount2);

                    // Add Object to 1 of 3 JSONArrays
                    childrenList.add(object2);

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
                        Long productCount3 = (Long) temp3.get("ProductCount");
                        JSONObject object3 = new JSONObject();
                        object3.put("CategoryId", categoryId3);
                        object3.put("ParentId", parentId3);
                        object3.put("Name", name3);
                        object3.put("ProductCount", productCount3);

                        // Add Object to 1 of 3 JSONArrays
                        grandChildrenList.add(object3);
                    }
                }
            }

            //Printing out JSONArray list

            // Create Mutable List to insert Objects.
            List<Object> tableList = new ArrayList<>();

            /*
             Inserting Objects from JSONArray {parentsList, childrenList, grandChildrenList}
             into List.
             */

            /*
                parentsList size = 49
                childrenList size = 679
                grandChildrenList size = 436

             */
            for (int i = 0; i < childrenList.size(); i++) {
                JSONObject childObject = (JSONObject) childrenList.get(i);
                String childName = (String) childObject.get("Name");

                for (int j = 0; j < parentsList.size(); j++) {
                    JSONObject parentObject = (JSONObject) parentsList.get(j);
                    Long parentId = (Long) parentObject.get("CategoryId");

                    if (parentId.equals(childObject.get("ParentId"))) {
                        String parentName = (String) parentObject.get("Name");

                        // If there are grandchildren, do not add addition null row
                        boolean hasGrandChildren = false;


                        for (int k = 0; k < grandChildrenList.size(); k++) {
                            JSONObject grandChildObject = (JSONObject) grandChildrenList.get(k);
                            Long grandChildParentId = (Long) grandChildObject.get("ParentId");

                            if (grandChildParentId.equals(childObject.get("CategoryId"))) {
                                String grandChildName = (String) grandChildObject.get("Name");

                                Object[] row = new Object[]{parentName, childName, grandChildName};
                                tableList.add(row);
                                hasGrandChildren = true;
                            }
                        }
                        if (!hasGrandChildren) {
                            // Add a row with a null value for grandchildren
                            Object[] row = new Object[]{parentName, childName, null};
                            tableList.add(row);
                        }
                    }
                }
            }
            Object[][] table = new Object[tableList.size()][3];
            for (int i = 0; i < tableList.size(); i++) {
                Object[] rowData = (Object[]) tableList.get(i);
                for (int j = 0; j < 3; j++) {
                    table[i][j] = rowData[j];
                }
            }

            // Display 2d table
            for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(table[i][j]  + "\t|\t");
                }
                System.out.println();
            }

            System.out.printf("Table size: " + table.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
