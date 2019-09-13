import {findInterval} from "../findInterval";

describe('find interval', () => {
    it('no limits on 10', () => {
        expect(findInterval(10, 0, 100, 10))
            .toStrictEqual([5, 14])

    });

    it('first page, no way to move left', () => {
        expect(findInterval(1, 1, 100, 10))
            .toStrictEqual([1, 10])
    });

    it('last page, no way to more right', () => {
        expect(findInterval(20, 1, 20, 10))
            .toStrictEqual([11, 20]);
    });
});