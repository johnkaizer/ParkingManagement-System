package com.project.parkingmngmtsystem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ParkingController {

    @GetMapping("/")
    public String landing() {
        return "landing";
    }
    @GetMapping("/adminDashboard")
    public String adminDashboard() {
        return "adminDashboard";
    }
    @GetMapping("/customerDashboard")
    public String customerDashboard() {
        return "customerDashboard";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @GetMapping("/fragments/{page}")
    public String loadPage(@PathVariable String page) {
        return "fragments/" + page;
    }
}
