package it.apperol.group.worlddatabank.myadapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import it.apperol.group.worlddatabank.R;
import it.apperol.group.worlddatabank.itemlist.MyCountryItem;
import it.apperol.group.worlddatabank.myactivities.TopicActivity;
import it.apperol.group.worlddatabank.myviews.MyTextView;

public class MyCountryAdapter extends RecyclerView.Adapter<MyCountryAdapter.ViewHolder> {

    public static String countryIso2Code;

    private List<MyCountryItem> myCountryItems;
    private Context context;

    public MyCountryAdapter(List<MyCountryItem> myCountryItems, Context context) {
        this.myCountryItems = myCountryItems;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.country_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MyCountryItem myCountryItem = myCountryItems.get(i);

        viewHolder.myTvCountryName.setText(myCountryItem.getCountryName());
        viewHolder.myTvCapitalName.setText(myCountryItem.getCapitalName());

        Picasso.get()
                .load(myCountryItem.getImageUrl())
                .into(viewHolder.ivFlag);

        viewHolder.llCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryIso2Code = myCountryItem.getCountryIso2Code();
                Intent topicIntent = new Intent(context, TopicActivity.class);
                context.startActivity(topicIntent);
                Toast.makeText(context, "Hai cliccato sul paese " + myCountryItem.getCountryName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myCountryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public MyTextView myTvCountryName;
        public MyTextView myTvCapitalName;
        public ImageView ivFlag;
        public LinearLayout llCountry;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            myTvCountryName = itemView.findViewById(R.id.myTvCountryName);
            myTvCapitalName = itemView.findViewById(R.id.myTvCapitalName);
            ivFlag = itemView.findViewById(R.id.ivFlag);
            llCountry = itemView.findViewById(R.id.llCountry);
        }
    }
}
