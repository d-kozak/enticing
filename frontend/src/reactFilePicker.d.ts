declare module "react-file-picker" {

    interface FilePickerProps {
        extensions: Array<String>,
        onChange: (file: File) => void,
        onError: (error: any) => void
    }

    declare class FilePicker extends React.Component<FilePickerProps> {
    }
}