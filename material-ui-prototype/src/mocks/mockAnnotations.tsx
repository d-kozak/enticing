import React from 'react';
import {Annotation, AnnotationPosition} from "../entities/Annotation";


export const edSheeran: Annotation = {
    id: 1,
    color: "red",
    text: "Ed Sheeran",
    image: 'https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTM5ODkxNzYyODU1NDIwOTM4/ed-sheeran-gettyimages-494227430_1600jpg.jpg',
    type: 'Person',
    content: [
        {
            name: 'name',
            value: 'Ed Sheeran'
        }, {
            name: 'birthdate',
            value: '17.2.1991'
        }, {
            name: 'gender',
            value: 'male'
        }
    ]
}

