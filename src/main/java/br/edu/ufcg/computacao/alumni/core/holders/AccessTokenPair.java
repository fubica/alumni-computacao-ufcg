package br.edu.ufcg.computacao.alumni.core.holders;

public class AccessTokenPair {
    private static AccessTokenPair instance;
    private static String state;
    private static String code;

    private AccessTokenPair() {
        this.state = "";
        this.code = "";
    }

    public static synchronized AccessTokenPair getInstance() {
        if (instance == null) {
            instance = new AccessTokenPair();
        }
        return instance;
    }

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        AccessTokenPair.state = state;
    }

    public static String getCode() {
        return code;
    }

    public static void setCode(String code) {
        AccessTokenPair.code = code;
    }
}
