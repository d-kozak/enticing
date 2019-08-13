# Enticing Documentation
This document contains the documentation for the whole project. It covers mostly high-level design choices. For low-level details, please consult the source code itself or the javadoc.
The documentation is divided into the following parts.
* [Architecture](architecture.md) - Describes the architecture of the project, it's deployment units, modules, and dependencies between them.
* [Indexing](indexing.md) - Talks about components performing indexing and searching and their interfaces.
* [Dispatcher](dispatcher.md) - Describes the dispatcher algorithm used to dispatch queries between nodes in the WebServer and collections in the IndexServers.
* [EQL specification](eql_spec.md) - Contains the specification of EQL - Enticing Query Language.
* [EQL internals](eql_impl.md) - Describes how EQL is implemented.
* [Eql.g4](Eql.g4) - Contains the grammar of EQL in the antlr4 format.
* [Console Client](console_client.md) - Describes the console client for querying from the command line.  
* [QueryProcessing](./query_processing.md) - Describes how the queries flow through the system and how they are handled in the very end.
* [ResultFormat](./result_format.md) - Describes the result formats that IndexServers support.
* [TextFormat](./text_format.md) - Describes text formats used for transferring annotated text between components.
* [Terminology](terminology.md) - Contains hopefully simple informal definitions for terms used in the documentation.

Additional info:
* [Frontend Updates](frontend_updates.md) - Some discussions regarding the development of the frontend