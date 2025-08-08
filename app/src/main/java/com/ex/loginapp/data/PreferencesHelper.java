package com.ex.loginapp.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.ex.loginapp.model.User;

public class PreferencesHelper {
    private static final String PREFS_NAME = "LoginPrefs";
    private static final String KEY_REMEMBER = "rememberMe";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    private final SharedPreferences prefs;

    public PreferencesHelper(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveRememberedUser(User user) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_REMEMBER, true);
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_PASSWORD, user.getPassword()); // ملاحظة أمان أدناه
        editor.apply();
    }

    public void clearRememberedUser() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_REMEMBER, false);
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_PASSWORD);
        editor.apply();
    }

    public User getRememberedUser() {
        boolean remember = prefs.getBoolean(KEY_REMEMBER, false);
        if (!remember) return null;
        String username = prefs.getString(KEY_USERNAME, "");
        String password = prefs.getString(KEY_PASSWORD, "");
        return new User(username, password);
    }
}
