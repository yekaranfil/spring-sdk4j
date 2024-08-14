package dileksoft.sdk.Security.SecurityService;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class AuthenticationResponse implements Serializable {
    private final String jwt;
    private final String expirationDate;
    private final String AuthorizedUser;


    public AuthenticationResponse(String jwt, String expirationDate, String authorizedUser) {
        this.jwt = jwt;
        this.expirationDate = expirationDate;
        AuthorizedUser = authorizedUser;
    }

    public String getJwt() {
        return jwt;

    }

    public String getExpirationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return expirationDate;
    }

    public String getAuthorizedUser() {
        return AuthorizedUser;
    }
}
