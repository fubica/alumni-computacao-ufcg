package br.edu.ufcg.computacao.alumni.core.util;

import java.util.List;
import java.util.Map;

public class HttpResponse {
    private final Map<String, List<String>> headers;
    private int httpCode;

    public HttpResponse(String content, int httpCode, Map<String, List<String>> headers) {
        this.httpCode = httpCode;
        this.headers = headers;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }
}
