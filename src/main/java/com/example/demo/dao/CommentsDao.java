package com.example.demo.dao;

import com.example.demo.DataSourceConfiguration;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Family;
import com.example.demo.pojo.Person;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.UnknownHostException;
import java.util.ArrayList;
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

    /**
     *
     * @return List<Comment>
     */
    public List<Comment> listarTodos() {

        return datastore.createQuery(Comment.class).asList();

    }

    /**
     *
     * @param id referencia a la propiedad selfId del objeto Comment
     * @return Objeto tipo Comment
     */
    public Comment obtenerPorId(int id) {

        return datastore.find(Comment.class).field("selfId").equal(id).get();

    }

    /**
     *
     * @param p objeto tipo Persona
     * @return List<Comment>
     */
    public List<Comment> obtenerByUser(Person p) {

        //List<Comment> comentarios =  datastore.find(Comment.class).field("persona.selfId").equal(p.getselfId()).asList();

        List<Comment> comentariosRetorno = new ArrayList<>();
        ArrayList<Comment> comentarios = (ArrayList<Comment>) datastore.createQuery(Comment.class).asList();
        for (Comment c : comentarios) {

            for (Person pe : c.getPersona()) {
                if (pe.getselfId() == p.getselfId()) {
                    comentariosRetorno.add(c);
                }
            }

        }

        return comentariosRetorno;
    }

    /**
     *
     * @param c Objeto tipo Comentario
     * @return WriteResult
     */
    public WriteResult delete(Comment c) {

        return datastore.delete(c);

    }

    /**
     *
     * @param c Objeto tipo Comentario
     * @return Key<Comment>
     */
    public Key<Comment> crear(Comment c) {

        c.setSelfId(listarTodos().size() + 1);

        return datastore.save(c);

    }

    /**
     *
     * @param comentarios tipo ArrayList<Comment>
     * @return Iterable<Key<Comment>>
     */
    public Iterable<Key<Comment>> crearPorLote(ArrayList<Comment> comentarios) {

        return datastore.save(comentarios);

    }

    /**
     *
     * @param id referencia a la propiedad selfId del objeto Comment
     * @param c Objeto tipo Comment
     * @return Key<Comment>
     */
    public Key<Comment> modificar(int id, Comment c) {

        Key<Comment> personUpdate = null;
        Comment cOrigin = obtenerPorId(id);
        //c.setSelfId(listarTodos().size()+1);

        if (cOrigin != null) {

            c.setId(cOrigin.getId());
            c.setSelfId(cOrigin.getSelfId());
            personUpdate = datastore.merge(c);

        }

        return personUpdate;

    }

    /**
     *
     * @param f Objeto tipo Family
     * @return List<Comment>
     */
    public List<Comment> obtenerByFamily(Family f) {

        List<Comment> comentariosRetorno = new ArrayList<>();
        ArrayList<Comment> comentarios = (ArrayList<Comment>) datastore.createQuery(Comment.class).asList();
        for (Comment c : comentarios) {

            for (Family fe : c.getFamilia()) {
                if (fe.getSelfId() == f.getSelfId()) {
                    comentariosRetorno.add(c);
                }
            }

        }

        return comentariosRetorno;
    }

}
