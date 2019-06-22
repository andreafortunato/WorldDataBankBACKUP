package it.apperol.group.worlddatabank.myadapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import it.apperol.group.worlddatabank.R;
import it.apperol.group.worlddatabank.itemlist.MyIndicatorItem;
import it.apperol.group.worlddatabank.myactivities.PlotActivity;
import it.apperol.group.worlddatabank.myviews.MyTextView;

public class MyIndicatorAdapter extends RecyclerView.Adapter<MyIndicatorAdapter.ViewHolder> {

    public static String indicatorID;

    private List<MyIndicatorItem> myIndicatorItems;
    private Context context;

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
        viewHolder.myTvIndicatorID.setText("Indicator ID: " + myIndicatorItem.getIndicatorID());

        viewHolder.llIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicatorID = myIndicatorItem.getIndicatorID();
                Intent plotIntent = new Intent(context, PlotActivity.class);
                context.startActivity(plotIntent);
                Toast.makeText(context, "Hai cliccato sull'indicator " + myIndicatorItem.getIndicatorName(), Toast.LENGTH_SHORT).show();
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
}
