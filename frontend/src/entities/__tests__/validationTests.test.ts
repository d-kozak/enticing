import {
    isAnnotatedText,
    isAnnotation,
    isAnnotationPosition,
    isMatchedRegion,
    isQueryMapping,
    MatchedRegion
} from "../Annotation";

describe('validation tests', () => {
    it('matched region validation', () => {
        const empty = {};
        expect(isMatchedRegion(empty)).toBe(false);
        const fromIsNotNumber = {
            from: 'foo',
            size: 3
        };
        expect(isMatchedRegion(fromIsNotNumber)).toBe(false);
        const sizeIsNegative = {
            from: 0,
            size: -1
        };
        expect(isMatchedRegion(sizeIsNegative)).toBe(false);
        const valid: MatchedRegion = {
            from: 0,
            size: 5,
            to: 5
        };
        expect(isMatchedRegion(valid)).toBe(true);
    });

    it('query mapping validation', () => {
        const empty = {};
        expect(isQueryMapping(empty)).toBe(false);
        const invalidTextIndex = {
            textIndex: {},
            queryIndex: {from: 10, size: 10}
        };
        expect(isQueryMapping(invalidTextIndex)).toBe(false);

        expect(isQueryMapping({
            textIndex: {
                from: 3,
                size: 1
            },
            queryIndex: {
                from: 1,
                size: 0
            }
        })).toBe(false)

        expect(isQueryMapping({
            textIndex: {
                from: 3,
                size: 2
            },
            queryIndex: {
                from: 1,
                size: 4
            }
        })).toBe(true)

    });

    it('annotation validation', () => {
        const invalid = [
            {},
            {id: ''},
            {id: ''},
            {id: '', content: {}},
        ];
        for (let annotation of invalid) {
            expect(isAnnotation(annotation)).toBe(false)
        }
        expect(isAnnotation({id: 'abc', content: {foo: 'bar'}})).toBe(true)
    });

    it('annotation position validation', () => {
        const invalid = [
            {},
            {annotationId: ''},
            {
                annotationId: '', match: {
                    from: 1,
                    size: 2
                }
            },
            {
                annotationId: 'id', match: {
                    from: 1,
                    size: 0
                }
            },
            {
                annotationId: 'id', match: {
                    from: 1,
                    size: 1
                }, subAnnotations: [{
                    annotationId: '',
                    match: {
                        from: 1,
                        size: 1
                    }
                }]
            },
            {
                annotationId: 'id', match: {
                    from: 1,
                    size: 1
                }, subAnnotations: [{
                    annotationId: 'id',
                    match: {
                        from: 1,
                        size: -1

                    }
                }]
            }
        ];
        for (let position of invalid) {
            expect(isAnnotationPosition(position)).toBe(false)
        }

        const valid = [
            {
                annotationId: 'id', match: {
                    from: 1,
                    size: 1
                }, subAnnotations: []
            }, {
                annotationId: 'id',
                match: {
                    from: 1,
                    size: 1
                },
                subAnnotations: [{
                    annotationId: 'id2',
                    match: {
                        from: 3,
                        size: 3
                    }
                }]
            }
        ];
        for (let position of valid) {
            expect(isAnnotationPosition(position)).toBe(true)
        }
    });

    it('annotated text validation', () => {
        const invalid = [{},];
        for (let text of invalid) {
            expect(isAnnotatedText(text)).toBe(false)
        }
    })
})