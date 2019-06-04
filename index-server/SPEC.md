# Specification of the Enticing indexing-related components

This document covers the design of the components of the Enticing platform that contribute to it's indexing and searching functionality. 
Currently I propose to create two components: 

* indexer  
* \[slave\] index service

And on top of that, I present the possibility to add master index service, either immediately or in the future. 
  
## Indexer 
The responsibility of an indexer is to create indexes out of .mg4j files. Part of this process is to evenly divide the 
indexed data into N parts, so that multiple index servers can each handle one part. 
This component can start as a simple command line tool, but there is an option to eventually extend it 
into a spring boot app with rest interface that can be used to start the index process or to ask for the current status 
(since indexing is a time consuming process). Therefore even the initial version should be implemented with this extension in mind.

## Slave index service

Slave index service manages already indexed .mg4j files and uses [mg4j](http://mg4j.di.unimi.it/) to perform queries on them.

### Lifecycle
When started, it loads it's configuration, which is required as an input parameter. The configuration contains the following. 
* format which is used in it's input .mg4j files
    * names and descriptions of individual indexes
    * names and descriptions of entities and their attributes
* information about indexed files
    * name of the corpus (e.g. WIKI 2017b, something that uniquely identifies the corpus)
    * the directory where the indexed data are stored
*  url of the manager-service (for automatic registration) - \[extension\]

If no information about indexed files is provided, the server will respond with error messages to all query, document and snippet requests 
until a new configuration is set using the rest api. 

### Rest interface
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
    predefinedOption = "all" // compact way to say 'give it all'
    ```
    ```javascript
    responsePayload = {
        snippets: Array<Snippet>,
        offset:int // for pagination, where to start next
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
        textIndex: [int,int], // this part of text
        queryIndex: [int,int] // was matched by which part of query
    }
    EnhancedText = {  
        text:string,
        annotations:Map<annotationId,Annotation>, 
        positions:Array<{from:int,to:int,annotationId}>
    }
    annotationId = int
    ```
* /format
    * GET
        * ask about indexes and entities that can be used in queries
    ```javascript
    responsePayload = {
        corpusName: string // name of the corpus that the data belongs to
        entities: Map<nertag,Pair<description,Map<atttribute,description>>>, // entities and their attributes
        indexes: Map<indexName,description>  // all indexes that can be queried
    }
    nertag = atttribute = description = indexName = string
    ```  
* /document
    * whole document
    * POST 
    ```javascript
    requestPayload = {
        documentId: UUID,
        collectionId: string,
        query:string?, // optional, if we want to see how the query match the document
        wantedIndexes, // as in '/query'
        responseFormat // as in '/query'  
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
        wantedIndexes, // as in '/query'
        responseFormat // as in '/query'
    }
    ```
    ```javascript
    responsePayload = {
        before: text // text to insert before, datatype as in '/query',
        after: text, // text to insert after, datatype as in '/query'
        canExtend: boolean  
    }
    ```
 * /config
    * change index configuration 
    * POST
    ```javascript
    requestPayload = {   
        directory: string, // which directory the query
        corpusName: string // what index the data belong to
    }
    ```
 
 * /actuator/health
    * to check availability
    * part of Spring Boot Actuator, see [documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)
    
 ## Master index service - \[Extension\]
 The reason why I am suggesting to create a master index service is the following. As far as I understand it, 
 at any point in time one slave index service belongs to group of index services that altogether handles one indexed corpus, e.g. Wiki ABC or CommonCrawl XYZ.
 This group can possibly change over time, if we add given functionality to the index service, but there is always one corpus, 
 to which one index server belongs. Let's call this group of index servers an **indexed corpus**. Therefore, 
 from the webserver perspective, it only makes sense to talk about the indexed corpus, not individual indexes. 
 
 But currently the admin user handles adding and deleting of individual index servers in the GUI of webserver 
 as part of defining the search settings. Given the previously stated fact, I suggest changing that. 
 Instead of adding individual IPs of index servers, which is both time consuming and error prone, 
 I find it more meaningful to provide the admin user with a select component containing a list of currently available indexed corpuses, 
 which can be queried. However, he should still have to possibility to do everything manually if something goes wrong.
 
 I believe this approach also prevents situations like 'admin accidentally added the wrong index server in the wrong group'.
 
 But to be able to talk about this concept of indexed corpus, there must be a component in the system, 
 whose responsibility is to keep track of all currently running index servers and which indexed corpus they belong to.
 This component could be the webserver itself, but maybe a new component is more suitable for this purpose, 
 because the functionality of this master index service can be extended over time to handle other tasks like:
 
 * starting and shutting down slave index servers and whole indexed corpuses 
 * starting and monitoring the indexing process using the indexer component with subsequently starting the index corpus
 * monitoring the infrastructure 
 * error detection and recovery
 * notifications when 'interesting' things happen
     * for example when there is a reason to think that there is a DDOS attack happening,... 
 
 The minimal functionality that I suggest starting with is the following:
 * add new index into indexed corpus
     * and create new indexed corpus if this is the first slave index service that belongs to it
 * return list of known index corpuses
     * each corpus consists of a list of urls of index server that belongs to it
 * periodically check the availability of known index servers 
     * and remove them from the list if they don't respond 
 * notify the webserver when change happens 
 
 As we all know, handling consistency in distributed systems is hard. And by adding master index service, 
 we introduce a potential problem, which is ensuring that the "readonly copy" of indexed corpuses in the webserver is not out of date.
 However, there might be a straightforward solution to this problem. 
 We could move the query functionality from the webserver to the master index service.
 The webserver's responsibility would be only to serve and handle the frontend, while the searching functionality would 
 belong to the master index service. I believe that this would probably also allow for better scaling support in the future, 
 because it will be easier to deploy multiple master services, one main and other with readonly views of the indexed corpuses.
 
 However, one must consider security when building such system and it can actually be quite hard to make 
 such infrastructure that allows automatic registration of components secure. One must be able to verify that the component 
 that wants to be registered is really our index server etc...
 And all of this would make the infrastructure significantly more complex.
 Therefore it might be better to keep this as a possible extension.
 
 Also it is worth mentioning that there already are libraries out there, like projects in Spring Cloud or Spring Boot Admin 
 that handle the monitoring quite well, so maybe adding one of these might be 'just good enough' solution for the monitoring problem to start with.
 And the registration and classification of index services based on the index they are querying can be added to the webserver.