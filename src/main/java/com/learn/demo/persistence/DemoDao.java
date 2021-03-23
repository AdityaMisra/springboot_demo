package com.learn.demo.persistence;


import com.learn.demo.model.DemoModel;

/**
 * Created on 15/3/21, 10:02 PM
 * DemoDaoHibernate.java
 *
 * @author aditya.misra
 */


public interface DemoDao extends GenericDao<DemoModel, String> {

    DemoModel getByName(String name);

    DemoModel getByUserName(String userName);
}
