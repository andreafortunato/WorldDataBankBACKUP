package it.apperol.group.worlddatabank.myadapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import it.apperol.group.worlddatabank.R;
import it.apperol.group.worlddatabank.itemlist.MyTopicItem;
import it.apperol.group.worlddatabank.myactivities.IndicatorActivity;
import it.apperol.group.worlddatabank.myviews.MyTextView;

public class MyTopicAdapter extends RecyclerView.Adapter<MyTopicAdapter.ViewHolder> {

    public static Integer topicID;

    private List<MyTopicItem> myTopicItems;
    private Context context;

    public MyTopicAdapter(List<MyTopicItem> myTopicItems, Context context) {
        this.myTopicItems = myTopicItems;
        this.context = context;
    }


    @NonNull
    @Override
    public MyTopicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.topic_item, viewGroup, false);
        return new MyTopicAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTopicAdapter.ViewHolder viewHolder, int i) {
        final MyTopicItem myTopicItem = myTopicItems.get(i);

        viewHolder.myTvTopicName.setText(myTopicItem.getTopicName());

        viewHolder.llTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicID = myTopicItem.getTopicID();
                Intent indicatorIntent = new Intent(context, IndicatorActivity.class);
                context.startActivity(indicatorIntent);
                Toast.makeText(context, "Hai cliccato sul topic " + myTopicItem.getTopicName() + " ID: " + myTopicItem.getTopicID(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTopicItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public MyTextView myTvTopicName;
        public LinearLayout llTopic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            myTvTopicName = itemView.findViewById(R.id.myTvTopicName);
            llTopic = itemView.findViewById(R.id.llTopic);
        }
    }
}
