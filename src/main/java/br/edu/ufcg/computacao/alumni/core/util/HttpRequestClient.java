package br.edu.ufcg.computacao.alumni.core.util;

import br.edu.ufcg.computacao.alumni.constants.HttpMethod;
import org.apache.log4j.Logger;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpRequestClient {
    private static final Logger LOGGER = Logger.getLogger(HttpRequestClient.class);

    public static HttpResponse doGenericRequest(HttpMethod method, String endpoint, Map<String, String> headers,
                                                Map<String, String> body) throws IOException {
        int responseCode = -1;
        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method.getName());

            addHeadersIntoConnection(connection, headers);

            if (!body.isEmpty()) {
                connection.setDoOutput(true);
                OutputStream os = connection.getOutputStream();
                os.write(toByteArray(body));
                os.flush();
                os.close();
            }

            responseCode = connection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer responseBuffer = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBuffer.append(inputLine);
            }
            in.close();

            Map<String, List<String>> responseHeaders = connection.getHeaderFields();
            return new HttpResponse(responseBuffer.toString(), responseCode, responseHeaders);
        } catch (ProtocolException | MalformedURLException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    private static void addHeadersIntoConnection(HttpURLConnection connection, Map<String, String> headers) {
        for (String key : headers.keySet()) {
            connection.setRequestProperty(key, headers.get(key));
        }
    }

    private static byte[] toByteArray(Map<String, String> body) {
        String json = new Gson().toJson(body, Map.class);
        return json.getBytes();
    }
}
