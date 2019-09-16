import {parseNewAnnotatedText} from "../TextUnitList";
import {PerfTimer} from "../../../utils/perf";

const input = {
    "content": [{
        "type": "word",
        "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["1", "22", "LS", "@card@", "0", "ROOT", "0", "0", "0", "0", "0", "0", "22", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["0", "§", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["1", "The", "DT", "the", "14", "NMOD", "2018", "@card@", "+13", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["2", "current", "JJ", "current", "14", "NMOD", "2018", "@card@", "+12", "0", "0", "0", "current", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["3", "Baby", "NP", "Baby", "2", "SUFFIX", "current", "current", "-1", "0", "0", "0", "baby", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["4", "of", "IN", "of", "2", "NMOD", "current", "current", "-2", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["5", "the", "DT", "the", "7", "SBJ", "is", "be", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["6", "House", "NP", "House", "5", "SUFFIX", "the", "the", "-1", "0", "0", "0", "house", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["7", "is", "VBZ", "be", "4", "SUB", "of", "of", "-3", "0", "0", "0", "is", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["8", "Ebba", "NP", "Ebba", "2", "SUFFIX", "current", "current", "-6", "0", "0", "0", "ebba", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["9", "Hermansson", "NP", "Hermansson", "2", "SUFFIX", "current", "current", "-7", "https://en.wikipedia.org/wiki/Ebba_Hermansson", "2", "0", "hermansson", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["10", "(", "(", "(", "14", "HMOD", "2018", "@card@", "+4", "0", "0", "0", "(", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "queryMatch",
        "queryMatch": {"from": 0, "to": 1},
        "content": [{
            "type": "word",
            "indexes": ["11", "entered", "VVN", "enter", "10", "SUFFIX", "(", "(", "-1", "0", "0", "0", "entered", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
        }]
    }, {
        "type": "word",
        "indexes": ["12", "in", "IN", "in", "10", "TMP", "(", "(", "-2", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["13", "September", "NP", "September", "10", "SUFFIX", "(", "(", "-3", "0", "0", "0", "september", "t:6e1de13bb9", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "entity",
        "attributes": ["0", "0", "2018", "9", "0", "date", "2"],
        "entityClass": "date",
        "words": [{
            "type": "word",
            "indexes": ["14", "2018", "CD", "@card@", "0", "ROOT", "0", "0", "0", "0", "0", "4.0", "2018", "t:6e1de13bb9", "date", "0", "0", "2018", "9", "0", "0", "0", "0", "0", "0", "date", "2"]
        }, {
            "type": "word",
            "indexes": ["15", "at", "IN", "at", "14", "LOC", "2018", "@card@", "-1", "0", "0", "0", "at", "0", "0", "0", "0", "2018", "9", "0", "0", "0", "0", "0", "0", "date", "-1"]
        }]
    }, {
        "type": "word",
        "indexes": ["16", "the", "DT", "the", "17", "NMOD", "age", "age", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "2018", "9", "0", "0", "0", "0", "0", "0", "date", "-1"]
    }, {
        "type": "word",
        "indexes": ["17", "age", "NN", "age", "15", "PMOD", "at", "at", "-2", "0", "0", "0", "age", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["18", "of", "IN", "of", "17", "NMOD", "age", "age", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["19", "22", "CD", "@card@", "18", "PMOD", "of", "of", "-1", "0", "0", "0", "22", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["20", ")", ")", ")", "14", "P", "2018", "@card@", "-6", "0", "0", "0", ")", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["21", ".", "SENT", ".", "14", "SUFFIX", "2018", "@card@", "-7", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["0", "¶", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["1", "The", "DT", "the", "3", "NMOD", "person", "person", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["2", "youngest", "JJS", "young", "3", "NMOD", "person", "person", "+1", "0", "0", "0", "youngest", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["3", "person", "NN", "person", "13", "SBJ", "is", "be", "+10", "0", "0", "0", "person", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["4", "ever", "RB", "ever", "5", "ADV", "to", "to", "+1", "0", "0", "0", "ever", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["5", "to", "TO", "to", "3", "NMOD", "person", "person", "-2", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["6", "be", "VB", "be", "5", "IM", "to", "to", "-1", "0", "0", "0", "be", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["7", "elected", "VVN", "elect", "6", "OBJ", "be", "be", "-1", "0", "0", "0", "elected", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "queryMatch",
        "queryMatch": {"from": 0, "to": 1},
        "content": [{
            "type": "entity",
            "attributes": ["http://www.freebase.com/m/0c5f1xh", "0", "M__P", "0", "0", "0", "0", "0", "0", "0", "kb", "1"],
            "entityClass": "person",
            "words": [{
                "type": "word",
                "indexes": ["8", "MP", "NN", "MP", "6", "PRD", "be", "be", "-2", "0", "0", "4.0", "mp", "p:25e3c3eee0", "person", "http://www.freebase.com/m/0c5f1xh", "0", "M__P", "0", "0", "0", "0", "0", "0", "0", "kb", "1"]
            }]
        }]
    }, {
        "type": "word",
        "indexes": ["9", "to", "TO", "to", "8", "NMOD", "MP", "MP", "-1", "0", "0", "0", "to", "0", "0", "http://www.freebase.com/m/0c5f1xh", "0", "M__P", "0", "0", "0", "0", "0", "0", "0", "kb", "-1"]
    }, {
        "type": "word",
        "indexes": ["10", "a", "DT", "a", "12", "NMOD", "parliament", "parliament", "+2", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "entity",
        "attributes": ["https://en.wikipedia.org/wiki/Sweden", "http://athena3.fit.vutbr.cz/kb/images/freebase/05pjg9h.jpg", "Swedish", "Sweden", "kb", "1"],
        "entityClass": "nationality",
        "words": [{
            "type": "word",
            "indexes": ["11", "Swedish", "JJ", "Swedish", "12", "NMOD", "parliament", "parliament", "+1", "0", "0", "4.0", "swedish", "n:0d0vqn", "nationality", "https://en.wikipedia.org/wiki/Sweden", "http://athena3.fit.vutbr.cz/kb/images/freebase/05pjg9h.jpg", "Swedish", "Sweden", "0", "0", "0", "0", "0", "0", "kb", "1"]
        }]
    }, {
        "type": "word",
        "indexes": ["12", "parliament", "NN", "parliament", "9", "PMOD", "to", "to", "-3", "0", "0", "0", "parliament", "0", "0", "https://en.wikipedia.org/wiki/Sweden", "http://athena3.fit.vutbr.cz/kb/images/freebase/05pjg9h.jpg", "Swedish", "Sweden", "0", "0", "0", "0", "0", "0", "kb", "-1"]
    }, {
        "type": "word",
        "indexes": ["13", "is", "VBZ", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "is", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["14", "Anton", "NP", "Anton", "13", "SUFFIX", "is", "be", "-1", "0", "0", "0", "anton", "p:e10b8b8672", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "queryMatch",
        "queryMatch": {"from": 0, "to": 1},
        "content": [{
            "type": "entity",
            "attributes": ["https://en.wikipedia.org/wiki/Anton_Abele", "http://athena3.fit.vutbr.cz/kb/images/freebase/0h02y6w.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Anton_Abele_2013.jpg", "Anton_Abele", "M", "Stockholm", "1992_01_10", "0", "0", "0", "Swedish", "kb", "2"],
            "entityClass": "person",
            "words": [{
                "type": "word",
                "indexes": ["15", "Abele", "NP", "Abele", "13", "SUFFIX", "is", "be", "-2", "https://en.wikipedia.org/wiki/Anton_Abele", "2", "4.1", "abele", "p:e10b8b8672", "person", "https://en.wikipedia.org/wiki/Anton_Abele", "http://athena3.fit.vutbr.cz/kb/images/freebase/0h02y6w.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Anton_Abele_2013.jpg", "Anton_Abele", "M", "Stockholm", "1992_01_10", "0", "0", "0", "Swedish", "kb", "2"]
            }]
        }]
    }, {
        "type": "queryMatch",
        "queryMatch": {"from": 0, "to": 1},
        "content": [{
            "type": "word",
            "indexes": ["16", "who", "WP", "who", "17", "SBJ", "was", "be", "+1", "0", "0", "2", "who", "p:e10b8b8672", "person", "https://en.wikipedia.org/wiki/Anton_Abele", "http://athena3.fit.vutbr.cz/kb/images/freebase/0h02y6w.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Anton_Abele_2013.jpg", "Anton_Abele", "M", "Stockholm", "1992_01_10", "0", "0", "0", "Swedish", "kb", "-1"]
        }]
    }, {
        "type": "word",
        "indexes": ["17", "was", "VBD", "be", "13", "OBJ", "is", "be", "-4", "0", "0", "0", "was", "0", "0", "https://en.wikipedia.org/wiki/Anton_Abele", "http://athena3.fit.vutbr.cz/kb/images/freebase/0h02y6w.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/d/d9/Anton_Abele_2013.jpg", "Anton_Abele", "M", "Stockholm", "1992_01_10", "0", "0", "0", "Swedish", "kb", "-1"]
    }, {
        "type": "word",
        "indexes": ["18", "only", "RB", "only", "13", "NMOD", "is", "be", "-5", "0", "0", "0", "only", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["19", "aged", "VVN", "age", "18", "SUFFIX", "only", "only", "-1", "0", "0", "0", "aged", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["20", "18", "CD", "@card@", "18", "DEP", "only", "only", "-2", "0", "0", "0", "18", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["21", "when", "WRB", "when", "25", "HMOD", "2010", "@card@", "+4", "0", "0", "0", "when", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["22", "elected", "VVN", "elect", "21", "SUFFIX", "when", "when", "-1", "0", "0", "0", "elected", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }, {
        "type": "word",
        "indexes": ["23", "in", "IN", "in", "21", "TMP", "when", "when", "-2", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
    }]
};

it("parse new annotated text performance test", () => {
    const timer = new PerfTimer("parse new annotated text");
    const parsed = parseNewAnnotatedText(input);
    timer.finish();
});