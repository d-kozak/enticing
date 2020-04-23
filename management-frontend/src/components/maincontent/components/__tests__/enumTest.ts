import {ComponentType} from "../../../../entities/ComponentInfo";


it('simple loop', () => {
    for (let elem in ComponentType) {
        console.log(elem)
    }
});

it('object values', () => {
    for (let elem of Object.values(ComponentType))
        console.log(elem)
});


it('object keys', () => {
    for (let elem of Object.keys(ComponentType))
        console.log(elem)
});

it('accessing using index', () => {
    console.log(ComponentType[0])
    console.log(ComponentType[1])
    console.log(ComponentType[2])
});

it('object keys filtered', () => {
    for (let elem of Object.keys(ComponentType))
        if (!isNaN(+elem))
            console.log(ComponentType[+elem])
});






