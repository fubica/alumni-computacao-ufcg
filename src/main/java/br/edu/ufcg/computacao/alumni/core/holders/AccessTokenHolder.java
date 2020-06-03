package br.edu.ufcg.computacao.alumni.core.holders;

import br.edu.ufcg.computacao.alumni.api.http.response.AccessToken;

public class AccessTokenHolder {
    private static AccessTokenHolder instance;
    private static String state;
    private static AccessToken accessToken;

    private AccessTokenHolder() {
        this.state = "";
        this.accessToken = new AccessToken(null, 0);
    }

    public static synchronized AccessTokenHolder getInstance() {
        if (instance == null) {
            instance = new AccessTokenHolder();
        }
        return instance;
    }

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        AccessTokenHolder.state = state;
    }

    public static AccessToken getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(AccessToken newAccessToken) {
        accessToken = newAccessToken;
    }
}
