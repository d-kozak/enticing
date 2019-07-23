# Response types
This document describes response types that the index server supports. The difference between response types and response formats is that the response type describes what parts of document 
are sent back to the client, while response formats describe what is the structure of those parts. Response formats are discussed [here](./response_format.md).

The plan is to support two response types. Currently only Full is implemented. For Identifiers, [EQL](./eql_spec.md) has to be implemented first. 

## Full
If this type is requested, the response contains full text of the matched region, containing all the matched indexes. 

## Identifiers
EQL allows users to assign identifiers to parts of the query. If this type is selected, the response will include only parts of the document matched by the identified subqueries. 

A small context can also be requested, containing  either the sentence or the paragraph at which these subqueries were matched. If they do not appear in the same unit of context, 
the response will contain all sentences starting from the one containing the first match until the one containing the last match.  

 
