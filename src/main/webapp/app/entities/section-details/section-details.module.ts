import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    SectionDetailsService,
    SectionDetailsPopupService,
    SectionDetailsComponent,
    SectionDetailsDetailComponent,
    SectionDetailsDialogComponent,
    SectionDetailsPopupComponent,
    SectionDetailsDeletePopupComponent,
    SectionDetailsDeleteDialogComponent,
    sectionDetailsRoute,
    sectionDetailsPopupRoute,
} from './';

let ENTITY_STATES = [
    ...sectionDetailsRoute,
    ...sectionDetailsPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SectionDetailsComponent,
        SectionDetailsDetailComponent,
        SectionDetailsDialogComponent,
        SectionDetailsDeleteDialogComponent,
        SectionDetailsPopupComponent,
        SectionDetailsDeletePopupComponent,
    ],
    entryComponents: [
        SectionDetailsComponent,
        SectionDetailsDialogComponent,
        SectionDetailsPopupComponent,
        SectionDetailsDeleteDialogComponent,
        SectionDetailsDeletePopupComponent,
    ],
    providers: [
        SectionDetailsService,
        SectionDetailsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderSectionDetailsModule {}
