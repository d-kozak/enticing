# AIS

## Obsah
1. tri pripady uziti - vyhledat dokumenty, vybrat pozadovana metadata, tvorba korpusu  
2. vyjimky - 1) webserver nedostupny, query neni validni, index-servery nereaguji, index servery failnou
3. MVC uvnitr webserveru a indexserveru
4. diagram sekvence - vyhledavani dokumentu, format corpusu
request -> web:controller -> web:service -> query:dispatcher -> index:controller -> index:service  -> query:dispatcher-> lib:collectionManager -> lib:engine
5. dto pro dotazy a odpovedi
6. viz 4  
7. dto - > visitor pro text processing
8. viz 3 
9. frontend - vyber metadat, collectionManager, queryDispatcher
10. screenshoty + prechody mezi obrazovkami
11. acceptance tests -> 
uzivatel se prihlasi jako admin 
importuje vyhledavaci konfiguraci 
zvoli si metadata
napise dotaz  
zobrazi si vysledky
proklika stranky az do konce
rozsiri si opakovane kontext
zobrazi si cely dokument
zobrazi si zdroj dat
