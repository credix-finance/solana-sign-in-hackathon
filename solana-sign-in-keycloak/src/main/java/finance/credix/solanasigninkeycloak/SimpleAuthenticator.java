package finance.credix.solanasigninkeycloak;

import io.github.novacrypto.base58.Base58;
import org.jboss.logging.Logger;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.OAuth2ErrorRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import software.pando.crypto.nacl.Crypto;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SimpleAuthenticator implements Authenticator {

    private final Keycloak keycloak = KeycloakBuilder.builder()
            .serverUrl("http://127.0.0.1:8080/auth")
            .realm("master")
            .clientId("admin-cli")
            .username("admin")
            .password("admin")
            .build();

    private static final Logger LOG = Logger.getLogger(SimpleAuthenticator.class);

    public static Response errorResponse(int status, String error, String errorDescription) {
        OAuth2ErrorRepresentation errorRep = new OAuth2ErrorRepresentation(error, errorDescription);
        return Response.status(status).entity(errorRep).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    public SimpleAuthenticator(KeycloakSession session) {
        // configure from session
    }

    private boolean validSignature(String address, String signature) {
        try {
            return Crypto.signVerify(
                    Crypto.signingPublicKey(Base58.base58Decode(address)),
                    address.getBytes(StandardCharsets.UTF_8),
                    Base58.base58Decode(signature));
        } catch (Exception e){
            LOG.infof(e.toString());
            return false;
        }
    }

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        LOG.infof(context.getHttpRequest().getFormParameters().toString());

        String address = context.getHttpRequest().getFormParameters().getFirst("username");
        String signature = context.getHttpRequest().getFormParameters().getFirst("password");

        LOG.infof("Received address %s and signature %s", address, signature);

        if (address.equals("admin") && signature.equals("admin")) {
            context.success();
            return;
        }

        if (validSignature(address, signature)) {
            // create user in keycloak
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue("redundant");

            UserRepresentation user = new UserRepresentation();
            user.setUsername(address);
            user.setCredentials(Arrays.asList(credential));
            user.setEnabled(true);
            user.setRealmRoles(Arrays.asList("admin"));

            keycloak.realm("master").users().create(user);

            // set success
            context.success();
        } else {
            LOG.infof("Invalid signature");
            Response challengeResponse = errorResponse(Response.Status.UNAUTHORIZED.getStatusCode(), "INVALID_SIGNATURE", "Invalid signature");
            context.failure(AuthenticationFlowError.INVALID_CREDENTIALS, challengeResponse);
        }
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
    }

    @Override
    public void action(AuthenticationFlowContext context) {
    }

    @Override
    public void close() {
        // NOOP
    }
}
