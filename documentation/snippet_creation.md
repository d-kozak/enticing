# Snippet creation
This document describes the algorithms producing snippets that are sent from the index-server.

The input of the algorithm is the output of mg4j query execution - 
document and intervals over this document, which were matched by the query.

The output of the algorithm is a list of intervals, each representing one snippet to be created.



Current code, which only implements the simple algorithm described above, can be found [here](../index-lib/src/main/kotlin/cz/vutbr/fit/knot/enticing/index/query/QueryExecutor.kt).
It is hardcoded into QueryExecutor. It can be refactored using the Strategy pattern to support multiple snippet creation algorithms.

