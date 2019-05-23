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
    annotationId: number;

    constructor(text: string, annotationId: number) {
        this.text = text;
        this.annotationId = annotationId;
    }

    equals(other: any) {
        if (!(other instanceof TextWithAnnotation)) return false
        if (other.text !== this.text) return false
        return other.annotationId === this.annotationId;
    }

}

export type ProcessedAnnotatedText = string | TextWithDecoration | TextWithAnnotation