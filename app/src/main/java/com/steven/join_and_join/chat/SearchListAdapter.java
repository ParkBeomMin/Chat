package com.steven.join_and_join.chat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder> {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    Context mContext;
    List<String> name;
    Dialog mDialog;

    public SearchListAdapter(Context mContext, List<String> name) {
        this.mContext = mContext;
        this.name = name;
    }

    @NonNull
    @Override
    public SearchListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.search_list,parent,false);
        final SearchListViewHolder viewHolder = new SearchListViewHolder(view);

        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.make_room_dialog);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText titleEdt = (EditText)mDialog.findViewById(R.id.room_title_edt);
        final Button titleBtn = (Button)mDialog.findViewById(R.id.room_title_btn);

        viewHolder.nameLi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String roomTitle = titleEdt.getText().toString();
                        final String[] opponentUid = new String[1];
                        mFirestore.collection("users").whereEqualTo("name", name.get(viewHolder.getAdapterPosition())).addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                                for(DocumentSnapshot ds : documentSnapshots ) {
                                    opponentUid[0] = ds.get("uid").toString();
                                    Log.d("opp", opponentUid[0]);
                                }
                                Map room = new HashMap();
                                room.put("title", roomTitle);
                                room.put("opponent", opponentUid[0]);
                                mFirestore.collection("users").document(mAuth.getUid()).collection("room")
                                        .add(room)
                                        .addOnSuccessListener(new OnSuccessListener() {
                                            @Override
                                            public void onSuccess(Object o) {

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });

                                Map room_ = new HashMap();
                                room_.put("title", roomTitle);
                                room_.put("opponent", mAuth.getUid());
                                mFirestore.collection("users").document(opponentUid[0]).collection("room").add(room_)
                                        .addOnSuccessListener(new OnSuccessListener() {
                                            @Override
                                            public void onSuccess(Object o) {

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                            }
                        });
                    mDialog.cancel();
                    Intent intent = new Intent(mContext, RoomActivity.class);
                    intent.putExtra("title", roomTitle);
                    mContext.startActivity(intent);

                    }
                });
        mDialog.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListViewHolder holder, int position) {

        holder.nameTv.setText(name.get(position));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public static class SearchListViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout nameLi;
        private TextView nameTv;
        public SearchListViewHolder(View itemView) {
            super(itemView);

            nameLi = (LinearLayout)itemView.findViewById(R.id.search_list_li);
            nameTv = (TextView)itemView.findViewById(R.id.search_list_user_tv);
        }
    }
}
