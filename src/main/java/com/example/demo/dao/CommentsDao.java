package com.example.demo.dao;

import com.example.demo.DataSourceConfiguration;
import com.example.demo.pojo.Comment;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.UnknownHostException;
import java.util.List;

public class CommentsDao {

    private static CommentsDao INSTANCE = null;

    @Autowired
    private Datastore datastore;

    public static synchronized CommentsDao getInstance() throws UnknownHostException {

        if (INSTANCE == null) {

            INSTANCE = new CommentsDao();

        }

        return INSTANCE;
    }

    public CommentsDao() throws UnknownHostException {

        super();
        datastore = DataSourceConfiguration.getConnection();

    }

    public List<Comment> listarTodos() {

        return datastore.createQuery(Comment.class).asList();

    }

    public Comment obtenerPorId(int id)  {

        return datastore.find(Comment.class).field("selfId").equal(id).get();

    }

    public WriteResult delete(Comment c) {

        return datastore.delete(c);

    }

    public Key<Comment> crear(Comment c)  {

        c.setSelfId(listarTodos().size() + 1);

        return datastore.save(c);

    }

    public Key<Comment> modificar(int id, Comment c) {

        Key<Comment> personUpdate = null;
        Comment cOrigin = obtenerPorId(id);

        if (cOrigin != null) {

            c.setId(cOrigin.getId());
            personUpdate = datastore.merge(c);

        }

        return personUpdate;

    }

}
