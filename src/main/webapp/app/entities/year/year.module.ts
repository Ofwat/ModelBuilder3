import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    YearService,
    YearPopupService,
    YearComponent,
    YearDetailComponent,
    YearDialogComponent,
    YearPopupComponent,
    YearDeletePopupComponent,
    YearDeleteDialogComponent,
    yearRoute,
    yearPopupRoute,
} from './';

let ENTITY_STATES = [
    ...yearRoute,
    ...yearPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        YearComponent,
        YearDetailComponent,
        YearDialogComponent,
        YearDeleteDialogComponent,
        YearPopupComponent,
        YearDeletePopupComponent,
    ],
    entryComponents: [
        YearComponent,
        YearDialogComponent,
        YearPopupComponent,
        YearDeleteDialogComponent,
        YearDeletePopupComponent,
    ],
    providers: [
        YearService,
        YearPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderYearModule {}
