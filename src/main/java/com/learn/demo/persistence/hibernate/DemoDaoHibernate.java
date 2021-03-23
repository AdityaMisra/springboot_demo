package com.learn.demo.persistence.hibernate;

import com.learn.demo.model.DemoModel;
import com.learn.demo.persistence.DemoDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


/**
 * Created on 15/3/21, 10:04 PM
 * DemoDaoHibernate.java
 *
 * @author aditya.misra
 */


@Repository("demoDao")
public class DemoDaoHibernate extends GenericDaoHibernate<DemoModel, String> implements DemoDao {
    @Override
    public DemoModel getByName(String name) {
        try {
            Session session = getSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<DemoModel> cq = cb.createQuery(DemoModel.class);

            Root<DemoModel> root = cq.from(DemoModel.class);
            cq.select(root).where(cb.equal(root.get("demoName"), name));

            Query<DemoModel> query = session.createQuery(cq);

            return query.uniqueResult();
        } catch (HibernateException e) {
            return null;
        }
    }

    @Override
    public DemoModel getByUserName(String userName) {
        try {
            Session session = getSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<DemoModel> cq = cb.createQuery(DemoModel.class);

            Root<DemoModel> root = cq.from(DemoModel.class);

            cq.select(root).where(cb.equal(root.get("demoUser"), userName));

            Query<DemoModel> query = session.createQuery(cq);

            return query.uniqueResult();
        } catch (HibernateException e) {
            return null;
        }
    }
}
