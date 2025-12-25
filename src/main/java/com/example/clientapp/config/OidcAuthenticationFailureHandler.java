package com.example.clientapp.config;

import com.example.clientapp.controller.UserController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OidcAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    public static final String LOGIN_ERROR_ATTR = "login_error";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        log.warn("OIDC login failed: {}", exception.getMessage(), exception);

        // Message chung cho UI (KHÔNG lộ chi tiết)
        request.getSession().setAttribute(
                LOGIN_ERROR_ATTR,
                "Đăng nhập không thành công. Vui lòng thử lại."
        );

        response.sendRedirect("/login-error");
    }
}
