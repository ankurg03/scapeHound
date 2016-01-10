package com.sh.service.db_dao;

import com.sh.service.health.HealthStatus;
import com.sh.service.health.SimpleHealthStatus;
import org.hibernate.SessionFactory;


import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by ankurgupta.p on 31/12/15.
 */
public class TransactionLender {


    private final SessionFactory factory;

    public TransactionLender(SessionFactory factory) {
        this.factory = factory;
    }

    public void execute(WorkUnit unit) {
        unit.doWork(this.factory);
    }

//    public HealthStatus getHealth() {
//        try {
//            final AtomicReference<Boolean> resultSizeCheck = new AtomicReference<Boolean>();
//            execute(new WorkUnit() {
//                @Override
//                public void actualWork() {
//                    List result = factory.getCurrentSession().createCriteria(SourceType.class).setMaxResults(1).list();
//                    resultSizeCheck.set(result.size() == 1);
//                }
//            });
//            return new SimpleHealthStatus(resultSizeCheck.get(),
//                    (resultSizeCheck.get() ? "": "No ") + "result fetched from DB");
//        } catch (Exception e) {
//            return new SimpleHealthStatus(false, "Exception while getting error: " + e.getMessage());
//        }
//    }
}
