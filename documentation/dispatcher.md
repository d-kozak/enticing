# Dispatcher algorithm
This document describes the dispatcher algorithm. This algorithm is used for dispatching requests to a set of nodes, distributing the amount of wanted snippets between them. It is used in [two different parts](./query_processing.md) of the system.
1) In the webserver to dispatch a request to a group of IndexServers
2) In IndexServers to dispatch a request to collections that the IndexServer handles

Simplified pseudocode of the algorithm is the following:
```
snippets = []
nodes = allAvailableNodes
while len(snippets) < wantedSnippets and nodes.isNotEmpty():
   results = call(nodes)
   nodes = []
   for result in results:
        if result.isSuccess and result.snippets.isNotEmpty():
             snippets.addAll(result.snippets)
             if result.offset:
                nodes.add(nodeWithOffset(result.node,result.offset))
               
```
The algorithm is iterative and maintains a set of nodes that should be called in the next iteration. 
This set initially contains all the nodes that are available. In each step, the wanted amount of snippets is distributed between the nodes and a request is sent to each of them. 
If the request was successful, some results were returned and information about the offset was sent back, the node can be used in the next iteration. There are two terminating conditions. The first is one is when the wanted amount of snippets is reached
and the second one is when there are no nodes left for the next iteration. Since in each step a node is kept for the next iteration only if it successfully returned some snippets, one of the two conditions has to be eventually reached and therefore
the algorithm is guaranteed to terminate.

Real implementation of the algorithm can be found [here](../query-dispatcher/src/main/kotlin/cz/vutbr/fit/knot/enticing/query/processor/QueryDispatcher.kt).