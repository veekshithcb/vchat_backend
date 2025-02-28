package com.xchat.xchat.email;

import com.xchat.xchat.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

public class OAuthTokenService {


    @Autowired
    private static AppCache appCache;



    private  static String TOKEN_URL = appCache.cache.get("MAIL_SENDER_TOKENURL");
    private static final String CLIENT_ID = appCache.cache.get("MAIL_SENDER_CLIENTID");
    private static final String CLIENT_SECRET = appCache.cache.get("MAIL_SENDER_CLIENTSECRET");

    public static String getOAuthAccessToken() {
        WebClient webClient = WebClient.create();

        Map<String, String> formData = Map.of(
                "grant_type", "client_credentials",
                "client_id", CLIENT_ID,
                "client_secret", CLIENT_SECRET,
                "scope", "https://graph.microsoft.com/.default");

        return webClient.post()
                .uri(TOKEN_URL)
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> response.get("access_token").toString())
                .block();
    }
}
