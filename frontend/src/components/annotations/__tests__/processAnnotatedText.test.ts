import {firstResult, secondResult, thirdResult} from "../../../mocks/mockSearchApi";
import {processAnnotatedText, splitAnnotations} from "../processAnnotatedText";
import {Decoration, TextWithAnnotation, TextWithDecoration} from "../ProcessedAnnotatedText";

describe("process annotated text", () => {
    it("ed sheeran full process", () => {
        const edSheeran = firstResult;
        const [, processed] = processAnnotatedText(edSheeran.snippet);
        expect(processed.length).toBe(2)
        const [first, second] = processed;

        const expectedFirst = new TextWithDecoration(
            [new TextWithAnnotation("Ed Sheeran", 1)],
            new Decoration("nertag:person")
        );
        const expectedSecond = " visited Liberia and meets JD, a homeless Liberian 14-year-old boy. After Sheeran saw an older man hitting JD in public, he knew"

        expect(first).toEqual(expectedFirst)
        expect(second).toEqual(expectedSecond)
    })

    it("donald trump full process", () => {
        const donaldTrump = secondResult;
        const [, processed] = processAnnotatedText(donaldTrump.snippet);
        expect(processed.length).toBe(3)
        const [decoration, annotation, text] = processed;

        const expectedDecoration = new TextWithDecoration(
            ["President ", new TextWithAnnotation("Donald", 2)],
            new Decoration("nertag:person")
        );
        const expectedAnnotation = new TextWithAnnotation(" Trump", 2)
        const expectedText = " visited San Antonio for a closed-door fundraiser at The Argyle, the exclusive dinner club in Alamo Heights. Air Force ..."


        expect(decoration).toEqual(expectedDecoration)
        expect(annotation).toEqual(expectedAnnotation)
        expect(text).toEqual(expectedText)

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