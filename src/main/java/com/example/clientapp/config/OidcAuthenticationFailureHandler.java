package com.example.clientapp.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

//TODO xem xét lại xem có nên giữ không?
@Component
public class OidcAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final Logger log = LoggerFactory.getLogger(OidcAuthenticationFailureHandler.class);
    public static final String LOGIN_ERROR_ATTR = "login_error";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        log.warn("OIDC login failed: '{}'", exception.getMessage(), exception);

        request.getSession().setAttribute(
                LOGIN_ERROR_ATTR,
                resolveMessage(exception)
        );

        getRedirectStrategy().sendRedirect(request, response, "/login");
    }

    private String resolveMessage(AuthenticationException ex) {
        if (ex instanceof OAuth2AuthenticationException oauthEx) {
            return oauthEx.getError().getDescription();
        }
        return "Đăng nhập không thành công";
    }
}
