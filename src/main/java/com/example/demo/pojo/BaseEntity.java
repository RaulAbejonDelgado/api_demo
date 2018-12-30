package com.example.demo.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public abstract class BaseEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @JsonSerialize(using = NoObjectiIdSerializer.class)
    protected ObjectId _id;

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_id == null) ? 0 : _id.hashCode());
        return result;
    }

}
