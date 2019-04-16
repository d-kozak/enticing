export type color = 'blue' | 'green' | 'red' | 'orange' | 'purple';

export interface AnnotationValue {
    name: string;
    value: string
}

export interface Annotation {
    text: string;
    color: color;
    content: Array<AnnotationValue>;
    image?: string
    type: string
}
