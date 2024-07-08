package com.example.musicplayerfive;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MugicListAdapter extends RecyclerView.Adapter<MugicListAdapter.ViewHolder>
{


    ArrayList<AudioModel> songsList;
    Context context;

    public MugicListAdapter(ArrayList<AudioModel> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new MugicListAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MugicListAdapter.ViewHolder holder, int position) {


        AudioModel songData = songsList.get(position);
        holder.titleTextView.setText(songData.getTitle());

        if (MyMediaPlayer.currentIdex==position)
        {
            holder.titleTextView.setTextColor(Color.parseColor("#0000C8"));
        }
        else
        {
            holder.titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIdex = position;

                Intent intent = new Intent(context, MugicPlayerActivity.class);
                intent.putExtra("LIST",songsList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);



            }
        });


    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView titleTextView;
        ImageView iconImageView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.mugic_title_text);
            iconImageView = itemView.findViewById(R.id.icon_view);


        }
    }




}
