package it.apperol.group.worlddatabank.mythreads;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import it.apperol.group.worlddatabank.MainActivity;
import it.apperol.group.worlddatabank.itemlist.MyIndicatorItem;
import it.apperol.group.worlddatabank.myactivities.CountryActivity;
import it.apperol.group.worlddatabank.myactivities.IndicatorActivity;
import it.apperol.group.worlddatabank.myactivities.PlotActivity;
import it.apperol.group.worlddatabank.myactivities.ProgressActivity;
import it.apperol.group.worlddatabank.myactivities.TopicActivity;
import it.apperol.group.worlddatabank.myadapters.MyIndicatorAdapter;


public class FetchData extends AsyncTask<Void,Void,Void> {

    private Boolean fetchDone;

    private String urlString;
    private Context mContext;
    private Integer calling;

    private String data = "";
    private Integer totalItems;
    public static JSONArray ja;

    private AlertDialog.Builder noDataFoundDialog;

    public FetchData(String urlString, Context mContext, Integer calling) {
        this.urlString = urlString;
        this.mContext = mContext;
        this.calling = calling;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        if(fetch(urlString + "&per_page=1")) {
            data = "";
            fetch(urlString + "&per_page=" + totalItems);
        } else {
            ja = null;
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(!fetchDone) {
            ProgressActivity.actProg.finish();
            noDataFoundDialog.setTitle("AlertDialog di MrWebMaster");
            noDataFoundDialog.setMessage("Questa è la mia prima AlertDialog");
            noDataFoundDialog.setCancelable(false);
            noDataFoundDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = noDataFoundDialog.create();
            alert.show();
            return;
        }


        if(calling == 0) { // Mi ha chiamato il contry activity
            CountryActivity.fetchCountry();
        }
        else if (calling == 1){ // Mi ha chiamato l indicators activity
            IndicatorActivity.fetchIndicator();
        }
        else if(calling == 2){ // Mi ha chiamato il topic activity
            TopicActivity.fetchTopic();
        }
        else if(calling == 3){ // Mi ha chiamato plot activity
            MyIndicatorAdapter.fetchDataControl();
        }
        ProgressActivity.actProg.finish();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        noDataFoundDialog = new AlertDialog.Builder(mContext);

        Intent progressIntent = new Intent(MainActivity.mainActivityContext, ProgressActivity.class);
        progressIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainActivity.mainActivityContext.startActivity(progressIntent);
    }

    private Boolean fetch(String urlString) {
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
        }catch (JSONException e) {
            e.printStackTrace();
            fetchDone = false;
            return false;
        }catch (Exception e){
            e.printStackTrace();
            fetchDone = false;
            return false;
        }
        fetchDone = true;
        return true;
    }
}
