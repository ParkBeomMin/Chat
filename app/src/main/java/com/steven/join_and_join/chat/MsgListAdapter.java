package com.steven.join_and_join.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MsgListAdapter extends RecyclerView.Adapter<MsgListAdapter.MsgListViewHolder> {

    Context mContext;
    ArrayList<String> msgList = new ArrayList<String>();

    public MsgListAdapter(Context mContext, ArrayList<String> msgList) {
        this.mContext = mContext;
        this.msgList = msgList;
    }

    @NonNull
    @Override
    public MsgListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.msg_list, parent, false);
        MsgListViewHolder viewHolder = new MsgListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MsgListViewHolder holder, int position) {

        holder.msgTv.setText(msgList.get(position));
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public static class MsgListViewHolder extends RecyclerView.ViewHolder{
        private TextView msgTv;

        public MsgListViewHolder(View itemView) {
            super(itemView);
            msgTv = (TextView)itemView.findViewById(R.id.msg_list_tv);
        }
    }
}
