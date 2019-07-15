export const parseValidationErrors = (error: any): any => {
    const result: any = {}
    try {
        const errors = JSON.parse(error.response.data.message).errors;
        if (Array.isArray(errors)) {
            for (let {field, defaultMessage} of errors) {
                result[field] = defaultMessage
            }
        } else {
            console.warn('error when parsing error messages ' + error.response.data)
        }
    } catch (e) {
        console.warn('error when parsing error messages ' + error.response.data)
    }
    return result;
}