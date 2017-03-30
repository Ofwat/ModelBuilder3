import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    YearCodeService,
    YearCodePopupService,
    YearCodeComponent,
    YearCodeDetailComponent,
    YearCodeDialogComponent,
    YearCodePopupComponent,
    YearCodeDeletePopupComponent,
    YearCodeDeleteDialogComponent,
    yearCodeRoute,
    yearCodePopupRoute,
} from './';

let ENTITY_STATES = [
    ...yearCodeRoute,
    ...yearCodePopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        YearCodeComponent,
        YearCodeDetailComponent,
        YearCodeDialogComponent,
        YearCodeDeleteDialogComponent,
        YearCodePopupComponent,
        YearCodeDeletePopupComponent,
    ],
    entryComponents: [
        YearCodeComponent,
        YearCodeDialogComponent,
        YearCodePopupComponent,
        YearCodeDeleteDialogComponent,
        YearCodeDeletePopupComponent,
    ],
    providers: [
        YearCodeService,
        YearCodePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderYearCodeModule {}
