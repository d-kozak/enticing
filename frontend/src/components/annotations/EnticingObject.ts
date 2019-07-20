import {isEqual} from "lodash";

export abstract class EnticingObject {

    equals(other: Object): boolean {
        return isEqual(this, other)
    }

    toJson() {
        return JSON.stringify(this, null, 2);
    }

    dumpJson() {
        console.log(this.toJson());
    }
}
