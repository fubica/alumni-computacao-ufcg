package br.edu.ufcg.computacao.alumni.api.http.request;

import br.edu.ufcg.computacao.alumni.constants.*;
import br.edu.ufcg.computacao.alumni.core.holders.AccessTokenHolder;
import br.edu.ufcg.computacao.alumni.core.util.HttpRequestClient;
import br.edu.ufcg.computacao.alumni.core.util.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = Me.ENDPOINT)
@Api(description = ApiDocumentation.Authentication.API)
public class Me {
    protected static final String ENDPOINT = SystemConstants.SERVICE_BASE_ENDPOINT + "admin";

    private static final Logger LOGGER = Logger.getLogger(Me.class);

    private static final String TARGET = "https://api.linkedin.com/v2/me";

    public Me() throws Exception {
    }

    @ApiOperation(value = ApiDocumentation.Authentication.REQUEST_ACCESS_TOKEN)
    @GetMapping
    public ResponseEntity<String> authenticate() throws Exception {
        LOGGER.info("GET admin");
        String token = AccessTokenHolder.getInstance().getAccessToken().getToken();
        Map<String, String> headers = getHeaders(token);
        Map<String, String> body = new HashMap<>();
        HttpResponse response = HttpRequestClient.doGenericRequest(HttpMethod.GET, TARGET, headers, body);
        LOGGER.info(String.format(Messages.Info.HTTP_RESPONSE, String.format("%d", response.getHttpCode())));
        if (response.getHttpCode() == 200) {
            return new ResponseEntity<>(response.getContent(), HttpStatus.OK);
        } else {
            throw new Exception(String.format(Messages.Exception.OPERATION_FAILED_S, response.getHttpCode()));
        }
    }

    private Map<String, String> getHeaders(String token) {
        Map<String, String> headers = new HashMap<>();
        String authorizationHeader = String.format("Bearer %s", token);
        headers.put("Authorization", authorizationHeader);
        headers.put("Host", TARGET);
        headers.put("Connection", "keep-alive");
        headers.put("Cache-Control", "no-cache");
        return headers;
    }
}
