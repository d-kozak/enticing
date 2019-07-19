import React from 'react';
import {Entity, Word} from "../components/annotations/new/NewAnnotatedText";


export const EdSheeran: Entity = new Entity(['Ed Sheeran',
        'https://cs.wikipedia.org/wiki/Ed_Sheeran',
        'male',
        'Halifax: England',
        '17.2.1991',
        'https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTM5ODkxNzYyODU1NDIwOTM4/ed-sheeran-gettyimages-494227430_1600jpg.jpg',
        'person'],
    "person",
    [new Word(["Ed"]), new Word(["Sheeran", "sheer"])])


export const DonaldTrump: Entity = new Entity(['Donald Trump',
    'https://cs.wikipedia.org/wiki/Donald_Trump',
    'male',
    'Queens, New York',
    '14.6.1946',
    'https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Donald_Trump_official_portrait.jpg/800px-Donald_Trump_official_portrait.jpg',
    'person'], "person", [new Word(["Donald"]), new Word(["Trump"])]);


export const KarlovyVary: Entity = new Entity(['Karlovy Vary',
    'https://cs.wikipedia.org/wiki/Karlovy_Vary',
    'Czechia',
    'https://upload.wikimedia.org/wikipedia/commons/d/d8/Karlovy_Vary_Czech.jpg',
    'location'], "location", [new Word(["Karlovy", "karel"]), new Word(["Vary"])]);



