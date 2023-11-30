//import org.json.simple.JSONObject;
import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class splittingJson {

    public static void main(String[] args) {

        JSONArray parentsList = new JSONArray();
        JSONArray childrenList = new JSONArray();
        JSONArray grandChildrenList = new JSONArray();

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("Postman Exports/categories.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray categories = (JSONArray)jsonObject.get("Categories");
            Iterator iteratorParent = categories.iterator();
            //System.out.println(categories);
            while(iteratorParent.hasNext()) {
                JSONObject temp = (JSONObject) iteratorParent.next();
                //System.out.println(temp.get("CategoryId"));
                Long categoryId = (Long) temp.get("CategoryId");
                Long parentId = (Long) temp.get("ParentId");
                String name = (String) temp.get("Name");
                Long productCount = (Long) temp.get("ProductCount");
                //System.out.println(categoryId);
                JSONObject object = new JSONObject();
                object.put("CategoryId", categoryId);
                object.put("ParentId", parentId);
                object.put("Name", name);
                object.put("ProductCount", productCount);

                parentsList.add(object);

                JSONArray tempChild = (JSONArray) temp.get("Children");
                Iterator iteratorChild = tempChild.iterator();
                while(iteratorChild.hasNext()) {
                    JSONObject temp2 = (JSONObject) iteratorChild.next();
                    Long categoryId2 = (Long) temp2.get("CategoryId");
                    Long parentId2 = (Long) temp2.get("ParentId");
                    String name2 = (String) temp2.get("Name");
                    Long productCount2 = (Long) temp2.get("ProductCount");
                    JSONObject object2 = new JSONObject();
                    object2.put("CategoryId", categoryId2);
                    object2.put("ParentId", parentId2);
                    object2.put("Name", name2);
                    object2.put("ProductCount", productCount2);

                    childrenList.add(object2);

                    JSONArray tempGrandChild = (JSONArray) temp2.get("Children");
                    Iterator iteratorGrandChild = tempGrandChild.iterator();
                    while(iteratorGrandChild.hasNext()) {
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
                        grandChildrenList.add(object3);
                    }
                }
            }

             //Printing out JSONArray list
            Object[][] table = new String[15691][3];

            for (int i=0; i < grandChildrenList.size(); i++) {
                JSONObject j1 = (JSONObject) grandChildrenList.get(i);
                table[i][0] = j1.get("Name");

                for (int j=0; j < childrenList.size(); j++) {
                    JSONObject j2 = (JSONObject) childrenList.get(j);

                    Long j1ID = (Long) j1.get("ParentId");
                    Long j2ID = (Long) j2.get("CategoryId");

                    if (j1ID == j2ID) {
                        //table[i][1] = j2.get("Name");
                        System.out.println("Grand "+ j1ID);
                        System.out.println("Child "+ j2ID);
                    }
                }
            }


            /*

            for (int i=0; i < table.length; i++) {
                System.out.println(table[i][0]);
            }
*/

/*            for(int i=0; i< 10; i++) {
                System.out.println(grandChildrenList.get(i));
            }*/

            /*for (int i=0; i < parentsList.size(); i++) {
                System.out.println(parentsList.get(i).toString().substring(parentsList.get(i).toString().indexOf("Name") + 6));
            }*/

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
