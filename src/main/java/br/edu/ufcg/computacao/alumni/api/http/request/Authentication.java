package br.edu.ufcg.computacao.alumni.api.http.request;

import br.edu.ufcg.computacao.alumni.constants.*;
import br.edu.ufcg.computacao.alumni.core.holders.AccessTokenPair;
import br.edu.ufcg.computacao.alumni.core.holders.PropertiesHolder;
import br.edu.ufcg.computacao.alumni.core.util.HttpRequestClient;
import br.edu.ufcg.computacao.alumni.core.util.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

@CrossOrigin
@RestController
@RequestMapping(value = Authentication.ENDPOINT)
@Api(description = ApiDocumentation.Authentication.API)
public class Authentication {
    protected static final String ENDPOINT = SystemConstants.SERVICE_BASE_ENDPOINT + "authenticate";

    private static final Logger LOGGER = Logger.getLogger(Authentication.class);
    private Properties properties;

    public Authentication() throws Exception {
        this.properties = PropertiesHolder.getInstance().getProperties();
    }

    @ApiOperation(value = ApiDocumentation.Authentication.REQUEST_ACCESS_TOKEN)
    @GetMapping
    public ResponseEntity<Void> authenticate() throws IOException {
        LOGGER.info("GET authenticate");
        String clientId = this.properties.getProperty(ConfigurationPropertyKeys.CLIENT_ID);
        String cmdFormat = "https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=%s&redirect_uri=http://150.165.15.12:8080/alumni/authenticate&state=%s&scope=r_liteprofile%sr_emailaddress%sw_member_social";
        String state = "4567hhgaksskkskskskskAAAA";
        AccessTokenPair.getInstance().setState(state);
        String endpoint = String.format(cmdFormat, clientId, state, "%20", "%20");
        LOGGER.info(String.format("Executing command [%s].", endpoint));
        Map<String, String> headers = new HashMap<>();
        Map<String, String> body = new HashMap<>();
        HttpResponse response = HttpRequestClient.doGenericRequest(HttpMethod.GET, endpoint, headers, body);
        return null;
    }

    @ApiOperation(value = ApiDocumentation.Authentication.RECEIVE_ACCESS_TOKEN)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> callbackPost(@RequestParam("code") String code, @RequestParam("state") String state) throws IOException {
        LOGGER.info(String.format("POST authenticate: code [%s], state [%s].", code, state));
        AccessTokenPair currentToken = AccessTokenPair.getInstance();
        if (currentToken.getState().equals(state)) {
            String clientId = this.properties.getProperty(ConfigurationPropertyKeys.CLIENT_ID);
            String clientSecret = this.properties.getProperty(ConfigurationPropertyKeys.CLIENT_SECRET);
            String cmdFormat = "https://www.linkedin.com/oauth/v2/accessToken?grant_type=authorization_code&code=%s&redirect_uri=http://150.165.15.12:8080/alumni/authenticate&client_id=%s&client_secret=%s\n";
            String endpoint = String.format(cmdFormat, code, clientId, clientSecret);
            LOGGER.info(String.format("Executing command [%s].", endpoint));
            Map<String, String> headers = new HashMap<>();
            Map<String, String> body = new HashMap<>();
            HttpResponse response = HttpRequestClient.doGenericRequest(HttpMethod.GET, endpoint, headers, body);
            LOGGER.info(String.format(Messages.Info.RESSETING_CODE_S, String.format("%d", response.getHttpCode())));
            currentToken.setCode(code);
        } else {
            LOGGER.error(String.format(Messages.Error.INVALID_STATE_S, state));
        }
        return null;
    }
}
