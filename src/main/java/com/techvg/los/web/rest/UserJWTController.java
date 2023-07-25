package com.techvg.los.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techvg.los.captcha.CaptchaValidator;
import com.techvg.los.security.jwt.JWTFilter;
import com.techvg.los.security.jwt.PasswordDecryption;
import com.techvg.los.security.jwt.TokenProvider;
import com.techvg.los.web.rest.vm.LoginVM;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    CaptchaValidator captchaValidator;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );

        Authentication authentication = null;
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Exception("Failed to sign in! You have entered an invalid username or password"));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        this.tokenProvider.myHash.put(loginVM.getUsername(), jwt);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    //--------------------------------------------------------------------------

    @PostMapping("/authenticateWeb")
    public ResponseEntity<?> authorizeWeb(@Valid @RequestBody LoginVM loginVM) {
        if (loginVM.getCaptacha() != null) {
            if (!captchaValidator.isValidCaptcha(loginVM.getCaptacha())) {
                throw new UsernameNotFoundException("Invalid captcha");
            }
        }
        PasswordDecryption passDecryption = new PasswordDecryption();
        String decryptPassword;

        try {
            decryptPassword = passDecryption.decrypt(loginVM);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            decryptPassword
        );

        Authentication authentication = null;
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Exception("Failed to sign in! You have entered an invalid username or password"));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        this.tokenProvider.myHash.put(loginVM.getUsername(), jwt);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    //------------------------------------------------------------------

    @PostMapping("/authenticateBackend")
    public ResponseEntity<?> authorizeBackend(@Valid @RequestBody LoginVM loginVM) {
        if (loginVM.getCaptacha() != null) {
            if (!captchaValidator.isValidCaptcha(loginVM.getCaptacha())) {
                throw new UsernameNotFoundException("Invalid captcha");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );

        Authentication authentication = null;
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Exception("Failed to sign in! You have entered an invalid username or password"));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        this.tokenProvider.myHash.put(loginVM.getUsername(), jwt);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    //-------------------------------------------------------------------

    @PostMapping("/authenticateWithCaptcha")
    public ResponseEntity<?> authorizeWithCaptcha(@Valid @RequestBody LoginVM loginVM) {
        if (captchaValidator.isValidCaptcha(loginVM.getCaptacha())) {
            PasswordDecryption passDecryption = new PasswordDecryption();
            String decryptPassword;

            try {
                decryptPassword = passDecryption.decrypt(loginVM);
            } catch (Exception e) {
                return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginVM.getUsername(),
                decryptPassword
            );

            Authentication authentication = null;
            try {
                authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            } catch (Exception e) {
                return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new Exception("Failed to sign in! You have entered an invalid username or password"));
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
            this.tokenProvider.myHash.put(loginVM.getUsername(), jwt);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
            return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("Invalid captcha");
        }
    }

    //------------------------------------------------------------------------------
    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Authentication> fetchSignoutSite(
        HttpServletRequest request,
        HttpServletResponse response,
        @Valid @RequestBody LoginVM loginVM
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            try {
                if (loginVM.getUsername().length() > 0 && tokenProvider.myHash.containsKey(loginVM.getUsername())) {
                    tokenProvider.myHash.remove(loginVM.getUsername());
                } else {
                    throw new UsernameNotFoundException("Invalid UserName");
                }
            } catch (Exception e) {
                throw new UsernameNotFoundException("Invalid UserName");
            }

            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ResponseEntity<>(null, null, HttpStatus.OK);
    }
}
