package es.mycloset.back;

import java.util.ArrayList;

public class Info {

    ReadJson reader = new ReadJson();

    public String Data_FILE_PATH = "Data/cp-national-datafile.json";
    public String Data_FILE_PATH_W = "Data/cp-national-datafile.json";
    ArrayList<NationalDatafileJson> addprod (NationalDatafileJson produ){
        ArrayList<NationalDatafileJson> userlist = reader.readJsonFile(Data_FILE_PATH);
        userlist.add(produ);
        reader.writeJsonFile(Data_FILE_PATH_W,userlist);
        return userlist;
    }

    ArrayList<NationalDatafileJson> deleteprod(String id) {

        ArrayList<NationalDatafileJson> userlist = reader.readJsonFile(Data_FILE_PATH); // Obtain NationalDatafileJsons list from JSON file
        int index = -1;

        // Find NationalDatafile with the Id
        for (int i = 0; i < userlist.size(); i++) {
            if (userlist.get(i).getId().equals(id)) {

                System.out.println("Found");
                index = i;
                break;
            }
        }

        if (index != -1) {
            userlist.remove(index);
            reader.writeJsonFile(Data_FILE_PATH_W,userlist);
            System.out.println("Deleted");
            return userlist; // Return updated list
        } else {
            return null; // It was not found the data with the id given
        }
    }

    ArrayList<NationalDatafileJson> modificar(String id, NationalDatafileJson NationalDatafileJsonModificado) {
        ArrayList<NationalDatafileJson> NationalDatafileJsonList = reader.readJsonFile(Data_FILE_PATH); // Obtain NationalDatafileJsons list from JSON file

        int index = -1;

        //  Find NationalDatafile with the Id
        for (int i = 0; i < NationalDatafileJsonList.size(); i++) {
            if (NationalDatafileJsonList.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            NationalDatafileJsonList.set(index, NationalDatafileJsonModificado); // Replace el NationalDatafileJson on the list
            reader.writeJsonFile(Data_FILE_PATH_W, NationalDatafileJsonList); // Write the updated list in the JSON File
            return NationalDatafileJsonList; // Return updated list
        } else {
            return null; // It was not found the data with the id given
        }
    }


}
