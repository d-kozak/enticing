import AnnotatedElement from "../components/annotations/AnnotatedElement";
import React from 'react';

export const mockAddAnnotations = (input: string): any => {
    const annotated = input.split(/\s+/)
        .map((word, index) => {
            if (index % 5 == 0) {
                return <AnnotatedElement key={index} id={`{index}`} text={word + " "}/>
            } else {
                return word + " ";
            }
        })
    return <span>
        {annotated}
    </span>
}