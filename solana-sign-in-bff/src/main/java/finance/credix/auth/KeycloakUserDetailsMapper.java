package finance.credix.auth;

//import finance.credix.entity.PublicKey;
//import finance.credix.entity.User;
//import finance.credix.repository.UserRepository;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.rxjava2.http.client.RxHttpClient;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import io.reactivex.Flowable;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Named("keycloak")
@Singleton
class KeycloakUserDetailsMapper implements OauthAuthenticationMapper {

//    @Inject
//    UserRepository userRepository;

    @Property(name = "micronaut.security.oauth2.clients.keycloak.client-id")
    private String clientId;

    @Property(name = "micronaut.security.oauth2.clients.keycloak.client-secret")
    private String clientSecret;

    @Client("${custom.keycloak.url}")
    @Inject
    private RxHttpClient client;

    @Override
    public Publisher<AuthenticationResponse> createAuthenticationResponse(TokenResponse tokenResponse, @Nullable State state) {
        System.out.println(tokenResponse);
        Flowable<HttpResponse<KeycloakUser>> res = client
                .exchange(HttpRequest.POST("/auth/realms/master/protocol/openid-connect/token/introspect",
                        "token=" + tokenResponse.getAccessToken())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .basicAuth(clientId, clientSecret), KeycloakUser.class);
        return res.map(user -> {
            System.out.println(user.body());
            String identity = user.body().getUsername();
//
//            if (userRepository.findByPublicKey(identity).isEmpty()) {
//                PublicKey publicKey = new PublicKey();
//                publicKey.setPublicKey(identity);
//                User _user = new User();
//                publicKey.setUser(_user);
//                _user.setPublicKeys(Collections.singleton(publicKey));
//                userRepository.save(_user);
//            }

            System.out.println(tokenResponse.getAccessToken());
            Map<String, Object> attrs = new HashMap<>();
            attrs.put("openIdToken", tokenResponse.getAccessToken());
            return AuthenticationResponse.success(user.body().getUsername(), attrs);
        });
    }
}
