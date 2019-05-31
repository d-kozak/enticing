# Specification of the Index server

Index server manages .mg4j files and uses [mg4j](http://mg4j.di.unimi.it/) to index them and perform queries on them.

# Lifecycle
When started, it loads it's configuration, which is required as an input parameter. The configuration contains the following. 
* format(indexes and entities) which is used in it's input .mg4j files
* \[folder with indexed data which should be used for querying] 
* \[EXTENSION] url of the manager-service (for automatic registration)

If no index folder is provided, the server will respond with error messages to all query, document and snippet requests 
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
       query: string,
       snippetCount: int,
       fromDocument: int,
       wantedElements,
       responseFormat 
    }
    responseFormat = {
        format: "json" | "html"
    }  
    wantedElements = {
       entities,
       fieldAttributes
    }
    entities = predefinedOption | Map<nertag,fieldAttributes>
    nertag = string
    fieldAttributes = predefinedOption | List<string>
    // predefined options, possibility to add more 
    predefinedOption = "all"
    ```
    ```javascript
    responsePayload = {
        snippets: Array<Snippet>,
        lastProcessedDocument:int
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
        text:string
        annotations:Map<annotationId,Annotation>,
        positions:Array<{from:int,to:int,annotationId}>
    }
    ```
* /format
    * GET
        * attributes that can be used in query
    ```javascript
    responsePayload = {
        entities: Map<string,Map<string,string>>,
        attributes: List<string> 
    }
    ```  
* /document
    * whole document
    * POST 
    ```javascript
    requestPayload = {
        documentId: UUID,
        collectionId: string,
        query:string, // if we want to see how the query match the document
        format // as in '/query'    
    }
    ```
    ```javascript
    responsePayload = {
        title: string,
        url: url, // ?? redundant, since we already know it from the snippet, but might be useful, maybe? ??
        text, // as in '/query',
        mapping: Array<QueryMapping>
    }
    
    ```
* /snippet
    * extend snippet
    * POST
    ```javascript
    requestPayload = {
        documentId: UUID,
        collectionId: string,
        position: int, // start position of the snippet which we want to extend
        size: int, // how many extra characters we want,
        format // as in '/query'
    }
    ```
    ```javascript
    responsePayload = {
        before: text // text to insert before, datatype as in '/query',
        after: text // text to insert after, datatype as in '/query'  
    }
    ```
 * /input-directory
    * set new index directory to use for queries 
    * POST
    ```javascript
    requestPayload = {   
        directory: string
    }
    ```
 
 * /actuator/health
    * to check availability
    * part of Spring Boot Actuator, see [documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)
    
