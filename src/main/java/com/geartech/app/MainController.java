package com.geartech.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geartech.app.security.CurrentUser;
import com.geartech.app.security.Permission;
import com.geartech.app.security.Session;

@RestController
@RequestMapping("/app")
public class MainController {

    @GetMapping("/test")
    @Permission("ADMIN2")
    public String load(@Session CurrentUser user) {
        return "TESTE 03";
    }
}
