package it.apperol.group.worlddatabank.myadapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

import it.apperol.group.worlddatabank.R;
import it.apperol.group.worlddatabank.itemlist.MyIndicatorItem;
import it.apperol.group.worlddatabank.myactivities.IndicatorActivity;
import it.apperol.group.worlddatabank.myactivities.PlotActivity;
import it.apperol.group.worlddatabank.myobjects.PlotObj;
import it.apperol.group.worlddatabank.mythreads.FetchData;
import it.apperol.group.worlddatabank.myviews.MyTextView;

public class MyIndicatorAdapter extends RecyclerView.Adapter<MyIndicatorAdapter.ViewHolder> {

    public static String indicatorID;

    private List<MyIndicatorItem> myIndicatorItems;
    private static Context context;

    public static JSONArray ja;

    public static MyIndicatorItem myIndicatorItemText;

    public MyIndicatorAdapter(List<MyIndicatorItem> myIndicatorItems, Context context) {
        this.myIndicatorItems = myIndicatorItems;
        this.context = context;
    }


    @NonNull
    @Override
    public MyIndicatorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.indicator_item, viewGroup, false);
        return new MyIndicatorAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyIndicatorAdapter.ViewHolder viewHolder, int i) {
        final MyIndicatorItem myIndicatorItem = myIndicatorItems.get(i);

        viewHolder.myTvIndicatorName.setText(myIndicatorItem.getIndicatorName());
        viewHolder.myTvIndicatorID.setText(String.format("Indicator ID: %s", myIndicatorItem.getIndicatorID()));

        viewHolder.llIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicatorID = myIndicatorItem.getIndicatorID();
                myIndicatorItemText = myIndicatorItem;
                FetchData process = new FetchData("http://api.worldbank.org/v2/country/" + MyCountryAdapter.countryIso2Code + "/indicator/" + myIndicatorItem.getIndicatorID() + "?format=json", IndicatorActivity.indicatorActivityContext, 3);
                process.execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myIndicatorItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public MyTextView myTvIndicatorName;
        public MyTextView myTvIndicatorID;
        public LinearLayout llIndicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            myTvIndicatorName = itemView.findViewById(R.id.myTvIndicatorName);
            myTvIndicatorID = itemView.findViewById(R.id.myTvIndicatorID);
            llIndicator = itemView.findViewById(R.id.llIndicator);
        }
    }

    public static void fetchDataControl() {
        ja = FetchData.ja;

                /*try {
                    for(int i = 0; i < ja.getJSONArray(1).length(); i++){
                        JSONObject jo = (JSONObject) ja.getJSONArray(1).get(i);
                        if(!jo.get("value").equals("") && jo.get("value") != null && !jo.get("value").equals("null") && !jo.isNull("value")) {
                            plotDatas.add(new PlotObj(jo.get("date").toString(), jo.get("value").toString()));
                        }
                    }
                }catch (Exception e){
                    Log.i("[CRASH]", "CRASH");
                    e.printStackTrace();
                    return;
                }*/

        if(ja != null) {
            Intent plotIntent = new Intent(context, PlotActivity.class);
            context.startActivity(plotIntent);
            Toast.makeText(context, "Hai cliccato sull'indicator " + myIndicatorItemText.getIndicatorName(), Toast.LENGTH_SHORT).show();
        } else {
            Log.i("[ERRORE]", "ERRORE");
        }
    }
}
