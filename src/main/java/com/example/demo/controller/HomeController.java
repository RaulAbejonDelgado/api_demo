package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador raiz de la api.
 * el cual nos muestra el contenido que hay en resources/templates doc.html con la documentacion de la api
 */
@Controller
public class HomeController {

    @GetMapping
    public String getHOme() {
        return "doc";
    }
}
