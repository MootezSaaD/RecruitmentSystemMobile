package tn.medtech.recruitmentsystemapp.util;

import android.content.SharedPreferences;

import tn.medtech.recruitmentsystemapp.api.models.AccessToken;

public class TokenService {

    private static TokenService INSTANCE = null;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private TokenService(SharedPreferences prefs) {
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public static synchronized TokenService getInstance(SharedPreferences prefs) {
        if (INSTANCE == null) {
            INSTANCE = new TokenService(prefs);
        }
        return INSTANCE;
    }

    public void saveToken(AccessToken token) {
        editor.putString("ACCESS_TOKEN", token.getAccessToken()).commit();
        editor.putString("REFRESH_TOKEN", token.getRefreshToken()).commit();
    }

    public void deleteToken() {
        editor.remove("ACCESS_TOKEN").commit();
        editor.remove("REFRESH_TOKEN").commit();
    }

    public AccessToken getToken() {
        AccessToken token = new AccessToken();
        token.setAccessToken(prefs.getString("ACCESS_TOKEN", null));
        token.setRefreshToken(prefs.getString("REFRESH_TOKEN", null));
        return token;
    }

}
