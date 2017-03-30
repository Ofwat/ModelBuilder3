import { SectionDetails } from '../section-details';
import { ModelBuilderDocument } from '../model-builder-document';
import { Line } from '../line';
import { Form } from '../form';
import { Page } from '../page';
export class Section {
    constructor(
        public id?: number,
        public sectionDetail?: SectionDetails,
        public documents?: ModelBuilderDocument,
        public lines?: Line,
        public forms?: Form,
        public page?: Page,
    ) { }
}
