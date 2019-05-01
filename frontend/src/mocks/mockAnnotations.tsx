import React from 'react';
import {Annotation} from "../entities/Annotation";


export const EdSheeran: Annotation = {
    id: 1,
    color: "green",
    text: "Ed Sheeran",
    image: 'https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTM5ODkxNzYyODU1NDIwOTM4/ed-sheeran-gettyimages-494227430_1600jpg.jpg',
    type: 'Person',
    content: [
        {
            name: 'name',
            value: 'Ed Sheeran'
        },
        {
            name: 'url',
            value: 'https://cs.wikipedia.org/wiki/Ed_Sheeran'
        }, {
            name: 'gender',
            value: 'male'
        }, {
            name: 'birthplace',
            value: 'Halifax, England'
        }, {
            name: 'birthdate',
            value: '17.2.1991'
        },
    ]
}

export const DonaldTrump: Annotation = {
    id: 2,
    color: "red",
    text: "Donald Trump",
    image: 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Donald_Trump_official_portrait.jpg/800px-Donald_Trump_official_portrait.jpg',
    type: 'Person',
    content: [
        {
            name: 'name',
            value: 'Donald Trump'
        },
        {
            name: 'url',
            value: 'https://cs.wikipedia.org/wiki/Donald_Trump'
        }, {
            name: 'gender',
            value: 'male'
        }, {
            name: 'birthplace',
            value: 'Queens, New York'
        }, {
            name: 'birthdate',
            value: '14.6.1946'
        },
    ]
};


export const KarlovyVary: Annotation = {
    id: 3,
    color: "blue",
    text: "Karlovy Vary",
    image: 'https://upload.wikimedia.org/wikipedia/commons/d/d8/Karlovy_Vary_Czech.jpg',
    type: 'Place',
    content: [
        {
            name: 'name',
            value: 'Karlovy Vary'
        },
        {
            name: 'url',
            value: 'https://cs.wikipedia.org/wiki/Karlovy_Vary'
        }, {
            name: 'Country',
            value: 'Czechia'
        },
    ]
};
