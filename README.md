# API RESTFULL

    Esta api de ejemplo nos permite realizar las operaciones basicas de un crud en 3 objetos distintos:
    
            *Persons 
            *Familys
            *Comments
            
## Tareas
### BackEnd
- [x] Conexion de api con db mongoDB
- [x] Integracion de Morphia ODM
- [x] Validaciones
- [x] Importacion de documentos xml a mongoDB usando la api
- [x] Exportacion de Colecciones mongodb y creacion de archivos xml
- [x] Integracion de Hateoas
- [ ] Documentar con javaDoc
- [ ] Añadir campos al objeto person para registro/ingreso
- [ ] Agregar objeto familias al objeto person
- [ ] Implementar LOGGER
### FrontEnd
- [ ] Cliente angular
    - [ ] Crear Tema para cliente angular
    - [ ] Crear registro
    - [ ] Implementacion de seguridad(Guards)
    - [ ] Crear Estancia usuario 
        - [ ] Lectura de comentarios
        - [ ] Creacion de comentarios           
    - [ ] Crear Zona administracion
        - [ ] Crear Crud
            - [ ] Crud personas
            - [ ] Crud familias
            - [ ] Crud comentarios
    
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

**Index**

    [GET] http://localhost:8080

**Personas**

    [GET] http://localhost:8080/publicaciones/persons
    [GET]http://localhost:8080/publicaciones/persons/{id}
    [DELETE]http://localhost:8080/publicaciones/persons/{id}
    [POST] http://localhost:8080/publicaciones/persons
    objeto esperado:
    
    {
        "nombre": "enhord",
        "familyId":1 
    }
    [PUT] http://localhost:8080/publicaciones/persons/{id}
    objeto esperado:
    
    {
    	"nombre":"drohne",
    	"selfId":9,
    	"familyId":1
    }
    
**Importar coleccion personas**

        http://localhost:8080/publicaciones/persons/data?action=1
        
**Exportar coleccion personas** 
        
        http://localhost:8080/publicaciones/persons/data?action=2

**Familias** 
       
    [GET] http://localhost:8080/publicaciones/familys
    [GET]http://localhost:8080/publicaciones/familys/{id}
    [DELETE]http://localhost:8080/publicaciones/familys/{id}
    [POST] http://localhost:8080/publicaciones/familys
    objeto esperado:
    {
    	"nombre":"familia drohne5",
    	"personas":[
    		{
    			"nombre":"drohne",
    			"selfId":9
    		}
    	]
    }
    [PUT] http://localhost:8080/publicaciones/familys
    objeto esperado:
    {
        "_id": "5c29f97fa4033c513f32bf0f",
        "selfId": 11,
        "nombre": "familia Morphia Modificada",
        "personas": [
            {
                "_id": null,
                "selfId": 12,
                "familyId": 11,
                "nombre": "enhord y mi cipote2",
                "id": null
            }
        ]
    }
    
**Importar coleccion familias**
   
        http://localhost:8080/publicaciones/familys/data?action=1

**Exportar coleccion familias**

        http://localhost:8080/publicaciones/familys/data?action=2
        
**Comentarios** 

    [GET]http://localhost:8080/publicaciones/comments
    [GET]http://localhost:8080/publicaciones/comments/{id}
    [DELETE]http://localhost:8080/publicaciones/comments/{id}
    [POST] http://localhost:8080/publicaciones/comments
    Objeto:Esperado:
    
    {
    "familia": [{
        "_id": "5c23ad0ffae44e51fe620f2b",
        "selfId": 1,
        "nombre": "familia AsierRaul"
       
    }],
    "texto": "asier",
    "persona": [{
        "_id": "5c1a48f34d1fe28adf723f69",
        "selfId": 1,
        "familyId": 1,
        "nombre": "Raul"

    }]
    
    [PUT] http://localhost:8080/publicaciones/comments/{id}
        Objeto:Esperado:
        
        {
        "familia": [{
            "_id": "5c23ad0ffae44e51fe620f2b",
            "selfId": 1,
            "nombre": "familia AsierRaul"
           
        }],
        "texto": "asier",
        "persona": [{
            "_id": "5c1a48f34d1fe28adf723f69",
            "selfId": 1,
            "familyId": 1,
            "nombre": "Raul"
    
        }]
    

    
**Importar coleccion familias**

        http://localhost:8080/publicaciones/comments/data?action=1
        
**Exportar coleccion familias**

        http://localhost:8080/publicaciones/comments/data?action=2
    


            
