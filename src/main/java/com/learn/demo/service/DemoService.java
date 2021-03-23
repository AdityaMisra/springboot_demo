package com.learn.demo.service;

import com.learn.demo.model.DemoModel;
import com.learn.demo.persistence.manager.DemoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 15/3/21, 9:53 PM
 * DemoService.java
 *
 * @author aditya.misra
 */


@Service
public class DemoService {

    @Autowired
    DemoManager demoManager;

    public DemoModel getSomeDataUsingName(String name) {
        return demoManager.getByName(name);
    }

}
