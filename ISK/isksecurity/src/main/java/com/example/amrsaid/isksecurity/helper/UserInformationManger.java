package com.example.amrsaid.isksecurity.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by AmrSaid on 05/01/2016.
 */
public class UserInformationManger {
    private static String TAG = UserInformationManger.class.getSimpleName();

    // Shared Preferences for user inf
    SharedPreferences UserPref;
    SharedPreferences.Editor editor;
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Shared preferences file name
    private static final String PREF_NAME = "ISKUSERINFO";
    private static final String secret_Key = "secretKey";
    private static final String user_id = "user_id";
    private static final String group_id = "group_id";
    private static final String full_Name = "fullName";
    private static final String email = "email";
    private static final String Phone_Number = "phone";
    private static final String Profile_picture = "picture";
    public UserInformationManger(Context context) {
        this._context = context;
        UserPref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = UserPref.edit();
    }
    public void setProfilePicture(String photo) {

        editor.putString(Profile_picture, photo);

        // commit changes
        editor.commit();

        Log.d(TAG, "User Profile picture session modified!");
    }
    public void setPhoneNumber(String Phone) {

        editor.putString(Phone_Number, Phone);

        // commit changes
        editor.commit();

        Log.d(TAG, "User Phone session modified!");
    }
    public void setSecretKey(String SecretKey) {

        editor.putString(secret_Key, SecretKey);

        // commit changes
        editor.commit();

        Log.d(TAG, "User SecretKey session modified!");
    }
    public void setEmail(String Email) {

        editor.putString(email, Email);

        // commit changes
        editor.commit();

        Log.d(TAG, "User Email session modified!");
    }
    public void setFullName(String FullName) {

        editor.putString(full_Name, FullName);

        // commit changes
        editor.commit();

        Log.d(TAG, "User full Name session modified!");
    }
    public void setUserId(String UserId) {

        editor.putString(user_id, UserId);

        // commit changes
        editor.commit();

        Log.d(TAG, "User UserId session modified!");
    }
    public void setGroupId(String GroupId) {

        editor.putString(group_id, GroupId);

        // commit changes
        editor.commit();

        Log.d(TAG, "User GroupId session modified!");


    }
    public String getGroupId(){
        return UserPref.getString(group_id, "");
    }
    public String getUserId(){
        return UserPref.getString(user_id, "");
    }
    public String getSecretKey(){
        return UserPref.getString(secret_Key, "");
    }
    public String getFullName(){
        return UserPref.getString(full_Name, "");
    }
    public String getEmail(){
        return UserPref.getString(email, "");
    }
    public String getPhoneNumber(){
        return UserPref.getString(Phone_Number, "");
    }
    public String getProfilePicture(){
        return UserPref.getString(Profile_picture, "");
    }
}
