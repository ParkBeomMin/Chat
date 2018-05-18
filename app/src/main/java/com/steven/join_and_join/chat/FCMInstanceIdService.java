package com.steven.join_and_join.chat;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FCMInstanceIdService extends FirebaseInstanceIdService{
    String refreshedToken;

    /*
    1. 앱에서 인스턴스 ID 삭제
    2. 새 기기에서 앱 복원
    3. 사용자가 앱 삭제/재설치
    4. 사용자가 앱 데이터 소거
    할 때 호출
     */
    @Override
    public void onTokenRefresh() {
//        super.onTokenRefresh();
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCMInstanceIdService", "Token : " + refreshedToken);
    }

    public String getToken(){
        return refreshedToken;
    }
}
