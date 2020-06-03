package br.edu.ufcg.computacao.alumni.core.util;

import java.util.List;
import java.util.Map;

public class HttpResponse {
    private String content;
    private final Map<String, List<String>> headers;
    private int httpCode;

    public HttpResponse(String content, int httpCode, Map<String, List<String>> headers) {
        this.content = content;
        this.httpCode = httpCode;
        this.headers = headers;
    }

    public String getContent() {
        return content;
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
