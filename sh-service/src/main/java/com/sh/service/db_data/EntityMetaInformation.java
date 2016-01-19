package com.sh.service.db_data;

import com.sh.service.util.IngestionType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ankurgupta.p on 28/12/15.
 */

@Data
@Entity(name = "entity_meta_info")
public class EntityMetaInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Setter(AccessLevel.PACKAGE)
    private Long entityId;

    @javax.persistence.Column(name = "ownerName", nullable = false)
    private String ownerName;

    @javax.persistence.Column(name = "ownerEmail", nullable = false)
    private String ownerEmail;

    @javax.persistence.Column(name = "company", nullable = false)
    private String company;

    @javax.persistence.Column(name = "namespace", nullable = false)
    private String namespace;

    @javax.persistence.Column(name = "entityName", nullable = false)
    private String entityName;

    @javax.persistence.Column(name = "ingestionType", nullable = false)
    @Enumerated(EnumType.STRING)
    private IngestionType ingestionType;

    @javax.persistence.Column(name = "updatedAt", nullable = false)
    private Date updatedAt;

    public static EntityMetaInformation of(
            String company, String namespace, String entityName,
            String ownerName, String ownerEmail, IngestionType ingestionType) {
        EntityMetaInformation info = new EntityMetaInformation();
        info.ownerName = ownerName;
        info.namespace = namespace;
        info.company = company;
        info.ownerEmail = ownerEmail;
        info.ingestionType = ingestionType;
        info.entityName = entityName;
        return info;
    }
}