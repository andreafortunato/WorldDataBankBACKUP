package it.apperol.group.worlddatabank.myactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.apperol.group.worlddatabank.R;
import it.apperol.group.worlddatabank.itemlist.MyCountryItem;
import it.apperol.group.worlddatabank.myadapters.MyCountryAdapter;
import it.apperol.group.worlddatabank.myadapters.MyIndicatorAdapter;
import it.apperol.group.worlddatabank.myobjects.PlotObj;
import it.apperol.group.worlddatabank.mythreads.FetchData;

public class PlotActivity extends AppCompatActivity {

    private List<PlotObj> plotDatas = new ArrayList<>();
    private TextView myTest;

    private JSONArray ja;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        myTest = findViewById(R.id.myTest);

        FetchData process = new FetchData("http://api.worldbank.org/v2/country/" + MyCountryAdapter.countryIso2Code + "/indicator/" + MyIndicatorAdapter.indicatorID + "?format=json");
        try {
            process.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ja = FetchData.ja;

        try {
            for(int i = 0; i < ja.getJSONArray(1).length(); i++){
                JSONObject jo = (JSONObject) ja.getJSONArray(1).get(i);
                if(!jo.get("value").equals("") || jo.get("value") != null) {
                    plotDatas.add(new PlotObj(jo.get("date").toString(), jo.get("value").toString()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        myTest.setText(plotDatas.toString());
        String dataaa = "";
        for(int i=0; i < plotDatas.size(); i++) {
            dataaa += "Year: " + plotDatas.get(i).getYear() + "Value: " + plotDatas.get(i).getValue() + "\n";
        }

        myTest.setText(dataaa);
    }
}
