# Enticing Query Language (EQL) specification
EQL is a language which you can use for querying semantically enhanced documents on the Enticing platform. 
The queries can be as simple as a few words which should be in the document, but they can be also very complex, containing logical operations, subqueries over multiple indexes or global constraints further limiting the results.

Each word in an indexed document contains meta information such as it's word class, lemma and many others. The documents may also contain entities, such as people or places. These entities have their own meta information. 
For example a person will probably have a name and a birthdate at least. All this extra information can be used to write more advanced queries. 
The amount of meta information depends on the index which you are querying. 

## EQL Operators
The operators can be divided into four categories.
### Basic operators
* **Implicit and** ```A B``` 

If you specify no operator, **and** is chosen automatically. That is all mentioned words have to be in the document, not necessarily in this order.
* **Sequence** ``` "A B C" ``` 

A, B and C have to appear in sequence in this exact order.
* **Order**  ```A < B```

A should appear before B, but they do **not** have to be next to each other.
* **And**   ```A & B``` 

Both a and b have to be in the document.
* **Or**  ```_A | B_ ``` 

At least one of A, B have to be in the document.
* **NOT** - ```!B``` 
 
 B should not be in the document.
* **Parenthesis** - ```(A | B) & C``` 

You can use parenthesis to build more complex logical expressions, such as this example, where we want at least one of A or B and C.  
* **Proximity** - ```A B ~ 5```
 
A and B should appear exactly 5 positions next to each other.

### Index operators
To work with meta information, you have to specify index which you are querying. That can be done using the following operators.
* **Index** ```index:A``` 

Look for document, where A is present at given index.
For example ```lemma:work``` will match any document in which any word, whose lemma is work, appears, e.g. works, working, worked, ...

If you want to match one of multiple different values in a given index, you can use shorthand notation ```index:(A|B|C)```, which is equivalent to 
```index:A | index:B  | index:C```

If the index you are querying contains integers or dates, you can use the range operator to specify interval, such as
```date:[1/1/1970..2/2/2000]``` or ```person.age:[20..30]```. 

* **Align** ```index1:A ^ index2:B``` 

The align operator allows to express multiple requirements on one word. For example, we might want  to look
for a noun, whose lemma is do. This query can be written as ```pos:noun^lemma:do```.

The align operator is also useful when working with entities. Let's say that we want to find documents talking about people who were born in Brno.
This can be done using the following query: ```nertag:person^person.birthplace:Brno```. 

### Context constraints
The default context in which we are searching is the whole document. For more granular queries, 
you can add the following limitation to the end of the query.
* **Paragraph limit**  ``` - _PAR_``` The whole query has to be matched in one paragraph. 
* **Sentence limit** - ``` - _SENT_ ``` The whole query has to be matched in one sentence.

### Global constraints
Sometimes we might want to specify some relationship between multiple entities that can't be expressed using the previous operators.
Let's say that we are searching for documents talking about two artists influencing each other. The query we might come up with is the following. 
```
nertag:artist < lemma:influence < nertag:artist
``` 
This query will work, but there is a catch. 
This query might return more documents that we want, because we didn't specify that the two artist should be different people. 
This where the global constraints come into play. The global constraint is a predicate which is separated from the query by ```&&```.
The constrain consists of one or more equalities or inequalities connected using logical operators **and**,**or**, **nor** and parenthesis, if necessary. 

But first of all we have to be able to identify a certain part of the query, right? 

* **Identifier operator** ```x:=A```

This operator allows us to assign an identifier to a certain part of the query.

Once we have the identifier, we can use it to write the new query.
```
1:=nertag:artist < lemma:influence < 2:=nertag:artist && 1.nerid != 2.nerid 
```
Now we get back only snippets in which the two artists are different people.

If our query grows, we might prefer to use string identifiers instead of numbers.
```
influencer:=nertag:artist < lemma:influence < influencee:=nertag:artist && influencer.nerid != influencee.nerid 
```

## Grammar
Formal grammar written in [Antlr4](https://www.antlr.org/) format can be found at this [link](Eql.g4).
