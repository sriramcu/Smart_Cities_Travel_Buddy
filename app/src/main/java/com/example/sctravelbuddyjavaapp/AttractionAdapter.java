package com.example.sctravelbuddyjavaapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.MyViewHolder> {
    private ArrayList<Attraction> attractions;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position) throws IOException;
    }

    public void setOnItemClickListener(OnItemClickListener l){
        listener = l;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView address;
        public TextView description;
        public TextView type;

        public MyViewHolder(View itemView, OnItemClickListener cListener) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            address=itemView.findViewById(R.id.address);
            description=itemView.findViewById(R.id.description);
            type = itemView.findViewById(R.id.attrType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cListener != null) {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION) {
                            try{
                                cListener.onItemClick(position);
                            }
                            catch(IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });

        }
    }

    public AttractionAdapter(ArrayList<Attraction> a) {
        attractions = a;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionAdapter.MyViewHolder holder, int position) {
        Attraction current = attractions.get(position);

        holder.name.setText(current.getName());
        holder.address.setText(current.getAddress());
        holder.description.setText(current.getDescription());
        int type_num = current.getType();
        String type_str = "";
        if (type_num==1){
            type_str = "Tourist Attraction";
            holder.type.setTextColor(Color.RED);
        }

        if (type_num==2){
            type_str = "Hotel";
            holder.type.setTextColor(Color.YELLOW);
        }

        if (type_num==3){
            type_str = "Restaurant";
            holder.type.setTextColor(Color.GREEN);
        }
        holder.type.setText(type_str);
    }

    @Override
    public int getItemCount(){
        return attractions.size();
    }


}