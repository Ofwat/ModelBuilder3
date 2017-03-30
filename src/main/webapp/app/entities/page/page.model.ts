import { PageDetails } from '../page-details';
import { Section } from '../section';
import { ModelBuilderDocument } from '../model-builder-document';
import { Model } from '../model';
export class Page {
    constructor(
        public id?: number,
        public pageDetail?: PageDetails,
        public sections?: Section,
        public documents?: ModelBuilderDocument,
        public model?: Model,
    ) { }
}
