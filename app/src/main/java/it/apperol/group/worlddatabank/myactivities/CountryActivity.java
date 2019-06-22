package it.apperol.group.worlddatabank.myactivities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.apperol.group.worlddatabank.R;
import it.apperol.group.worlddatabank.itemlist.MyCountryItem;
import it.apperol.group.worlddatabank.myadapters.MyCountryAdapter;
import it.apperol.group.worlddatabank.mythreads.FetchData;

public class CountryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private JSONArray ja;

    private List<MyCountryItem> myCountryItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        recyclerView = findViewById(R.id.rvCountry);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        myCountryItems = new ArrayList<>();
        FetchData process = new FetchData("http://api.worldbank.org/v2/country/?format=json");
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

        adapter = new MyCountryAdapter(myCountryItems, this);
        recyclerView.setAdapter(adapter);
    }
}
