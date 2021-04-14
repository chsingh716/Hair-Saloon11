package uk.ac.tees.S6145076.HairSaloon;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPref {

    private static final String MY_PREF_NAME = "bomor";

    private static final String USER_NAME = "user_name";
    public static final String IMAGE = "image";
    private static final String USER_ID = "user_id";
    private static final String IS_LOGIN = "is_login";
    private static final String USER_RATING = "user_rating";

    private static MySharedPref mySharedPref;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
//    private Gson gson;

    private MySharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

//        type = new TypeToken<List<RatingModel>>() {
//        }.getType();
//
//        gson = new Gson();

    }


    public static synchronized MySharedPref getInstance(Context context) {
        if (mySharedPref == null)
            mySharedPref = new MySharedPref(context);

        return mySharedPref;
    }


    public void saveName(String name) {
        editor.putString(USER_NAME, name).commit();
    }

    public void saveValue(String name, String key) {
        editor.putString(key, name).commit();
    }

    public String getName() {
        return sharedPreferences.getString(USER_NAME, null);
    }
    public String getValue(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void saveID(String id) {
        editor.putString(USER_ID, id).commit();
    }

    public void saveUserRating(float rating) {
        editor.putFloat(USER_RATING, rating).commit();
    }

    public float getUserRating() {
        return sharedPreferences.getFloat(USER_RATING, 0f);
    }

    public String getUserId() {
        return sharedPreferences.getString(USER_ID, null);
    }

    public void setLogin(boolean value) {
        editor.putBoolean(IS_LOGIN, value).commit();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

}
