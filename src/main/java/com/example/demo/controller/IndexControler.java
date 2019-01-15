package com.example.demo.controller;

import com.example.demo.pojo.Index;
import org.apache.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Genera y  muestra los principales endpoints de la api.(Hardcodeados)
 * Para ello he creado una clase Index donde agregao una descripcion del endpoint y con hateoas agrego hypermedia
 */
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/API")
public class IndexControler {

    private static ArrayList<Resource<Index>> resources = new ArrayList<>();
    private final static Logger LOG = Logger.getLogger(IndexControler.class);


    public IndexControler() {
        super();
        resources = new ArrayList<>();

    }

    /**
     *
     * @return Resource con objetos Index y enlaces hatoas a los recursos que se ofrecen
     * Tambien devolvemos el estado de respuesta http 200
     */
    @RequestMapping(method = RequestMethod.GET, produces={"application/x-resource+json"})
    public ResponseEntity<Object> resourcesList() {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        LOG.info(("IndexControler"));
        try {
            if (resources.isEmpty()) {

                //creamos los enlaces hateoas
                Link publicacionesLink = linkTo(PersonController.class).withSelfRel();
                Link familiasLink = linkTo(FamilyController.class).withSelfRel();
                Link comentariosLink = linkTo(CommentsController.class).withSelfRel();
                Link batch1Link = linkTo(CustomBatch1Controller.class).withSelfRel();

                //Creanis 3 objetos index
                Index publi = new Index("Personas");
                Index comment = new Index("Comentarios");
                Index familias = new Index("Familias");
                Index batch1 = new Index("Proceso batch");

                //Creamos los recursos existentes en la api
                Resource<Index> pR = new Resource<>(publi);
                Resource<Index> cR = new Resource<>(comment);
                Resource<Index> fR = new Resource<>(familias);
                Resource<Index> b1R = new Resource<>(batch1);

                //Seteamos en el recurso el enlace
                pR.add(publicacionesLink);
                cR.add(comentariosLink);
                fR.add(familiasLink);
                b1R.add(batch1Link);


                //añadimos los recursos con los enlacez hateoas en  el arrayList que devolveremos en la respuesta
                resources.add(pR);
                resources.add(cR);
                resources.add(fR);
                resources.add(b1R);

            }

            response = new ResponseEntity<>(resources, HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
            LOG.error((e.getMessage()));

        }

        return response;

    }
}
