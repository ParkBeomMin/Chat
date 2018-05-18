package com.steven.join_and_join.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {

    private TextView userNameTv;

    private FirebaseAuth mAuth;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, null);
        userNameTv = (TextView)view.findViewById(R.id.user_name_tv);
        userNameTv.setText(mAuth.getCurrentUser().getDisplayName() + " 님 안녕하세요!!");
        return view;
    }



    void init() {
        mAuth = FirebaseAuth.getInstance();
    }
}
