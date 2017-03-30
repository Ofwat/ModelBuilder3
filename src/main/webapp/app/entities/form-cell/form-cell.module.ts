import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    FormCellService,
    FormCellPopupService,
    FormCellComponent,
    FormCellDetailComponent,
    FormCellDialogComponent,
    FormCellPopupComponent,
    FormCellDeletePopupComponent,
    FormCellDeleteDialogComponent,
    formCellRoute,
    formCellPopupRoute,
} from './';

let ENTITY_STATES = [
    ...formCellRoute,
    ...formCellPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FormCellComponent,
        FormCellDetailComponent,
        FormCellDialogComponent,
        FormCellDeleteDialogComponent,
        FormCellPopupComponent,
        FormCellDeletePopupComponent,
    ],
    entryComponents: [
        FormCellComponent,
        FormCellDialogComponent,
        FormCellPopupComponent,
        FormCellDeleteDialogComponent,
        FormCellDeletePopupComponent,
    ],
    providers: [
        FormCellService,
        FormCellPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderFormCellModule {}
