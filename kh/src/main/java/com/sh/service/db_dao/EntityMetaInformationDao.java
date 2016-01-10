package com.sh.service.db_dao;
import com.sh.service.db_data.EntityMetaInformation;
import com.sh.service.util.IngestionType;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;

import java.util.*;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * Created by ankurgupta.p on 31/12/15.
 */
@Slf4j
public class EntityMetaInformationDao extends AbstractDAO<EntityMetaInformation>{


    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */

    private SessionFactory sessionFactory;
    BasicFormatterImpl basicFormatter ;

    public EntityMetaInformationDao(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
        basicFormatter = new BasicFormatterImpl();
    }

    /**
     * To Save or update
     * @param comp
     * @param namespace
     * @param entityName
     * @param ownerEmail
     * @param ingestionType
     */
    public void saveOrUpdate(String comp, String namespace, String entityName, IngestionType ingestionType, String ownerName, String ownerEmail){
        EntityMetaInformation result = getEntityOwnerResult(comp, namespace, entityName);
        Session session = null;
        try{
            session = this.sessionFactory.openSession();
            EntityMetaInformation entityOwnerInformation = EntityMetaInformation.of(comp, namespace, entityName, ownerName, ownerEmail, ingestionType );
            entityOwnerInformation.setUpdatedAt(new Date());
            if (result == null)
                session.save(entityOwnerInformation);
            else{
                result.setUpdatedAt(new Date());
                result.setIngestionType(ingestionType);
                result.setOwnerName(ownerName);
                result.setOwnerEmail(ownerEmail);
                session.update(result);
            }
        }catch(Exception e){
            log.error("Some error while saving/updating entityInfo, error was " + e);
            throw new RuntimeException("Some error while saving/updating entityInfo, error was ", e);
        }
        finally {
            if(session.isOpen())
                session.close();
        }

    }


    public List<EntityMetaInformation> getEntityInformationForComp(String comp){
        List<EntityMetaInformation> result = criteria().add(eq("company", comp)).list();
        return result;
    }

    public EntityMetaInformation getEntityOwnerResult(String comp, String namespace, String entityName){
        EntityMetaInformation result = uniqueResult(criteria()
                .add(eq("company", comp))
                .add(eq("namespace", namespace))
                .add(eq("entityName", entityName)));
        return result;
    }


}
