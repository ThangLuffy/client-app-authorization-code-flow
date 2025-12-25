package com.example.clientapp.controller;

import com.example.clientapp.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Objects;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private WebClient webClient;

    @GetMapping("/users")
    public String findUsers(Model model) {
        Users[] users = webClient.get()
                .uri("http://localhost:8086/api/users")
                .retrieve()
                .bodyToMono(Users[].class)
                .block();

        log.info("List users from resource server '{}'", Objects.isNull(users) ? null : Arrays.toString(users));
        model.addAttribute("users", users != null ? users : new Users[]{});
        return "users";
    }
}
