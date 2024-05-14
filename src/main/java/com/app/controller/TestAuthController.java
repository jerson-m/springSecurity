package com.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class TestAuthController {

    @GetMapping("/get")
    public  String helloGet(){
        return "Hello world - GET";
    }

    @GetMapping("/post")
    public  String helloPost(){
        return "Hello world - POST";
    }

    @GetMapping("/put")
    public  String helloPut(){
        return "Hello world - PUT";
    }

    @GetMapping("/delete")
    public  String helloDelete(){
        return "Hello world - DELETE";
    }

    @GetMapping("/patch")
    public  String helloPatch(){
        return "Hello world - PATCH";
    }

}
