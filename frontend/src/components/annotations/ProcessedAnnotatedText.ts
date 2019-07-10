export class Decoration {
    text: string

    constructor(text: string) {
        this.text = text;

    }
}

export class TextWithDecoration {
    text: Array<string | TextWithAnnotation>;
    decoration: Decoration;

    constructor(text: Array<string | TextWithAnnotation>, decoration: Decoration) {
        this.text = text;
        this.decoration = decoration;
    }
}

export class TextWithAnnotation {
    text: string;
    annotationId: string;

    constructor(text: string, annotationId: string) {
        this.text = text;
        this.annotationId = annotationId;
    }

}

export type ProcessedAnnotatedText = string | TextWithDecoration | TextWithAnnotation