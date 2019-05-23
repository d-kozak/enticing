import {firstResult, secondResult, thirdResult} from "../../../mocks/mockSearchApi";
import {processAnnotatedText, splitAnnotations} from "../processAnnotatedText";
import {TextWithAnnotation, TextWithDecoration} from "../ProcessedAnnotatedText";

describe("process annotated text", () => {
    it("ed sheeran full process", () => {
        const edSheeran = firstResult;
        const [, processed] = processAnnotatedText(edSheeran.snippet);
        expect(processed.length).toBe(2)
        const [first, second] = processed;
        expect(first instanceof TextWithDecoration).toBe(true)
        if (!(first instanceof TextWithDecoration)) {
            fail('expecting text with decoration');
            return;
        }
        expect(first.text.length).toBe(1)
        const annotation = first.text[0];
        if (!(annotation instanceof TextWithAnnotation)) {
            fail('expecting text with annotation');
            return;
        }
        expect(annotation.annotationId).toBe(1)
        expect(annotation.text).toBe("Ed Sheeran");

        if (typeof second !== "string") {
            fail('expecting string');
            return;
        }
        expect(second).toBe(" visited Liberia and meets JD, a homeless Liberian 14-year-old boy. After Sheeran saw an older man hitting JD in public, he knew")
    })

    it("donald trump full process", () => {
        const donaldTrump = secondResult;
        const [, processed] = processAnnotatedText(donaldTrump.snippet);
        expect(processed.length).toBe(3)
        const [decoration, annotation, text] = processed;
        if (!(decoration instanceof TextWithDecoration)) {
            fail('expecting text with decoration');
            return;
        }
        expect(decoration.text.length).toBe(2)
        {
            const [decorationText, decorationAnnotation] = decoration.text;
            if (typeof decorationText !== "string") {
                fail("expecting string");
                return;
            }
            expect(decorationText).toBe("President ")
            if (!(decorationAnnotation instanceof TextWithAnnotation)) {
                fail("expecting test with annotation");
                return;
            }
            expect(decorationAnnotation.text).toBe("Donald")
            expect(decorationAnnotation.annotationId).toBe(2)
        }
        if (!(annotation instanceof TextWithAnnotation)) {
            fail("expecting text with annotation");
            return;
        }
        expect(annotation.text).toBe(" Trump")
        expect(annotation.annotationId).toBe(2)
        expect(text).toBe(" visited San Antonio for a closed-door fundraiser at The Argyle, the exclusive dinner club in Alamo Heights. Air Force ...")
    });

    it("split annotations ed", () => {
        const edSheeran = firstResult;
        const split = splitAnnotations(edSheeran.snippet);
        expect(split.positions.length)
            .toBe(1)
        const position = split.positions[0]
        expect(position.from).toBe(0)
        expect(position.to).toBe(10)
    })

    it("split annotations donald", () => {
        const donald = secondResult;
        const split = splitAnnotations(donald.snippet);
        expect(split.positions.length)
            .toBe(2)
        const [position1, position2] = split.positions
        expect(position1.from).toBe(10)
        expect(position1.to).toBe(16)
        expect(position2.from).toBe(16)
        expect(position2.to).toBe(22)

    })

    it("split annotations vary", () => {
        const vary = thirdResult;
        const split = splitAnnotations(vary.snippet);
        expect(split.positions.length)
            .toBe(2)
        const [position1, position2] = split.positions
        expect(position1.from).toBe(114)
        expect(position1.to).toBe(117)
        expect(position2.from).toBe(117)
        expect(position2.to).toBe(127)

    })

});

describe("dto test", () => {
    it("text with annotation equals", () => {
        const a = new TextWithAnnotation('foo', 1)
        const b = new TextWithAnnotation('foo', 1)
        const c = new TextWithAnnotation('foo2', 1)
        const d = new TextWithAnnotation('foo2', 2)
        expect(a.equals(b)).toBe(true)
        expect(a.equals(c)).toBe(false)
        expect(d.equals(c)).toBe(false)
    });
})