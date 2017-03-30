import { Form } from '../form';
export class FormCell {
    constructor(
        public id?: number,
        public cellCode?: string,
        public useConfidenceGrade?: boolean,
        public inputConfidenceGrade?: boolean,
        public confidenceGradeInputCode?: string,
        public rowHeadingSource?: boolean,
        public columnHeadingSource?: boolean,
        public groupDescriptionCode?: string,
        public row?: string,
        public formColumn?: string,
        public rowSpan?: string,
        public formColumnSpan?: string,
        public width?: string,
        public form?: Form,
    ) { }
}
