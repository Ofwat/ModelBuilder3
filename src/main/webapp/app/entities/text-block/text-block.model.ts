import { Text } from '../text';
export class TextBlock {
    constructor(
        public id?: number,
        public description?: string,
        public versionNumber?: string,
        public textFormatCode?: string,
        public textTypeCode?: string,
        public retired?: boolean,
        public data?: string,
        public text?: Text,
    ) { }
}
