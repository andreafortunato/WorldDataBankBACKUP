package it.apperol.group.worlddatabank.mythreads;

import android.os.AsyncTask;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class FetchData extends AsyncTask<Void,Void,Void> {


    private String data = "";
    private Integer totalItems;
    public static JSONArray ja;

    public FetchData(String urlString) {
        this.urlString = urlString;
    }

    private String urlString;

    @Override
    protected Void doInBackground(Void... voids) {
        fetch(urlString + "&per_page=1");
        data = "";
        fetch(urlString + "&per_page=" + totalItems);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    private void fetch(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                if (line != null) {
                    data = data + line;
                }
            }

            ja = new JSONArray(data);

            totalItems = ja.getJSONObject(0).getInt("total");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
