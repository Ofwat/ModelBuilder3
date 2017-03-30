import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    FormHeadingCellService,
    FormHeadingCellPopupService,
    FormHeadingCellComponent,
    FormHeadingCellDetailComponent,
    FormHeadingCellDialogComponent,
    FormHeadingCellPopupComponent,
    FormHeadingCellDeletePopupComponent,
    FormHeadingCellDeleteDialogComponent,
    formHeadingCellRoute,
    formHeadingCellPopupRoute,
} from './';

let ENTITY_STATES = [
    ...formHeadingCellRoute,
    ...formHeadingCellPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FormHeadingCellComponent,
        FormHeadingCellDetailComponent,
        FormHeadingCellDialogComponent,
        FormHeadingCellDeleteDialogComponent,
        FormHeadingCellPopupComponent,
        FormHeadingCellDeletePopupComponent,
    ],
    entryComponents: [
        FormHeadingCellComponent,
        FormHeadingCellDialogComponent,
        FormHeadingCellPopupComponent,
        FormHeadingCellDeleteDialogComponent,
        FormHeadingCellDeletePopupComponent,
    ],
    providers: [
        FormHeadingCellService,
        FormHeadingCellPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderFormHeadingCellModule {}
