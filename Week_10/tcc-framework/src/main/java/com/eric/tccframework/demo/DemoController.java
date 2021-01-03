package com.eric.tccframework.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tx")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/confirm")
    public String confirm() {
        try {
            demoService.txSuccess();
            return "confirm success";
        } catch (Exception e) {
            return "confirm failed";
        }
    }

    @GetMapping("/cancel")
    public String cancel() {
        try {
            demoService.txFailed();
            return "cancel success";
        } catch (Exception e) {
            return "cancel failed";
        }
    }
}
