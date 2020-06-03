package br.edu.ufcg.computacao.alumni.api.http.response;

import br.edu.ufcg.computacao.alumni.constants.ApiDocumentation;
import io.swagger.annotations.ApiModelProperty;

public class AccessToken {
    @ApiModelProperty(example = ApiDocumentation.Model.ACCESS_TOKEN)
    private String token;
    private long expiresIn;

    public AccessToken(String token, String expiresIn) {
        this.token = token;
        this.expiresIn = Long.parseLong(expiresIn);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
