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
        String callbackURI = this.properties.getProperty(ConfigurationPropertyKeys.CALLBACK_URI);
        String cmdFormat = "https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=%s&redirect_uri=%s&state=%s&scope=r_liteprofile%sr_emailaddress%sw_member_social";
        String state = "4567hhgaksskkskskskskAAAA";
        AccessTokenPair.getInstance().setState(state);
        String endpoint = String.format(cmdFormat, callbackURI, clientId, state, "%20", "%20");
        LOGGER.info(String.format("Executing command [%s].", endpoint));
        Map<String, String> headers = new HashMap<>();
        Map<String, String> body = new HashMap<>();
        HttpResponse response = HttpRequestClient.doGenericRequest(HttpMethod.GET, endpoint, headers, body);
        LOGGER.info(String.format(Messages.Info.HTTP_RESPONSE, String.format("%d", response.getHttpCode())));
        return null;
    }
}
