package com.sh.service.response;

import com.sh.service.request.EntityMetaRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Created by ankurgupta.p on 30/12/15.
 */


@EqualsAndHashCode
public class EntityCreationResponse extends BaseResponse {

    @Getter
    EntityMetaRequest entityMetaReq;
    public EntityCreationResponse(ServiceResponse status, EntityMetaRequest entityMetaReq) {
        super(status);
        this.entityMetaReq = entityMetaReq;
    }
}
