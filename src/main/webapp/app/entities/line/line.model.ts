import { LineDetails } from '../line-details';
import { CellRange } from '../cell-range';
import { Cell } from '../cell';
import { ModelBuilderDocument } from '../model-builder-document';
import { Section } from '../section';
export class Line {
    constructor(
        public id?: number,
        public lineDetail?: LineDetails,
        public cellRange?: CellRange,
        public cells?: Cell,
        public documents?: ModelBuilderDocument,
        public section?: Section,
    ) { }
}
