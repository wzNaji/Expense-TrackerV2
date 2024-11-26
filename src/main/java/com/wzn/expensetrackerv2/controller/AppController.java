package com.wzn.expensetrackerv2.controller;

import com.wzn.expensetrackerv2.service.implementation.AppUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping()
@RestController
public class AppController {

    private final AppUserServiceImpl appUserService;

    @Autowired
    public AppController(AppUserServiceImpl appUserService) {
        this.appUserService = appUserService;
    }



}
