package br.edu.ufcg.computacao.alumni.api.http.request;

import br.edu.ufcg.computacao.alumni.constants.*;
import br.edu.ufcg.computacao.alumni.core.holders.AccessTokenHolder;
import br.edu.ufcg.computacao.alumni.core.holders.PropertiesHolder;
import br.edu.ufcg.computacao.alumni.core.util.HttpRequestClient;
import br.edu.ufcg.computacao.alumni.core.util.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(value = Authentication.ENDPOINT)
@Api(description = ApiDocumentation.Authentication.API)
public class Authentication {
    protected static final String ENDPOINT = SystemConstants.SERVICE_BASE_ENDPOINT + "authentication";

    private static final Logger LOGGER = Logger.getLogger(Authentication.class);
    private Properties properties;

    public Authentication() throws Exception {
        this.properties = PropertiesHolder.getInstance().getProperties();
    }

    @ApiOperation(value = ApiDocumentation.Authentication.REQUEST_ACCESS_TOKEN)
    @GetMapping(value = "/uri")
    public ResponseEntity<String> uri() throws IOException {
        LOGGER.info("GET URI");
        String clientId = this.properties.getProperty(ConfigurationPropertyKeys.CLIENT_ID);
        String callbackURI = this.properties.getProperty(ConfigurationPropertyKeys.CALLBACK_URI);
        String state = UUID.randomUUID().toString();
        String cmdFormat = "https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=%s&redirect_uri=%s&state=%s&scope=w_member_social";
        AccessTokenHolder.getInstance().setState(state);
        String endpoint = String.format(cmdFormat, clientId, callbackURI, state, "%20", "%20");
        //LOGGER.info(String.format("Executing command [%s].", endpoint));
        //Map<String, String> headers = new HashMap<>();
        //Map<String, String> body = new HashMap<>();
        //HttpResponse response = HttpRequestClient.doGenericRequest(HttpMethod.GET, endpoint, headers, body);
        //LOGGER.info(String.format(Messages.Info.HTTP_RESPONSE, String.format("%d", response.getHttpCode())));
        return new ResponseEntity<>(endpoint, HttpStatus.OK);
    }

    @ApiOperation(value = ApiDocumentation.Authentication.REQUEST_ACCESS_TOKEN)
    @GetMapping(value = "/token")
    public ResponseEntity<String> token() throws IOException {
        LOGGER.info("GET token");
        String token = AccessTokenHolder.getInstance().getAccessToken().getToken();
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
