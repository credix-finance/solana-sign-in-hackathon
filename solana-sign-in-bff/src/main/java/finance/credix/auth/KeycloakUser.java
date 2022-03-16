package finance.credix.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Introspected
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KeycloakUser {
    private String email;
    private String username;
    private List<String> roles;
}
