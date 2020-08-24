import {CommandRequest} from "../../entities/CommandDto";

it('hello world', () => {
    const req: CommandRequest = {
        type: "ADD_COMPONENT",
        arguments: '123'
    }
    console.log('hello, world')
})