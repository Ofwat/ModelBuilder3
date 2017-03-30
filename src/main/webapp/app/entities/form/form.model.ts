import { FormDetails } from '../form-details';
import { FormCell } from '../form-cell';
import { FormHeadingCell } from '../form-heading-cell';
import { Section } from '../section';
export class Form {
    constructor(
        public id?: number,
        public formDetail?: FormDetails,
        public formCells?: FormCell,
        public formHeadingsTop?: FormHeadingCell,
        public formHeadingsLeft?: FormHeadingCell,
        public section?: Section,
    ) { }
}
