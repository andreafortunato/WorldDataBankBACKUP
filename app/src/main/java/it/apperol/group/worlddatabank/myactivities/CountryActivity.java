package it.apperol.group.worlddatabank.myactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.apperol.group.worlddatabank.R;
import it.apperol.group.worlddatabank.itemlist.MyCountryItem;
import it.apperol.group.worlddatabank.myadapters.MyCountryAdapter;
import it.apperol.group.worlddatabank.mythreads.FetchData;

public class CountryActivity extends AppCompatActivity {

    private static Context countryActivityContext;

    private static RecyclerView recyclerView;
    private static RecyclerView.Adapter adapter;
    private static JSONArray ja;

    private static List<MyCountryItem> myCountryItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        countryActivityContext = this;

        recyclerView = findViewById(R.id.rvCountry);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FetchData process = new FetchData("http://api.worldbank.org/v2/country/?format=json", this, 0);
        process.execute();
    }

    public static void fetchCountry() {
        myCountryItems = new ArrayList<>();

        ja = FetchData.ja;

        try {
            for(int i = 0; i < ja.getJSONArray(1).length(); i++){
                JSONObject jo = (JSONObject) ja.getJSONArray(1).get(i);
                if(!jo.get("capitalCity").equals("")) {
                    MyCountryItem myCountryItem = new MyCountryItem(
                            jo.getString("name") + " (" + jo.getString("iso2Code") + ")",
                            jo.getString("capitalCity"),
                            "http://flagpedia.net/data/flags/w580/" + jo.getString("iso2Code").toLowerCase() + ".png", jo.getString("iso2Code"));

                    myCountryItems.add(myCountryItem);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Collections.sort(myCountryItems, new Comparator<MyCountryItem>(){
            public int compare(MyCountryItem obj1, MyCountryItem obj2) {
                // Ordine crescente
                return obj1.getCountryName().compareToIgnoreCase(obj2.getCountryName());
            }
        });

        adapter = new MyCountryAdapter(myCountryItems, countryActivityContext);
        recyclerView.setAdapter(adapter);
    }
}
