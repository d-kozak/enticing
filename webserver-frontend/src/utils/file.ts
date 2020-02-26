export const downloadFile = (filename: string, data: string, type: string = 'application/json') => {
    const blob = new Blob([data], {type});
    if (window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveBlob(blob, filename);
    } else {
        const elem = window.document.createElement('a');
        elem.href = window.URL.createObjectURL(blob);
        elem.download = filename;
        document.body.appendChild(elem);
        elem.click();
        document.body.removeChild(elem);
    }
};

export const uploadFile = (file: File, onLoad: (content: string) => void) => {
    const fileReader = new FileReader();
    fileReader.onload = () => {
        onLoad(fileReader.result as string)
    }
    fileReader.readAsText(file);
}