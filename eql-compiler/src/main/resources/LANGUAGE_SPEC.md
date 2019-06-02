# Enticing Query Language (EQL) specification
EQL is a language which you can use to query semantically enhanced documents on the Enticing platform. 
The queries can be as simple as only a few words, but also very complex, containing logical operations, subqueries over multiple indexes or constraints further limiting the results.

Each word in an indexed document contains meta information such as it's word class, lemma and many others. The documents may also contain entities, such as people or places. These entities have their own meta information. 
For example a person will probably have a name and a birthdate at least. All this extra information can be used to write more advanced queries. 
The amount of meta information depends on the index which you are querying. 

The rest of the document is structured as follows. First we provide a practical guide that shows how to use EQL. It starts with a simple query and gradually adds more operators 
to it to satisfy the requirements. Then we provide a list of all operators that EQL supports with their description. Those with background in formal language theory might appreciate 
the formal grammar of EQL which we can be found in the end.  

## Practical guide

Let's start with a simple query. We might want to search for documents talking about Picasso visiting Paris. I guess we all would probably write something like
```
Picasso visited Paris
```
For sure, this query is a good starting point, but there is quite a lot of place for improvement. 
First thing we might be tempted to do is to relax the ordering of the words, so that sentences like _Paris visited by Picasso_ are matched as well.
Well I have good news for you, this is actually the default behavior. On the other hand, if we want to ensure the ordering, we can use the **order** operator.
```
Picasso < visited < Paris
```
This query will search for any document where these three words appear in the specified order. If we want to be even more strict, we can use the **sequence** operator.
```
"Picasso visited Paris"
``` 
Now the three words are required to appear next to each other. But this is probably not what we want 
so let's go back to the original ```Picasso visited Paris``` and try to improve it differently. Even though the order might not be important, 
we might want these words to appear close to each other. Without any modification, the words can be anywhere inside the document. We can use the **Context constraints** to change that.
```
Picasso visited Paris - _PAR_
Picasso visited Paris - _SENT_
```
These two queries require the words to appear in one paragraph or in one sentence, respectively. Let's go with the latter option for now.
Using just one verb might be a bit too specific. Let's add a second option, the verb explore. The **or** operator can be used for that.
```
Picasso ( visited | explored )  Paris - _SENT_
```
Now we are saying that we are looking for documents containing words Picasso, Paris and either visited or explored, or both. Note that the parenthesis are not necessary in the case. The query ```Picasso visited | explored  Paris - _SENT_```
has the same meaning, but the first version might be more explicit. Without the parenthesis it might look like both words on each side are part of the **or**, which is not the case. If we actually wanted that, we can use parenthesis ```(Picasso visited) | (explored  Paris) - _SENT_```, 
but the meaning is different of course.

Let's keep focusing on the verbs a bit longer. There might be documents out there talking about Picasso visiting Paris, but the shape of the verb might be different. 
Maybe the documents are written in the present tense. To be able to match documents like that, we can use the **index** operator. So far our query used only the default index, which is _token_. 
This index contains words from the original document. The metadata about words can be found on the other indexes. 
```
Picasso ( lemma:visit | lemma:explore )  Paris - _SENT_
```
We relaxed the requirements a bit. Any verbs, whose lemmas are either visit or explore will be used. This allows us to match more documents, hopefully including the one we are searching for.
Unfortunately, the query got uglier as well. We can fix this using a shorthand notation.
```
Picasso lemma:(visit|explore) Paris - _SENT_
```
That's better, right?

So far we used only basic indexes, logic operator and constraints. Let's utilize the semantic enhancement more now. EQL allows you the query for an entity instead of an exact word. 
Let's say that there is a sentence talking about Picasso and Paris, but it uses pronouns instead of the direct names. We want to match those as well and using the **align** operator we can.
```
nertag:person^(person.name:Picasso) lemma:(visit|explore) Paris - _SENT_
``` 
Well, this step might be hard to swallow at once, so let's digest it piece by piece. We replaced ```Picasso``` with ```nertag:person^(person.name:Picasso)```. We are already familiar with the first part, ```nertag:person```.
This is an **index** operator. The only difference is that instead of _lemma_ we are querying _nertag_, which is an index containing the type of entity.  So why didn't we just write ```nertag:person lemma:(visit|explore) Paris - _SENT_```? 
It is definitely simpler, but that would match **any** person visiting Paris and this is not what we want. We want that word to be a person, but only a person whose name is Picasso. More technically speaking, we want to 
query two indexes, _nertag_ and _name_, where the name is an attribute of entity person, but these two requirements apply to the same word, they **align**. That's where the name comes from. Now that we understand our new query, 
let's change Paris the same way.
```
nertag:person^(person.name:Picasso) lemma:(visit|explore)  nertag:place^(place.name:Paris) - _SENT_
```
This new query shouldn't be surprising for you. Instead of an exact word Paris, we are using an entity, whose name is Paris, but the real word used in the sentence can be a pronoun for example. 

By applying a few operators, we got a query, which is more generic and therefore matches more documents.  

