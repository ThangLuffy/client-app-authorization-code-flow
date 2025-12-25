package com.example.clientapp.controller;

import com.example.clientapp.config.OidcAuthenticationFailureHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login(HttpSession session, Model model) {
        Object error = session.getAttribute(OidcAuthenticationFailureHandler.LOGIN_ERROR_ATTR);
        if (error != null) {
            model.addAttribute("errorMessage", error);
            session.removeAttribute(OidcAuthenticationFailureHandler.LOGIN_ERROR_ATTR);
        }
        return "login";
    }

    @GetMapping("/secured")
    public String loginSuccess(Model model, Authentication authentication) {
        model.addAttribute("name", authentication.getName());
        return "secured";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception {
        request.logout();
        return "redirect:/";
    }
}
