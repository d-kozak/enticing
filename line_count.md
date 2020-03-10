# Line Count
To track the size of the codebase, [cloc](https://github.com/AlDanial/cloc) will be run every once in a while and it's results will be saved here.

Note: It is important to exclude node_modules and lib dirs not to count dependencies and output jars.
```
cloc . --exclude-dir=node_modules,lib,build,out,data,gradle,.gradle,.idea --not-match-f=package\.lock\.json [--categorized=categories ...]
```
--categorized is just for debug to see which files were included and to which category

## 22.8.2019 
Before removing old result format generating classes
```
\-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
TypeScript                      94            695             93          13458
Kotlin                         180           2323            792          10094
Java                             6            501           1105           2680
Markdown                        22            172              0            808
Groovy                          11             91              8            438
Bourne Shell                    12             42             45            301
DOS Batch                        2             46              4            118
JSON                             3              0              0            100
CSS                              2             26              1             85
ANTLR Grammar                    1             33             34             81
Python                           1             19              0             51
YAML                             1              8             13             26
HTML                             1              3             20             19
Qt Linguist                      1              0              0              1
-------------------------------------------------------------------------------
SUM:                           337           3959           2115          28260
-------------------------------------------------------------------------------
```
After removing them
```
\-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
TypeScript                      94            695             93          13458
Kotlin                         175           2250            790           9733
Java                             6            501           1105           2680
Markdown                        22            172              0            808
Groovy                          11             91              8            438
Bourne Shell                    13             42             45            302
DOS Batch                        2             46              4            118
JSON                             3              0              0            100
CSS                              2             26              1             85
ANTLR Grammar                    1             33             34             81
Python                           1             19              0             51
YAML                             1              8             13             26
HTML                             1              3             20             19
Qt Linguist                      1              0              0              1
-------------------------------------------------------------------------------
SUM:                           333           3886           2113          27900
-------------------------------------------------------------------------------
```

## 10.3.2020
```
--------------------------------------------------------------------------------
Language                      files          blank        comment           code
--------------------------------------------------------------------------------
Kotlin                          324           3695           1297          15442
TypeScript                      119            866            146          12583
Markdown                         29            213              0            954
Groovy                           15            153            131            576
Bourne Shell                     33             66             45            572
Python                            9            144              2            515
JSON                             11              0              0            509
CSS                               4             35              1            137
DOS Batch                         2             46              4            118
HTML                              3              8             40             91
INI                               5             15              0             76
ANTLR Grammar                     1             21              1             62
Bourne Again Shell                5              0              0             40
YAML                              1              9             13             37
--------------------------------------------------------------------------------
SUM:                            561           5271           1680          31712
--------------------------------------------------------------------------------

```