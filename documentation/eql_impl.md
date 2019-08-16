# EQL implementation 
This document describes how EQL is implemented. For the description of it's functionality, see [EQL specification](./eql_spec.md). 

The work behind EQL can be divided into three parts.
1. parsing & validating query
2. querying using mg4j
3. postprocessing 

Each of them will now be described in more details.

## Parsing & Validation
For parsing EQL, [Antlr4](https://www.antlr.org/) based generated parser is used. The resulting parse tree is converted to the EQL Abstract Syntax Tree. If there were recoverable syntax errors, 
this tree contains error nodes, which are ignored by the validation phase, which comes next. This allows us to provide semantic validation for the parts of the query that can be parsed while skipping 
the unparsable. The validation consists of the following checks. 

* All indexes, entities and their attributes are checked in the corpus format.

* The global constraints are checked. It is necessary for each variables used in them to correspond to a named subquery. Furthermore each of these variables has to refer to an entity. 
It is also necessary to check if the attributes they request actually exist on these entities. 

Other checks might be added later. Also some AST transformations will probably happen here to perform "syntactic desugaring".  

## Querying
There are two possible ways how the querying can be implemented. One important and a bit sad thing to note is that the parsing has to happen for each collection separately. 
It would be best to parse the query once and then use it in all collections inside IndexServer, but unfortunately even though mg4j allows for custom parsers to be used, 
it does not have a way to directly input AST instead of a query string, parsers are required to take strings as input and there seems to be no way to skip the parsing without providing a
custom SearchEngine implementation that would do that.

The first approach is to serialize EQL AST into a string containing mg4j query. This query is then processed by mg4j, which parses it again. This approach requires 1 EQL parser invocations 
and N mg4j parsers invocations, one for each collection.

The second approach is to directly insert the EQL parser into the mg4j SearchEngine. In this case, the parser would output mg4j AST in the end instead of a string. 
This approach requires N EQL parser invocations.

In the end, I decided to use the first approach for the following reasons. First of all serializing EQL AST to mg4j string is something that would be implemented anyways, 
at least for debugging purposes to see what is happening with the query inside the compiler. The second reason is that outputting a string is much simpler than outputting an AST, therefore the
first version will be simpler and faster to implement. The third reason is based on an assumption, which might not be true, because I haven't done the measurements yet, but I believe is reasonable.
Assuming that parsing EQL is more time consuming than parsing mg4j, especially because of the extra semantic validations and entity attributes to polymorphic index transformations, 
it might be faster to do one EQL parse followed by N mgj4 parses instead of N EQL parses. Once the EQL parser is finished, this assumption can be verified. 
If it proves to be false, it will still be possible to implement the second approach.

## Postprocessing
There are two pieces of functionality inside EQL that are unfortunately not directly handled by mg4j. The first problem is that mgj4 does not return the information how the query was matched. 
It returns intervals for each index, but does not tell what parts of query matched them. Also if there are multiple requirements on one index, the resulting interval will be the whole interval 
fulfilling all of these requirements, so we don't know what exactly matched these individual requirements. Therefore to support named subqueries and in general to be able to tell the user why the 
given result was returned with respect to the query, it is necessary to compute this on our own. The second problem is that mg4j does not support global constraints. EQL allows to express relationships 
between entities matched by named subqueries. Therefore some results returned by mg4j are invalid and has to be filtered out. This actually requires the first problem to be solved, because otherwise 
we don't have the information needed to do this. 

The postprocessing consists of two steps, Query Match Analysis and Global Constraints Check.

### Query Match Analysis
The goal of this step is to find out how the query matched the document. The inputs are interval of the document that should be analyzed and an EQL query in the form of it's AST.
The algorithm appends additional information to the AST. When it finishes, each AST node contains information whether it matched the document and if so, which intervals of the document
were matched by it. Since one node can match the query in multiple ways, there might be and quite often will be more than one such intervals per node.

To implement it, we can use the [algorithms used inside mg4j](http://vigna.di.unimi.it/ftp/papers/EfficientAlgorithmsMinimalIntervalSemantics.pdf), with some modifications.

### Global Constraints Check
The global constraint expression is evaluated. It is again a recursive operation on the AST of the constraint. For leaves, their values are fetched based on what they point to. Internal nodes are again
checked based on their children.
 
  