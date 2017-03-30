import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    LineDetailsService,
    LineDetailsPopupService,
    LineDetailsComponent,
    LineDetailsDetailComponent,
    LineDetailsDialogComponent,
    LineDetailsPopupComponent,
    LineDetailsDeletePopupComponent,
    LineDetailsDeleteDialogComponent,
    lineDetailsRoute,
    lineDetailsPopupRoute,
} from './';

let ENTITY_STATES = [
    ...lineDetailsRoute,
    ...lineDetailsPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LineDetailsComponent,
        LineDetailsDetailComponent,
        LineDetailsDialogComponent,
        LineDetailsDeleteDialogComponent,
        LineDetailsPopupComponent,
        LineDetailsDeletePopupComponent,
    ],
    entryComponents: [
        LineDetailsComponent,
        LineDetailsDialogComponent,
        LineDetailsPopupComponent,
        LineDetailsDeleteDialogComponent,
        LineDetailsDeletePopupComponent,
    ],
    providers: [
        LineDetailsService,
        LineDetailsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderLineDetailsModule {}
