#  ENTICING - Entities in Context Interface (New Generation) 
master [![CircleCI](https://circleci.com/gh/d-kozak/enticing/tree/master.svg?style=svg&circle-token=6229d8e724544b9e418fdbbe97d704de68388098)](https://circleci.com/gh/d-kozak/enticing/tree/master)
release [![CircleCI](https://circleci.com/gh/d-kozak/enticing/tree/release.svg?style=svg&circle-token=6229d8e724544b9e418fdbbe97d704de68388098)](https://circleci.com/gh/d-kozak/enticing/tree/release)

App deployed at [enticing.herokuapp.com](https://enticing.herokuapp.com/)

All source code related to the Enticing project. 

## Components
Main components of the system can be seen on the following diagram.
![alt text](./img/components.png)

## Modules
The whole repository is a gradle multi-project consisting of the following submodules.
![alt text](./img/modules.png)
* frontend
    * Web interface implemented in Typescript in React
* webserver
    * Spring Boot application that serves the frontend and communicates with other backend components
* indexer
    * command line app that creates indexes out of mg4j files
* index-server
    * spring boot app that manages indexed mg4j files and responds to EQL queries
* index-config
    * configuration DSLs of index-server and indexer (they share certain parts) 
* eql-compiler
    * All code connected with parsing, analyzing and translating EQL - Enticing Query Language
* dto
    * Data transfer objects for communicating between components of the backend
    * Currently contains only DTOs between webserver and indexserver(to be done)




## Build
### Webserver & frontend
Production build
```
gradle stage
```
Run locally
```
gradle bootRun
```

## Test
Execute all tests
```
gradle test --info
```

## Authors
* [d-kozak](https://github.com/d-kozak/)
    * contact: [dkozak94@gmail.com](mailto:dkozak94@gmail.com)
