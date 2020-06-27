import {decodeQuery, encodeQuery} from "../queryEncoding";

it('encode', () => {
    const input = "?query=a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url";
    expect(encodeQuery(input)).toBe("?query=a:=nertag:person%20%3C%20lemma:(influence%20%7C%20impact%20%7C%20(paid%20%3C%20tribute)%20)%20%3C%20b:=nertag:person%20ctx:sent%20%26%26%20a.url%20!=%20b.url")
});

it('decode', () => {
    const input = "?query=a:=nertag:person%20%3C%20lemma:(influence%20%7C%20impact%20%7C%20(paid%20%3C%20tribute)%20)%20%3C%20b:=nertag:person%20ctx:sent%20%26%26%20a.url%20!=%20b.url";
    expect(decodeQuery(input)).toBe("a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url")
});