import {parseNewAnnotatedText} from "../TextUnitList";
import {measureAverage} from "../../../utils/perf";

const input = {
        "content":
            [{
                "type": "word",
                "indexes": ["1", "Sainte-Marie", "NN", "Sainte-Marie", "0", "ROOT", "0", "0", "0", "0", "0", "0", "sainte-marie", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "among", "IN", "among", "1", "LOC", "Sainte-Marie", "Sainte-Marie", "-1", "0", "0", "0", "among", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "the", "DT", "the", "1", "NMOD", "Sainte-Marie", "Sainte-Marie", "-2", "0", "0", "0", "the", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "kb", "4"],
                "entityClass": "museum",
                "words": [{
                    "type": "word",
                    "indexes": ["4", "Hurons", "NP", "Hurons", "3", "SUFFIX", "the", "the", "-1", "0", "0", "4.0", "hurons", "c:9d7ba74864", "museum", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "4"]
                }, {
                    "type": "word",
                    "indexes": ["5", ";", ":", ";", "1", "NMOD", "Sainte-Marie", "Sainte-Marie", "-4", "0", "0", "0", ";", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["1", "Sainte-Marie", "NN", "Sainte-Marie", "0", "ROOT", "0", "0", "0", "0", "0", "0", "sainte-marie", "c:9d7ba74864", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "among", "IN", "among", "1", "LOC", "Sainte-Marie", "Sainte-Marie", "-1", "0", "0", "0", "among", "c:9d7ba74864", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "the", "DT", "the", "1", "NMOD", "Sainte-Marie", "Sainte-Marie", "-2", "0", "0", "0", "the", "c:9d7ba74864", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "kb", "4"],
                "entityClass": "museum",
                "words": [{
                    "type": "word",
                    "indexes": ["4", "Hurons", "NP", "Hurons", "3", "SUFFIX", "the", "the", "-1", "0", "0", "4.0", "hurons", "c:9d7ba74864", "museum", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "4"]
                }, {
                    "type": "word",
                    "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["1", "Sainte-Marie", "NN", "Sainte-Marie", "0", "ROOT", "0", "0", "0", "0", "0", "0", "sainte-marie", "c:9d7ba74864", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["2", "among", "IN", "among", "1", "LOC", "Sainte-Marie", "Sainte-Marie", "-1", "0", "0", "0", "among", "c:9d7ba74864", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "the", "DT", "the", "1", "NMOD", "Sainte-Marie", "Sainte-Marie", "-2", "0", "0", "0", "the", "c:9d7ba74864", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["4", "Hurons", "NP", "Hurons", "3", "SUFFIX", "the", "the", "-1", "0", "0", "4.0", "hurons", "c:9d7ba74864", "museum", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Established", "VVN", "establish", "0", "ROOT", "0", "0", "0", "0", "0", "0", "established", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1634", "0", "0", "date", "1"],
                "entityClass": "date",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "1634", "LS", "@card@", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "1634", "t:f57b95782f", "date", "0", "0", "1634", "0", "0", "0", "0", "0", "0", "0", "date", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Location", "NN", "location", "0", "ROOT", "0", "0", "0", "0", "0", "0", "location", "0", "0", "0", "0", "1634", "0", "0", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Midland,_Texas", "http://athena3.fit.vutbr.cz/kb/images/freebase/03r_7l2.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Midland44_Skyline.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/09gxlzf.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02b_6wx.jpg", "Midland", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Midland", "NP", "Midland", "0", "SUFFIX", "0", "0", "0", "0", "0", "4.0", "midland", "l:11dddc1488", "location", "https://en.wikipedia.org/wiki/Midland,_Texas", "http://athena3.fit.vutbr.cz/kb/images/freebase/03r_7l2.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Midland44_Skyline.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/09gxlzf.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02b_6wx.jpg", "Midland", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["2", ",", ",", ",", "1", "NMOD", "Midland", "Midland", "-1", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Midland,_Texas", "http://athena3.fit.vutbr.cz/kb/images/freebase/03r_7l2.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Midland44_Skyline.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/09gxlzf.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02b_6wx.jpg", "Midland", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "Ontario", "NP", "Ontario", "2", "SUFFIX", ",", ",", "-1", "https://en.wikipedia.org/wiki/Midland,_Ontario", "3", "location", "ontario", "l:8a4e54e619", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", ",", ",", ",", "2", "P", ",", ",", "-2", "0", "0", "0", ",", "l:8a4e54e619", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "kb", "3"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["5", "Canada", "NP", "Canada", "2", "SUFFIX", ",", ",", "-3", "0", "0", "4.0", "canada", "l:8a4e54e619", "location", "https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["1", "Type", "NN", "type", "0", "ROOT", "0", "0", "0", "0", "0", "0", "type", "0", "0", "https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "living", "VVG", "live", "0", "ROOT", "0", "0", "0", "0", "0", "0", "living", "0", "0", "https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "museum", "NN", "museum", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/Living_museum", "2", "0", "museum", "0", "0", "https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Website", "NN", "website", "0", "ROOT", "0", "0", "0", "0", "0", "0", "website", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "www.saintemarieamongthehurons.on.ca/", "NN", "www.saintemarieamongthehurons.on.ca/", "0", "ROOT", "0", "0", "0", "http://www.saintemarieamongthehurons.on.ca/", "1", "0", "www.saintemarieamongthehurons.on.ca/", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "National", "JJ", "national", "3", "NMOD", "Site", "site", "+2", "0", "0", "0", "national", "l:7b031e8aa3", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "Historic", "JJ", "historic", "3", "NMOD", "Site", "site", "+1", "0", "0", "0", "historic", "l:7b031e8aa3", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Weir_Farm_National_Historic_Site", "http://athena3.fit.vutbr.cz/kb/images/freebase/02gm0xs.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StudioOfJAldenWierInRidgefieldCT10082008.JPG", "Weir_Farm_National_Historic_Site", "United_States", "kb", "3"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["3", "Site", "NN", "site", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "site", "l:7b031e8aa3", "location", "https://en.wikipedia.org/wiki/Weir_Farm_National_Historic_Site", "http://athena3.fit.vutbr.cz/kb/images/freebase/02gm0xs.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StudioOfJAldenWierInRidgefieldCT10082008.JPG", "Weir_Farm_National_Historic_Site", "United_States", "0", "0", "0", "0", "0", "0", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["4", "of", "IN", "of", "3", "NMOD", "Site", "site", "-1", "0", "0", "0", "of", "0", "0", "https://en.wikipedia.org/wiki/Weir_Farm_National_Historic_Site", "http://athena3.fit.vutbr.cz/kb/images/freebase/02gm0xs.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StudioOfJAldenWierInRidgefieldCT10082008.JPG", "Weir_Farm_National_Historic_Site", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["5", "Canada", "NP", "Canada", "3", "SUFFIX", "Site", "site", "-2", "https://en.wikipedia.org/wiki/National_Historic_Sites_of_Canada", "5", "4.2", "canada", "l:87165b8173", "location", "https://en.wikipedia.org/wiki/Weir_Farm_National_Historic_Site", "http://athena3.fit.vutbr.cz/kb/images/freebase/02gm0xs.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StudioOfJAldenWierInRidgefieldCT10082008.JPG", "Weir_Farm_National_Historic_Site", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Designated", "VVN", "designate", "0", "ROOT", "0", "0", "0", "0", "0", "0", "designated", "0", "0", "https://en.wikipedia.org/wiki/Weir_Farm_National_Historic_Site", "http://athena3.fit.vutbr.cz/kb/images/freebase/02gm0xs.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StudioOfJAldenWierInRidgefieldCT10082008.JPG", "Weir_Farm_National_Historic_Site", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1920", "0", "0", "date", "1"],
                "entityClass": "date",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "1920", "LS", "@card@", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "1920", "t:d81bf21ac0", "date", "0", "0", "1920", "0", "0", "0", "0", "0", "0", "0", "date", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Vegetable", "NN", "vegetable", "2", "NMOD", "garden", "garden", "+1", "0", "0", "0", "vegetable", "0", "0", "0", "0", "1920", "0", "0", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "garden", "NN", "garden", "0", "ROOT", "0", "0", "0", "0", "0", "0", "garden", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", ".", "SENT", ".", "2", "SUFFIX", "garden", "garden", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Sainte-Marie", "NN", "Sainte-Marie", "0", "ROOT", "0", "0", "0", "0", "0", "0", "sainte-marie", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "among", "IN", "among", "1", "LOC", "Sainte-Marie", "Sainte-Marie", "-1", "0", "0", "0", "among", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "the", "DT", "the", "1", "NMOD", "Sainte-Marie", "Sainte-Marie", "-2", "0", "0", "0", "the", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "kb", "4"],
                "entityClass": "museum",
                "words": [{
                    "type": "word",
                    "indexes": ["4", "Hurons", "NP", "Hurons", "3", "SUFFIX", "the", "the", "-1", "0", "0", "4.0", "hurons", "c:9d7ba74864", "museum", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "4"]
                }, {
                    "type": "word",
                    "indexes": ["5", "(", "(", "(", "10", "SBJ", "was", "be", "+5", "0", "0", "0", "(", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["6", "French", "NP", "French", "5", "SUFFIX", "(", "(", "-1", "https://en.wikipedia.org/wiki/French_language", "1", "4.2", "french", "n:0f8l9c", "nationality", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["7", ":", ":", ":", "5", "P", "(", "(", "-2", "0", "0", "0", ":", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["8", "Sainte-Marie-au-pays-des-Hurons", "NP", "Sainte-Marie-au-pays-des-Hurons", "5", "SUFFIX", "(", "(", "-3", "0", "0", "4.0", "sainte-marie-au-pays-des-hurons", "l:81213c12e1", "location", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["9", ")", ")", ")", "5", "P", "(", "(", "-4", "0", "0", "0", ")", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "was", "VBD", "be", "1", "NMOD", "Sainte-Marie", "Sainte-Marie", "-9", "0", "0", "0", "was", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "a", "DT", "a", "14", "NMOD", "settlement", "settlement", "+3", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/freebase/0k3rnwg.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "French", "France", "kb", "1"],
                "entityClass": "nationality",
                "words": [{
                    "type": "word",
                    "indexes": ["12", "French", "JJ", "French", "14", "NMOD", "settlement", "settlement", "+2", "https://en.wikipedia.org/wiki/France", "1", "4.1", "french", "n:0f8l9c", "nationality", "https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/freebase/0k3rnwg.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "French", "France", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["13", "Jesuit", "NN", "Jesuit", "14", "NMOD", "settlement", "settlement", "+1", "https://en.wikipedia.org/wiki/Jesuit", "1", "0", "jesuit", "0", "0", "https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/freebase/0k3rnwg.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "French", "France", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["14", "settlement", "NN", "settlement", "10", "PRD", "was", "be", "-4", "0", "0", "0", "settlement", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "in", "IN", "in", "14", "LOC", "settlement", "settlement", "-1", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Wendake,_Quebec", "http://athena3.fit.vutbr.cz/kb/images/freebase/0bcj6d4.jpg", "Wendake", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["16", "Wendake", "NP", "Wendake", "14", "SUFFIX", "settlement", "settlement", "-2", "0", "0", "4.0", "wendake", "l:ba48a23e40", "location", "https://en.wikipedia.org/wiki/Wendake,_Quebec", "http://athena3.fit.vutbr.cz/kb/images/freebase/0bcj6d4.jpg", "Wendake", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["17", ",", ",", ",", "1", "NMOD", "Sainte-Marie", "Sainte-Marie", "-16", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Wendake,_Quebec", "http://athena3.fit.vutbr.cz/kb/images/freebase/0bcj6d4.jpg", "Wendake", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["18", "the", "DT", "the", "19", "NMOD", "land", "land", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "land", "NN", "land", "1", "NMOD", "Sainte-Marie", "Sainte-Marie", "-18", "0", "0", "0", "land", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", "of", "IN", "of", "19", "NMOD", "land", "land", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", "the", "DT", "the", "1", "NMOD", "Sainte-Marie", "Sainte-Marie", "-20", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "Wendat", "NP", "Wendat", "21", "SUFFIX", "the", "the", "-1", "https://en.wikipedia.org/wiki/Wyandot_people", "1", "0", "wendat", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["23", ",", ",", ",", "21", "P", "the", "the", "-2", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["24", "near", "IN", "near", "21", "LOC", "the", "the", "-3", "0", "0", "0", "near", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["25", "modern", "JJ", "modern", "1", "NMOD", "Sainte-Marie", "Sainte-Marie", "-24", "0", "0", "0", "modern", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Midland,_Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/02chwrf.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Midland-trumpeter-swan.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04zsjmc.jpg", "Midland", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["26", "Midland", "NP", "Midland", "25", "SUFFIX", "modern", "modern", "-1", "0", "0", "4.0", "midland", "l:50cebc0b41", "location", "https://en.wikipedia.org/wiki/Midland,_Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/02chwrf.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Midland-trumpeter-swan.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04zsjmc.jpg", "Midland", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["27", ",", ",", ",", "25", "P", "modern", "modern", "-2", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Midland,_Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/02chwrf.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Midland-trumpeter-swan.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04zsjmc.jpg", "Midland", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["28", "Ontario", "NP", "Ontario", "27", "SUFFIX", ",", ",", "-1", "https://en.wikipedia.org/wiki/Midland,_Ontario", "3", "location", "ontario", "l:8a4e54e619", "location", "https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["29", ",", ",", ",", "25", "P", "modern", "modern", "-4", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["30", "from", "IN", "from", "25", "AMOD", "modern", "modern", "-5", "0", "0", "0", "from", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["31", "1639", "CD", "@card@", "30", "PMOD", "from", "from", "-1", "0", "0", "0", "1639", "v:f3d99e9b90", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["32", "to", "TO", "to", "25", "AMOD", "modern", "modern", "-7", "0", "0", "0", "to", "v:f3d99e9b90", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1639", "0", "0", "1649", "0", "0", "interval", "3"],
                "entityClass": "interval",
                "words": [{
                    "type": "word",
                    "indexes": ["33", "1649", "CD", "@card@", "32", "PMOD", "to", "to", "-1", "0", "0", "4.0", "1649", "v:f3d99e9b90", "interval", "0", "0", "1639", "0", "0", "1649", "0", "0", "0", "0", "interval", "3"]
                }, {
                    "type": "word",
                    "indexes": ["34", ".", "SENT", ".", "32", "SUFFIX", "to", "to", "-2", "0", "0", "0", ".", "0", "0", "0", "0", "1639", "0", "0", "1649", "0", "0", "0", "0", "interval", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["1", "It", "PP", "it", "0", "ROOT", "0", "0", "0", "0", "0", "0", "it", "0", "0", "0", "0", "1639", "0", "0", "1649", "0", "0", "0", "0", "interval", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "was", "VBD", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "was", "0", "0", "0", "0", "1639", "0", "0", "1649", "0", "0", "0", "0", "interval", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "the", "DT", "the", "6", "NMOD", "settlement", "settlement", "+3", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "first", "JJ", "first", "6", "NMOD", "settlement", "settlement", "+2", "0", "0", "0", "first", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Europe", "http://athena3.fit.vutbr.cz/kb/images/freebase/02c2pky.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s3v95.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/01z0kd0.jpg", "European", "Europe", "kb", "1"],
                "entityClass": "nationality",
                "words": [{
                    "type": "word",
                    "indexes": ["5", "European", "JJ", "European", "6", "NMOD", "settlement", "settlement", "+1", "https://en.wikipedia.org/wiki/Europe", "1", "4.1", "european", "n:02j9z", "nationality", "https://en.wikipedia.org/wiki/Europe", "http://athena3.fit.vutbr.cz/kb/images/freebase/02c2pky.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s3v95.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/01z0kd0.jpg", "European", "Europe", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["6", "settlement", "NN", "settlement", "2", "PRD", "was", "be", "-4", "0", "0", "0", "settlement", "0", "0", "https://en.wikipedia.org/wiki/Europe", "http://athena3.fit.vutbr.cz/kb/images/freebase/02c2pky.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s3v95.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/01z0kd0.jpg", "European", "Europe", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["7", "in", "IN", "in", "6", "LOC", "settlement", "settlement", "-1", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "what", "WP", "what", "9", "SBJ", "is", "be", "+1", "0", "0", "0", "what", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "is", "VBZ", "be", "7", "PMOD", "in", "in", "-2", "0", "0", "0", "is", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "now", "RB", "now", "9", "TMP", "is", "be", "-1", "0", "0", "0", "now", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "the", "DT", "the", "12", "NMOD", "province", "province", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "province", "NN", "province", "9", "PRD", "is", "be", "-3", "0", "0", "0", "province", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "of", "IN", "of", "12", "NMOD", "province", "province", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["14", "Ontario", "NP", "Ontario", "12", "SUFFIX", "province", "province", "-2", "https://en.wikipedia.org/wiki/Ontario", "1", "4.1", "ontario", "l:8a4e54e619", "location", "https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["15", ".", "SENT", ".", "12", "SUFFIX", "province", "province", "-3", "0", "0", "0", ".", "0", "0", "https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Eight", "CD", "Eight", "2", "NMOD", "missionaries", "missionary", "+1", "0", "0", "0", "eight", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "missionaries", "NNS", "missionary", "5", "SBJ", "were", "be", "+3", "https://en.wikipedia.org/wiki/Canadian_Martyrs", "2", "person", "missionaries", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "from", "IN", "from", "2", "NMOD", "missionaries", "missionary", "-1", "0", "0", "0", "from", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["4", "Sainte-Marie", "NP", "Sainte-Marie", "2", "SUFFIX", "missionaries", "missionary", "-2", "0", "0", "4.0", "sainte-marie", "l:81213c12e1", "location", "https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["5", "were", "VBD", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "were", "0", "0", "https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["6", "martyred", "VVN", "martyr", "5", "SUFFIX", "were", "be", "-1", "https://en.wikipedia.org/wiki/Martyr", "1", "0", "martyred", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", ",", ",", ",", "5", "P", "were", "be", "-2", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "and", "CC", "and", "5", "COORD", "were", "be", "-3", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "were", "VBD", "be", "8", "CONJ", "and", "and", "-1", "0", "0", "0", "were", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "canonized", "VVN", "canonize", "9", "OBJ", "were", "be", "-1", "https://en.wikipedia.org/wiki/Canonization", "1", "0", "canonized", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "by", "IN", "by", "9", "LGS", "were", "be", "-2", "0", "0", "0", "by", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "the", "DT", "the", "5", "NMOD", "were", "be", "-7", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "Catholic", "NP", "Catholic", "12", "SUFFIX", "the", "the", "-1", "0", "0", "0", "catholic", "c:95c71738f9", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["http://www.getty.edu/vow/ULANFullDisplay?find=&role=&nation=&subjectid=500241849", "0", "Roman_Catholic_Church", "religious_institution", "0", "0", "0", "kb", "2"],
                "entityClass": "museum",
                "words": [{
                    "type": "word",
                    "indexes": ["14", "Church", "NP", "Church", "12", "SUFFIX", "the", "the", "-2", "https://en.wikipedia.org/wiki/Catholic_Church", "2", "4.2", "church", "c:95c71738f9", "museum", "http://www.getty.edu/vow/ULANFullDisplay?find=&role=&nation=&subjectid=500241849", "0", "Roman_Catholic_Church", "religious_institution", "0", "0", "0", "0", "0", "0", "kb", "2"]
                }, {
                    "type": "word",
                    "indexes": ["15", "in", "IN", "in", "12", "TMP", "the", "the", "-3", "0", "0", "0", "in", "0", "0", "http://www.getty.edu/vow/ULANFullDisplay?find=&role=&nation=&subjectid=500241849", "0", "Roman_Catholic_Church", "religious_institution", "0", "0", "0", "0", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["16", "1930", "CD", "@card@", "15", "PMOD", "in", "in", "-1", "0", "0", "4.0", "1930", "t:7fd9a7339c", "date", "http://www.getty.edu/vow/ULANFullDisplay?find=&role=&nation=&subjectid=500241849", "0", "Roman_Catholic_Church", "religious_institution", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["17", ".", "SENT", ".", "15", "SUFFIX", "in", "in", "-2", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Sainte-Marie", "NN", "Sainte-Marie", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "sainte-marie", "l:81213c12e1", "location", "https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["2", "Among", "IN", "among", "1", "NMOD", "Sainte-Marie", "Sainte-Marie", "-1", "0", "0", "0", "among", "0", "0", "https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "the", "DT", "the", "5", "SBJ", "was", "be", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "Hurons", "NP", "Hurons", "3", "SUFFIX", "the", "the", "-1", "0", "0", "0", "hurons", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "was", "VBD", "be", "2", "SUB", "Among", "among", "-3", "0", "0", "0", "was", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "designated", "VVN", "designate", "5", "OBJ", "was", "be", "-1", "0", "0", "0", "designated", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "a", "DT", "a", "10", "NMOD", "Site", "site", "+3", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "National", "NP", "National", "7", "SUFFIX", "a", "a", "-1", "0", "0", "0", "national", "l:7b031e8aa3", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "Historic", "JJ", "historic", "10", "NMOD", "Site", "site", "+1", "0", "0", "0", "historic", "l:7b031e8aa3", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Weir_Farm_National_Historic_Site", "http://athena3.fit.vutbr.cz/kb/images/freebase/02gm0xs.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StudioOfJAldenWierInRidgefieldCT10082008.JPG", "Weir_Farm_National_Historic_Site", "United_States", "kb", "3"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["10", "Site", "NN", "site", "5", "PRD", "was", "be", "-5", "0", "0", "4.0", "site", "l:7b031e8aa3", "location", "https://en.wikipedia.org/wiki/Weir_Farm_National_Historic_Site", "http://athena3.fit.vutbr.cz/kb/images/freebase/02gm0xs.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StudioOfJAldenWierInRidgefieldCT10082008.JPG", "Weir_Farm_National_Historic_Site", "United_States", "0", "0", "0", "0", "0", "0", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["11", "of", "IN", "of", "10", "NMOD", "Site", "site", "-1", "0", "0", "0", "of", "0", "0", "https://en.wikipedia.org/wiki/Weir_Farm_National_Historic_Site", "http://athena3.fit.vutbr.cz/kb/images/freebase/02gm0xs.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StudioOfJAldenWierInRidgefieldCT10082008.JPG", "Weir_Farm_National_Historic_Site", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["12", "Canada", "NP", "Canada", "10", "SUFFIX", "Site", "site", "-2", "https://en.wikipedia.org/wiki/National_Historic_Sites_of_Canada", "5", "4.2", "canada", "l:87165b8173", "location", "https://en.wikipedia.org/wiki/Weir_Farm_National_Historic_Site", "http://athena3.fit.vutbr.cz/kb/images/freebase/02gm0xs.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StudioOfJAldenWierInRidgefieldCT10082008.JPG", "Weir_Farm_National_Historic_Site", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["13", "in", "IN", "in", "5", "TMP", "was", "be", "-8", "0", "0", "0", "in", "0", "0", "https://en.wikipedia.org/wiki/Weir_Farm_National_Historic_Site", "http://athena3.fit.vutbr.cz/kb/images/freebase/02gm0xs.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StudioOfJAldenWierInRidgefieldCT10082008.JPG", "Weir_Farm_National_Historic_Site", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1920", "0", "0", "date", "1"],
                "entityClass": "date",
                "words": [{
                    "type": "word",
                    "indexes": ["14", "1920", "CD", "@card@", "13", "PMOD", "in", "in", "-1", "0", "0", "4.0", "1920", "t:2e1ac3c879", "date", "0", "0", "1920", "0", "0", "0", "0", "0", "0", "0", "date", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["15", ".", "SENT", ".", "13", "SUFFIX", "in", "in", "-2", "0", "0", "0", ".", "0", "0", "0", "0", "1920", "0", "0", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "[", "SYM", "[", "8", "NMOD", "reconstruction", "reconstruction", "+7", "0", "0", "0", "[", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "1", "CD", "1", "5", "DEP", "2", "2", "+3", "0", "0", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "]", "SYM", "]", "5", "NMOD", "2", "2", "+2", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons#cite_note-1", "3", "0", "]", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "[", "SYM", "[", "5", "NMOD", "2", "2", "+1", "0", "0", "0", "[", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "2", "CD", "2", "6", "AMOD", "]", "]", "+1", "0", "0", "0", "2", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "]", "SYM", "]", "8", "NMOD", "reconstruction", "reconstruction", "+2", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons#cite_note-2", "3", "0", "]", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "A", "DT", "a", "8", "NMOD", "reconstruction", "reconstruction", "+1", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "reconstruction", "NN", "reconstruction", "0", "ROOT", "0", "0", "0", "0", "0", "0", "reconstruction", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "of", "IN", "of", "8", "NMOD", "reconstruction", "reconstruction", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "the", "DT", "the", "11", "NMOD", "mission", "mission", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "mission", "NN", "mission", "9", "PMOD", "of", "of", "-2", "0", "0", "0", "mission", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "now", "RB", "now", "14", "DEP", "as", "as", "+2", "0", "0", "0", "now", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "operates", "VVZ", "operate", "12", "SUFFIX", "now", "now", "-1", "0", "0", "0", "operates", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "as", "IN", "as", "8", "NMOD", "reconstruction", "reconstruction", "-6", "0", "0", "0", "as", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "a", "DT", "a", "17", "NMOD", "museum", "museum", "+2", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "living", "VVG", "live", "15", "SUFFIX", "a", "a", "-1", "0", "0", "0", "living", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "museum", "NN", "museum", "14", "PMOD", "as", "as", "-3", "https://en.wikipedia.org/wiki/Living_museum", "2", "0", "museum", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", ".", "SENT", ".", "17", "SUFFIX", "museum", "museum", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "A", "DT", "a", "4", "NMOD", "site", "site", "+3", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "nearby", "JJ", "nearby", "4", "NMOD", "site", "site", "+2", "0", "0", "0", "nearby", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "historic", "JJ", "historic", "4", "NMOD", "site", "site", "+1", "0", "0", "0", "historic", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "site", "NN", "site", "10", "NMOD", "spot", "spot", "+6", "0", "0", "0", "site", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", ",", ",", ",", "10", "P", "spot", "spot", "+5", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Carhagouha", "http://athena3.fit.vutbr.cz/kb/images/freebase/03r6cjp.jpg", "Carhagouha", "0", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["6", "Carhagouha", "NP", "Carhagouha", "5", "SUFFIX", ",", ",", "-1", "https://en.wikipedia.org/wiki/Carhagouha", "1", "4.1", "carhagouha", "l:0edd89a4c1", "location", "https://en.wikipedia.org/wiki/Carhagouha", "http://athena3.fit.vutbr.cz/kb/images/freebase/03r6cjp.jpg", "Carhagouha", "0", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["7", ",", ",", ",", "5", "P", ",", ",", "-2", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Carhagouha", "http://athena3.fit.vutbr.cz/kb/images/freebase/03r6cjp.jpg", "Carhagouha", "0", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["8", "marks", "VVZ", "mark", "5", "SUFFIX", ",", ",", "-3", "0", "0", "0", "marks", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "the", "DT", "the", "10", "NMOD", "spot", "spot", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "spot", "NN", "spot", "0", "ROOT", "0", "0", "0", "0", "0", "0", "spot", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Carhagouha", "http://athena3.fit.vutbr.cz/kb/images/freebase/03r6cjp.jpg", "Carhagouha", "0", "coref", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["11", "where", "WRB", "where", "10", "NMOD", "spot", "spot", "-1", "0", "0", "2", "where", "l:0edd89a4c1", "location", "https://en.wikipedia.org/wiki/Carhagouha", "http://athena3.fit.vutbr.cz/kb/images/freebase/03r6cjp.jpg", "Carhagouha", "0", "0", "0", "0", "0", "0", "0", "coref", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["12", "an", "DT", "an", "15", "NMOD", "missionary", "missionary", "+3", "0", "0", "0", "an", "0", "0", "https://en.wikipedia.org/wiki/Carhagouha", "http://athena3.fit.vutbr.cz/kb/images/freebase/03r6cjp.jpg", "Carhagouha", "0", "0", "0", "0", "0", "0", "0", "coref", "-1"]
            }, {
                "type": "word",
                "indexes": ["13", "earlier", "JJR", "early", "15", "NMOD", "missionary", "missionary", "+2", "0", "0", "0", "earlier", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "Récollet", "NP", "Récollet", "13", "SUFFIX", "earlier", "early", "-1", "https://en.wikipedia.org/wiki/Recollects", "1", "0", "récollet", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "missionary", "NN", "missionary", "10", "NMOD", "spot", "spot", "-5", "0", "0", "0", "missionary", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "to", "TO", "to", "15", "NMOD", "missionary", "missionary", "-1", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Wendake,_Quebec", "http://athena3.fit.vutbr.cz/kb/images/freebase/0bcj6d4.jpg", "Wendake", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["17", "Wendake", "NP", "Wendake", "15", "SUFFIX", "missionary", "missionary", "-2", "0", "0", "4.0", "wendake", "l:ba48a23e40", "location", "https://en.wikipedia.org/wiki/Wendake,_Quebec", "http://athena3.fit.vutbr.cz/kb/images/freebase/0bcj6d4.jpg", "Wendake", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["18", ",", ",", ",", "15", "P", "missionary", "missionary", "-3", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Wendake,_Quebec", "http://athena3.fit.vutbr.cz/kb/images/freebase/0bcj6d4.jpg", "Wendake", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["19", "Fr", "NP", "Fr", "15", "SUFFIX", "missionary", "missionary", "-4", "0", "0", "0", "fr", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", ".", "SENT", ".", "15", "SUFFIX", "missionary", "missionary", "-5", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Joseph", "NP", "Joseph", "0", "ROOT", "0", "0", "0", "0", "0", "0", "joseph", "p:87419ddfc7", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "Le", "NP", "Le", "1", "SUFFIX", "Joseph", "Joseph", "-1", "0", "0", "0", "le", "p:87419ddfc7", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Joseph_Le_Caron", "0", "Joseph_Le_Caron", "M", "Ile_de_France", "1586", "0", "1632_03_29", "0", "French|Canadian", "kb", "3"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["3", "Caron", "NP", "Caron", "2", "SUFFIX", "Le", "Le", "-1", "https://en.wikipedia.org/wiki/Joseph_Le_Caron", "3", "4.1", "caron", "p:87419ddfc7", "person", "https://en.wikipedia.org/wiki/Joseph_Le_Caron", "0", "Joseph_Le_Caron", "M", "Ile_de_France", "1586", "0", "1632_03_29", "0", "French|Canadian", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["4", ",", ",", ",", "1", "P", "Joseph", "Joseph", "-3", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Joseph_Le_Caron", "0", "Joseph_Le_Caron", "M", "Ile_de_France", "1586", "0", "1632_03_29", "0", "French|Canadian", "kb", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["5", "presided", "VVN", "preside", "1", "SUFFIX", "Joseph", "Joseph", "-4", "0", "0", "0", "presided", "0", "0", "https://en.wikipedia.org/wiki/Joseph_Le_Caron", "0", "Joseph_Le_Caron", "M", "Ile_de_France", "1586", "0", "1632_03_29", "0", "French|Canadian", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["6", "in", "IN", "in", "1", "TMP", "Joseph", "Joseph", "-5", "0", "0", "0", "in", "0", "0", "https://en.wikipedia.org/wiki/Joseph_Le_Caron", "0", "Joseph_Le_Caron", "M", "Ile_de_France", "1586", "0", "1632_03_29", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1615", "0", "0", "date", "1"],
                "entityClass": "date",
                "words": [{
                    "type": "word",
                    "indexes": ["7", "1615", "CD", "@card@", "6", "PMOD", "in", "in", "-1", "0", "0", "4.0", "1615", "t:1de1403318", "date", "0", "0", "1615", "0", "0", "0", "0", "0", "0", "0", "date", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["8", "over", "IN", "over", "1", "TMP", "Joseph", "Joseph", "-7", "0", "0", "0", "over", "0", "0", "0", "0", "1615", "0", "0", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["9", "the", "DT", "the", "12", "NMOD", "mass", "mass", "+3", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "first", "JJ", "first", "12", "NMOD", "mass", "mass", "+2", "0", "0", "0", "first", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "Catholic", "JJ", "Catholic", "12", "NMOD", "mass", "mass", "+1", "0", "0", "0", "catholic", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "mass", "NN", "mass", "8", "PMOD", "over", "over", "-4", "0", "0", "0", "mass", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "conducted", "VVN", "conduct", "12", "SUFFIX", "mass", "mass", "-1", "0", "0", "0", "conducted", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "in", "IN", "in", "1", "LOC", "Joseph", "Joseph", "-13", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "present-day", "JJ", "present-day", "1", "NMOD", "Joseph", "Joseph", "-14", "0", "0", "0", "present-day", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["16", "Ontario", "NP", "Ontario", "15", "SUFFIX", "present-day", "present-day", "-1", "0", "0", "4.0", "ontario", "l:8a4e54e619", "location", "https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["17", ".", "SENT", ".", "15", "SUFFIX", "present-day", "present-day", "-2", "0", "0", "0", ".", "0", "0", "https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Another", "DT", "another", "3", "NMOD", "site", "site", "+2", "0", "0", "0", "another", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "related", "JJ", "related", "3", "NMOD", "site", "site", "+1", "0", "0", "0", "related", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "site", "NN", "site", "7", "SBJ", "is", "be", "+4", "0", "0", "0", "site", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "of", "IN", "of", "3", "NMOD", "site", "site", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "historical", "JJ", "historical", "6", "NMOD", "interest", "interest", "+1", "0", "0", "0", "historical", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "interest", "NN", "interest", "4", "PMOD", "of", "of", "-2", "0", "0", "0", "interest", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "is", "VBZ", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "is", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/St._Louis", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sg4qd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042dcl2.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StLouisMontage.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0291qcj.jpg", "St__Louis", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["8", "Saint-Louis", "NP", "Saint-Louis", "7", "SUFFIX", "is", "be", "-1", "0", "0", "4.0", "saint-louis", "l:3224004713", "location", "https://en.wikipedia.org/wiki/St._Louis", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sg4qd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042dcl2.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StLouisMontage.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0291qcj.jpg", "St__Louis", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["9", "Mission", "NP", "Mission", "7", "SUFFIX", "is", "be", "-2", "0", "0", "0", "mission", "0", "0", "https://en.wikipedia.org/wiki/St._Louis", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sg4qd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042dcl2.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StLouisMontage.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0291qcj.jpg", "St__Louis", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["10", "National", "NP", "National", "7", "SUFFIX", "is", "be", "-3", "0", "0", "0", "national", "l:7b031e8aa3", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "Historic", "JJ", "historic", "12", "NMOD", "Site", "site", "+1", "0", "0", "0", "historic", "l:7b031e8aa3", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Weir_Farm_National_Historic_Site", "http://athena3.fit.vutbr.cz/kb/images/freebase/02gm0xs.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StudioOfJAldenWierInRidgefieldCT10082008.JPG", "Weir_Farm_National_Historic_Site", "United_States", "kb", "3"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["12", "Site", "NN", "site", "7", "PRD", "is", "be", "-5", "0", "0", "4.0", "site", "l:7b031e8aa3", "location", "https://en.wikipedia.org/wiki/Weir_Farm_National_Historic_Site", "http://athena3.fit.vutbr.cz/kb/images/freebase/02gm0xs.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StudioOfJAldenWierInRidgefieldCT10082008.JPG", "Weir_Farm_National_Historic_Site", "United_States", "0", "0", "0", "0", "0", "0", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["13", ",", ",", ",", "7", "P", "is", "be", "-6", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Weir_Farm_National_Historic_Site", "http://athena3.fit.vutbr.cz/kb/images/freebase/02gm0xs.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StudioOfJAldenWierInRidgefieldCT10082008.JPG", "Weir_Farm_National_Historic_Site", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["14", "located", "VVN", "locate", "7", "SUFFIX", "is", "be", "-7", "0", "0", "0", "located", "0", "0", "https://en.wikipedia.org/wiki/Weir_Farm_National_Historic_Site", "http://athena3.fit.vutbr.cz/kb/images/freebase/02gm0xs.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StudioOfJAldenWierInRidgefieldCT10082008.JPG", "Weir_Farm_National_Historic_Site", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["15", "in", "IN", "in", "7", "LOC", "is", "be", "-8", "0", "0", "0", "in", "0", "0", "https://en.wikipedia.org/wiki/Weir_Farm_National_Historic_Site", "http://athena3.fit.vutbr.cz/kb/images/freebase/02gm0xs.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StudioOfJAldenWierInRidgefieldCT10082008.JPG", "Weir_Farm_National_Historic_Site", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["16", "present-day", "JJ", "present-day", "7", "NMOD", "is", "be", "-9", "0", "0", "0", "present-day", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "Victoria", "NP", "Victoria", "16", "SUFFIX", "present-day", "present-day", "-1", "0", "0", "0", "victoria", "l:6c8cd29b56", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Victoria_Harbour_(British_Columbia)", "http://athena3.fit.vutbr.cz/kb/images/freebase/044lyyb.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/07b72tt.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Aerial_photo_of_Victoria,_BC,_on_Vancouver_Island,_Canada.jpg", "Victoria_Harbour", "Canada", "kb", "2"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["18", "Harbour", "NP", "Harbour", "17", "SUFFIX", "Victoria", "Victoria", "-1", "0", "0", "4.0", "harbour", "l:6c8cd29b56", "location", "https://en.wikipedia.org/wiki/Victoria_Harbour_(British_Columbia)", "http://athena3.fit.vutbr.cz/kb/images/freebase/044lyyb.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/07b72tt.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Aerial_photo_of_Victoria,_BC,_on_Vancouver_Island,_Canada.jpg", "Victoria_Harbour", "Canada", "0", "0", "0", "0", "0", "0", "kb", "2"]
                }, {
                    "type": "word",
                    "indexes": ["19", ",", ",", ",", "16", "P", "present-day", "present-day", "-3", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Victoria_Harbour_(British_Columbia)", "http://athena3.fit.vutbr.cz/kb/images/freebase/044lyyb.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/07b72tt.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Aerial_photo_of_Victoria,_BC,_on_Vancouver_Island,_Canada.jpg", "Victoria_Harbour", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["20", "Ontario", "NP", "Ontario", "16", "SUFFIX", "present-day", "present-day", "-4", "https://en.wikipedia.org/wiki/Victoria_Harbour,_Ontario", "4", "4.2", "ontario", "l:8a4e54e619", "location", "https://en.wikipedia.org/wiki/Victoria_Harbour_(British_Columbia)", "http://athena3.fit.vutbr.cz/kb/images/freebase/044lyyb.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/07b72tt.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Aerial_photo_of_Victoria,_BC,_on_Vancouver_Island,_Canada.jpg", "Victoria_Harbour", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["21", ".", "SENT", ".", "16", "SUFFIX", "present-day", "present-day", "-5", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "It", "PP", "it", "0", "ROOT", "0", "0", "0", "0", "0", "0", "it", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "was", "VBD", "be", "1", "COORD", "It", "it", "-1", "0", "0", "0", "was", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "at", "IN", "at", "2", "PRD", "was", "be", "-1", "0", "0", "0", "at", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/St._Louis", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sg4qd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042dcl2.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StLouisMontage.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0291qcj.jpg", "St__Louis", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["4", "Saint-Louis", "NP", "Saint-Louis", "3", "SUFFIX", "at", "at", "-1", "0", "0", "4.0", "saint-louis", "l:3224004713", "location", "https://en.wikipedia.org/wiki/St._Louis", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sg4qd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042dcl2.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StLouisMontage.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0291qcj.jpg", "St__Louis", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["5", "that", "IN/that", "that", "3", "SUFFIX", "at", "at", "-2", "0", "0", "0", "that", "0", "0", "https://en.wikipedia.org/wiki/St._Louis", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sg4qd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042dcl2.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/StLouisMontage.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0291qcj.jpg", "St__Louis", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["6", "Jesuit", "NP", "Jesuit", "3", "SUFFIX", "at", "at", "-3", "0", "0", "0", "jesuit", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "missionaries", "NNS", "missionary", "3", "PMOD", "at", "at", "-4", "0", "0", "0", "missionaries", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "Jean", "NP", "Jean", "7", "SUFFIX", "missionaries", "missionary", "-1", "0", "0", "0", "jean", "p:fbb09f2520", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "de", "NP", "de", "7", "SUFFIX", "missionaries", "missionary", "-2", "0", "0", "0", "de", "p:fbb09f2520", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "3"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["10", "Brébeuf", "NP", "Brébeuf", "7", "SUFFIX", "missionaries", "missionary", "-3", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "3", "4.1", "brébeuf", "p:fbb09f2520", "person", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["11", "and", "CC", "and", "2", "COORD", "was", "be", "-9", "0", "0", "0", "and", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["12", "Gabriel", "NP", "Gabriel", "2", "OBJ", "was", "be", "-10", "0", "0", "0", "gabriel", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["13", "Lalement", "NP", "Lalement", "12", "SUFFIX", "Gabriel", "Gabriel", "-1", "0", "0", "0", "lalement", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["14", "were", "VBD", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "were", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "captured", "VVN", "capture", "14", "OBJ", "were", "be", "-1", "0", "0", "0", "captured", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "when", "WRB", "when", "20", "TMP", "was", "be", "+4", "0", "0", "0", "when", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "the", "DT", "the", "19", "NMOD", "village", "village", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "Wendat", "NP", "Wendat", "17", "SUFFIX", "the", "the", "-1", "0", "0", "0", "wendat", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "village", "NN", "village", "20", "SBJ", "was", "be", "+1", "0", "0", "0", "village", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", "was", "VBD", "be", "14", "TMP", "were", "be", "-6", "0", "0", "0", "was", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", "attacked", "VVN", "attack", "20", "SUFFIX", "was", "be", "-1", "0", "0", "0", "attacked", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "by", "IN", "by", "20", "LGS", "was", "be", "-2", "0", "0", "0", "by", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["23", "the", "DT", "the", "27", "HMOD", "16", "@card@", "+4", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Iroquois,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Beadle_County_South_Dakota_Incorporated_and_Unincorporated_areas_Iroquois_Highlighted.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/029fn7x.jpg", "Iroquois", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["24", "Iroquois", "NP", "Iroquois", "23", "SUFFIX", "the", "the", "-1", "0", "0", "4.0", "iroquois", "l:7970493be8", "location", "https://en.wikipedia.org/wiki/Iroquois,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Beadle_County_South_Dakota_Incorporated_and_Unincorporated_areas_Iroquois_Highlighted.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/029fn7x.jpg", "Iroquois", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["25", "on", "IN", "on", "23", "NMOD", "the", "the", "-2", "0", "0", "0", "on", "0", "0", "https://en.wikipedia.org/wiki/Iroquois,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Beadle_County_South_Dakota_Incorporated_and_Unincorporated_areas_Iroquois_Highlighted.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/029fn7x.jpg", "Iroquois", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["26", "March", "NP", "March", "23", "SUFFIX", "the", "the", "-3", "0", "0", "0", "march", "t:3d7cab7c20", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["27", "16", "CD", "@card@", "22", "PMOD", "by", "by", "-5", "0", "0", "0", "16", "t:3d7cab7c20", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["28", ",|GGG", ",", ",", "20", "P", "was", "be", "-8", "0", "0", "0", ",", "t:3d7cab7c20", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1649", "3", "16", "date", "4"],
                "entityClass": "date",
                "words": [{
                    "type": "word",
                    "indexes": ["29", "1649", "CD", "@card@", "20", "PRD", "was", "be", "-9", "0", "0", "4.0", "1649", "t:3d7cab7c20", "date", "0", "0", "1649", "3", "16", "0", "0", "0", "0", "0", "date", "4"]
                }, {
                    "type": "word",
                    "indexes": ["30", ".", "SENT", ".", "29", "SUFFIX", "1649", "@card@", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "1649", "3", "16", "0", "0", "0", "0", "0", "date", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["1", "History", "NN", "history", "0", "ROOT", "0", "0", "0", "0", "0", "0", "history", "0", "0", "0", "0", "1649", "3", "16", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Sainte-Marie", "NN", "Sainte-Marie", "0", "ROOT", "0", "0", "0", "0", "0", "0", "sainte-marie", "c:9d7ba74864", "0", "0", "0", "1649", "3", "16", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "among", "IN", "among", "1", "LOC", "Sainte-Marie", "Sainte-Marie", "-1", "0", "0", "0", "among", "c:9d7ba74864", "0", "0", "0", "1649", "3", "16", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "the", "DT", "the", "5", "SBJ", "was", "be", "+2", "0", "0", "0", "the", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "kb", "4"],
                "entityClass": "museum",
                "words": [{
                    "type": "word",
                    "indexes": ["4", "Hurons", "NP", "Hurons", "3", "SUFFIX", "the", "the", "-1", "0", "0", "4.0", "hurons", "c:9d7ba74864", "museum", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "4"]
                }, {
                    "type": "word",
                    "indexes": ["5", "was", "VBD", "be", "2", "SUB", "among", "among", "-3", "0", "0", "0", "was", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["6", "established", "VVN", "establish", "5", "OBJ", "was", "be", "-1", "0", "0", "0", "established", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["7", "in", "IN", "in", "5", "TMP", "was", "be", "-2", "0", "0", "0", "in", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["8", "1639", "CD", "@card@", "7", "PMOD", "in", "in", "-1", "0", "0", "4.0", "1639", "t:a90af1b1fe", "date", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["9", "by", "IN", "by", "5", "LGS", "was", "be", "-4", "0", "0", "0", "by", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/freebase/0k3rnwg.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "French", "France", "kb", "1"],
                "entityClass": "nationality",
                "words": [{
                    "type": "word",
                    "indexes": ["10", "French", "JJ", "French", "11", "NMOD", "Jesuits", "Jesuit", "+1", "0", "0", "4.0", "french", "n:0f8l9c", "nationality", "https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/freebase/0k3rnwg.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "French", "France", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["11", "Jesuits", "NNS", "Jesuit", "9", "PMOD", "by", "by", "-2", "0", "0", "0", "jesuits", "0", "0", "https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/freebase/0k3rnwg.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "French", "France", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["12", ",", ",", ",", "1", "P", "Sainte-Marie", "Sainte-Marie", "-11", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "Fathers", "NP", "Fathers", "1", "SUFFIX", "Sainte-Marie", "Sainte-Marie", "-12", "0", "0", "0", "fathers", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "Jérôme", "NP", "Jérôme", "1", "SUFFIX", "Sainte-Marie", "Sainte-Marie", "-13", "0", "0", "0", "jérôme", "p:ed517f78a3", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Jérôme_Lalemant", "0", "Jerome_Lalemant", "M", "France", "1593_04_27", "0", "1673_01_26", "0", "0", "kb", "2"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["15", "Lalemant", "NP", "Lalemant", "1", "SUFFIX", "Sainte-Marie", "Sainte-Marie", "-14", "https://en.wikipedia.org/wiki/Jérôme_Lalemant", "2", "4.1", "lalemant", "p:ed517f78a3", "person", "https://en.wikipedia.org/wiki/Jérôme_Lalemant", "0", "Jerome_Lalemant", "M", "France", "1593_04_27", "0", "1673_01_26", "0", "0", "kb", "2"]
                }, {
                    "type": "word",
                    "indexes": ["16", "and", "CC", "and", "1", "COORD", "Sainte-Marie", "Sainte-Marie", "-15", "0", "0", "0", "and", "0", "0", "https://en.wikipedia.org/wiki/Jérôme_Lalemant", "0", "Jerome_Lalemant", "M", "France", "1593_04_27", "0", "1673_01_26", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["17", "Jean", "NP", "Jean", "1", "SUFFIX", "Sainte-Marie", "Sainte-Marie", "-16", "0", "0", "0", "jean", "p:fbb09f2520", "0", "https://en.wikipedia.org/wiki/Jérôme_Lalemant", "0", "Jerome_Lalemant", "M", "France", "1593_04_27", "0", "1673_01_26", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["18", "de", "NP", "de", "1", "SUFFIX", "Sainte-Marie", "Sainte-Marie", "-17", "0", "0", "0", "de", "p:fbb09f2520", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "3"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["19", "Brébeuf", "NP", "Brébeuf", "1", "SUFFIX", "Sainte-Marie", "Sainte-Marie", "-18", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "3", "4.1", "brébeuf", "p:fbb09f2520", "person", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["20", "in", "IN", "in", "1", "LOC", "Sainte-Marie", "Sainte-Marie", "-19", "0", "0", "0", "in", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["21", "the", "DT", "the", "22", "NMOD", "land", "land", "+1", "0", "0", "0", "the", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["22", "land", "NN", "land", "20", "PMOD", "in", "in", "-2", "0", "0", "0", "land", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["23", "of", "IN", "of", "22", "NMOD", "land", "land", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["24", "the", "DT", "the", "1", "NMOD", "Sainte-Marie", "Sainte-Marie", "-23", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["25", "Wendat", "NP", "Wendat", "24", "SUFFIX", "the", "the", "-1", "0", "0", "0", "wendat", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["26", ".", "SENT", ".", "24", "SUFFIX", "the", "the", "-2", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "The", "DT", "the", "4", "NMOD", "settlement", "settlement", "+3", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "fortified", "VVN", "fortify", "1", "SUFFIX", "The", "the", "-1", "0", "0", "0", "fortified", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "missionary", "NN", "missionary", "4", "NMOD", "settlement", "settlement", "+1", "0", "0", "0", "missionary", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "settlement", "NN", "settlement", "0", "ROOT", "0", "0", "0", "0", "0", "0", "settlement", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "acted", "VVN", "act", "4", "SUFFIX", "settlement", "settlement", "-1", "0", "0", "0", "acted", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "as", "IN", "as", "4", "NMOD", "settlement", "settlement", "-2", "0", "0", "0", "as", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "a", "DT", "a", "8", "NMOD", "centre", "centre", "+1", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "centre", "NN", "centre", "6", "PMOD", "as", "as", "-2", "0", "0", "0", "centre", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "and", "CC", "and", "8", "COORD", "centre", "centre", "-1", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "base", "NN", "base", "9", "CONJ", "and", "and", "-1", "0", "0", "0", "base", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "of", "IN", "of", "10", "NMOD", "base", "base", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "operations", "NNS", "operation", "11", "PMOD", "of", "of", "-1", "0", "0", "0", "operations", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "for", "IN", "for", "8", "NMOD", "centre", "centre", "-5", "0", "0", "0", "for", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "Jesuit", "NN", "Jesuit", "15", "NMOD", "missionaries", "missionary", "+1", "https://en.wikipedia.org/wiki/Jesuit", "1", "0", "jesuit", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "missionaries", "NNS", "missionary", "13", "PMOD", "for", "for", "-2", "https://en.wikipedia.org/wiki/Missionaries", "1", "0", "missionaries", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "on", "IN", "on", "15", "NMOD", "missionaries", "missionary", "-1", "0", "0", "0", "on", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "the", "DT", "the", "18", "NMOD", "outskirts", "outskirt", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "outskirts", "NNS", "outskirt", "16", "PMOD", "on", "on", "-2", "0", "0", "0", "outskirts", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "of", "IN", "of", "18", "NMOD", "outskirts", "outskirt", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", "what", "WP", "what", "21", "SBJ", "is", "be", "+1", "0", "0", "0", "what", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", "is", "VBZ", "be", "19", "PMOD", "of", "of", "-2", "0", "0", "0", "is", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "now", "RB", "now", "21", "TMP", "is", "be", "-1", "0", "0", "0", "now", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Midland,_Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/02chwrf.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Midland-trumpeter-swan.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04zsjmc.jpg", "Midland", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["23", "Midland", "NP", "Midland", "22", "SUFFIX", "now", "now", "-1", "0", "0", "4.0", "midland", "l:50cebc0b41", "location", "https://en.wikipedia.org/wiki/Midland,_Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/02chwrf.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Midland-trumpeter-swan.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04zsjmc.jpg", "Midland", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["24", ",", ",", ",", "21", "P", "is", "be", "-3", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Midland,_Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/02chwrf.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Midland-trumpeter-swan.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04zsjmc.jpg", "Midland", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["25", "Ontario", "NP", "Ontario", "21", "P", "is", "be", "-4", "https://en.wikipedia.org/wiki/Midland,_Ontario", "3", "location", "ontario", "l:8a4e54e619", "location", "https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["26", "as", "IN", "as", "21", "ADV", "is", "be", "-5", "0", "0", "0", "as", "0", "0", "https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["27", "they", "PP", "they", "19", "SUFFIX", "of", "of", "-8", "0", "0", "0", "they", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["28", "worked", "VVD", "work", "19", "SUFFIX", "of", "of", "-9", "0", "0", "0", "worked", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["29", "amongst", "IN", "amongst", "8", "NMOD", "centre", "centre", "-21", "0", "0", "0", "amongst", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["30", "the", "DT", "the", "4", "NMOD", "settlement", "settlement", "-26", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["31", "Huron", "NP", "Huron", "30", "SUFFIX", "the", "the", "-1", "0", "0", "4.0", "huron", "l:163a24c73c", "location", "https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["32", ".", "SENT", ".", "30", "SUFFIX", "the", "the", "-2", "0", "0", "0", ".", "0", "0", "https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "It", "PP", "it", "0", "ROOT", "0", "0", "0", "0", "0", "0", "it", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "also", "RB", "also", "5", "NMOD", "example", "example", "+3", "0", "0", "0", "also", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "provided", "VVD", "provide", "2", "SUFFIX", "also", "also", "-1", "0", "0", "0", "provided", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "an", "DT", "an", "5", "NMOD", "example", "example", "+1", "0", "0", "0", "an", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "example", "NN", "example", "1", "OBJ", "It", "it", "-4", "0", "0", "0", "example", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "of", "IN", "of", "5", "NMOD", "example", "example", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "a", "DT", "a", "10", "NMOD", "community", "community", "+3", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "functioning", "VVG", "function", "7", "SUFFIX", "a", "a", "-1", "0", "0", "0", "functioning", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Europe", "http://athena3.fit.vutbr.cz/kb/images/freebase/02c2pky.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s3v95.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/01z0kd0.jpg", "European", "Europe", "kb", "1"],
                "entityClass": "nationality",
                "words": [{
                    "type": "word",
                    "indexes": ["9", "European", "JJ", "European", "10", "NMOD", "community", "community", "+1", "0", "0", "4.0", "european", "n:02j9z", "nationality", "https://en.wikipedia.org/wiki/Europe", "http://athena3.fit.vutbr.cz/kb/images/freebase/02c2pky.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s3v95.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/01z0kd0.jpg", "European", "Europe", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["10", "community", "NN", "community", "6", "PMOD", "of", "of", "-4", "0", "0", "0", "community", "0", "0", "https://en.wikipedia.org/wiki/Europe", "http://athena3.fit.vutbr.cz/kb/images/freebase/02c2pky.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s3v95.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/01z0kd0.jpg", "European", "Europe", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["11", "to", "TO", "to", "1", "ADV", "It", "it", "-10", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "the", "DT", "the", "1", "NMOD", "It", "it", "-11", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["13", "Huron", "NP", "Huron", "12", "SUFFIX", "the", "the", "-1", "0", "0", "4.0", "huron", "l:163a24c73c", "location", "https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["14", ".", "SENT", ".", "12", "SUFFIX", "the", "the", "-2", "0", "0", "0", ".", "0", "0", "https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "The", "DT", "the", "2", "NMOD", "mission", "mission", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "mission", "NN", "mission", "3", "SBJ", "was", "be", "+1", "0", "0", "0", "mission", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "was", "VBD", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "was", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "built", "VVN", "build", "3", "SUFFIX", "was", "be", "-1", "0", "0", "0", "built", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "near", "IN", "near", "3", "PRD", "was", "be", "-2", "0", "0", "0", "near", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "the", "DT", "the", "8", "NMOD", "settlement", "settlement", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["7", "Huron", "NP", "Huron", "6", "SUFFIX", "the", "the", "-1", "0", "0", "4.0", "huron", "l:163a24c73c", "location", "https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["8", "settlement", "NN", "settlement", "5", "PMOD", "near", "near", "-3", "0", "0", "0", "settlement", "0", "0", "https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["9", "of", "IN", "of", "8", "NMOD", "settlement", "settlement", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "Quieunonascaranas", "NP", "Quieunonascaranas", "8", "SUFFIX", "settlement", "settlement", "-2", "https://en.wikipedia.org/wiki/Quieunonascaranas", "1", "0", "quieunonascaranas", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", ",", ",", ",", "3", "P", "was", "be", "-8", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "led", "VVN", "lead", "11", "SUFFIX", ",", ",", "-1", "0", "0", "0", "led", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "by", "IN", "by", "3", "MNR", "was", "be", "-10", "0", "0", "0", "by", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "chief", "JJ", "chief", "3", "NMOD", "was", "be", "-11", "0", "0", "0", "chief", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "Auoindaon", "NP", "Auoindaon", "14", "SUFFIX", "chief", "chief", "-1", "https://en.wikipedia.org/wiki/Auoindaon", "1", "person", "auoindaon", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", ".", "SENT", ".", "14", "SUFFIX", "chief", "chief", "-2", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "The", "DT", "the", "2", "NMOD", "mission", "mission", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "mission", "NN", "mission", "3", "SBJ", "was", "be", "+1", "0", "0", "0", "mission", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "was", "VBD", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "was", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "initially", "RB", "initially", "3", "TMP", "was", "be", "-1", "0", "0", "0", "initially", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "founded", "VVN", "found", "4", "SUFFIX", "initially", "initially", "-1", "0", "0", "0", "founded", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "by", "IN", "by", "3", "EXT", "was", "be", "-3", "0", "0", "0", "by", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "18", "CD", "@card@", "8", "NMOD", "men", "man", "+1", "0", "0", "0", "18", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "men", "NNS", "man", "6", "PMOD", "by", "by", "-2", "0", "0", "0", "men", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", ".", "SENT", ".", "8", "SUFFIX", "men", "man", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Arriving", "VVG", "arrive", "0", "ROOT", "0", "0", "0", "0", "0", "0", "arriving", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "in", "IN", "in", "28", "NMOD", "walls", "wall", "+26", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "November", "NP", "November", "2", "SUFFIX", "in", "in", "-1", "0", "0", "0", "november", "t:60698e55aa", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1639", "11", "0", "date", "2"],
                "entityClass": "date",
                "words": [{
                    "type": "word",
                    "indexes": ["4", "1639", "CD", "@card@", "2", "PMOD", "in", "in", "-2", "0", "0", "4.0", "1639", "t:60698e55aa", "date", "0", "0", "1639", "11", "0", "0", "0", "0", "0", "0", "date", "2"]
                }, {
                    "type": "word",
                    "indexes": ["5", ",", ",", ",", "28", "P", "walls", "wall", "+23", "0", "0", "0", ",", "0", "0", "0", "0", "1639", "11", "0", "0", "0", "0", "0", "0", "date", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["6", "the", "DT", "the", "7", "NMOD", "priests", "priest", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "1639", "11", "0", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["7", "priests", "NNS", "priest", "11", "NMOD", "shelter", "shelter", "+4", "0", "0", "0", "priests", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "erected", "VVD", "erect", "7", "SUFFIX", "priests", "priest", "-1", "0", "0", "0", "erected", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "a", "DT", "a", "11", "NMOD", "shelter", "shelter", "+2", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "makeshift", "JJ", "makeshift", "11", "NMOD", "shelter", "shelter", "+1", "0", "0", "0", "makeshift", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "shelter", "NN", "shelter", "23", "NMOD", "clay", "clay", "+12", "0", "0", "0", "shelter", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "out", "IN", "out", "11", "NMOD", "shelter", "shelter", "-1", "0", "0", "0", "out", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "of", "IN", "of", "12", "PMOD", "out", "out", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "cypress", "NN", "cypress", "15", "NMOD", "pillars", "pillar", "+1", "https://en.wikipedia.org/wiki/Cupressaceae", "1", "0", "cypress", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "pillars", "NNS", "pillar", "13", "PMOD", "of", "of", "-2", "0", "0", "0", "pillars", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "and", "CC", "and", "11", "COORD", "shelter", "shelter", "-5", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "a", "DT", "a", "20", "NMOD", "roof", "roof", "+3", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "birch", "JJ", "birch", "20", "NMOD", "roof", "roof", "+2", "https://en.wikipedia.org/wiki/Birch", "1", "0", "birch", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "bark", "NN", "bark", "20", "NMOD", "roof", "roof", "+1", "0", "0", "0", "bark", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", "roof", "NN", "roof", "16", "CONJ", "and", "and", "-4", "0", "0", "0", "roof", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", ",", ",", ",", "11", "P", "shelter", "shelter", "-10", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "using", "VVG", "use", "11", "SUFFIX", "shelter", "shelter", "-11", "0", "0", "0", "using", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["23", "clay", "NN", "clay", "28", "NMOD", "walls", "wall", "+5", "0", "0", "0", "clay", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["24", "to", "TO", "to", "23", "NMOD", "clay", "clay", "-1", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["25", "build", "VV", "build", "23", "SUFFIX", "clay", "clay", "-2", "0", "0", "0", "build", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["26", "the", "DT", "the", "28", "NMOD", "walls", "wall", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["27", "interior", "JJ", "interior", "28", "NMOD", "walls", "wall", "+1", "0", "0", "0", "interior", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["28", "walls", "NNS", "wall", "0", "ROOT", "0", "0", "0", "0", "0", "0", "walls", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["29", ".", "SENT", ".", "28", "SUFFIX", "walls", "wall", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "After", "IN", "after", "0", "ROOT", "0", "0", "0", "0", "0", "0", "after", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "the", "DT", "the", "3", "NMOD", "arrival", "arrival", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "arrival", "NN", "arrival", "1", "PMOD", "After", "after", "-2", "0", "0", "0", "arrival", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "of", "IN", "of", "3", "NMOD", "arrival", "arrival", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "carpenter", "NN", "carpenter", "4", "PMOD", "of", "of", "-1", "0", "0", "0", "carpenter", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "Charles", "NP", "Charles", "3", "SUFFIX", "arrival", "arrival", "-3", "0", "0", "0", "charles", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "Boivin", "NP", "Boivin", "3", "SUFFIX", "arrival", "arrival", "-4", "0", "0", "0", "boivin", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", ",", ",", ",", "1", "P", "After", "after", "-7", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "further", "JJR", "further", "10", "NMOD", "construction", "construction", "+1", "0", "0", "0", "further", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "construction", "NN", "construction", "1", "NMOD", "After", "after", "-9", "0", "0", "0", "construction", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "resulted", "VVN", "result", "10", "SUFFIX", "construction", "construction", "-1", "0", "0", "0", "resulted", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "in", "IN", "in", "10", "LOC", "construction", "construction", "-2", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "a", "DT", "a", "14", "NMOD", "chapel", "chapel", "+1", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "chapel", "NN", "chapel", "12", "PMOD", "in", "in", "-2", "https://en.wikipedia.org/wiki/Chapel", "1", "0", "chapel", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", ",", ",", ",", "14", "P", "chapel", "chapel", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "a", "DT", "a", "17", "NMOD", "residence", "residence", "+1", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "residence", "NN", "residence", "14", "APPO", "chapel", "chapel", "-3", "0", "0", "0", "residence", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "for", "IN", "for", "17", "NMOD", "residence", "residence", "-1", "0", "0", "0", "for", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "the", "DT", "the", "20", "NMOD", "Jesuits", "Jesuit", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", "Jesuits", "NNS", "Jesuit", "18", "PMOD", "for", "for", "-2", "0", "0", "0", "jesuits", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", ",", ",", ",", "20", "P", "Jesuits", "Jesuit", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "a", "DT", "a", "23", "NMOD", "cookhouse", "cookhouse", "+1", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["23", "cookhouse", "NN", "cookhouse", "20", "APPO", "Jesuits", "Jesuit", "-3", "0", "0", "0", "cookhouse", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["24", ",", ",", ",", "23", "P", "cookhouse", "cookhouse", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["25", "a", "DT", "a", "26", "NMOD", "smithy", "smithy", "+1", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["26", "smithy", "NN", "smithy", "23", "COORD", "cookhouse", "cookhouse", "-3", "https://en.wikipedia.org/wiki/Forge", "1", "0", "smithy", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["27", "and", "CC", "and", "26", "COORD", "smithy", "smithy", "-1", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["28", "other", "JJ", "other", "29", "NMOD", "buildings", "building", "+1", "0", "0", "0", "other", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["29", "buildings", "NNS", "building", "27", "CONJ", "and", "and", "-2", "0", "0", "0", "buildings", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["30", ".", "SENT", ".", "29", "SUFFIX", "buildings", "building", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Sainte-Marie", "NP", "Sainte-Marie", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "sainte-marie", "l:81213c12e1", "location", "https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["2", "became", "VVD", "become", "1", "SUFFIX", "Sainte-Marie", "Sainte-Marie", "-1", "0", "0", "0", "became", "0", "0", "https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "the", "DT", "the", "5", "NMOD", "headquarters", "headquarters", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "Jesuit", "NN", "Jesuit", "5", "NMOD", "headquarters", "headquarters", "+1", "0", "0", "0", "jesuit", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "headquarters", "NN", "headquarters", "0", "ROOT", "0", "0", "0", "0", "0", "0", "headquarters", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "in", "IN", "in", "5", "LOC", "headquarters", "headquarters", "-1", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Huronia_(region)", "0", "Huronia", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["7", "Huronia", "NP", "Huronia", "6", "SUFFIX", "in", "in", "-1", "https://en.wikipedia.org/wiki/Huronia_(region)", "1", "4.1", "huronia", "l:e16c7711e5", "location", "https://en.wikipedia.org/wiki/Huronia_(region)", "0", "Huronia", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["8", ",", ",", ",", "5", "P", "headquarters", "headquarters", "-3", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Huronia_(region)", "0", "Huronia", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["9", "from", "IN", "from", "12", "NMOD", "Jesuits", "Jesuit", "+3", "0", "0", "0", "from", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "which", "WDT", "which", "9", "PMOD", "from", "from", "-1", "0", "0", "0", "which", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "the", "DT", "the", "12", "NMOD", "Jesuits", "Jesuit", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "Jesuits", "NNS", "Jesuit", "5", "NMOD", "headquarters", "headquarters", "-7", "0", "0", "0", "jesuits", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "travelled", "VVN", "travel", "12", "SUFFIX", "Jesuits", "Jesuit", "-1", "0", "0", "4.0", "travelled", "h:abb74da36f", "activity", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "activity", "1"]
            }, {
                "type": "word",
                "indexes": ["14", "among", "IN", "among", "12", "LOC", "Jesuits", "Jesuit", "-2", "0", "0", "0", "among", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "activity", "-1"]
            }, {
                "type": "word",
                "indexes": ["15", "the", "DT", "the", "5", "NMOD", "headquarters", "headquarters", "-10", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "Iroqouian-speaking", "NP", "Iroqouian-speaking", "15", "SUFFIX", "the", "the", "-1", "0", "0", "0", "iroqouian-speaking", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["17", "Huron", "NP", "Huron", "15", "SUFFIX", "the", "the", "-2", "https://en.wikipedia.org/wiki/Wyandot_people", "1", "4.2", "huron", "l:163a24c73c", "location", "https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["18", "and", "CC", "and", "15", "COORD", "the", "the", "-3", "0", "0", "0", "and", "0", "0", "https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["19", "Petun", "NP", "Petun", "18", "SUFFIX", "and", "and", "-1", "https://en.wikipedia.org/wiki/Petun", "1", "0", "petun", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", ",", ",", ",", "15", "P", "the", "the", "-5", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", "and", "CC", "and", "15", "COORD", "the", "the", "-6", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "the", "DT", "the", "29", "NMOD", "peoples", "people", "+7", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["23", "Algonquian-speaking", "NP", "Algonquian-speaking", "22", "SUFFIX", "the", "the", "-1", "0", "0", "0", "algonquian-speaking", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Nipissing,_Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/05tltgl.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Nipissing_Twp_ON.JPG", "Nipissing", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["24", "Nipissing", "NP", "Nipissing", "23", "SUFFIX", "Algonquian-speaking", "Algonquian-speaking", "-1", "https://en.wikipedia.org/wiki/Nipissing_First_Nation", "1", "location", "nipissing", "l:5fc23f38c1", "location", "https://en.wikipedia.org/wiki/Nipissing,_Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/05tltgl.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Nipissing_Twp_ON.JPG", "Nipissing", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["25", ",", ",", ",", "22", "P", "the", "the", "-3", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Nipissing,_Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/05tltgl.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Nipissing_Twp_ON.JPG", "Nipissing", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Ottawa", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/OttawaCollage.png|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8glv.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0bwwwh0.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dth40.jpg", "Ottawa", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["26", "Ottawa", "NP", "Ottawa", "22", "SUFFIX", "the", "the", "-4", "https://en.wikipedia.org/wiki/Odawa", "1", "4.2", "ottawa", "l:cbb60d39fd", "location", "https://en.wikipedia.org/wiki/Ottawa", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/OttawaCollage.png|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8glv.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0bwwwh0.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dth40.jpg", "Ottawa", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["27", "and", "CC", "and", "22", "COORD", "the", "the", "-5", "0", "0", "0", "and", "0", "0", "https://en.wikipedia.org/wiki/Ottawa", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/OttawaCollage.png|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8glv.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0bwwwh0.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dth40.jpg", "Ottawa", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Ojibwa,_Wisconsin", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/WIMap-doton-Ojibwa.png|http://athena3.fit.vutbr.cz/kb/images/freebase/029dzkh.jpg", "Ojibwa", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["28", "Ojibwa", "NP", "Ojibwa", "22", "SUFFIX", "the", "the", "-6", "https://en.wikipedia.org/wiki/Ojibwe", "1", "4.2", "ojibwa", "l:951af4f48f", "location", "https://en.wikipedia.org/wiki/Ojibwa,_Wisconsin", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/WIMap-doton-Ojibwa.png|http://athena3.fit.vutbr.cz/kb/images/freebase/029dzkh.jpg", "Ojibwa", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["29", "peoples", "NNS", "people", "21", "CONJ", "and", "and", "-8", "0", "0", "0", "peoples", "0", "0", "https://en.wikipedia.org/wiki/Ojibwa,_Wisconsin", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/WIMap-doton-Ojibwa.png|http://athena3.fit.vutbr.cz/kb/images/freebase/029dzkh.jpg", "Ojibwa", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["30", ",", ",", ",", "29", "P", "peoples", "people", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["31", "whose", "WP$", "whose", "32", "NMOD", "languages", "language", "+1", "0", "0", "0", "whose", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["32", "languages", "NNS", "language", "33", "SBJ", "were", "be", "+1", "0", "0", "0", "languages", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["33", "were", "VBD", "be", "29", "NMOD", "peoples", "people", "-4", "0", "0", "0", "were", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["34", "distinct", "JJ", "distinct", "33", "PRD", "were", "be", "-1", "0", "0", "0", "distinct", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["35", "but", "CC", "but", "34", "COORD", "distinct", "distinct", "-1", "0", "0", "0", "but", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["36", "related", "VVN", "relate", "34", "SUFFIX", "distinct", "distinct", "-2", "0", "0", "0", "related", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["37", "to", "TO", "to", "34", "AMOD", "distinct", "distinct", "-3", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["38", "each", "DT", "each", "39", "NMOD", "other", "other", "+1", "0", "0", "0", "each", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["39", "other", "JJ", "other", "37", "PMOD", "to", "to", "-2", "0", "0", "0", "other", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["40", ".", "SENT", ".", "39", "SUFFIX", "other", "other", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Missionary", "NN", "missionary", "2", "NMOD", "life", "life", "+1", "0", "0", "0", "missionary", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "life", "NN", "life", "0", "ROOT", "0", "0", "0", "0", "0", "0", "life", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["http://www.freebase.com/m/048q44j", "0", "Wigwam", "United_States_of_America", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Wigwam", "NN", "wigwam", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "wigwam", "l:fea2de4cf9", "location", "http://www.freebase.com/m/048q44j", "0", "Wigwam", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "A", "DT", "a", "3", "NMOD", "group", "group", "+2", "0", "0", "0", "a", "0", "0", "http://www.freebase.com/m/048q44j", "0", "Wigwam", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "small", "JJ", "small", "3", "NMOD", "group", "group", "+1", "0", "0", "0", "small", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "group", "NN", "group", "0", "ROOT", "0", "0", "0", "0", "0", "0", "group", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "of", "IN", "of", "3", "NMOD", "group", "group", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "religiously", "RB", "religiously", "7", "NMOD", "men", "man", "+2", "0", "0", "0", "religiously", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "devoted", "VVN", "devote", "5", "SUFFIX", "religiously", "religiously", "-1", "0", "0", "0", "devoted", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "men", "NNS", "man", "4", "PMOD", "of", "of", "-3", "0", "0", "0", "men", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", ",", ",", ",", "3", "P", "group", "group", "-5", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "also", "RB", "also", "11", "PMOD", "as", "as", "+2", "0", "0", "0", "also", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "known", "VVN", "know", "9", "SUFFIX", "also", "also", "-1", "0", "0", "0", "known", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "as", "IN", "as", "3", "NMOD", "group", "group", "-8", "0", "0", "0", "as", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "donnés", "NNS", "donné", "3", "NMOD", "group", "group", "-9", "0", "0", "0", "donnés", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "(", "(", "(", "22", "PMOD", "at", "at", "+9", "0", "0", "0", "(", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "offered", "VVN", "offer", "13", "SUFFIX", "(", "(", "-1", "0", "0", "0", "offered", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", ",", ",", ",", "13", "P", "(", "(", "-2", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "given", "VVN", "give", "13", "SUFFIX", "(", "(", "-3", "0", "0", "0", "given", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "or", "CC", "or", "16", "COORD", "given", "give", "-1", "0", "0", "0", "or", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "gifts", "NNS", "gift", "17", "CONJ", "or", "or", "-1", "0", "0", "0", "gifts", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", ")", ")", ")", "13", "P", "(", "(", "-6", "0", "0", "0", ")", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", ",", ",", ",", "13", "P", "(", "(", "-7", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", "worked", "VVN", "work", "13", "SUFFIX", "(", "(", "-8", "0", "0", "0", "worked", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "at", "IN", "at", "12", "LOC", "donnés", "donné", "-10", "0", "0", "0", "at", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["23", "the", "DT", "the", "24", "NMOD", "mission", "mission", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["24", "mission", "NN", "mission", "22", "PMOD", "at", "at", "-2", "0", "0", "0", "mission", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["25", "in", "IN", "in", "12", "NMOD", "donnés", "donné", "-13", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["26", "return", "NN", "return", "25", "PMOD", "in", "in", "-1", "0", "0", "4.0", "return", "s:d9a6c22579", "activity", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "activity", "1"]
            }, {
                "type": "word",
                "indexes": ["27", "for", "IN", "for", "26", "NMOD", "return", "return", "-1", "0", "0", "0", "for", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "activity", "-1"]
            }, {
                "type": "word",
                "indexes": ["28", "food", "NN", "food", "27", "PMOD", "for", "for", "-1", "0", "0", "0", "food", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["29", ",", ",", ",", "28", "P", "food", "food", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["30", "clothing", "NN", "clothing", "28", "COORD", "food", "food", "-2", "0", "0", "0", "clothing", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["31", ",", ",", ",", "30", "P", "clothing", "clothing", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["32", "and", "CC", "and", "30", "COORD", "clothing", "clothing", "-2", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["33", "shelter", "NN", "shelter", "32", "CONJ", "and", "and", "-1", "0", "0", "0", "shelter", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["34", ".", "SENT", ".", "28", "SUFFIX", "food", "food", "-6", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "The", "DT", "the", "2", "NMOD", "Jesuits", "Jesuit", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "Jesuits", "NNS", "Jesuit", "4", "NMOD", "engagés", "engagés", "+2", "0", "0", "0", "jesuits", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "hired", "VVD", "hire", "2", "SUFFIX", "Jesuits", "Jesuit", "-1", "0", "0", "0", "hired", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "engagés", "NNS", "engagés", "0", "ROOT", "0", "0", "0", "0", "0", "0", "engagés", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", ",", ",", ",", "4", "P", "engagés", "engagés", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "labourers", "NNS", "labourer", "4", "COORD", "engagés", "engagés", "-2", "0", "0", "0", "labourers", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", ",", ",", ",", "6", "P", "labourers", "labourer", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "and", "CC", "and", "6", "COORD", "labourers", "labourer", "-2", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "non-clerical", "JJ", "non-clerical", "10", "NMOD", "Jesuits", "Jesuit", "+1", "0", "0", "0", "non-clerical", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "Jesuits", "NNS", "Jesuit", "8", "CONJ", "and", "and", "-2", "0", "0", "0", "jesuits", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "known", "VVN", "know", "10", "SUFFIX", "Jesuits", "Jesuit", "-1", "0", "0", "0", "known", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "as", "IN", "as", "4", "NMOD", "engagés", "engagés", "-8", "0", "0", "0", "as", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "``", "``", "``", "15", "P", "brothers", "brother", "+2", "0", "0", "0", "``", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "lay", "VV", "lay", "13", "SUFFIX", "``", "``", "-1", "0", "0", "0", "lay", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "brothers", "NNS", "brother", "12", "PMOD", "as", "as", "-3", "0", "0", "0", "brothers", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "''", "''", "''", "15", "P", "brothers", "brother", "-1", "0", "0", "0", "''", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", ".", "SENT", ".", "15", "SUFFIX", "brothers", "brother", "-2", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "The", "DT", "the", "2", "NMOD", "Jesuits", "Jesuit", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "Jesuits", "NNS", "Jesuit", "0", "ROOT", "0", "0", "0", "0", "0", "0", "jesuits", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "preached", "VVD", "preach", "2", "SUFFIX", "Jesuits", "Jesuit", "-1", "0", "0", "0", "preached", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "the", "DT", "the", "2", "NMOD", "Jesuits", "Jesuit", "-2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "Christian", "NP", "Christian", "4", "SUFFIX", "the", "the", "-1", "https://en.wikipedia.org/wiki/Christian", "1", "0", "christian", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "Gospel", "NP", "Gospel", "4", "SUFFIX", "the", "the", "-2", "https://en.wikipedia.org/wiki/Gospel", "1", "0", "gospel", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "to", "TO", "to", "4", "ADV", "the", "the", "-3", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "the", "DT", "the", "14", "NMOD", "story", "story", "+6", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["9", "Huron", "NP", "Huron", "8", "SUFFIX", "the", "the", "-1", "0", "0", "4.0", "huron", "l:163a24c73c", "location", "https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["10", ",", ",", ",", "8", "P", "the", "the", "-2", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["11", "often", "RB", "often", "14", "NMOD", "story", "story", "+3", "0", "0", "0", "often", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "adapting", "VVG", "adapt", "11", "SUFFIX", "often", "often", "-1", "0", "0", "0", "adapting", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "the", "DT", "the", "14", "NMOD", "story", "story", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "story", "NN", "story", "7", "PMOD", "to", "to", "-7", "0", "0", "0", "story", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "to", "TO", "to", "4", "ADV", "the", "the", "-11", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "local", "JJ", "local", "17", "NMOD", "customs", "custom", "+1", "0", "0", "0", "local", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "customs", "NNS", "custom", "15", "PMOD", "to", "to", "-2", "0", "0", "0", "customs", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "and", "CC", "and", "17", "COORD", "customs", "custom", "-1", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "symbols", "NNS", "symbol", "18", "CONJ", "and", "and", "-1", "0", "0", "0", "symbols", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", ".", "SENT", ".", "18", "SUFFIX", "and", "and", "-2", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "One", "CD", "One", "9", "SBJ", "was", "be", "+8", "0", "0", "0", "one", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "of", "IN", "of", "1", "NMOD", "One", "One", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "the", "DT", "the", "6", "NMOD", "examples", "example", "+3", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "most", "RBS", "most", "5", "AMOD", "famous", "famous", "+1", "0", "0", "0", "most", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "famous", "JJ", "famous", "6", "NMOD", "examples", "example", "+1", "0", "0", "0", "famous", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "examples", "NNS", "example", "2", "PMOD", "of", "of", "-4", "0", "0", "0", "examples", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "of", "IN", "of", "6", "NMOD", "examples", "example", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "this", "DT", "this", "7", "PMOD", "of", "of", "-1", "0", "0", "0", "this", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "was", "VBD", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "was", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "the", "DT", "the", "18", "NMOD", "hymn", "hymn", "+8", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "``", "``", "``", "18", "NMOD", "hymn", "hymn", "+7", "0", "0", "0", "``", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["12", "Huron", "NP", "Huron", "11", "SUFFIX", "``", "``", "-1", "0", "0", "4.0", "huron", "l:163a24c73c", "location", "https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["13", "Carol", "NP", "Carol", "11", "SUFFIX", "``", "``", "-2", "https://en.wikipedia.org/wiki/Huron_Carol", "2", "0", "carol", "0", "0", "https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["14", "``", "``", "``", "18", "P", "hymn", "hymn", "+4", "0", "0", "0", "``", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", ",", ",", ",", "14", "P", "``", "``", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "a", "DT", "a", "18", "NMOD", "hymn", "hymn", "+2", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "Christmas", "NP", "Christmas", "16", "SUFFIX", "a", "a", "-1", "https://en.wikipedia.org/wiki/Christmas", "1", "0", "christmas", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "hymn", "NN", "hymn", "9", "PRD", "was", "be", "-9", "https://en.wikipedia.org/wiki/Hymn", "1", "0", "hymn", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "written", "VVN", "write", "18", "SUFFIX", "hymn", "hymn", "-1", "0", "0", "0", "written", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", "by", "IN", "by", "18", "NMOD", "hymn", "hymn", "-2", "0", "0", "0", "by", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", "Jean", "NP", "Jean", "18", "SUFFIX", "hymn", "hymn", "-3", "0", "0", "0", "jean", "p:fbb09f2520", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "de", "NP", "de", "18", "SUFFIX", "hymn", "hymn", "-4", "0", "0", "0", "de", "p:fbb09f2520", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "3"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["23", "Brébeuf", "NP", "Brébeuf", "18", "SUFFIX", "hymn", "hymn", "-5", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "3", "4.1", "brébeuf", "p:fbb09f2520", "person", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["24", ".", "SENT", ".", "18", "SUFFIX", "hymn", "hymn", "-6", "0", "0", "0", ".", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["1", "A", "DT", "a", "3", "NMOD", "version", "version", "+2", "0", "0", "0", "a", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "translated", "VVN", "translate", "1", "SUFFIX", "A", "a", "-1", "0", "0", "0", "translated", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "version", "NN", "version", "0", "ROOT", "0", "0", "0", "0", "0", "0", "version", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "of", "IN", "of", "3", "NMOD", "version", "version", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "this", "DT", "this", "6", "NMOD", "song", "song", "+1", "0", "0", "0", "this", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "song", "NN", "song", "4", "PMOD", "of", "of", "-2", "0", "0", "0", "song", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "remains", "VVZ", "remain", "6", "SUFFIX", "song", "song", "-1", "0", "0", "0", "remains", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "popular", "JJ", "popular", "6", "APPO", "song", "song", "-2", "0", "0", "0", "popular", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "in", "IN", "in", "8", "AMOD", "popular", "popular", "-1", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "Canadian", "JJ", "Canadian", "11", "NMOD", "churches", "church", "+1", "0", "0", "0", "canadian", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "churches", "NNS", "church", "9", "PMOD", "in", "in", "-2", "0", "0", "0", "churches", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "to", "TO", "to", "8", "AMOD", "popular", "popular", "-4", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "this", "DT", "this", "14", "NMOD", "day", "day", "+1", "0", "0", "0", "this", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "day", "NN", "day", "12", "PMOD", "to", "to", "-2", "0", "0", "0", "day", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", ".", "SENT", ".", "14", "SUFFIX", "day", "day", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Soldiers", "NNS", "soldier", "7", "NMOD", "presence", "presence", "+6", "0", "0", "0", "soldiers", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "had", "VHD", "have", "1", "SUFFIX", "Soldiers", "soldier", "-1", "0", "0", "0", "had", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "a", "DT", "a", "7", "NMOD", "presence", "presence", "+4", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "small", "JJ", "small", "7", "NMOD", "presence", "presence", "+3", "0", "0", "0", "small", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "but", "CC", "but", "4", "COORD", "small", "small", "-1", "0", "0", "0", "but", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "important", "JJ", "important", "5", "CONJ", "but", "but", "-1", "0", "0", "0", "important", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "presence", "NN", "presence", "0", "ROOT", "0", "0", "0", "0", "0", "0", "presence", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "at", "IN", "at", "7", "LOC", "presence", "presence", "-1", "0", "0", "0", "at", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "the", "DT", "the", "10", "NMOD", "mission", "mission", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "mission", "NN", "mission", "8", "PMOD", "at", "at", "-2", "0", "0", "0", "mission", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", ".", "SENT", ".", "10", "SUFFIX", "mission", "mission", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Twenty-three", "NP", "Twenty-three", "0", "ROOT", "0", "0", "0", "0", "0", "0", "twenty-three", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "soldiers", "NNS", "soldier", "1", "NMOD", "Twenty-three", "Twenty-three", "-1", "0", "0", "0", "soldiers", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "wintered", "VVN", "winter", "2", "SUFFIX", "soldiers", "soldier", "-1", "0", "0", "0", "wintered", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "at", "IN", "at", "2", "NMOD", "soldiers", "soldier", "-2", "0", "0", "0", "at", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["5", "Sainte-Marie", "NP", "Sainte-Marie", "2", "SUFFIX", "soldiers", "soldier", "-3", "0", "0", "4.0", "sainte-marie", "l:81213c12e1", "location", "https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["6", "in", "IN", "in", "2", "TMP", "soldiers", "soldier", "-4", "0", "0", "0", "in", "0", "0", "https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1644", "0", "0", "date", "1"],
                "entityClass": "date",
                "words": [{
                    "type": "word",
                    "indexes": ["7", "1644", "CD", "@card@", "6", "PMOD", "in", "in", "-1", "0", "0", "4.0", "1644", "t:3d5c4ff9ba", "date", "0", "0", "1644", "0", "0", "0", "0", "0", "0", "0", "date", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["8", ",", ",", ",", "2", "P", "soldiers", "soldier", "-6", "0", "0", "0", ",", "0", "0", "0", "0", "1644", "0", "0", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["9", "but", "CC", "but", "2", "COORD", "soldiers", "soldier", "-7", "0", "0", "0", "but", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "many", "JJ", "many", "16", "NMOD", "idea", "idea", "+6", "0", "0", "0", "many", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "of", "IN", "of", "10", "AMOD", "many", "many", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "the", "DT", "the", "13", "NMOD", "Jesuits", "Jesuit", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "Jesuits", "NNS", "Jesuit", "11", "PMOD", "of", "of", "-2", "0", "0", "0", "jesuits", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "resisted", "VVD", "resist", "13", "SUFFIX", "Jesuits", "Jesuit", "-1", "0", "0", "0", "resisted", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "the", "DT", "the", "16", "NMOD", "idea", "idea", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "idea", "NN", "idea", "9", "CONJ", "but", "but", "-7", "0", "0", "0", "idea", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "of", "IN", "of", "16", "NMOD", "idea", "idea", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "a", "DT", "a", "20", "NMOD", "presence", "presence", "+2", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "military", "JJ", "military", "20", "NMOD", "presence", "presence", "+1", "0", "0", "0", "military", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", "presence", "NN", "presence", "17", "PMOD", "of", "of", "-3", "0", "0", "0", "presence", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", ".", "SENT", ".", "20", "SUFFIX", "presence", "presence", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "They", "PP", "they", "0", "ROOT", "0", "0", "0", "0", "0", "0", "they", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "feared", "VVD", "fear", "1", "SUFFIX", "They", "they", "-1", "0", "0", "0", "feared", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "the", "DT", "the", "4", "NMOD", "soldiers", "soldier", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "soldiers", "NNS", "soldier", "5", "SBJ", "would", "would", "+1", "0", "0", "0", "soldiers", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "would", "MD", "would", "1", "OBJ", "They", "they", "-4", "0", "0", "0", "would", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "``", "``", "``", "5", "P", "would", "would", "-1", "0", "0", "0", "``", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "bring", "VV", "bring", "5", "SUFFIX", "would", "would", "-2", "0", "0", "0", "bring", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "the", "DT", "the", "9", "NMOD", "worst", "bad", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "worst", "JJS", "bad", "7", "PRD", "bring", "bring", "-2", "0", "0", "0", "worst", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "of", "IN", "of", "9", "NMOD", "worst", "bad", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Europe", "http://athena3.fit.vutbr.cz/kb/images/freebase/03s3v95.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c2pky.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/01z0kd0.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Europe_orthographic_Caucasus_Urals_boundary_(with_borders).svg", "Europe", "0", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["11", "Europe", "NP", "Europe", "9", "SUFFIX", "worst", "bad", "-2", "0", "0", "4.0", "europe", "l:42dc235f63", "location", "https://en.wikipedia.org/wiki/Europe", "http://athena3.fit.vutbr.cz/kb/images/freebase/03s3v95.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c2pky.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/01z0kd0.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Europe_orthographic_Caucasus_Urals_boundary_(with_borders).svg", "Europe", "0", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["12", "''", "''", "''", "9", "P", "worst", "bad", "-3", "0", "0", "0", "''", "0", "0", "https://en.wikipedia.org/wiki/Europe", "http://athena3.fit.vutbr.cz/kb/images/freebase/03s3v95.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c2pky.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/01z0kd0.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Europe_orthographic_Caucasus_Urals_boundary_(with_borders).svg", "Europe", "0", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["13", "with", "IN", "with", "5", "ADV", "would", "would", "-8", "0", "0", "0", "with", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "them", "PP", "them", "5", "SUFFIX", "would", "would", "-9", "0", "0", "0", "them", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", ".", "SENT", ".", "5", "SUFFIX", "would", "would", "-10", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "The", "DT", "the", "2", "NMOD", "founding", "founding", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "founding", "NN", "founding", "0", "ROOT", "0", "0", "0", "0", "0", "0", "founding", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "of", "IN", "of", "2", "NMOD", "founding", "founding", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "the", "DT", "the", "5", "NMOD", "mission", "mission", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "mission", "NN", "mission", "3", "PMOD", "of", "of", "-2", "0", "0", "0", "mission", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "led", "VVN", "lead", "5", "SUFFIX", "mission", "mission", "-1", "0", "0", "0", "led", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "to", "TO", "to", "2", "NMOD", "founding", "founding", "-5", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "division", "NN", "division", "7", "PMOD", "to", "to", "-1", "0", "0", "0", "division", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "amongst", "IN", "amongst", "2", "NMOD", "founding", "founding", "-7", "0", "0", "0", "amongst", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "the", "DT", "the", "19", "DEP", "to", "to", "+9", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "Wendat", "NP", "Wendat", "10", "SUFFIX", "the", "the", "-1", "https://en.wikipedia.org/wiki/Wyandot_people", "1", "0", "wendat", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", ",", ",", ",", "10", "P", "the", "the", "-2", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "with", "IN", "with", "10", "NMOD", "the", "the", "-3", "0", "0", "0", "with", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "conflict", "NN", "conflict", "13", "PMOD", "with", "with", "-1", "0", "0", "0", "conflict", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "between", "IN", "between", "14", "NMOD", "conflict", "conflict", "-1", "0", "0", "0", "between", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "those", "DT", "those", "15", "PMOD", "between", "between", "-1", "0", "0", "0", "those", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "who", "WP", "who", "19", "DEP", "to", "to", "+2", "0", "0", "0", "who", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "converted", "VVD", "convert", "17", "SUFFIX", "who", "who", "-1", "https://en.wikipedia.org/wiki/Religious_conversion", "1", "0", "converted", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "to", "TO", "to", "9", "SUB", "amongst", "amongst", "-10", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", "Christianity", "NN", "Christianity", "19", "PMOD", "to", "to", "-1", "0", "0", "0", "christianity", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", "and", "CC", "and", "2", "COORD", "founding", "founding", "-19", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "those", "DT", "those", "26", "NMOD", "beliefs", "belief", "+4", "0", "0", "0", "those", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["23", "who", "WP", "who", "26", "NMOD", "beliefs", "belief", "+3", "0", "0", "0", "who", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["24", "maintained", "VVD", "maintain", "23", "SUFFIX", "who", "who", "-1", "0", "0", "0", "maintained", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["25", "traditional", "JJ", "traditional", "26", "NMOD", "beliefs", "belief", "+1", "0", "0", "0", "traditional", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["26", "beliefs", "NNS", "belief", "21", "CONJ", "and", "and", "-5", "0", "0", "0", "beliefs", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["27", ".", "SENT", ".", "26", "SUFFIX", "beliefs", "belief", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Infectious", "JJ", "infectious", "2", "NMOD", "disease", "disease", "+1", "0", "0", "0", "infectious", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "disease", "NN", "disease", "16", "NMOD", "animals", "animal", "+14", "https://en.wikipedia.org/wiki/Infectious_disease", "2", "0", "disease", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", ",", ",", ",", "2", "P", "disease", "disease", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "an", "DT", "an", "6", "NMOD", "result", "result", "+2", "0", "0", "0", "an", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "unintended", "JJ", "unintended", "6", "NMOD", "result", "result", "+1", "0", "0", "0", "unintended", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "result", "NN", "result", "2", "APPO", "disease", "disease", "-4", "0", "0", "0", "result", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "of", "IN", "of", "6", "NMOD", "result", "result", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "first", "JJ", "first", "9", "NMOD", "contact", "contact", "+1", "0", "0", "0", "first", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "contact", "NN", "contact", "7", "PMOD", "of", "of", "-2", "0", "0", "0", "contact", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "between", "IN", "between", "9", "NMOD", "contact", "contact", "-1", "0", "0", "0", "between", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "the", "DT", "the", "12", "NMOD", "Jesuits", "Jesuit", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "Jesuits", "NNS", "Jesuit", "10", "PMOD", "between", "between", "-2", "0", "0", "0", "jesuits", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", ",", ",", ",", "16", "NMOD", "animals", "animal", "+3", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "their", "PP$", "their", "13", "SUFFIX", ",", ",", "-1", "0", "0", "0", "their", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "farm", "NN", "farm", "16", "NMOD", "animals", "animal", "+1", "0", "0", "0", "farm", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "animals", "NNS", "animal", "25", "NMOD", "gap", "gap", "+9", "0", "0", "0", "animals", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "and", "CC", "and", "16", "COORD", "animals", "animal", "-1", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "the", "DT", "the", "22", "DEP", "to", "to", "+4", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "Wendat", "NP", "Wendat", "18", "SUFFIX", "the", "the", "-1", "0", "0", "0", "wendat", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", ",", ",", ",", "18", "P", "the", "the", "-2", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", "served", "VVD", "serve", "18", "SUFFIX", "the", "the", "-3", "0", "0", "0", "served", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "to", "TO", "to", "16", "NMOD", "animals", "animal", "-6", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["23", "further", "VV", "further", "16", "SUFFIX", "animals", "animal", "-7", "0", "0", "0", "further", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["24", "the", "DT", "the", "25", "NMOD", "gap", "gap", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["25", "gap", "NN", "gap", "0", "ROOT", "0", "0", "0", "0", "0", "0", "gap", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["26", "between", "IN", "between", "25", "NMOD", "gap", "gap", "-1", "0", "0", "0", "between", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["27", "the", "DT", "the", "28", "NMOD", "traditional", "traditional", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["28", "traditional", "JJ", "traditional", "26", "PMOD", "between", "between", "-2", "0", "0", "0", "traditional", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["29", "Wendat", "NP", "Wendat", "28", "SUFFIX", "traditional", "traditional", "-1", "0", "0", "0", "wendat", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["30", "and", "CC", "and", "28", "COORD", "traditional", "traditional", "-2", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["31", "the", "DT", "the", "32", "NMOD", "missionaries", "missionary", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["32", "missionaries", "NNS", "missionary", "30", "CONJ", "and", "and", "-2", "0", "0", "0", "missionaries", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["33", ".", "SENT", ".", "32", "SUFFIX", "missionaries", "missionary", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Epidemics", "NNS", "epidemic", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/Epidemics", "1", "0", "epidemics", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "of", "IN", "of", "1", "NMOD", "Epidemics", "epidemic", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "smallpox", "NN", "smallpox", "2", "PMOD", "of", "of", "-1", "https://en.wikipedia.org/wiki/Smallpox", "1", "0", "smallpox", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", ",", ",", ",", "3", "P", "smallpox", "smallpox", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "which", "WDT", "which", "10", "SBJ", "were", "be", "+5", "0", "0", "0", "which", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "raged", "VVD", "rage", "5", "SUFFIX", "which", "which", "-1", "0", "0", "0", "raged", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "from", "IN", "from", "5", "DIR", "which", "which", "-2", "0", "0", "0", "from", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1634", "0", "0", "1640", "0", "0", "interval", "1"],
                "entityClass": "interval",
                "words": [{
                    "type": "word",
                    "indexes": ["8", "1634-1640", "CD", "@card@", "7", "PMOD", "from", "from", "-1", "0", "0", "4.0", "1634-1640", "v:bdc82a3c0f", "interval", "0", "0", "1634", "0", "0", "1640", "0", "0", "0", "0", "interval", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["9", ",", ",", ",", "5", "P", "which", "which", "-4", "0", "0", "0", ",", "0", "0", "0", "0", "1634", "0", "0", "1640", "0", "0", "0", "0", "interval", "-1"]
            }, {
                "type": "word",
                "indexes": ["10", "were", "VBD", "be", "3", "NMOD", "smallpox", "smallpox", "-7", "0", "0", "0", "were", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "brought", "VVN", "bring", "10", "OBJ", "were", "be", "-1", "0", "0", "0", "brought", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "by", "IN", "by", "10", "EXT", "were", "be", "-2", "0", "0", "0", "by", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "the", "DT", "the", "15", "NMOD", "number", "number", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "increased", "VVN", "increase", "13", "SUFFIX", "the", "the", "-1", "0", "0", "0", "increased", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "number", "NN", "number", "12", "PMOD", "by", "by", "-3", "0", "0", "0", "number", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "of", "IN", "of", "15", "NMOD", "number", "number", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "children", "NNS", "child", "16", "PMOD", "of", "of", "-1", "0", "0", "0", "children", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "emigrating", "VVG", "emigrate", "15", "SUFFIX", "number", "number", "-3", "0", "0", "0", "emigrating", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "to", "TO", "to", "10", "ADV", "were", "be", "-9", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", "the", "DT", "the", "21", "NMOD", "colonies", "colony", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", "colonies", "NNS", "colony", "19", "PMOD", "to", "to", "-2", "0", "0", "0", "colonies", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "with", "IN", "with", "21", "NMOD", "colonies", "colony", "-1", "0", "0", "0", "with", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["23", "families", "NNS", "family", "22", "PMOD", "with", "with", "-1", "0", "0", "0", "families", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["24", "from", "IN", "from", "23", "NMOD", "families", "family", "-1", "0", "0", "0", "from", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["25", "cities", "NNS", "city", "24", "PMOD", "from", "from", "-1", "0", "0", "0", "cities", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["26", "where", "WRB", "where", "29", "LOC", "was", "be", "+3", "0", "0", "0", "where", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["27", "the", "DT", "the", "28", "NMOD", "disease", "disease", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["28", "disease", "NN", "disease", "29", "SBJ", "was", "be", "+1", "0", "0", "0", "disease", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["29", "was", "VBD", "be", "25", "NMOD", "cities", "city", "-4", "0", "0", "0", "was", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["30", "endemic", "JJ", "endemic", "29", "PRD", "was", "be", "-1", "https://en.wikipedia.org/wiki/Endemic_(epidemiology)", "1", "0", "endemic", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["31", "in", "IN", "in", "29", "LOC", "was", "be", "-2", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_France.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "Republic_of_France", "France", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["32", "France", "NP", "France", "31", "SUFFIX", "in", "in", "-1", "0", "0", "4.0", "france", "l:17eddfc4a7", "location", "https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_France.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "Republic_of_France", "France", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["33", ",", ",", ",", "29", "P", "was", "be", "-4", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_France.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "Republic_of_France", "France", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/England", "http://athena3.fit.vutbr.cz/kb/images/freebase/02nbhpf.jpg", "England", "United_Kingdom", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["34", "England", "NP", "England", "33", "SUFFIX", ",", ",", "-1", "https://en.wikipedia.org/wiki/England", "1", "4.1", "england", "l:73ce21e1a1", "location", "https://en.wikipedia.org/wiki/England", "http://athena3.fit.vutbr.cz/kb/images/freebase/02nbhpf.jpg", "England", "United_Kingdom", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["35", "and", "CC", "and", "29", "COORD", "was", "be", "-6", "0", "0", "0", "and", "0", "0", "https://en.wikipedia.org/wiki/England", "http://athena3.fit.vutbr.cz/kb/images/freebase/02nbhpf.jpg", "England", "United_Kingdom", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["36", "the", "DT", "the", "1", "NMOD", "Epidemics", "epidemic", "-35", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Netherlands", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_the_Netherlands.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/05pqvsv.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/029nndj.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03cbt81.jpg", "Kingdom_of_the_Netherlands", "Netherlands", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["37", "Netherlands", "NPS", "Netherlands", "36", "SUFFIX", "the", "the", "-1", "https://en.wikipedia.org/wiki/Netherlands", "1", "4.1", "netherlands", "l:fcfc299ba0", "location", "https://en.wikipedia.org/wiki/Netherlands", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_the_Netherlands.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/05pqvsv.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/029nndj.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03cbt81.jpg", "Kingdom_of_the_Netherlands", "Netherlands", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["38", ".", "SENT", ".", "36", "SUFFIX", "the", "the", "-2", "0", "0", "0", ".", "0", "0", "https://en.wikipedia.org/wiki/Netherlands", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_the_Netherlands.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/05pqvsv.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/029nndj.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03cbt81.jpg", "Kingdom_of_the_Netherlands", "Netherlands", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Also", "RB", "also", "2", "PMOD", "during", "during", "+1", "0", "0", "0", "also", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "during", "IN", "during", "0", "ROOT", "0", "0", "0", "0", "0", "0", "during", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "this", "DT", "this", "4", "NMOD", "time", "time", "+1", "0", "0", "0", "this", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "time", "NN", "time", "2", "PMOD", "during", "during", "-2", "0", "0", "0", "time", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", ",", ",", ",", "2", "NMOD", "during", "during", "-3", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "the", "DT", "the", "7", "NMOD", "rivalry", "rivalry", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "rivalry", "NN", "rivalry", "2", "NMOD", "during", "during", "-5", "0", "0", "0", "rivalry", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "between", "IN", "between", "7", "NMOD", "rivalry", "rivalry", "-1", "0", "0", "0", "between", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "the", "DT", "the", "14", "DEP", "to", "to", "+5", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "Wendat", "NP", "Wendat", "9", "SUFFIX", "the", "the", "-1", "0", "0", "0", "wendat", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "and", "CC", "and", "9", "COORD", "the", "the", "-2", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Iroquois,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Beadle_County_South_Dakota_Incorporated_and_Unincorporated_areas_Iroquois_Highlighted.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/029fn7x.jpg", "Iroquois", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["12", "Iroquois", "NP", "Iroquois", "9", "SUFFIX", "the", "the", "-3", "https://en.wikipedia.org/wiki/Iroquois", "1", "4.2", "iroquois", "l:7970493be8", "location", "https://en.wikipedia.org/wiki/Iroquois,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Beadle_County_South_Dakota_Incorporated_and_Unincorporated_areas_Iroquois_Highlighted.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/029fn7x.jpg", "Iroquois", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["13", "began", "VVD", "begin", "9", "SUFFIX", "the", "the", "-4", "0", "0", "0", "began", "0", "0", "https://en.wikipedia.org/wiki/Iroquois,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Beadle_County_South_Dakota_Incorporated_and_Unincorporated_areas_Iroquois_Highlighted.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/029fn7x.jpg", "Iroquois", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["14", "to", "TO", "to", "8", "PMOD", "between", "between", "-6", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "reignite", "VV", "reignite", "14", "SUFFIX", "to", "to", "-1", "0", "0", "0", "reignite", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", ".", "SENT", ".", "14", "SUFFIX", "to", "to", "-2", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "The", "DT", "the", "3", "SBJ", "were", "be", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "Wendat", "NP", "Wendat", "1", "SUFFIX", "The", "the", "-1", "0", "0", "0", "wendat", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "were", "VBD", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "were", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "weakened", "VVN", "weaken", "3", "SUFFIX", "were", "be", "-1", "0", "0", "0", "weakened", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "by", "IN", "by", "3", "EXT", "were", "be", "-2", "0", "0", "0", "by", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "their", "PP$", "their", "3", "SUFFIX", "were", "be", "-3", "0", "0", "0", "their", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "internal", "JJ", "internal", "8", "NMOD", "divisions", "division", "+1", "0", "0", "0", "internal", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "divisions", "NNS", "division", "3", "PRD", "were", "be", "-5", "0", "0", "0", "divisions", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "and", "CC", "and", "8", "COORD", "divisions", "division", "-1", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "their", "PP$", "their", "8", "SUFFIX", "divisions", "division", "-2", "0", "0", "0", "their", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "losses", "NNS", "loss", "3", "PRD", "were", "be", "-8", "0", "0", "0", "losses", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "from", "IN", "from", "11", "NMOD", "losses", "loss", "-1", "0", "0", "0", "from", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "conflict", "NN", "conflict", "12", "PMOD", "from", "from", "-1", "0", "0", "0", "conflict", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", ".", "SENT", ".", "13", "SUFFIX", "conflict", "conflict", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "War", "NN", "war", "0", "ROOT", "0", "0", "0", "0", "0", "0", "war", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "and", "CC", "and", "1", "COORD", "War", "war", "-1", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "martyrdom", "NN", "martyrdom", "2", "CONJ", "and", "and", "-1", "0", "0", "0", "martyrdom", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "With", "IN", "with", "12", "ADV", "were", "be", "+11", "0", "0", "0", "with", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Iroquois,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Beadle_County_South_Dakota_Incorporated_and_Unincorporated_areas_Iroquois_Highlighted.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/029fn7x.jpg", "Iroquois", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["2", "Iroquois", "JJ", "Iroquois", "3", "NMOD", "aggression", "aggression", "+1", "https://en.wikipedia.org/wiki/Iroquois", "1", "4.2", "iroquois", "l:7970493be8", "location", "https://en.wikipedia.org/wiki/Iroquois,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Beadle_County_South_Dakota_Incorporated_and_Unincorporated_areas_Iroquois_Highlighted.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/029fn7x.jpg", "Iroquois", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["3", "aggression", "NN", "aggression", "1", "PMOD", "With", "with", "-2", "0", "0", "0", "aggression", "0", "0", "https://en.wikipedia.org/wiki/Iroquois,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Beadle_County_South_Dakota_Incorporated_and_Unincorporated_areas_Iroquois_Highlighted.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/029fn7x.jpg", "Iroquois", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["4", "on", "IN", "on", "3", "NMOD", "aggression", "aggression", "-1", "0", "0", "0", "on", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "the", "DT", "the", "6", "NMOD", "rise", "rise", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "rise", "NN", "rise", "4", "PMOD", "on", "on", "-2", "0", "0", "0", "rise", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", ",", ",", ",", "12", "P", "were", "be", "+5", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "an", "DT", "an", "11", "NMOD", "soldiers", "soldier", "+3", "0", "0", "0", "an", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "additional", "JJ", "additional", "11", "NMOD", "soldiers", "soldier", "+2", "0", "0", "0", "additional", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "six", "CD", "six", "11", "NMOD", "soldiers", "soldier", "+1", "0", "0", "0", "six", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "soldiers", "NNS", "soldier", "12", "SBJ", "were", "be", "+1", "0", "0", "0", "soldiers", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "were", "VBD", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "were", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "dispatched", "VVN", "dispatch", "12", "SUFFIX", "were", "be", "-1", "0", "0", "0", "dispatched", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "from", "IN", "from", "12", "ADV", "were", "be", "-2", "0", "0", "0", "from", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_France.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "Republic_of_France", "France", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["15", "France", "NP", "France", "14", "SUFFIX", "from", "from", "-1", "0", "0", "4.0", "france", "l:17eddfc4a7", "location", "https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_France.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "Republic_of_France", "France", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["16", "in", "IN", "in", "14", "TMP", "from", "from", "-2", "0", "0", "0", "in", "0", "0", "https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_France.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "Republic_of_France", "France", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1649", "0", "0", "date", "1"],
                "entityClass": "date",
                "words": [{
                    "type": "word",
                    "indexes": ["17", "1649", "CD", "@card@", "16", "PMOD", "in", "in", "-1", "0", "0", "4.0", "1649", "t:c4b3d04090", "date", "0", "0", "1649", "0", "0", "0", "0", "0", "0", "0", "date", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["18", ".", "SENT", ".", "16", "SUFFIX", "in", "in", "-2", "0", "0", "0", ".", "0", "0", "0", "0", "1649", "0", "0", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "The", "DT", "the", "4", "NMOD", "nation", "nation", "+3", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "weakened", "JJ", "weakened", "4", "NMOD", "nation", "nation", "+2", "0", "0", "0", "weakened", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "Wendat", "NP", "Wendat", "2", "SUFFIX", "weakened", "weakened", "-1", "0", "0", "0", "wendat", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "nation", "NN", "nation", "5", "SBJ", "was", "be", "+1", "0", "0", "0", "nation", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "was", "VBD", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "was", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "little", "JJ", "little", "7", "NMOD", "match", "match", "+1", "0", "0", "0", "little", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "match", "NN", "match", "5", "PRD", "was", "be", "-2", "0", "0", "0", "match", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "for", "IN", "for", "7", "NMOD", "match", "match", "-1", "0", "0", "0", "for", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "the", "DT", "the", "18", "NMOD", "alliances", "alliance", "+9", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "strengthened", "JJ", "strengthened", "18", "NMOD", "alliances", "alliance", "+8", "0", "0", "0", "strengthened", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Iroquois,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Beadle_County_South_Dakota_Incorporated_and_Unincorporated_areas_Iroquois_Highlighted.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/029fn7x.jpg", "Iroquois", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["11", "Iroquois", "NP", "Iroquois", "10", "SUFFIX", "strengthened", "strengthened", "-1", "0", "0", "4.0", "iroquois", "l:7970493be8", "location", "https://en.wikipedia.org/wiki/Iroquois,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Beadle_County_South_Dakota_Incorporated_and_Unincorporated_areas_Iroquois_Highlighted.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/029fn7x.jpg", "Iroquois", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["12", ",", ",", ",", "10", "P", "strengthened", "strengthened", "-2", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Iroquois,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Beadle_County_South_Dakota_Incorporated_and_Unincorporated_areas_Iroquois_Highlighted.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/029fn7x.jpg", "Iroquois", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["13", "who", "WP", "who", "18", "NMOD", "alliances", "alliance", "+5", "0", "0", "0", "who", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "had", "VHD", "have", "13", "SUFFIX", "who", "who", "-1", "0", "0", "0", "had", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "used", "VVN", "use", "13", "SUFFIX", "who", "who", "-2", "0", "0", "0", "used", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "their", "PP$", "their", "13", "SUFFIX", "who", "who", "-3", "0", "0", "0", "their", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "trading", "NN", "trading", "18", "NMOD", "alliances", "alliance", "+1", "0", "0", "0", "trading", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "alliances", "NNS", "alliance", "8", "PMOD", "for", "for", "-10", "0", "0", "0", "alliances", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "with", "IN", "with", "18", "NMOD", "alliances", "alliance", "-1", "0", "0", "0", "with", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", "the", "DT", "the", "21", "NMOD", "Dutch", "Dutch", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Netherlands", "http://athena3.fit.vutbr.cz/kb/images/freebase/029nndj.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/05pqvsv.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03cbt81.jpg", "Dutch", "Netherlands", "kb", "1"],
                "entityClass": "nationality",
                "words": [{
                    "type": "word",
                    "indexes": ["21", "Dutch", "JJ", "Dutch", "19", "PMOD", "with", "with", "-2", "https://en.wikipedia.org/wiki/Dutch_Republic", "1", "nationality", "dutch", "n:059j2", "nationality", "https://en.wikipedia.org/wiki/Netherlands", "http://athena3.fit.vutbr.cz/kb/images/freebase/029nndj.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/05pqvsv.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03cbt81.jpg", "Dutch", "Netherlands", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["22", "to", "TO", "to", "7", "NMOD", "match", "match", "-15", "0", "0", "0", "to", "0", "0", "https://en.wikipedia.org/wiki/Netherlands", "http://athena3.fit.vutbr.cz/kb/images/freebase/029nndj.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/05pqvsv.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03cbt81.jpg", "Dutch", "Netherlands", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["23", "gain", "VV", "gain", "7", "SUFFIX", "match", "match", "-16", "0", "0", "0", "gain", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["24", "firearms", "NNS", "firearm", "5", "PRD", "was", "be", "-19", "0", "0", "0", "firearms", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["25", ".", "SENT", ".", "24", "SUFFIX", "firearms", "firearm", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Eight", "CD", "Eight", "0", "ROOT", "0", "0", "0", "0", "0", "0", "eight", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "of", "IN", "of", "1", "NMOD", "Eight", "Eight", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "the", "DT", "the", "4", "NMOD", "missionaries—St", "missionaries—St", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "missionaries—St", "NN", "missionaries—St", "2", "PMOD", "of", "of", "-2", "0", "0", "0", "missionaries—st", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", ".", "SENT", ".", "4", "SUFFIX", "missionaries—St", "missionaries—St", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Jean", "NP", "Jean", "0", "ROOT", "0", "0", "0", "0", "0", "0", "jean", "p:fbb09f2520", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "de", "NP", "de", "1", "SUFFIX", "Jean", "Jean", "-1", "0", "0", "0", "de", "p:fbb09f2520", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "3"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["3", "Brébeuf", "NP", "Brébeuf", "1", "SUFFIX", "Jean", "Jean", "-2", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "3", "4.1", "brébeuf", "p:fbb09f2520", "person", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["4", "(", "(", "(", "5", "P", "1649", "@card@", "+1", "0", "0", "0", "(", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["5", "1649|GGG", "CD", "@card@", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "1649", "t:2045f26e27", "date", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["6", ")", ")", ")", "5", "P", "1649", "@card@", "-1", "0", "0", "0", ")", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["7", ",", ",", ",", "5", "P", "1649", "@card@", "-2", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "St.", "NP", "St.", "5", "SUFFIX", "1649", "@card@", "-3", "0", "0", "0", "st.", "p:4c877d413b", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "Noël", "NP", "Noël", "5", "SUFFIX", "1649", "@card@", "-4", "0", "0", "0", "noël", "p:4c877d413b", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Noël_Chabanel", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Noel_Chabanel", "M", "France", "1613_02_02", "Sainte_Marie_among_the_Hurons", "1649_12_08", "0", "French|Canadian", "kb", "3"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["10", "Chabanel", "NP", "Chabanel", "5", "SUFFIX", "1649", "@card@", "-5", "https://en.wikipedia.org/wiki/Noël_Chabanel", "2", "4.1", "chabanel", "p:4c877d413b", "person", "https://en.wikipedia.org/wiki/Noël_Chabanel", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Noel_Chabanel", "M", "France", "1613_02_02", "Sainte_Marie_among_the_Hurons", "1649_12_08", "0", "French|Canadian", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["11", "(", "(", "(", "12", "P", "1649", "@card@", "+1", "0", "0", "0", "(", "0", "0", "https://en.wikipedia.org/wiki/Noël_Chabanel", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Noel_Chabanel", "M", "France", "1613_02_02", "Sainte_Marie_among_the_Hurons", "1649_12_08", "0", "French|Canadian", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["12", "1649|GGG", "CD", "@card@", "5", "PRN", "1649", "@card@", "-7", "0", "0", "4.0", "1649", "t:62bbfb7940", "date", "https://en.wikipedia.org/wiki/Noël_Chabanel", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Noel_Chabanel", "M", "France", "1613_02_02", "Sainte_Marie_among_the_Hurons", "1649_12_08", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["13", ")", ")", ")", "12", "P", "1649", "@card@", "-1", "0", "0", "0", ")", "0", "0", "https://en.wikipedia.org/wiki/Noël_Chabanel", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Noel_Chabanel", "M", "France", "1613_02_02", "Sainte_Marie_among_the_Hurons", "1649_12_08", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["14", ",", ",", ",", "5", "P", "1649", "@card@", "-9", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "St.", "NP", "St.", "5", "SUFFIX", "1649", "@card@", "-10", "0", "0", "0", "st.", "p:35bb6a9697", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "Antoine", "NP", "Antoine", "5", "SUFFIX", "1649", "@card@", "-11", "0", "0", "0", "antoine", "p:35bb6a9697", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Antoine_Daniel", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Antoine_Daniel", "M", "Dieppe", "1601_05_27", "Springwater", "1648_07_04", "0", "French|Canadian", "kb", "3"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["17", "Daniel", "NP", "Daniel", "5", "SUFFIX", "1649", "@card@", "-12", "https://en.wikipedia.org/wiki/Antoine_Daniel", "2", "4.1", "daniel", "p:35bb6a9697", "person", "https://en.wikipedia.org/wiki/Antoine_Daniel", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Antoine_Daniel", "M", "Dieppe", "1601_05_27", "Springwater", "1648_07_04", "0", "French|Canadian", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["18", "(", "(", "(", "19", "P", "1648", "@card@", "+1", "0", "0", "0", "(", "0", "0", "https://en.wikipedia.org/wiki/Antoine_Daniel", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Antoine_Daniel", "M", "Dieppe", "1601_05_27", "Springwater", "1648_07_04", "0", "French|Canadian", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["19", "1648|GGG", "CD", "@card@", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "1648", "t:37fd4a722a", "date", "https://en.wikipedia.org/wiki/Antoine_Daniel", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Antoine_Daniel", "M", "Dieppe", "1601_05_27", "Springwater", "1648_07_04", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["20", ")", ")", ")", "19", "P", "1648", "@card@", "-1", "0", "0", "0", ")", "0", "0", "https://en.wikipedia.org/wiki/Antoine_Daniel", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Antoine_Daniel", "M", "Dieppe", "1601_05_27", "Springwater", "1648_07_04", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["21", ",", ",", ",", "19", "P", "1648", "@card@", "-2", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "St.", "NP", "St.", "19", "SUFFIX", "1648", "@card@", "-3", "0", "0", "0", "st.", "p:44632bcc32", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["23", "Charles", "NP", "Charles", "19", "SUFFIX", "1648", "@card@", "-4", "0", "0", "0", "charles", "p:44632bcc32", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Charles_Garnier_(missionary)", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Charles_Garnier", "M", "Paris", "1606_05_25", "Sainte_Marie_among_the_Hurons", "1649_12_07", "0", "French|Canadian", "kb", "3"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["24", "Garnier", "NP", "Garnier", "19", "SUFFIX", "1648", "@card@", "-5", "https://en.wikipedia.org/wiki/Saint_Charles_Garnier", "2", "4.2", "garnier", "p:44632bcc32", "person", "https://en.wikipedia.org/wiki/Charles_Garnier_(missionary)", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Charles_Garnier", "M", "Paris", "1606_05_25", "Sainte_Marie_among_the_Hurons", "1649_12_07", "0", "French|Canadian", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["25", "(", "(", "(", "26", "P", "1649", "@card@", "+1", "0", "0", "0", "(", "0", "0", "https://en.wikipedia.org/wiki/Charles_Garnier_(missionary)", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Charles_Garnier", "M", "Paris", "1606_05_25", "Sainte_Marie_among_the_Hurons", "1649_12_07", "0", "French|Canadian", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["26", "1649|GGG", "CD", "@card@", "19", "PRN", "1648", "@card@", "-7", "0", "0", "4.0", "1649", "t:ecbcdf6969", "date", "https://en.wikipedia.org/wiki/Charles_Garnier_(missionary)", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Charles_Garnier", "M", "Paris", "1606_05_25", "Sainte_Marie_among_the_Hurons", "1649_12_07", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["27", ")", ")", ")", "26", "P", "1649", "@card@", "-1", "0", "0", "0", ")", "0", "0", "https://en.wikipedia.org/wiki/Charles_Garnier_(missionary)", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Charles_Garnier", "M", "Paris", "1606_05_25", "Sainte_Marie_among_the_Hurons", "1649_12_07", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["28", ",", ",", ",", "19", "P", "1648", "@card@", "-9", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["29", "St.", "NP", "St.", "19", "SUFFIX", "1648", "@card@", "-10", "0", "0", "0", "st.", "p:17a8bd654b", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["30", "René", "NP", "René", "19", "SUFFIX", "1648", "@card@", "-11", "0", "0", "0", "rené", "p:17a8bd654b", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/René_Goupil", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Rene_Goupil", "M", "Saint_Martin_du_Bois__Maine_et_Loire", "1608_05_16", "Auriesville", "1642_09_23", "0", "French|Canadian", "kb", "3"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["31", "Goupil", "NP", "Goupil", "19", "SUFFIX", "1648", "@card@", "-12", "https://en.wikipedia.org/wiki/René_Goupil", "2", "4.1", "goupil", "p:17a8bd654b", "person", "https://en.wikipedia.org/wiki/René_Goupil", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Rene_Goupil", "M", "Saint_Martin_du_Bois__Maine_et_Loire", "1608_05_16", "Auriesville", "1642_09_23", "0", "French|Canadian", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["32", "(", "(", "(", "33", "P", "1642", "@card@", "+1", "0", "0", "0", "(", "0", "0", "https://en.wikipedia.org/wiki/René_Goupil", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Rene_Goupil", "M", "Saint_Martin_du_Bois__Maine_et_Loire", "1608_05_16", "Auriesville", "1642_09_23", "0", "French|Canadian", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["33", "1642|GGG", "CD", "@card@", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "1642", "t:a7384c8262", "date", "https://en.wikipedia.org/wiki/René_Goupil", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Rene_Goupil", "M", "Saint_Martin_du_Bois__Maine_et_Loire", "1608_05_16", "Auriesville", "1642_09_23", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["34", ")", ")", ")", "33", "P", "1642", "@card@", "-1", "0", "0", "0", ")", "0", "0", "https://en.wikipedia.org/wiki/René_Goupil", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Rene_Goupil", "M", "Saint_Martin_du_Bois__Maine_et_Loire", "1608_05_16", "Auriesville", "1642_09_23", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["35", ",", ",", ",", "33", "P", "1642", "@card@", "-2", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["36", "St.", "NP", "St.", "33", "SUFFIX", "1642", "@card@", "-3", "0", "0", "0", "st.", "p:e91daa8a97", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["37", "Isaac", "NP", "Isaac", "33", "SUFFIX", "1642", "@card@", "-4", "0", "0", "0", "isaac", "p:e91daa8a97", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Isaac_Jogues", "http://athena3.fit.vutbr.cz/kb/images/freebase/02d8lzc.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03tcwqj.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/St-isaac-jogues_(1).jpg", "Isaac_Jogues", "M", "Orleans", "1607_01_10", "Auriesville", "1646_10_18", "0", "American|French|Canadian", "kb", "3"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["38", "Jogues", "NP", "Jogues", "33", "SUFFIX", "1642", "@card@", "-5", "https://en.wikipedia.org/wiki/Isaac_Jogues", "2", "4.1", "jogues", "p:e91daa8a97", "person", "https://en.wikipedia.org/wiki/Isaac_Jogues", "http://athena3.fit.vutbr.cz/kb/images/freebase/02d8lzc.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03tcwqj.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/St-isaac-jogues_(1).jpg", "Isaac_Jogues", "M", "Orleans", "1607_01_10", "Auriesville", "1646_10_18", "0", "American|French|Canadian", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["39", "(", "(", "(", "40", "P", "1646", "@card@", "+1", "0", "0", "0", "(", "0", "0", "https://en.wikipedia.org/wiki/Isaac_Jogues", "http://athena3.fit.vutbr.cz/kb/images/freebase/02d8lzc.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03tcwqj.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/St-isaac-jogues_(1).jpg", "Isaac_Jogues", "M", "Orleans", "1607_01_10", "Auriesville", "1646_10_18", "0", "American|French|Canadian", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["40", "1646|GGG", "CD", "@card@", "33", "PRN", "1642", "@card@", "-7", "0", "0", "4.0", "1646", "t:383ac9bd7b", "date", "https://en.wikipedia.org/wiki/Isaac_Jogues", "http://athena3.fit.vutbr.cz/kb/images/freebase/02d8lzc.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03tcwqj.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/St-isaac-jogues_(1).jpg", "Isaac_Jogues", "M", "Orleans", "1607_01_10", "Auriesville", "1646_10_18", "0", "American|French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["41", ")", ")", ")", "40", "P", "1646", "@card@", "-1", "0", "0", "0", ")", "0", "0", "https://en.wikipedia.org/wiki/Isaac_Jogues", "http://athena3.fit.vutbr.cz/kb/images/freebase/02d8lzc.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03tcwqj.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/St-isaac-jogues_(1).jpg", "Isaac_Jogues", "M", "Orleans", "1607_01_10", "Auriesville", "1646_10_18", "0", "American|French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["42", ",", ",", ",", "33", "P", "1642", "@card@", "-9", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["43", "St.", "NP", "St.", "33", "SUFFIX", "1642", "@card@", "-10", "0", "0", "0", "st.", "p:232e36e72a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["44", "Jean", "NP", "Jean", "33", "SUFFIX", "1642", "@card@", "-11", "0", "0", "0", "jean", "p:232e36e72a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["45", "de", "NP", "de", "33", "SUFFIX", "1642", "@card@", "-12", "0", "0", "0", "de", "p:232e36e72a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Jean_de_Lalande", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Jean_de_Lalande", "M", "Dieppe", "0", "Auriesville", "1646_10_18", "0", "French|Canadian", "kb", "4"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["46", "Lalande", "NP", "Lalande", "33", "SUFFIX", "1642", "@card@", "-13", "https://en.wikipedia.org/wiki/Jean_de_Lalande", "3", "4.1", "lalande", "p:232e36e72a", "person", "https://en.wikipedia.org/wiki/Jean_de_Lalande", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Jean_de_Lalande", "M", "Dieppe", "0", "Auriesville", "1646_10_18", "0", "French|Canadian", "kb", "4"]
                }, {
                    "type": "word",
                    "indexes": ["47", "(", "(", "(", "48", "P", "1646", "@card@", "+1", "0", "0", "0", "(", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Lalande", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Jean_de_Lalande", "M", "Dieppe", "0", "Auriesville", "1646_10_18", "0", "French|Canadian", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["48", "1646|GGG", "CD", "@card@", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "1646", "t:568a6dac54", "date", "https://en.wikipedia.org/wiki/Jean_de_Lalande", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Jean_de_Lalande", "M", "Dieppe", "0", "Auriesville", "1646_10_18", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["49", ")", ")", ")", "48", "P", "1646", "@card@", "-1", "0", "0", "0", ")", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Lalande", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Jean_de_Lalande", "M", "Dieppe", "0", "Auriesville", "1646_10_18", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["50", ",", ",", ",", "48", "P", "1646", "@card@", "-2", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Lalande", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_American_Martyrs.jpg", "Jean_de_Lalande", "M", "Dieppe", "0", "Auriesville", "1646_10_18", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["51", "and", "CC", "and", "48", "COORD", "1646", "@card@", "-3", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["52", "St.", "NP", "St.", "48", "SUFFIX", "1646", "@card@", "-4", "0", "0", "0", "st.", "p:82bac2b938", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["53", "Gabriel", "NP", "Gabriel", "48", "SUFFIX", "1646", "@card@", "-5", "0", "0", "0", "gabriel", "p:82bac2b938", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Gabriel_Lalemant", "http://athena3.fit.vutbr.cz/kb/images/freebase/02g4f8c.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/SOJ_Saint_Gabriel-Lallemant.jpg", "Gabriel_Lalemant", "M", "Paris", "1610_10_03", "Sainte_Marie_among_the_Hurons", "1649_05_17", "0", "French|Canadian", "kb", "3"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["54", "Lallemant", "NP", "Lallemant", "48", "SUFFIX", "1646", "@card@", "-6", "https://en.wikipedia.org/wiki/Gabriel_Lallemant", "2", "4.2", "lallemant", "p:82bac2b938", "person", "https://en.wikipedia.org/wiki/Gabriel_Lalemant", "http://athena3.fit.vutbr.cz/kb/images/freebase/02g4f8c.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/SOJ_Saint_Gabriel-Lallemant.jpg", "Gabriel_Lalemant", "M", "Paris", "1610_10_03", "Sainte_Marie_among_the_Hurons", "1649_05_17", "0", "French|Canadian", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["55", "(", "(", "(", "56", "P", "1649", "@card@", "+1", "0", "0", "0", "(", "0", "0", "https://en.wikipedia.org/wiki/Gabriel_Lalemant", "http://athena3.fit.vutbr.cz/kb/images/freebase/02g4f8c.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/SOJ_Saint_Gabriel-Lallemant.jpg", "Gabriel_Lalemant", "M", "Paris", "1610_10_03", "Sainte_Marie_among_the_Hurons", "1649_05_17", "0", "French|Canadian", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["56", "1649|GGG", "CD", "@card@", "48", "PRN", "1646", "@card@", "-8", "0", "0", "4.0", "1649", "t:ffd8d3a11d", "date", "https://en.wikipedia.org/wiki/Gabriel_Lalemant", "http://athena3.fit.vutbr.cz/kb/images/freebase/02g4f8c.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/SOJ_Saint_Gabriel-Lallemant.jpg", "Gabriel_Lalemant", "M", "Paris", "1610_10_03", "Sainte_Marie_among_the_Hurons", "1649_05_17", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["57", ")", ")", ")", "56", "P", "1649", "@card@", "-1", "0", "0", "0", ")", "0", "0", "https://en.wikipedia.org/wiki/Gabriel_Lalemant", "http://athena3.fit.vutbr.cz/kb/images/freebase/02g4f8c.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/SOJ_Saint_Gabriel-Lallemant.jpg", "Gabriel_Lalemant", "M", "Paris", "1610_10_03", "Sainte_Marie_among_the_Hurons", "1649_05_17", "0", "French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["58", "—were", "NN", "—were", "0", "ROOT", "0", "0", "0", "0", "0", "0", "—were", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["59", "martyred", "VVN", "martyr", "58", "SUFFIX", "—were", "—were", "-1", "0", "0", "0", "martyred", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["60", "in", "IN", "in", "58", "LOC", "—were", "—were", "-2", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["61", "the", "DT", "the", "58", "NMOD", "—were", "—were", "-3", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["62", "Huron-Iroquois", "NP", "Huron-Iroquois", "61", "SUFFIX", "the", "the", "-1", "0", "0", "4.0", "huron-iroquois", "l:163a24c73c", "location", "https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["63", "War", "NP", "War", "61", "SUFFIX", "the", "the", "-2", "https://en.wikipedia.org/wiki/Huron-Iroquois_War", "2", "4.2", "war", "e:0gkxk23", "event", "https://en.wikipedia.org/wiki/Huron—Bruce", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Huron-Bruce.png|http://athena3.fit.vutbr.cz/kb/images/freebase/02fnz00.jpg", "Huron_Bruce", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["64", ".", "SENT", ".", "61", "SUFFIX", "the", "the", "-3", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Owing", "VVG", "owe", "0", "ROOT", "0", "0", "0", "0", "0", "0", "owing", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "to", "TO", "to", "1", "ADV", "Owing", "owe", "-1", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "the", "DT", "the", "4", "NMOD", "proximity", "proximity", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "proximity", "NN", "proximity", "2", "PMOD", "to", "to", "-2", "0", "0", "0", "proximity", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "of", "IN", "of", "4", "NMOD", "proximity", "proximity", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "their", "PP$", "their", "4", "SUFFIX", "proximity", "proximity", "-2", "0", "0", "0", "their", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "deaths", "NNS", "death", "0", "ROOT", "0", "0", "0", "0", "0", "0", "deaths", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "to", "TO", "to", "7", "NMOD", "deaths", "death", "-1", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["9", "Sainte-Marie", "NP", "Sainte-Marie", "8", "SUFFIX", "to", "to", "-1", "0", "0", "4.0", "sainte-marie", "l:81213c12e1", "location", "https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["10", ",", ",", ",", "7", "NMOD", "deaths", "death", "-3", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["11", "the", "DT", "the", "15", "NMOD", "bodies", "body", "+4", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/freebase/0k3rnwg.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "French", "France", "kb", "1"],
                "entityClass": "nationality",
                "words": [{
                    "type": "word",
                    "indexes": ["12", "French", "NP", "French", "11", "SUFFIX", "the", "the", "-1", "0", "0", "4.0", "french", "n:0f8l9c", "nationality", "https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/freebase/0k3rnwg.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "French", "France", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["13", "recovered", "VVD", "recover", "11", "SUFFIX", "the", "the", "-2", "0", "0", "0", "recovered", "0", "0", "https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/freebase/0k3rnwg.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "French", "France", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["14", "the", "DT", "the", "15", "NMOD", "bodies", "body", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "bodies", "NNS", "body", "7", "NMOD", "deaths", "death", "-8", "0", "0", "0", "bodies", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "of", "IN", "of", "15", "NMOD", "bodies", "body", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "coref", "1"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["17", "Brébeuf", "NP", "Brébeuf", "15", "SUFFIX", "bodies", "body", "-2", "0", "0", "2", "brébeuf", "p:fbb09f2520", "person", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "coref", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["18", "and", "CC", "and", "15", "COORD", "bodies", "body", "-3", "0", "0", "0", "and", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "coref", "-1"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Gabriel_Lalemant", "http://athena3.fit.vutbr.cz/kb/images/freebase/02g4f8c.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/SOJ_Saint_Gabriel-Lallemant.jpg", "Gabriel_Lalemant", "M", "Paris", "1610_10_03", "Sainte_Marie_among_the_Hurons", "1649_05_17", "0", "French|Canadian", "coref", "1"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["19", "Lallemant", "NP", "Lallemant", "15", "SUFFIX", "bodies", "body", "-4", "0", "0", "2", "lallemant", "p:82bac2b938", "person", "https://en.wikipedia.org/wiki/Gabriel_Lalemant", "http://athena3.fit.vutbr.cz/kb/images/freebase/02g4f8c.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/SOJ_Saint_Gabriel-Lallemant.jpg", "Gabriel_Lalemant", "M", "Paris", "1610_10_03", "Sainte_Marie_among_the_Hurons", "1649_05_17", "0", "French|Canadian", "coref", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["20", "to", "TO", "to", "15", "NMOD", "bodies", "body", "-5", "0", "0", "0", "to", "0", "0", "https://en.wikipedia.org/wiki/Gabriel_Lalemant", "http://athena3.fit.vutbr.cz/kb/images/freebase/02g4f8c.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/SOJ_Saint_Gabriel-Lallemant.jpg", "Gabriel_Lalemant", "M", "Paris", "1610_10_03", "Sainte_Marie_among_the_Hurons", "1649_05_17", "0", "French|Canadian", "coref", "-1"]
            }, {
                "type": "word",
                "indexes": ["21", "be", "VB", "be", "20", "IM", "to", "to", "-1", "0", "0", "0", "be", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "buried", "VVN", "bury", "21", "OBJ", "be", "be", "-1", "0", "0", "0", "buried", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["23", "at", "IN", "at", "21", "ADV", "be", "be", "-2", "0", "0", "0", "at", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["24", "the", "DT", "the", "25", "NMOD", "mission", "mission", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["25", "mission", "NN", "mission", "23", "PMOD", "at", "at", "-2", "0", "0", "0", "mission", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["26", ".", "SENT", ".", "25", "SUFFIX", "mission", "mission", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Burning", "NN", "burning", "0", "ROOT", "0", "0", "0", "0", "0", "0", "burning", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "of", "IN", "of", "1", "NMOD", "Burning", "burning", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["3", "Sainte-Marie", "NP", "Sainte-Marie", "1", "SUFFIX", "Burning", "burning", "-2", "0", "0", "4.0", "sainte-marie", "l:81213c12e1", "location", "https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Brébeuf", "NP", "Brébeuf", "0", "ROOT", "0", "0", "0", "0", "0", "2", "brébeuf", "p:fbb09f2520", "person", "https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "and", "CC", "and", "1", "COORD", "Brébeuf", "Brébeuf", "-1", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Gabriel_Lalemant", "http://athena3.fit.vutbr.cz/kb/images/freebase/02g4f8c.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/SOJ_Saint_Gabriel-Lallemant.jpg", "Gabriel_Lalemant", "M", "Paris", "1610_10_03", "Sainte_Marie_among_the_Hurons", "1649_05_17", "0", "French|Canadian", "coref", "1"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["3", "Lallemant", "NP", "Lallemant", "1", "SUFFIX", "Brébeuf", "Brébeuf", "-2", "0", "0", "2", "lallemant", "p:82bac2b938", "person", "https://en.wikipedia.org/wiki/Gabriel_Lalemant", "http://athena3.fit.vutbr.cz/kb/images/freebase/02g4f8c.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/SOJ_Saint_Gabriel-Lallemant.jpg", "Gabriel_Lalemant", "M", "Paris", "1610_10_03", "Sainte_Marie_among_the_Hurons", "1649_05_17", "0", "French|Canadian", "coref", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["4", "stand", "VVP", "stand", "1", "SUFFIX", "Brébeuf", "Brébeuf", "-3", "0", "0", "0", "stand", "0", "0", "https://en.wikipedia.org/wiki/Gabriel_Lalemant", "http://athena3.fit.vutbr.cz/kb/images/freebase/02g4f8c.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/SOJ_Saint_Gabriel-Lallemant.jpg", "Gabriel_Lalemant", "M", "Paris", "1610_10_03", "Sainte_Marie_among_the_Hurons", "1649_05_17", "0", "French|Canadian", "coref", "-1"]
            }, {
                "type": "word",
                "indexes": ["5", "ready", "JJ", "ready", "0", "ROOT", "0", "0", "0", "0", "0", "0", "ready", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "for", "IN", "for", "5", "AMOD", "ready", "ready", "-1", "0", "0", "0", "for", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "boiling", "VVG", "boil", "5", "SUFFIX", "ready", "ready", "-2", "0", "0", "0", "boiling", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "water/fire", "NN", "water/fire", "0", "ROOT", "0", "0", "0", "0", "0", "0", "water/fire", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "``", "``", "``", "11", "NMOD", "''", "''", "+2", "0", "0", "0", "``", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "Baptism", "NP", "Baptism", "9", "SUFFIX", "``", "``", "-1", "0", "0", "0", "baptism", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "''", "''", "''", "8", "P", "water/fire", "water/fire", "-3", "0", "0", "0", "''", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", ",", ",", ",", "8", "P", "water/fire", "water/fire", "-4", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "flaying", "VVG", "flay", "8", "SUFFIX", "water/fire", "water/fire", "-5", "0", "0", "0", "flaying", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "On", "IN", "on", "5", "NMOD", "1649", "@card@", "+4", "0", "0", "0", "on", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "June", "NP", "June", "1", "SUFFIX", "On", "on", "-1", "0", "0", "0", "june", "t:160c75d10e", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "16", "CD", "@card@", "2", "NMOD", "June", "June", "-1", "0", "0", "0", "16", "t:160c75d10e", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", ",|GGG", ",", ",", "5", "P", "1649", "@card@", "+1", "0", "0", "0", ",", "t:160c75d10e", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1649", "6", "16", "date", "4"],
                "entityClass": "date",
                "words": [{
                    "type": "word",
                    "indexes": ["5", "1649", "CD", "@card@", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "1649", "t:160c75d10e", "date", "0", "0", "1649", "6", "16", "0", "0", "0", "0", "0", "date", "4"]
                }, {
                    "type": "word",
                    "indexes": ["6", ",", ",", ",", "5", "NMOD", "1649", "@card@", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "1649", "6", "16", "0", "0", "0", "0", "0", "date", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["7", "the", "DT", "the", "8", "NMOD", "missionaries", "missionary", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "1649", "6", "16", "0", "0", "0", "0", "0", "date", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["8", "missionaries", "NNS", "missionary", "13", "NMOD", "mission", "mission", "+5", "0", "0", "0", "missionaries", "0", "0", "0", "0", "1649", "6", "16", "0", "0", "0", "0", "0", "date", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["9", "chose", "VVD", "choose", "8", "SUFFIX", "missionaries", "missionary", "-1", "0", "0", "0", "chose", "0", "0", "0", "0", "1649", "6", "16", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["10", "to", "TO", "to", "8", "NMOD", "missionaries", "missionary", "-2", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "burn", "VV", "burn", "8", "SUFFIX", "missionaries", "missionary", "-3", "0", "0", "0", "burn", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "the", "DT", "the", "13", "NMOD", "mission", "mission", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "mission", "NN", "mission", "18", "SBJ", "being", "be", "+5", "0", "0", "0", "mission", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "rather", "RB", "rather", "15", "DEP", "than", "than", "+1", "0", "0", "0", "rather", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "than", "IN", "than", "13", "NMOD", "mission", "mission", "-2", "0", "0", "0", "than", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "risk", "NN", "risk", "15", "PMOD", "than", "than", "-1", "0", "0", "0", "risk", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "it", "PP", "it", "16", "SUFFIX", "risk", "risk", "-1", "0", "0", "0", "it", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "being", "VBG", "be", "5", "NMOD", "1649", "@card@", "-13", "0", "0", "0", "being", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "desecrated", "VVN", "desecrate", "18", "SUFFIX", "being", "be", "-1", "0", "0", "0", "desecrated", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", "or", "CC", "or", "18", "COORD", "being", "be", "-2", "0", "0", "0", "or", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", "permanently", "RB", "permanently", "20", "CONJ", "or", "or", "-1", "0", "0", "0", "permanently", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "overrun", "VVN", "overrun", "20", "SUFFIX", "or", "or", "-2", "0", "0", "0", "overrun", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["23", "by", "IN", "by", "20", "CONJ", "or", "or", "-3", "0", "0", "0", "by", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Iroquois,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Beadle_County_South_Dakota_Incorporated_and_Unincorporated_areas_Iroquois_Highlighted.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/029fn7x.jpg", "Iroquois", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["24", "Iroquois", "NP", "Iroquois", "20", "SUFFIX", "or", "or", "-4", "0", "0", "4.0", "iroquois", "l:7970493be8", "location", "https://en.wikipedia.org/wiki/Iroquois,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Beadle_County_South_Dakota_Incorporated_and_Unincorporated_areas_Iroquois_Highlighted.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/029fn7x.jpg", "Iroquois", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["25", "in", "IN", "in", "20", "CONJ", "or", "or", "-5", "0", "0", "0", "in", "0", "0", "https://en.wikipedia.org/wiki/Iroquois,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Beadle_County_South_Dakota_Incorporated_and_Unincorporated_areas_Iroquois_Highlighted.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/029fn7x.jpg", "Iroquois", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["26", "further", "JJR", "further", "27", "NMOD", "attacks", "attack", "+1", "0", "0", "0", "further", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["27", "attacks", "NNS", "attack", "25", "PMOD", "in", "in", "-2", "0", "0", "0", "attacks", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["28", ".", "SENT", ".", "27", "SUFFIX", "attacks", "attack", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Fr", "NP", "Fr", "0", "ROOT", "0", "0", "0", "0", "0", "0", "fr", "p:705cc0286b", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", ".|GGG", "SENT", ".", "1", "SUFFIX", "Fr", "Fr", "-1", "0", "0", "0", ".", "p:705cc0286b", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["http://www.freebase.com/m/0115s3z8", "0", "Frantisek_Paul", "M", "Pardubice", "1898_04_28", "Prague", "1976_11_08", "0", "Czech|Austria_Hungary", "kb", "3"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Paul", "NP", "Paul", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "paul", "p:705cc0286b", "person", "http://www.freebase.com/m/0115s3z8", "0", "Frantisek_Paul", "M", "Pardubice", "1898_04_28", "Prague", "1976_11_08", "0", "Czech|Austria_Hungary", "kb", "3"]
                }]
            }, {
                "type": "word",
                "indexes": ["2", "Ragueneau", "NP", "Ragueneau", "1", "SUFFIX", "Paul", "Paul", "-1", "https://en.wikipedia.org/wiki/Paul_Ragueneau", "2", "person", "ragueneau", "l:22e258481e", "location", "http://www.freebase.com/m/0115s3z8", "0", "Frantisek_Paul", "M", "Pardubice", "1898_04_28", "Prague", "1976_11_08", "0", "Czech|Austria_Hungary", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "wrote", "VVD", "write", "2", "SUFFIX", "Ragueneau", "Ragueneau", "-1", "0", "0", "0", "wrote", "0", "0", "http://www.freebase.com/m/0115s3z8", "0", "Frantisek_Paul", "M", "Pardubice", "1898_04_28", "Prague", "1976_11_08", "0", "Czech|Austria_Hungary", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["4", ",", ",", ",", "1", "P", "Paul", "Paul", "-3", "0", "0", "0", ",", "0", "0", "http://www.freebase.com/m/0115s3z8", "0", "Frantisek_Paul", "M", "Pardubice", "1898_04_28", "Prague", "1976_11_08", "0", "Czech|Austria_Hungary", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "``", "``", "``", "5", "NMOD", "fire", "fire", "+4", "0", "0", "0", "``", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "We", "PP", "we", "1", "SUFFIX", "``", "``", "-1", "0", "0", "0", "we", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "ourselves", "PP", "ourselves", "1", "SUFFIX", "``", "``", "-2", "0", "0", "0", "ourselves", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "set", "VVD", "set", "1", "SUFFIX", "``", "``", "-3", "0", "0", "0", "set", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "fire", "NN", "fire", "11", "NMOD", "burn", "burn", "+6", "0", "0", "0", "fire", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "to", "TO", "to", "5", "NMOD", "fire", "fire", "-1", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "it", "PP", "it", "5", "SUFFIX", "fire", "fire", "-2", "0", "0", "0", "it", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", ",", ",", ",", "5", "P", "fire", "fire", "-3", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "and", "CC", "and", "5", "COORD", "fire", "fire", "-4", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "beheld", "VVD", "behold", "5", "SUFFIX", "fire", "fire", "-5", "0", "0", "0", "beheld", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "burn", "NN", "burn", "0", "ROOT", "0", "0", "0", "0", "0", "0", "burn", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "before", "IN", "before", "11", "NMOD", "burn", "burn", "-1", "0", "0", "0", "before", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "our", "PP$", "our", "11", "SUFFIX", "burn", "burn", "-2", "0", "0", "0", "our", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "eyes", "NNS", "eye", "0", "ROOT", "0", "0", "0", "0", "0", "0", "eyes", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "and", "CC", "and", "14", "COORD", "eyes", "eye", "-1", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "in", "IN", "in", "15", "CONJ", "and", "and", "-1", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "less", "JJR", "less", "19", "DEP", "one", "one", "+2", "0", "0", "0", "less", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "than", "IN", "than", "19", "DEP", "one", "one", "+1", "0", "0", "0", "than", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "one", "CD", "one", "20", "NMOD", "hour", "hour", "+1", "0", "0", "0", "one", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", "hour", "NN", "hour", "16", "PMOD", "in", "in", "-4", "0", "0", "0", "hour", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", ",", ",", ",", "14", "P", "eyes", "eye", "-7", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "our", "PP$", "our", "14", "SUFFIX", "eyes", "eye", "-8", "0", "0", "0", "our", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["23", "work", "NN", "work", "0", "ROOT", "0", "0", "0", "0", "0", "0", "work", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["24", "of", "IN", "of", "23", "NMOD", "work", "work", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["25", "nine", "CD", "nine", "28", "NMOD", "years", "year", "+3", "0", "0", "0", "nine", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["26", "or", "CC", "or", "25", "COORD", "nine", "nine", "-1", "0", "0", "0", "or", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["27", "ten", "CD", "ten", "26", "CONJ", "or", "or", "-1", "0", "0", "0", "ten", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["28", "years", "NNS", "year", "24", "PMOD", "of", "of", "-4", "0", "0", "0", "years", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["29", ".", "SENT", ".", "28", "SUFFIX", "years", "year", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["30", "''", "''", "''", "23", "P", "work", "work", "-7", "0", "0", "0", "''", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Before", "IN", "before", "13", "ADV", "would", "would", "+12", "0", "0", "0", "before", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "the", "DT", "the", "3", "NMOD", "burning", "burning", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "burning", "NN", "burning", "1", "PMOD", "Before", "before", "-2", "0", "0", "0", "burning", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", ",", ",", ",", "13", "P", "would", "would", "+9", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "the", "DT", "the", "6", "NMOD", "survivors", "survivor", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "survivors", "NNS", "survivor", "13", "SBJ", "would", "would", "+7", "0", "0", "0", "survivors", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "had", "VHD", "have", "6", "SUFFIX", "survivors", "survivor", "-1", "0", "0", "0", "had", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "decided", "VVN", "decide", "6", "SUFFIX", "survivors", "survivor", "-2", "0", "0", "0", "decided", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "that", "IN/that", "that", "6", "SUFFIX", "survivors", "survivor", "-3", "0", "0", "0", "that", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "coref", "1"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["10", "Brébeuf", "NP", "Brébeuf", "6", "SUFFIX", "survivors", "survivor", "-4", "0", "0", "2", "brébeuf", "p:fbb09f2520", "person", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "coref", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["11", "and", "CC", "and", "6", "COORD", "survivors", "survivor", "-5", "0", "0", "0", "and", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "coref", "-1"]
            }, {
                "type": "word",
                "indexes": ["12", "Lalemant", "NP", "Lalemant", "6", "SUFFIX", "survivors", "survivor", "-6", "0", "0", "0", "lalemant", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "would", "MD", "would", "0", "ROOT", "0", "0", "0", "0", "0", "0", "would", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "be", "VB", "be", "13", "VC", "would", "would", "-1", "0", "0", "0", "be", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "canonized", "VVN", "canonize", "14", "OBJ", "be", "be", "-1", "https://en.wikipedia.org/wiki/Canonization", "1", "0", "canonized", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "as", "IN", "as", "14", "ADV", "be", "be", "-2", "0", "0", "0", "as", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "martyrs", "NNS", "martyr", "16", "PMOD", "as", "as", "-1", "0", "0", "0", "martyrs", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", ".", "SENT", ".", "16", "SUFFIX", "as", "as", "-2", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["http://www.freebase.com/m/04c3qkf", "0", "Shoemaker", "United_States_of_America", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Shoemaker", "NP", "Shoemaker", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "shoemaker", "l:233e45523e", "location", "http://www.freebase.com/m/04c3qkf", "0", "Shoemaker", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["2", "Christophe", "NP", "Christophe", "1", "SUFFIX", "Shoemaker", "Shoemaker", "-1", "0", "0", "0", "christophe", "0", "0", "http://www.freebase.com/m/04c3qkf", "0", "Shoemaker", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "Regnault", "NP", "Regnault", "1", "SUFFIX", "Shoemaker", "Shoemaker", "-2", "0", "0", "0", "regnault", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "had", "VHD", "have", "1", "SUFFIX", "Shoemaker", "Shoemaker", "-3", "0", "0", "0", "had", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "to", "TO", "to", "1", "NMOD", "Shoemaker", "Shoemaker", "-4", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "extract", "VV", "extract", "1", "SUFFIX", "Shoemaker", "Shoemaker", "-5", "0", "0", "0", "extract", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "the", "DT", "the", "8", "NMOD", "bones", "bone", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "bones", "NNS", "bone", "1", "APPO", "Shoemaker", "Shoemaker", "-7", "0", "0", "0", "bones", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "of", "IN", "of", "8", "NMOD", "bones", "bone", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "the", "DT", "the", "12", "NMOD", "men", "man", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "two", "CD", "two", "12", "NMOD", "men", "man", "+1", "0", "0", "0", "two", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "men", "NNS", "man", "9", "PMOD", "of", "of", "-3", "0", "0", "0", "men", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "to", "TO", "to", "1", "NMOD", "Shoemaker", "Shoemaker", "-12", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "save", "VV", "save", "1", "SUFFIX", "Shoemaker", "Shoemaker", "-13", "0", "0", "0", "save", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "as", "IN", "as", "1", "NMOD", "Shoemaker", "Shoemaker", "-14", "0", "0", "0", "as", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "relics", "NNS", "relic", "15", "PMOD", "as", "as", "-1", "0", "0", "0", "relics", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", ".", "SENT", ".", "1", "SUFFIX", "Shoemaker", "Shoemaker", "-16", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Regnault", "NP", "Regnault", "0", "ROOT", "0", "0", "0", "0", "0", "0", "regnault", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "exhumed", "VVD", "exhume", "1", "SUFFIX", "Regnault", "Regnault", "-1", "0", "0", "0", "exhumed", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "the", "DT", "the", "4", "NMOD", "bodies", "body", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "bodies", "NNS", "body", "0", "ROOT", "0", "0", "0", "0", "0", "0", "bodies", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", ",", ",", ",", "4", "P", "bodies", "body", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "placed", "VVD", "place", "4", "SUFFIX", "bodies", "body", "-2", "0", "0", "0", "placed", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "them", "PP", "them", "4", "SUFFIX", "bodies", "body", "-3", "0", "0", "0", "them", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "into", "IN", "into", "4", "NMOD", "bodies", "body", "-4", "0", "0", "0", "into", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "a", "DT", "a", "11", "NMOD", "solution", "solution", "+2", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "lye", "NN", "lye", "11", "NMOD", "solution", "solution", "+1", "https://en.wikipedia.org/wiki/Lye", "1", "0", "lye", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "solution", "NN", "solution", "8", "PMOD", "into", "into", "-3", "0", "0", "0", "solution", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "and", "CC", "and", "4", "COORD", "bodies", "body", "-8", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "wrapped", "VVD", "wrap", "4", "SUFFIX", "bodies", "body", "-9", "0", "0", "0", "wrapped", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "the", "DT", "the", "15", "NMOD", "bones", "bone", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "bones", "NNS", "bone", "4", "APPO", "bodies", "body", "-11", "0", "0", "0", "bones", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "in", "IN", "in", "15", "LOC", "bones", "bone", "-1", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "linens", "NNS", "linen", "16", "PMOD", "in", "in", "-1", "0", "0", "0", "linens", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", ".", "SENT", ".", "16", "SUFFIX", "in", "in", "-2", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "The", "DT", "the", "2", "NMOD", "men", "man", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "men", "NNS", "man", "5", "NMOD", "remains", "remain", "+3", "0", "0", "0", "men", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "'s", "POS", "'s", "2", "SUFFIX", "men", "man", "-1", "0", "0", "0", "'s", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "flesh", "NN", "flesh", "5", "NMOD", "remains", "remain", "+1", "0", "0", "0", "flesh", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "remains", "NNS", "remain", "6", "SBJ", "were", "be", "+1", "0", "0", "0", "remains", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "were", "VBD", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "were", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "reburied", "VVN", "reburied", "6", "SUFFIX", "were", "be", "-1", "0", "0", "0", "reburied", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "together", "RB", "together", "9", "PMOD", "in", "in", "+1", "0", "0", "0", "together", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "in", "IN", "in", "6", "TMP", "were", "be", "-3", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "the", "DT", "the", "12", "NMOD", "grave", "grave", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "same", "JJ", "same", "12", "NMOD", "grave", "grave", "+1", "0", "0", "0", "same", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "grave", "NN", "grave", "9", "PMOD", "in", "in", "-3", "0", "0", "0", "grave", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", ".", "SENT", ".", "12", "SUFFIX", "grave", "grave", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "The", "DT", "the", "2", "NMOD", "missionaries", "missionary", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "missionaries", "NNS", "missionary", "0", "ROOT", "0", "0", "0", "0", "0", "0", "missionaries", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "travelled", "VVN", "travel", "2", "SUFFIX", "missionaries", "missionary", "-1", "0", "0", "4.0", "travelled", "q:e69a4b28a3", "activity", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "activity", "1"]
            }, {
                "type": "word",
                "indexes": ["4", "to", "TO", "to", "2", "NMOD", "missionaries", "missionary", "-2", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "activity", "-1"]
            }, {
                "type": "word",
                "indexes": ["5", "Gahoendoe", "NP", "Gahoendoe", "2", "SUFFIX", "missionaries", "missionary", "-3", "https://en.wikipedia.org/wiki/Christian_Island,_Ontario", "1", "0", "gahoendoe", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "with", "IN", "with", "2", "NMOD", "missionaries", "missionary", "-4", "0", "0", "0", "with", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "the", "DT", "the", "16", "NMOD", "mission", "mission", "+9", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "Wendat", "NP", "Wendat", "7", "SUFFIX", "the", "the", "-1", "0", "0", "0", "wendat", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "in", "IN", "in", "7", "LOC", "the", "the", "-2", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "an", "DT", "an", "11", "NMOD", "effort", "effort", "+1", "0", "0", "0", "an", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "effort", "NN", "effort", "9", "PMOD", "in", "in", "-2", "0", "0", "0", "effort", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "to", "TO", "to", "11", "NMOD", "effort", "effort", "-1", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "construct", "VV", "construct", "11", "SUFFIX", "effort", "effort", "-2", "0", "0", "0", "construct", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "a", "DT", "a", "16", "NMOD", "mission", "mission", "+2", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "second", "JJ", "second", "16", "NMOD", "mission", "mission", "+1", "0", "0", "0", "second", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "mission", "NN", "mission", "6", "PMOD", "with", "with", "-10", "0", "0", "0", "mission", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "designed", "VVN", "design", "16", "SUFFIX", "mission", "mission", "-1", "0", "0", "0", "designed", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "especially", "RB", "especially", "19", "PMOD", "for", "for", "+1", "0", "0", "0", "especially", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "for", "IN", "for", "16", "NMOD", "mission", "mission", "-3", "0", "0", "0", "for", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", "defence", "NN", "defence", "19", "PMOD", "for", "for", "-1", "0", "0", "0", "defence", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", ".", "SENT", ".", "20", "SUFFIX", "defence", "defence", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "It", "PP", "it", "0", "ROOT", "0", "0", "0", "0", "0", "0", "it", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "was", "VBD", "be", "1", "COORD", "It", "it", "-1", "0", "0", "0", "was", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "named", "VVN", "name", "2", "OBJ", "was", "be", "-1", "0", "0", "0", "named", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Grapevine_Canyon_Petroglyphs", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Many_petroglyphs_on_a_rock.JPG", "Ste", "United_States_of_America", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["4", "Ste", "NN", "Ste", "2", "PRD", "was", "be", "-2", "0", "0", "4.0", "ste", "l:3f5490ce58", "location", "https://en.wikipedia.org/wiki/Grapevine_Canyon_Petroglyphs", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Many_petroglyphs_on_a_rock.JPG", "Ste", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["5", ".", "SENT", ".", "4", "SUFFIX", "Ste", "Ste", "-1", "0", "0", "0", ".", "0", "0", "https://en.wikipedia.org/wiki/Grapevine_Canyon_Petroglyphs", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Many_petroglyphs_on_a_rock.JPG", "Ste", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Marie", "0", "Marie", "France", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Marie", "NP", "Marie", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "marie", "l:09aabfbdda", "location", "https://en.wikipedia.org/wiki/Marie", "0", "Marie", "France", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["2", "II", "NP", "II", "1", "SUFFIX", "Marie", "Marie", "-1", "0", "0", "0", "ii", "0", "0", "https://en.wikipedia.org/wiki/Marie", "0", "Marie", "France", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", ".", "SENT", ".", "1", "SUFFIX", "Marie", "Marie", "-2", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "A", "DT", "a", "3", "NMOD", "winter", "winter", "+2", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "severe", "JJ", "severe", "3", "NMOD", "winter", "winter", "+1", "0", "0", "0", "severe", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "winter", "NN", "winter", "0", "ROOT", "0", "0", "0", "0", "0", "0", "winter", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "and", "CC", "and", "3", "COORD", "winter", "winter", "-1", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "the", "DT", "the", "7", "NMOD", "threat", "threat", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "constant", "JJ", "constant", "7", "NMOD", "threat", "threat", "+1", "0", "0", "0", "constant", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "threat", "NN", "threat", "4", "CONJ", "and", "and", "-3", "0", "0", "0", "threat", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "of", "IN", "of", "7", "NMOD", "threat", "threat", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Iroquois,_Louisville", "0", "Iroquois", "United_States_of_America", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["9", "Iroquois", "JJ", "Iroquois", "10", "NMOD", "attack", "attack", "+1", "0", "0", "4.0", "iroquois", "l:be431a2acf", "location", "https://en.wikipedia.org/wiki/Iroquois,_Louisville", "0", "Iroquois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["10", "attack", "NN", "attack", "8", "PMOD", "of", "of", "-2", "0", "0", "0", "attack", "0", "0", "https://en.wikipedia.org/wiki/Iroquois,_Louisville", "0", "Iroquois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["11", "eventually", "RB", "eventually", "14", "NMOD", "French", "French", "+3", "0", "0", "0", "eventually", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "forced", "VVD", "force", "11", "SUFFIX", "eventually", "eventually", "-1", "0", "0", "0", "forced", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "the", "DT", "the", "14", "NMOD", "French", "French", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/freebase/0k3rnwg.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "French", "France", "kb", "1"],
                "entityClass": "nationality",
                "words": [{
                    "type": "word",
                    "indexes": ["14", "French", "JJ", "French", "10", "APPO", "attack", "attack", "-4", "0", "0", "4.0", "french", "n:0f8l9c", "nationality", "https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/freebase/0k3rnwg.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "French", "France", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["15", "from", "IN", "from", "14", "NMOD", "French", "French", "-1", "0", "0", "0", "from", "0", "0", "https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/freebase/0k3rnwg.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "French", "France", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["16", "the", "DT", "the", "17", "NMOD", "area", "area", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "area", "NN", "area", "15", "PMOD", "from", "from", "-2", "0", "0", "0", "area", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", ",", ",", ",", "3", "P", "winter", "winter", "-15", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "and", "CC", "and", "3", "COORD", "winter", "winter", "-16", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", "they", "PP", "they", "3", "SUFFIX", "winter", "winter", "-17", "0", "0", "0", "they", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", "travelled", "VVD", "travel", "3", "SUFFIX", "winter", "winter", "-18", "0", "0", "4.0", "travelled", "q:5f2bdf68fc", "activity", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "activity", "1"]
            }, {
                "type": "word",
                "indexes": ["22", "back", "RB", "back", "3", "NMOD", "winter", "winter", "-19", "0", "0", "0", "back", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "activity", "-1"]
            }, {
                "type": "word",
                "indexes": ["23", "to", "TO", "to", "22", "AMOD", "back", "back", "-1", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["24", "New", "NP", "New", "3", "SUFFIX", "winter", "winter", "-21", "0", "0", "0", "new", "l:2daed0efda", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/New_France,_Antigonish_County", "0", "New_France__Antigonish_County", "Canada", "kb", "2"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["25", "France", "NP", "France", "3", "SUFFIX", "winter", "winter", "-22", "0", "0", "4.0", "france", "l:2daed0efda", "location", "https://en.wikipedia.org/wiki/New_France,_Antigonish_County", "0", "New_France__Antigonish_County", "Canada", "0", "0", "0", "0", "0", "0", "kb", "2"]
                }, {
                    "type": "word",
                    "indexes": ["26", ".", "SENT", ".", "3", "SUFFIX", "winter", "winter", "-23", "0", "0", "0", ".", "0", "0", "https://en.wikipedia.org/wiki/New_France,_Antigonish_County", "0", "New_France__Antigonish_County", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "They", "PP", "they", "0", "ROOT", "0", "0", "0", "0", "0", "0", "they", "0", "0", "https://en.wikipedia.org/wiki/New_France,_Antigonish_County", "0", "New_France__Antigonish_County", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "took", "VVD", "take", "1", "SUFFIX", "They", "they", "-1", "0", "0", "0", "took", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "along", "IN", "along", "1", "LOC", "They", "they", "-2", "0", "0", "0", "along", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "the", "DT", "the", "5", "NMOD", "bones", "bone", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "bones", "NNS", "bone", "3", "PMOD", "along", "along", "-2", "0", "0", "0", "bones", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "of", "IN", "of", "5", "NMOD", "bones", "bone", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "coref", "1"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["7", "Brébeuf", "NP", "Brébeuf", "5", "SUFFIX", "bones", "bone", "-2", "0", "0", "2", "brébeuf", "p:fbb09f2520", "person", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "coref", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["8", "and", "CC", "and", "5", "COORD", "bones", "bone", "-3", "0", "0", "0", "and", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "coref", "-1"]
            }, {
                "type": "word",
                "indexes": ["9", "Lalemant", "NP", "Lalemant", "5", "SUFFIX", "bones", "bone", "-4", "0", "0", "0", "lalemant", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "on", "IN", "on", "1", "NMOD", "They", "they", "-9", "0", "0", "0", "on", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "both", "DT", "both", "12", "NMOD", "trips", "trip", "+1", "0", "0", "0", "both", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "trips", "NNS", "trip", "10", "PMOD", "on", "on", "-2", "0", "0", "0", "trips", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", ".", "SENT", ".", "12", "SUFFIX", "trips", "trip", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "The", "DT", "the", "2", "NMOD", "bones", "bone", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "bones", "NNS", "bone", "0", "ROOT", "0", "0", "0", "0", "0", "0", "bones", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "have", "VHP", "have", "2", "SUFFIX", "bones", "bone", "-1", "0", "0", "0", "have", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "been", "VBN", "be", "2", "APPO", "bones", "bone", "-2", "0", "0", "0", "been", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "held", "VVN", "hold", "2", "SUFFIX", "bones", "bone", "-3", "0", "0", "0", "held", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "as", "IN", "as", "2", "NMOD", "bones", "bone", "-4", "0", "0", "0", "as", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "holy", "JJ", "holy", "8", "NMOD", "relics", "relic", "+1", "0", "0", "0", "holy", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "relics", "NNS", "relic", "6", "PMOD", "as", "as", "-2", "0", "0", "0", "relics", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "at", "IN", "at", "2", "LOC", "bones", "bone", "-7", "0", "0", "0", "at", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Grapevine_Canyon_Petroglyphs", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Many_petroglyphs_on_a_rock.JPG", "Ste", "United_States_of_America", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["10", "Ste", "NP", "Ste", "2", "SUFFIX", "bones", "bone", "-8", "0", "0", "4.0", "ste", "l:3f5490ce58", "location", "https://en.wikipedia.org/wiki/Grapevine_Canyon_Petroglyphs", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Many_petroglyphs_on_a_rock.JPG", "Ste", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["11", ".", "SENT", ".", "2", "SUFFIX", "bones", "bone", "-9", "0", "0", "0", ".", "0", "0", "https://en.wikipedia.org/wiki/Grapevine_Canyon_Petroglyphs", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Many_petroglyphs_on_a_rock.JPG", "Ste", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Marie", "0", "Marie", "France", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Marie", "NP", "Marie", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "marie", "l:09aabfbdda", "location", "https://en.wikipedia.org/wiki/Marie", "0", "Marie", "France", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["2", "II", "NP", "II", "1", "SUFFIX", "Marie", "Marie", "-1", "0", "0", "0", "ii", "0", "0", "https://en.wikipedia.org/wiki/Marie", "0", "Marie", "France", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", ",", ",", ",", "1", "P", "Marie", "Marie", "-2", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "which", "WDT", "which", "5", "SBJ", "can", "can", "+1", "0", "0", "0", "which", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "can", "MD", "can", "1", "COORD", "Marie", "Marie", "-4", "0", "0", "0", "can", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "be", "VB", "be", "5", "VC", "can", "can", "-1", "0", "0", "0", "be", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "visited", "VVN", "visit", "6", "OBJ", "be", "be", "-1", "0", "0", "4.0", "visited", "x:28f3333dfe", "activity", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "activity", "1"]
            }, {
                "type": "word",
                "indexes": ["8", "across", "RP", "across", "6", "PRT", "be", "be", "-2", "0", "0", "0", "across", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "activity", "-1"]
            }, {
                "type": "word",
                "indexes": ["9", "from", "IN", "from", "6", "ADV", "be", "be", "-3", "0", "0", "0", "from", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "the", "DT", "the", "12", "NMOD", "church", "church", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "Anglican", "JJ", "Anglican", "12", "NMOD", "church", "church", "+1", "0", "0", "0", "anglican", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "church", "NN", "church", "9", "PMOD", "from", "from", "-3", "0", "0", "0", "church", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "on", "IN", "on", "12", "NMOD", "church", "church", "-1", "0", "0", "0", "on", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "Christian", "NP", "Christian", "12", "SUFFIX", "church", "church", "-2", "0", "0", "0", "christian", "l:baccb3e599", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Christian_Island_(Ontario)", "0", "Christian_Island", "Canada", "kb", "2"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["15", "Island", "NP", "Island", "12", "SUFFIX", "church", "church", "-3", "0", "0", "4.0", "island", "l:baccb3e599", "location", "https://en.wikipedia.org/wiki/Christian_Island_(Ontario)", "0", "Christian_Island", "Canada", "0", "0", "0", "0", "0", "0", "kb", "2"]
                }, {
                    "type": "word",
                    "indexes": ["16", ".", "SENT", ".", "12", "SUFFIX", "church", "church", "-4", "0", "0", "0", ".", "0", "0", "https://en.wikipedia.org/wiki/Christian_Island_(Ontario)", "0", "Christian_Island", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Modern", "JJ", "modern", "2", "NMOD", "reconstruction", "reconstruction", "+1", "0", "0", "0", "modern", "0", "0", "https://en.wikipedia.org/wiki/Christian_Island_(Ontario)", "0", "Christian_Island", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "reconstruction", "NN", "reconstruction", "0", "ROOT", "0", "0", "0", "0", "0", "0", "reconstruction", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Mural", "http://athena3.fit.vutbr.cz/kb/images/freebase/03s46h6.jpg", "Mural", "kb", "1"],
                "entityClass": "form",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Mural", "NN", "mural", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "mural", "f:04rd7", "form", "https://en.wikipedia.org/wiki/Mural", "http://athena3.fit.vutbr.cz/kb/images/freebase/03s46h6.jpg", "Mural", "0", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["2", "in", "IN", "in", "1", "LOC", "Mural", "mural", "-1", "0", "0", "0", "in", "0", "0", "https://en.wikipedia.org/wiki/Mural", "http://athena3.fit.vutbr.cz/kb/images/freebase/03s46h6.jpg", "Mural", "0", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "the", "DT", "the", "4", "NMOD", "harbour", "harbour", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "harbour", "NN", "harbour", "2", "PMOD", "in", "in", "-2", "0", "0", "0", "harbour", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "of", "IN", "of", "4", "NMOD", "harbour", "harbour", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Midland,_Texas", "http://athena3.fit.vutbr.cz/kb/images/freebase/03r_7l2.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Midland44_Skyline.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/09gxlzf.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02b_6wx.jpg", "Midland", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["6", "Midland", "NP", "Midland", "4", "SUFFIX", "harbour", "harbour", "-2", "0", "0", "4.0", "midland", "l:11dddc1488", "location", "https://en.wikipedia.org/wiki/Midland,_Texas", "http://athena3.fit.vutbr.cz/kb/images/freebase/03r_7l2.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Midland44_Skyline.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/09gxlzf.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02b_6wx.jpg", "Midland", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["7", ",", ",", ",", "1", "P", "Mural", "mural", "-6", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Midland,_Texas", "http://athena3.fit.vutbr.cz/kb/images/freebase/03r_7l2.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Midland44_Skyline.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/09gxlzf.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02b_6wx.jpg", "Midland", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["8", "commemorating", "VVG", "commemorate", "1", "SUFFIX", "Mural", "mural", "-7", "0", "0", "0", "commemorating", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "Sainte-Marie", "NP", "Sainte-Marie", "1", "SUFFIX", "Mural", "mural", "-8", "0", "0", "0", "sainte-marie", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "among", "IN", "among", "1", "LOC", "Mural", "mural", "-9", "0", "0", "0", "among", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "the", "DT", "the", "1", "NMOD", "Mural", "mural", "-10", "0", "0", "0", "the", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "kb", "4"],
                "entityClass": "museum",
                "words": [{
                    "type": "word",
                    "indexes": ["12", "Hurons", "NP", "Hurons", "11", "SUFFIX", "the", "the", "-1", "0", "0", "4.0", "hurons", "c:9d7ba74864", "museum", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "4"]
                }, {
                    "type": "word",
                    "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["1", "The", "DT", "the", "2", "NMOD", "site", "site", "+1", "0", "0", "0", "the", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["2", "site", "NN", "site", "0", "ROOT", "0", "0", "0", "0", "0", "0", "site", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "lay", "VVD", "lie", "2", "SUFFIX", "site", "site", "-1", "0", "0", "0", "lay", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["4", "dormant", "JJ", "dormant", "2", "APPO", "site", "site", "-2", "0", "0", "0", "dormant", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["5", "until", "IN", "until", "2", "TMP", "site", "site", "-3", "0", "0", "0", "until", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1844", "0", "0", "date", "1"],
                "entityClass": "date",
                "words": [{
                    "type": "word",
                    "indexes": ["6", "1844", "CD", "@card@", "5", "PMOD", "until", "until", "-1", "0", "0", "4.0", "1844", "t:6bc1f1a472", "date", "0", "0", "1844", "0", "0", "0", "0", "0", "0", "0", "date", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["7", ",", ",", ",", "6", "P", "1844", "@card@", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "1844", "0", "0", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["8", "when", "WRB", "when", "2", "NMOD", "site", "site", "-6", "0", "0", "0", "when", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "Jesuit", "NP", "Jesuit", "8", "SUFFIX", "when", "when", "-1", "0", "0", "0", "jesuit", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "Fr", "NP", "Fr", "8", "SUFFIX", "when", "when", "-2", "0", "0", "0", "fr", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", ".", "SENT", ".", "8", "SUFFIX", "when", "when", "-3", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Pierre,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/PierreSD_Capitol.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dhsg1.jpg", "Pierre", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Pierre", "NP", "Pierre", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "pierre", "l:00fae4c2fe", "location", "https://en.wikipedia.org/wiki/Pierre,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/PierreSD_Capitol.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dhsg1.jpg", "Pierre", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["2", "Chazelle", "NP", "Chazelle", "1", "SUFFIX", "Pierre", "Pierre", "-1", "0", "0", "0", "chazelle", "0", "0", "https://en.wikipedia.org/wiki/Pierre,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/PierreSD_Capitol.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dhsg1.jpg", "Pierre", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "conducted", "VVD", "conduct", "1", "SUFFIX", "Pierre", "Pierre", "-2", "0", "0", "0", "conducted", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "initial", "JJ", "initial", "6", "NMOD", "excavations", "excavation", "+2", "0", "0", "0", "initial", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "site", "NN", "site", "6", "NMOD", "excavations", "excavation", "+1", "0", "0", "0", "site", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "excavations", "NNS", "excavation", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/Excavation_(archaeology)", "1", "0", "excavations", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", ".", "SENT", ".", "6", "SUFFIX", "excavations", "excavation", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Father", "NP", "Father", "0", "ROOT", "0", "0", "0", "0", "0", "0", "father", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "Félix", "NP", "Félix", "1", "SUFFIX", "Father", "Father", "-1", "0", "0", "0", "félix", "a:10e1e85a89", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Félix_Martin", "0", "Felix_Martin", "M", "Brittany", "1804", "Paris", "1886", "0", "Quebecois|French|Canadian", "kb", "2"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["3", "Martin", "NP", "Martin", "1", "SUFFIX", "Father", "Father", "-2", "https://en.wikipedia.org/wiki/Félix_Martin", "2", "4.1", "martin", "a:10e1e85a89", "person", "https://en.wikipedia.org/wiki/Félix_Martin", "0", "Felix_Martin", "M", "Brittany", "1804", "Paris", "1886", "0", "Quebecois|French|Canadian", "kb", "2"]
                }, {
                    "type": "word",
                    "indexes": ["4", "continued", "VVD", "continue", "1", "SUFFIX", "Father", "Father", "-3", "0", "0", "0", "continued", "0", "0", "https://en.wikipedia.org/wiki/Félix_Martin", "0", "Felix_Martin", "M", "Brittany", "1804", "Paris", "1886", "0", "Quebecois|French|Canadian", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["5", "this", "DT", "this", "4", "OBJ", "continued", "continue", "-1", "0", "0", "0", "this", "0", "0", "https://en.wikipedia.org/wiki/Félix_Martin", "0", "Felix_Martin", "M", "Brittany", "1804", "Paris", "1886", "0", "Quebecois|French|Canadian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["6", "in", "IN", "in", "1", "TMP", "Father", "Father", "-5", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1855", "0", "0", "date", "1"],
                "entityClass": "date",
                "words": [{
                    "type": "word",
                    "indexes": ["7", "1855", "CD", "@card@", "6", "PMOD", "in", "in", "-1", "0", "0", "4.0", "1855", "t:5f02b7b488", "date", "0", "0", "1855", "0", "0", "0", "0", "0", "0", "0", "date", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["8", ".", "SENT", ".", "6", "SUFFIX", "in", "in", "-2", "0", "0", "0", ".", "0", "0", "0", "0", "1855", "0", "0", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "In", "IN", "in", "0", "ROOT", "0", "0", "0", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1940", "0", "0", "date", "1"],
                "entityClass": "date",
                "words": [{
                    "type": "word",
                    "indexes": ["2", "1940", "CD", "@card@", "1", "PMOD", "In", "in", "-1", "0", "0", "4.0", "1940", "t:545acc9ca8", "date", "0", "0", "1940", "0", "0", "0", "0", "0", "0", "0", "date", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["3", "the", "DT", "the", "9", "NMOD", "property", "property", "+6", "0", "0", "0", "the", "0", "0", "0", "0", "1940", "0", "0", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["4", "Society", "NP", "Society", "3", "SUFFIX", "the", "the", "-1", "0", "0", "0", "society", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "of", "IN", "of", "3", "NMOD", "the", "the", "-2", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "Jesus", "NP", "Jesus", "3", "SUFFIX", "the", "the", "-3", "0", "0", "0", "jesus", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "purchased", "VVD", "purchase", "3", "SUFFIX", "the", "the", "-4", "0", "0", "0", "purchased", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "the", "DT", "the", "9", "NMOD", "property", "property", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "property", "NN", "property", "1", "NMOD", "In", "in", "-8", "0", "0", "0", "property", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Pierre,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/PierreSD_Capitol.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dhsg1.jpg", "Pierre", "United_States", "coref", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["10", "where", "WRB", "where", "1", "NMOD", "In", "in", "-9", "0", "0", "2", "where", "l:00fae4c2fe", "location", "https://en.wikipedia.org/wiki/Pierre,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/PierreSD_Capitol.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dhsg1.jpg", "Pierre", "United_States", "0", "0", "0", "0", "0", "0", "coref", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["11", "Sainte-Marie", "NP", "Sainte-Marie", "10", "SUFFIX", "where", "where", "-1", "0", "0", "4.0", "sainte-marie", "l:eea9e79ab3", "location", "https://en.wikipedia.org/wiki/Pierre,_South_Dakota", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/PierreSD_Capitol.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dhsg1.jpg", "Pierre", "United_States", "0", "0", "0", "0", "0", "0", "coref", "-1"]
            }, {
                "type": "word",
                "indexes": ["12", "had", "VHD", "have", "10", "SUFFIX", "where", "where", "-2", "0", "0", "0", "had", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "stood", "VVN", "stand", "10", "SUFFIX", "where", "where", "-3", "0", "0", "0", "stood", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", ".", "SENT", ".", "10", "SUFFIX", "where", "where", "-4", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "In", "IN", "in", "0", "ROOT", "0", "0", "0", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1941", "0", "0", "date", "1"],
                "entityClass": "date",
                "words": [{
                    "type": "word",
                    "indexes": ["2", "1941", "CD", "@card@", "1", "PMOD", "In", "in", "-1", "0", "0", "4.0", "1941", "t:5abe8903ff", "date", "0", "0", "1941", "0", "0", "0", "0", "0", "0", "0", "date", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["3", ",", ",", ",", "1", "NMOD", "In", "in", "-2", "0", "0", "0", ",", "0", "0", "0", "0", "1941", "0", "0", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["4", "Kenneth", "NP", "Kenneth", "3", "SUFFIX", ",", ",", "-1", "0", "0", "0", "kenneth", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "Kidd", "NP", "Kidd", "3", "SUFFIX", ",", ",", "-2", "0", "0", "0", "kidd", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "of", "IN", "of", "3", "NMOD", ",", ",", "-3", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "the", "DT", "the", "15", "NMOD", "excavations", "excavation", "+8", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "Royal", "NP", "Royal", "7", "SUFFIX", "the", "the", "-1", "0", "0", "0", "royal", "c:2336fe9cc4", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "Ontario", "NP", "Ontario", "7", "SUFFIX", "the", "the", "-2", "0", "0", "0", "ontario", "c:2336fe9cc4", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Royal_Ontario_Museum", "http://athena3.fit.vutbr.cz/kb/images/freebase/03smntj.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:ROM.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02cjwsh.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04296py.jpg", "Royal_Ontario_Museum", "Antiquities_museum|History_museum|Art_Gallery|Natural_history_museum", "1912_04_16", "Janet_Carding", "Toronto", "kb", "3"],
                "entityClass": "museum",
                "words": [{
                    "type": "word",
                    "indexes": ["10", "Museum", "NP", "Museum", "7", "SUFFIX", "the", "the", "-3", "https://en.wikipedia.org/wiki/Royal_Ontario_Museum", "3", "4.1", "museum", "c:2336fe9cc4", "museum", "https://en.wikipedia.org/wiki/Royal_Ontario_Museum", "http://athena3.fit.vutbr.cz/kb/images/freebase/03smntj.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:ROM.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02cjwsh.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04296py.jpg", "Royal_Ontario_Museum", "Antiquities_museum|History_museum|Art_Gallery|Natural_history_museum", "1912_04_16", "Janet_Carding", "Toronto", "0", "0", "0", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["11", "undertook", "VVD", "undertake", "7", "SUFFIX", "the", "the", "-4", "0", "0", "0", "undertook", "0", "0", "https://en.wikipedia.org/wiki/Royal_Ontario_Museum", "http://athena3.fit.vutbr.cz/kb/images/freebase/03smntj.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:ROM.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02cjwsh.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04296py.jpg", "Royal_Ontario_Museum", "Antiquities_museum|History_museum|Art_Gallery|Natural_history_museum", "1912_04_16", "Janet_Carding", "Toronto", "0", "0", "0", "kb", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["12", "the", "DT", "the", "15", "NMOD", "excavations", "excavation", "+3", "0", "0", "0", "the", "0", "0", "https://en.wikipedia.org/wiki/Royal_Ontario_Museum", "http://athena3.fit.vutbr.cz/kb/images/freebase/03smntj.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:ROM.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02cjwsh.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04296py.jpg", "Royal_Ontario_Museum", "Antiquities_museum|History_museum|Art_Gallery|Natural_history_museum", "1912_04_16", "Janet_Carding", "Toronto", "0", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["13", "first", "JJ", "first", "15", "NMOD", "excavations", "excavation", "+2", "0", "0", "0", "first", "0", "0", "https://en.wikipedia.org/wiki/Royal_Ontario_Museum", "http://athena3.fit.vutbr.cz/kb/images/freebase/03smntj.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:ROM.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02cjwsh.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04296py.jpg", "Royal_Ontario_Museum", "Antiquities_museum|History_museum|Art_Gallery|Natural_history_museum", "1912_04_16", "Janet_Carding", "Toronto", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["14", "scientific", "JJ", "scientific", "15", "NMOD", "excavations", "excavation", "+1", "0", "0", "0", "scientific", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "excavations", "NNS", "excavation", "6", "PMOD", "of", "of", "-9", "0", "0", "0", "excavations", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "of", "IN", "of", "15", "NMOD", "excavations", "excavation", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "the", "DT", "the", "18", "NMOD", "site", "site", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "site", "NN", "site", "16", "PMOD", "of", "of", "-2", "0", "0", "0", "site", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", ",", ",", ",", "3", "P", ",", ",", "-16", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", "aided", "VVN", "aid", "3", "SUFFIX", ",", ",", "-17", "0", "0", "0", "aided", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", "by", "IN", "by", "1", "NMOD", "In", "in", "-20", "0", "0", "0", "by", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "W.J", "NP", "W.J", "21", "SUFFIX", "by", "by", "-1", "0", "0", "0", "w.j", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["23", ".", "SENT", ".", "21", "SUFFIX", "by", "by", "-2", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Wintemberg", "NP", "Wintemberg", "0", "ROOT", "0", "0", "0", "0", "0", "0", "wintemberg", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", ".", "SENT", ".", "1", "SUFFIX", "Wintemberg", "Wintemberg", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Wilfrid", "NP", "Wilfrid", "0", "ROOT", "0", "0", "0", "0", "0", "0", "wilfrid", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "and", "CC", "and", "1", "COORD", "Wilfrid", "Wilfrid", "-1", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Elsie,_Oregon", "http://athena3.fit.vutbr.cz/kb/images/freebase/05tncyc.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Elderberry_Inn_Elsie_Oregon.JPG", "Elsie", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["3", "Elsie", "NP", "Elsie", "1", "SUFFIX", "Wilfrid", "Wilfrid", "-2", "0", "0", "4.0", "elsie", "l:6c669346cd", "location", "https://en.wikipedia.org/wiki/Elsie,_Oregon", "http://athena3.fit.vutbr.cz/kb/images/freebase/05tncyc.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Elderberry_Inn_Elsie_Oregon.JPG", "Elsie", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["4", "Jury", "NP", "Jury", "1", "SUFFIX", "Wilfrid", "Wilfrid", "-3", "0", "0", "0", "jury", "0", "0", "https://en.wikipedia.org/wiki/Elsie,_Oregon", "http://athena3.fit.vutbr.cz/kb/images/freebase/05tncyc.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Elderberry_Inn_Elsie_Oregon.JPG", "Elsie", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["5", "of", "IN", "of", "1", "NMOD", "Wilfrid", "Wilfrid", "-4", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "the", "DT", "the", "13", "NMOD", "excavations", "excavation", "+7", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "University", "NP", "University", "6", "SUFFIX", "the", "the", "-1", "0", "0", "0", "university", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "of", "IN", "of", "6", "NMOD", "the", "the", "-2", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "Western", "NP", "Western", "6", "SUFFIX", "the", "the", "-3", "0", "0", "0", "western", "l:1b779b2e33", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Southwestern_Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/02fbyn7.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Map_of_Ontario_SOUTHWESTERN.svg", "Southwestern_Ontario", "Canada", "kb", "2"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["10", "Ontario", "NP", "Ontario", "6", "SUFFIX", "the", "the", "-4", "https://en.wikipedia.org/wiki/University_of_Western_Ontario", "4", "4.2", "ontario", "l:1b779b2e33", "location", "https://en.wikipedia.org/wiki/Southwestern_Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/02fbyn7.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Map_of_Ontario_SOUTHWESTERN.svg", "Southwestern_Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "2"]
                }, {
                    "type": "word",
                    "indexes": ["11", "undertook", "VVD", "undertake", "6", "SUFFIX", "the", "the", "-5", "0", "0", "0", "undertook", "0", "0", "https://en.wikipedia.org/wiki/Southwestern_Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/02fbyn7.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Map_of_Ontario_SOUTHWESTERN.svg", "Southwestern_Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["12", "additional", "JJ", "additional", "13", "NMOD", "excavations", "excavation", "+1", "0", "0", "0", "additional", "0", "0", "https://en.wikipedia.org/wiki/Southwestern_Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/02fbyn7.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Map_of_Ontario_SOUTHWESTERN.svg", "Southwestern_Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["13", "excavations", "NNS", "excavation", "5", "PMOD", "of", "of", "-8", "0", "0", "0", "excavations", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", ".", "SENT", ".", "13", "SUFFIX", "excavations", "excavation", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "In", "IN", "in", "0", "ROOT", "0", "0", "0", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1954", "0", "0", "date", "1"],
                "entityClass": "date",
                "words": [{
                    "type": "word",
                    "indexes": ["2", "1954", "CD", "@card@", "3", "NMOD", "Fr", "Fr", "+1", "0", "0", "4.0", "1954", "t:f2ce79ffbb", "date", "0", "0", "1954", "0", "0", "0", "0", "0", "0", "0", "date", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["3", "Fr", "NN", "Fr", "1", "PMOD", "In", "in", "-2", "0", "0", "0", "fr", "0", "0", "0", "0", "1954", "0", "0", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["4", ".", "SENT", ".", "3", "SUFFIX", "Fr", "Fr", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Dennis,_Texas", "0", "Dennis", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Dennis", "NP", "Dennis", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "dennis", "l:c14e9cf703", "location", "https://en.wikipedia.org/wiki/Dennis,_Texas", "0", "Dennis", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["2", "Hegarty", "NP", "Hegarty", "1", "SUFFIX", "Dennis", "Dennis", "-1", "0", "0", "0", "hegarty", "0", "0", "https://en.wikipedia.org/wiki/Dennis,_Texas", "0", "Dennis", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "discovered", "VVD", "discover", "1", "SUFFIX", "Dennis", "Dennis", "-2", "0", "0", "0", "discovered", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "the", "DT", "the", "5", "NMOD", "graves", "grave", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "graves", "NNS", "grave", "0", "ROOT", "0", "0", "0", "0", "0", "0", "graves", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "of", "IN", "of", "5", "NMOD", "graves", "grave", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "coref", "1"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["7", "Brébeuf", "NP", "Brébeuf", "5", "SUFFIX", "graves", "grave", "-2", "0", "0", "2", "brébeuf", "p:fbb09f2520", "person", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "coref", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["8", "and", "CC", "and", "5", "COORD", "graves", "grave", "-3", "0", "0", "0", "and", "0", "0", "https://en.wikipedia.org/wiki/Jean_de_Brébeuf", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Brébuef-jesuits04jesuuoft.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02c9n1h.jpg", "Jean_de_Brebeuf", "M", "Conde_sur_Vire", "1593_03_25", "Midland", "1649_03_16", "0", "French|Canadian", "coref", "-1"]
            }, {
                "type": "word",
                "indexes": ["9", "Lalemant", "NP", "Lalemant", "5", "SUFFIX", "graves", "grave", "-4", "0", "0", "0", "lalemant", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", ".", "SENT", ".", "5", "SUFFIX", "graves", "grave", "-5", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Starting", "VVG", "start", "0", "ROOT", "0", "0", "0", "0", "0", "0", "starting", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "in", "IN", "in", "6", "TMP", "was", "be", "+4", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "1964", "0", "0", "date", "1"],
                "entityClass": "date",
                "words": [{
                    "type": "word",
                    "indexes": ["3", "1964", "CD", "@card@", "2", "PMOD", "in", "in", "-1", "0", "0", "4.0", "1964", "t:1bd28df3e5", "date", "0", "0", "1964", "0", "0", "0", "0", "0", "0", "0", "date", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["4", ",", ",", ",", "6", "SBJ", "was", "be", "+2", "0", "0", "0", ",", "0", "0", "0", "0", "1964", "0", "0", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["5", "Sainte-Marie", "NP", "Sainte-Marie", "4", "SUFFIX", ",", ",", "-1", "0", "0", "4.0", "sainte-marie", "l:81213c12e1", "location", "https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["6", "was", "VBD", "be", "1", "TMP", "Starting", "start", "-5", "0", "0", "0", "was", "0", "0", "https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["7", "reconstructed", "VVN", "reconstruct", "6", "OBJ", "was", "be", "-1", "0", "0", "0", "reconstructed", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "as", "IN", "as", "6", "ADV", "was", "be", "-2", "0", "0", "0", "as", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "a", "DT", "a", "14", "NMOD", "museum", "museum", "+5", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "historical", "JJ", "historical", "14", "NMOD", "museum", "museum", "+4", "0", "0", "0", "historical", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "site", "NN", "site", "14", "NMOD", "museum", "museum", "+3", "0", "0", "0", "site", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "and", "CC", "and", "11", "COORD", "site", "site", "-1", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "living", "NN", "living", "12", "CONJ", "and", "and", "-1", "0", "0", "0", "living", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "museum", "NN", "museum", "8", "PMOD", "as", "as", "-6", "0", "0", "0", "museum", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", ".", "SENT", ".", "14", "SUFFIX", "museum", "museum", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "[", "SYM", "[", "3", "NMOD", "]", "]", "+2", "0", "0", "0", "[", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "3", "CD", "3", "3", "NMOD", "]", "]", "+1", "0", "0", "0", "3", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "]", "SYM", "]", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons#cite_note-3", "3", "0", "]", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "All", "DT", "all", "3", "NMOD", "]", "]", "-1", "0", "0", "0", "all", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "of", "IN", "of", "4", "NMOD", "All", "all", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "the", "DT", "the", "7", "NMOD", "buildings", "building", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "buildings", "NNS", "building", "5", "PMOD", "of", "of", "-2", "0", "0", "0", "buildings", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "and", "CC", "and", "4", "COORD", "All", "all", "-4", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "their", "PP$", "their", "4", "SUFFIX", "All", "all", "-5", "0", "0", "0", "their", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "contents", "NNS", "content", "11", "SBJ", "are", "be", "+1", "0", "0", "0", "contents", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "are", "VBP", "be", "4", "NMOD", "All", "all", "-7", "0", "0", "0", "are", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "reproductions", "NNS", "reproduction", "11", "PRD", "are", "be", "-1", "0", "0", "0", "reproductions", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", ".", "SENT", ".", "4", "SUFFIX", "All", "all", "-9", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "A", "DT", "a", "4", "NMOD", "attraction", "attraction", "+3", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "popular", "JJ", "popular", "4", "NMOD", "attraction", "attraction", "+2", "0", "0", "0", "popular", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "tourist", "NN", "tourist", "4", "NMOD", "attraction", "attraction", "+1", "0", "0", "0", "tourist", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "attraction", "NN", "attraction", "8", "NMOD", "thousands", "thousand", "+4", "0", "0", "0", "attraction", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", ",", ",", ",", "8", "NMOD", "thousands", "thousand", "+3", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "it", "PP", "it", "5", "SUFFIX", ",", ",", "-1", "0", "0", "0", "it", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "draws", "VVZ", "draw", "5", "SUFFIX", ",", ",", "-2", "0", "0", "0", "draws", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "thousands", "NNS", "thousand", "12", "NMOD", "week", "week", "+4", "0", "0", "0", "thousands", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "of", "IN", "of", "8", "NMOD", "thousands", "thousand", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "visitors", "NNS", "visitor", "9", "PMOD", "of", "of", "-1", "0", "0", "0", "visitors", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "each", "DT", "each", "12", "NMOD", "week", "week", "+1", "0", "0", "0", "each", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "week", "NN", "week", "0", "ROOT", "0", "0", "0", "0", "0", "0", "week", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "during", "IN", "during", "12", "TMP", "week", "week", "-1", "0", "0", "0", "during", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "the", "DT", "the", "16", "NMOD", "months", "month", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "summer", "NN", "summer", "16", "NMOD", "months", "month", "+1", "0", "0", "0", "summer", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "months", "NNS", "month", "13", "PMOD", "during", "during", "-3", "0", "0", "0", "months", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", ".", "SENT", ".", "16", "SUFFIX", "months", "month", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "The", "DT", "the", "2", "NMOD", "site", "site", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "site", "NN", "site", "3", "SBJ", "is", "be", "+1", "0", "0", "0", "site", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "is", "VBZ", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "is", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "managed", "VVN", "manage", "3", "SUFFIX", "is", "be", "-1", "0", "0", "0", "managed", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "by", "IN", "by", "3", "PRD", "is", "be", "-2", "0", "0", "0", "by", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Huronia_(region)", "0", "Huronia", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["6", "Huronia", "NP", "Huronia", "3", "SUFFIX", "is", "be", "-3", "0", "0", "4.0", "huronia", "l:e16c7711e5", "location", "https://en.wikipedia.org/wiki/Huronia_(region)", "0", "Huronia", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["7", "Historical", "JJ", "historical", "3", "NMOD", "is", "be", "-4", "0", "0", "0", "historical", "0", "0", "https://en.wikipedia.org/wiki/Huronia_(region)", "0", "Huronia", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["8", "Parks", "NP", "Parks", "7", "SUFFIX", "Historical", "historical", "-1", "0", "0", "0", "parks", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", ",", ",", ",", "3", "NMOD", "is", "be", "-6", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "an", "DT", "an", "11", "NMOD", "agency", "agency", "+1", "0", "0", "0", "an", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "agency", "NN", "agency", "3", "NMOD", "is", "be", "-8", "0", "0", "0", "agency", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "of", "IN", "of", "11", "NMOD", "agency", "agency", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "the", "DT", "the", "3", "NMOD", "is", "be", "-10", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["14", "Ontario", "NP", "Ontario", "13", "SUFFIX", "the", "the", "-1", "0", "0", "4.0", "ontario", "l:8a4e54e619", "location", "https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["15", "Ministry", "NP", "Ministry", "13", "SUFFIX", "the", "the", "-2", "0", "0", "0", "ministry", "0", "0", "https://en.wikipedia.org/wiki/Ontario", "http://athena3.fit.vutbr.cz/kb/images/freebase/0426lhd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03s8gj8.jpg", "Ontario", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["16", "of", "IN", "of", "13", "NMOD", "the", "the", "-3", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "Tourism", "NP", "Tourism", "13", "SUFFIX", "the", "the", "-4", "0", "0", "0", "tourism", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "and", "CC", "and", "13", "COORD", "the", "the", "-5", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "Culture", "NP", "Culture", "13", "SUFFIX", "the", "the", "-6", "https://en.wikipedia.org/wiki/Ministry_of_Tourism_and_Culture_(Ontario)", "6", "0", "culture", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", ".", "SENT", ".", "13", "SUFFIX", "the", "the", "-7", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Canonized", "VVN", "canonize", "0", "ROOT", "0", "0", "0", "0", "0", "0", "canonized", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "by", "IN", "by", "0", "ROOT", "0", "0", "0", "0", "0", "0", "by", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "Pope", "NP", "Pope", "2", "SUFFIX", "by", "by", "-1", "0", "0", "0", "pope", "p:6b74e8b4c4", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "Pius", "NP", "Pius", "2", "SUFFIX", "by", "by", "-2", "0", "0", "0", "pius", "p:6b74e8b4c4", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Pope_Pius_XI", "http://athena3.fit.vutbr.cz/kb/images/freebase/04ss7y9.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03ryh56.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Papst_Pius_XI._1JS.jpg", "Pope_Pius_XI", "M", "Desio", "1857_05_31", "Apostolic_Palace", "1939_02_10", "0", "Italian", "kb", "3"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["5", "XI", "NP", "XI", "2", "SUFFIX", "by", "by", "-3", "https://en.wikipedia.org/wiki/Pope_Pius_XI", "3", "4.1", "xi", "p:6b74e8b4c4", "person", "https://en.wikipedia.org/wiki/Pope_Pius_XI", "http://athena3.fit.vutbr.cz/kb/images/freebase/04ss7y9.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03ryh56.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Papst_Pius_XI._1JS.jpg", "Pope_Pius_XI", "M", "Desio", "1857_05_31", "Apostolic_Palace", "1939_02_10", "0", "Italian", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["6", "in", "IN", "in", "2", "TMP", "by", "by", "-4", "0", "0", "0", "in", "0", "0", "https://en.wikipedia.org/wiki/Pope_Pius_XI", "http://athena3.fit.vutbr.cz/kb/images/freebase/04ss7y9.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03ryh56.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Papst_Pius_XI._1JS.jpg", "Pope_Pius_XI", "M", "Desio", "1857_05_31", "Apostolic_Palace", "1939_02_10", "0", "Italian", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["7", "1930", "CD", "@card@", "6", "PMOD", "in", "in", "-1", "0", "0", "4.0", "1930", "t:bfb0bcdf29", "date", "https://en.wikipedia.org/wiki/Pope_Pius_XI", "http://athena3.fit.vutbr.cz/kb/images/freebase/04ss7y9.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03ryh56.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Papst_Pius_XI._1JS.jpg", "Pope_Pius_XI", "M", "Desio", "1857_05_31", "Apostolic_Palace", "1939_02_10", "0", "Italian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["8", ",", ",", ",", "2", "NMOD", "by", "by", "-6", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Pope_Pius_XI", "http://athena3.fit.vutbr.cz/kb/images/freebase/04ss7y9.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03ryh56.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Papst_Pius_XI._1JS.jpg", "Pope_Pius_XI", "M", "Desio", "1857_05_31", "Apostolic_Palace", "1939_02_10", "0", "Italian", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["9", "the", "DT", "the", "2", "NMOD", "by", "by", "-7", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "eight", "CD", "eight", "2", "NMOD", "by", "by", "-8", "0", "0", "0", "eight", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "martyred", "VVN", "martyr", "10", "SUFFIX", "eight", "eight", "-1", "0", "0", "0", "martyred", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "missionaries", "NNS", "missionary", "13", "SBJ", "are", "be", "+1", "0", "0", "0", "missionaries", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "are", "VBP", "be", "2", "NMOD", "by", "by", "-11", "0", "0", "0", "are", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "collectively", "RB", "collectively", "13", "ADV", "are", "be", "-1", "0", "0", "0", "collectively", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "known", "VVN", "know", "13", "SUFFIX", "are", "be", "-2", "0", "0", "0", "known", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "as", "IN", "as", "13", "ADV", "are", "be", "-3", "0", "0", "0", "as", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "the", "DT", "the", "19", "NMOD", "Martyrs", "Martyrs", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "Canadian", "JJ", "Canadian", "19", "NMOD", "Martyrs", "Martyrs", "+1", "0", "0", "0", "canadian", "p:3f518162bb", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Canadian_Martyrs", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg", "Canadian_Martyrs", "M", "France", "0", "Canada", "1642", "0", "0", "kb", "2"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["19", "Martyrs", "NN", "Martyrs", "16", "PMOD", "as", "as", "-3", "https://en.wikipedia.org/wiki/Canadian_Martyrs", "2", "4.1", "martyrs", "p:3f518162bb", "person", "https://en.wikipedia.org/wiki/Canadian_Martyrs", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg", "Canadian_Martyrs", "M", "France", "0", "Canada", "1642", "0", "0", "kb", "2"]
                }, {
                    "type": "word",
                    "indexes": ["20", ".", "SENT", ".", "19", "SUFFIX", "Martyrs", "Martyrs", "-1", "0", "0", "0", ".", "0", "0", "https://en.wikipedia.org/wiki/Canadian_Martyrs", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg", "Canadian_Martyrs", "M", "France", "0", "Canada", "1642", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "The", "DT", "the", "2", "NMOD", "site", "site", "+1", "0", "0", "0", "the", "0", "0", "https://en.wikipedia.org/wiki/Canadian_Martyrs", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg", "Canadian_Martyrs", "M", "France", "0", "Canada", "1642", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "site", "NN", "site", "8", "NMOD", "Shrine", "shrine", "+6", "0", "0", "0", "site", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "adjacent", "JJ", "adjacent", "2", "APPO", "site", "site", "-1", "0", "0", "0", "adjacent", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "to", "TO", "to", "3", "AMOD", "adjacent", "adjacent", "-1", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "the", "DT", "the", "6", "NMOD", "Martyrs", "Martyrs", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Canadian_Martyrs", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg", "Canadian_Martyrs", "M", "France", "0", "Canada", "1642", "0", "0", "coref", "1"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["6", "Martyrs", "NN", "Martyrs", "4", "PMOD", "to", "to", "-2", "0", "0", "2", "martyrs", "p:3f518162bb", "person", "https://en.wikipedia.org/wiki/Canadian_Martyrs", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg", "Canadian_Martyrs", "M", "France", "0", "Canada", "1642", "0", "0", "coref", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["7", "'", "POS", "'", "6", "SUFFIX", "Martyrs", "Martyrs", "-1", "0", "0", "0", "'", "0", "0", "https://en.wikipedia.org/wiki/Canadian_Martyrs", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg", "Canadian_Martyrs", "M", "France", "0", "Canada", "1642", "0", "0", "coref", "-1"]
            }, {
                "type": "word",
                "indexes": ["8", "Shrine", "NN", "shrine", "9", "SBJ", "was", "be", "+1", "https://en.wikipedia.org/wiki/Martyrs'_Shrine", "3", "0", "shrine", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "was", "VBD", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "was", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "visited", "VVN", "visit", "9", "SUFFIX", "was", "be", "-1", "0", "0", "4.0", "visited", "b:d2fde6d73e", "activity", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "activity", "1"]
            }, {
                "type": "word",
                "indexes": ["11", "by", "IN", "by", "9", "MNR", "was", "be", "-2", "0", "0", "0", "by", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "activity", "-1"]
            }, {
                "type": "word",
                "indexes": ["12", "Pope", "NP", "Pope", "9", "SUFFIX", "was", "be", "-3", "0", "0", "0", "pope", "p:dc2854781a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "John", "NP", "John", "9", "SUFFIX", "was", "be", "-4", "0", "0", "0", "john", "p:dc2854781a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "Paul", "NP", "Paul", "13", "SUFFIX", "John", "John", "-1", "0", "0", "0", "paul", "p:dc2854781a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Pope_John_Paul_II", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qyld5.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/JohannesPaul2-portrait.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02bhcn4.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/05k84xx.jpg", "Pope_John_Paul_II", "M", "Wadowice", "1920_05_18", "Apostolic_Palace", "2005_04_02", "0", "Polish|none", "kb", "4"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["15", "II", "NP", "II", "13", "SUFFIX", "John", "John", "-2", "https://en.wikipedia.org/wiki/Pope_John_Paul_II", "4", "4.1", "ii", "p:dc2854781a", "person", "https://en.wikipedia.org/wiki/Pope_John_Paul_II", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qyld5.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/JohannesPaul2-portrait.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02bhcn4.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/05k84xx.jpg", "Pope_John_Paul_II", "M", "Wadowice", "1920_05_18", "Apostolic_Palace", "2005_04_02", "0", "Polish|none", "kb", "4"]
                }, {
                    "type": "word",
                    "indexes": ["16", "in", "IN", "in", "9", "TMP", "was", "be", "-7", "0", "0", "0", "in", "0", "0", "https://en.wikipedia.org/wiki/Pope_John_Paul_II", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qyld5.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/JohannesPaul2-portrait.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02bhcn4.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/05k84xx.jpg", "Pope_John_Paul_II", "M", "Wadowice", "1920_05_18", "Apostolic_Palace", "2005_04_02", "0", "Polish|none", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["17", "1984", "CD", "@card@", "16", "PMOD", "in", "in", "-1", "0", "0", "4.0", "1984", "t:cd3b946f94", "date", "https://en.wikipedia.org/wiki/Pope_John_Paul_II", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qyld5.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/JohannesPaul2-portrait.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02bhcn4.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/05k84xx.jpg", "Pope_John_Paul_II", "M", "Wadowice", "1920_05_18", "Apostolic_Palace", "2005_04_02", "0", "Polish|none", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["18", "as", "IN", "as", "9", "ADV", "was", "be", "-9", "0", "0", "0", "as", "0", "0", "https://en.wikipedia.org/wiki/Pope_John_Paul_II", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qyld5.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/JohannesPaul2-portrait.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02bhcn4.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/05k84xx.jpg", "Pope_John_Paul_II", "M", "Wadowice", "1920_05_18", "Apostolic_Palace", "2005_04_02", "0", "Polish|none", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["19", "part", "NN", "part", "18", "PMOD", "as", "as", "-1", "0", "0", "0", "part", "0", "0", "https://en.wikipedia.org/wiki/Pope_John_Paul_II", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qyld5.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/JohannesPaul2-portrait.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02bhcn4.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/05k84xx.jpg", "Pope_John_Paul_II", "M", "Wadowice", "1920_05_18", "Apostolic_Palace", "2005_04_02", "0", "Polish|none", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["20", "of", "IN", "of", "19", "NMOD", "part", "part", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Pope_John_Paul_II", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qyld5.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/JohannesPaul2-portrait.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02bhcn4.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/05k84xx.jpg", "Pope_John_Paul_II", "M", "Wadowice", "1920_05_18", "Apostolic_Palace", "2005_04_02", "0", "Polish|none", "coref", "1"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["21", "his", "PP$", "his", "19", "SUFFIX", "part", "part", "-2", "0", "0", "2", "his", "p:dc2854781a", "person", "https://en.wikipedia.org/wiki/Pope_John_Paul_II", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qyld5.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/JohannesPaul2-portrait.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02bhcn4.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/05k84xx.jpg", "Pope_John_Paul_II", "M", "Wadowice", "1920_05_18", "Apostolic_Palace", "2005_04_02", "0", "Polish|none", "coref", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["22", "papal", "JJ", "papal", "23", "NMOD", "visit", "visit", "+1", "0", "0", "0", "papal", "0", "0", "https://en.wikipedia.org/wiki/Pope_John_Paul_II", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qyld5.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/JohannesPaul2-portrait.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02bhcn4.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/05k84xx.jpg", "Pope_John_Paul_II", "M", "Wadowice", "1920_05_18", "Apostolic_Palace", "2005_04_02", "0", "Polish|none", "coref", "-1"]
            }, {
                "type": "word",
                "indexes": ["23", "visit", "NN", "visit", "9", "PRD", "was", "be", "-14", "0", "0", "4.0", "visit", "j:e9bbc0402b", "activity", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "activity", "1"]
            }, {
                "type": "word",
                "indexes": ["24", "to", "TO", "to", "23", "NMOD", "visit", "visit", "-1", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "activity", "-1"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Canada", "http://athena3.fit.vutbr.cz/kb/images/freebase/02nbcyh.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_Canada.svg", "Canada", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["25", "Canada", "NP", "Canada", "23", "SUFFIX", "visit", "visit", "-2", "0", "0", "4.0", "canada", "l:87165b8173", "location", "https://en.wikipedia.org/wiki/Canada", "http://athena3.fit.vutbr.cz/kb/images/freebase/02nbcyh.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_Canada.svg", "Canada", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["26", ".", "SENT", ".", "23", "SUFFIX", "visit", "visit", "-3", "0", "0", "0", ".", "0", "0", "https://en.wikipedia.org/wiki/Canada", "http://athena3.fit.vutbr.cz/kb/images/freebase/02nbcyh.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_Canada.svg", "Canada", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "On", "IN", "on", "5", "HMOD", "2006", "@card@", "+4", "0", "0", "0", "on", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "August", "NP", "August", "1", "SUFFIX", "On", "on", "-1", "0", "0", "0", "august", "t:acf3ccb424", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "22", "CD", "@card@", "2", "NMOD", "August", "August", "-1", "0", "0", "0", "22", "t:acf3ccb424", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", ",|GGG", ",", ",", "5", "P", "2006", "@card@", "+1", "0", "0", "0", ",", "t:acf3ccb424", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["0", "0", "2006", "8", "22", "date", "4"],
                "entityClass": "date",
                "words": [{
                    "type": "word",
                    "indexes": ["5", "2006", "CD", "@card@", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "2006", "t:acf3ccb424", "date", "0", "0", "2006", "8", "22", "0", "0", "0", "0", "0", "date", "4"]
                }, {
                    "type": "word",
                    "indexes": ["6", ",", ",", ",", "5", "NMOD", "2006", "@card@", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "2006", "8", "22", "0", "0", "0", "0", "0", "date", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["7", "three", "CD", "three", "17", "NMOD", "workshop", "workshop", "+10", "0", "0", "0", "three", "0", "0", "0", "0", "2006", "8", "22", "0", "0", "0", "0", "0", "date", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["8", "of", "IN", "of", "7", "NMOD", "three", "three", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "2006", "8", "22", "0", "0", "0", "0", "0", "date", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["9", "the", "DT", "the", "13", "NMOD", "forge", "forge", "+4", "0", "0", "0", "the", "0", "0", "0", "0", "2006", "8", "22", "0", "0", "0", "0", "0", "date", "-1"]
            }, {
                "type": "word",
                "indexes": ["10", "reconstructed", "JJ", "reconstructed", "13", "NMOD", "forge", "forge", "+3", "0", "0", "0", "reconstructed", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "buildings—the", "NN", "buildings—the", "12", "NMOD", "blacksmith", "blacksmith", "+1", "0", "0", "0", "buildings—the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "blacksmith", "NN", "blacksmith", "13", "NMOD", "forge", "forge", "+1", "https://en.wikipedia.org/wiki/Blacksmith", "1", "0", "blacksmith", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "forge", "NN", "forge", "8", "PMOD", "of", "of", "-5", "0", "0", "0", "forge", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", ",", ",", ",", "13", "P", "forge", "forge", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "carpenter", "NN", "carpenter", "13", "COORD", "forge", "forge", "-2", "0", "0", "0", "carpenter", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["16", "'s", "POS", "'s", "13", "SUFFIX", "forge", "forge", "-3", "0", "0", "0", "'s", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["17", "workshop", "NN", "workshop", "5", "NMOD", "2006", "@card@", "-12", "0", "0", "0", "workshop", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["18", "and", "CC", "and", "17", "COORD", "workshop", "workshop", "-1", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["19", "the", "DT", "the", "20", "NMOD", "chapel—were", "chapel—were", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["20", "chapel—were", "NN", "chapel—were", "18", "CONJ", "and", "and", "-2", "0", "0", "0", "chapel—were", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["21", "severely", "RB", "severely", "23", "PMOD", "in", "in", "+2", "0", "0", "0", "severely", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["22", "damaged", "VVN", "damage", "21", "SUFFIX", "severely", "severely", "-1", "0", "0", "0", "damaged", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["23", "in", "IN", "in", "17", "LOC", "workshop", "workshop", "-6", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["24", "a", "DT", "a", "25", "NMOD", "fire", "fire", "+1", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["25", "fire", "NN", "fire", "23", "PMOD", "in", "in", "-2", "0", "0", "0", "fire", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["26", ".", "SENT", ".", "25", "SUFFIX", "fire", "fire", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "They", "PP", "they", "0", "ROOT", "0", "0", "0", "0", "0", "0", "they", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "have", "VHP", "have", "1", "SUFFIX", "They", "they", "-1", "0", "0", "0", "have", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "since", "RB", "since", "1", "TMP", "They", "they", "-2", "0", "0", "0", "since", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "been", "VBN", "be", "1", "VC", "They", "they", "-3", "0", "0", "0", "been", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "rebuilt", "VVD", "rebuild", "1", "SUFFIX", "They", "they", "-4", "0", "0", "0", "rebuilt", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", ".", "SENT", ".", "1", "SUFFIX", "They", "they", "-5", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Affiliations", "NNS", "affiliation", "0", "ROOT", "0", "0", "0", "0", "0", "0", "affiliations", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "The", "DT", "the", "2", "NMOD", "museum", "museum", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "museum", "NN", "museum", "3", "SBJ", "is", "be", "+1", "0", "0", "0", "museum", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "is", "VBZ", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "is", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "affiliated", "VVN", "affiliate", "3", "SUFFIX", "is", "be", "-1", "0", "0", "0", "affiliated", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "with", "IN", "with", "3", "ADV", "is", "be", "-2", "0", "0", "0", "with", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "the", "DT", "the", "3", "NMOD", "is", "be", "-3", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Canadian_Martyrs", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg", "Canadian_Martyrs", "M", "France", "0", "Canada", "1642", "0", "0", "coref", "1"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["7", "Canadian", "NP", "Canadian", "6", "SUFFIX", "the", "the", "-1", "0", "0", "2", "canadian", "p:3f518162bb", "person", "https://en.wikipedia.org/wiki/Canadian_Martyrs", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg", "Canadian_Martyrs", "M", "France", "0", "Canada", "1642", "0", "0", "coref", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["8", "Museums", "NPS", "Museums", "6", "SUFFIX", "the", "the", "-2", "0", "0", "0", "museums", "c:2f4ad05085", "0", "https://en.wikipedia.org/wiki/Canadian_Martyrs", "http://athena3.fit.vutbr.cz/kb/images/freebase/03sc8hb.jpg", "Canadian_Martyrs", "M", "France", "0", "Canada", "1642", "0", "0", "coref", "-1"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Museums_Association", "0", "Museums_Association", "0", "0", "0", "0", "kb", "2"],
                "entityClass": "museum",
                "words": [{
                    "type": "word",
                    "indexes": ["9", "Association", "NP", "Association", "8", "SUFFIX", "Museums", "Museums", "-1", "https://en.wikipedia.org/wiki/Canadian_Museums_Association", "3", "4.2", "association", "c:2f4ad05085", "museum", "https://en.wikipedia.org/wiki/Museums_Association", "0", "Museums_Association", "0", "0", "0", "0", "0", "0", "0", "kb", "2"]
                }, {
                    "type": "word",
                    "indexes": ["10", ",", ",", ",", "3", "NMOD", "is", "be", "-7", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Museums_Association", "0", "Museums_Association", "0", "0", "0", "0", "0", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["11", "the", "DT", "the", "3", "NMOD", "is", "be", "-8", "0", "0", "0", "the", "0", "0", "https://en.wikipedia.org/wiki/Museums_Association", "0", "Museums_Association", "0", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["12", "Canadian", "NP", "Canadian", "11", "SUFFIX", "the", "the", "-1", "0", "0", "0", "canadian", "c:fdc828e346", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "Heritage", "NP", "Heritage", "11", "SUFFIX", "the", "the", "-2", "0", "0", "0", "heritage", "c:fdc828e346", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "Information", "NP", "Information", "11", "SUFFIX", "the", "the", "-3", "0", "0", "0", "information", "c:fdc828e346", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Canadian_Heritage_Information_Network", "0", "Canadian_Heritage_Information_Network", "0", "0", "0", "0", "kb", "4"],
                "entityClass": "museum",
                "words": [{
                    "type": "word",
                    "indexes": ["15", "Network", "NP", "Network", "11", "SUFFIX", "the", "the", "-4", "https://en.wikipedia.org/wiki/Canadian_Heritage_Information_Network", "4", "4.1", "network", "c:fdc828e346", "museum", "https://en.wikipedia.org/wiki/Canadian_Heritage_Information_Network", "0", "Canadian_Heritage_Information_Network", "0", "0", "0", "0", "0", "0", "0", "kb", "4"]
                }, {
                    "type": "word",
                    "indexes": ["16", "and", "CC", "and", "11", "COORD", "the", "the", "-5", "0", "0", "0", "and", "0", "0", "https://en.wikipedia.org/wiki/Canadian_Heritage_Information_Network", "0", "Canadian_Heritage_Information_Network", "0", "0", "0", "0", "0", "0", "0", "kb", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["17", "the", "DT", "the", "3", "NMOD", "is", "be", "-14", "0", "0", "0", "the", "0", "0", "https://en.wikipedia.org/wiki/Canadian_Heritage_Information_Network", "0", "Canadian_Heritage_Information_Network", "0", "0", "0", "0", "0", "0", "0", "kb", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["18", "Virtual", "NP", "Virtual", "17", "SUFFIX", "the", "the", "-1", "0", "0", "0", "virtual", "c:630c7079a9", "0", "https://en.wikipedia.org/wiki/Canadian_Heritage_Information_Network", "0", "Canadian_Heritage_Information_Network", "0", "0", "0", "0", "0", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["19", "Museum", "NP", "Museum", "17", "SUFFIX", "the", "the", "-2", "0", "0", "0", "museum", "c:630c7079a9", "0", "https://en.wikipedia.org/wiki/Canadian_Heritage_Information_Network", "0", "Canadian_Heritage_Information_Network", "0", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["20", "of", "IN", "of", "17", "NMOD", "the", "the", "-3", "0", "0", "0", "of", "c:630c7079a9", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Virtual_Museum_of_Canada", "http://athena3.fit.vutbr.cz/kb/images/freebase/05l3ppt.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0gzwmf2.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:Symbol_of_the_Virtual_Museum_of_Canada.jpg", "Virtual_Museum_of_Canada", "Virtual_Museum", "2001", "0", "0", "kb", "4"],
                "entityClass": "museum",
                "words": [{
                    "type": "word",
                    "indexes": ["21", "Canada", "NP", "Canada", "17", "SUFFIX", "the", "the", "-4", "https://en.wikipedia.org/wiki/Virtual_Museum_of_Canada", "4", "4.1", "canada", "c:630c7079a9", "museum", "https://en.wikipedia.org/wiki/Virtual_Museum_of_Canada", "http://athena3.fit.vutbr.cz/kb/images/freebase/05l3ppt.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0gzwmf2.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:Symbol_of_the_Virtual_Museum_of_Canada.jpg", "Virtual_Museum_of_Canada", "Virtual_Museum", "2001", "0", "0", "0", "0", "0", "kb", "4"]
                }, {
                    "type": "word",
                    "indexes": ["22", ".", "SENT", ".", "17", "SUFFIX", "the", "the", "-5", "0", "0", "0", ".", "0", "0", "https://en.wikipedia.org/wiki/Virtual_Museum_of_Canada", "http://athena3.fit.vutbr.cz/kb/images/freebase/05l3ppt.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0gzwmf2.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:Symbol_of_the_Virtual_Museum_of_Canada.jpg", "Virtual_Museum_of_Canada", "Virtual_Museum", "2001", "0", "0", "0", "0", "0", "kb", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["1", "Grounds", "NNS", "ground", "0", "ROOT", "0", "0", "0", "0", "0", "0", "grounds", "0", "0", "https://en.wikipedia.org/wiki/Virtual_Museum_of_Canada", "http://athena3.fit.vutbr.cz/kb/images/freebase/05l3ppt.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0gzwmf2.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:Symbol_of_the_Virtual_Museum_of_Canada.jpg", "Virtual_Museum_of_Canada", "Virtual_Museum", "2001", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Jesuit", "NP", "Jesuit", "0", "ROOT", "0", "0", "0", "0", "0", "0", "jesuit", "0", "0", "https://en.wikipedia.org/wiki/Virtual_Museum_of_Canada", "http://athena3.fit.vutbr.cz/kb/images/freebase/05l3ppt.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0gzwmf2.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:Symbol_of_the_Virtual_Museum_of_Canada.jpg", "Virtual_Museum_of_Canada", "Virtual_Museum", "2001", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "community", "NN", "community", "4", "NMOD", "building", "building", "+2", "0", "0", "0", "community", "0", "0", "https://en.wikipedia.org/wiki/Virtual_Museum_of_Canada", "http://athena3.fit.vutbr.cz/kb/images/freebase/05l3ppt.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0gzwmf2.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:Symbol_of_the_Virtual_Museum_of_Canada.jpg", "Virtual_Museum_of_Canada", "Virtual_Museum", "2001", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "reconstructed", "VVD", "reconstruct", "2", "SUFFIX", "community", "community", "-1", "0", "0", "0", "reconstructed", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "building", "NN", "building", "0", "ROOT", "0", "0", "0", "0", "0", "0", "building", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Jesuit", "NP", "Jesuit", "0", "ROOT", "0", "0", "0", "0", "0", "0", "jesuit", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "chapel", "JJ", "chapel", "3", "NMOD", "interior", "interior", "+1", "0", "0", "0", "chapel", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "interior", "NN", "interior", "0", "ROOT", "0", "0", "0", "0", "0", "0", "interior", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Reconstructed", "JJ", "reconstructed", "2", "NMOD", "buildings", "building", "+1", "0", "0", "0", "reconstructed", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "buildings", "NNS", "building", "6", "NMOD", "section", "section", "+4", "0", "0", "0", "buildings", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "in", "IN", "in", "2", "LOC", "buildings", "building", "-1", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "First", "NP", "First", "2", "SUFFIX", "buildings", "building", "-2", "0", "0", "0", "first", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "Nation", "NP", "Nation", "2", "SUFFIX", "buildings", "building", "-3", "0", "0", "0", "nation", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "section", "NN", "section", "0", "ROOT", "0", "0", "0", "0", "0", "0", "section", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "First", "NP", "First", "0", "ROOT", "0", "0", "0", "0", "0", "0", "first", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "Nations", "NPS", "Nations", "1", "SUFFIX", "First", "First", "-1", "0", "0", "0", "nations", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Chapel,_West_Virginia", "0", "Chapel", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["3", "Chapel", "NP", "Chapel", "1", "SUFFIX", "First", "First", "-2", "0", "0", "4.0", "chapel", "l:841bc96a67", "location", "https://en.wikipedia.org/wiki/Chapel,_West_Virginia", "0", "Chapel", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["4", "exterior", "NN", "exterior", "0", "ROOT", "0", "0", "0", "0", "0", "0", "exterior", "0", "0", "https://en.wikipedia.org/wiki/Chapel,_West_Virginia", "0", "Chapel", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Chapel,_West_Virginia", "0", "Chapel", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Chapel", "JJ", "chapel", "2", "NMOD", "interior", "interior", "+1", "0", "0", "4.0", "chapel", "l:841bc96a67", "location", "https://en.wikipedia.org/wiki/Chapel,_West_Virginia", "0", "Chapel", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["2", "interior", "NN", "interior", "0", "ROOT", "0", "0", "0", "0", "0", "0", "interior", "0", "0", "https://en.wikipedia.org/wiki/Chapel,_West_Virginia", "0", "Chapel", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Chapel,_West_Virginia", "0", "Chapel", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Chapel", "JJ", "chapel", "2", "NMOD", "altar", "altar", "+1", "0", "0", "4.0", "chapel", "l:841bc96a67", "location", "https://en.wikipedia.org/wiki/Chapel,_West_Virginia", "0", "Chapel", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["2", "altar", "NN", "altar", "0", "ROOT", "0", "0", "0", "0", "0", "0", "altar", "0", "0", "https://en.wikipedia.org/wiki/Chapel,_West_Virginia", "0", "Chapel", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "See", "VV", "see", "0", "ROOT", "0", "0", "0", "0", "0", "0", "see", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "also", "RB", "also", "1", "ADV", "See", "see", "-1", "0", "0", "0", "also", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Jesuit", "NP", "Jesuit", "0", "ROOT", "0", "0", "0", "0", "0", "0", "jesuit", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "missions", "NNS", "mission", "0", "ROOT", "0", "0", "0", "0", "0", "0", "missions", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "in", "IN", "in", "2", "LOC", "missions", "mission", "-1", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "North", "NP", "North", "2", "SUFFIX", "missions", "mission", "-2", "0", "0", "0", "north", "l:6d2aec8d13", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/North_America", "http://athena3.fit.vutbr.cz/kb/images/freebase/01z0kh_.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Location_North_America.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/02gqrbk.jpg", "North_America", "0", "kb", "2"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["5", "America", "NP", "America", "2", "SUFFIX", "missions", "mission", "-3", "https://en.wikipedia.org/wiki/Jesuit_missions_in_North_America", "5", "4.2", "america", "l:6d2aec8d13", "location", "https://en.wikipedia.org/wiki/North_America", "http://athena3.fit.vutbr.cz/kb/images/freebase/01z0kh_.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Location_North_America.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/02gqrbk.jpg", "North_America", "0", "0", "0", "0", "0", "0", "0", "kb", "2"]
                }, {
                    "type": "word",
                    "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Sainte", "NP", "Sainte", "0", "ROOT", "0", "0", "0", "0", "0", "0", "sainte", "c:477faa20b0", "0", "https://en.wikipedia.org/wiki/North_America", "http://athena3.fit.vutbr.cz/kb/images/freebase/01z0kh_.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Location_North_America.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/02gqrbk.jpg", "North_America", "0", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "Marie", "NP", "Marie", "1", "SUFFIX", "Sainte", "Sainte", "-1", "0", "0", "0", "marie", "c:477faa20b0", "0", "https://en.wikipedia.org/wiki/North_America", "http://athena3.fit.vutbr.cz/kb/images/freebase/01z0kh_.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Location_North_America.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/02gqrbk.jpg", "North_America", "0", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "among", "IN", "among", "1", "LOC", "Sainte", "Sainte", "-2", "0", "0", "0", "among", "c:477faa20b0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "the", "DT", "the", "1", "NMOD", "Sainte", "Sainte", "-3", "0", "0", "0", "the", "c:477faa20b0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Sainte_Marie_among_the_Iroquois", "0", "Sainte_Marie_among_the_Iroquois", "0", "1990_0", "0", "0", "kb", "5"],
                "entityClass": "museum",
                "words": [{
                    "type": "word",
                    "indexes": ["5", "Iroquois", "NP", "Iroquois", "4", "SUFFIX", "the", "the", "-1", "https://en.wikipedia.org/wiki/Sainte_Marie_among_the_Iroquois", "5", "4.1", "iroquois", "c:477faa20b0", "museum", "https://en.wikipedia.org/wiki/Sainte_Marie_among_the_Iroquois", "0", "Sainte_Marie_among_the_Iroquois", "0", "1990_0", "0", "0", "0", "0", "0", "kb", "5"]
                }, {
                    "type": "word",
                    "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["1", "Wyandot", "NP", "Wyandot", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "wyandot", "l:dc634855a2", "location", "https://en.wikipedia.org/wiki/Sainte_Marie_among_the_Iroquois", "0", "Sainte_Marie_among_the_Iroquois", "0", "1990_0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "people", "NNS", "people", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/Wyandot_people", "2", "0", "people", "0", "0", "https://en.wikipedia.org/wiki/Sainte_Marie_among_the_Iroquois", "0", "Sainte_Marie_among_the_Iroquois", "0", "1990_0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "References", "NNS", "reference", "0", "ROOT", "0", "0", "0", "0", "0", "0", "references", "0", "0", "https://en.wikipedia.org/wiki/Sainte_Marie_among_the_Iroquois", "0", "Sainte_Marie_among_the_Iroquois", "0", "1990_0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "↑", "NP", "↑", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons#cite_ref-1", "1", "0", "↑", "0", "0", "https://en.wikipedia.org/wiki/Sainte_Marie_among_the_Iroquois", "0", "Sainte_Marie_among_the_Iroquois", "0", "1990_0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "Sainte-Marie", "NP", "Sainte-Marie", "1", "SUFFIX", "↑", "↑", "-1", "0", "0", "0", "sainte-marie", "c:9d7ba74864", "0", "https://en.wikipedia.org/wiki/Sainte_Marie_among_the_Iroquois", "0", "Sainte_Marie_among_the_Iroquois", "0", "1990_0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "Among", "IN", "among", "1", "NMOD", "↑", "↑", "-2", "0", "0", "0", "among", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "the", "DT", "the", "14", "NMOD", "Significance", "significance", "+10", "0", "0", "0", "the", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "Hurons", "NP", "Hurons", "4", "SUFFIX", "the", "the", "-1", "0", "0", "0", "hurons", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "kb", "5"],
                "entityClass": "museum",
                "words": [{
                    "type": "word",
                    "indexes": ["6", "Mission", "NP", "Mission", "5", "SUFFIX", "Hurons", "Hurons", "-1", "http://www.pc.gc.ca/apps/lhn-nhs/det_E.asp?oqSID=0845&oqeName=Sainte-Marie+Among+the+Hurons+Mission&oqfName=Poste+de+Sainte-Marie-au-Pays-des-Hurons", "5", "4.2", "mission", "c:9d7ba74864", "museum", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "5"]
                }, {
                    "type": "word",
                    "indexes": ["7", ",", ",", ",", "4", "P", "the", "the", "-3", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
                }]
            }, {
                "type": "word",
                "indexes": ["8", "Directory", "NP", "Directory", "4", "SUFFIX", "the", "the", "-4", "0", "0", "4.0", "directory", "c:d47fb1ecf2", "museum", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["9", "of", "IN", "of", "4", "NMOD", "the", "the", "-5", "0", "0", "0", "of", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["10", "Designations", "NP", "Designations", "4", "SUFFIX", "the", "the", "-6", "0", "0", "0", "designations", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["11", "of", "IN", "of", "4", "NMOD", "the", "the", "-7", "0", "0", "0", "of", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["12", "National", "NP", "National", "4", "SUFFIX", "the", "the", "-8", "0", "0", "0", "national", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", "Historic", "JJ", "historic", "14", "NMOD", "Significance", "significance", "+1", "0", "0", "0", "historic", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "Significance", "NN", "significance", "3", "PMOD", "Among", "among", "-11", "0", "0", "0", "significance", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["15", "of", "IN", "of", "14", "NMOD", "Significance", "significance", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Canada", "http://athena3.fit.vutbr.cz/kb/images/freebase/02nbcyh.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_Canada.svg", "Canada", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["16", "Canada", "NP", "Canada", "14", "SUFFIX", "Significance", "significance", "-2", "0", "0", "4.0", "canada", "l:87165b8173", "location", "https://en.wikipedia.org/wiki/Canada", "http://athena3.fit.vutbr.cz/kb/images/freebase/02nbcyh.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_Canada.svg", "Canada", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "↑", "NP", "↑", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons#cite_ref-2", "1", "0", "↑", "0", "0", "https://en.wikipedia.org/wiki/Canada", "http://athena3.fit.vutbr.cz/kb/images/freebase/02nbcyh.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_Canada.svg", "Canada", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "Sainte-Marie", "NP", "Sainte-Marie", "1", "SUFFIX", "↑", "↑", "-1", "0", "0", "0", "sainte-marie", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "Among", "IN", "among", "1", "NMOD", "↑", "↑", "-2", "0", "0", "0", "among", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "the", "DT", "the", "1", "NMOD", "↑", "↑", "-3", "0", "0", "0", "the", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "Hurons", "NP", "Hurons", "4", "SUFFIX", "the", "the", "-1", "0", "0", "0", "hurons", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "kb", "5"],
                "entityClass": "museum",
                "words": [{
                    "type": "word",
                    "indexes": ["6", "Mission", "NP", "Mission", "4", "SUFFIX", "the", "the", "-2", "http://www.historicplaces.ca/en/rep-reg/place-lieu.aspx?id=12088&pid=0", "5", "4.2", "mission", "c:9d7ba74864", "museum", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "5"]
                }, {
                    "type": "word",
                    "indexes": ["7", ".", "SENT", ".", "4", "SUFFIX", "the", "the", "-3", "0", "0", "0", ".", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["1", "Canadian", "NP", "Canadian", "0", "ROOT", "0", "0", "0", "0", "0", "2", "canadian", "p:3f518162bb", "person", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "Register", "NP", "Register", "1", "SUFFIX", "Canadian", "Canadian", "-1", "0", "0", "0", "register", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["3", "of", "IN", "of", "2", "NMOD", "Register", "Register", "-1", "0", "0", "0", "of", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["4", "Historic", "JJ", "historic", "1", "NMOD", "Canadian", "Canadian", "-3", "0", "0", "0", "historic", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["5", "Places", "NP", "Places", "4", "SUFFIX", "Historic", "historic", "-1", "https://en.wikipedia.org/wiki/Canadian_Register_of_Historic_Places", "5", "0", "places", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", ".", "SENT", ".", "4", "SUFFIX", "Historic", "historic", "-2", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "↑", "NP", "↑", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons#cite_ref-3", "1", "0", "↑", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "Article", "NP", "Article", "1", "SUFFIX", "↑", "↑", "-1", "0", "0", "0", "article", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "on", "IN", "on", "1", "NMOD", "↑", "↑", "-2", "0", "0", "0", "on", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["4", "Sainte-Marie", "NP", "Sainte-Marie", "3", "SUFFIX", "on", "on", "-1", "http://www.thecanadianencyclopedia.ca/en/article/ste-marie-among-the-hurons/", "3", "4.2", "sainte-marie", "l:81213c12e1", "location", "https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["5", ",", ",", ",", "1", "NMOD", "↑", "↑", "-4", "0", "0", "0", ",", "0", "0", "https://en.wikipedia.org/wiki/Ste._Marie,_Illinois", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Illinois_-_outline_map.svg", "Ste__Marie__Illinois", "United_States_of_America", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["6", "The", "DT", "the", "1", "NMOD", "↑", "↑", "-5", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "Canadian", "NP", "Canadian", "6", "SUFFIX", "The", "the", "-1", "0", "0", "0", "canadian", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "Encyclopedia", "NP", "Encyclopedia", "6", "SUFFIX", "The", "the", "-2", "0", "0", "0", "encyclopedia", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Wikimedia", "NP", "Wikimedia", "0", "ROOT", "0", "0", "0", "0", "0", "0", "wikimedia", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "Commons", "NP", "Commons", "1", "SUFFIX", "Wikimedia", "Wikimedia", "-1", "0", "0", "0", "commons", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "has", "VHZ", "have", "1", "SUFFIX", "Wikimedia", "Wikimedia", "-2", "0", "0", "0", "has", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "media", "NNS", "medium", "0", "ROOT", "0", "0", "0", "0", "0", "0", "media", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "related", "VVN", "relate", "4", "SUFFIX", "media", "medium", "-1", "0", "0", "0", "related", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "to", "TO", "to", "4", "NMOD", "media", "medium", "-2", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "Sainte-Marie", "NP", "Sainte-Marie", "4", "SUFFIX", "media", "medium", "-3", "0", "0", "0", "sainte-marie", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "among", "IN", "among", "4", "LOC", "media", "medium", "-4", "0", "0", "0", "among", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "the", "DT", "the", "4", "NMOD", "media", "medium", "-5", "0", "0", "0", "the", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "kb", "4"],
                "entityClass": "museum",
                "words": [{
                    "type": "word",
                    "indexes": ["10", "Hurons", "NP", "Hurons", "9", "SUFFIX", "the", "the", "-1", "https://commons.wikimedia.org/wiki/Special:Search/Sainte-Marie%20among%20the%20Hurons", "4", "4.2", "hurons", "c:9d7ba74864", "museum", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "4"]
                }, {
                    "type": "word",
                    "indexes": ["11", ".", "SENT", ".", "9", "SUFFIX", "the", "the", "-2", "0", "0", "0", ".", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["1", "External", "JJ", "external", "2", "NMOD", "links", "link", "+1", "0", "0", "0", "external", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "links", "NNS", "link", "0", "ROOT", "0", "0", "0", "0", "0", "0", "links", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Sainte-Marie", "NN", "Sainte-Marie", "0", "ROOT", "0", "0", "0", "0", "0", "0", "sainte-marie", "c:9d7ba74864", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "among", "IN", "among", "1", "LOC", "Sainte-Marie", "Sainte-Marie", "-1", "0", "0", "0", "among", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "the", "DT", "the", "6", "NMOD", "site", "site", "+3", "0", "0", "0", "the", "c:9d7ba74864", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "kb", "4"],
                "entityClass": "museum",
                "words": [{
                    "type": "word",
                    "indexes": ["4", "Hurons", "NP", "Hurons", "3", "SUFFIX", "the", "the", "-1", "0", "0", "4.0", "hurons", "c:9d7ba74864", "museum", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "4"]
                }, {
                    "type": "word",
                    "indexes": ["5", "official", "NN", "official", "6", "NMOD", "site", "site", "+1", "0", "0", "0", "official", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["6", "site", "NN", "site", "2", "PMOD", "among", "among", "-4", "http://www.saintemarieamongthehurons.on.ca/", "6", "0", "site", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
                }, {
                    "type": "word",
                    "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Jesuit", "NP", "Jesuit", "0", "ROOT", "0", "0", "0", "0", "0", "0", "jesuit", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "Relations", "NPS", "Relations", "1", "SUFFIX", "Jesuit", "Jesuit", "-1", "http://puffin.creighton.edu/jesuit/relations", "2", "0", "relations", "0", "0", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons", "http://athena3.fit.vutbr.cz/kb/images/freebase/03qzv67.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02drtst.jpg|http://athena3.fit.vutbr.cz/kb/images/http://en.wikipedia.org/wiki/File:St_Marie_Hurons_1.jpg", "Sainte_Marie_among_the_Hurons", "Open_air_museum|Living_museum", "1634", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Coordinates", "NNS", "coordinate", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/Geographic_coordinate_system", "1", "0", "coordinates", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", ":", ":", ":", "1", "P", "Coordinates", "coordinate", "-1", "0", "0", "0", ":", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "44°44′03.00″N", "NP", "44°44′03.00″N", "1", "SUFFIX", "Coordinates", "coordinate", "-2", "geo:44.73416666666667,-79.84527777777777", "1", "0", "44°44′03.00″n", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "79°50′43.00″W", "NP", "79°50′43.00″W", "1", "SUFFIX", "Coordinates", "coordinate", "-3", "geo:44.73416666666667,-79.84527777777777", "1", "0", "79°50′43.00″w", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "﻿", "NP", "﻿", "1", "SUFFIX", "Coordinates", "coordinate", "-4", "geo:44.73416666666667,-79.84527777777777", "1", "0", "﻿", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "/", "SYM", "/", "10", "NMOD", "﻿", "﻿", "+4", "geo:44.73416666666667,-79.84527777777777", "1", "0", "/", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "﻿", "JJ", "﻿", "10", "NMOD", "﻿", "﻿", "+3", "geo:44.73416666666667,-79.84527777777777", "1", "0", "﻿", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "44.7341667°N", "JJ", "44.7341667°N", "10", "NMOD", "﻿", "﻿", "+2", "0", "0", "0", "44.7341667°n", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "79.8452778°W", "JJ", "79.8452778°W", "10", "NMOD", "﻿", "﻿", "+1", "geo:44.73416666666667,-79.84527777777777", "2", "0", "79.8452778°w", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "﻿", "NN", "﻿", "11", "AMOD", "/", "/", "+1", "geo:44.73416666666667,-79.84527777777777", "1", "0", "﻿", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "/", "SYM", "/", "0", "ROOT", "0", "0", "0", "geo:44.73416666666667,-79.84527777777777", "1", "0", "/", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", "44.7341667", "CD", "@card@", "11", "NMOD", "/", "/", "-1", "0", "0", "0", "44.7341667", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["13", ";", ":", ";", "11", "P", "/", "/", "-2", "0", "0", "0", ";", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["14", "-79.8452778", "CD", "@card@", "11", "NMOD", "/", "/", "-3", "geo:44.73416666666667,-79.84527777777777", "3", "0", "-79.8452778", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "__IMG__", "NP", "__IMG__", "0", "ROOT", "0", "0", "0", "http://en.wikipedia.org/I/m/Maple_Leaf_(from_roundel).svg.png", "1", "0", "__img__", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "National", "NP", "National", "1", "SUFFIX", "__IMG__", "__IMG__", "-1", "0", "0", "0", "national", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "Historic", "JJ", "historic", "7", "NMOD", "by", "by", "+4", "0", "0", "0", "historic", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "Sites", "NP", "Sites", "3", "SUFFIX", "Historic", "historic", "-1", "0", "0", "0", "sites", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "of", "IN", "of", "7", "NMOD", "by", "by", "+2", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Canada", "http://athena3.fit.vutbr.cz/kb/images/freebase/02nbcyh.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_Canada.svg", "Canada", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["6", "Canada", "NP", "Canada", "5", "SUFFIX", "of", "of", "-1", "https://en.wikipedia.org/wiki/National_Historic_Sites_of_Canada", "5", "4.2", "canada", "l:87165b8173", "location", "https://en.wikipedia.org/wiki/Canada", "http://athena3.fit.vutbr.cz/kb/images/freebase/02nbcyh.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_Canada.svg", "Canada", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["7", "by", "IN", "by", "1", "LGS", "__IMG__", "__IMG__", "-6", "0", "0", "0", "by", "0", "0", "https://en.wikipedia.org/wiki/Canada", "http://athena3.fit.vutbr.cz/kb/images/freebase/02nbcyh.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_Canada.svg", "Canada", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["8", "location", "NN", "location", "7", "PMOD", "by", "by", "-1", "0", "0", "0", "location", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Provinces", "NNS", "province", "0", "ROOT", "0", "0", "0", "0", "0", "0", "provinces", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Alberta", "http://athena3.fit.vutbr.cz/kb/images/freebase/03tcfpj.jpg", "Alberta", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Alberta", "NP", "Alberta", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Alberta", "1", "4.2", "alberta", "l:c21573c6d5", "location", "https://en.wikipedia.org/wiki/Alberta", "http://athena3.fit.vutbr.cz/kb/images/freebase/03tcfpj.jpg", "Alberta", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "British", "NP", "British", "0", "ROOT", "0", "0", "0", "0", "0", "0", "british", "l:cf1c1e5cf3", "0", "https://en.wikipedia.org/wiki/Alberta", "http://athena3.fit.vutbr.cz/kb/images/freebase/03tcfpj.jpg", "Alberta", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/British_Columbia", "http://athena3.fit.vutbr.cz/kb/images/freebase/04s2swp.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03stp7w.jpg", "British_Columbia", "Canada", "kb", "2"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["2", "Columbia", "NP", "Columbia", "1", "SUFFIX", "British", "British", "-1", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_British_Columbia", "2", "4.2", "columbia", "l:cf1c1e5cf3", "location", "https://en.wikipedia.org/wiki/British_Columbia", "http://athena3.fit.vutbr.cz/kb/images/freebase/04s2swp.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03stp7w.jpg", "British_Columbia", "Canada", "0", "0", "0", "0", "0", "0", "kb", "2"]
                }, {
                    "type": "word",
                    "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Manitoba", "NP", "Manitoba", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Manitoba", "1", "4.2", "manitoba", "l:929db146d3", "location", "https://en.wikipedia.org/wiki/British_Columbia", "http://athena3.fit.vutbr.cz/kb/images/freebase/04s2swp.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03stp7w.jpg", "British_Columbia", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "New", "NP", "New", "0", "ROOT", "0", "0", "0", "0", "0", "0", "new", "l:e9cd9cfc4b", "0", "https://en.wikipedia.org/wiki/British_Columbia", "http://athena3.fit.vutbr.cz/kb/images/freebase/04s2swp.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03stp7w.jpg", "British_Columbia", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/New_Brunswick", "http://athena3.fit.vutbr.cz/kb/images/freebase/044n02g.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02d_3qf.jpg", "New_Brunswick_Nouveau_Brunswick", "Canada", "kb", "2"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["2", "Brunswick", "NP", "Brunswick", "1", "SUFFIX", "New", "New", "-1", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_New_Brunswick", "2", "4.2", "brunswick", "l:e9cd9cfc4b", "location", "https://en.wikipedia.org/wiki/New_Brunswick", "http://athena3.fit.vutbr.cz/kb/images/freebase/044n02g.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02d_3qf.jpg", "New_Brunswick_Nouveau_Brunswick", "Canada", "0", "0", "0", "0", "0", "0", "kb", "2"]
                }, {
                    "type": "word",
                    "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Newfoundland", "NP", "Newfoundland", "0", "ROOT", "0", "0", "0", "0", "0", "0", "newfoundland", "l:a8a56c328c", "0", "https://en.wikipedia.org/wiki/New_Brunswick", "http://athena3.fit.vutbr.cz/kb/images/freebase/044n02g.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02d_3qf.jpg", "New_Brunswick_Nouveau_Brunswick", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "and", "CC", "and", "1", "COORD", "Newfoundland", "Newfoundland", "-1", "0", "0", "0", "and", "l:a8a56c328c", "0", "https://en.wikipedia.org/wiki/New_Brunswick", "http://athena3.fit.vutbr.cz/kb/images/freebase/044n02g.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02d_3qf.jpg", "New_Brunswick_Nouveau_Brunswick", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Newfoundland_and_Labrador", "http://athena3.fit.vutbr.cz/kb/images/freebase/02h19gv.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02d68kh.jpg", "Newfoundland_and_Labrador", "Canada", "kb", "3"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["3", "Labrador", "NP", "Labrador", "1", "SUFFIX", "Newfoundland", "Newfoundland", "-2", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Newfoundland_and_Labrador", "3", "4.2", "labrador", "l:a8a56c328c", "location", "https://en.wikipedia.org/wiki/Newfoundland_and_Labrador", "http://athena3.fit.vutbr.cz/kb/images/freebase/02h19gv.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02d68kh.jpg", "Newfoundland_and_Labrador", "Canada", "0", "0", "0", "0", "0", "0", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["1", "Nova", "NP", "Nova", "0", "ROOT", "0", "0", "0", "0", "0", "0", "nova", "l:f1b4e6c9d7", "0", "https://en.wikipedia.org/wiki/Newfoundland_and_Labrador", "http://athena3.fit.vutbr.cz/kb/images/freebase/02h19gv.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02d68kh.jpg", "Newfoundland_and_Labrador", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "Scotia", "NP", "Scotia", "1", "SUFFIX", "Nova", "Nova", "-1", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Nova_Scotia", "2", "4.2", "scotia", "l:f1b4e6c9d7", "location", "https://en.wikipedia.org/wiki/Newfoundland_and_Labrador", "http://athena3.fit.vutbr.cz/kb/images/freebase/02h19gv.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02d68kh.jpg", "Newfoundland_and_Labrador", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Ontario", "NP", "Ontario", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Ontario", "1", "4.2", "ontario", "l:8a4e54e619", "location", "https://en.wikipedia.org/wiki/Newfoundland_and_Labrador", "http://athena3.fit.vutbr.cz/kb/images/freebase/02h19gv.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02d68kh.jpg", "Newfoundland_and_Labrador", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Lewis_Hamilton", "http://athena3.fit.vutbr.cz/kb/images/freebase/03t96lz.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0427wnw.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04xynp_.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Lewis_Hamilton_October_2014.jpg", "Lewis_Hamilton", "M", "Stevenage", "1985_01_07", "0", "0", "0", "British", "kb", "1"],
                "entityClass": "person",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Hamilton", "NP", "Hamilton", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Hamilton,_Ontario", "1", "4.2", "hamilton", "p:aaf5f4f473", "person", "https://en.wikipedia.org/wiki/Lewis_Hamilton", "http://athena3.fit.vutbr.cz/kb/images/freebase/03t96lz.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0427wnw.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04xynp_.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Lewis_Hamilton_October_2014.jpg", "Lewis_Hamilton", "M", "Stevenage", "1985_01_07", "0", "0", "0", "British", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Kingston", "NP", "Kingston", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Kingston,_Ontario", "1", "4.2", "kingston", "l:c4eeff8bab", "location", "https://en.wikipedia.org/wiki/Lewis_Hamilton", "http://athena3.fit.vutbr.cz/kb/images/freebase/03t96lz.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0427wnw.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04xynp_.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Lewis_Hamilton_October_2014.jpg", "Lewis_Hamilton", "M", "Stevenage", "1985_01_07", "0", "0", "0", "British", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Niagara,_Oregon", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_Santiam_River_at_Niagara_County_Park_06268.JPG", "Niagara", "United_States", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Niagara", "NP", "Niagara", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Niagara_Region", "1", "4.2", "niagara", "l:bef539a5ce", "location", "https://en.wikipedia.org/wiki/Niagara,_Oregon", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_Santiam_River_at_Niagara_County_Park_06268.JPG", "Niagara", "United_States", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Ottawa", "NP", "Ottawa", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Ottawa", "1", "4.2", "ottawa", "l:cbb60d39fd", "location", "https://en.wikipedia.org/wiki/Niagara,_Oregon", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/North_Santiam_River_at_Niagara_County_Park_06268.JPG", "Niagara", "United_States", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Toronto", "http://athena3.fit.vutbr.cz/kb/images/freebase/03rbl5x.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Montage_of_Toronto_7.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04r6w79.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0292s3l.jpg", "Toronto", "Canada", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "Toronto", "NP", "Toronto", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Toronto", "1", "4.2", "toronto", "l:86c446608c", "location", "https://en.wikipedia.org/wiki/Toronto", "http://athena3.fit.vutbr.cz/kb/images/freebase/03rbl5x.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Montage_of_Toronto_7.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04r6w79.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0292s3l.jpg", "Toronto", "Canada", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Prince", "NP", "Prince", "0", "ROOT", "0", "0", "0", "0", "0", "0", "prince", "l:45198b6ff7", "0", "https://en.wikipedia.org/wiki/Toronto", "http://athena3.fit.vutbr.cz/kb/images/freebase/03rbl5x.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Montage_of_Toronto_7.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04r6w79.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0292s3l.jpg", "Toronto", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "Edward", "NP", "Edward", "1", "SUFFIX", "Prince", "Prince", "-1", "0", "0", "0", "edward", "l:45198b6ff7", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Prince_Edward_Island", "http://athena3.fit.vutbr.cz/kb/images/freebase/042610n.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04rwzl8.jpg", "Prince_Edward_Island", "Canada", "kb", "3"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["3", "Island", "NP", "Island", "1", "SUFFIX", "Prince", "Prince", "-2", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Prince_Edward_Island", "3", "4.2", "island", "l:45198b6ff7", "location", "https://en.wikipedia.org/wiki/Prince_Edward_Island", "http://athena3.fit.vutbr.cz/kb/images/freebase/042610n.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04rwzl8.jpg", "Prince_Edward_Island", "Canada", "0", "0", "0", "0", "0", "0", "kb", "3"]
                }, {
                    "type": "word",
                    "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["1", "Quebec", "NP", "Quebec", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Quebec", "1", "4.2", "quebec", "l:acfa48a7dd", "location", "https://en.wikipedia.org/wiki/Prince_Edward_Island", "http://athena3.fit.vutbr.cz/kb/images/freebase/042610n.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04rwzl8.jpg", "Prince_Edward_Island", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Montreal", "NP", "Montreal", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Montreal", "1", "4.2", "montreal", "l:eafa36fedb", "location", "https://en.wikipedia.org/wiki/Prince_Edward_Island", "http://athena3.fit.vutbr.cz/kb/images/freebase/042610n.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04rwzl8.jpg", "Prince_Edward_Island", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Quebec", "NP", "Quebec", "0", "ROOT", "0", "0", "0", "0", "0", "0", "quebec", "l:8de47b98b6", "0", "https://en.wikipedia.org/wiki/Prince_Edward_Island", "http://athena3.fit.vutbr.cz/kb/images/freebase/042610n.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/04rwzl8.jpg", "Prince_Edward_Island", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Quebec_City", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Quebec_City_Montage.png|http://athena3.fit.vutbr.cz/kb/images/freebase/0bcv9ww.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0bnbyfc.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/041pnck.jpg", "Quebec", "Canada", "kb", "2"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["2", "City", "NP", "City", "1", "SUFFIX", "Quebec", "Quebec", "-1", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Quebec_City", "2", "4.2", "city", "l:8de47b98b6", "location", "https://en.wikipedia.org/wiki/Quebec_City", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Quebec_City_Montage.png|http://athena3.fit.vutbr.cz/kb/images/freebase/0bcv9ww.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0bnbyfc.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/041pnck.jpg", "Quebec", "Canada", "0", "0", "0", "0", "0", "0", "kb", "2"]
                }, {
                    "type": "word",
                    "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Saskatchewan", "NP", "Saskatchewan", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Saskatchewan", "1", "4.2", "saskatchewan", "l:f69ac398cf", "location", "https://en.wikipedia.org/wiki/Quebec_City", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Quebec_City_Montage.png|http://athena3.fit.vutbr.cz/kb/images/freebase/0bcv9ww.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0bnbyfc.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/041pnck.jpg", "Quebec", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Territories", "NNS", "territory", "0", "ROOT", "0", "0", "0", "0", "0", "0", "territories", "0", "0", "https://en.wikipedia.org/wiki/Quebec_City", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Quebec_City_Montage.png|http://athena3.fit.vutbr.cz/kb/images/freebase/0bcv9ww.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0bnbyfc.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/041pnck.jpg", "Quebec", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Northwest", "NP", "Northwest", "0", "ROOT", "0", "0", "0", "0", "0", "0", "northwest", "l:0e9a682d9d", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/Northwest_Territories", "http://athena3.fit.vutbr.cz/kb/images/freebase/044mlg3.jpg", "Northwest_Territories", "Canada", "kb", "2"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["2", "Territories", "NP", "Territories", "1", "SUFFIX", "Northwest", "Northwest", "-1", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_the_Northwest_Territories", "2", "4.2", "territories", "l:0e9a682d9d", "location", "https://en.wikipedia.org/wiki/Northwest_Territories", "http://athena3.fit.vutbr.cz/kb/images/freebase/044mlg3.jpg", "Northwest_Territories", "Canada", "0", "0", "0", "0", "0", "0", "kb", "2"]
                }, {
                    "type": "word",
                    "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Nunavut", "NP", "Nunavut", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Nunavut", "1", "4.2", "nunavut", "l:345b515f3f", "location", "https://en.wikipedia.org/wiki/Northwest_Territories", "http://athena3.fit.vutbr.cz/kb/images/freebase/044mlg3.jpg", "Northwest_Territories", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Yukon", "NP", "Yukon", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_Yukon", "1", "4.2", "yukon", "l:ad1099adcd", "location", "https://en.wikipedia.org/wiki/Northwest_Territories", "http://athena3.fit.vutbr.cz/kb/images/freebase/044mlg3.jpg", "Northwest_Territories", "Canada", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Other", "JJ", "other", "2", "NMOD", "countries", "country", "+1", "0", "0", "0", "other", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "countries", "NNS", "country", "0", "ROOT", "0", "0", "0", "0", "0", "0", "countries", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_France.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "Republic_of_France", "France", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["1", "France", "NP", "France", "0", "ROOT", "0", "0", "0", "https://en.wikipedia.org/wiki/List_of_National_Historic_Sites_of_Canada_in_France", "1", "4.2", "france", "l:17eddfc4a7", "location", "https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_France.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "Republic_of_France", "France", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "__IMG__", "NP", "__IMG__", "0", "ROOT", "0", "0", "0", "http://en.wikipedia.org/I/m/Folder_Hexagonal_Icon.svg.png", "1", "0", "__img__", "0", "0", "https://en.wikipedia.org/wiki/France", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Flag_of_France.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03t44_5.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/042gv1r.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02h7nzl.jpg", "Republic_of_France", "France", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "Category", "NP", "Category", "1", "SUFFIX", "__IMG__", "__IMG__", "-1", "0", "0", "0", "category", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "__IMG__", "NP", "__IMG__", "0", "ROOT", "0", "0", "0", "http://en.wikipedia.org/I/m/Portal-puzzle.svg.png", "1", "0", "__img__", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "entity",
                "attributes": ["https://en.wikipedia.org/wiki/The_Portal", "0", "The_Portal", "0", "kb", "1"],
                "entityClass": "location",
                "words": [{
                    "type": "word",
                    "indexes": ["2", "Portal", "NP", "Portal", "1", "SUFFIX", "__IMG__", "__IMG__", "-1", "0", "0", "4.0", "portal", "l:465d58defe", "location", "https://en.wikipedia.org/wiki/The_Portal", "0", "The_Portal", "0", "0", "0", "0", "0", "0", "0", "kb", "1"]
                }]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "__IMG__", "NP", "__IMG__", "0", "ROOT", "0", "0", "0", "http://en.wikipedia.org/I/m/People_icon.svg.png", "1", "0", "__img__", "0", "0", "https://en.wikipedia.org/wiki/The_Portal", "0", "The_Portal", "0", "0", "0", "0", "0", "0", "0", "kb", "-1"]
            }, {
                "type": "word",
                "indexes": ["2", "WikiProject", "NP", "WikiProject", "1", "SUFFIX", "__IMG__", "__IMG__", "-1", "0", "0", "0", "wikiproject", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "This", "DT", "this", "2", "NMOD", "article", "article", "+1", "0", "0", "0", "this", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "article", "NN", "article", "3", "SBJ", "is", "be", "+1", "0", "0", "0", "article", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "is", "VBZ", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "is", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "issued", "VVN", "issue", "3", "SUFFIX", "is", "be", "-1", "0", "0", "0", "issued", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "from", "IN", "from", "3", "PRD", "is", "be", "-2", "0", "0", "0", "from", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "Wikipedia", "NP", "Wikipedia", "3", "SUFFIX", "is", "be", "-3", "https://en.wikipedia.org/wiki/Sainte-Marie_among_the_Hurons?oldid=849184305", "1", "0", "wikipedia", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", ".", "SENT", ".", "3", "SUFFIX", "is", "be", "-4", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "The", "DT", "the", "2", "NMOD", "text", "text", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "text", "NN", "text", "3", "SBJ", "is", "be", "+1", "0", "0", "0", "text", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "is", "VBZ", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "is", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "licensed", "VVN", "license", "3", "SUFFIX", "is", "be", "-1", "0", "0", "0", "licensed", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "under", "IN", "under", "3", "PRD", "is", "be", "-2", "0", "0", "0", "under", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "Creative", "NP", "Creative", "3", "SUFFIX", "is", "be", "-3", "0", "0", "0", "creative", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "Commons", "NPS", "Commons", "3", "SUFFIX", "is", "be", "-4", "0", "0", "0", "commons", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "-", ":", "-", "3", "P", "is", "be", "-5", "0", "0", "0", "-", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", "Attribution", "NP", "Attribution", "3", "SUFFIX", "is", "be", "-6", "0", "0", "0", "attribution", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["10", "-", ":", "-", "3", "P", "is", "be", "-7", "0", "0", "0", "-", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["11", "Sharealike", "JJ", "Sharealike", "3", "PRD", "is", "be", "-8", "http://creativecommons.org/licenses/by-sa/4.0/", "6", "0", "sharealike", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["12", ".", "SENT", ".", "3", "SUFFIX", "is", "be", "-9", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["1", "Additional", "JJ", "additional", "2", "NMOD", "terms", "term", "+1", "0", "0", "0", "additional", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["2", "terms", "NNS", "term", "3", "SBJ", "may", "may", "+1", "0", "0", "0", "terms", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["3", "may", "MD", "may", "0", "ROOT", "0", "0", "0", "0", "0", "0", "may", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["4", "apply", "VV", "apply", "3", "SUFFIX", "may", "may", "-1", "0", "0", "0", "apply", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["5", "for", "IN", "for", "3", "ADV", "may", "may", "-2", "0", "0", "0", "for", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["6", "the", "DT", "the", "8", "NMOD", "files", "file", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["7", "media", "NNS", "medium", "8", "NMOD", "files", "file", "+1", "0", "0", "0", "media", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["8", "files", "NNS", "file", "5", "PMOD", "for", "for", "-3", "0", "0", "0", "files", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }, {
                "type": "word",
                "indexes": ["9", ".", "SENT", ".", "8", "SUFFIX", "files", "file", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
            }]
    }
;

it("parse new annotated text performance test", () => {
    const [avg, deviation] = measureAverage(() => parseNewAnnotatedText(input), 100);
    // before optimizations, 100 iterations, avg = 1531, deviation 2729 [ms]
    console.log(`Document parsing::Average value: ${avg}, deviation ${deviation}`);
});