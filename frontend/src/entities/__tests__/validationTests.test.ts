import {
    isAnnotatedText,
    isAnnotation,
    isAnnotationPosition,
    isMatchedRegion,
    isQueryMapping,
    MatchedRegion
} from "../Annotation";
import {isCorpusFormat} from "../CorpusFormat";

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

    it('corpus format validation', () => {
        const input = {
            "corpusName": "CC",
            "indexes": {
                "position": "Position of the word in the document",
                "token": "Original word in the document",
                "tag": "tag",
                "lemma": "Lemma of the word",
                "parpos": "parpos",
                "function": "function",
                "parwrod": "parword",
                "parlemma": "parlemma",
                "paroffset": "paroffset",
                "link": "link",
                "length": "length",
                "docuri": "docuri",
                "lower": "lower",
                "nerid": "nerid",
                "nertag": "nertag",
                "param0": "",
                "param1": "",
                "param2": "",
                "param3": "",
                "param4": "",
                "param5": "",
                "param6": "",
                "param7": "",
                "param8": "",
                "param9": "",
                "nertype": "nertype",
                "nerlength": "nerlength"
            },
            "entities": {
                "person": {
                    "description": "",
                    "attributes": {
                        "url": "",
                        "image": "",
                        "name": "",
                        "gender": "",
                        "birthplace": "",
                        "birthdate": "",
                        "deathplace": "",
                        "deathdate": "",
                        "profession": "",
                        "nationality": "",
                        "nertype": "nertype",
                        "nerlength": "nerlength"
                    }
                },
                "artist": {
                    "description": "",
                    "attributes": {
                        "url": "",
                        "image": "",
                        "name": "",
                        "gender": "",
                        "birthplace": "",
                        "birthdate": "",
                        "deathplace": "",
                        "deathdate": "",
                        "role": "",
                        "nationality": "",
                        "nertype": "nertype",
                        "nerlength": "nerlength"
                    }
                },
                "location": {
                    "description": "",
                    "attributes": {
                        "url": "",
                        "image": "",
                        "name": "",
                        "country": "",
                        "nertype": "nertype",
                        "nerlength": "nerlength"
                    }
                },
                "artwork": {
                    "description": "",
                    "attributes": {
                        "url": "",
                        "image": "",
                        "name": "",
                        "form": "",
                        "datebegun": "",
                        "datecompleted": "",
                        "movement": "",
                        "genre": "",
                        "author": "",
                        "nertype": "nertype",
                        "nerlength": "nerlength"
                    }
                },
                "event": {
                    "description": "",
                    "attributes": {
                        "url": "",
                        "image": "",
                        "name": "",
                        "startdate": "",
                        "enddate": "",
                        "location": "",
                        "nertype": "nertype",
                        "nerlength": "nerlength"
                    }
                },
                "museum": {
                    "description": "",
                    "attributes": {
                        "url": "",
                        "image": "",
                        "name": "",
                        "type": "",
                        "established": "",
                        "director": "",
                        "location": "",
                        "nertype": "nertype",
                        "nerlength": "nerlength"
                    }
                },
                "family": {
                    "description": "",
                    "attributes": {
                        "url": "",
                        "image": "",
                        "name": "",
                        "role": "",
                        "nationality": "",
                        "members": "",
                        "nertype": "nertype",
                        "nerlength": "nerlength"
                    }
                },
                "group": {
                    "description": "",
                    "attributes": {
                        "url": "",
                        "image": "",
                        "name": "",
                        "role": "",
                        "nationality": "",
                        "nertype": "nertype",
                        "nerlength": "nerlength"
                    }
                },
                "nationality": {
                    "description": "",
                    "attributes": {
                        "url": "",
                        "image": "",
                        "name": "",
                        "country": "",
                        "nertype": "nertype",
                        "nerlength": "nerlength"
                    }
                },
                "date": {
                    "description": "",
                    "attributes": {
                        "url": "",
                        "image": "",
                        "year": "",
                        "month": "",
                        "day": "",
                        "nertype": "nertype",
                        "nerlength": "nerlength"
                    }
                },
                "interval": {
                    "description": "",
                    "attributes": {
                        "url": "",
                        "image": "",
                        "fromyear": "",
                        "frommonth": "",
                        "fromday": "",
                        "toyear": "",
                        "tomonth": "",
                        "today": "",
                        "nertype": "nertype",
                        "nerlength": "nerlength"
                    }
                },
                "form": {
                    "description": "",
                    "attributes": {"url": "", "image": "", "name": "", "nertype": "nertype", "nerlength": "nerlength"}
                },
                "medium": {
                    "description": "",
                    "attributes": {"url": "", "image": "", "name": "", "nertype": "nertype", "nerlength": "nerlength"}
                },
                "mythology": {
                    "description": "",
                    "attributes": {"url": "", "image": "", "name": "", "nertype": "nertype", "nerlength": "nerlength"}
                },
                "movement": {
                    "description": "",
                    "attributes": {"url": "", "image": "", "name": "", "nertype": "nertype", "nerlength": "nerlength"}
                },
                "genre": {
                    "description": "",
                    "attributes": {"url": "", "image": "", "name": "", "nertype": "nertype", "nerlength": "nerlength"}
                }
            }
        }
        expect(isCorpusFormat(input)).toBe(true);
    })
})