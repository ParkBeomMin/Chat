package com.steven.join_and_join.chat;

//import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
//import com.steven.join_and_join.onetoone.databinding.SearchFragmentBinding;

import java.util.ArrayList;

public class SearchFragment extends android.support.v4.app.Fragment {
    private String TAG = "SearchFragment";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText SearchEdt;
    private Button SearchBtn;
    private RecyclerView SearchList;

    private ArrayList<String> searchArray;
//    ViewDataBinding binding;
//    SearchFragmentBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchArray = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false);
//        View view = binding.getRoot();

        View view = inflater.inflate(R.layout.search_fragment, null);
        SearchList = (RecyclerView)view.findViewById(R.id.search_list);
        final SearchListAdapter searchListAdapter = new SearchListAdapter(getContext(), searchArray);
        SearchList.setLayoutManager(new LinearLayoutManager(getActivity()));
        SearchList.setAdapter(searchListAdapter);
        SearchEdt = (EditText)view.findViewById(R.id.search_edt);
        SearchBtn = (Button)view.findViewById(R.id.search_btn);
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("users").whereEqualTo("name", SearchEdt.getText().toString()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                        for(DocumentSnapshot ds : documentSnapshots ) {
                            Log.d(TAG, ds.get("name").toString());
//                            ds.get("name");
                            searchArray.add(ds.get("name").toString());
                        }
                        searchListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        return view;
    }


//    public void searchEvent() {
//        db.collection("users").whereEqualTo("name", SearchEdt.getText().toString()).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//
//                for(DocumentSnapshot ds : documentSnapshots ) {
//                    Log.d(TAG, ds.get("name").toString());
////                            ds.get("name");
//                    searchArray.add(ds.get("name").toString());
//                }
//            }
//        });
//    }
}
