package com.steven.join_and_join.chat;

public class UserDTO {
    private String email;
    private String uid;
    private String name;
    private String fcmToken;

    public UserDTO(String uid, String email, String name, String fcmToken) {
        this.uid = uid;
        this.email =email;
        this.name = name;
        this.fcmToken = fcmToken;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getFcmToken() {
        return fcmToken;
    }
}
