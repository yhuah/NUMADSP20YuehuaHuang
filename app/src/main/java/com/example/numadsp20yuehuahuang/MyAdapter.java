package com.example.numadsp20yuehuahuang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<LinkCollectorActivity.DataPair> myData;

    public MyAdapter(ArrayList<LinkCollectorActivity.DataPair> myData){

        this.myData= myData;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView url;

        public MyViewHolder(@NonNull View v){
            super(v);
            name=v.findViewById(R.id.link_name);
            url=v.findViewById(R.id.link_url);

        }
    }

    //create new views
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new MyViewHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.my_link_layout, parent, false));

    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder( @NonNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String link_name = myData.get(position).getName();
        String link_url = myData.get(position).getUrl();

        holder.name.setText(link_name);
        holder.url.setText(link_url);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myData.size();
    }


}
