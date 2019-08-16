# Components

This document describes in more details all components of the system. 

## Config
All scripts require environment variable ENTICING_HOME to be specified and to point to the directory of the project. When inside the directory, you can set it for example this way.
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

Only the configuration parameter is necessary, since everything can be specified just using the configuration. If you specify them, 
they will take precedence over what is in the configuration file. This allows for easier reuse of the script without the need
to generate new one for each execution.

## Index-server
This component maintains a set of collections, each of which was created by the index-builder. It is able to search them using EQL queries, respond to context extension requests and 
return the whole documents as well.
```
./scripts/index-server /path/to/config.kts [collectionsInfo]
```
* **config.kts** configuration script
* **collectionsInfo** string of the format {collectionId1:[mg4j1,mg4j2,..mg4jN,indexedDir],...,collectionIdN:[...]}
    * The structure is basically a JSON object, where each key represents one collection and its value has the same structure as in index-builder. That is it can either 
    start with a directory, from which all mg4j files should be taken, or it can explicitly enumerate mg4j files. The last element should point to the directory containing the indexed data,
    that is the output of index-builder for those input files. This compact form was chosen, because it is meant to be used by scripts, not by people. For manual start, you can use custom configuration
    script, in which you can configure these in more readable way.
Again, only the configuration script is necessary, reasoning why is the same as in Index-builder.

## WebServer
This component handles the web GUI. Since it has to communicate with a database, it expects ENTICING_DATABASE environment variable to be set and to point to a postgreSQL database,
otherwise it starts with an h2 in-memory database, so all the data are lost when it finishes. It requires no configuration except for that 
variable.
```
./scripts/webserver.sh
```

## ConsoleClient
This component allows to query index-servers from the command line. It has interactive and non-interactive mode.
```
./scripts/console-client.sh /path/to/config.kts [query]
``` 
* **config.kts** configuration script 
* **query** if a query was specified, it's results are shown and the client terminates, otherwise it starts in an interactive mode in which you can ask queries.