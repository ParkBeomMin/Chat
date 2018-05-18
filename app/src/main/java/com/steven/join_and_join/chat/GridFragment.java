package com.steven.join_and_join.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GridFragment extends Fragment {
    private RecyclerView gridRecyclerView;
    private ArrayList<String> roomList = new ArrayList<String>();

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_fragment, null);

        gridRecyclerView = (RecyclerView)view.findViewById(R.id.room_recyclerview);
        final GridListAdpater gridListAdpater = new GridListAdpater(getContext(), roomList);
        gridRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        gridRecyclerView.setAdapter(gridListAdpater);
        mFirestore.collection("users").document(mAuth.getUid()).collection("room").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                roomList.clear();
                for (DocumentSnapshot ds:documentSnapshots) {
                    roomList.add(ds.get("title").toString());
                }
                gridListAdpater.notifyDataSetChanged();
            }
        });
        return view;
    }
}
