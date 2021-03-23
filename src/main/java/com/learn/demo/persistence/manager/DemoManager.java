package com.learn.demo.persistence.manager;

import com.learn.demo.model.DemoModel;
import com.learn.demo.persistence.DemoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created on 16/3/21, 8:20 AM
 * DemoManager.java
 *
 * @author aditya.misra
 */


@Service
@Transactional
public class DemoManager {

    @Autowired
    DemoDao demoDao;

    public DemoModel getByName(String name){
        return demoDao.getByName(name);
    }
}
