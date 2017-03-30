export class SectionDetails {
    constructor(
        public id?: number,
        public display?: string,
        public code?: string,
        public groupType?: string,
        public useLineNumber?: boolean,
        public useItemCode?: boolean,
        public useItemDescription?: boolean,
        public useUnit?: boolean,
        public useYearCode?: boolean,
        public useConfidenceGrades?: boolean,
        public allowGroupSelection?: boolean,
        public allowDataChanges?: boolean,
        public sectionType?: string,
        public itemCodeColumn?: number,
    ) { }
}
