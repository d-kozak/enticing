# Architecture
This document describes the architecture of the enticing platform.
## Components
Main components of the system can be seen on the following diagram.
![alt text](../img/components.png)
* Web frontend
    * web interface of the search engine
* Webserver
    * handles the web frontend
* Console client
    * Queries index servers from the command line
* Index server
    * processes EQL queries on it's indexed data
* Index builder
    * prepares indexes for index servers


## Modules
The whole repository is a gradle multi-project consisting of the submodules shown on the following diagram.
![alt text](../img/modules.png)
Responsibilities of each module are the following.
* frontend
    * GUI for the webserver 
* webserver
    * user authentication
    * query submission
    * search settings
    * user management
* console client
    * query submission from the command line
* query-dispatcher
    * execute query on multiple index servers 
    * query validation(using eql-compiler)
* eql-compiler
    * compile EQL to mg4j
    * query validation
* dto
    * domain objects used in all modules
    * configuration dsls for all modules
* index-lib
    * index data using mg4j
    * query indexed data
* index-server
    * handle queries using index-common
* index-builder
    * index data using index-common
