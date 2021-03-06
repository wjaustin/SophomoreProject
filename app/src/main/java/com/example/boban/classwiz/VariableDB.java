package com.example.boban.classwiz;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Boban on 12/8/2014.
 */
public class VariableDB {
    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "prefs";

    public VariableDB() {
        // Blank
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getUsername(Context context) {
        return getPrefs(context).getString("username_key", "null");
    }

    public static String getPassword(Context context) {
        return getPrefs(context).getString("password_key", "null");
    }

    public static Set<String> getList(Context context) {
        return getPrefs(context).getStringSet("list_key", null);
    }

    public static void setUsername(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("username_key", input);
        editor.commit();
    }

    public static void setPassword(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("password_key", input);
        editor.commit();
    }

    public static void addItem(Context context, String Title, String date){
        ArrayList<String> temp = new ArrayList<String>();
        String full = Title + " " + date;
        temp.add(full);
        Set<String> set = getList(context);
        set.addAll(temp);
        setList(context, set);setList(context, set);
    }

    public static void setList(Context context, Set<String> set) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putStringSet("list_key", set);
        editor.commit();
    }

    public static void clearALL(Context context){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.clear();
        editor.commit();
    }
}
