package br.edu.ufcg.computacao.alumni.api.http.request;

import br.edu.ufcg.computacao.alumni.api.http.response.AccessToken;
import br.edu.ufcg.computacao.alumni.constants.*;
import br.edu.ufcg.computacao.alumni.core.holders.AccessTokenHolder;
import br.edu.ufcg.computacao.alumni.core.holders.PropertiesHolder;
import br.edu.ufcg.computacao.alumni.core.util.HttpRequestClient;
import br.edu.ufcg.computacao.alumni.core.util.HttpResponse;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping(value = Callback.ENDPOINT)
@Api(description = ApiDocumentation.Authentication.API)
public class Callback {
    protected static final String ENDPOINT = SystemConstants.SERVICE_BASE_ENDPOINT + "callback";

    private static final Logger LOGGER = Logger.getLogger(Callback.class);
    private Properties properties;

    public Callback() throws Exception {
        this.properties = PropertiesHolder.getInstance().getProperties();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Void> callback(@RequestParam("code") String code, @RequestParam("state") String state) throws IOException {
        LOGGER.info(String.format("GET callback: code [%s], state [%s].", code, state));
        AccessTokenHolder currentToken = AccessTokenHolder.getInstance();
        if (currentToken.getState().equals(state)) {
            String clientId = this.properties.getProperty(ConfigurationPropertyKeys.CLIENT_ID);
            String clientSecret = this.properties.getProperty(ConfigurationPropertyKeys.CLIENT_SECRET);
            String callbackURI = this.properties.getProperty(ConfigurationPropertyKeys.CALLBACK_URI);
            String cmdFormat = "https://www.linkedin.com/oauth/v2/accessToken?grant_type=authorization_code&code=%s&redirect_uri=%s&client_id=%s&client_secret=%s\n";
            String endpoint = String.format(cmdFormat, code, callbackURI, clientId, clientSecret);
            LOGGER.info(String.format("Executing command [%s].", endpoint));
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "x-www-form-urlencoded");
            Map<String, String> body = new HashMap<>();
            HttpResponse response = HttpRequestClient.doGenericRequest(HttpMethod.GET, endpoint, headers, body);
            LOGGER.info(String.format(Messages.Info.HTTP_RESPONSE, String.format("%d", response.getHttpCode())));
            LOGGER.info(String.format("Content:[%s]", response.getContent()));
            String token = new Gson().fromJson("access_token", String.class);
            String expiresIn = new Gson().fromJson("expires_in", String.class);
            LOGGER.info(String.format("Token:[%s]", token));
            LOGGER.info(String.format("ExpiresIn:[%s]", expiresIn));
            AccessToken accessToken = new AccessToken(token, expiresIn);
            currentToken.setAccessToken(accessToken);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
