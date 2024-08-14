package dileksoft.sdk.Security.SecurityService;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
    private String username;
    private String password;

    @JsonCreator
    public AuthenticationRequest(@JsonProperty("username") String username,
                                 @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    // Getter ve Setter metodları
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}