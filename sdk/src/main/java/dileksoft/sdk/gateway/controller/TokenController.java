package dileksoft.sdk.gateway.controller;


import dileksoft.sdk.Security.SecurityService.AuthenticationRequest;
import dileksoft.sdk.Security.SecurityService.AuthenticationResponse;
import dileksoft.sdk.Security.SecurityService.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping("/auth")
public class TokenController implements Serializable {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtTokenUtil;

    public TokenController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Username or Password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        // Expiration tarihini milisaniye cinsinden al
        final long expirationDateMillis = jwtTokenUtil.extractExpirationMillis(jwt); // Expiration'ı milisaniye olarak al

        // AuthenticationResponse oluşturma
        AuthenticationResponse authResponse = new AuthenticationResponse();
        AuthenticationResponse.Data data = new AuthenticationResponse.Data();
        data.setToken(jwt);
        data.setExpiresAt(expirationDateMillis); // Expiration tarihini milisaniye cinsinden ekle
        authResponse.setData(data);
        authResponse.setStatus(200);  // Başarılı durum kodu
        authResponse.setProperties(null);  // Properties şu an için null

        return ResponseEntity.ok(authResponse);
    }
}