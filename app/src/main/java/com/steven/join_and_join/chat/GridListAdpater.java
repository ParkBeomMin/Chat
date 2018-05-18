package com.steven.join_and_join.chat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GridListAdpater extends RecyclerView.Adapter<GridListAdpater.GridListViewHolder> {


    private Context mContext;
    private ArrayList<String> mTitle = new ArrayList<String>();

    public GridListAdpater(Context mContext, ArrayList<String> mTitle) {
        this.mContext = mContext;
        this.mTitle = mTitle;
    }

    @NonNull
    @Override
    public GridListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_list, parent, false);
        GridListViewHolder viewHolder = new GridListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridListViewHolder holder, final int position) {

        holder.titleTv.setText(mTitle.get(position));
        holder.gridLi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RoomActivity.class);
                intent.putExtra("title", mTitle.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTitle.size();
    }

    public static class GridListViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout gridLi;
        private TextView titleTv;
        public GridListViewHolder(View itemView) {
            super(itemView);
            gridLi = (LinearLayout)itemView.findViewById(R.id.grid_li);
            titleTv = (TextView)itemView.findViewById(R.id.grid_tv);
        }
    }
}
