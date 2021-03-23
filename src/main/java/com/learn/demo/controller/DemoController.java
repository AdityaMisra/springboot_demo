package com.learn.demo.controller;


import com.learn.demo.model.DemoModel;
import com.learn.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created on 15/3/21, 9:51 PM
 * DemoController.java
 *
 * @author aditya.misra
 */

@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    DemoService demoService;

    @GetMapping(path = "/")
    public String getDemoMessage() {
        return "Hey there!";
    }

    @GetMapping(path = "/{name}", produces = "application/json")
    public DemoModel getDemo(@PathVariable String name) {
        try {
            return demoService.getSomeDataUsingName(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
