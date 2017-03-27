package com.peeredge.ui.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by root on 3/25/17.
 */

public class AppManager {

    private static AppManager manager = null;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MYPREF = "mypref";

    private Context context;

    public AppManager(Context context) {
        this.context = context;
    }

    public static AppManager getInstance(Context context)
    {
        if(manager == null)
            manager = new AppManager(context);

        return manager;
    }

    public void setUsername(String username)
    {
        SharedPreferences sPref = context.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(USERNAME, username);
        ed.commit();
    }

    public void setPassword(String password)
    {
        SharedPreferences sPref = context.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(PASSWORD, password);
        ed.commit();
    }

    public String getUsername()
    {
        SharedPreferences sPref = context.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        String username = sPref.getString(USERNAME, null);

        return username;
    }

    public String getPassword()
    {
        SharedPreferences sPref = context.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        String password = sPref.getString(PASSWORD, null);

        return password;
    }

    public boolean hasAccount()
    {
        String username = getUsername();
        String password = getPassword();

        return (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password));
    }

    public void clear()
    {
        SharedPreferences sPref = context.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.clear();
        ed.commit();
    }


}
