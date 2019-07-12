# Specification of the Enticing indexing-related components

This document covers the design of the components of the Enticing platform that contribute to it's indexing and searching functionality. 
Currently I propose to create three components: 

* indexer  
* \[slave\] index service
* Commandline index client

And on top of that, I present the possibility to add master index service, either immediately or in the future. 
  
## Indexer 
The responsibility of an indexer is to create indexes out of .mg4j files. Part of this process is to evenly divide the 
indexed data into N parts, so that multiple index servers can each handle one part. 
This component can start as a simple command line tool, but there is an option to eventually extend it 
into a spring boot app with rest interface that can be used to start the index process or to ask for the current status 
(since indexing is a time consuming process). Therefore even the initial version should be implemented with this extension in mind.

## Commandline index client
To allow for automated querying, for example for performance testing, a simple command line client can be implemented. 
This client will either talk to only one index server or the whole indexed corpus. 
The results will be printed into standard output or into a file.  

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
        * accepts [SearchQuery](../dto/src/main/kotlin/cz/vutbr/fit/knot/enticing/dto/SearchQuery.kt)
        * returns [SearchResult](../dto/src/main/kotlin/cz/vutbr/fit/knot/enticing/dto/IndexServer.kt)
* /format
    * GET
        * ask about indexes and entities that can be used in queries
        * returns [CorpusFormat](../dto/src/main/kotlin/cz/vutbr/fit/knot/enticing/dto/CorpusFormat.kt) 
* /document
    * POST 
        * get whole document
        * accepts [DocumentQuery](../dto/src/main/kotlin/cz/vutbr/fit/knot/enticing/dto/IndexServer.kt)
        * returns [FullDocument](../dto/src/main/kotlin/cz/vutbr/fit/knot/enticing/dto/IndexServer.kt)
    
* /context
    * POST
        * extend context
        * accepts[ContextExtensionQuery](../dto/src/main/kotlin/cz/vutbr/fit/knot/enticing/dto/IndexServer.kt)
        ```javascript
        responsePayload = {
            before: annotatedText // text to insert before, datatype as in '/query',
            after: annotatedText, // text to insert after, datatype as in '/query'
            canExtend: boolean  
        }
        ```
 * /config \[Extension\]
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
    
    
 ### Updates of annotations in indexed documents
 Since the automated process of semantic annotation is not always correct, the system should be able to update annotations in the indexed documents.
 A component for updated .mg4j files has already been implemented, but it is necessary to integrate it into the platform.
 
 This process can be implemented as follows. Every index service will maintain a set of outdated documents. That is documents that have been updated 
 and therefore their indexed version is incorrect. When returning a snippet or a document, the uuid of the document is checked and 
 if it matches one of the outdated ones, it is filtered out. New endpoint will be added to the index server to inform it that a document has been updated. 
 This set of document ids will also be persisted in a single file to survive restarting the service.    
 
 For each corpus, there will be extra index service, whose job is to maintain documents with updated annotations. 
 Once the size of documents handled by this index service grows too large, indexer can be run to reindex the corpus.
 
 
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
 
 ## Note on security
 Even if the Master-slave architecture is not going to be used, some kind of security has to be employed anyways.
 For example the /config endpoint of slave index service should be accessible only to it's master component, 
 regardless whether it is gonna be a specialized component or the webserver itself. 
 Currently I can't think of anything better than public/private key cryptography. 
