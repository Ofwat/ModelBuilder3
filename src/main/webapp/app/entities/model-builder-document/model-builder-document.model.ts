import { Model } from '../model';
import { Page } from '../page';
import { Section } from '../section';
import { Line } from '../line';
export class ModelBuilderDocument {
    constructor(
        public id?: number,
        public reporter?: string,
        public auditor?: string,
        public model?: Model,
        public page?: Page,
        public section?: Section,
        public line?: Line,
    ) { }
}
