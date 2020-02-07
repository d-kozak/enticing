# Dispatcher algorithm
This document describes the dispatcher algorithm. Two versions of this algorithm are presented, one synchronous and one asynchronous that returns results as they arrive.
The synchronous version is concurrent in the sense that requests on all nodes are processed concurrently, but it waits for all results before returning, which is bottleneck.
This algorithm is used for dispatching requests to a set of nodes, dividing the amount of wanted snippets between them, with a specified lower bound in order not to send requests for too 
few snippets. It is used in [two different parts](./query_processing.md) of the system.
1) In the WebServer to dispatch a request to a group of IndexServers
2) In IndexServers to dispatch a request to collections that the IndexServer handles

Both versions are implemented using [coroutines](https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html).
## Synchronous version
Simplified pseudocode of the algorithm is the following:
```python
def queryDispatcher(query,initialNodes,snippetCount):
    snippets = []
    nodes = initialNodes
    while len(snippets) < snippetCount and nodes.isNotEmpty():
       results = call(query,nodes)
       nodes = []
       for result in results:
            if result.isSuccess and result.snippets.isNotEmpty():
                 snippets.addAll(result.snippets)
                 if result.offset:
                    nodes.add(NodeWithOffset(result.node,result.offset))
    return snippets
```
The algorithm is iterative and maintains a set of nodes that should be called in the next iteration. 
This set initially contains all the nodes that are available. In each step, the wanted amount of snippets is distributed between the nodes and a request is sent to each of them. 
If the request was successful, some results were returned and information about the offset was sent back, the node can be used in the next iteration. There are two terminating conditions. 
The first is one is when the wanted amount of snippets is reached and the second one is when there are no nodes left for the next iteration. 
Since in each step a node is kept for the next iteration only if it successfully returned some snippets, one of the two conditions has to be eventually reached and therefore
the algorithm is guaranteed to terminate.

Full implementation of the algorithm can be found [here](../query-dispatcher/src/main/kotlin/cz/vutbr/fit/knot/enticing/query/processor/QueryDispatcher.kt).

## Asynchronous version
The synchronous dispatcher processed individual nodes concurrently, but still waits for all of them to finish before any result is returned. It would be more efficient to return results "as they arrive".
This technique is also useful for the ConsoleClient, where pagination is not necessary and all results can be processed at once.
Therefore an asynchronous version of the dispatcher algorithm is proposed, but it is not yet implemented. Apart from the input of the synchronous algorithm, it also accepts a callback 
that will be called when any result is available. 

[Channels](https://kotlinlang.org/docs/reference/coroutines/channels.html) can be used to communicate between coroutines and since each coroutine will need it's own channel, we can use the 
[Actors](https://kotlinlang.org/docs/reference/coroutines/shared-mutable-state-and-concurrency.html#actors) which are essentially coroutines grouped together with their channels.

For each request a _dispatcher_ actor is started which spans N child _executor_ actors, one for each node. The rest of the algorithm is similar to the synchronous one. It is again iterative and 
keeps track of how many snippets were collected and which _executors_ provided successful results and offsets, and in the beginning all _executors_ are used. 
The _dispatcher_ sends a message to each _executor_ informing it how many snippets it should provide. Then it listens on it's channel and every time it receives a message from any _executor_, 
it processes it and forwards the result immediately using the callback function. After all _executors_ replied or timed out, next iteration is performed if possible(some actors provided results) and 
necessary(there is not enough snippets collected yet).

To provide a support for bidirectional communication over http, [WebSockets](https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/websocket.html) can be used.

Small example how coroutines, channels and actors can used for this can found [here](../query-dispatcher/src/main/kotlin/cz/vutbr/fit/knot/enticing/query/processor/asynchron/dispatcher.kt) 
