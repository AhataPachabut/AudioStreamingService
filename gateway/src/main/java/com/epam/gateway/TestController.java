package com.epam.gateway;

import com.epam.gateway.feign.AuthServiceClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping
public class TestController {

    @Autowired
    private AuthServiceClient authServiceClient;

    @HystrixCommand(fallbackMethod = "reliable")
    @GetMapping("/test")
    public String test() throws IOException {
        return "gateway test";
    }

    public String reliable() {
        return "Cloud Native Java (O'Reilly)";
    }

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/token")
    public String getToken() throws IOException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                    ((OAuth2AuthenticationToken)authentication).getAuthorizedClientRegistrationId(),
                    authentication.getName());

            OAuth2AccessToken accessToken = authorizedClient.getAccessToken();

            if (accessToken != null) {
                return accessToken.getTokenValue();
            }
        }
        else if (authentication instanceof BearerTokenAuthentication) {
            OAuth2AccessToken accessToken = ((BearerTokenAuthentication)authentication).getToken();

            if (accessToken != null) {
                return accessToken.getTokenValue();
            }
        }

        return null;
    }

    @GetMapping("/testAuth")
    public void testAuth() throws IOException {
        String test = authServiceClient.testUser();
        System.out.println(test);

        String test2 = authServiceClient.testUser2("vdsd");
        System.out.println(test2);

    }

}
