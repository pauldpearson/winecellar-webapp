package info.mywinecellar.api;

import info.mywinecellar.json.MyWineCellar;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides setup for all ITCase's
 */
public class BaseITCase {

    private final RestTemplate client = new RestTemplate();
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected MyWineCellar myWineCellar;

    /**
     * Setup our MyWineCellar response object, used for testing specific nested objects
     *
     * @param json The ResponseEntity json
     * @return Our MyWineCellar json envelope object
     */
    protected MyWineCellar setupResponseObject(ResponseEntity<String> json) {
        try {
            myWineCellar = objectMapper.readValue(json.getBody(), MyWineCellar.class);
        } catch (JsonProcessingException ignored) {
        }
        return myWineCellar;
    }

    /**
     * Request to our API for any/all requests
     *
     * @param path       The path of the request past /api
     * @param body       The body of the request, can be null
     * @param httpMethod GET, PUT, POST, DELETE
     * @return The response
     */
    protected ResponseEntity<String> apiRequest(String path, String body, HttpMethod httpMethod) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        return client.exchange("http://localhost:8080/api" + path,
                httpMethod, httpEntity, String.class);
    }

    /**
     * Request for any image/multipart form data requests
     *
     * @param path The path of the request
     * @return The response
     */
    protected ResponseEntity<String> apiImageRequest(String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        var content = new FileSystemResource("src/test/resources/opus_one.jpg");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", content);

        HttpEntity<MultiValueMap<String, Object>> httpEntity =
                new HttpEntity<>(body, headers);
        return client.exchange("http://localhost:8080/api" + path, HttpMethod.PUT, httpEntity, String.class);
    }

    /**
     * JSON body used to test entities that can only edit description and weblink
     *
     * @return JSONObject.toString
     */
    protected String jsonBody() {
        JSONObject json = new JSONObject();
        try {
            json.put("description", "edited description");
            json.put("weblink", "edited weblink");
        } catch (JSONException ignore) {
        }
        return json.toString();
    }

}
