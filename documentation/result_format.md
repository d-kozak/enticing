# Result format
This document describes result formats that the index server supports. The difference between a result format and a text format is that the result format describes what parts of the document 
are sent back to the client, while the text format describes what is the structure of the annotated text inside the result. Text formats are discussed [here](text_format.md).

The component responsible for creating results is the ResultCreator. As mentioned in the [query processing](query_processing.md), the input for the result creator is a document, 
annotated AST and a result offset. Each node in the AST of the query is associated with a set of intervals over the document that matched the node. Depending on the result format, the sets from some of 
these nodes are chosen. Than we can compute the Cartesian product **X** of these sets. Any member of **X** contains one interval per requirement. For each member of **X**, 
we can find the smallest and the biggest value of all intervals it consists of(essentially we construct an interval that includes all of these intervals). The interval between these two values 
represents a part of the document that should be returned for this combination. Let's call it the MatchedRegion. Using some of the members of **X** would result in MatchedRegions that are either 
too big or overlapping with other MatchedRegions, which can be confusing. Therefore some kind of filtering can be performed. For each result format, a different strategy is used.  
No matter what strategy is used, the result offset is used afterwards to skip the already used results.

## Snippet
If this format is requested, the response contains the whole MatchedRegion, possibly extended if it was too small on it's own. For this format, intervals for leaf nodes and named subqueries nodes 
will be processed. 

The final filtering for this result format filters out MatchedRegions that are bigger than a specified threshold and also intervals that are too overlapping with each other. 
When two intervals are overlapping, a decision has to be made which one of them to keep and which one to filter out. Therefore some kind of metric has to be employed to allow for comparison between
different MatchedRegions. I suggest using the length of the MatchedRegions.

We can use the following algorithm. First, intervals that are longer than **M** are filtered out. Then the reminding intervals are sorted by their length. 
Afterwards the algorithm iterates over the intervals from the shortest and filters out any longer intervals which overlap with the shorter one by more than **N** %. 
In the end, it is also possible to limit the number of results per document to **O**. Parameters **M**, **N**, and **O** can be either constants that are chosen based on experience from using the system
or in some cases they can be computed for each document. For example parameter **O** can be computed based on the size of the document.

## IdentifierList
EQL allows to assign identifiers to parts of the query, constructing named subqueries. If this format is requested, the response will contain only parts of the document matched by the 
identified subqueries. Therefore only intervals for nodes representing named subqueries will be processed. All members of **X** can be used in this format, because any combination of them
might be interesting for the user.

A small context can also be requested, containing  either the sentence or the paragraph at which these subqueries were matched. If they do not appear in the same unit of context, 
the response will contain all sentences starting from the one containing the first match to the one containing the last match.  

Currently only Snippet is implemented. For IdentifierList, [EQL](./eql_spec.md) has to be implemented first.