package oauth.naver;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.SecureRandom;

@Controller
@RequestMapping("/oauth/naver")
public class naverController {

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.password}")
    private String clientPw;

    @Value("${naver.request.url}")
    private String requestUrl;

    @Value("${naver.request.token.url}")
    private String requestTokenUrl;

    @Value("${naver.request.userInfo}")
    private String requestUserInfo;

    @Value("${naver.redirect.url}")
    private String redirectUrl;

    private final RestTemplate restTemplate = new RestTemplate();



    @RequestMapping(value = "login", method = {RequestMethod.GET})
    public String login(){

        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString();
        String url = requestUrl+"?client_id="+clientId+"&redirect_uri="+redirectUrl+"&state="+state+"&response_type=code";

        return "redirect:" + url;
    }

    @RequestMapping(value = "callback", method = {RequestMethod.GET})
    public void callback(HttpServletRequest request){

        String code = request.getParameter("code");
        String error = request.getParameter("error");
        String state = request.getParameter("state");

        if(error == null){

            String token = getToken(code, state);
            JsonNode userInfo = getUserInfo(token);
            System.out.println(userInfo);
            System.out.println("id :" + userInfo.get("response").get("id").asText());
        }
    }

    public String getToken(String code, String state){

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientPw);
        params.add("redirect_uri", redirectUrl);
        params.add("state", state);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);
        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(requestTokenUrl, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();

        return accessTokenNode.get("access_token").asText();
    }

    public JsonNode getUserInfo(String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(requestUserInfo, HttpMethod.GET, entity, JsonNode.class).getBody();
    }

}
