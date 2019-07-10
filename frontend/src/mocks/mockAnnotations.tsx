import React from 'react';
import {Annotation} from "../entities/Annotation";


export const EdSheeran: Annotation = {
    id: "ed",
    content: {
        name: 'Ed Sheeran',
        url: 'https://cs.wikipedia.org/wiki/Ed_Sheeran',
        gender: 'male',
        birthplace: 'Halifax: England',
        birthdate: '17.2.1991',
        image: 'https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTM5ODkxNzYyODU1NDIwOTM4/ed-sheeran-gettyimages-494227430_1600jpg.jpg',
        nertag: 'person',
    }

}
export const DonaldTrump: Annotation = {
    id: "donald",
    content: {
        name: 'Donald Trump',
        url: 'https://cs.wikipedia.org/wiki/Donald_Trump',
        gender: 'male',
        birthplace: 'Queens, New York',
        birthdate: '14.6.1946',
        image: 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Donald_Trump_official_portrait.jpg/800px-Donald_Trump_official_portrait.jpg',
        nertag: 'person'
    }
};


export const KarlovyVary: Annotation = {
    id: "kv",
    content: {
        name: 'Karlovy Vary',
        url: 'https://cs.wikipedia.org/wiki/Karlovy_Vary',
        Country: 'Czechia',
        image: 'https://upload.wikimedia.org/wikipedia/commons/d/d8/Karlovy_Vary_Czech.jpg',
        nertag: 'place'
    }
};
