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