import { Form } from '../form';
export class FormHeadingCell {
    constructor(
        public id?: number,
        public text?: string,
        public useLineNumber?: boolean,
        public useItemCode?: boolean,
        public useItemDescription?: boolean,
        public useUnit?: boolean,
        public useYearCode?: boolean,
        public useConfidenceGrades?: boolean,
        public row?: string,
        public formHeadingColumn?: string,
        public rowSpan?: string,
        public formHeadingColumnSpan?: string,
        public itemCode?: string,
        public yearCode?: string,
        public width?: string,
        public cellCode?: string,
        public groupDescriptionCode?: string,
        public formTop?: Form,
        public formLeft?: Form,
    ) { }
}
