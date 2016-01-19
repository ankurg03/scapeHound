package com.sh.service;

import com.sh.service.config.EntityServiceConfig;
import com.sh.service.db_dao.DbConnection;
import com.sh.service.db_dao.EntityMetaInformationDao;
import com.sh.service.db_dao.TransactionLender;
import com.sh.service.db_dao.WorkUnit;
import com.sh.service.db_data.EntityMetaInformation;
import com.sh.service.request.EntityMetaRequest;
import com.sh.service.response.EntityCreationResponse;
import com.sh.service.response.ServiceResponse;
import com.sh.service.util.IngestionType;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankurgupta.p on 30/12/15.
 */

@Path("/entity")
@Slf4j
@Produces(MediaType.APPLICATION_JSON)
public class EntityResource {

    private TransactionLender txnLender;
    EntityMetaInformationDao entityMetaInformationDao;
    public EntityResource(EntityServiceConfig configuration) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        SessionFactory sessionFactory = new DbConnection(configuration).getSessionFactory();
        this.txnLender = new TransactionLender(sessionFactory);
        entityMetaInformationDao = new EntityMetaInformationDao(sessionFactory);
    }

    @GET
    public String testApi(){
        return "FINE";
    }

    @POST
    @Path("/createEntity")
    public EntityCreationResponse createEntity(@Valid EntityMetaRequest entityMetaRequest) {
        final String company = entityMetaRequest.getCompany();
        final String namespace = entityMetaRequest.getNamespace();
        final String entityName = entityMetaRequest.getEntityName();
        final String ownerName = entityMetaRequest.getOwnerName();
        final String ownerEmail = entityMetaRequest.getOwnerEmail();
        final IngestionType ingestionType = entityMetaRequest.getIngestionType();
        doInTransaction(new WorkUnit() {
            @Override
            public void actualWork() {
                entityMetaInformationDao
                        .saveOrUpdate(company, namespace, entityName,
                                ingestionType, ownerName, ownerEmail);
            }

            @Override
            public String getMessage() {
                return "creating Entity";
            }
        });
        return new EntityCreationResponse(ServiceResponse.SUCCESS, entityMetaRequest);
    }

    @GET
    @Path("/getEntity")
    public List<EntityMetaInformation> getEntityMetaInformation(@QueryParam("comp") final String comp){
        final List<EntityMetaInformation> lst = new ArrayList<EntityMetaInformation>();
        doInTransaction(new WorkUnit() {
            @Override
            public void actualWork() {
                List<EntityMetaInformation> list = entityMetaInformationDao.getEntityInformationForComp(comp);
                for(EntityMetaInformation entityMetaInformation : list)
                    lst.add(entityMetaInformation);
            }

            @Override
            public String getMessage() {
                return "getting Entity information";
            }
        });
        return lst;
    }

    private void doInTransaction(WorkUnit work) {
        this.txnLender.execute(work);
    }

}
