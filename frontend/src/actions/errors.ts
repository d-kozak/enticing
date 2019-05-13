export const parseValidationErrors = (error: any): any => {
    const result: any = {}
    try {
        const errors = JSON.parse(error.response.data.message).errors;
        if (Array.isArray(errors)) {
            for (let {field, defaultMessage} of errors) {
                result[field] = defaultMessage
            }
        }
    } catch (e) {
    }
    return result;
}