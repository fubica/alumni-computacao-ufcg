package br.edu.ufcg.computacao.alumni.api.http.request;

import br.edu.ufcg.computacao.alumni.constants.*;
import br.edu.ufcg.computacao.alumni.core.holders.AccessTokenPair;
import br.edu.ufcg.computacao.alumni.core.holders.PropertiesHolder;
import br.edu.ufcg.computacao.alumni.core.util.HttpRequestClient;
import br.edu.ufcg.computacao.alumni.core.util.HttpResponse;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> callback(@RequestParam("code") String code, @RequestParam("state") String state) throws IOException {
        LOGGER.info(String.format("POST callback: code [%s], state [%s].", code, state));
        AccessTokenPair currentToken = AccessTokenPair.getInstance();
        if (currentToken.getState().equals(state)) {
            String clientId = this.properties.getProperty(ConfigurationPropertyKeys.CLIENT_ID);
            String clientSecret = this.properties.getProperty(ConfigurationPropertyKeys.CLIENT_SECRET);
            String callbackURI = this.properties.getProperty(ConfigurationPropertyKeys.CALLBACK_URI);
            String cmdFormat = "https://www.linkedin.com/oauth/v2/accessToken?grant_type=authorization_code&code=%s&redirect_uri=%s&client_id=%s&client_secret=%s\n";
            String endpoint = String.format(cmdFormat, code, callbackURI, clientId, clientSecret);
            LOGGER.info(String.format("Executing command [%s].", endpoint));
            Map<String, String> headers = new HashMap<>();
            Map<String, String> body = new HashMap<>();
            HttpResponse response = HttpRequestClient.doGenericRequest(HttpMethod.GET, endpoint, headers, body);
            LOGGER.info(String.format(Messages.Info.HTTP_RESPONSE, String.format("%d", response.getHttpCode())));
            LOGGER.info(String.format(Messages.Info.RESETTING_CODE_S, code));
            currentToken.setCode(code);
        } else {
            LOGGER.error(String.format(Messages.Error.INVALID_STATE_S, state));
        }
        return null;
    }
}