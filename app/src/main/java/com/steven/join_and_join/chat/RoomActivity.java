package com.steven.join_and_join.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RoomActivity extends AppCompatActivity {
    private EditText msgEdt; // test
    private Button msgBtn; // teset

    private RecyclerView msgRecyclerView;
    private MsgListAdapter msgListAdapter;
    private ArrayList<String> msgList = new ArrayList<String>();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Intent intent = getIntent();
        Log.d("RoomActivity",intent.getExtras().getString("title"));
        final String title = intent.getExtras().getString("title");
        final String[] title_id = new String[2];
        final String[] opponent_id = new String[1];
        msgRecyclerView = (RecyclerView)findViewById(R.id.msg_recyclerview);
        msgListAdapter = new MsgListAdapter(this, msgList);
        msgRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        msgRecyclerView.setAdapter(msgListAdapter);
        mFireStore.collection("users").document(mAuth.getUid()).collection("room").whereEqualTo("title", title).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentSnapshot ds : documentSnapshots) {
                    Log.d("RoomActivity", ds.getId());
                    title_id[0] = ds.getId();
                    opponent_id[0] = ds.get("opponent").toString();

                    Log.d("RoomActivity", title_id[0]);
                }
                mFireStore.collection("users").document(mAuth.getUid()).collection("room").document(title_id[0]).collection("contents").orderBy("regDate")
                   .addSnapshotListener(new EventListener<QuerySnapshot>() {
                       @Override
                       public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                           msgList.clear();
                                    for (DocumentSnapshot ds : documentSnapshots) {
                                        msgList.add(ds.get("content").toString());
                                    }
                                    msgListAdapter.notifyDataSetChanged();
                       }

                   });//
            }
        });


        msgEdt = (EditText)findViewById(R.id.msg_edt);
        msgBtn = (Button)findViewById(R.id.msg_btn);
        msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map msg = new HashMap();
                msg.put("sender", mAuth.getUid());
                msg.put("content", msgEdt.getText().toString());
                msg.put("regDate", new Date());
                mFireStore.collection("users").document(mAuth.getUid()).collection("room").document(title_id[0]).collection("contents").add(msg).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
                mFireStore.collection("users").document(opponent_id[0]).collection("room").whereEqualTo("title", title).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        for (DocumentSnapshot ds: documentSnapshots) {
                         title_id[1] = ds.getId();
                        }
                        mFireStore.collection("users").document(opponent_id[0]).collection("room").document(title_id[1]).collection("contents").add(msg).addOnSuccessListener(new OnSuccessListener() {
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


            }
        });


    }
}
