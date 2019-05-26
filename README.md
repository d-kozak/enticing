#  ENTICING - Entities in Context Interface (New Generation) 
master [![buddy pipeline](https://app.buddy.works/dkozak94/enticing/pipelines/pipeline/187183/badge.svg?token=4cd61eabc6a41d23dbc621b88f3c255e343107f39aa165e546de09fa05955e67 "buddy pipeline")](https://app.buddy.works/dkozak94/enticing/pipelines/pipeline/187183)
release [![buddy pipeline](https://app.buddy.works/dkozak94/enticing/pipelines/pipeline/187243/badge.svg?token=4cd61eabc6a41d23dbc621b88f3c255e343107f39aa165e546de09fa05955e67 "buddy pipeline")](https://app.buddy.works/dkozak94/enticing/pipelines/pipeline/187243)

App deployed at [enticing.herokuapp.com](https://enticing.herokuapp.com/)

All source code related to the Enticing project. 

## Modules
The whole repository is a gradle multi-project consisting of the following subprojects.
* frontend
    * Web interface implemented in Typescript in React
* webserver
    * Spring Boot application that serves the frontend and communicates with other backend components
* mg4j-compiler
    * All code connected with parsing, analyzing and translating mg4j-eql
* dto
    * Data transfer objects for communicating between components of the backend
    * Currently contains only DTOs between webserver and indexserver(to be done)

## Build
Production build
```
gradle stage
```

Run locally
```
gradle bootRun
```

## Authors
* [d-kozak](https://github.com/d-kozak/)
    * contact: [dkozak94@gmail.com](mailto:dkozak94@gmail.com)
