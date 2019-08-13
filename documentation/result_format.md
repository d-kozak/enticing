# Result format
This document describes result format that the index server supports. The difference between result formats and text formats is that the result type describes what parts of document 
are sent back to the client, while text formats describe what is the structure of annotated text itself. Text formats are discussed [here](text_format.md).

The plan is to support two result formats. Currently only Snippet is implemented. For IdentifierList, [EQL](./eql_spec.md) has to be implemented first. 

TODO "add interval match to result"

## Snippet
If this format is requested, the response contains whole matched region, possibly extended if it was too small on it's own. 

## IdentifierList
EQL allows to assign identifiers to parts of the query. If this format is requested, the response will contain only parts of the document matched by the identified subqueries. 

A small context can also be requested, containing  either the sentence or the paragraph at which these subqueries were matched. If they do not appear in the same unit of context, 
the response will contain all sentences starting from the one containing the first match until the one containing the last match.  

 
