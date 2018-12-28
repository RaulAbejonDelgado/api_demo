package com.example.demo.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Version;

public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonSerialize(using = NoObjectiIdSerializer.class)
    protected ObjectId _id;

    @Version
    @Property("version")
    private Long version;

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_id == null) ? 0 : _id.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        return result;
    }

}
