import {loremIpsumLong} from "./loremIpsum";

it("transform", () => {

    // @ts-ignore
    const input = loremIpsumLong.replace("\n", ' ')

    const transformed = input.split(' ')
        .map((text: string) => `new Word(["${text}"])`)
        .join(',')

    console.log('new NewAnnotatedText([' + transformed + '])');

})