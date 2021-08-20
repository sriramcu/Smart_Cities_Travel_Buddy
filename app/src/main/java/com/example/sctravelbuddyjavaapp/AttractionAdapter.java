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


/**
 * Class to represent and interact with card items stored in the Recycler View of the MapsActivity class
 */
public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.MyViewHolder> {
    private ArrayList<Attraction> attractions;
    private OnItemClickListener listener;


    public interface OnItemClickListener{
        void onItemClick(int position) throws IOException;
    }

    /**
     * Used to set the listener attribute for the Class (for the card item)
     * @param l is the listener set in the MapsActivity class
     */
    public void setOnItemClickListener(OnItemClickListener l){
        listener = l;
    }

    /**
     * Nested class specifically for the data displayed in the ViewHolder
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView address;
        public TextView description;
        public TextView type;

        /**
         *
         * @param itemView is the View corresponding to the card item
         * @param cListener is the click listener for the card item
         */
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


    /**
     * Used to instantiate a new ViewHolder object corresponding to a certain type and parent
     * @param parent is the parent view group (RecyclerView in this case)
     * @param viewType is the type of view being instantiated
     * @return ViewHolder object
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v, listener);
        return vh;
    }

    /**
     * Used to bind arraylist values to the XML UI elements and display them in the app
     * @param holder ViewHolder object
     * @param position is the index for the attractions ArrayList corresponding to one card
     */
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

    /**
     * Used to find the number of cards (attractions) to be displayed
     * @return size of attractions arraylist
     */
    @Override
    public int getItemCount(){
        return attractions.size();
    }


}