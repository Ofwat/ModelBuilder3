export class PageDetails {
    constructor(
        public id?: number,
        public code?: string,
        public description?: string,
        public pageText?: string,
        public companyType?: string,
        public heading?: string,
        public commercialInConfidence?: boolean,
        public hidden?: boolean,
        public textCode?: string,
    ) { }
}
