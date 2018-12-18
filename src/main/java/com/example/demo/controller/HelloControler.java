package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloControler {

    @GetMapping
    public String getHello() {
        return "hello";
    }
}
