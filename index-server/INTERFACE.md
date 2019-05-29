# Rest interface of the Index server

* all paths prefixed (e.g /api/v1/* )
    * to support multiple APIs simultaneously (v1,v2,v3), if necessary
    * to add GUI at '/', if deemed useful in the future

* /query
     * POST
        * perform search query, return list of snippets
    ```
    requestPayload = {
       query: string,
       snippetCount: int,
       format
    }
    format = formatDefinition | formatId
    formatDefinition = {
       entities,
       attributes
    }
    entities = string | Map<string,Map<string,string>>
    attributes = string | List<string>
    // string for predefined option (e.g. "all")
    // Map | List for exact definition
    ```
    ```
    responsePayload = {
        snippets: Array<Snippet>
    }
    Snippet = {
        documentId: UUID,
        collectionId: string,
        position: int // where in the document the snippet starts,
        url: url // url location of the original document,
        canExtend: boolean // is it possible to further extend the snippet?
        text : EnhancedText,
        mapping : Array<QueryMapping>
    } 
    QueryMapping = {
        from: int,
        to: int
        queryPart:string
    }
    EnhancedText = {
        ??
        text:string
        annotations:Map<int,Annotation>
        positions:Array<{from:int,to:int,annotationId:int}>
        ??
        OR
        ??
        Array<Map<string,string>>
        ??
    }
    ```
* /format
    * GET
        * attributes that can be used in query
    ```
    responsePayload = {
        entities: Map<string,Map<string,string>>,
        attributes: List<string> 
    }
    ```  
    * POST
       * save format to be used for subsequent search requests
       * requestPayload the same as formatDefinition from '/query'
    ```
    responsePayload = {
        id: formatId // id to identify the settings
    }
    ```
* /document
    * whole document
    * POST 
    ```
    requestPayload = {
        documentId: UUID,
        collectionId: string,
        format // as in '/query'    
    }
    ```
    ```
    responsePayload = {
        title: string,
        url: url // ?? redundant, since we already know it from the snippet, but might be useful
        text: enhancedText // as in '/query',
    }
    
    ```
* /snippet
    * extend snippet
    * POST
    ```
    requestPayload = {
        documentId: UUID,
        collectionId: string,
        position: int, // start position of the snippet which we want to extend
        size: int // how many extra characters we want,
        format // as in '/query'
    }
    ```
    ```
    responsePayload = {
        prev: enhancedText // text to insert before, datatype as in '/query',
        after: enhancedText // text to insert after, datatype as in '/query'  
    }
    ```
 * /index
    * build index
    * POST
    ```
    requestPayload = {
        inputDirectory: string,
        outputDirectory: string
        indexSentences: boolean // index sentences instead of documents, default false
        gzipped: boolean // process gzipped files, default false
    }
    ```
    ```
    responsePayload = {
        ??
        time: long // ms of execution
    }
    ```
 
 * /actuator/health
    * to check for availability
    * part of Spring Boot Actuator, see [documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)
    