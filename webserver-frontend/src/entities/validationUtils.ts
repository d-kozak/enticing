import {Schema, ValidationError} from "yup";

export function validateOrNull<T>(schema: Schema<T>, obj: Object): T | null {
    try {
        return schema.validateSync(obj)
    } catch (e) {
        if (e instanceof ValidationError) {
            e.errors.forEach(error => console.log(error));
            e.inner.forEach(
                inner => inner.errors.forEach(error => console.log(error)))
            return null;
        } else {
            throw e;
        }
    }
}
