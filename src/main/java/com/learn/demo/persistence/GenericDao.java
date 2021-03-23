package com.learn.demo.persistence;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * Created on 15/3/21, 9:56 PM
 * GenericDao.java
 *
 * @author aditya.misra
 *
 * Generic DAO (Data Access Object) with common methods to CRUD POJOs.
 * Extend this interface if you want typesafe (no casting necessary) DAO's for your
 * domain objects.
 */

public interface GenericDao<T, PK extends Serializable> {
    /**
     * Generic method used to get all objects of a particular communicationType. This
     * is the same as lookup up all rows in a table.
     *
     * @return List of populated objects
     */
    List<T> getAll();


    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T get(PK id);

    /**
     * Checks for existence of an object of communicationType T using the id arg.
     *
     * @param id the id of the entity
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(PK id);

    /**
     * Generic method to saveMedication an object - handles both update and insert.
     *
     * @param object the object to saveMedication
     * @return the persisted object
     */
    T save(T object);

    /**
     * Generic method to update an object - caller must ensure that the passed object is persistent.
     *
     * @param object the object to saveMedication
     */
    void updateObject(T object);

    /**
     * Generic method to delete an object
     *
     * @param object the object to remove
     */
    void remove(T object);

    /**
     * Generic method to delete an object
     *
     * @param id the identifier (primary key) of the object to remove
     */
    void remove(PK id);

    /**
     * execute a raw sql query and return the results along with the meta data
     */
    Map getRawSqlResults(String sql);

    /**
     * execute a create query
     */
    String executeCreate(final String sql) throws SQLException;
}