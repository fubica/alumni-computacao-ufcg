package br.edu.ufcg.computacao.alumni.core.holders;

public class AccessTokenHolder {
    private static AccessTokenHolder instance;
    private static String state;
    private static String code;

    private AccessTokenHolder() {
        this.state = "";
        this.code = "";
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

    public static String getCode() {
        return code;
    }

    public static void setCode(String code) {
        AccessTokenHolder.code = code;
    }
}
