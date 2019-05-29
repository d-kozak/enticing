# Specification of the Index server

Index server manages .mg4j files and uses [mg4j](http://mg4j.di.unimi.it/) to index them and perform queries on them.

# Lifecycle
When started, it loads it's configuration, which is required as an input parameter. The configuration contains the following. 
* format(attributes and annotations) which is used in it's input .mg4j files
* input folder which should be indexed XOR already indexed folder which should be used for querying 
* \[EXTENSION] url of the manager-service (for automatic registration)

If an input folder is specified the server tries to index it after startup. If the indexation fails, the startup fails.
?? If neither input folder nor output folder or both of them are specified, startup fails.
?? Another approach is to let it start anyway, but respond with 500 to any request until the indexing is successfully finished using '/index' endpoint. I think the second might be better. If not for any other reason, the debugging during development will be faster, because there will be no need to restart the spring boot app every time



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
       format
    }
    format = formatDefinition | formatId
    formatId = long
    formatDefinition = {
       entities,
       attributes
    }
    entities = string | Map<string,Map<string,string>>
    attributes = string | List<string>
    // string for predefined option (e.g. "all")
    // Map | List for exact definition
    ```
    ```javascript
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
        // I can see two options here
        // first one, currently used in the UI
        ??
        text:string
        annotations:Map<int,Annotation>
        positions:Array<{from:int,to:int,annotationId:int}>
        ??
        OR
        // second one, as described in the thesis, 
        // more meaningful if we assume a lot of annotations and other meta info
        ??
        Array<Map<string,string>>
        ??
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
    * POST
       * save format to be used for subsequent search requests
       * requestPayload the same as formatDefinition from '/query'
    ```javascript
    responsePayload = {
        id: formatId // id to identify the settings
    }
    ```
* /document
    * whole document
    * POST 
    ```javascript
    requestPayload = {
        documentId: UUID,
        collectionId: string,
        format // as in '/query'    
    }
    ```
    ```javascript
    responsePayload = {
        title: string,
        url: url // ?? redundant, since we already know it from the snippet, but might be useful, maybe? ??
        text: EnhancedText // as in '/query',
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
        size: int // how many extra characters we want,
        format // as in '/query'
    }
    ```
    ```javascript
    responsePayload = {
        before: EnhancedText // text to insert before, datatype as in '/query',
        after: EnhancedText // text to insert after, datatype as in '/query'  
    }
    ```
 * /index
    * build index
    * POST
    ```javascript
    requestPayload = {
        inputDirectory: string,
        outputDirectory: string
        indexSentences: boolean // index sentences instead of documents, default false
        gzipped: boolean // process gzipped files, default false
    }
    ```
    ```javascript
    responsePayload = {
        ??
        time: long // ms of execution
    }
    ```
 
 * /actuator/health
    * to check availability
    * part of Spring Boot Actuator, see [documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)
    