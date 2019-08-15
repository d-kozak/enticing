# Enticing Query Language (EQL) specification
EQL is a language which you can use to query semantically enhanced documents on the Enticing platform. 
The queries can be as simple as only a few words, but also very complex, containing logical operations, subqueries over multiple indexes or constraints further limiting the results.

Each word in an indexed document contains meta information such as it's word class, lemma and many others. The documents may also contain entities, such as people or places. These entities have their own meta information. 
For example a person will probably have a name and a birth date at least. All this extra information can be used to write more advanced queries. 
The amount of meta information depends on the index which you are querying and you can check it in the settings.

The rest of the document is structured as follows. First we provide a practical guide that shows how to use EQL. It starts with a simple query and gradually adds more operators 
to it to satisfy the requirements. Then we provide a list of all operators that EQL supports with their description. Those with background in formal language theory might appreciate 
the formal grammar of EQL which we can be found in the end.  

## Practical guide

Let's start with a simple query. We might want to search for documents talking about Bonaparte visiting Jaffa. I guess we all would probably write something like
```
Bonaparte visits Jaffa
```
For sure, this query is a good starting point, but there is quite a lot of place for improvement. 
First thing we might be tempted to do is to relax the ordering of the words, so that sentences like _Jaffa visited by Bonaparte_ are matched as well.
Well I have good news for you, this is actually the default behavior. On the other hand, if we want to ensure the ordering, we can use the **order** operator.
For some queries this might be very useful. Let's consider the following one.
```
Gauguin < influenced < Picasso
```
This query will search for any document where these three words appear in the specified order. If we want to be even more strict, we can use the **sequence** operator.
```
"Gauguin influenced Picasso"
``` 
Now the three words are required to appear next to each other. 

Let's go back to the original ```Bonaparte visits Jaffa``` and try to improve it differently. Even though the order is not be important, 
we might want these words to appear close to each other. Without any modification, the words can be anywhere inside the document. We can use the **Context constraints** to change that.
```
Bonaparte visits Jaffa - _PAR_
Bonaparte visits Jaffa - _SENT_
```
These two queries require the words to appear in one paragraph or in one sentence, respectively. Let's go with the latter option for now.
Using just one verb might be a bit too specific. Let's add a second option, the verb explore. The **or** operator can be used for that.
```
Bonaparte ( visits | explores ) Jaffa - _SENT_
```
Now we are saying that we are looking for documents containing words Bonaparte, Jaffa and either visited or explored, or both. Note that the parenthesis are not necessary in the case. The query ```Bonaparte visites | explores Jaffa ~ _SENT_```
has the same meaning, but the first version might be more explicit. Without the parenthesis it might look like both words on each side are part of the **or**, which is not the case. If we actually wanted that, we can use parenthesis ```(Bonaparte visites) | (explores Jaffa) ~ _SENT_```, 
but the meaning is different of course.

Let's keep focusing on the verbs a bit longer. There might be documents out there talking about Bonaparte visiting Jaffa, but the form of the word visit might be different. 
Maybe the documents are written in the past tense. To be able to match documents like that, we can use the **index** operator. So far our query used only the default index, which is _token_. 
This index contains words from the original document. The metadata about words can be found on the other indexes. 
```
Bonaparte lemma:(visit|explore) Jaffa - _SENT_
```
So far we used only basic indexes, logic operator and constraints. Let's utilize the semantic enhancement more now. EQL allows you the query for an entity instead of an exact word. 
Let's say that there is a sentence talking about Bonaparte and Jaffa, but it uses pronouns instead of the direct names. We want to match those as well.
```
person.name:Bonaparte lemma:(visit|explore) Jaffa - _SENT_
``` 
We replaced ```Bonaparte``` with ```person.name:Bonaparte```. In this example _person_ and _name_ are just another indexes that we are querying, just like _lemma_ or _token_.
Here we say that we are looking for an entity of type person whose name is Bonaparte. If we wanted to by more generic and look for any person, we could just use the **index** operator
and ask a query like this ```nertag:person lemma:(visit|explore) Jaffa - _SENT_```
  
Now that we understand our new query, let's change Jaffa the same way.
```
person.name:Bonaparte lemma:(visit|explore)  place.name:Jaffa - _SENT_
```
This new query shouldn't be surprising for you. Instead of an exact word Jaffa, we are using an entity, whose name is Jaffa, but the real word used in the sentence can be a pronoun for example. 

By applying a few operators, we got a query, which is more generic and therefore matches more documents.  

There is still one more group of operators to talk about, **global constraints**. To illustrate what they are for, let's say that we are searching for two artists and a relationship between them.
The skeleton of the query is ```artist influenced artist```, but we are going to need a few operators to make the query more generic.
```
nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < nertag:(person|artist) - _PAR_
``` 
We should already be familiar with the operators used in this query, but as an exercise, let's translate it to English. We are searching for a document where there is an entity followed by a word or words followed by an entity. 
Both entities should be of type person or artist.  The word can be either by a single word,
whose lemma is either influence or impact, or 'paid tribute', again in any form thanks to using the _lemma_ index. On top of that, this whole query is limited to a single paragraph. Seems good. But there is a problem. 
Currently, we didn't express that these two entities should be different people, and that is actually really important, isn't it? Now comes the time to use **global constraints**. 
First we will identify parts of the query we are interested in using the **assignment operator**, than we will use them to express our global constraint.
```
influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist) - _PAR_ && influencer.nerid != influencee.nerid
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
 
A and B should appear at most 5 positions next to each other.

### Index operators
To work with meta information, you have to specify index which you are querying. That can be done using the following operators.
* **Index** ```index:A``` 

Look for document, where value A is present at given index.
For example ```lemma:work``` will match any document in which any word, whose lemma is work, appears, e.g. works, working, worked, ...

If you want to match one of multiple different values in a given index, you can use the notation ```index:(A|B|C)```.

When working with entities, we can query for their attributes as well, ```person.name:Picasso```

If the index you are querying contains integers or dates, you can use the range operator to specify interval, such as
```date:[1/1/1970..2/2/2000]``` or ```person.age:[20..30]```. 

* **Align** ```index1:A ^ index2:B``` 

The align operator allows to express multiple requirements on one word. For example, we might want  to look
for a noun, whose lemma is do. This query can be written as ```pos:noun ^ lemma:do```.

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
