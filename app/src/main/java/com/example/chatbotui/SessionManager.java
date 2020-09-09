package com.example.chatbotui;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    //Variables
    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;

    //Session Names
    public static final String SESSION_USERSESSION = "userLoginSession";
    public static final String SESSION_REMEMBERME = "rememberMe";

    //User sessions Variables
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_FIRSTNAME = "firstName";
    public static final String KEY_LASTNAME = "lastName";
    public static final String KEY_CONTACTNUMBER = "contactNumber";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_GENDER = "gender";

    //Remember Me variables
    private static final String IS_REMEMBERME = "IsRememberMe";
    public static final String KEY_SESSIONEMAIL = "email";
    public static final String KEY_SESSIONPASSWORD = "password";

    public SessionManager(Context _context,String sessionName) {
        context = _context;
        usersSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = usersSession.edit();
    }

    //USER login Session
    public HashMap<String, String> getUserDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_FIRSTNAME, usersSession.getString(KEY_FIRSTNAME, null));
        userData.put(KEY_LASTNAME, usersSession.getString(KEY_LASTNAME, null));
        userData.put(KEY_CONTACTNUMBER, usersSession.getString(KEY_CONTACTNUMBER, null));
        userData.put(KEY_EMAIL, usersSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PASSWORD, usersSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_GENDER, usersSession.getString(KEY_GENDER, null));

        return userData;
    }

    public void createLoginSession(String firstName, String lastName, String contactNo, String email, String password, String gender) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_FIRSTNAME, firstName);
        editor.putString(KEY_LASTNAME, lastName);
        editor.putString(KEY_CONTACTNUMBER, contactNo);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_GENDER, gender);

        editor.commit();
    }
    //User login fetching data

    public boolean checkLogin() {
        if (usersSession.getBoolean(IS_LOGIN, true)) {
            return true;
        } else {
            return false;
        }
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }

    public void creatRememberMeSession(String email, String password){

        editor.putBoolean(IS_REMEMBERME, true);

        editor.putString(KEY_SESSIONEMAIL, email);
        editor.putString(KEY_SESSIONPASSWORD, password);

        editor.commit();
    }

    public HashMap<String, String> getRememberMeDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_SESSIONEMAIL, usersSession.getString(KEY_SESSIONEMAIL, null));
        userData.put(KEY_PASSWORD, usersSession.getString(KEY_SESSIONPASSWORD, null));

        return userData;
    }

    public boolean checkRememberMe() {
        if (usersSession.getBoolean(IS_REMEMBERME, true)) {
            return true;
        } else {
            return false;
        }
    }

}
