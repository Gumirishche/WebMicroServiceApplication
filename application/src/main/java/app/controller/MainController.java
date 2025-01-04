package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping("/")
    public String login(){
        return "login.html";
    }

    @GetMapping("/home")
    public String home(){
        return "home.html";
    }

    @GetMapping("/order")
    public String order(){
        return "order.html";
    }

    @ExceptionHandler(value = Exception.class)
    public String handleError(Exception e) {
        return "error";
    }
}
