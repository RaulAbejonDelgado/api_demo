# API RESTFULL

    Esta api de ejemplo nos permite realizar las operaciones basicas de un crud en 3 objetos distintos:
    
            *Persons 
            *Familys
            *Comments
## Notas

    Para el mapeo de objetos java a mongo se usa el ODM Morphia 
**[Morphia](https://github.com/MorphiaOrg/morphia/blob/master/Roadmap.md)**

    La base de datos MongoDb
**[MongoDB](https://www.mongodb.com/)**

    Hateoas para enlaces hypermedia 
**[Hateoas](https://spring.io/understanding/HATEOAS)**

    Validaciones
**[Bean Validation API](https://mvnrepository.com/artifact/javax.validation/validation-api/2.0.1.Final)**
#### Usadas para la practica de importacion/ exportacion desde mongoDB a xml y viceversa
    Lectura de objetos java y escritura de archivos xml
**[jackson-dataformat-xml](https://fasterxml.github.io/jackson-dataformat-xml/javadoc/2.7/com/fasterxml/jackson/dataformat/xml/XmlMapper.html)**

    Lectura de archivos xml y mapeo a objetos java
**[jaxb-api](https://mvnrepository.com/artifact/javax.xml.bind/jaxb-api)**
            
## Urls de la api

**Personas**

    [GET] http://localhost:8080/publicaciones/persons
    
**Importar coleccion personas**

        http://localhost:8080/publicaciones/persons/data?action=1
        
**Exportar coleccion personas** 
        
        http://localhost:8080/publicaciones/persons/data?action=2

**Familias** 
       
    http://localhost:8080/publicaciones/familys
    
**Importar coleccion familias**
   
        http://localhost:8080/publicaciones/familys/data?action=1

**Exportar coleccion familias**

        http://localhost:8080/publicaciones/familys/data?action=2
        
**Comentarios** 

    http://localhost:8080/publicaciones/comments
    
**Importar coleccion familias**

        http://localhost:8080/publicaciones/comments/data?action=1
        
**Exportar coleccion familias**

        http://localhost:8080/publicaciones/comments/data?action=2
    


            
