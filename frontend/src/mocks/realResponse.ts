import {Snippet} from "../entities/Snippet";
import {parseNewAnnotatedText} from "../components/annotations/new/NewAnnotatedText";
import {isSearchResult} from "../entities/SearchResult";


export function getProcessedSnippets(): Array<Snippet> {
    if (!isSearchResult(realResponse)) {
        throw "this should be valid for sure"
    }
    // @ts-ignore
    return realResponse.snippets.map(
        (snippet, index) => {
            // @ts-ignore
            snippet.id = index
            // @ts-ignore
            snippet.payload.content = parseNewAnnotatedText(snippet.payload.content)!
            return snippet;
        }
    )
}


export const realResponse = {
    "snippets": [{
        "host": "127.0.0.1:8001",
        "collection": "name",
        "documentId": 56,
        "location": 349,
        "size": 50,
        "url": "http://alitalia1.biz/get-free-grocery-coupons-in-the-mail/",
        "documentTitle": "Get free grocery coupons in the mail tria 4x coupon",
        "payload": {
            "type": "new", "content": {
                "content": [{
                    "type": "queryMatch",
                    "queryMatch": {"from": 0, "to": 1},
                    "content": [{
                        "type": "word",
                        "indexes": ["144", "entered", "VVN", "enter", "143", "SUFFIX", "are", "be", "-1", "0", "0", "0", "entered", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                    }]
                }, {
                    "type": "word",
                    "indexes": ["145", "at", "IN", "at", "143", "ADV", "are", "be", "-2", "0", "0", "0", "at", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["146", "checkout", "NN", "checkout", "145", "PMOD", "at", "at", "-1", "0", "0", "0", "checkout", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["147", "while", "IN", "while", "143", "PRD", "are", "be", "-4", "0", "0", "0", "while", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["148", "shopping", "VVG", "shop", "141", "SUFFIX", "codes", "code", "-7", "0", "0", "0", "shopping", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["149", "online", "JJ", "online", "141", "NMOD", "codes", "code", "-8", "0", "0", "0", "online", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["150", ".|G__", "SENT", ".", "141", "SUFFIX", "codes", "code", "-9", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["1", "Every", "DT", "every", "3", "NMOD", "bit", "bit", "+2", "0", "0", "0", "every", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["2", "little", "JJ", "little", "3", "NMOD", "bit", "bit", "+1", "0", "0", "0", "little", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["3", "bit", "NN", "bit", "0", "ROOT", "0", "0", "0", "0", "0", "0", "bit", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["4", "of", "IN", "of", "3", "NMOD", "bit", "bit", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["5", "savings", "NNS", "saving", "4", "PMOD", "of", "of", "-1", "0", "0", "0", "savings", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["6", "adds", "VVZ", "add", "3", "SUFFIX", "bit", "bit", "-3", "0", "0", "0", "adds", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["7", "up", "RP", "up", "6", "PRT", "adds", "add", "-1", "0", "0", "0", "up", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["8", "!|G__", "SENT", "!", "3", "SUFFIX", "bit", "bit", "-5", "0", "0", "0", "!", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["1", "Login", "NN", "Login", "5", "NMOD", "account", "account", "+4", "0", "0", "0", "login", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["2", "with", "IN", "with", "1", "NMOD", "Login", "Login", "-1", "0", "0", "0", "with", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["3", "your", "PP$", "your", "1", "SUFFIX", "Login", "Login", "-2", "0", "0", "0", "your", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["4", "existing", "JJ", "existing", "5", "NMOD", "account", "account", "+1", "0", "0", "0", "existing", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["5", "account", "NN", "account", "10", "NMOD", "account", "account", "+5", "0", "0", "0", "account", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["6", ",|G__", ",", ",", "10", "P", "account", "account", "+4", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["7", "create", "VV", "create", "6", "SUFFIX", ",", ",", "-1", "0", "0", "0", "create", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["8", "a", "DT", "a", "10", "NMOD", "account", "account", "+2", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["9", "new", "JJ", "new", "10", "NMOD", "account", "account", "+1", "0", "0", "0", "new", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["10", "account", "NN", "account", "0", "ROOT", "0", "0", "0", "0", "0", "0", "account", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["11", ",|G__", ",", ",", "10", "P", "account", "account", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["12", "login", "NN", "login", "10", "APPO", "account", "account", "-2", "0", "0", "0", "login", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["13", "/", "SYM", "/", "14", "AMOD", "Sign", "Sign", "+1", "0", "0", "0", "/", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["14", "Sign", "JJ", "Sign", "10", "APPO", "account", "account", "-4", "0", "0", "0", "sign", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["15", ".|G__", "SENT", ".", "10", "SUFFIX", "account", "account", "-5", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "entity",
                    "attributes": ["http://www.freebase.com/m/0115vjjc", "0", "Thank_You", "0", "0", "0", "0", "0", "0", "0", "coref", "1"],
                    "entityClass": "person",
                    "words": [{
                        "type": "word",
                        "indexes": ["1", "You", "PP", "you", "0", "ROOT", "0", "0", "0", "0", "0", "0", "you", "p:111d027659", "person", "http://www.freebase.com/m/0115vjjc", "0", "Thank_You", "0", "0", "0", "0", "0", "0", "0", "coref", "1"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://www.freebase.com/m/0115vjjc", "0", "Thank_You", "0", "0", "0", "0", "0", "0", "0", "coref", "0"],
                    "entityClass": "person",
                    "words": [{
                        "type": "word",
                        "indexes": ["2", "can", "MD", "can", "0", "ROOT", "0", "0", "0", "0", "0", "0", "can", "p:111d027659", "person", "http://www.freebase.com/m/0115vjjc", "0", "Thank_You", "0", "0", "0", "0", "0", "0", "0", "coref", "0"]
                    }]
                }, {
                    "type": "word",
                    "indexes": ["3", "filter", "VV", "filter", "2", "SUFFIX", "can", "can", "-1", "0", "0", "0", "filter", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["4", "by", "IN", "by", "2", "MNR", "can", "can", "-2", "0", "0", "0", "by", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["5", "site", "NN", "site", "6", "NMOD", "section", "section", "+1", "0", "0", "0", "site", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["6", "section", "NN", "section", "4", "PMOD", "by", "by", "-2", "0", "0", "0", "section", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["7", "on", "IN", "on", "6", "LOC", "section", "section", "-1", "0", "0", "0", "on", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["8", "the", "DT", "the", "10", "NMOD", "page", "page", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["9", "results", "NNS", "result", "10", "NMOD", "page", "page", "+1", "0", "0", "0", "results", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["10", "page", "NN", "page", "7", "PMOD", "on", "on", "-3", "0", "0", "0", "page", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["11", ".|G__", "SENT", ".", "10", "SUFFIX", "page", "page", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["1", "Search", "NN", "search", "9", "SBJ", "would", "would", "+8", "0", "0", "0", "search", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["2", ",|G__", ",", ",", "9", "P", "would", "would", "+7", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["3", "type", "NN", "type", "5", "NMOD", "term", "term", "+2", "0", "0", "0", "type", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["4", "the", "DT", "the", "5", "NMOD", "term", "term", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["5", "term", "NN", "term", "9", "SBJ", "would", "would", "+4", "0", "0", "0", "term", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["6", "or", "CC", "or", "5", "COORD", "term", "term", "-1", "0", "0", "0", "or", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["7", "phrase", "NN", "phrase", "6", "CONJ", "or", "or", "-1", "0", "0", "0", "phrase", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["8", "you", "PP", "you", "5", "SUFFIX", "term", "term", "-3", "0", "0", "0", "you", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["9", "would", "MD", "would", "0", "ROOT", "0", "0", "0", "0", "0", "0", "would", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["10", "like", "VV", "like", "9", "SUFFIX", "would", "would", "-1", "0", "0", "0", "like", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }
        },
        "canExtend": true
    }, {
        "host": "127.0.0.1:8001",
        "collection": "name",
        "documentId": 91,
        "location": 4032,
        "size": 50,
        "url": "http://archive.jns.org/latest-articles/2012/7/10/jns-briefs-7-10-12.html",
        "documentTitle": "JNS Briefs 7-10-12 — JNS.org",
        "payload": {
            "type": "new", "content": {
                "content": [{
                    "type": "queryMatch",
                    "queryMatch": {"from": 0, "to": 1},
                    "content": [{
                        "type": "word",
                        "indexes": ["8", "visited", "VVD", "visit", "7", "SUFFIX", "recently", "recently", "-1", "0", "0", "0", "visited", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                    }]
                }, {
                    "type": "word",
                    "indexes": ["9", "Israel", "NP", "Israel", "8", "SUFFIX", "visited", "visit", "-1", "0", "0", "0", "israel", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["10", ",|G__", ",", ",", "7", "P", "recently", "recently", "-3", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["11", "meeting", "NN", "meeting", "0", "ROOT", "0", "0", "0", "0", "0", "0", "meeting", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["12", "with", "IN", "with", "11", "NMOD", "meeting", "meeting", "-1", "0", "0", "0", "with", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["13", "Israeli", "NP", "Israeli", "11", "SUFFIX", "meeting", "meeting", "-2", "0", "0", "0", "israeli", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["14", "Water", "NP", "Water", "11", "SUFFIX", "meeting", "meeting", "-3", "0", "0", "0", "water", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["15", "and", "CC", "and", "11", "COORD", "meeting", "meeting", "-4", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["16", "Energy", "NP", "Energy", "11", "SUFFIX", "meeting", "meeting", "-5", "0", "0", "0", "energy", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["17", "Minister", "NP", "Minister", "11", "SUFFIX", "meeting", "meeting", "-6", "0", "0", "0", "minister", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["18", "Uzi", "NP", "Uzi", "11", "SUFFIX", "meeting", "meeting", "-7", "0", "0", "0", "uzi", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["19", "Landau", "NP", "Landau", "11", "SUFFIX", "meeting", "meeting", "-8", "0", "0", "0", "landau", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["20", "and", "CC", "and", "11", "COORD", "meeting", "meeting", "-9", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["21", "agreeing", "VVG", "agree", "11", "SUFFIX", "meeting", "meeting", "-10", "0", "0", "0", "agreeing", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["22", "to", "TO", "to", "11", "NMOD", "meeting", "meeting", "-11", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["23", "boost", "VV", "boost", "11", "SUFFIX", "meeting", "meeting", "-12", "0", "0", "0", "boost", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["24", "ties", "NNS", "ty", "0", "ROOT", "0", "0", "0", "0", "0", "0", "ties", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["25", "and", "CC", "and", "24", "COORD", "ties", "ty", "-1", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["26", "launch", "VV", "launch", "24", "SUFFIX", "ties", "ty", "-2", "0", "0", "0", "launch", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["27", "several", "JJ", "several", "31", "NMOD", "projects", "project", "+4", "0", "0", "0", "several", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["28", "joint", "JJ", "joint", "31", "NMOD", "projects", "project", "+3", "0", "0", "0", "joint", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["29", "renewable", "JJ", "renewable", "31", "NMOD", "projects", "project", "+2", "0", "0", "0", "renewable", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["30", "energy", "NN", "energy", "31", "NMOD", "projects", "project", "+1", "0", "0", "0", "energy", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["31", "projects", "NNS", "project", "0", "ROOT", "0", "0", "0", "0", "0", "0", "projects", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["32", ",|G__", ",", ",", "31", "P", "projects", "project", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["33", "Yediot", "NP", "Yediot", "31", "SUFFIX", "projects", "project", "-2", "0", "0", "0", "yediot", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["34", "Achronot", "NP", "Achronot", "31", "SUFFIX", "projects", "project", "-3", "0", "0", "0", "achronot", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["35", "reported", "VVD", "report", "31", "SUFFIX", "projects", "project", "-4", "0", "0", "0", "reported", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["36", ".|G__", "SENT", ".", "31", "SUFFIX", "projects", "project", "-5", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["1", "Additionally", "RB", "additionally", "0", "ROOT", "0", "0", "0", "0", "0", "0", "additionally", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["2", ",|G__", ",", ",", "1", "NMOD", "Additionally", "additionally", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["3", "Russian", "NP", "Russian", "2", "SUFFIX", ",", ",", "-1", "0", "0", "0", "russian", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["4", "President", "NP", "President", "2", "SUFFIX", ",", ",", "-2", "0", "0", "0", "president", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["5", "Vladimir", "NP", "Vladimir", "2", "SUFFIX", ",", ",", "-3", "0", "0", "0", "vladimir", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["6", "Putin", "NP", "Putin", "2", "SUFFIX", ",", ",", "-4", "0", "0", "0", "putin", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["7", "|G__", "CC", "0", "1", "NMOD", "Additionally", "additionally", "-6", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["8", "rsquo|G__", "NP", "rsquo", "7", "SUFFIX", "0", "0", "-1", "0", "0", "0", "rsquo", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["9", ";|G__", ":", ";", "7", "P", "0", "0", "-2", "0", "0", "0", ";", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["10", "s|G__", "JJ", "s", "12", "NMOD", "visit", "visit", "+2", "0", "0", "0", "s", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["11", "recent", "JJ", "recent", "12", "NMOD", "visit", "visit", "+1", "0", "0", "0", "recent", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["12", "visit", "NN", "visit", "1", "NMOD", "Additionally", "additionally", "-11", "0", "0", "0", "visit", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["13", "to", "TO", "to", "12", "NMOD", "visit", "visit", "-1", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["14", "Israel", "NP", "Israel", "12", "SUFFIX", "visit", "visit", "-2", "0", "0", "0", "israel", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["15", "came", "VVD", "come", "12", "SUFFIX", "visit", "visit", "-3", "0", "0", "0", "came", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["16", "in", "IN", "in", "12", "LOC", "visit", "visit", "-4", "0", "0", "0", "in", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["17", "the", "DT", "the", "18", "NMOD", "wake", "wake", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["18", "wake", "NN", "wake", "16", "PMOD", "in", "in", "-2", "0", "0", "0", "wake", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["19", "of", "IN", "of", "18", "NMOD", "wake", "wake", "-1", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["20", "news", "NN", "news", "19", "PMOD", "of", "of", "-1", "0", "0", "0", "news", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["21", "that", "IN/that", "that", "18", "SUFFIX", "wake", "wake", "-3", "0", "0", "0", "that", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["22", "Russian", "JJ", "Russian", "24", "NMOD", "company", "company", "+2", "0", "0", "0", "russian", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }
        },
        "canExtend": true
    }, {
        "host": "127.0.0.1:8001",
        "collection": "name",
        "documentId": 110,
        "location": 319,
        "size": 50,
        "url": "http://articles.baltimoresun.com/keyword/fighter-pilot",
        "documentTitle": "Articles about Fighter Pilot - tribunedigital-baltimoresun",
        "payload": {
            "type": "new", "content": {
                "content": [{
                    "type": "queryMatch",
                    "queryMatch": {"from": 0, "to": 1},
                    "content": [{
                        "type": "word",
                        "indexes": ["23", "visited", "VVD", "visit", "21", "SUFFIX", "why", "why", "-2", "0", "0", "0", "visited", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                    }]
                }, {
                    "type": "word",
                    "indexes": ["24", "the", "DT", "the", "25", "NMOD", "schools", "school", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["25", "schools", "NNS", "school", "32", "NMOD", "post", "post", "+7", "0", "0", "0", "schools", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["26", "so", "RB", "so", "28", "PMOD", "after", "after", "+2", "0", "0", "0", "so", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["27", "soon", "RB", "soon", "26", "AMOD", "so", "so", "-1", "0", "0", "0", "soon", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["28", "after", "IN", "after", "25", "TMP", "schools", "school", "-3", "0", "0", "0", "after", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["29", "returning", "VVG", "return", "28", "SUFFIX", "after", "after", "-1", "0", "0", "0", "returning", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["30", "to", "TO", "to", "25", "NMOD", "schools", "school", "-5", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["31", "his", "PP$", "his", "25", "SUFFIX", "schools", "school", "-6", "0", "0", "0", "his", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["32", "post", "NN", "post", "0", "ROOT", "0", "0", "0", "0", "0", "0", "post", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["33", "at", "IN", "at", "32", "LOC", "post", "post", "-1", "0", "0", "0", "at", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["34", "Langley", "NP", "Langley", "32", "SUFFIX", "post", "post", "-2", "0", "0", "0", "langley", "l:a9a70f9df3", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["35", "Air", "NP", "Air", "32", "SUFFIX", "post", "post", "-3", "0", "0", "0", "air", "l:a9a70f9df3", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["36", "Force", "NP", "Force", "32", "SUFFIX", "post", "post", "-4", "0", "0", "0", "force", "l:a9a70f9df3", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Langley_Field", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/6/68/Langley_AFB.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0h0kjzp.jpg", "Langley_Field", "Virginia", "kb", "4"],
                    "entityClass": "location",
                    "words": [{
                        "type": "word",
                        "indexes": ["37", "Base", "NP", "Base", "32", "SUFFIX", "post", "post", "-5", "0", "0", "0", "base", "l:a9a70f9df3", "location", "http://en.wikipedia.org/wiki/Langley_Field", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/6/68/Langley_AFB.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0h0kjzp.jpg", "Langley_Field", "Virginia", "0", "0", "0", "0", "0", "0", "kb", "4"]
                    }, {
                        "type": "word",
                        "indexes": ["38", "in", "IN", "in", "32", "LOC", "post", "post", "-6", "0", "0", "0", "in", "l:a9a70f9df3", "location", "http://en.wikipedia.org/wiki/Langley_Field", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/6/68/Langley_AFB.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0h0kjzp.jpg", "Langley_Field", "Virginia", "0", "0", "0", "0", "0", "0", "kb", "0"]
                    }, {
                        "type": "word",
                        "indexes": ["39", "Virginia", "NP", "Virginia", "32", "SUFFIX", "post", "post", "-7", "0", "0", "0", "virginia", "l:a9a70f9df3", "location", "http://en.wikipedia.org/wiki/Langley_Field", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/6/68/Langley_AFB.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0h0kjzp.jpg", "Langley_Field", "Virginia", "0", "0", "0", "0", "0", "0", "kb", "0"]
                    }, {
                        "type": "word",
                        "indexes": ["40", ".|G__", "SENT", ".", "32", "SUFFIX", "post", "post", "-8", "0", "0", "0", ".", "l:a9a70f9df3", "location", "http://en.wikipedia.org/wiki/Langley_Field", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/6/68/Langley_AFB.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0h0kjzp.jpg", "Langley_Field", "Virginia", "0", "0", "0", "0", "0", "0", "kb", "0"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Langley_Field", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/6/68/Langley_AFB.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0h0kjzp.jpg", "Langley_Field", "Virginia", "kb", "0"],
                    "entityClass": "location",
                    "words": [{
                        "type": "word",
                        "indexes": ["1", "By", "IN", "by", "0", "ROOT", "0", "0", "0", "0", "0", "0", "by", "l:a9a70f9df3", "location", "http://en.wikipedia.org/wiki/Langley_Field", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/6/68/Langley_AFB.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0h0kjzp.jpg", "Langley_Field", "Virginia", "0", "0", "0", "0", "0", "0", "kb", "0"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Langley_Field", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/6/68/Langley_AFB.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0h0kjzp.jpg", "Langley_Field", "Virginia", "kb", "0"],
                    "entityClass": "location",
                    "words": [{
                        "type": "word",
                        "indexes": ["1", "By", "IN", "by", "0", "ROOT", "0", "0", "0", "0", "0", "0", "by", "l:a9a70f9df3", "location", "http://en.wikipedia.org/wiki/Langley_Field", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/6/68/Langley_AFB.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0h0kjzp.jpg", "Langley_Field", "Virginia", "0", "0", "0", "0", "0", "0", "kb", "0"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Langley_Field", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/6/68/Langley_AFB.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0h0kjzp.jpg", "Langley_Field", "Virginia", "kb", "0"],
                    "entityClass": "location",
                    "words": [{
                        "type": "word",
                        "indexes": ["1", "By", "IN", "by", "0", "ROOT", "0", "0", "0", "0", "0", "0", "by", "l:a9a70f9df3", "location", "http://en.wikipedia.org/wiki/Langley_Field", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/6/68/Langley_AFB.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0h0kjzp.jpg", "Langley_Field", "Virginia", "0", "0", "0", "0", "0", "0", "kb", "0"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Langley_Field", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/6/68/Langley_AFB.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0h0kjzp.jpg", "Langley_Field", "Virginia", "kb", "0"],
                    "entityClass": "location",
                    "words": [{
                        "type": "word",
                        "indexes": ["1", "By", "IN", "by", "0", "ROOT", "0", "0", "0", "0", "0", "0", "by", "l:a9a70f9df3", "location", "http://en.wikipedia.org/wiki/Langley_Field", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/6/68/Langley_AFB.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0h0kjzp.jpg", "Langley_Field", "Virginia", "0", "0", "0", "0", "0", "0", "kb", "0"]
                    }]
                }, {
                    "type": "word",
                    "indexes": ["2", "Robert", "NP", "Robert", "1", "SUFFIX", "By", "by", "-1", "0", "0", "0", "robert", "p:4f4ddb268f", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Robert_A._Baron", "0", "Robert_A__Baron", "M", "0", "0", "0", "0", "Psychologist", "0", "kb", "2"],
                    "entityClass": "person",
                    "words": [{
                        "type": "word",
                        "indexes": ["3", "A.", "NP", "A.", "1", "SUFFIX", "By", "by", "-2", "0", "0", "0", "a.", "p:4f4ddb268f", "person", "http://en.wikipedia.org/wiki/Robert_A._Baron", "0", "Robert_A__Baron", "M", "0", "0", "0", "0", "Psychologist", "0", "kb", "2"]
                    }, {
                        "type": "word",
                        "indexes": ["4", "Erlandson", "NP", "Erlandson", "1", "SUFFIX", "By", "by", "-3", "0", "0", "0", "erlandson", "p:4f4ddb268f", "person", "http://en.wikipedia.org/wiki/Robert_A._Baron", "0", "Robert_A__Baron", "M", "0", "0", "0", "0", "Psychologist", "0", "kb", "0"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Robert_A._Baron", "0", "Robert_A__Baron", "M", "0", "0", "0", "0", "Psychologist", "0", "kb", "0"],
                    "entityClass": "person",
                    "words": [{
                        "type": "word",
                        "indexes": ["5", "and", "CC", "and", "1", "COORD", "By", "by", "-4", "0", "0", "0", "and", "p:4f4ddb268f", "person", "http://en.wikipedia.org/wiki/Robert_A._Baron", "0", "Robert_A__Baron", "M", "0", "0", "0", "0", "Psychologist", "0", "kb", "0"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Robert_A._Baron", "0", "Robert_A__Baron", "M", "0", "0", "0", "0", "Psychologist", "0", "kb", "0"],
                    "entityClass": "person",
                    "words": [{
                        "type": "word",
                        "indexes": ["5", "and", "CC", "and", "1", "COORD", "By", "by", "-4", "0", "0", "0", "and", "p:4f4ddb268f", "person", "http://en.wikipedia.org/wiki/Robert_A._Baron", "0", "Robert_A__Baron", "M", "0", "0", "0", "0", "Psychologist", "0", "kb", "0"]
                    }]
                }, {
                    "type": "word",
                    "indexes": ["6", "Robert", "NP", "Robert", "1", "SUFFIX", "By", "by", "-5", "0", "0", "0", "robert", "p:4f4ddb268f", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Robert_A._Baron", "0", "Robert_A__Baron", "M", "0", "0", "0", "0", "Psychologist", "0", "kb", "2"],
                    "entityClass": "person",
                    "words": [{
                        "type": "word",
                        "indexes": ["7", "A.", "NP", "A.", "1", "SUFFIX", "By", "by", "-6", "0", "0", "0", "a.", "p:4f4ddb268f", "person", "http://en.wikipedia.org/wiki/Robert_A._Baron", "0", "Robert_A__Baron", "M", "0", "0", "0", "0", "Psychologist", "0", "kb", "2"]
                    }, {
                        "type": "word",
                        "indexes": ["8", "Erlandson", "NP", "Erlandson", "7", "SUFFIX", "A.", "A.", "-1", "0", "0", "0", "erlandson", "p:4f4ddb268f", "person", "http://en.wikipedia.org/wiki/Robert_A._Baron", "0", "Robert_A__Baron", "M", "0", "0", "0", "0", "Psychologist", "0", "kb", "0"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Robert_A._Baron", "0", "Robert_A__Baron", "M", "0", "0", "0", "0", "Psychologist", "0", "kb", "0"],
                    "entityClass": "person",
                    "words": [{
                        "type": "word",
                        "indexes": ["9", ",|G__", ",", ",", "1", "P", "By", "by", "-8", "0", "0", "0", ",", "p:4f4ddb268f", "person", "http://en.wikipedia.org/wiki/Robert_A._Baron", "0", "Robert_A__Baron", "M", "0", "0", "0", "0", "Psychologist", "0", "kb", "0"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Robert_A._Baron", "0", "Robert_A__Baron", "M", "0", "0", "0", "0", "Psychologist", "0", "kb", "0"],
                    "entityClass": "person",
                    "words": [{
                        "type": "word",
                        "indexes": ["9", ",|G__", ",", ",", "1", "P", "By", "by", "-8", "0", "0", "0", ",", "p:4f4ddb268f", "person", "http://en.wikipedia.org/wiki/Robert_A._Baron", "0", "Robert_A__Baron", "M", "0", "0", "0", "0", "Psychologist", "0", "kb", "0"]
                    }]
                }, {
                    "type": "word",
                    "indexes": ["10", "SUN|G__", "NP", "Sun", "1", "SUFFIX", "By", "by", "-9", "0", "0", "0", "sun", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["11", "STAFF", "NP", "Staff", "1", "SUFFIX", "By", "by", "-10", "0", "0", "0", "staff", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["12", "¦", "SYM", "¦", "16", "NMOD", "1996", "@card@", "+4", "0", "0", "0", "¦", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["13", "January", "NP", "January", "12", "SUFFIX", "¦", "¦", "-1", "0", "0", "0", "january", "t:8525e585dd", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["14", "21", "CD", "@card@", "13", "NMOD", "January", "January", "-1", "0", "0", "0", "21", "t:8525e585dd", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["15", ",|GGG", ",", ",", "16", "P", "1996", "@card@", "+1", "0", "0", "0", ",", "t:8525e585dd", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "entity",
                    "attributes": ["0", "0", "1996", "1", "21", "date", "4"],
                    "entityClass": "date",
                    "words": [{
                        "type": "word",
                        "indexes": ["16", "1996", "CD", "@card@", "1", "PMOD", "By", "by", "-15", "0", "0", "0", "1996", "t:8525e585dd", "date", "0", "0", "1996", "1", "21", "0", "0", "0", "0", "0", "date", "4"]
                    }, {
                        "type": "word",
                        "indexes": ["1", "Harry", "NP", "Harry", "0", "ROOT", "0", "0", "0", "0", "0", "0", "harry", "t:8525e585dd", "date", "0", "0", "1996", "1", "21", "0", "0", "0", "0", "0", "date", "0"]
                    }, {
                        "type": "word",
                        "indexes": ["2", "S.", "NP", "S.", "1", "SUFFIX", "Harry", "Harry", "-1", "0", "0", "0", "s.", "t:8525e585dd", "date", "0", "0", "1996", "1", "21", "0", "0", "0", "0", "0", "date", "0"]
                    }, {
                        "type": "word",
                        "indexes": ["3", "Freedman", "NP", "Freedman", "2", "SUFFIX", "S.", "S.", "-1", "0", "0", "0", "freedman", "t:8525e585dd", "date", "0", "0", "1996", "1", "21", "0", "0", "0", "0", "0", "date", "0"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["0", "0", "1996", "1", "21", "date", "0"],
                    "entityClass": "date",
                    "words": [{
                        "type": "word",
                        "indexes": ["4", ",|G__", ",", ",", "5", "P", "53", "@card@", "+1", "0", "0", "0", ",", "t:8525e585dd", "date", "0", "0", "1996", "1", "21", "0", "0", "0", "0", "0", "date", "0"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["0", "0", "1996", "1", "21", "date", "0"],
                    "entityClass": "date",
                    "words": [{
                        "type": "word",
                        "indexes": ["4", ",|G__", ",", ",", "5", "P", "53", "@card@", "+1", "0", "0", "0", ",", "t:8525e585dd", "date", "0", "0", "1996", "1", "21", "0", "0", "0", "0", "0", "date", "0"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["0", "0", "1996", "1", "21", "date", "0"],
                    "entityClass": "date",
                    "words": [{
                        "type": "word",
                        "indexes": ["4", ",|G__", ",", ",", "5", "P", "53", "@card@", "+1", "0", "0", "0", ",", "t:8525e585dd", "date", "0", "0", "1996", "1", "21", "0", "0", "0", "0", "0", "date", "0"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["0", "0", "1996", "1", "21", "date", "0"],
                    "entityClass": "date",
                    "words": [{
                        "type": "word",
                        "indexes": ["4", ",|G__", ",", ",", "5", "P", "53", "@card@", "+1", "0", "0", "0", ",", "t:8525e585dd", "date", "0", "0", "1996", "1", "21", "0", "0", "0", "0", "0", "date", "0"]
                    }]
                }, {
                    "type": "word",
                    "indexes": ["5", "53", "CD", "@card@", "1", "NMOD", "Harry", "Harry", "-4", "0", "0", "0", "53", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["6", ",|G__", ",", ",", "5", "P", "53", "@card@", "-1", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["7", "formerly", "RB", "formerly", "8", "DEP", "of", "of", "+1", "0", "0", "0", "formerly", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["8", "of", "IN", "of", "5", "NMOD", "53", "@card@", "-3", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Pimlico", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/7/7b/Belgrave.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0290y7p.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02gd39l.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/05ltmhw.jpg", "Pimlico", "United_Kingdom", "kb", "1"],
                    "entityClass": "location",
                    "words": [{
                        "type": "word",
                        "indexes": ["9", "Pimlico", "NP", "Pimlico", "8", "SUFFIX", "of", "of", "-1", "0", "0", "0", "pimlico", "l:39c1afd978", "location", "http://en.wikipedia.org/wiki/Pimlico", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/7/7b/Belgrave.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0290y7p.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02gd39l.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/05ltmhw.jpg", "Pimlico", "United_Kingdom", "0", "0", "0", "0", "0", "0", "kb", "1"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Pimlico", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/7/7b/Belgrave.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0290y7p.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02gd39l.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/05ltmhw.jpg", "Pimlico", "United_Kingdom", "kb", "0"],
                    "entityClass": "location",
                    "words": [{
                        "type": "word",
                        "indexes": ["10", ",|G__", ",", ",", "1", "NMOD", "Harry", "Harry", "-9", "0", "0", "0", ",", "l:39c1afd978", "location", "http://en.wikipedia.org/wiki/Pimlico", "http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/7/7b/Belgrave.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/0290y7p.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02gd39l.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/05ltmhw.jpg", "Pimlico", "United_Kingdom", "0", "0", "0", "0", "0", "0", "kb", "0"]
                    }]
                }, {
                    "type": "word",
                    "indexes": ["11", "a", "DT", "a", "16", "NMOD", "pilot", "pilot", "+5", "0", "0", "0", "a", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["12", "decorated", "VVN", "decorate", "11", "SUFFIX", "a", "a", "-1", "0", "0", "0", "decorated", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["13", "Vietnam", "NP", "Vietnam", "11", "SUFFIX", "a", "a", "-2", "0", "0", "0", "vietnam", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["14", "War", "NP", "War", "11", "SUFFIX", "a", "a", "-3", "0", "0", "0", "war", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["15", "fighter", "NN", "fighter", "16", "NMOD", "pilot", "pilot", "+1", "0", "0", "0", "fighter", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["16", "pilot", "NN", "pilot", "1", "NMOD", "Harry", "Harry", "-15", "0", "0", "0", "pilot", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Samuel_G._Freedman", "0", "Samuel_G__Freedman", "M", "New_York_City", "1955_10_03", "0", "0", "Professor|Journalist|Author", "American", "coref", "1"],
                    "entityClass": "person",
                    "words": [{
                        "type": "word",
                        "indexes": ["17", "who", "WP", "who", "1", "NMOD", "Harry", "Harry", "-16", "0", "0", "0", "who", "p:c43dc275e9", "person", "http://en.wikipedia.org/wiki/Samuel_G._Freedman", "0", "Samuel_G__Freedman", "M", "New_York_City", "1955_10_03", "0", "0", "Professor|Journalist|Author", "American", "coref", "1"]
                    }]
                }]
            }
        },
        "canExtend": true
    }, {
        "host": "127.0.0.1:8001",
        "collection": "name",
        "documentId": 112,
        "location": 890,
        "size": 50,
        "url": "http://aryarrega.tk/steve-harvey-dating-questions.html",
        "documentTitle": "None",
        "payload": {
            "type": "new", "content": {
                "content": [{
                    "type": "queryMatch",
                    "queryMatch": {"from": 0, "to": 1},
                    "content": [{
                        "type": "word",
                        "indexes": ["9", "visited", "VVN", "visit", "7", "SUFFIX", "guys", "guy", "-2", "0", "0", "0", "visited", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Ukraine", "http://athena3.fit.vutbr.cz/kb/images/freebase/03f5m2w.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/4/49/Flag_of_Ukraine.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f5df1.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f6hm6.jpg", "Ukraine", "Ukraine", "kb", "1"],
                    "entityClass": "location",
                    "words": [{
                        "type": "word",
                        "indexes": ["10", "Ukraine", "NP", "Ukraine", "7", "SUFFIX", "guys", "guy", "-3", "0", "0", "0", "ukraine", "l:f000776798", "location", "http://en.wikipedia.org/wiki/Ukraine", "http://athena3.fit.vutbr.cz/kb/images/freebase/03f5m2w.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/4/49/Flag_of_Ukraine.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f5df1.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f6hm6.jpg", "Ukraine", "Ukraine", "0", "0", "0", "0", "0", "0", "kb", "1"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Ukraine", "http://athena3.fit.vutbr.cz/kb/images/freebase/03f5m2w.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/4/49/Flag_of_Ukraine.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f5df1.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f6hm6.jpg", "Ukraine", "Ukraine", "kb", "0"],
                    "entityClass": "location",
                    "words": [{
                        "type": "word",
                        "indexes": ["11", "and", "CC", "and", "7", "COORD", "guys", "guy", "-4", "0", "0", "0", "and", "l:f000776798", "location", "http://en.wikipedia.org/wiki/Ukraine", "http://athena3.fit.vutbr.cz/kb/images/freebase/03f5m2w.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/4/49/Flag_of_Ukraine.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f5df1.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f6hm6.jpg", "Ukraine", "Ukraine", "0", "0", "0", "0", "0", "0", "kb", "0"]
                    }]
                }, {
                    "type": "word",
                    "indexes": ["12", "are", "VBP", "be", "11", "CONJ", "and", "and", "-1", "0", "0", "0", "are", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["13", "telling", "VVG", "tell", "11", "SUFFIX", "and", "and", "-2", "0", "0", "0", "telling", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["14", "their", "PP$", "their", "11", "SUFFIX", "and", "and", "-3", "0", "0", "0", "their", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["15", "story", "NN", "story", "11", "CONJ", "and", "and", "-4", "0", "0", "0", "story", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["16", ".|G__", "SENT", ".", "11", "SUFFIX", "and", "and", "-5", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Ukraine", "http://athena3.fit.vutbr.cz/kb/images/freebase/03f5m2w.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/4/49/Flag_of_Ukraine.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f5df1.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f6hm6.jpg", "Ukraine", "Ukraine", "coref", "1"],
                    "entityClass": "location",
                    "words": [{
                        "type": "word",
                        "indexes": ["1", "Here", "RB", "here", "2", "LOC-PRD", "are", "be", "+1", "0", "0", "0", "here", "l:f000776798", "location", "http://en.wikipedia.org/wiki/Ukraine", "http://athena3.fit.vutbr.cz/kb/images/freebase/03f5m2w.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/4/49/Flag_of_Ukraine.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f5df1.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f6hm6.jpg", "Ukraine", "Ukraine", "0", "0", "0", "0", "0", "0", "coref", "1"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Ukraine", "http://athena3.fit.vutbr.cz/kb/images/freebase/03f5m2w.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/4/49/Flag_of_Ukraine.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f5df1.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f6hm6.jpg", "Ukraine", "Ukraine", "coref", "0"],
                    "entityClass": "location",
                    "words": [{
                        "type": "word",
                        "indexes": ["2", "are", "VBP", "be", "0", "ROOT", "0", "0", "0", "0", "0", "0", "are", "l:f000776798", "location", "http://en.wikipedia.org/wiki/Ukraine", "http://athena3.fit.vutbr.cz/kb/images/freebase/03f5m2w.jpg|http://athena3.fit.vutbr.cz/kb/images/wikimedia/commons/4/49/Flag_of_Ukraine.svg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f5df1.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f6hm6.jpg", "Ukraine", "Ukraine", "0", "0", "0", "0", "0", "0", "coref", "0"]
                    }]
                }, {
                    "type": "word",
                    "indexes": ["3", "some", "DT", "some", "4", "NMOD", "highlights", "highlight", "+1", "0", "0", "0", "some", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["4", "highlights", "NNS", "highlight", "2", "SBJ", "are", "be", "-2", "0", "0", "0", "highlights", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["5", "from", "IN", "from", "4", "NMOD", "highlights", "highlight", "-1", "0", "0", "0", "from", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["6", "the", "DT", "the", "11", "NMOD", "tests", "test", "+5", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["7", "forum…", "JJ", "forum…", "11", "NMOD", "tests", "test", "+4", "0", "0", "0", "forum…", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Ukraine", "http://athena3.fit.vutbr.cz/kb/images/freebase/03f6hm6.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f5m2w.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f5df1.jpg", "Ukrainian", "Ukraine", "kb", "1"],
                    "entityClass": "nationality",
                    "words": [{
                        "type": "word",
                        "indexes": ["8", "Ukrainian", "JJ", "Ukrainian", "11", "NMOD", "tests", "test", "+3", "0", "0", "0", "ukrainian", "n:07t21", "nationality", "http://en.wikipedia.org/wiki/Ukraine", "http://athena3.fit.vutbr.cz/kb/images/freebase/03f6hm6.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f5m2w.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f5df1.jpg", "Ukrainian", "Ukraine", "0", "0", "0", "0", "0", "0", "kb", "1"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Ukraine", "http://athena3.fit.vutbr.cz/kb/images/freebase/03f6hm6.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f5m2w.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f5df1.jpg", "Ukrainian", "Ukraine", "kb", "0"],
                    "entityClass": "nationality",
                    "words": [{
                        "type": "word",
                        "indexes": ["9", "women", "NNS", "woman", "10", "NMOD", "shit", "shit", "+1", "0", "0", "0", "women", "n:07t21", "nationality", "http://en.wikipedia.org/wiki/Ukraine", "http://athena3.fit.vutbr.cz/kb/images/freebase/03f6hm6.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f5m2w.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/03f5df1.jpg", "Ukrainian", "Ukraine", "0", "0", "0", "0", "0", "0", "kb", "0"]
                    }]
                }, {
                    "type": "word",
                    "indexes": ["10", "shit", "NN", "shit", "11", "NMOD", "tests", "test", "+1", "0", "0", "0", "shit", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["11", "tests", "NNS", "test", "5", "PMOD", "from", "from", "-6", "0", "0", "0", "tests", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["12", ".|G__", "SENT", ".", "11", "SUFFIX", "tests", "test", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["1", "When", "WRB", "when", "4", "DEP", "to", "to", "+3", "0", "0", "0", "when", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["2", "you", "PP", "you", "1", "SUFFIX", "When", "when", "-1", "0", "0", "0", "you", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["3", "talk", "VVP", "talk", "1", "SUFFIX", "When", "when", "-2", "0", "0", "0", "talk", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["4", "to", "TO", "to", "0", "ROOT", "0", "0", "0", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["5", "ukrainian", "NN", "ukrainian", "4", "PMOD", "to", "to", "-1", "0", "0", "0", "ukrainian", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["6", ".|G__", "SENT", ".", "5", "SUFFIX", "ukrainian", "ukrainian", "-1", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Pakistan", "http://athena3.fit.vutbr.cz/kb/images/freebase/02sjsqd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dpn3x.jpg", "Pakistani", "Pakistan", "kb", "1"],
                    "entityClass": "nationality",
                    "words": [{
                        "type": "word",
                        "indexes": ["1", "Pakistani", "JJ", "Pakistani", "2", "NMOD", "singles", "single", "+1", "0", "0", "0", "pakistani", "n:05sb1", "nationality", "http://en.wikipedia.org/wiki/Pakistan", "http://athena3.fit.vutbr.cz/kb/images/freebase/02sjsqd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dpn3x.jpg", "Pakistani", "Pakistan", "0", "0", "0", "0", "0", "0", "kb", "1"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Pakistan", "http://athena3.fit.vutbr.cz/kb/images/freebase/02sjsqd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dpn3x.jpg", "Pakistani", "Pakistan", "kb", "0"],
                    "entityClass": "nationality",
                    "words": [{
                        "type": "word",
                        "indexes": ["2", "singles", "NNS", "single", "5", "NMOD", "community", "community", "+3", "0", "0", "0", "singles", "n:05sb1", "nationality", "http://en.wikipedia.org/wiki/Pakistan", "http://athena3.fit.vutbr.cz/kb/images/freebase/02sjsqd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dpn3x.jpg", "Pakistani", "Pakistan", "0", "0", "0", "0", "0", "0", "kb", "0"]
                    }]
                }, {
                    "type": "word",
                    "indexes": ["3", "and", "CC", "and", "2", "COORD", "singles", "single", "-1", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["4", "dating", "VVG", "date", "2", "SUFFIX", "singles", "single", "-2", "0", "0", "0", "dating", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["5", "community", "NN", "community", "0", "ROOT", "0", "0", "0", "0", "0", "0", "community", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["6", "for", "IN", "for", "5", "NMOD", "community", "community", "-1", "0", "0", "0", "for", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["7", "single", "JJ", "single", "9", "NMOD", "women", "woman", "+2", "0", "0", "0", "single", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Pakistan", "http://athena3.fit.vutbr.cz/kb/images/freebase/02sjsqd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dpn3x.jpg", "Pakistani", "Pakistan", "kb", "1"],
                    "entityClass": "nationality",
                    "words": [{
                        "type": "word",
                        "indexes": ["8", "Pakistani", "JJ", "Pakistani", "9", "NMOD", "women", "woman", "+1", "0", "0", "0", "pakistani", "n:05sb1", "nationality", "http://en.wikipedia.org/wiki/Pakistan", "http://athena3.fit.vutbr.cz/kb/images/freebase/02sjsqd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dpn3x.jpg", "Pakistani", "Pakistan", "0", "0", "0", "0", "0", "0", "kb", "1"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Pakistan", "http://athena3.fit.vutbr.cz/kb/images/freebase/02sjsqd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dpn3x.jpg", "Pakistani", "Pakistan", "kb", "0"],
                    "entityClass": "nationality",
                    "words": [{
                        "type": "word",
                        "indexes": ["9", "women", "NNS", "woman", "6", "PMOD", "for", "for", "-3", "0", "0", "0", "women", "n:05sb1", "nationality", "http://en.wikipedia.org/wiki/Pakistan", "http://athena3.fit.vutbr.cz/kb/images/freebase/02sjsqd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dpn3x.jpg", "Pakistani", "Pakistan", "0", "0", "0", "0", "0", "0", "kb", "0"]
                    }]
                }, {
                    "type": "word",
                    "indexes": ["10", "and", "CC", "and", "9", "COORD", "women", "woman", "-1", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["11", "men", "NNS", "man", "10", "CONJ", "and", "and", "-1", "0", "0", "0", "men", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["12", ".|G__", "SENT", ".", "10", "SUFFIX", "and", "and", "-2", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["1", "Join", "VV", "join", "0", "ROOT", "0", "0", "0", "0", "0", "0", "join", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["2", "now", "RB", "now", "3", "PMOD", "to", "to", "+1", "0", "0", "0", "now", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["3", "to", "TO", "to", "1", "PMOD", "Join", "join", "-2", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["4", "meet", "VV", "meet", "3", "SUFFIX", "to", "to", "-1", "0", "0", "0", "meet", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Pakistan", "http://athena3.fit.vutbr.cz/kb/images/freebase/02sjsqd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dpn3x.jpg", "Pakistani", "Pakistan", "kb", "1"],
                    "entityClass": "nationality",
                    "words": [{
                        "type": "word",
                        "indexes": ["5", "Pakistani", "JJ", "Pakistani", "7", "NMOD", "amp", "amp", "+2", "0", "0", "0", "pakistani", "n:05sb1", "nationality", "http://en.wikipedia.org/wiki/Pakistan", "http://athena3.fit.vutbr.cz/kb/images/freebase/02sjsqd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dpn3x.jpg", "Pakistani", "Pakistan", "0", "0", "0", "0", "0", "0", "kb", "1"]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["http://en.wikipedia.org/wiki/Pakistan", "http://athena3.fit.vutbr.cz/kb/images/freebase/02sjsqd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dpn3x.jpg", "Pakistani", "Pakistan", "kb", "0"],
                    "entityClass": "nationality",
                    "words": [{
                        "type": "word",
                        "indexes": ["6", "girls", "NNS", "girl", "7", "NMOD", "amp", "amp", "+1", "0", "0", "0", "girls", "n:05sb1", "nationality", "http://en.wikipedia.org/wiki/Pakistan", "http://athena3.fit.vutbr.cz/kb/images/freebase/02sjsqd.jpg|http://athena3.fit.vutbr.cz/kb/images/freebase/02dpn3x.jpg", "Pakistani", "Pakistan", "0", "0", "0", "0", "0", "0", "kb", "0"]
                    }]
                }, {
                    "type": "word",
                    "indexes": ["7", "amp", "NN", "amp", "3", "PMOD", "to", "to", "-4", "0", "0", "0", "amp", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["8", ";|G__", ":", ";", "1", "P", "Join", "join", "-7", "0", "0", "0", ";", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["9", "men", "NNS", "man", "1", "NMOD", "Join", "join", "-8", "0", "0", "0", "men", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["10", ".|G__", "SENT", ".", "1", "SUFFIX", "Join", "join", "-9", "0", "0", "0", ".", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["1", "Explore", "VV", "explore", "0", "ROOT", "0", "0", "0", "0", "0", "0", "explore", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["2", "Indonesia", "NP", "Indonesia", "1", "SUFFIX", "Explore", "explore", "-1", "0", "0", "0", "indonesia", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["3", "holidays", "NNS", "holiday", "0", "ROOT", "0", "0", "0", "0", "0", "0", "holidays", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }
        },
        "canExtend": true
    }, {
        "host": "127.0.0.1:8001",
        "collection": "name",
        "documentId": 189,
        "location": 2,
        "size": 50,
        "url": "http://anikendra.com/football-manager-2006-cheat-training-schedule/",
        "documentTitle": "  Football Manager 2006 cheat training schedule ¦ Anikendra",
        "payload": {
            "type": "new", "content": {
                "content": [{
                    "type": "queryMatch",
                    "queryMatch": {"from": 0, "to": 1},
                    "content": [{
                        "type": "entity",
                        "attributes": ["0", "0", "2006", "0", "0", "date", "1"],
                        "entityClass": "date",
                        "words": [{
                            "type": "word",
                            "indexes": ["3", "2006", "CD", "@card@", "5", "NMOD", "training", "training", "+2", "0", "0", "0", "2006", "t:ca39c91065", "date", "0", "0", "2006", "0", "0", "0", "0", "0", "0", "0", "date", "1"]
                        }]
                    }]
                }, {
                    "type": "entity",
                    "attributes": ["0", "0", "2006", "0", "0", "date", "0"],
                    "entityClass": "date",
                    "words": [{
                        "type": "word",
                        "indexes": ["4", "cheat", "NN", "cheat", "5", "NMOD", "training", "training", "+1", "0", "0", "0", "cheat", "t:ca39c91065", "date", "0", "0", "2006", "0", "0", "0", "0", "0", "0", "0", "date", "0"]
                    }]
                }, {
                    "type": "word",
                    "indexes": ["5", "training", "NN", "training", "6", "NMOD", "schedule", "schedule", "+1", "0", "0", "0", "training", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["6", "schedule", "NN", "schedule", "0", "ROOT", "0", "0", "0", "0", "0", "0", "schedule", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["1", "__IMG__", "NN", "__IMG__", "0", "ROOT", "0", "0", "0", "0", "0", "0", "__img__", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["1", "Well", "UH", "Well", "5", "NMOD", "things", "thing", "+4", "0", "0", "0", "well", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["2", "yes", "UH", "yes", "5", "NMOD", "things", "thing", "+3", "0", "0", "0", "yes", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["3", "to", "TO", "to", "5", "NMOD", "things", "thing", "+2", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["4", "make", "VV", "make", "3", "SUFFIX", "to", "to", "-1", "0", "0", "0", "make", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["5", "things", "NNS", "thing", "10", "NMOD", "course", "course", "+5", "0", "0", "0", "things", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["6", "more", "RBR", "more", "10", "NMOD", "course", "course", "+4", "0", "0", "0", "more", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["7", "easier", "JJR", "easy", "10", "NMOD", "course", "course", "+3", "0", "0", "0", "easier", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["8", "and", "CC", "and", "7", "COORD", "easier", "easy", "-1", "0", "0", "0", "and", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["9", "off", "JJ", "off", "8", "CONJ", "and", "and", "-1", "0", "0", "0", "off", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["10", "course", "NN", "course", "0", "ROOT", "0", "0", "0", "0", "0", "0", "course", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["11", "to", "TO", "to", "10", "NMOD", "course", "course", "-1", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["12", "save", "VV", "save", "10", "SUFFIX", "course", "course", "-2", "0", "0", "0", "save", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["13", "time", "NN", "time", "0", "ROOT", "0", "0", "0", "0", "0", "0", "time", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["14", "(", "(", "(", "15", "P", "something", "something", "+1", "0", "0", "0", "(", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["15", "something|G__", "NN", "something", "13", "PRN", "time", "time", "-2", "0", "0", "0", "something", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["16", "which", "WDT", "which", "17", "SBJ", "is", "be", "+1", "0", "0", "0", "which", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["17", "is", "VBZ", "be", "15", "NMOD", "something", "something", "-2", "0", "0", "0", "is", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["18", "very", "RB", "very", "19", "AMOD", "precious", "precious", "+1", "0", "0", "0", "very", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["19", "precious", "JJ", "precious", "17", "PRD", "is", "be", "-2", "0", "0", "0", "precious", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["20", "now", "RB", "now", "17", "TMP", "is", "be", "-3", "0", "0", "0", "now", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["21", ")|G__", ")", ")", "15", "P", "something", "something", "-6", "0", "0", "0", ")", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["22", ",|G__", ",", ",", "13", "P", "time", "time", "-9", "0", "0", "0", ",", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["23", "I", "PP", "I", "13", "SUFFIX", "time", "time", "-10", "0", "0", "0", "i", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["24", "decided", "VVD", "decide", "13", "SUFFIX", "time", "time", "-11", "0", "0", "0", "decided", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["25", "to", "TO", "to", "13", "NMOD", "time", "time", "-12", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["26", "hack", "VV", "hack", "13", "SUFFIX", "time", "time", "-13", "0", "0", "0", "hack", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["27", "the", "DT", "the", "29", "NMOD", "schedule", "schedule", "+2", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["28", "training", "NN", "training", "29", "NMOD", "schedule", "schedule", "+1", "0", "0", "0", "training", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["29", "schedule", "NN", "schedule", "13", "APPO", "time", "time", "-16", "0", "0", "0", "schedule", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["30", "which", "WDT", "which", "31", "SBJ", "will", "will", "+1", "0", "0", "0", "which", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["31", "will", "MD", "will", "29", "NMOD", "schedule", "schedule", "-2", "0", "0", "0", "will", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["32", "help", "VV", "help", "13", "SUFFIX", "time", "time", "-19", "0", "0", "0", "help", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["33", "me", "PP", "me", "13", "SUFFIX", "time", "time", "-20", "0", "0", "0", "me", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["34", "to", "TO", "to", "13", "NMOD", "time", "time", "-21", "0", "0", "0", "to", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["35", "get", "VV", "get", "13", "SUFFIX", "time", "time", "-22", "0", "0", "0", "get", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["36", "the", "DT", "the", "37", "NMOD", "best", "good", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["37", "best", "JJS", "good", "13", "PRD", "time", "time", "-24", "0", "0", "0", "best", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["38", "out", "RB", "out", "13", "NMOD", "time", "time", "-25", "0", "0", "0", "out", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["39", "of", "IN", "of", "13", "NMOD", "time", "time", "-26", "0", "0", "0", "of", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["40", "players", "NNS", "player", "39", "PMOD", "of", "of", "-1", "0", "0", "0", "players", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["41", "who", "WP", "who", "42", "SBJ", "will", "will", "+1", "0", "0", "0", "who", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["42", "will", "MD", "will", "40", "NMOD", "players", "player", "-2", "0", "0", "0", "will", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["43", "then", "RB", "then", "42", "TMP", "will", "will", "-1", "0", "0", "0", "then", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["44", "do", "VV", "do", "13", "SUFFIX", "time", "time", "-31", "0", "0", "0", "do", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["45", "the", "DT", "the", "46", "NMOD", "job", "job", "+1", "0", "0", "0", "the", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }, {
                    "type": "word",
                    "indexes": ["46", "job", "NN", "job", "13", "APPO", "time", "time", "-33", "0", "0", "0", "job", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]
                }]
            }
        },
        "canExtend": true
    }, {
        "host": "127.0.0.1:8001",
        "collection": "name",
        "documentId": 410,
        "location": 708,
        "size": 50,
        "url": "http://aqualab.cs.northwestern.edu/publications/290-in-and-out-of-cuba-characterizing-cuba-s-connectivity",
        "documentTitle": "In and Out of Cuba: Characterizing Cuba's Connectivity",
        "payload": {"type": "new", "content": {"content": []}},
        "canExtend": true
    }], "errors": {}
}