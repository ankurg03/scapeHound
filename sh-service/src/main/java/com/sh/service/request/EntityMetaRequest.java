package com.sh.service.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sh.service.util.IngestionType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


/**
 * Created by ankurgupta.p on 11/05/15.
 */

@EqualsAndHashCode
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
@Getter

public class EntityMetaRequest {

    @NotEmpty
    private String ownerName;
    @NotEmpty
    private String ownerEmail;
    @NotEmpty
    private String company;
    @NotEmpty
    private String namespace;
    @NotEmpty
    private String entityName;

    private String entityId;

    @Enumerated(EnumType.STRING)
    private IngestionType ingestionType;

    public EntityMetaRequest(String company, String namespace, String entityName, String ownerName, String ownerEmail, IngestionType ingestionType, String entityId){
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.company = company;
        this.entityName = entityName;
        this.entityId = entityId;
        this.namespace = namespace;
        this.ingestionType = ingestionType;
    }
    public EntityMetaRequest(){

    }

    @Override
    public String toString() {
        return String.format("{\"EntityRequest\"{\"ownerName\":%d" +
                        "\"ownerEmail\":%d" +
                        "\"company\":%d" +
                        "\"entityName\":%d" +
                        "\"entityId\":%d" +
                        "\"namespace\":%d" +
                        "\"ingestionType\":%d}",
                ownerName, ownerEmail, company, entityName, entityId, namespace, ingestionType.name());
    }
}
