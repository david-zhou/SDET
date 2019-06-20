/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gisttesting;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;

/**
 *
 * @author davidzhou
 */
public class CRUDGistAPI {
    
    private String gistAPIBaseURL;
    private ArrayList<String> gistIds;
    private String token;
    
    //initialize variables
    public CRUDGistAPI () {
        gistIds = new ArrayList<>();
        token = ReadPropertyFile.getToken();
        
        gistAPIBaseURL = ReadPropertyFile.getProperty("gistapibaseurl");
        
    }
    
    //gets a random gist id
    public String getRandomGist() {
        int l = gistIds.size();
        Random r = new Random();
        int randomNumber = r.nextInt(l);
        return gistIds.get(randomNumber);
    }
    
    // adds a gist with the given values
    public boolean addGistAPI(String description, boolean isPublic, String fileName, String code) throws Exception {
        
        JSONObject jsoncode = new JSONObject().put("content", code);
        JSONObject files = new JSONObject().put(fileName, jsoncode);
        String jsonMessage = new JSONObject().put("description", description).put("public", isPublic).put("files", files).toString();
        
        URL obj = new URL(gistAPIBaseURL);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        
        con.setRequestProperty("Authorization", "token " + token);
     
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(jsonMessage);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        if (responseCode != 201) {
            return false;
        }
        System.out.println("\nSending 'POST' request to URL : " + gistAPIBaseURL);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
        }
        in.close();
        
        JSONObject json = new JSONObject(response.toString());
        String gistid = json.getString("id");
        System.out.println("Gist created with id = " + gistid);
        gistIds.add(gistid);
        return true;
    }
    
    //edits a gist with the given gist id
    public boolean editGistAPI(String gistId, String description, String filename, String code) throws Exception {
        
        String url = gistAPIBaseURL + "/" +gistId;
        
        JSONObject jsoncode = new JSONObject().put("content", code);
        JSONObject files = new JSONObject().put(filename, jsoncode);
        String jsonMessage = new JSONObject().put("description", description).put("files", files).toString();
        
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");  
        con.setRequestProperty("Authorization", "token " + token);
        
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(jsonMessage);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            return false;
        }
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
        }
        in.close();
        
        JSONObject json = new JSONObject(response.toString());
        String gistid = json.getString("id");
        System.out.println("updated Gist id = " + gistid);
        return true;
    }
}
