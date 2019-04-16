import AnnotatedElement from "../components/annotations/AnnotatedElement";
import React from 'react';
import {Annotation} from "../entities/Annotation";


const edSheeran: Annotation = {
    color: "red",
    text: "Ed Sheeran",
    image: 'https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTM5ODkxNzYyODU1NDIwOTM4/ed-sheeran-gettyimages-494227430_1600jpg.jpg',
    content: [
        {
            name: 'type',
            value: 'person'
        }
    ]
}

export const mockAddAnnotations = (input: string): any => {
    const annotated = input.split(/\s+/)
        .map((word, index) => {
            if (index % 5 == 0) {
                return <AnnotatedElement annotation={edSheeran}/>
            } else {
                return word + " ";
            }
        })
    return <span>
        {annotated}
    </span>
}