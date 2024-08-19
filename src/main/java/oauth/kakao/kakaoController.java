package oauth.kakao;

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

@Controller
@RequestMapping("/oauth/kakao")
public class kakaoController {

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.request.url}")
    private String requestUrl;

    @Value("${kakao.request.token.url}")
    private String requestTokenUrl;

    @Value("${kakao.request.userInfo}")
    private String requestUserInfo;

    @Value("${kakao.redirect.url}")
    private String redirectUrl;

    private final RestTemplate restTemplate = new RestTemplate();



    @RequestMapping(value = "login", method = {RequestMethod.GET})
    public String login() {

        String url = requestUrl+"?client_id="+clientId+"&redirect_uri="+redirectUrl+"&response_type=code";

        return "redirect:" + url;
    }

    @RequestMapping(value = "callback", method = {RequestMethod.GET})
    public void callback(HttpServletRequest request) {

        String code = request.getParameter("code");
        String error = request.getParameter("error");

        if(error == null){

            String token = getToken(code);
            JsonNode userInfo = getUserInfo(token);
            System.out.println(userInfo);
            System.out.println("id: " + userInfo.get("id").asText());
        }
    }

    public String getToken(String code){

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUrl);
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
