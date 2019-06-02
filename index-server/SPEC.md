# Specification of the Index server

Index server manages indexed .mg4j files and uses [mg4j](http://mg4j.di.unimi.it/) to perform queries on them.

# Lifecycle
When started, it loads it's configuration, which is required as an input parameter. The configuration contains the following. 
* format which is used in it's input .mg4j files
    * names and descriptions of individual indexes
    * names and descriptions of entities
* information about indexed files
    * name of the index (e.g. WIKI 2017b, something that uniquely identifies the indexed data)  
    * the directory where the indexes are stored
*  url of the manager-service (for automatic registration)

If no information about index is provided, the server will respond with error messages to all query, document and snippet requests 
until a new folder is set using the rest api. 

# Rest interface
* all paths prefixed (e.g /api/v1/* )
    * to support multiple APIs simultaneously (v1,v2,v3), if necessary
    * to add GUI at '/', if deemed useful in the future

* /query
     * POST
        * perform search query, return list of snippets
    ```javascript
    requestPayload = {
       query: string, // EQL query
       snippetCount: int, // how many snippets to return
       offset: int, // for pagination, offset at which to start searching
       wantedIndexes, // which indexes to include in the rensponse
     
       ?? still not convinced about supporting different response types than json ??
       ?? assuming two clients ,web frontend and tui, both of them will actually have different visualization ??
       ?? so I would leave the json to html/text conversion to them ??
       responseFormat // what format should the result have
    }
    responseFormat = {
        format: "json" | "html"
    }  
    wantedIndexes = {
       entities,
       fieldAttributes
    }
    entities = predefinedOption | Map<nertag,fieldAttributes>
    nertag = string
    fieldAttributes = predefinedOption | List<string> 
    predefinedOption = "all" // simple way to say all
    ```
    ```javascript
    responsePayload = {
        snippets: Array<Snippet>,
        offset:int // for pagination
    }
    Snippet = {
        documentId: UUID,
        collectionId: string,
        position: int, // where in the document the snippet starts,
        url: url, // url location of the original document,
        canExtend: boolean, // is it possible to further extend the snippet?
        text
    } 
    text = html | json
    json = {
        text : EnhancedText,
        mapping : Array<QueryMapping>
    }
    QueryMapping = {
        textIndex: [int,int],
        queryIndex: [int,int]
    }
    EnhancedText = {  
        text:string,
        annotations:Map<annotationId,Annotation>,
        positions:Array<{from:int,to:int,annotationId}>
    }
    ```
* /format
    * GET
        * attributes that can be used in query
    ```javascript
    responsePayload = {
        entities: Map<nertag,Map<atttribute,description>>, // entities and their attributes
        indexes: Map<index,description>  // all indexes that can be queried
    }
    nertag = atttribute = description = index = string
    ```  
* /document
    * whole document
    * POST 
    ```javascript
    requestPayload = {
        documentId: UUID,
        collectionId: string,
        query:string?, // optional, if we want to see how the query match the document
        wantedIndexes // as in '/query'  
    }
    ```
    ```javascript
    responsePayload = {
        title: string,
        url: url, // url of the source
        text, // as in '/query',
        mapping: Array<QueryMapping>? // optional, only used if query was specified
    }
    
    ```
* /snippet
    * extend snippet
    * POST
    ```javascript
    requestPayload = {
        documentId: UUID,
        collectionId: string,
        snippetStart: int, // where current snippet starts
        snippetSize: int // size of current spippets
        size: int, // how many extra characters are wanted
        wantedIndexes // as in '/query'
    }
    ```
    ```javascript
    responsePayload = {
        before: text // text to insert before, datatype as in '/query',
        after: text // text to insert after, datatype as in '/query'  
    }
    ```
 * /index
    * set new index directory to use for queries 
    * POST
    ```javascript
    requestPayload = {   
        directory: string, // which directory the query
        indexName: string // what index the data belong to
    }
    ```
 
 * /actuator/health
    * to check availability
    * part of Spring Boot Actuator, see [documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)
    
