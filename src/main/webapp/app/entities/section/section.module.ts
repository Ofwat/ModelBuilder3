import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    SectionService,
    SectionPopupService,
    SectionComponent,
    SectionDetailComponent,
    SectionDialogComponent,
    SectionPopupComponent,
    SectionDeletePopupComponent,
    SectionDeleteDialogComponent,
    sectionRoute,
    sectionPopupRoute,
} from './';

let ENTITY_STATES = [
    ...sectionRoute,
    ...sectionPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SectionComponent,
        SectionDetailComponent,
        SectionDialogComponent,
        SectionDeleteDialogComponent,
        SectionPopupComponent,
        SectionDeletePopupComponent,
    ],
    entryComponents: [
        SectionComponent,
        SectionDialogComponent,
        SectionPopupComponent,
        SectionDeleteDialogComponent,
        SectionDeletePopupComponent,
    ],
    providers: [
        SectionService,
        SectionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderSectionModule {}
