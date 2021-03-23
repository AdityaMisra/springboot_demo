package com.learn.demo.persistence.hibernate;

import com.learn.demo.persistence.GenericDao;
import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.jdbc.Work;
import org.springframework.orm.ObjectRetrievalFailureException;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created on 15/3/21, 9:58 PM
 * GenericDaoHibernate.java
 *
 * @author aditya.misra
 */


public class GenericDaoHibernate<T, PK extends Serializable> implements GenericDao<T, PK> {
    protected static final int DEFAULT_OFFSET = 0;
    protected static final int DEFAULT_LIMIT = 100;

    protected Class<T> persistentClass;

    private static SessionFactory sessionFactory = createSessionFactory();

    public GenericDaoHibernate() {
    }

    public GenericDaoHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /**
     * Constructor that takes in a class and sessionFactory for easy creation of DAO.
     *
     * @param persistentClass the class source you'd like to persist
     * @param sessionFactory  the pre-configured Hibernate SessionFactory
     */
    public GenericDaoHibernate(final Class<T> persistentClass, SessionFactory sessionFactory) {
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();

            Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();

            sessionFactory = metaData.getSessionFactoryBuilder().build();
        }
        return sessionFactory;
    }

    public Session getSession() throws HibernateException {

        Session sess = sessionFactory.getCurrentSession();

        if (sess == null) {
            sess = sessionFactory.openSession();
        }
        return sess;
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        Session sess = getSession();
        return sess.createCriteria(persistentClass).list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T get(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);

        if (entity == null) {
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public boolean exists(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);

        // for some reason hibernate is throwing an exception org.hibernate.ObjectNotFoundException
        // its not supposed to but it is
        // lets handle it here
        T entity = null;
        try {
            entity = (T) byId.load(id);
        } catch (ObjectNotFoundException ex) {
            // ignore it
            // it gets already logged in the hibernate code so don't log it here
        }
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T save(T object) {
        Session sess = getSession();
        return (T) sess.merge(object);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void updateObject(T object) {
        Session sess = getSession();
        sess.update(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(T object) {
        Session sess = getSession();
        sess.delete(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);
        sess.delete(entity);
    }

    @Override
    public Map getRawSqlResults(String sql) {
        final Map<String, Object> resultMap = new HashMap<String, Object>();
        final String finalSql = sql.trim().toLowerCase();
        if (finalSql.startsWith("select")) {
            Session session = getSession();
            session.doWork(new Work() {
                public void execute(Connection connection) throws SQLException {
                    connection.createStatement().executeQuery(finalSql);
                    ResultSet rs = connection.createStatement().executeQuery(finalSql);
                    ResultSetMetaData rsmd = rs.getMetaData();

                    // add the metadata to the map as well
                    resultMap.put("metadata", rsmd);

                    List<List<Object>> data = new ArrayList<List<Object>>();
                    resultMap.put("data", data);

                    int cols = rsmd.getColumnCount();
                    while (rs.next()) {
                        // Get the data from the row using the column index
                        List<Object> row = new ArrayList<Object>();
                        for (int i = 1; i <= cols; i++) {
                            row.add(rs.getObject(i));
                        }
                        data.add(row);
                    }
                }
            });
        }
        return resultMap;
    }

    @Override
    public String executeCreate(String sql) throws SQLException {
        final List<String> l = new ArrayList<>();
        Session session = getSession();
        session.doWork(new Work() {
            public void execute(Connection connection) throws SQLException {
                int result;
                try {
                    result = connection.createStatement().executeUpdate(sql);
                    l.add("" + result);
                } catch (Exception e) {
                    l.add(e.toString());
                }
            }
        });
        return l.get(0);
    }
}