There is still one more group of operators to talk about, **global constraints**. To illustrate what they are for, let's say that we are searching for two artists and a relationship between them.
The skeleton of the query is ```artist influenced artist```, but we are going to need a few operators to make the query more generic.
```
nertag:(person|artist) < ( lemma:(influce|impact) | (lemma:paid < lemma:tribute) )  < nertag:(person|artist) - _PAR_
``` 
We should already be familiar with the operators used in this query, but as an exercise, let's translate it to English. We are searching for a document where there is an entity followed by a word or words followed by an entity. 
Both entities should be of type person or artist.  The word can be either by a single word,
whose lemma is either influence or impact, or 'paid tribute', again in any form thanks to using the _lemma_ index. On top of that, this whole query is limited to a single paragraph. Seems good. But there is a problem. 
Currently, we didn't express that these two entities should be different people, and that is actually really important, isn't it? Now comes the time to use **global constraints**. 
First we will identify parts of the query we are interested in using the **assignment operator**, than we will use them to express our global constraint.
```
influencer:=nertag:(person|artist) < ( lemma:(influce|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist) - _PAR_ && influencer.nerid != influencee.nerid
``` 
Now we have ensured that the two artist should be different and our query should work as expected.

Let us finish with one more query.
```
a:=nertag:(person|artist) < lemma:visit < b:=nertag:(person|artist) nertag:place^place.name:Barcelona nertag:event^event.date:[1/1/1960..12/12/2012] - _PAR_ && a.nerid != b.nerid
```
In this example we are looking for documents talking about one artist visiting another in Barcelona during an event that happened between 1/1/1960 and 12/12/2012. 
The context is limited to a single paragraph and global constraints are used to ensure that the two artists are different. 

## EQL Operators
The operators can be divided into four categories.
### Basic operators
* **Implicit and** ```A B``` 

If you specify no operator, **and** is chosen automatically. That is all mentioned words have to be in the document, not necessarily in this order.
* **Order**  ```A < B```

A should appear before B, but they do **not** have to be next to each other.
* **Sequence** ``` "A B C" ``` 

A, B and C have to appear in this order next to each other.
* **And**   ```A & B``` 

Both A and B have to be in the document, not necessarily in this order.
* **Or**  ```A | B``` 

At least one of A, B have to be in the document.
* **NOT** - ```!B``` 
 
 B should not be in the document.
* **Parenthesis** - ```(A | B) & C``` 

You can use parenthesis to build more complex logic expressions, such as this example, where we want at least one of A or B and C.  
* **Proximity** - ```A B ~ 5```
 
A and B should appear exactly 5 positions next to each other.

### Index operators
To work with meta information, you have to specify index which you are querying. That can be done using the following operators.
* **Index** ```index:A``` 

Look for document, where value A is present at given index.
For example ```lemma:work``` will match any document in which any word, whose lemma is work, appears, e.g. works, working, worked, ...

If you want to match one of multiple different values in a given index, you can use the shorthand notation ```index:(A|B|C)```, which is equivalent to 
```index:A | index:B  | index:C```

If the index you are querying contains integers or dates, you can use the range operator to specify interval, such as
```date:[1/1/1970..2/2/2000]``` or ```person.age:[20..30]```. 

* **Align** ```index1:A ^ index2:B``` 

The align operator allows to express multiple requirements on one word. For example, we might want  to look
for a noun, whose lemma is do. This query can be written as ```pos:noun ^ lemma:do```.

The align operator is also useful when working with entities. Let's say that we want to find documents talking about people who were born in Brno.
This can be done using the following query: ```nertag:person ^ person.birthplace:Brno```. 

### Context constraints
The default context in which we are searching is the whole document. For more granular queries, 
you can add the following limitation to the end of the query.
* **Paragraph limit**  ``` - _PAR_``` The whole query has to be matched in one paragraph. 
* **Sentence limit** ``` - _SENT_ ``` The whole query has to be matched in one sentence.

### Global constraints
Sometimes we might want to specify some relationship between multiple entities that can't be expressed using the previous operators.
Let's say that we are searching for documents talking about two artists influencing each other. The query we might come up with is the following. 
```
nertag:artist < lemma:influence < nertag:artist
``` 
This query will work, but there is a catch. 
This query might return irrelevant snippets, because we didn't specify that the two artist should be different. 
This where the global constraints come into play. The global constraint is a predicate which is separated from the query by the symbol ```&&```.
The constraint consists of one or more equalities and inequalities connected using logical operators **and**,**or**, **not** and parenthesis, if necessary. 

But before using global constraints we have to be able to identify parts of the query, right? 

* **Assignment operator** ```x:=A```

This operator allows us to assign an identifier to a certain part of the query.

Once we have the identifier, we can use it to write the new query.
```
1:=nertag:artist < lemma:influence < 2:=nertag:artist && 1.nerid != 2.nerid 
```
Now we get back only snippets in which the two artists are different.

If our query grows, we might prefer to use string identifiers instead of numbers.
```
influencer:=nertag:artist < lemma:influence < influencee:=nertag:artist && influencer.nerid != influencee.nerid 
```

## Grammar
Formal grammar written in [Antlr4](https://www.antlr.org/) format can be found at this [link](Eql.g4).
