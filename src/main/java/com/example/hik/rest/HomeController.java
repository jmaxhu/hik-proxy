package com.example.hik.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class HomeController {
    @GetMapping("/")
    public void getIndex(HttpServletResponse response) {
        response.setHeader("Location", "index.html");
        response.setStatus(302);
    }
}
