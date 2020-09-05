package network;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Restaurant {
    private ArrayList<String> restaurantNames;


    public Restaurant() {
        this.restaurantNames = new ArrayList<>();
    }

    //Modifies: this
    //Effects: returns a String of restaurant names
    public ArrayList<String> getRestaurantNames() throws IOException, JSONException {
        downloadRestaurant();
        return restaurantNames;
    }

    //Effects: checks whether or not a given restaurant is in the restaurantNames array list
    public boolean checkRestaurantName(String name) {
        if (restaurantNames.contains(name)) {
            return true;
        } else {
            return false;
        }
    }
    //https://developers.zomato.com/documentation#!/restaurant/search
    //Used JsonParserExample repo
    //https://www.baeldung.com/java-http-request
    //https://stackoverflow.com/questions/8997598/importing-json-into-an-eclipse-project/8997634


    //Modifies: this
    //Effects: creates a JSONObject and array from the Zomato API, to get some restaurants
    // from toronto in this case, using the JSONArray created to make lit of restaurants names
    //throws IO exception when problems in reading or writing to a file, JSON exception when problem ith JSON object
    public void downloadRestaurant() throws IOException, JSONException {
        URL url = new URL("https://developers.zomato.com/api/v2.1/search?entity_id=89&entity_type=city&count=5");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("user-key", "383a97c888cccdb2fff90eff2a26cbb0");
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        //System.out.println(content.toString());
        JSONObject jsonObject = new JSONObject(content.toString());
        // jsonObject.get("resturants");
        JSONArray allRestaurants = jsonObject.getJSONArray("restaurants");
        createListRestaurant(allRestaurants);
        //System.out.println(jsonObject.getJSONArray("restaurants"));
        in.close();
        con.disconnect();

    }

    //Modifies: this
    //Effects: creating a list of restaurants names from the JSONArray given
    private void createListRestaurant(JSONArray allRestaurants) throws JSONException {
        for (int index = 0; index < allRestaurants.length(); index++) {
            String resturantName = allRestaurants.getJSONObject(index).getJSONObject("restaurant").getString("name");
            this.restaurantNames.add(resturantName);
            //System.out.println(resturantName);
        }
    }

    //Modifies: this
    //Effects: makes the API calls and then prints out the restaurant names retrieved from the API.
    public void showRestaurants() throws IOException, JSONException {
        downloadRestaurant();
        for (String i : restaurantNames) {
            System.out.println(i);
        }
    }

}


