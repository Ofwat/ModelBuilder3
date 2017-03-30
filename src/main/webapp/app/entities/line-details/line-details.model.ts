export class LineDetails {
    constructor(
        public id?: number,
        public heading?: boolean,
        public code?: string,
        public description?: string,
        public equation?: string,
        public lineNumber?: string,
        public ruleText?: string,
        public lineType?: string,
        public companyType?: string,
        public useConfidenceGrade?: boolean,
        public validationRuleCode?: string,
        public textCode?: string,
        public unit?: string,
        public decimalPlaces?: number,
    ) { }
}
