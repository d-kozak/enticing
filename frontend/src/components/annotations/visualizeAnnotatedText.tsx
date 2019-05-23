import {Annotation} from "../../entities/Annotation";
import {ProcessedAnnotatedText, TextWithAnnotation} from "./ProcessedAnnotatedText";
import React from "react";
import AnnotatedElement from "./AnnotatedElement";
import EnrichedText from "./EnrichedText";

export const visualizeElem = (elem: ProcessedAnnotatedText, annotations: Map<number, Annotation>): React.ReactNode => {
    if (typeof elem === "string") {
        return elem;
    } else if (elem instanceof TextWithAnnotation) {
        return <AnnotatedElement annotation={annotations.get(elem.annotationId)!} text={elem.text} color="blue"/>
    } else {
        const content = <React.Fragment>
            {elem.text.map(i => visualizeElem(i, annotations))}
        </React.Fragment>
        return <EnrichedText content={content} decoration={elem.decoration.text}/>
    }
}

export const visualizeAnnotatedText = (text: Array<ProcessedAnnotatedText>, annotations: Map<number, Annotation>): React.ReactNode => {
    return <React.Fragment>
        {
            text.map(elem => visualizeElem(elem, annotations))
        }
    </React.Fragment>
}