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
import it.apperol.group.worlddatabank.itemlist.MyTopicItem;
import it.apperol.group.worlddatabank.myadapters.MyTopicAdapter;
import it.apperol.group.worlddatabank.mythreads.FetchData;

public class TopicActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private JSONArray ja;

    private List<MyTopicItem> myTopicItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        recyclerView = findViewById(R.id.rvTopic);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        myTopicItems = new ArrayList<>();
        FetchData process = new FetchData("http://api.worldbank.org/v2/topic/?format=json");
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
                MyTopicItem myTopicItem = new MyTopicItem(jo.getString("value"), jo.getInt("id"));

                myTopicItems.add(myTopicItem);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Collections.sort(myTopicItems, new Comparator<MyTopicItem>(){
            public int compare(MyTopicItem obj1, MyTopicItem obj2) {
                // Ordine crescente
                return obj1.getTopicName().compareToIgnoreCase(obj2.getTopicName());
            }
        });

        adapter = new MyTopicAdapter(myTopicItems, this);
        recyclerView.setAdapter(adapter);
    }

}
