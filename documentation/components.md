# Components

This document describes in more details all components of the system. 

## Config
All scripts require an environment variable ENTICING_HOME to be specified and to point to the directory of the project. When inside the directory, you can set it for example this way.
```
export ENTICING_HOME=`realpath .`
```
## Build
All components can be compiled using the following command. 
```
gradle buildAll
```
Note that this command first of all downloads mg4j dependencies so it requires access to the internet.
Also note that for it to work properly you either have to have the ENTICING_HOME set or be in that directory when executing it. 
After the compilation finishes, all jars including mg4j dependencies will be located in the  **lib** directory.

## Index-builder
This component is responsible for preprocessing mg4j files and creating indexes that are later used by index-servers.
```
./scripts/index-builder.sh /path/to/config.kts [collectionId] [input1 ... inputN] [output]
```
* **config.kts** configuration script 
* **collectionId** unique id for this collection to prevent from collisions with other collections
* **input** can be either a single directory, from which all *.mg4j* files should be taken, or an enumeration of mg4j files to be processed
* **output** directory into which indexed data should be saved

Only the configuration parameter is necessary, since everything else can be specified just using the configuration. If you specify them as arguments, 
they will take precedence over what is in the configuration file. This allows for easier reuse of the script without the need
to generate new one for each collection.

## Index-server
This component maintains a set of collections, each of which has been indexed by the index-builder. It is able to search in them using EQL queries, respond to context extension requests and 
return the whole documents as well.
```
./scripts/index-server.sh /path/to/config.kts [collectionsInfo] [...any spring boot params]
```
* **config.kts** configuration script
* **collectionsInfo** string of the format {collectionId1:[mg4j1,mg4j2,..mg4jN,indexedDir],...,collectionIdN:[...]}
    * The structure is basically a JSON object, where each key represents one collection and its value is an array whose structure is as in the index-builder. That is it can either 
    start with a directory, from which all mg4j files should be taken, or it can explicitly enumerate mg4j files. The last element should point to the directory containing the indexed data,
    that is the output of index-builder for those input files. This compact form was chosen, because this parameter is meant to be used by scripts, not by people. For manual start, you can 
    use custom configuration script, in which you can configure these in more readable way.
* **spring boot params** any other parameters are passed directly to the started spring boot app, so you can use them to change the values for example for the port to connect to 

## WebServer
This component handles the web GUI. Since it has to persist data to a database, it expects ENTICING_DATABASE environment variable to be set and to point to a postgreSQL database,
otherwise it starts with an h2 in-memory database, so all the data are lost when it finishes. It requires no other configuration.
```
./scripts/webserver.sh [...any spring boot params]
```
* **spring boot params** any parameters are passed directly to the started spring boot app, so you can use them to change the values for example for the port to connect to* 

## ConsoleClient
This component allows to query index-servers from the command line. It has interactive and non-interactive mode. Because it has many optional parameters, they are non positional.
```
./scripts/console-client.sh [-f output_file] [-r result_format] [-t text_format] /path/to/config.kts [query]
``` 
* **output_file** where to print the output, default is stdout
* **result_format** [details](./result_format.md)
* **text_format** [details](./text_format.md)
* **config.kts** configuration script 
* **query** if a query was specified, it's results are shown and the client terminates, otherwise it starts in an interactive mode in which you can ask queries in a loop.