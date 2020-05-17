package tn.medtech.recruitmentsystemapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class TokenService {

    private static SharedPreferences sharedPreferences;

    private TokenService() {}

    public static void init(Context context) {
        if(sharedPreferences==null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
        }
    }
    public static String getToken() {
        return sharedPreferences.getString("token","");
    }
    public static void storeToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token",token);
        editor.commit();
    }
}
