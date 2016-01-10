package com.sh.service.db_dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


/**
 * Created by ankurgupta.p on 31/12/15.
 */

@Slf4j
public abstract class WorkUnit {

    final void doWork(SessionFactory sessionFactory) {

        Transaction txn = sessionFactory.getCurrentSession().getTransaction();


            txn.begin();
        try {
            actualWork();
            sessionFactory.getCurrentSession().getTransaction().commit();
        } catch (RuntimeException e) {
            log.error("Error while doing db op: " + getMessage(), e);
            sessionFactory.getCurrentSession().getTransaction().rollback();
            throw e;
        } finally {
            sessionFactory.getCurrentSession().close();
        }
    }

    public abstract void actualWork();

    /**
     * Optionally override this method to give the required message
     * that'll get logged in case of error.
     *
     * @return
     */
    public String getMessage() {
        return "";
    }
}
